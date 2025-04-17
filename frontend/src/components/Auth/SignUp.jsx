import AuthForm from './AuthForm';
import { useNavigate } from 'react-router-dom';


const SignUp = ({ setPopup }) => {
    const navigate = useNavigate();
    const handleSignup = async (form) => {
        try {
            const res = await fetch('http://localhost:8080/api/public/registration', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(form),
            });

            const data = await res.json();
            setPopup({
                show: true,
                status: res.ok ? 'success' : 'error',
                message: data.message || 'Something went wrong',
                data: data.data || '',
                buttonText: res.ok ? 'Go to Login' : null,
                onButtonClick: () => {
                    setPopup({ show: false });
                    navigate('/login');
                }
            });

        } catch (err) {
            setPopup({
                show: true,
                status: 'error',
                message: 'Something went wrong',
                data: 'Could not connect to server. Try again later', // e.g. "Email already exists"
            });
        }
    };

    return <AuthForm type="signup" onSubmit={handleSignup} setPopup={setPopup} />;
};

export default SignUp;
