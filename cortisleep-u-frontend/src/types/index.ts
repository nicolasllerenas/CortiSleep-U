// Tipos iniciales y flexibles; ampliar según DTOs del backend

export type AuthResponse = {
  accessToken: string
  refreshToken?: string
}

export type UserDto = {
  id?: number
  email?: string
  name?: string
}

export type CheckInRequest = Record<string, any>
export type CheckInResponse = Record<string, any>

export type ProfileRequest = Record<string, any>
export type ProfileResponse = Record<string, any>

export type POIResponse = {
  id?: number
  name?: string
  description?: string
  category?: string
  latitude?: number
  longitude?: number
  address?: string
  imageUrl?: string
  benefits?: string
  openingHours?: string
  university?: string
  pointsReward?: number
  averageRating?: number
  totalRatings?: number
  distanceKm?: number
}

export type NearbyPOIRequest = {
  latitude: number
  longitude: number
  radiusKm?: number
}

export type VisitPOIRequest = {
  durationMinutes?: number
  rating?: number
  comment?: string
}

export type SenseType = 'AUDIO' | 'VISUAL' | 'TACTILE' | 'OLFACTORY' | 'TASTE'

export type SensoryContent = {
  id?: number
  title?: string
  description?: string
  senseType?: SenseType
  contentUrl?: string
  thumbnailUrl?: string
  durationSeconds?: number
  tags?: string
  stressLevelMin?: number
  stressLevelMax?: number
  isActive?: boolean
  viewCount?: number
  createdAt?: string
  updatedAt?: string
}

export type UserSensoryPreference = {
  id?: number
  userId?: number
  content?: SensoryContent
  favorite?: boolean
  playCount?: number
  lastPlayedAt?: string | null
  rating?: number | null
  createdAt?: string
  updatedAt?: string
}

export type FocusSessionRequest = {
  durationMinutes?: number
  type?: string
}
export type FocusSessionResponse = Record<string, any>
export type FocusStats = Record<string, any>

export type Quest = Record<string, any>
export type Reward = Record<string, any>

export type ScreenTimeEntry = Record<string, any>
export type AppUsage = Record<string, any>
export type ScreenTimeStats = Record<string, any>

export { default as api } from '../services/api'
