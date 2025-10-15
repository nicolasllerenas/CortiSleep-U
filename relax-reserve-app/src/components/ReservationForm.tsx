import React, { useState } from 'react';

const ReservationForm = () => {
    const [activity, setActivity] = useState('');
    const [date, setDate] = useState('');
    const [time, setTime] = useState('');
    const [participants, setParticipants] = useState(1);

    const handleSubmit = (e) => {
        e.preventDefault();
        // Handle reservation submission logic here
        console.log({ activity, date, time, participants });
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label htmlFor="activity">Activity:</label>
                <input
                    type="text"
                    id="activity"
                    value={activity}
                    onChange={(e) => setActivity(e.target.value)}
                    required
                />
            </div>
            <div>
                <label htmlFor="date">Date:</label>
                <input
                    type="date"
                    id="date"
                    value={date}
                    onChange={(e) => setDate(e.target.value)}
                    required
                />
            </div>
            <div>
                <label htmlFor="time">Time:</label>
                <input
                    type="time"
                    id="time"
                    value={time}
                    onChange={(e) => setTime(e.target.value)}
                    required
                />
            </div>
            <div>
                <label htmlFor="participants">Participants:</label>
                <input
                    type="number"
                    id="participants"
                    value={participants}
                    onChange={(e) => setParticipants(e.target.value)}
                    min="1"
                    required
                />
            </div>
            <button type="submit">Reserve</button>
        </form>
    );
};

export default ReservationForm;