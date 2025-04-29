export const authFetch = async (url , options = {} ,retry = true) =>{
    const accessToken = localStorage.getItem('accessToken');

    const res = await fetch(url, {
        ...options,
        headers: {
            ...options.headers,
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': options.headers?.['Content-Type'] || 'application/json',
        },
        credentials: 'include',
    });

    if(res.status === 401 && retry){
        const refreshToken = await fetch(`http://localhost:8080/api/public/refresh`, {
            method: "POST",
            credentials: 'include'
        })
        if(refreshToken.ok){
            const refreshData = await refreshToken.json();
            const newToken = refreshData.data?.accessToken;
            if(newToken){
                localStorage.setItem('accessToken',newToken);
                const updatedOptions = {
                    ...options,
                    headers: {
                        ...options.headers,
                        'Authorization': `Bearer ${newToken}`,
                        'Content-Type': options.headers?.['Content-Type'] || 'application/json',
                    },
                    credentials: 'include',
                };
                return authFetch(url, updatedOptions,false);
            }
        }
        throw new Error("Session expired, please log in again");
    }
    return res;
};