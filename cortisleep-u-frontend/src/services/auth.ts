import api from './api'
import screentimeService from './screentime'

type LoginRequest = {
  email: string
  password: string
}

type RegisterRequest = {
  email: string
  password: string
  firstName: string
  lastName: string
}

type AuthResponse = {
  accessToken: string
  refreshToken?: string
}

const ACCESS_KEY = 'accessToken'
const REFRESH_KEY = 'refreshToken'

function saveTokens(data: AuthResponse) {
  try {
    if (data.accessToken) localStorage.setItem(ACCESS_KEY, data.accessToken)
    if (data.refreshToken) localStorage.setItem(REFRESH_KEY, data.refreshToken)
    // mark session start when tokens are saved (login/register)
    try { localStorage.setItem('sessionStart', String(Date.now())) } catch (e) {}
  } catch (e) {
    // ignore storage errors
  }
}

export async function login(payload: LoginRequest) {
  const res = await api.post<{ data: AuthResponse }>('/auth/login', payload)
  // backend wraps responses in ApiResponse<T> where payload is in `data`
  const tokens = (res && (res as any).data) || res
  saveTokens(tokens)
  return tokens
}

export async function register(payload: RegisterRequest) {
  const res = await api.post<{ data: AuthResponse }>('/auth/register', payload)
  const tokens = (res && (res as any).data) || res
  saveTokens(tokens)
  return tokens
}

async function sendScreenTimeIfAny(): Promise<void> {
  try {
    const start = localStorage.getItem('sessionStart')
    if (!start) return
    const startTs = Number(start)
    if (!startTs || Number.isNaN(startTs)) return
    const minutes = Math.max(1, Math.round((Date.now() - startTs) / 60000))
    const date = new Date().toISOString().slice(0, 10)
    try {
      await screentimeService.upsertEntry(date, minutes)
    } catch (e) {
      // ignore errors when flushing on logout
    } finally {
      try { localStorage.removeItem('sessionStart') } catch (e) {}
    }
  } catch (e) {
    // swallow errors
  }
}

export async function refresh(refreshToken?: string) {
  const token = refreshToken || localStorage.getItem(REFRESH_KEY)
  if (!token) throw new Error('No refresh token available')
  const res = await api.post<{ data: AuthResponse }>('/auth/refresh', { refreshToken: token })
  const tokens = (res && (res as any).data) || res
  saveTokens(tokens)
  return tokens
}

export function logoutLocal() {
  try {
    localStorage.removeItem(ACCESS_KEY)
    localStorage.removeItem(REFRESH_KEY)
  } catch (e) {}
}

export async function logoutServer() {
  try {
    // flush screen time using authenticated request before logging out
    await sendScreenTimeIfAny()
    await api.post('/auth/logout')
  } finally {
    logoutLocal()
  }
}

/**
 * Flush session duration during unload using keepalive fetch (used in beforeunload).
 * This tries to hit the same endpoint the backend exposes. It reads the access token
 * from localStorage and uses a keepalive POST so the browser can attempt to send
 * it while unloading.
 */
export function flushSessionKeepalive() {
  try {
    const start = localStorage.getItem('sessionStart')
    if (!start) return
    const startTs = Number(start)
    if (!startTs || Number.isNaN(startTs)) return
    const minutes = Math.max(1, Math.round((Date.now() - startTs) / 60000))
    const date = new Date().toISOString().slice(0, 10)

    // Reconstruct base URL similar to api.getBaseUrl logic
    const DEFAULT_API_URL = 'http://localhost:8081/api/v1'
    const env = (import.meta.env.VITE_API_URL as string | undefined)
    const base = env !== undefined ? env : DEFAULT_API_URL
    const url = `${base}/screentime/entries?date=${encodeURIComponent(date)}&totalMinutes=${encodeURIComponent(String(minutes))}`

    const token = localStorage.getItem(ACCESS_KEY)
    const headers: Record<string, string> = { 'Content-Type': 'application/json' }
    if (token) headers['Authorization'] = `Bearer ${token}`

    // Use keepalive fetch; send empty body as backend reads params
    try {
      navigator.sendBeacon
        ? navigator.sendBeacon(url)
        : fetch(url, { method: 'POST', headers, keepalive: true })
    } catch (e) {
      try { fetch(url, { method: 'POST', headers, keepalive: true }) } catch (err) {}
    } finally {
      try { localStorage.removeItem('sessionStart') } catch (e) {}
    }
  } catch (e) {}
}

export default {
  login,
  register,
  refresh,
  logoutLocal,
  logoutServer,
  flushSessionKeepalive,
}
