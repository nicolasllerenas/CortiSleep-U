import { useEffect, useState } from 'react'
import poiService from '../services/poi'

export default function POIDetail()  {
    const [poi, setPoi] = useState<any>(null)
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<string | null>(null)
    
    // For demonstration, we use a hardcoded POI ID. In a real app, this would come from route params.
    const poiId = '12345'
    useEffect(() => {
        setLoading(true)
        poiService.getPOI(poiId)
            .then((res) => {
                setPoi(res)
            })
            .catch((err) => setError(String(err?.body?.message || err?.message || err)))
            .finally(() => setLoading(false))
    }, [poiId])

    if (loading) return <p>Cargando detalles del punto de interés...</p>
    if (error) return <p className="text-sm text-red-600">{error}</p>
    if (!poi) return <p>No se encontró el punto de interés.</p>

    return (
        <div className="text-left space-y-4">
            <h2 className="text-xl font-semibold">{poi.name}</h2>
            <p className="text-sm text-gray-600">{poi.description}</p>
            <p className="text-sm text-gray-600">Ubicación: {poi.location}</p>
            <p className="text-sm text-gray-600">Visitas: {poi.visitCount}</p>
            <button className="px-4 py-2 btn-primary rounded-md">Visitar Punto de Interés</button>
        </div>
    )   
}