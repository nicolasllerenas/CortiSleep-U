import axios from 'axios';

const API_URL = '/api/focus-sessions';

export const fetchFocusSessions = async () => {
    const response = await axios.get(API_URL);
    return response.data;
};

export const createFocusSession = async (sessionData) => {
    const response = await axios.post(API_URL, sessionData);
    return response.data;
};

export const updateFocusSession = async (sessionId, sessionData) => {
    const response = await axios.put(`${API_URL}/${sessionId}`, sessionData);
    return response.data;
};

export const deleteFocusSession = async (sessionId) => {
    const response = await axios.delete(`${API_URL}/${sessionId}`);
    return response.data;
};