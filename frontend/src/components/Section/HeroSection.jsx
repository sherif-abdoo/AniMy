import './HeroSection.css';
import { useNavigate } from 'react-router-dom';
import madaraImg from '../../assets/hero.png';
import NavBar from "../Layout/NavBar";



const HeroSection = () =>{
    const navigate = useNavigate();
    const handelOnClick = ()=>{
        // eslint-disable-next-line react-hooks/rules-of-hooks
        navigate('/animes');
    }
    return (
        <div className="hero">
            <div className = "left_section">
                <div className = "text">
                    <h1>No Place For Weirdos</h1>
                    <h3>AniMy is your anime companion — track shows, connect
                        <br/> with friends, and explore your next obsession.</h3>
                </div>
                <button onClick = {handelOnClick}>Discover Animes</button>
            </div>
            <div className = "right_section">
                <img src = {madaraImg} alt="cover" className="signup-img" />
            </div>
        </div>
    )
}

export default  HeroSection;