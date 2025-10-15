import apiClient from '../utils/apiClient';

export const fetchScreenTimeRecords = async () => {
    const response = await apiClient.get('/screen-time');
    return response.data;
};

export const createScreenTimeRecord = async (record) => {
    const response = await apiClient.post('/screen-time', record);
    return response.data;
};

export const updateScreenTimeRecord = async (id, record) => {
    const response = await apiClient.put(`/screen-time/${id}`, record);
    return response.data;
};

export const deleteScreenTimeRecord = async (id) => {
    const response = await apiClient.delete(`/screen-time/${id}`);
    return response.data;
};