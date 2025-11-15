import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'

// Start MSW in development for API mocking
// Start MSW in development for API mocking. Controlled by VITE_USE_MSW.
// If VITE_USE_MSW is undefined we keep the previous behavior (start by default in DEV).
const shouldUseMsw = import.meta.env.DEV && (
  import.meta.env.VITE_USE_MSW === undefined || import.meta.env.VITE_USE_MSW === 'true'
)

if (shouldUseMsw) {
  // dynamic import so MSW code is not included in production bundles
  import('./mocks/browser')
    .then(async (mod) => {
      if (typeof (mod as any).startWorker === 'function') {
        try {
          // startWorker will resolve setupWorker dynamically and start it
          await (mod as any).startWorker({ onUnhandledRequest: 'bypass', serviceWorker: { url: '/mockServiceWorker.js' } })
          // eslint-disable-next-line no-console
          console.info('MSW worker started (dev)')
        } catch (err) {
          // eslint-disable-next-line no-console
          console.warn('MSW worker start failed, attempting manual registration:', err)
          try {
            // Try to register the service worker file directly so it appears in DevTools
            if ('serviceWorker' in navigator) {
              await navigator.serviceWorker.register('/mockServiceWorker.js')
              // eslint-disable-next-line no-console
              console.info('Registered /mockServiceWorker.js manually (dev)')
            }
          } catch (regErr) {
            // eslint-disable-next-line no-console
            console.error('Manual service worker registration failed:', regErr)
          }
        }
      } else {
        // eslint-disable-next-line no-console
        console.error('MSW: startWorker not found on imported module', mod)
      }
    })
    .catch((e) => {
      // eslint-disable-next-line no-console
      console.warn('MSW dynamic import failed (dev only):', e)
    })
}

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
