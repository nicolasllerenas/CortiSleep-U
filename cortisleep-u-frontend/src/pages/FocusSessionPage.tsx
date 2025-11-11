import { useEffect, useState } from 'react'
import checkinService from '../services/focus'

export default function FocusSessionPage() {
    const [sessions, setSessions] = useState<any[]>([])
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<string | null>(null)
    
    useEffect(() => {
        setLoading(true)
        checkinService.getMySessions()
            .then((res) => {
                setSessions(res)
            })
            .catch((err) => setError(String(err?.body?.message || err?.message || err)))
            .finally(() => setLoading(false))
    }, [])

    if (loading) return <p>Cargando mis sesiones de enfoque...</p>
    if (error) return <p className="text-sm text-red-600">{error}</p>

    return (
        <div className="text-left space-y-4">
            <h1 className="text-2xl font-bold">Mis Sesiones de Enfoque</h1>
            <ul className="space-y-2">
                {sessions.map((session) => (
                    <li key={session.id} className="border p-4 rounded-md">
                        <h3 className="text-lg font-medium">Sesión del {new Date(session.date).toLocaleDateString()}</h3>
                        <p className="text-sm text-gray-600">Duración: {session.duration} minutos</p>
                        <p className="text-sm text-gray-600">Puntos obtenidos: {session.pointsEarned}</p>
                    </li>
                ))}
            </ul>
        </div>
    )   
}