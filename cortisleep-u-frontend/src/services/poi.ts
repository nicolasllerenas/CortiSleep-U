import api from './api'
import type { POIResponse, NearbyPOIRequest, VisitPOIRequest } from '../types'

export async function getAllPOIs(): Promise<POIResponse[]> {
  const res = await api.get('/poi')
  const data = (res && (res as any).data) || []
  return data
}

export async function getPOIById(id: string | number): Promise<POIResponse | null> {
  const res = await api.get(`/poi/${encodeURIComponent(String(id))}`)
  const data = (res && (res as any).data) || null
  return data
}

export async function getNearbyPOIs(request: NearbyPOIRequest): Promise<POIResponse[]> {
  const res = await api.post('/poi/nearby', request)
  const data = (res && (res as any).data) || []
  return data
}

export async function searchPOIs(q: string): Promise<POIResponse[]> {
  const res = await api.get(`/poi/search?q=${encodeURIComponent(q)}`)
  const data = (res && (res as any).data) || []
  return data
}

export async function visitPOI(id: string | number, payload: VisitPOIRequest) {
  const res = await api.post(`/poi/${encodeURIComponent(String(id))}/visit`, payload)
  const data = (res && (res as any).data) || null
  return data
}

export async function getMyVisits(page?: number, size?: number) {
  const qs = page !== undefined ? `?page=${page}${size !== undefined ? `&size=${size}` : ''}` : ''
  const res = await api.get(`/poi/me/visits${qs}`)
  const data = (res && (res as any).data) || []
  return data
}

export default {
  getAllPOIs,
  getPOIById,
  getNearbyPOIs,
  searchPOIs,
  visitPOI,
  getMyVisits,
  // aliases for existing pages/components that expected these names
  listPOIs: getAllPOIs,
  getPOI: getPOIById,
}