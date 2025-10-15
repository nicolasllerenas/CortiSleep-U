import React, { useEffect, useState } from 'react';
import ActivityCard from '../components/ActivityCard';
import { fetchActivities } from '../services/api/activities';

const Activities = () => {
    const [activities, setActivities] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const loadActivities = async () => {
            try {
                const data = await fetchActivities();
                setActivities(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        loadActivities();
    }, []);

    if (loading) {
        return <div>Loading activities...</div>;
    }

    if (error) {
        return <div>Error loading activities: {error}</div>;
    }

    return (
        <div>
            <h1>Available Activities</h1>
            <div className="activities-list">
                {activities.map(activity => (
                    <ActivityCard key={activity.id} activity={activity} />
                ))}
            </div>
        </div>
    );
};

export default Activities;