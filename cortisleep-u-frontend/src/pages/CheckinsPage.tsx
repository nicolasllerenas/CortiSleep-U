import { useEffect, useState } from 'react'
import checkinService from '../services/checkin'
import sleepService from '../services/sleep'
import type { CheckInResponse, SleepEntryResponse } from '../types'

export default function CheckinsPage() {
  const [checkins, setCheckins] = useState<CheckInResponse[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [notes, setNotes] = useState('')
  const [duplicateAlert, setDuplicateAlert] = useState<string | null>(null)
  const [stressLevel, setStressLevel] = useState<number | ''>('')
  

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

  // fetch today's sleep summary for quick glance
  const [todaySleep, setTodaySleep] = useState<SleepEntryResponse | null>(null)
  async function refreshTodaySleep() {
    try {
      const entries = await sleepService.getMySleepEntries()
      const rows = Array.isArray(entries) ? entries : []
      const today = new Date().toISOString().slice(0, 10) // YYYY-MM-DD
      const found = rows.find((e: any) => String(e?.sleepAt ?? e?.createdAt ?? '').slice(0, 10) === today)
      setTodaySleep((found ?? null) as SleepEntryResponse | null)
    } catch (e) {
      // ignore
      setTodaySleep(null)
    }
  }
  useEffect(() => { refreshTodaySleep() }, [])

  async function handleCreate() {
    setError(null)
    setLoading(true)
    try {
      // backend derives date automatically (server uses checkInTime/now).
      // Ask server if today's checkin exists (more robust than client-side date guessing).
      setDuplicateAlert(null)
      const todayCheck = await checkinService.getTodayCheckIn()
      if (todayCheck) {
        setDuplicateAlert(todayCheck as any)
        setLoading(false)
        return
      }

      const payload: any = { notes }
  if (stressLevel !== '') payload.stressLevel = Number(stressLevel)
      await checkinService.createCheckIn(payload)
  await refreshTodaySleep()
      // try to refresh list
      const list = await checkinService.getMyCheckIns()
      const rows = Array.isArray(list) ? list : (list?.content ?? [])
      setCheckins(rows)
      setNotes('')
      setStressLevel('')
      
    } catch (err: any) {
      setError(String(err?.body?.message || err?.message || err))
    } finally {
      setLoading(false)
    }
  }

  // quick-create sleep modal
  const [showSleepModal, setShowSleepModal] = useState(false)
  const [sleepAt, setSleepAt] = useState('')
  const [wakeAt, setWakeAt] = useState('')
  const [sleepLoading, setSleepLoading] = useState(false)
  const [sleepError, setSleepError] = useState<string | null>(null)

  async function createSleepQuick() {
    setSleepError(null)
    setSleepLoading(true)
    try {
      if (!sleepAt || !wakeAt) throw new Error('Sleep and wake times are required')
      const payload = { sleepAt: new Date(sleepAt).toISOString(), wakeAt: new Date(wakeAt).toISOString() }
      await sleepService.createSleepEntry(payload)
      setShowSleepModal(false)
      setSleepAt('')
      setWakeAt('')
      await refreshTodaySleep()
      const list = await checkinService.getMyCheckIns()
      const rows = Array.isArray(list) ? list : (list?.content ?? [])
      setCheckins(rows)
    } catch (err: any) {
      setSleepError(String(err?.body?.message || err?.message || err))
    } finally { setSleepLoading(false) }
  }

  // edit flow
  const [editing, setEditing] = useState<boolean>(false)
  const [editingCheckin, setEditingCheckin] = useState<CheckInResponse | null>(null)

  function openEdit(checkin: any) {
    setEditing(true)
    setEditingCheckin(checkin)
    setNotes(checkin.notes ?? '')
    setStressLevel(checkin.stressLevel ?? checkin.stress_level ?? '')
    
    setDuplicateAlert(null)
  }

  async function saveEdit() {
    if (!editingCheckin?.id) return
    setLoading(true)
    try {
      const payload: any = { notes }
      if (stressLevel !== '') payload.stressLevel = Number(stressLevel)
      
      await checkinService.updateCheckIn(editingCheckin.id as number, payload)
      const list = await checkinService.getMyCheckIns()
      const rows = Array.isArray(list) ? list : (list?.content ?? [])
      setCheckins(rows)
      setEditing(false)
      setEditingCheckin(null)
      setNotes('')
      setStressLevel('')
      
    } catch (err: any) {
      setError(String(err?.body?.message || err?.message || err))
    } finally { setLoading(false) }
  }

  return (
    <div className="text-left space-y-4">
      {/* Today's sleep summary + quick-create CTA */}
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-xl font-semibold">Mis Check-ins</h2>
          {todaySleep ? (
            <p className="text-sm text-gray-700">Sueño de hoy: {todaySleep.durationMinutes ?? '—'} minutos {todaySleep.sleepAt ? `(desde ${new Date(todaySleep.sleepAt).toLocaleTimeString()})` : ''}</p>
          ) : (
            <p className="text-sm text-gray-500">No hay registro de sueño para hoy.</p>
          )}
        </div>
        <div>
          <button onClick={() => setShowSleepModal(true)} className="px-3 py-2 btn-primary rounded-md">Añadir sueño</button>
        </div>
      </div>
      <h2 className="text-xl font-semibold">Mis Check-ins</h2>
      {error && <p className="text-sm text-red-600">{error}</p>}

      <div className="space-y-2">
        {duplicateAlert && (
          <div className="p-3 bg-yellow-100 border border-yellow-300 text-yellow-800 rounded-md flex items-center justify-between">
            <div>
              {typeof duplicateAlert === 'string' ? duplicateAlert : 'Ya existe un check-in para hoy.'}
            </div>
            {typeof duplicateAlert === 'object' && (
              <div className="space-x-2">
                <button onClick={() => openEdit(duplicateAlert)} className="px-3 py-1 bg-blue-600 text-white rounded-md text-sm">Editar</button>
                <button onClick={() => setDuplicateAlert(null)} className="px-3 py-1 bg-gray-200 text-gray-800 rounded-md text-sm">Cerrar</button>
              </div>
            )}
          </div>
        )}
        <div>
          <label className="block text-sm font-medium text-gray-700">Notas</label>
          <input value={notes} onChange={(e) => setNotes(e.target.value)} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Nivel de estrés (1-10)</label>
          <input
            type="number"
            min={1}
            max={10}
            value={stressLevel as any}
            onChange={(e) => {
              const v = e.target.value
              setStressLevel(v === '' ? '' : Math.max(1, Math.min(10, Number(v))))
            }}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2"
            placeholder="Opcional"
          />
        </div>
        {/* sleepHours removed: checkins no longer track sleep */}
        <div className="flex space-x-2">
          {!editing ? (
            <button onClick={handleCreate} className="px-4 py-2 btn-primary rounded-md">{loading ? 'Enviando...' : 'Crear Check-in'}</button>
          ) : (
            <>
              <button onClick={saveEdit} className="px-4 py-2 btn-primary rounded-md">{loading ? 'Guardando...' : 'Guardar cambios'}</button>
              <button onClick={() => { setEditing(false); setEditingCheckin(null); setDuplicateAlert(null); setNotes(''); setStressLevel(''); }} className="px-4 py-2 bg-gray-200 rounded-md">Cancelar</button>
            </>
          )}
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
              <div className="text-sm">Stress: {c.stressLevel ?? c.stress_level ?? '—'}</div>
              {/* sleepHours removed from checkins */}
            </li>
          ))}
        </ul>
      </div>

      {/* Sleep quick-create modal */}
      {showSleepModal && (
        <div role="dialog" aria-modal="true" aria-label="Crear registro de sueño" className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 p-4">
          <div className="bg-white rounded-lg p-4 w-full max-w-md">
            <h3 className="font-semibold mb-2">Registrar sueño</h3>
            {sleepError && <p role="alert" className="text-sm text-red-600">{sleepError}</p>}
            <div className="space-y-2">
              <label className="block text-sm font-medium">Hora de dormir</label>
              <input aria-label="Hora de dormir" type="datetime-local" value={sleepAt} onChange={(e) => setSleepAt(e.target.value)} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
              <label className="block text-sm font-medium">Hora de despertar</label>
              <input aria-label="Hora de despertar" type="datetime-local" value={wakeAt} onChange={(e) => setWakeAt(e.target.value)} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm p-2" />
            </div>
            <div className="flex justify-end gap-2 mt-4">
              <button onClick={() => setShowSleepModal(false)} className="px-3 py-2 bg-gray-200 rounded">Cancelar</button>
              <button onClick={createSleepQuick} disabled={sleepLoading} className="px-3 py-2 btn-primary rounded">{sleepLoading ? 'Enviando...' : 'Guardar'}</button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
