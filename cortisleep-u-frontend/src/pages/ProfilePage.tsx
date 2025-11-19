import { useEffect, useState } from 'react'
import profileService from '../services/profile'
import type { ProfileRequest, ProfileResponse } from '../types'

export default function ProfilePage() {
  const [profile, setProfile] = useState<ProfileResponse | null>(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [form, setForm] = useState<ProfileRequest>({})
  const [points, setPoints] = useState<number | null>(null)
  const [editing, setEditing] = useState<boolean>(false)

  useEffect(() => {
    setLoading(true)
    profileService.getMyProfile()
      .then((res) => {
        setProfile(res)
        // sanitize values so inputs receive only strings/numbers (avoid runtime errors when backend returns objects)
        const safeForm: ProfileRequest = {
          alias: typeof res?.alias === 'string' ? res.alias : (res?.fullName ? String(res.fullName) : ''),
          faculty: typeof res?.faculty === 'string' ? res.faculty : undefined,
          semester: res?.semester != null ? Number(res.semester) : undefined,
          career: typeof res?.career === 'string' ? res.career : undefined,
          bio: typeof res?.bio === 'string' ? res.bio : undefined,
          avatarUrl: typeof res?.avatarUrl === 'string' ? res.avatarUrl : undefined,
          birthDate: typeof res?.birthDate === 'string' ? res.birthDate : (res?.birthDate ? String(res.birthDate) : undefined),
          stressLevel: res?.stressLevel != null ? Number(res.stressLevel) : undefined,
          sleepGoalHours: res?.sleepGoalHours != null ? Number(res.sleepGoalHours) : undefined,
          screenTimeLimitMinutes: res?.screenTimeLimitMinutes != null ? Number(res.screenTimeLimitMinutes) : undefined,
          preferredSenseType: typeof res?.preferredSenseType === 'string' ? (res.preferredSenseType as any) : undefined,
        }
        setForm(safeForm)
        // if a profile exists, show view mode by default; if not, enable editing to create it
        setEditing(!res)
      })
      .catch((err) => {
        // if backend returns 404 or similar, treat as no profile and open creation form
        setProfile(null)
        setEditing(true)
        setError(String(err?.body?.message || err?.message || err))
      })
      .finally(() => setLoading(false))

    profileService.getMyPoints()
      .then((res) => setPoints(res))
      .catch(() => setPoints(null))
  }, [])

  async function handleSave() {
    setError(null)
    setLoading(true)
    try {
      const payload: ProfileRequest = { ...form }
      const res = profile ? await profileService.updateProfile(payload) : await profileService.createProfile(payload)
      setProfile(res)
      // reflect returned values and switch to view mode
      populateFormFromProfile(res)
      setEditing(false)
    } catch (err: any) {
      setError(String(err?.body?.message || err?.message || err))
    } finally {
      setLoading(false)
    }
  }

  function populateFormFromProfile(res: ProfileResponse | null) {
    const safeForm: ProfileRequest = {
      alias: typeof res?.alias === 'string' ? res.alias : (res?.fullName ? String(res.fullName) : ''),
      faculty: typeof res?.faculty === 'string' ? res.faculty : undefined,
      semester: res?.semester != null ? Number(res.semester) : undefined,
      career: typeof res?.career === 'string' ? res.career : undefined,
      bio: typeof res?.bio === 'string' ? res.bio : undefined,
      avatarUrl: typeof res?.avatarUrl === 'string' ? res.avatarUrl : undefined,
      birthDate: typeof res?.birthDate === 'string' ? res.birthDate : (res?.birthDate ? String(res.birthDate) : undefined),
      stressLevel: res?.stressLevel != null ? Number(res.stressLevel) : undefined,
      sleepGoalHours: res?.sleepGoalHours != null ? Number(res.sleepGoalHours) : undefined,
      screenTimeLimitMinutes: res?.screenTimeLimitMinutes != null ? Number(res.screenTimeLimitMinutes) : undefined,
      preferredSenseType: typeof res?.preferredSenseType === 'string' ? (res.preferredSenseType as any) : undefined,
    }
    setForm(safeForm)
  }

  function handleCancel() {
    setError(null)
    if (profile) {
      populateFormFromProfile(profile)
      setEditing(false)
    } else {
      // no profile yet: clear form but remain in creation mode
      setForm({})
    }
  }

  if (loading && !profile) return <p>Cargando perfil...</p>

  return (
    <div className="text-left space-y-4">
      <h2 className="text-xl font-semibold">Mi Perfil</h2>
      {error && <p className="text-sm text-red-600">{error}</p>}

      {profile && !editing ? (
        <div className="space-y-3 max-w-md">
          <div className="flex items-center space-x-4">
            {typeof profile.avatarUrl === 'string' && profile.avatarUrl ? (
              <img src={profile.avatarUrl} alt="avatar" className="w-20 h-20 rounded-full object-cover" />
            ) : (
              <div className="w-20 h-20 rounded-full bg-gray-200 flex items-center justify-center">?
              </div>
            )}
            <div>
              <p className="font-medium">{String(profile.fullName ?? '—')}</p>
              <p className="text-sm text-gray-600">Email: {String(profile.userEmail ?? '—')}</p>
            </div>
          </div>

          <div>
            <p className="text-sm"><strong>Alias:</strong> {String(profile.alias ?? '—')}</p>
          </div>

          <div>
            <p className="text-sm"><strong>Facultad:</strong> {String(profile.faculty ?? '—')}</p>
          </div>

          <div className="grid grid-cols-2 gap-2">
            <div>
              <p className="text-sm"><strong>Semestre:</strong> {profile.semester ?? '—'}</p>
            </div>
            <div>
              <p className="text-sm"><strong>Carrera:</strong> {String(profile.career ?? '—')}</p>
            </div>
          </div>

          <div>
            <p className="text-sm"><strong>Biografía:</strong> {String(profile.bio ?? '—')}</p>
          </div>

          <div>
            <p className="text-sm"><strong>Fecha de nacimiento:</strong> {String(profile.birthDate ?? '—')}</p>
          </div>

          <div className="grid grid-cols-2 gap-2">
            <div>
              <p className="text-sm"><strong>Nivel de estrés:</strong> {profile.stressLevel ?? '—'}</p>
            </div>
            <div>
              <p className="text-sm"><strong>Horas objetivo sueño:</strong> {profile.sleepGoalHours ?? '—'}</p>
            </div>
          </div>

          <div>
            <p className="text-sm"><strong>Límite screen time:</strong> {profile.screenTimeLimitMinutes ?? '—'} min</p>
          </div>

          <div>
            <p className="text-sm"><strong>Preferencia sentido:</strong> {String(profile.preferredSenseType ?? '—')}</p>
          </div>

          <div className="flex space-x-2">
            <button onClick={() => { populateFormFromProfile(profile); setEditing(true) }} className="px-4 py-2 btn-primary rounded-md">Editar</button>
          </div>

          <div className="mt-4">
            <p>Puntos: {String(points ?? profile.totalPoints ?? '—')}</p>
          </div>
        </div>
      ) : (
        // editing mode or creating a new profile
        <div className="space-y-3 max-w-md">
          <div className="flex items-center space-x-4">
            {typeof form.avatarUrl === 'string' && form.avatarUrl ? (
              <img src={form.avatarUrl} alt="avatar" className="w-20 h-20 rounded-full object-cover" />
            ) : (
              <div className="w-20 h-20 rounded-full bg-gray-200 flex items-center justify-center">?
              </div>
            )}
            <div>
              <p className="font-medium">{String(profile?.fullName ?? form.alias ?? '—')}</p>
              <p className="text-sm text-gray-600">Email: {String(profile?.userEmail ?? '—')}</p>
            </div>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Alias</label>
            <input value={form.alias ?? ''} onChange={(e) => setForm({ ...form, alias: e.target.value })} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Facultad</label>
            <input value={form.faculty ?? ''} onChange={(e) => setForm({ ...form, faculty: e.target.value })} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
          </div>

          <div className="grid grid-cols-2 gap-2">
            <div>
              <label className="block text-sm font-medium text-gray-700">Semestre</label>
              <input type="number" min={1} max={12} value={form.semester ?? ''} onChange={(e) => setForm({ ...form, semester: e.target.value ? Number(e.target.value) : undefined })} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Carrera</label>
              <input value={form.career ?? ''} onChange={(e) => setForm({ ...form, career: e.target.value })} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
            </div>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Biografía</label>
            <textarea value={form.bio ?? ''} onChange={(e) => setForm({ ...form, bio: e.target.value })} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" rows={4} />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Avatar URL</label>
            <input value={form.avatarUrl ?? ''} onChange={(e) => setForm({ ...form, avatarUrl: e.target.value })} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Fecha de nacimiento</label>
            <input type="date" value={form.birthDate ?? ''} onChange={(e) => setForm({ ...form, birthDate: e.target.value })} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
          </div>

          <div className="grid grid-cols-2 gap-2">
            <div>
              <label className="block text-sm font-medium text-gray-700">Nivel de estrés (1-10)</label>
              <input type="number" min={1} max={10} value={form.stressLevel ?? ''} onChange={(e) => setForm({ ...form, stressLevel: e.target.value ? Number(e.target.value) : undefined })} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Horas de sueño objetivo</label>
              <input type="number" step="0.5" min={4} max={12} value={form.sleepGoalHours ?? ''} onChange={(e) => setForm({ ...form, sleepGoalHours: e.target.value ? Number(e.target.value) : undefined })} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
            </div>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Límite screen time (min)</label>
            <input type="number" min={60} value={form.screenTimeLimitMinutes ?? ''} onChange={(e) => setForm({ ...form, screenTimeLimitMinutes: e.target.value ? Number(e.target.value) : undefined })} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Preferencia de sentido</label>
            <select value={form.preferredSenseType ?? ''} onChange={(e) => setForm({ ...form, preferredSenseType: (e.target.value ? (e.target.value as any) : undefined) })} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2">
              <option value="">-- N/A --</option>
              <option value="AUDIO">Audio</option>
              <option value="VISUAL">Visual</option>
              <option value="TACTILE">Táctil</option>
              <option value="OLFACTORY">Olfativo</option>
              <option value="TASTE">Gusto</option>
            </select>
          </div>

          <div className="flex space-x-2">
            <button onClick={handleSave} className="px-4 py-2 btn-primary rounded-md">{loading ? 'Guardando...' : (profile ? 'Guardar' : 'Crear perfil')}</button>
            <button onClick={handleCancel} className="px-4 py-2 border rounded-md">Cancelar</button>
          </div>

          <div className="mt-4">
            <p>Puntos: {String(points ?? profile?.totalPoints ?? '—')}</p>
          </div>
        </div>
      )}
    </div>
  )
}
