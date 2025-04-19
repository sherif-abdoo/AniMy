import AuthForm from './AuthForm';

const SignIn = ({ setPopup }) => {
    const handleLogin = async (form) => {
        try {
            const res = await fetch('http://localhost:8080/api/public/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({
                    username: form.username,
                    password: form.password
                }),
                credentials: 'include'
            });
            console.log(res);
            const json = await res.json();

            if (res.ok) {
                const { accessToken, refreshToken } = json.data;

                localStorage.setItem('accessToken', accessToken);
                localStorage.setItem('refreshToken', refreshToken);

                setPopup({
                    show: true,
                    status: 'ana 48al ya bro',
                    message: 'Login successful',
                    data: 'gammeed'
                });
            } else {
                setPopup({
                    show: true,
                    status: 'error',
                    message: 'Login failed',
                    data: 'Invalid username or password'
                });
            }
        } catch (err) {
            setPopup({
                show: true,
                status: 'error',
                message: 'Server error',
                data: '' });
        }
    };

    return <AuthForm type="login" onSubmit={handleLogin} setPopup={setPopup} />;
};

export default SignIn;
