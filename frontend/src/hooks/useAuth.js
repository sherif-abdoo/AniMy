// src/hooks/useAuth.js
import { useState, useEffect } from 'react';
import { jwtDecode } from 'jwt-decode';
import {useNavigate} from "react-router-dom";

export const useAuth = () => {
    const [accessToken, setAccessToken] = useState(() => localStorage.getItem('accessToken'));
    const [user, setUser] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(!!accessToken);
    const navigate = useNavigate();
    useEffect(() => {
        if (accessToken) {
            try {
                const decoded = jwtDecode(accessToken);
                setUser(decoded);
                setIsLoggedIn(true);
            } catch (e) {
                console.error('Failed to decode token:', e);
                logout(); // if decoding failed, force logout
            }
        } else {
            setUser(null);
            setIsLoggedIn(false);
        }
    }, [accessToken]);

    const login = (token) => {
        localStorage.setItem('accessToken', token);
        setAccessToken(token);
    };

    const logout = () => {
        localStorage.removeItem('accessToken');
        setAccessToken(null);
        setUser(null);
        setIsLoggedIn(false);
        navigate('/');
    };

    return {
        accessToken,
        user,
        isLoggedIn,
        login,
        logout,
    };
};
