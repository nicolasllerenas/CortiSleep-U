import { useEffect, useState } from 'react'
import poiService from '../services/gamification/rewards'

export default function RewardsPage() {
    const [rewards, setRewards] = useState<any[]>([])
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<string | null>(null)
    
    useEffect(() => {
        setLoading(true)
        poiService.listRewards()
            .then((res) => {
                setRewards(res)
            })
            .catch((err) => setError(String(err?.body?.message || err?.message || err)))
            .finally(() => setLoading(false))
    }, [])
    
    if (loading) return <p>Cargando recompensas...</p>
    if (error) return <p className="text-sm text-red-600">{error}</p>
    return (
        <div className="text-left space-y-4">
            <h1 className="text-2xl font-bold">Recompensas</h1>
            <ul className="space-y-2">
                {rewards.map((reward) => (
                    <li key={reward.id} className="border p-4 rounded-md">
                        <h3 className="text-lg font-medium">{reward.title}</h3>
                        <p className="text-sm text-gray-600">{reward.description}</p>
                        <p className="text-sm text-green-600">Puntos necesarios: {reward.pointsRequired}</p>
                    </li>
                ))}
            </ul>
        </div>
    )
}
