import React, {useState} from 'react';
import './SignUp.css';
import { useNavigate } from 'react-router-dom';

const SignUp = ({setPopup}) => {
    const[form , setForm] = useState({ username: '', email: '', password: '' })
    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };


    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const res = await fetch('http://localhost:8080/api/public/registration', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(form)

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

        }catch (err) {
            setPopup({
                show: true,
                status: 'error',
                message: 'Something went wrong',
                data: 'Could not connect to server. Try again later', // e.g. "Email already exists"
            });
        }
    };




  return (
      <div className="signup-container">
        <form className={ "signup-form"} onSubmit={handleSubmit}>
            <div className="signup-header">
                <img src = "pics/temp.jpeg" alt="cover" className="signup-img" />
                <h1 className={"signup-title"}>Sign Up</h1>
            </div>
            <div className="signup-input">
                <input
                    type="text"
                    name="username"
                    placeholder="Username"
                    value={form.username}
                    onChange={handleChange}
                />
                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={form.email}
                    onChange={handleChange}
                />
                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={form.password}
                    onChange={handleChange}
                />
                <button type="submit" className="signup-btn">Sign Up</button>
            </div>
            <p className="signup-text">Already have an account? <a href="/login">Login</a></p>
        </form>
      </div>
  )
};

export default SignUp;