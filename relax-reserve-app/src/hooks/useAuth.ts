import { useState, useEffect, useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { login as apiLogin, logout as apiLogout, register as apiRegister } from '../services/api/auth';

const useAuth = () => {
    const { setUser, setIsAuthenticated } = useContext(AuthContext);
    const [loading, setLoading] = useState(true);

    const login = async (email, password) => {
        try {
            const user = await apiLogin(email, password);
            setUser(user);
            setIsAuthenticated(true);
        } catch (error) {
            throw new Error('Login failed');
        }
    };

    const register = async (email, password) => {
        try {
            const user = await apiRegister(email, password);
            setUser(user);
            setIsAuthenticated(true);
        } catch (error) {
            throw new Error('Registration failed');
        }
    };

    const logout = async () => {
        try {
            await apiLogout();
            setUser(null);
            setIsAuthenticated(false);
        } catch (error) {
            throw new Error('Logout failed');
        }
    };

    useEffect(() => {
        // Check if user is already authenticated on mount
        const checkAuth = async () => {
            // Logic to check authentication (e.g., token validation)
            setLoading(false);
        };

        checkAuth();
    }, []);

    return { login, register, logout, loading };
};

export default useAuth;