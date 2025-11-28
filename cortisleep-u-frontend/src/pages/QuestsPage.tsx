import { useEffect, useState } from 'react'
import questService from '../services/gamification/quests'

export default function QuestsPage() {
    const [quests, setQuests] = useState<any[]>([])
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<string | null>(null)
    
    useEffect(() => {
        setLoading(true)
        questService.listQuests()
            .then((res) => {
                // Defensive: ensure we always set an array so .map won't crash
                if (Array.isArray(res)) setQuests(res)
                else if (res && (res as any).data && Array.isArray((res as any).data)) setQuests((res as any).data)
                else if (res) setQuests([res])
                else setQuests([])
            })
            .catch((err) => setError(String(err?.body?.message || err?.message || err)))
            .finally(() => setLoading(false))
    }, [])

    if (loading) return <p>Cargando mis misiones...</p>
    if (error) return <p className="text-sm text-red-600">{error}</p>

    return (
        <div className="text-left space-y-4">
            <h1 className="text-2xl font-bold">Mis Misiones</h1>
            <ul className="space-y-2        ">
                {quests.map((quest) => (
                    <li key={quest.id} className="border p-4 rounded-md">
                        <h3 className="text-lg font-medium">{quest.title}</h3>
                        <p className="text-sm text-gray-600">{quest.description}</p>
                    </li>
                ))}
            </ul>
        </div>
    )   
}   
