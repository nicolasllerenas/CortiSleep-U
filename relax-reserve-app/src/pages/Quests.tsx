import React, { useEffect, useState } from 'react';
import { fetchQuests } from '../services/api/quests';
import QuestCard from '../components/QuestCard';

const Quests = () => {
    const [quests, setQuests] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const loadQuests = async () => {
            try {
                const data = await fetchQuests();
                setQuests(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        loadQuests();
    }, []);

    if (loading) {
        return <div>Loading quests...</div>;
    }

    if (error) {
        return <div>Error loading quests: {error}</div>;
    }

    return (
        <div>
            <h1>Available Quests</h1>
            <div>
                {quests.map((quest) => (
                    <QuestCard key={quest.id} quest={quest} />
                ))}
            </div>
        </div>
    );
};

export default Quests;