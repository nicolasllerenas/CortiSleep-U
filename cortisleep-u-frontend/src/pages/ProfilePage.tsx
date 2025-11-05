import { useEffect, useState } from 'react'
import profileService from '../services/profile'

export default function ProfilePage() {
  const [profile, setProfile] = useState<any>(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [editName, setEditName] = useState('')
  const [points, setPoints] = useState<number | null>(null)

  useEffect(() => {
    setLoading(true)
    profileService.getMyProfile()
      .then((res) => {
        setProfile(res)
        setEditName(res?.name ?? '')
      })
      .catch((err) => setError(String(err?.body?.message || err?.message || err)))
      .finally(() => setLoading(false))

    profileService.getMyPoints()
      .then((res) => setPoints(res))
      .catch(() => setPoints(null))
  }, [])

  async function handleSave() {
    setError(null)
    setLoading(true)
    try {
      const res = await profileService.updateProfile({ name: editName })
      setProfile(res)
    } catch (err: any) {
      setError(String(err?.body?.message || err?.message || err))
    } finally {
      setLoading(false)
    }
  }

  if (loading && !profile) return <p>Cargando perfil...</p>

  return (
    <div className="text-left space-y-4">
      <h2 className="text-xl font-semibold">Mi Perfil</h2>
      {error && <p className="text-sm text-red-600">{error}</p>}

      {profile ? (
        <div className="space-y-3">
          <div>
            <label className="block text-sm font-medium text-gray-700">Nombre</label>
            <input value={editName} onChange={(e) => setEditName(e.target.value)} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
          </div>

          <div>
            <p className="text-sm text-gray-600">Email: {profile.email}</p>
            <p className="text-sm text-gray-600">ID: {profile.id}</p>
          </div>

          <div className="flex space-x-2">
            <button onClick={handleSave} className="px-4 py-2 btn-primary rounded-md">{loading ? 'Guardando...' : 'Guardar'}</button>
          </div>

          <div className="mt-4">
            <p>Puntos: {points ?? '—'}</p>
          </div>
        </div>
      ) : (
        <p>No se encontró perfil.</p>
      )}
    </div>
  )
}
