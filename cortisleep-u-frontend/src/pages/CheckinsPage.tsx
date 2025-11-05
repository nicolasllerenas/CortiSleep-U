import { useEffect, useState } from 'react'
import checkinService from '../services/checkin'

export default function CheckinsPage() {
  const [checkins, setCheckins] = useState<any[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [date, setDate] = useState('')
  const [notes, setNotes] = useState('')

  useEffect(() => {
    setLoading(true)
    checkinService.getMyCheckIns()
      .then((res) => {
        // backend may return a Page or a list
        const rows = Array.isArray(res) ? res : (res?.content ?? [])
        setCheckins(rows)
      })
      .catch((err) => setError(String(err?.body?.message || err?.message || err)))
      .finally(() => setLoading(false))
  }, [])

  async function handleCreate() {
    setError(null)
    setLoading(true)
    try {
      const payload = { date: date || new Date().toISOString().slice(0, 10), notes }
      await checkinService.createCheckIn(payload)
      // try to refresh list
      const list = await checkinService.getMyCheckIns()
      const rows = Array.isArray(list) ? list : (list?.content ?? [])
      setCheckins(rows)
      setDate('')
      setNotes('')
    } catch (err: any) {
      setError(String(err?.body?.message || err?.message || err))
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="text-left space-y-4">
      <h2 className="text-xl font-semibold">Mis Check-ins</h2>
      {error && <p className="text-sm text-red-600">{error}</p>}

      <div className="space-y-2">
        <div>
          <label className="block text-sm font-medium text-gray-700">Fecha (YYYY-MM-DD)</label>
          <input value={date} onChange={(e) => setDate(e.target.value)} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Notas</label>
          <input value={notes} onChange={(e) => setNotes(e.target.value)} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
        </div>
        <div className="flex space-x-2">
          <button onClick={handleCreate} className="px-4 py-2 btn-primary rounded-md">{loading ? 'Enviando...' : 'Crear Check-in'}</button>
        </div>
      </div>

      <div className="mt-4">
        <h3 className="font-medium">Listado</h3>
        {loading && <p>Cargando...</p>}
        {!loading && checkins.length === 0 && <p>No hay check-ins.</p>}
        <ul className="space-y-2">
          {checkins.map((c: any) => (
            <li key={c.id ?? JSON.stringify(c)} className="p-3 border rounded-md">
              <div className="text-sm">Fecha: {c.date ?? c.createdAt ?? '—'}</div>
              <div className="text-sm">Notas: {c.notes ?? c.description ?? '—'}</div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  )
}
