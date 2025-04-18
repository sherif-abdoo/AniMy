import AuthForm from './AuthForm';

const SignIn = ({ setPopup }) => {
    const handleLogin = async (form) => {
        try {
            const res = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({
                    username: form.username,
                    password: form.password
                }),
                credentials: 'include'
            });

            if (res.ok) {
                window.location.href = '/dashboard';
            } else {
                setPopup({
                    show: true,
                    status: 'error',
                    message: 'Login failed',
                    data: 'Invalid username or password'
                });
            }
        } catch (err) {
            setPopup({ show: true, status: 'error', message: 'Server error', data: '' });
        }
    };

    return <AuthForm type="login" onSubmit={handleLogin} setPopup={setPopup} />;
};

export default SignIn;
