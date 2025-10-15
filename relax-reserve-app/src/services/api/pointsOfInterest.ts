import apiClient from '../../utils/apiClient';

export const fetchPointsOfInterest = async () => {
    const response = await apiClient.get('/points-of-interest');
    return response.data;
};

export const createPointOfInterest = async (poiData) => {
    const response = await apiClient.post('/points-of-interest', poiData);
    return response.data;
};

export const updatePointOfInterest = async (id, poiData) => {
    const response = await apiClient.put(`/points-of-interest/${id}`, poiData);
    return response.data;
};

export const deletePointOfInterest = async (id) => {
    const response = await apiClient.delete(`/points-of-interest/${id}`);
    return response.data;
};