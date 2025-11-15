import puppeteer from 'puppeteer'

async function run() {
  const url = 'http://localhost:5173/'
  const browser = await puppeteer.launch({
    args: ['--no-sandbox','--disable-setuid-sandbox','--disable-dev-shm-usage'],
    defaultViewport: { width: 1200, height: 800 },
    // use system chrome if available for better compatibility in CI/local
    executablePath: '/usr/bin/google-chrome-stable',
  })
  const page = await browser.newPage()

  try {
    // increase navigation timeout
    await page.goto(url, { waitUntil: 'networkidle2', timeout: 60000 })

    // Wait for service worker API to be available
    await page.waitForFunction(() => 'serviceWorker' in navigator, { timeout: 30000 })

    // Wait up to 30s for at least one registration or navigator.serviceWorker.ready
    const swRegistered = await page.evaluate(async () => {
      const start = Date.now()
      while (Date.now() - start < 30000) {
        try {
          const regs = await navigator.serviceWorker.getRegistrations()
          if (regs && regs.length > 0) return regs.map(r => r.scope)
          // fallback to waiting for ready
          const ready = await navigator.serviceWorker.ready
          if (ready) return (await navigator.serviceWorker.getRegistrations()).map(r => r.scope)
        } catch (e) {
          // ignore and retry
        }
        await new Promise(r => setTimeout(r, 500))
      }
      return null
    })

    if (!swRegistered) {
      throw new Error('Service worker not registered within timeout')
    }

    console.log('Service worker registrations:', swRegistered)

    console.log('Service worker registrations:', await page.evaluate(async () => (await navigator.serviceWorker.getRegistrations()).map(r=>r.scope)))

    const endpoints = ['/auth/login', '/auth/register', '/senses']
    for (const ep of endpoints) {
      const result = await page.evaluate(async (path) => {
        const res = await fetch(path, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ email: 'a@b.c', password: 'x', name: 'Tester' }) })
        const text = await res.text()
        let json = null
        try { json = text && res.headers.get('content-type')?.includes('application/json') ? JSON.parse(text) : text } catch (e) { json = text }
        return { path, status: res.status, body: json }
      }, ep)
      console.log(JSON.stringify(result, null, 2))
    }

  } catch (err) {
    console.error('Error during MSW check:', err)
    process.exitCode = 2
  } finally {
    await browser.close()
  }
}

run()
