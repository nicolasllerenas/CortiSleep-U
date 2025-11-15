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
      .then((res) => {
        // Defensive: API may return either an array or an object with `data`.
        const payload = (res as any)
        if (Array.isArray(payload)) {
          setEntries(payload)
        } else if (Array.isArray(payload?.data)) {
          setEntries(payload.data)
        } else {
          // Unexpected shape: coerce to empty array and surface a warning in console
          // so the UI doesn't crash. The error state is optional for dev.
          // eslint-disable-next-line no-console
          console.warn('ScreenTime: unexpected response shape', payload)
          setEntries([])
        }
      })
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
