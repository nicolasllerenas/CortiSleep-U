import React, { useEffect, useState } from 'react';
import { fetchScreenTimeData } from '../services/api/screenTime';

const ScreenTime: React.FC = () => {
    const [screenTime, setScreenTime] = useState<number>(0);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const getScreenTime = async () => {
            try {
                const data = await fetchScreenTimeData();
                setScreenTime(data);
            } catch (error) {
                console.error('Error fetching screen time data:', error);
            } finally {
                setLoading(false);
            }
        };

        getScreenTime();
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h1>Screen Time Usage</h1>
            <p>Your total screen time is: {screenTime} minutes</p>
        </div>
    );
};

export default ScreenTime;