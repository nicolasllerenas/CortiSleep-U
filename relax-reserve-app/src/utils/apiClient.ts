import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'https://api.example.com', // Replace with your API base URL
    headers: {
        'Content-Type': 'application/json',
    },
});

// Interceptor to add authentication token to requests
apiClient.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token'); // Assuming token is stored in localStorage
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Interceptor to handle responses
apiClient.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        // Handle errors globally
        if (error.response && error.response.status === 401) {
            // Handle unauthorized access (e.g., redirect to login)
        }
        return Promise.reject(error);
    }
);

export default apiClient;