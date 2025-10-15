import React, { useState, useEffect } from 'react';
import { fetchFocusSessions, createFocusSession } from '../services/api/focusSessions';

const FocusSession: React.FC = () => {
    const [sessions, setSessions] = useState([]);
    const [newSession, setNewSession] = useState({ title: '', duration: 0 });

    useEffect(() => {
        const loadSessions = async () => {
            const fetchedSessions = await fetchFocusSessions();
            setSessions(fetchedSessions);
        };
        loadSessions();
    }, []);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setNewSession({ ...newSession, [name]: value });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        await createFocusSession(newSession);
        setNewSession({ title: '', duration: 0 });
        const fetchedSessions = await fetchFocusSessions();
        setSessions(fetchedSessions);
    };

    return (
        <div>
            <h1>Focus Sessions</h1>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="title"
                    value={newSession.title}
                    onChange={handleInputChange}
                    placeholder="Session Title"
                    required
                />
                <input
                    type="number"
                    name="duration"
                    value={newSession.duration}
                    onChange={handleInputChange}
                    placeholder="Duration (minutes)"
                    required
                />
                <button type="submit">Create Session</button>
            </form>
            <ul>
                {sessions.map((session) => (
                    <li key={session.id}>
                        {session.title} - {session.duration} minutes
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default FocusSession;