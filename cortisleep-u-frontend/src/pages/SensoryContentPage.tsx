import { useEffect, useState } from 'react'
import sensesService from '../services/senses'
import type { SensoryContent, UserSensoryPreference } from '../types'

export default function SensoryContentPage() {
  const [items, setItems] = useState<SensoryContent[]>([])
  const [favorites, setFavorites] = useState<UserSensoryPreference[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    setLoading(true)
    Promise.all([sensesService.listSenses(), sensesService.getMyFavorites()])
      .then(([list, favs]) => {
        // Defensive: only set arrays. If the response is wrapped or a string
        // (e.g. index.html served), coerce to empty array and warn.
        if (Array.isArray(list)) setItems(list)
        else if (Array.isArray((list as any)?.data)) setItems((list as any).data)
        else {
          // eslint-disable-next-line no-console
          console.warn('SensoryContent: unexpected list shape', list)
          setItems([])
        }

        if (Array.isArray(favs)) setFavorites(favs)
        else if (Array.isArray((favs as any)?.data)) setFavorites((favs as any).data)
        else {
          // eslint-disable-next-line no-console
          console.warn('SensoryContent: unexpected favorites shape', favs)
          setFavorites([])
        }
      })
      .catch((err) => setError(String(err?.body?.message || err?.message || err)))
      .finally(() => setLoading(false))
  }, [])

  if (loading) return <div>Cargando contenido sensorial...</div>
  if (error) return <div>Error: {error}</div>

  return (
    <div>
      <h2 className="text-xl font-bold">Contenido Sensorial</h2>
      <div className="text-sm text-gray-600 mb-2">Favoritos: {favorites.length}</div>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {items.map((c) => (
          <div key={c.id} className="border p-3 rounded">
            <img src={c.thumbnailUrl} alt={c.title} className="w-full h-32 object-cover mb-2" />
            <h3 className="font-semibold">{c.title}</h3>
            <p className="text-sm text-gray-600">{c.description}</p>
            <div className="text-xs text-gray-500">Vistas: {c.viewCount}</div>
          </div>
        ))}
      </div>
    </div>
  )
}
