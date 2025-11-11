import api from './api'
import type { FocusSessionRequest, FocusSessionResponse, FocusStats } from '../types'

export async function startSession(payload: FocusSessionRequest): Promise<FocusSessionResponse | null> {
  const res = await api.post('/focus/start', payload)
  return (res && (res as any).data) || res
}

export async function completeSession(id: number): Promise<FocusSessionResponse | null> {
  const res = await api.put(`/focus/${id}/complete`)
  return (res && (res as any).data) || res
}

export async function getMySessions(page?: number, size?: number) {
  const qs = page !== undefined ? `?page=${page}${size !== undefined ? `&size=${size}` : ''}` : ''
  const res = await api.get(`/focus/me${qs}`)
  return (res && (res as any).data) || res
}

export async function getStats(): Promise<FocusStats | null> {
  const res = await api.get('/focus/me/stats')
  return (res && (res as any).data) || res
}

export default {
  startSession,
  completeSession,
  getMySessions,
  getStats,
}