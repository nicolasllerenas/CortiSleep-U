import React, { useState } from 'react';

const CheckIn: React.FC = () => {
    const [activityId, setActivityId] = useState('');
    const [message, setMessage] = useState('');

    const handleCheckIn = async () => {
        try {
            const response = await fetch(`/api/checkins`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ activityId }),
            });

            if (response.ok) {
                setMessage('Check-in successful!');
            } else {
                setMessage('Check-in failed. Please try again.');
            }
        } catch (error) {
            setMessage('An error occurred. Please try again later.');
        }
    };

    return (
        <div>
            <h1>Check In</h1>
            <input
                type="text"
                placeholder="Enter Activity ID"
                value={activityId}
                onChange={(e) => setActivityId(e.target.value)}
            />
            <button onClick={handleCheckIn}>Check In</button>
            {message && <p>{message}</p>}
        </div>
    );
};

export default CheckIn;