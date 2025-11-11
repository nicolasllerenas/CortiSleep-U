import api from '../../services/api'

export async function getAllRewards() {
  const res = await api.get('/rewards')
  return (res && (res as any).data) || res
}

export async function listRewards() {
  return getAllRewards()
}

export default {
  getAllRewards,
  listRewards,
}