import { useState } from 'react'
import focusSessionService from '../services/focus'

export default function FocusSessionHistory() {
    const [sessions, setSessions] = useState<any[]>([])
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<string | null>(null)
    
    const fetchSessions = () => {   
        setLoading(true)
        focusSessionService.getMySessions()
            .then((res) => {        
                setSessions(res)
            })
            .catch((err) => setError(String(err?.body?.message || err?.message || err)))
            .finally(() => setLoading(false))
    }

    return (
        <div className="text-left space-y-4">
            <h1 className="text-2xl font-bold">Historial de Sesiones de Enfoque</h1>
            <button 
                onClick={fetchSessions} 
                className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600"
            >
                Cargar Sesiones
            </button>
            {loading && <p>Cargando mis sesiones de enfoque...</p>}
            {error && <p className="text-sm text-red-600">{error}</p>}
            <ul className="space-y-2">
                {sessions.map((session) => (
                    <li key={session.id} className="border p-4 rounded-md">
                        <   h3 className="text-lg font-medium">Sesión del {new Date(session.date).toLocaleDateString()}</h3>
                        <p className="text-sm text-gray-600">Duración: {session.duration} minutos</p>
                        <p className="text-sm text-gray-600">Puntos obtenidos: {session.pointsEarned}</p>
                    </li>
                ))}
            </ul>
        </div>
    )   
}
