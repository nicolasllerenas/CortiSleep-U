import { useEffect, useState } from 'react'
import poiService from '../services/poi'

export default function MyVisits() {
    const [visits, setVisits] = useState<any[]>([])
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<string | null>(null)
        
    useEffect(() => {
        setLoading(true)
        poiService.getMyVisits()
            .then((res) => {
                setVisits(res)
            })
            .catch((err) => setError(String(err?.body?.message || err?.message || err)))
            .finally(() => setLoading(false))
    }, [])

    if (loading) return <p>Cargando mis visitas a puntos de interés...</p>
    if (error) return <p className="text-sm text-red-600">{error}</p>

    return (
        <div className="text-left space-y-4">
            <h2 className="text-xl font-semibold">Mis Visitas a Puntos de Interés</h2>
            <ul className="space-y-2">
                {visits.map((visit) => (
                    <li key={visit.id} className="border p-4 rounded-md">
                        <h3 className="text-lg font-medium">{visit.poiName}</h3>        
                        <p className="text-sm text-gray-600">Fecha de visita: {new Date(visit.date).toLocaleDateString()}</p>
                    </li>
                ))}
            </ul>
        </div>
    )
}