import api from './api'
import type { SleepEntryRequest, SleepEntryResponse } from '../types'

export async function createSleepEntry(payload: SleepEntryRequest): Promise<SleepEntryResponse | null> {
  const res = await api.post('/sleep', payload)
  // backend wraps payload in ApiResponse { success, message, data }
  return (res && (res.data ?? res)) ?? null
}

export async function getMySleepEntries(from?: string, to?: string): Promise<SleepEntryResponse[]> {
  const params = new URLSearchParams()
  if (from) params.append('from', from)
  if (to) params.append('to', to)
  const qs = params.toString() ? `?${params.toString()}` : ''
  const res = await api.get(`/sleep/me${qs}`)
  return (res && (res.data ?? res)) ?? []
}

export default { createSleepEntry, getMySleepEntries }
