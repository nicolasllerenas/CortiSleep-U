import React, { useEffect, useState } from 'react';
import { fetchRewards, fetchUserPoints } from '../services/api/rewards';

const Rewards: React.FC = () => {
    const [rewards, setRewards] = useState([]);
    const [points, setPoints] = useState(0);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const loadRewards = async () => {
            try {
                const rewardsData = await fetchRewards();
                const userPoints = await fetchUserPoints();
                setRewards(rewardsData);
                setPoints(userPoints);
            } catch (error) {
                console.error('Error fetching rewards:', error);
            } finally {
                setLoading(false);
            }
        };

        loadRewards();
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h1>Your Rewards</h1>
            <p>Points Balance: {points}</p>
            <ul>
                {rewards.map((reward) => (
                    <li key={reward.id}>
                        {reward.name} - {reward.cost} points
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Rewards;