import api from './api'

type ProfileRequest = Record<string, any>

export async function getMyProfile() {
  const res = await api.get('/profiles/me')
  return (res && (res as any).data) || res
}

export async function createProfile(payload: ProfileRequest) {
  const res = await api.post('/profiles', payload)
  return (res && (res as any).data) || res
}

export async function updateProfile(payload: ProfileRequest) {
  const res = await api.put('/profiles/me', payload)
  return (res && (res as any).data) || res
}

export async function deleteProfile() {
  const res = await api.delete('/profiles/me')
  return res
}

export async function getMyPoints() {
  const res = await api.get('/profiles/me/points')
  return (res && (res as any).data) || res
}

export default {
  getMyProfile,
  createProfile,
  updateProfile,
  deleteProfile,
  getMyPoints,
}
