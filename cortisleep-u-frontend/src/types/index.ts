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

export type CheckInRequest = {
  locationName?: string
  latitude?: number | null
  longitude?: number | null
  moodScore?: number | null
  stressLevel?: number | null
  energyLevel?: number | null
  notes?: string | null
  // frontend may send a date string (YYYY-MM-DD) but backend DTO currently doesn't include it;
  // backend may derive date from checkInTime. Keep optional here for flexibility.
  date?: string | null
}

export type CheckInResponse = {
  id?: number
  userId?: number
  locationName?: string
  latitude?: number | null
  longitude?: number | null
  moodScore?: number | null
  stressLevel?: number | null
  energyLevel?: number | null
  notes?: string | null
  date?: string | null
  checkInTime?: string | null
  createdAt?: string | null
  updatedAt?: string | null
}

export type ProfileRequest = {
  alias?: string
  faculty?: string
  semester?: number | null
  career?: string
  bio?: string
  avatarUrl?: string
  birthDate?: string | null // YYYY-MM-DD
  stressLevel?: number | null
  sleepGoalHours?: number | null
  screenTimeLimitMinutes?: number | null
  preferredSenseType?: SenseType | null
}

export type ProfileResponse = {
  id?: number
  userId?: number
  userEmail?: string
  fullName?: string
  alias?: string
  faculty?: string
  semester?: number | null
  career?: string
  bio?: string
  avatarUrl?: string
  birthDate?: string | null
  age?: number | null
  totalPoints?: number | null
  stressLevel?: number | null
  sleepGoalHours?: number | null
  screenTimeLimitMinutes?: number | null
  preferredSenseType?: SenseType | null
  createdAt?: string | null
}

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

export type SleepEntryRequest = {
  sleepAt: string // ISO datetime
  wakeAt: string // ISO datetime
}

export type SleepEntryResponse = {
  id?: number
  userId?: number
  sleepAt?: string
  wakeAt?: string
  durationMinutes?: number
  createdAt?: string
}
