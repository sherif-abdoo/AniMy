import { jwtDecode } from 'jwt-decode';


export const useAuth = () => {
    const accessToken = localStorage.getItem('accessToken');
    const isLoggedIn = !!accessToken;

    let user = null;

    if (accessToken) {
        try {
            user = jwtDecode(accessToken);
        } catch (e) {
            console.error('Failed to decode token:', e);
        }
    }

    return {
        accessToken,
        isLoggedIn,
        user,
    };
};
