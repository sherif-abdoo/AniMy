import React, {useState} from 'react';
import './AuthStyle.css';
import { useNavigate } from 'react-router-dom';

const AuthFrom = ({ type, onSubmit, setPopup }) => {
    const[form , setForm] = useState({ username: '', email: '', password: '' })
    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };


    const handleSubmit = async (e) => {
        e.preventDefault();
        onSubmit(form);
    };
    const isLogin = type === 'login';



    return (
        <div className="signup-container">
            <form className={ "signup-form"} onSubmit={handleSubmit}>
                <div className="signup-header">
                    <img src = {isLogin ? 'pics/temp2.jpg' : 'pics/temp.jpeg'} alt="cover" className="signup-img" />
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
                    {isLogin ? null :
                        <input
                            type="email"
                            name="email"
                            placeholder="Email"
                            value={form.email}
                            onChange={handleChange}
                        />
                    }
                    <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        value={form.password}
                        onChange={handleChange}
                    />
                    <button type="submit" className="signup-btn">{isLogin? 'Log In' : 'Sign Up'}</button>
                </div>
                <p className="signup-text">
                    {isLogin
                        ? "Don't have an account? "
                        : 'Already have an account? '}
                    <a href={isLogin ? "/signup" : "/login"}>
                        {isLogin ? 'Sign Up' : 'Log In'}
                    </a>
                </p>
            </form>
        </div>
    )
};

export default AuthFrom;