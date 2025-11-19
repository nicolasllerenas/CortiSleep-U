import { createContext, useContext, useState, useEffect } from 'react'
import type { ReactNode } from 'react'
import authService from '../services/auth'

type LoginPayload = { email: string; password: string }
type RegisterPayload = { email: string; password: string; firstName: string; lastName: string }

type AuthContextValue = {
  isAuthenticated: boolean
  login: (payload: LoginPayload) => Promise<any>
  register: (payload: RegisterPayload) => Promise<any>
  logout: () => void
}

const AuthContext = createContext<AuthContextValue | undefined>(undefined)

export function AuthProvider({ children }: { children: ReactNode }) {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(() => {
    try {
      return !!localStorage.getItem('accessToken')
    } catch (e) {
      return false
    }
  })

  useEffect(() => {
    // keep auth state in sync with localStorage changes (optional)
    function onStorage(e: StorageEvent) {
      if (e.key === 'accessToken') {
        setIsAuthenticated(!!e.newValue)
      }
    }
    window.addEventListener('storage', onStorage)
    return () => window.removeEventListener('storage', onStorage)
  }, [])

  async function login(payload: LoginPayload) {
    const tokens = await authService.login(payload)
    setIsAuthenticated(true)
    return tokens
  }

  async function register(payload: RegisterPayload) {
    const tokens = await authService.register(payload)
    setIsAuthenticated(true)
    return tokens
  }

  function logout() {
    // perform server logout and flush screen time; update auth state when done
    authService.logoutServer().finally(() => setIsAuthenticated(false))
  }

  useEffect(() => {
    function onBeforeUnload() {
      try {
        authService.flushSessionKeepalive()
      } catch (e) {}
    }
    window.addEventListener('beforeunload', onBeforeUnload)
    return () => window.removeEventListener('beforeunload', onBeforeUnload)
  }, [])

  const value: AuthContextValue = {
    isAuthenticated,
    login,
    register,
    logout,
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export function useAuth() {
  const ctx = useContext(AuthContext)
  if (!ctx) throw new Error('useAuth must be used within AuthProvider')
  return ctx
}

export default AuthContext
