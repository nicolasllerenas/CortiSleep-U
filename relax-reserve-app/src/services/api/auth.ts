import axios from 'axios';

const API_URL = 'https://api.example.com/auth'; // Replace with your actual API URL

export const register = async (userData) => {
    const response = await axios.post(`${API_URL}/register`, userData);
    return response.data;
};

export const login = async (credentials) => {
    const response = await axios.post(`${API_URL}/login`, credentials);
    return response.data;
};

export const logout = async () => {
    const response = await axios.post(`${API_URL}/logout`);
    return response.data;
};

export const refresh = async () => {
    const response = await axios.get(`${API_URL}/refresh`);
    return response.data;
};

export const getUserData = async () => {
    const response = await axios.get(`${API_URL}/me`);
    return response.data;
};