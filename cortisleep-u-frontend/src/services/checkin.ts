import api from './api'

type CheckInRequest = Record<string, any>

export async function createCheckIn(payload: CheckInRequest) {
  const res = await api.post('/checkins', payload)
  return (res && (res as any).data) || res
}

export async function getCheckInById(id: number) {
  const res = await api.get(`/checkins/${id}`)
  return (res && (res as any).data) || res
}

export async function getMyCheckIns() {
  const res = await api.get('/checkins/me')
  return (res && (res as any).data) || res
}

export async function getCheckInByDate(date: string) {
  const res = await api.get(`/checkins/me/date/${date}`)
  return (res && (res as any).data) || res
}

export async function getCheckInsByRange(startDate: string, endDate: string) {
  const res = await api.get(`/checkins/me/range?startDate=${startDate}&endDate=${endDate}`)
  return (res && (res as any).data) || res
}

export async function getStats(start?: string, end?: string) {
  const qs = start || end ? `?${start ? `startDate=${start}` : ''}${start && end ? '&' : ''}${end ? `endDate=${end}` : ''}` : ''
  const res = await api.get(`/checkins/me/stats${qs}`)
  return (res && (res as any).data) || res
}

export default {
  createCheckIn,
  getCheckInById,
  getMyCheckIns,
  getCheckInByDate,
  getCheckInsByRange,
  getStats,
}
