import api from './api'

type LoginRequest = {
  email: string
  password: string
}

type RegisterRequest = {
  email: string
  password: string
  name?: string
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
    await api.post('/auth/logout')
  } finally {
    logoutLocal()
  }
}

export default {
  login,
  register,
  refresh,
  logoutLocal,
  logoutServer,
}
