import SearchBar from "../Shared/SearchBar";
import logo from '../../assets/logo.png';
import {Link} from "react-router-dom";
import "./NavBar.css";

const NavBar = ()=>{
    return (
        <div className="nav-bar">
            <img src = {logo} className="logo" alt="logo"/>
            <SearchBar className = "search-bar" placeHolder={"Search Anime Title"}/>
            <ul className={"items"}>
                <li><Link to="/animes" className={"item"}>Home</Link></li>
                <li><Link to="/" className={"item"}>Friends</Link></li>
                <li><Link to="/" className={"item"}>About</Link></li>
                <Link to="/signup" className="btn purple">Sign Up</Link>
                <Link to="/login" className="btn white">Log In</Link>
            </ul>
        </div>
    )
}

export default NavBar;