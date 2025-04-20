import {Link} from "react-router-dom";
import './AnimeCard.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar } from '@fortawesome/free-solid-svg-icons';


const AnimeCard = ({anime})=>{
    return(

        <Link to={`/anime/${anime.mal_id}`} className="anime_card">
            <img src={anime.images?.jpg?.image_url} alt="anime" className="anime_image"/>
            <div className="anime_dis">
                <div className="anime_title">{anime.title_english || anime.title}</div>
                <div className = "content">
                    <p className={"year"}>{anime.year ?? new Date(anime.aired.from).getFullYear() ?? "?"}</p>
                    <div className={"rating"}>
                        <FontAwesomeIcon icon={faStar} className={"star"} />
                        <p className="rating">{anime.score || "N/A"}</p>
                    </div>
                </div>
            </div>
        </Link>
    )
}
export default AnimeCard;