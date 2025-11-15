const DEFAULT_API_URL = 'http://localhost:8081'

function getBaseUrl() {
  // Distinguish between the env var being undefined vs explicitly set to empty string.
  // When VITE_API_URL is explicitly set to an empty string (as in the `dev:mock` script),
  // we want to return '' so the frontend uses relative paths and the in-browser MSW
  // service worker can intercept requests. If the env var is undefined, fall back to
  // the default API URL (useful when running against a real backend).
  const env = (import.meta.env.VITE_API_URL as string | undefined)
  if (env !== undefined) return env
  return DEFAULT_API_URL
}

function getAccessToken() {
  try {
    return localStorage.getItem('accessToken')
  } catch (e) {
    return null
  }
}

function getRefreshToken() {
  try {
    return localStorage.getItem('refreshToken')
  } catch (e) {
    return null
  }
}

function saveTokensFromResponse(obj: any) {
  const tokens = obj?.data ?? obj
  try {
    if (tokens?.accessToken) localStorage.setItem('accessToken', tokens.accessToken)
    if (tokens?.refreshToken) localStorage.setItem('refreshToken', tokens.refreshToken)
  } catch (e) {
    // ignore storage errors
  }
}

let refreshing: Promise<boolean> | null = null

async function refreshAccessToken(): Promise<boolean> {
  // If a refresh is already in progress, wait for it
  if (refreshing) return refreshing

  const base = getBaseUrl()
  const refreshToken = getRefreshToken()
  if (!refreshToken) return false

  refreshing = (async () => {
    try {
      const res = await fetch(`${base}/auth/refresh`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ refreshToken }),
      })

      const text = await res.text()
      const isJson = res.headers.get('content-type')?.includes('application/json') ?? false
      const parsed = text && isJson ? JSON.parse(text) : text

      if (!res.ok) {
        // clear tokens on failed refresh
        try { localStorage.removeItem('accessToken'); localStorage.removeItem('refreshToken') } catch (e) {}
        return false
      }

      saveTokensFromResponse(parsed)
      return true
    } catch (e) {
      try { localStorage.removeItem('accessToken'); localStorage.removeItem('refreshToken') } catch (err) {}
      return false
    } finally {
      // reset refreshing after a tick so concurrent callers get the resolved value
      const p = refreshing
      refreshing = null
      return p ? await p : false
    }
  })()

  return refreshing
}

async function request<T = any>(
  path: string,
  options: RequestInit = {},
  attempt = 0
): Promise<T> {
  const base = getBaseUrl()
  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...(options.headers as Record<string, string>),
  }

  const token = getAccessToken()
  if (token) headers['Authorization'] = `Bearer ${token}`

  const res = await fetch(`${base}${path}`, {
    ...options,
    headers,
  })

  const text = await res.text()
  const isJson = res.headers.get('content-type')?.includes('application/json') ?? false

  if (res.status === 401 && attempt === 0) {
    const refreshed = await refreshAccessToken()
    if (refreshed) {
      // retry once with new token
      return request<T>(path, options, attempt + 1)
    }
    // else fall through to throw below
  }

  if (!res.ok) {
    const body = text && isJson ? JSON.parse(text) : text
    throw { status: res.status, body }
  }

  if (!text) return null as unknown as T
  return isJson ? JSON.parse(text) as T : (text as unknown as T)
}

export async function apiGet<T = any>(path: string): Promise<T> {
  return request<T>(path, { method: 'GET' })
}

export async function apiPost<T = any>(path: string, body?: any): Promise<T> {
  return request<T>(path, { method: 'POST', body: body ? JSON.stringify(body) : undefined })
}

export async function apiPut<T = any>(path: string, body?: any): Promise<T> {
  return request<T>(path, { method: 'PUT', body: body ? JSON.stringify(body) : undefined })
}

export async function apiDelete<T = any>(path: string): Promise<T> {
  return request<T>(path, { method: 'DELETE' })
}

export default {
  get: apiGet,
  post: apiPost,
  put: apiPut,
  delete: apiDelete,
}
