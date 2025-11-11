
import { useEffect, useState } from 'react'
import poiService from '../services/poi'
import type { POIResponse } from '../types'

export default function POIPage() {
  const [pois, setPois] = useState<POIResponse[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    setLoading(true)
    poiService
      .listPOIs()
      .then((res) => setPois(res || []))
      .catch((err) => setError(String(err?.body?.message || err?.message || err)))
      .finally(() => setLoading(false))
  }, [])

  if (loading) return <div>Cargando POIs...</div>
  if (error) return <div>Error: {error}</div>

  return (
    <div>
      <h2 className="text-xl font-bold">Points of Interest</h2>
      {pois.length === 0 ? (
        <p>No hay POIs disponibles.</p>
      ) : (
        <ul>
          {pois.map((p) => (
            <li key={p.id} className="mb-3">
              <strong>{p.name}</strong>
              <div>{p.description}</div>
              <div className="text-sm text-gray-600">{p.university}</div>
            </li>
          ))}
        </ul>
      )}
    </div>
  )
}


