import { useState, Suspense, lazy } from 'react'
import appLogo from './assets/Reset-U_logo.svg'
import appTextLogo from './assets/Reset-U_text_logo.svg'
import LoginForm from './components/LoginForm'
import RegisterForm from './components/RegisterForm'
import { AuthProvider, useAuth } from './contexts/AuthContext'
import ProfilePage from './pages/ProfilePage'
import CheckinsPage from './pages/CheckinsPage'
import RewardsPage from './pages/RewardsPage'
import POIPage from './pages/POIPage'
import FocusSessionPage from './pages/FocusSessionPage'
import ScreenTimePage from './pages/ScreenTimePage'
import SensoryContentPage from './pages/SensoryContentPage'
import QuestsPage from './pages/QuestsPage'
import MainMenu from './components/MainMenu'

const DevGallery = lazy(() => import('./pages/DevGallery'))

function AppInner() {
  const [view, setView] = useState<'home' | 'login' | 'register' | 'profile' | 'checkins' | 'dev-gallery' | 'rewards' | 'poi' | 'focus' | 'screentime' | 'sensory' | 'quests' | 'menu'>('home')
  const { isAuthenticated, logout } = useAuth()

  function handleLoginSuccess() {
    setView('home')
  }

  function handleLogout() {
    logout()
    setView('home')
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

       {/* Main menu for authenticated users (shows all pages) */}
       {isAuthenticated && (
         <MainMenu current={view as any} onNavigate={(v) => setView(v)} />
       )}

       {/* Dev-only quick access to preview all pages */}
       {import.meta.env.DEV && (
         <div className="mt-4">
           <button
             onClick={() => setView('dev-gallery')}
             className="px-3 py-2 text-xs border rounded text-black font-semibold"
             style={{ backgroundColor: '#D9A441' }}
           >
             Preview all pages (dev)
           </button>
         </div>
       )}

  <div className="mt-4 text-center">
         {view === 'home' && (
           <div className="space-y-4">
             <p className="text-gray-700 text-sm sm:text-base">Bienvenido a Reset U. Usa Login o Register para continuar.</p>
             {isAuthenticated && (
               <p className="text-gray-600 text-sm">Puedes gestionar tu perfil o registrar check-ins.</p>
             )}
           </div>
         )}

         {/* Protected views if not authenticated */}
         {!isAuthenticated && (view === 'profile' || view === 'checkins') ? (
           <div className="space-y-3">
             <p className="text-gray-700">Debes iniciar sesión para acceder a esta sección.</p>
             <div className="flex gap-2">
               <button onClick={() => setView('login')} className="px-4 py-2 btn-primary rounded-md">Login</button>
               <button onClick={() => setView('register')} className="px-4 py-2 btn-primary rounded-md">Registrar</button>
             </div>
           </div>
         ) : (
           <>
             {view === 'profile' && <ProfilePage />}
             {view === 'checkins' && <CheckinsPage />}
            {view === 'rewards' && <RewardsPage />}
            {view === 'poi' && <POIPage />}
            {view === 'focus' && <FocusSessionPage />}
            {view === 'screentime' && <ScreenTimePage />}
            {view === 'sensory' && <SensoryContentPage />}
            {view === 'quests' && <QuestsPage />}
             {view === 'dev-gallery' && (
               <Suspense fallback={<div>Loading preview...</div>}>
                 <DevGallery />
               </Suspense>
             )}

             {view === 'login' && (
               <LoginForm onSuccess={() => handleLoginSuccess()} onCancel={() => setView('home')} />
             )}

             {view === 'register' && (
               <RegisterForm onSuccess={() => handleLoginSuccess()} onCancel={() => setView('home')} />
             )}
           </>
         )}
       </div>

       {/* Bottom nav for mobile */}
       {isAuthenticated && (
         <div className="sm:hidden fixed bottom-4 left-0 right-0 mx-auto max-w-md px-4">
           <div className="bg-white/90 backdrop-blur border rounded-xl shadow-md flex justify-around py-2">
             <button onClick={() => setView('profile')} className={`px-3 py-2 text-sm ${view==='profile' ? 'text-blue-600 font-semibold' : 'text-gray-700'}`}>Perfil</button>
             <button onClick={() => setView('checkins')} className={`px-3 py-2 text-sm ${view==='checkins' ? 'text-blue-600 font-semibold' : 'text-gray-700'}`}>Check-ins</button>
             <button onClick={() => setView('home')} className={`px-3 py-2 text-sm ${view==='home' ? 'text-blue-600 font-semibold' : 'text-gray-700'}`}>Inicio</button>
               <button onClick={() => setView('menu')} className={`px-3 py-2 text-sm ${view==='menu' ? 'text-blue-600 font-semibold' : 'text-gray-700'}`}>Menu</button>
           </div>
         </div>
       )}

        {/* Menu overlay for mobile when user taps Menu */}
        {isAuthenticated && view === 'menu' && (
          <div className="fixed inset-0 z-40 flex items-center justify-center bg-black/40 p-4">
            <div className="bg-white rounded-xl shadow-lg w-full max-w-lg p-4">
              <div className="flex justify-between items-center mb-2">
                <h3 className="text-lg font-semibold">Menú</h3>
                <button onClick={() => setView('home')} className="px-3 py-1 border rounded">Cerrar</button>
              </div>
              <MainMenu current={view as any} onNavigate={(v) => { setView(v); window.scrollTo({top:0}); }} />
            </div>
          </div>
        )}
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
