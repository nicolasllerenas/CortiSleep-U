import { useState } from 'react'
import focusSessionService from '../services/focus'

export default function FocusStats() {
    const [stats, setStats] = useState<any>(null)
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<string | null>(null)
    
    const fetchStats = () => {   
        setLoading(true)
        focusSessionService.getStats()
            .then((res) => {        
                setStats(res)
            })
            .catch((err) => setError(String(err?.body?.message || err?.message || err)))
            .finally(() => setLoading(false))
    }

    return (
        <div className="text-left space-y-4">
            <h1 className="text-2xl font-bold">Estadísticas de Sesiones de Enfoque</h1>
            <button 
                onClick={fetchStats} 
                className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600"
            > </button>

                Cargar Estadísticas
            {loading && <p>Cargando estadísticas de sesiones de enfoque...</p>}
            {error && <p className="text-sm text-red-600">{error}</p>}
            {stats && (
                <div className="border p-4 rounded-md">
                    <p className="text-sm text-gray-600">Total de Sesiones: {stats.totalSessions}</p>
                    <p className="text-sm text-gray-600">Duración Total: {stats.totalDuration} minutos</p>
                    <p className="text-sm text-gray-600">Puntos Totales Obtenidos: {stats.totalPoints}</p>
                </div>
            )}
        </div>
    )   
}       

