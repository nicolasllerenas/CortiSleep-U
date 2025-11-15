import * as msw from 'msw'

// Vite's dep prebundling can sometimes change how named exports are presented at
// runtime (CJS/ESM interop). Use a resilient access pattern so the browser-side
// module always gets the `rest` helper whether it is a named export or a
// property on the default/namespace object.
// msw exports changed across versions/builds; try common locations for the
// REST helper: named `rest`, `default.rest`, or the `http` export which
// provides similar `get/post` helpers in newer msw builds.
const rest = (msw as any).rest ?? (msw as any).default?.rest ?? (msw as any).http ?? (msw as any).default?.http

// Helper: base path used by api.ts (adjust if your api uses /api prefix)
const API_BASE = '' // leave empty to match calls like '/auth/login' or '/senses'

export const handlers = [
  // Auth login
  rest.post(`${API_BASE}/auth/login`, (_req: any, res: any, ctx: any) => {
    return res(
      ctx.status(200),
      ctx.json({ accessToken: 'mock-access-token', refreshToken: 'mock-refresh-token' }),
    )
  }),

  // Auth register (mocked same shape as login)
  rest.post(`${API_BASE}/auth/register`, async (req: any, res: any, ctx: any) => {
    const body = await req.json()
    // return tokens and a mocked user id
    return res(
      ctx.status(201),
      ctx.json({ accessToken: 'mock-access-token', refreshToken: 'mock-refresh-token', user: { id: Math.floor(Math.random()*1000)+4, email: body.email, fullName: body.name ?? '' } }),
    )
  }),

  // Auth refresh
  rest.post(`${API_BASE}/auth/refresh`, async (req: any, res: any, ctx: any) => {
    // normally would validate refreshToken, here always return a new access token
    return res(ctx.status(200), ctx.json({ accessToken: 'mock-access-token-refreshed' }))
  }),

  // Auth logout
  rest.post(`${API_BASE}/auth/logout`, (_req: any, res: any, ctx: any) => {
    return res(ctx.status(200), ctx.json({ success: true }))
  }),

  // Get senses list
  rest.get(`${API_BASE}/senses`, (_req: any) => {
    const body = JSON.stringify([
      { id: 1, title: 'Rain Sounds', type: 'AUDIO', url: '/assets/rain.mp3' },
      { id: 2, title: 'Guided Breathing', type: 'GUIDE', url: '/assets/breathing.mp3' },
    ])
    return new Response(body, { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),

  // Full content list used by SensoryContentPage
  rest.get(`${API_BASE}/senses/content`, (_req: any) => {
    const body = JSON.stringify([
      { id: 'rain-1', title: 'Rain Sounds', description: 'Relaxing rain', thumbnailUrl: '/assets/rain-thumb.jpg', viewCount: 1234 },
      { id: 'breath-1', title: 'Guided Breathing', description: 'Short breathing guide', thumbnailUrl: '/assets/breathing-thumb.jpg', viewCount: 845 },
    ])
    return new Response(body, { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),

  // User favorites for sensory content
  rest.get(`${API_BASE}/senses/me/favorites`, (_req: any) => {
    const body = JSON.stringify([
      { contentId: 'rain-1', addedAt: '2025-11-10T12:00:00Z' },
    ])
    return new Response(body, { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),

  // Profile - get my profile
  rest.get(`${API_BASE}/profile/me`, (_req: any) => {
    const body = JSON.stringify({ id: 1, name: 'Mock User', email: 'mock@example.com' })
    return new Response(body, { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),

  // Profile - update
  rest.put(`${API_BASE}/profile/me`, async (req: any) => {
    const body = await req.json()
    const resp = JSON.stringify({ id: 1, name: body.name ?? 'Mock User', email: 'mock@example.com' })
    return new Response(resp, { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),

  // Profile points
  rest.get(`${API_BASE}/profile/me/points`, (_req: any) => {
    return new Response(JSON.stringify(123), { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),

  // Checkins
  rest.get(`${API_BASE}/checkins/me`, (_req: any) => {
    const body = JSON.stringify([
      { id: 1, userId: 1, date: '2025-11-10', mood: 'HAPPY', note: 'Good day' },
      { id: 2, userId: 1, date: '2025-11-09', mood: 'TIRED', note: 'Slept late' },
    ])
    return new Response(body, { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),

  rest.post(`${API_BASE}/checkins`, async (req: any) => {
    const body = await req.json()
    return new Response(JSON.stringify({ id: Math.floor(Math.random() * 1000) + 3, ...body }), { status: 201, headers: { 'Content-Type': 'application/json' } })
  }),

  // POI - nearby and visit
  rest.post(`${API_BASE}/pois/nearby`, async (_req: any) => {
    const body = JSON.stringify([
      { id: 10, name: 'Central Park', lat: -33.0, lon: -71.6, distance: 120 },
      { id: 11, name: 'Museum', lat: -33.01, lon: -71.61, distance: 400 },
    ])
    return new Response(body, { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),

  rest.post(`${API_BASE}/pois/visit`, async (req: any) => {
    const body = await req.json()
    return new Response(JSON.stringify({ success: true, visitedAt: new Date().toISOString(), poiId: body.poiId }), { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),

  // Screen time
  rest.get(`${API_BASE}/screentime/me`, (_req: any) => {
    const body = JSON.stringify([
      { id: 1, date: '2025-11-10', totalMinutes: 120 },
      { id: 2, date: '2025-11-09', totalMinutes: 95 },
    ])
    return new Response(body, { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),

  rest.post(`${API_BASE}/screentime`, async (req: any) => {
    const body = await req.json()
    return new Response(JSON.stringify({ id: Math.floor(Math.random() * 1000) + 1, ...body }), { status: 201, headers: { 'Content-Type': 'application/json' } })
  }),

  rest.post(`${API_BASE}/screentime/app-usage`, async (req: any) => {
    await req.json()
    return new Response(JSON.stringify({ success: true, usageId: Math.floor(Math.random() * 10000) }), { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),

  rest.get(`${API_BASE}/screentime/me/stats`, (_req: any) => {
    return new Response(JSON.stringify({ avgDailyMinutes: 110, last7Days: [100, 120, 90, 140, 110, 95, 105] }), { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),

  // Focus sessions
  // List user's focus sessions (used by FocusSessionPage)
  rest.get(`${API_BASE}/focus/me`, (_req: any) => {
    const body = JSON.stringify([
      { id: 1, date: '2025-11-10', duration: 25, pointsEarned: 10 },
      { id: 2, date: '2025-11-09', duration: 45, pointsEarned: 18 },
    ])
    return new Response(body, { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),

  rest.post(`${API_BASE}/focus-sessions`, async (req: any) => {
    const body = await req.json()
    return new Response(JSON.stringify({ id: Math.floor(Math.random() * 1000) + 1, ...body }), { status: 201, headers: { 'Content-Type': 'application/json' } })
  }),

  // Gamification - quests & rewards
  rest.get(`${API_BASE}/gamification/quests`, (_req: any) => {
    const body = JSON.stringify([
      { id: 1, title: 'First Checkin', description: 'Do your first checkin', points: 10, completed: true },
      { id: 2, title: '7-Day Streak', description: 'Checkin for 7 days', points: 50, completed: false },
    ])
    return new Response(body, { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),

  // Rewards list
  rest.get(`${API_BASE}/rewards`, (_req: any) => {
    const body = JSON.stringify([
      { id: 1, title: 'Coffee Coupon', description: 'Redeem for a free coffee', pointsRequired: 50 },
      { id: 2, title: 'Extra Sleep Guide', description: 'PDF guide for better sleep', pointsRequired: 100 },
    ])
    return new Response(body, { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),

  rest.post(`${API_BASE}/gamification/rewards/redeem`, async (req: any) => {
    const body = await req.json()
    return new Response(JSON.stringify({ success: true, rewardId: body.rewardId }), { status: 200, headers: { 'Content-Type': 'application/json' } })
  }),
]

export default handlers

