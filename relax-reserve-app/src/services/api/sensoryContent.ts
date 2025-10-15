import apiClient from '../utils/apiClient';

export const fetchSensoryContent = async () => {
    const response = await apiClient.get('/sensory-content');
    return response.data;
};

export const createSensoryContent = async (content) => {
    const response = await apiClient.post('/sensory-content', content);
    return response.data;
};

export const updateSensoryContent = async (id, content) => {
    const response = await apiClient.put(`/sensory-content/${id}`, content);
    return response.data;
};

export const deleteSensoryContent = async (id) => {
    const response = await apiClient.delete(`/sensory-content/${id}`);
    return response.data;
};