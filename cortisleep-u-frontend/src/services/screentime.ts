import api from './api'

// Tipos básicos — ir tipando más adelante según los DTOs del backend
export type ScreenTimeEntry = Record<string, any>
export type AppUsage = Record<string, any>
export type ScreenTimeStats = Record<string, any>

/**
 * Obtiene el screen time del usuario (por defecto último mes, según backend)
 */
export async function getMyScreenTime(): Promise<ScreenTimeEntry[] | null> {
  const res = await api.get('/screentime/me')
  return (res && (res as any).data) || res
}

/**
 * Crea o actualiza una entry de screen time para una fecha.
 * El backend espera parámetros como RequestParam en POST /screentime/entries
 */
export async function upsertEntry(
  date: string, // 'yyyy-MM-dd'
  totalMinutes: number,
  deviceType?: string
): Promise<ScreenTimeEntry | null> {
  const qs = `?date=${encodeURIComponent(date)}&totalMinutes=${encodeURIComponent(
    String(totalMinutes)
  )}${deviceType ? `&deviceType=${encodeURIComponent(deviceType)}` : ''}`
  const res = await api.post(`/screentime/entries${qs}`)
  return (res && (res as any).data) || res
}

/**
 * Registra el uso de una app en una entry existente.
 * POST /screentime/apps?entryId=...&appName=...&appCategory=...&usageMinutes=...
 */
export async function addAppUsage(
  entryId: number,
  appName: string,
  usageMinutes: number,
  appCategory?: string
): Promise<AppUsage | null> {
  const qs = `?entryId=${encodeURIComponent(String(entryId))}&appName=${encodeURIComponent(appName)}&usageMinutes=${encodeURIComponent(
    String(usageMinutes)
  )}${appCategory ? `&appCategory=${encodeURIComponent(appCategory)}` : ''}`
  const res = await api.post(`/screentime/apps${qs}`)
  return (res && (res as any).data) || res
}

/**
 * Obtiene estadísticas de screen time. El backend soporta ?range=weekly|monthly (por defecto weekly)
 */
export async function getStats(range: string = 'weekly'): Promise<ScreenTimeStats | null> {
  const res = await api.get(`/screentime/stats?range=${encodeURIComponent(range)}`)
  return (res && (res as any).data) || res
}

export default {
  getMyScreenTime,
  upsertEntry,
  addAppUsage,
  getStats,
}
