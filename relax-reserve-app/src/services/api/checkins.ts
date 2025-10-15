import apiClient from '../../utils/apiClient';

export const fetchCheckIns = async (userId) => {
    const response = await apiClient.get(`/checkins/${userId}`);
    return response.data;
};

export const createCheckIn = async (checkInData) => {
    const response = await apiClient.post('/checkins', checkInData);
    return response.data;
};

export const updateCheckIn = async (checkInId, checkInData) => {
    const response = await apiClient.put(`/checkins/${checkInId}`, checkInData);
    return response.data;
};

export const deleteCheckIn = async (checkInId) => {
    const response = await apiClient.delete(`/checkins/${checkInId}`);
    return response.data;
};