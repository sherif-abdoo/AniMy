import SearchBar from "../Shared/SearchBar";
import logo from '../../assets/logo.png';
import profile from '../../assets/profil.jpg';
import {Link, useNavigate} from "react-router-dom";
import "./NavBar.css";
import {useAuth} from "../../hooks/useAuth";

const NavBar = ()=>{
    const {isLoggedIn,user} = useAuth();
    const navigate = useNavigate();


    const profileClickHandler = () =>{
        navigate(`/profile/${user.sub}`);
    }
    const logoClickHandler = () =>{
        navigate("/");
    }
    if (!user && isLoggedIn) {
        // User is still loading, so don't render anything yet
        return null;
    }
    return (
        <div className="nav-bar">
            <img src = {logo} className="logo" alt="logo" onClick={logoClickHandler}/>
            <SearchBar className = "search-bar" placeHolder={"Search Anime Title"} type={"anime"}/>
            <ul className={"items"}>
                <li><Link to="/animes" className={"item"}>Animes</Link></li>
                <li><Link to="/friends" className={"item"}>Friends</Link></li>
                <li><Link to="/about" className={"item"}>About</Link></li>
            </ul>
                {!isLoggedIn?(
                        <div className="btns">
                            <Link to="/signup" className="btn purple">Sign Up</Link>
                            <Link to="/login" className="btn white">Log In</Link>
                        </div>
                    )
                    :(
                        <div className="profile-info" onClick={profileClickHandler}>
                            <img src={user.avatar} className="profile-pic" alt="profile"
                                 onClick={profileClickHandler} />
                            <span className="username"
                                  onClick={profileClickHandler}>{user?.sub}</span>
                        </div>
                    )
                }
        </div>
    )
}

export default NavBar;