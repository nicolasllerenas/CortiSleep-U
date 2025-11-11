import { useEffect, useState } from 'react'
import { getMyScreenTime } from '../services/screentime'
import type { ScreenTimeEntry } from '../types'

export default function ScreenTimePage() {
  const [entries, setEntries] = useState<ScreenTimeEntry[] | null>(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    setLoading(true)
    getMyScreenTime()
      .then((res) => setEntries(res as ScreenTimeEntry[]))
      .catch((err) => setError(String(err?.body?.message || err?.message || err)))
      .finally(() => setLoading(false))
  }, [])

  if (loading) return <div>Cargando screen time...</div>
  if (error) return <div>Error: {error}</div>

  return (
    <div>
      <h2 className="text-xl font-bold">Screen Time (últimos registros)</h2>
      {!entries || entries.length === 0 ? (
        <p>No hay registros.</p>
      ) : (
        <ul>
          {entries.map((e: any) => (
            <li key={e.id || e.date} className="mb-2">
              <strong>{e.date}</strong>: {e.totalMinutes ?? e.total_minutes ?? '—'} minutos
            </li>
          ))}
        </ul>
      )}
    </div>
  )
}
