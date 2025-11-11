import { useEffect, useState } from 'react'
import poiService from '../services/poi'

export default function POIList() {
    const [pois, setPois] = useState<any[]>([])
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<string | null>(null)
        
    useEffect(() => {
        setLoading(true)
        poiService.listPOIs()
            .then((res) => {
                setPois(res)
            })
            .catch((err) => setError(String(err?.body?.message || err?.message || err)))
            .finally(() => setLoading(false))
    }, [])

    if (loading) return <p>Cargando puntos de interés...</p>
    if (error) return <p className="text-sm text-red-600">{error}</p>

    return (
        <div className="text-left space-y-4">
            <h2 className="text-xl font-semibold">Puntos de Interés</h2>
            <ul className="space-y-2">
                {pois.map((poi) => (
                    <li key={poi.id} className="border p-4 rounded-md">
                        <h3 className="text-lg font-medium">{poi.name}</h3>
                        <p className="text  -sm text-gray-600">{poi.description}</p>
                    </li>
                ))}
            </ul>
        </div>
    )
}
