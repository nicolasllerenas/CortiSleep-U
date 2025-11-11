import api from '../../services/api'

export async function getAllQuests() {
  const res = await api.get('/quests')
  return (res && (res as any).data) || res
}

export async function getMyQuests() {
  const res = await api.get('/quests/me')
  return (res && (res as any).data) || res
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