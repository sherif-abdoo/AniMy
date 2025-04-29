import { authFetch } from "./AuthFetch";

export const refreshToken = async () => {
    try {
        const res = await authFetch('http://localhost:8080/api/public/refresh', {
            method: 'POST',
        });

        const data = await res.json();
        if (data.status === "success") {
            const newAccessToken = data.data.accessToken;
            localStorage.setItem('accessToken', newAccessToken);
        } else {
            throw new Error("Failed to refresh token");
        }
    } catch (err) {
        console.error("Refresh token error:", err);
        throw err;
    }
};
