import { useEffect, useState } from 'react'
import SensesService from '../services/senses'

export default function ReproducerWithTrack() {
    const [contents, setContents] = useState<any[]>([])
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<string | null>(null)
        
    useEffect(() => {
        setLoading(true)
        SensesService.getSenseByType('reproducerWithTrack')
            .then((res) => {
                setContents(res)
            })
            .catch((err) => setError(String(err?.body?.message || err?.message || err)))
            .finally(() => setLoading(false))
    }, [])
    
    if (loading) return <p>Cargando reproductores con pista...</p>
    if (error) return <p className="text-sm text-red-600">{error}</p>       
    return (

        <div className="text-left space-y-4">   
            <h2 className="text-xl font-semibold">Reproductores con Pista</h2>
            <ul className="space-y-2">       
                {contents.map((content) => (
                    <li key={content.id} className="border p-4 rounded-md">
                        <h3 className="text-lg font-medium">{content.title}</h3>
                        <p className="text-sm text-gray-600">{content.description}</p>
                        <audio controls src={content.audioUrl} className="w-full mt-2">
                            Your browser does not support the audio element.
                        </audio>
                    </li>
                ))}
            </ul>
        </div>
    )
}   