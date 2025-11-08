import { useState } from 'react'
import appLogo from './assets/Reset-U_logo.svg'
import appTextLogo from './assets/Reset-U_text_logo.svg'
import LoginForm from './components/LoginForm'
import RegisterForm from './components/RegisterForm'
import { AuthProvider, useAuth } from './contexts/AuthContext'
import ProfilePage from './pages/ProfilePage'
import CheckinsPage from './pages/CheckinsPage'

function AppInner() {
  const [view, setView] = useState<'home' | 'login' | 'register' | 'profile' | 'checkins'>('home')
  const { isAuthenticated, logout } = useAuth()

  function handleLoginSuccess() {
    setView('home')
  }

  function handleLogout() {
    logout()
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-white">
  <div className="text-center space-y-6 p-8 block-option rounded-2xl shadow-xl max-w-md w-full">
        <div className="flex justify-between items-center">
          <div className="flex items-center space-x-4">
            <img src={appLogo} className="h-8 w-8 object-contain" style={{ maxWidth: 40, maxHeight: 40 }} alt="Reset U logo" />
            <img src={appTextLogo} className="h-8 w-auto object-contain" style={{ maxHeight: 40 }} alt="Reset U text logo" />
          </div>

          <div className="space-x-2">
            {isAuthenticated ? (
              <button
                onClick={handleLogout}
                className="px-4 py-2 bg-red-500 text-white rounded-md"
              >
                Logout
              </button>
            ) : (
              <>
                <button onClick={() => setView('login')} className="px-4 py-2 btn-primary rounded-md">
                  Login
                </button>
                <button onClick={() => setView('register')} className="px-4 py-2 btn-primary rounded-md">
                  Register
                </button>
              </>
            )}
          </div>
        </div>

        <div className="mt-4">
          {view === 'home' && (
            <div className="space-y-4">
              <p className="text-gray-600">Bienvenido a Reset U. Usa Login o Register para continuar.</p>
            </div>
          )}

          {view === 'profile' && <ProfilePage />}
          {view === 'checkins' && <CheckinsPage />}

          {view === 'login' && (
            <LoginForm onSuccess={() => handleLoginSuccess()} onCancel={() => setView('home')} />
          )}

          {view === 'register' && (
            <RegisterForm onSuccess={() => handleLoginSuccess()} onCancel={() => setView('home')} />
          )}
        </div>
      </div>
    </div>
  )
}

export default function App() {
  return (
    <AuthProvider>
      <AppInner />
    </AuthProvider>
  )
}
