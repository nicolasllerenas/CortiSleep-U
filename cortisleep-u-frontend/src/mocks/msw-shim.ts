// Small shim to re-export msw browser setupWorker from the public browser
// entry. Using `msw/browser` aligns with the package's documented import
// surface and avoids deep internal imports that Vite may block.
export { setupWorker } from 'msw/browser'
