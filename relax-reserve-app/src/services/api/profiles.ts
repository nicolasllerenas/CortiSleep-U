import apiClient from '../../utils/apiClient';

export const fetchProfiles = async () => {
    const response = await apiClient.get('/profiles');
    return response.data;
};

export const fetchProfileById = async (id) => {
    const response = await apiClient.get(`/profiles/${id}`);
    return response.data;
};

export const createProfile = async (profileData) => {
    const response = await apiClient.post('/profiles', profileData);
    return response.data;
};

export const updateProfile = async (id, profileData) => {
    const response = await apiClient.put(`/profiles/${id}`, profileData);
    return response.data;
};

export const deleteProfile = async (id) => {
    const response = await apiClient.delete(`/profiles/${id}`);
    return response.data;
};