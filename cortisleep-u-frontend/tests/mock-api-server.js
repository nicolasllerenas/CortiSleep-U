import http from 'http'
import { parse as parseUrl } from 'url'

const PORT = process.env.PORT || 8082

function readBody(req) {
  return new Promise((resolve, reject) => {
    let body = ''
    req.on('data', chunk => { body += chunk })
    req.on('end', () => resolve(body ? JSON.parse(body) : {}))
    req.on('error', reject)
  })
}

const server = http.createServer(async (req, res) => {
  const parsed = parseUrl(req.url, true)
  const path = parsed.pathname
  res.setHeader('Content-Type', 'application/json')

  try {
    if (req.method === 'POST' && path === '/auth/login') {
      const body = await readBody(req)
      return res.end(JSON.stringify({ accessToken: 'mock-access-token', refreshToken: 'mock-refresh-token', user: { id: 1, email: body.email || 'mock@example.com' } }))
    }

    if (req.method === 'POST' && path === '/auth/register') {
      const body = await readBody(req)
      const first = body.firstName || ''
      const last = body.lastName || ''
      const fullName = `${first} ${last}`.trim()
      return res.end(JSON.stringify({ accessToken: 'mock-access-token', refreshToken: 'mock-refresh-token', user: { id: Math.floor(Math.random()*1000)+4, email: body.email, fullName } }))
    }

    if (req.method === 'POST' && path === '/auth/refresh') {
      return res.end(JSON.stringify({ accessToken: 'mock-access-token-refreshed' }))
    }

    if (req.method === 'GET' && path === '/senses') {
      return res.end(JSON.stringify([
        { id: 1, title: 'Rain Sounds', type: 'AUDIO', url: '/assets/rain.mp3' },
        { id: 2, title: 'Guided Breathing', type: 'GUIDE', url: '/assets/breathing.mp3' }
      ]))
    }

    if (req.method === 'GET' && path === '/profile/me') {
      return res.end(JSON.stringify({ id: 1, fullName: 'Mock User', userEmail: 'mock@example.com' }))
    }

    if (req.method === 'GET' && path === '/checkins/me') {
      return res.end(JSON.stringify([
        { id: 1, userId: 1, date: '2025-11-10', mood: 'HAPPY', note: 'Good day' },
        { id: 2, userId: 1, date: '2025-11-09', mood: 'TIRED', note: 'Slept late' }
      ]))
    }

    if (req.method === 'POST' && path === '/checkins') {
      const body = await readBody(req)
      return res.end(JSON.stringify({ id: Math.floor(Math.random()*1000)+3, ...body }))
    }

    // default 404
    res.statusCode = 404
    return res.end(JSON.stringify({ error: 'not found' }))
  } catch (err) {
    res.statusCode = 500
    return res.end(JSON.stringify({ error: String(err) }))
  }
})

server.listen(PORT, () => {
  console.log('Mock API server listening on port', PORT)
})
