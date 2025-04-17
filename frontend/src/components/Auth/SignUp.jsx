import React from 'react';
import './SignUp.css';
const SignUp = () => {
  return (
      <div className="signup-container">
        <form className={ "signup-form"} onsubmit="return false">
            <div className="signup-header">
                <img src = "pics/temp.jpeg" alt="cover" className="signup-img" />
                <h1 className={"signup-title"}>Sign Up</h1>
            </div>
            <div className="signup-input">
                <input type="text" placeholder="Username"/>
                <input type="text" placeholder="Email"/>
                <input type="password" placeholder="Password"/>
                <button type="submit" className="signup-btn">Sign Up</button>
            </div>
            <p className="signup-text">Already have an account? <a href="/login">Login</a></p>
        </form>
      </div>
  )
};

export default SignUp;