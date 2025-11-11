import { useEffect, useState } from 'react'
import poiService from '../services/poi'

export default function NearbyPOI() {
    const [pois, setPois] = useState<any[]>([])
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<string | null>(null)
    
    // For demonstration, we use hardcoded coordinates. In a real app, this would come from user location.
    const userLocation = { latitude: 40.7128, longitude: -74.0060 }         
    useEffect(() => {
        setLoading(true)
        poiService.getNearbyPOIs(userLocation)
            .then((res) => {
                setPois(res)
            })
            .catch((err) => setError(String(err?.body?.message || err?.message || err)))
            .finally(() => setLoading(false))
    }, [])

    if (loading) return <p>Cargando puntos de interés cercanos...</p>
    if (error) return <p className="text-sm text-red-600">{error}</p>

    return (
        <div className="text-left space-y-4">
            <h2 className="text-xl font-semibold">Puntos de Interés Cercanos</h2>
            <ul className="space-y-2">
                {pois.map((poi) => (
                    <li key={poi.id} className="border p-4 rounded-md">
                        <h3 className="text-lg font-medium">{poi.name}</h3>             
                        <p className="text-sm text-gray-600">{poi.description}</p>
                    </li>
                ))}
            </ul>
        </div>
    )
}
