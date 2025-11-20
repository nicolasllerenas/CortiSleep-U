import { useEffect, useState } from 'react'
import type { SleepEntryRequest, SleepEntryResponse } from '../types'
import sleepService from '../services/sleep'

function formatLocalISO(value: string) {
  // convert datetime-local (no timezone) to ISO with timezone using local tz
  if (!value) return ''
  const date = new Date(value)
  return date.toISOString()
}

function groupByDay(entries: Array<any>) {
  const map: Record<string, number> = {}
  entries.forEach((e) => {
    const d = new Date(e.sleepAt)
    const key = d.toISOString().slice(0, 10)
    map[key] = (map[key] || 0) + (e.durationMinutes || 0)
  })
  return map
}

export default function SleepPage() {
  const [entries, setEntries] = useState<SleepEntryResponse[]>([])
  const [sleepAt, setSleepAt] = useState('')
  const [wakeAt, setWakeAt] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    loadEntries()
  }, [])

  async function loadEntries() {
    setLoading(true)
    try {
      const res = await sleepService.getMySleepEntries()
      setEntries(res)
    } catch (e: any) {
      setError(String(e?.message || e))
    } finally {
      setLoading(false)
    }
  }

  async function handleSave() {
    setError(null)
    setLoading(true)
    try {
      if (!sleepAt || !wakeAt) {
        throw new Error('Both sleep and wake times are required')
      }
      const sleepIso = formatLocalISO(sleepAt)
      const wakeIso = formatLocalISO(wakeAt)
      if (new Date(wakeIso) <= new Date(sleepIso)) {
        throw new Error('Wake time must be after sleep time')
      }
      const payload: SleepEntryRequest = { sleepAt: sleepIso, wakeAt: wakeIso }
      await sleepService.createSleepEntry(payload)
      setSleepAt('')
      setWakeAt('')
      await loadEntries()
    } catch (e: any) {
      setError(String(e?.message || e))
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="max-w-lg space-y-4">
      <h2 className="text-xl font-semibold">Registro de Sueño</h2>
      {error && <p className="text-sm text-red-600">{error}</p>}

      <div>
        <label className="block text-sm font-medium text-gray-700">Hora que dormiste</label>
        <input type="datetime-local" value={sleepAt} onChange={(e) => setSleepAt(e.target.value)} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700">Hora que despertaste</label>
        <input type="datetime-local" value={wakeAt} onChange={(e) => setWakeAt(e.target.value)} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
      </div>

      <div className="flex space-x-2">
        <button onClick={handleSave} className="px-4 py-2 btn-primary rounded-md">{loading ? 'Guardando...' : 'Guardar'}</button>
      </div>

      <div>
        <h3 className="text-lg font-medium">Entradas recientes</h3>
        {entries.length === 0 ? (
          <p className="text-sm text-gray-600">No hay registros.</p>
        ) : (
          <>
            <div className="mt-2">
              {/* Simple 7-day bar chart */}
              <SmallSleepChart entries={entries} />
            </div>
            <ul className="space-y-2 mt-2">
              {entries.map((e) => (
                <li key={e.id} className="p-2 border rounded-md">
                  <div className="text-sm">Dormí: {e.sleepAt ? String(new Date(e.sleepAt).toLocaleString()) : '—'}</div>
                  <div className="text-sm">Desperté: {e.wakeAt ? String(new Date(e.wakeAt).toLocaleString()) : '—'}</div>
                  <div className="text-sm">Duración: {e.durationMinutes ?? '—'} min</div>
                </li>
              ))}
            </ul>
          </>
        )}
      </div>
    </div>
  )
}

function SmallSleepChart({ entries }: { entries: any[] }) {
  // group last 7 days
  const days: string[] = []
  const now = new Date()
  for (let i = 6; i >= 0; i--) {
    const d = new Date(now)
    d.setDate(now.getDate() - i)
    days.push(d.toISOString().slice(0, 10))
  }
  const grouped = groupByDay(entries)
  const values = days.map((day) => grouped[day] || 0)
  const max = Math.max(...values, 60)

  return (
    <div>
      <svg width="100%" height={80} viewBox={`0 0 ${days.length * 30} 80`}>
        {values.map((v, i) => {
          const x = i * 30 + 4
          const h = Math.round((v / max) * 60)
          return (
            <g key={i}>
              <rect x={x} y={70 - h} width={20} height={h} fill="#60A5FA" rx={3} />
              <text x={x + 10} y={78} fontSize={8} textAnchor="middle">{days[i].slice(5)}</text>
            </g>
          )
        })}
      </svg>
    </div>
  )
}
