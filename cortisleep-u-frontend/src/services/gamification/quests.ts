import api from '../../services/api'

function normalizeToArray<T = any>(value: any): T[] {
  if (!value && value !== 0) return []
  if (Array.isArray(value)) return value as T[]
  // common API shape: { data: [...] }
  if (value.data && Array.isArray(value.data)) return value.data as T[]
  // sometimes the service returns an object representing a single item
  if (typeof value === 'object') return [value as T]
  // fallback: not an object/array
  return []
}

export async function getAllQuests() {
  // Backend mock (and real backend) may expose quests under /gamification/quests
  const res = await api.get('/gamification/quests')
  // api.get returns parsed JSON (or whatever the response body was). Normalize to array.
  return normalizeToArray(res)
}

export async function getMyQuests() {
  const res = await api.get('/gamification/quests/me')
  return normalizeToArray(res)
}

// Aliases expected by some pages
export async function listQuests() {
  return getAllQuests()
}

export async function listMyQuests() {
  return getMyQuests()
}

export default {
  getAllQuests,
  getMyQuests,
  listQuests,
  listMyQuests,
}