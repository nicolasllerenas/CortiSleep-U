import api from './api'

export async function listSenses() {
    const res = await api.get('/senses/content')
    return (res && (res as any).data) || res
}

export async function getSenseByType(type: string) {
    const res = await api.get(`/senses/content/type/${encodeURIComponent(type)}`)
    return (res && (res as any).data) || res
}

export async function increaseView(id: string) {
    const res = await api.post(`/senses/content/${encodeURIComponent(id)}/view`)
    return (res && (res as any).data) || res
}

export async function toggleFavorite(id: string) {
    const res = await api.put(`/senses/content/${encodeURIComponent(id)}/favorite`)
    return (res && (res as any).data) || res
}

export async function registerReproduction(id: string) {
    const res = await api.put(`/senses/content/${encodeURIComponent(id)}/play`)
    return (res && (res as any).data) || res
}

export async function getRecommended(type?: string, stress?: number) { 
    const qs = `?${type ? `type=${encodeURIComponent(type)}` : ''}${type && stress !== undefined ? '&' : ''}${
        stress !== undefined ? `stress=${encodeURIComponent(String(stress))}` : ''
    }`
    const res = await api.get(`/senses/recommended${qs.replace(/\?$/, '')}`)
    return (res && (res as any).data) || res
}

export async function getMyFavorites() {
    const res = await api.get('/senses/me/favorites')
    return (res && (res as any).data) || res
}

export default {
    listSenses,
    getSenseByType,
    increaseView,
    toggleFavorite,
    registerReproduction,
    getRecommended,
    getMyFavorites,
}