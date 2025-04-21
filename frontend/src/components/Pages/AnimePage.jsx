import './AnimePage.css'
import {useParams} from "react-router-dom";
import {useAnimeContext} from "../context/AnimeContext";
import {useEffect, useState} from "react";
import axios from "axios";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faStar} from "@fortawesome/free-solid-svg-icons";
import Animes from "../Anime/Animes";
import AnimeSlider from "../Shared/AnimeSlider";

const AnimeDetails = ()=>{
    const {id} = useParams();
    const {getAnimeById} = useAnimeContext();
    const [anime, setAnime] = useState(null);

    useEffect(() => {
        const cached = getAnimeById(id);
        if (cached) {
            setAnime(cached);
            console.log("Loaded from cache");
        } else {
            axios.get(`https://api.jikan.moe/v4/anime/${id}`)
                .then(res => setAnime(res.data.data))
                .catch(err => console.log(err));
        }
    }, [id]);

    if(!anime){
        return(
            <h1 className={"loading"}>Loading...</h1>
        )
    }

    return (
        <div className="AnimeDetails">
            <div className = "top_section">
                <img src={anime.images.jpg.large_image_url} alt={anime.title} className="poster"/>
                <div className = "poster_info">
                    <h1 className={"poster_title"}>{anime.title_english || anime.title}</h1>
                    <div className={"tags"}>
                        <div className={"tag_row"}>
                            {anime.genres.map((genre) => (
                                <span key = {genre.mal_id} className={"tag poster_genre"}>{genre.name}</span>
                            ))}
                        </div>
                        <div className={"tag_row "}>
                            {anime.themes.map((theme) => (
                                <span key = {theme.mal_id} className={"tag poster_theme"}>{theme.name}</span>
                            ))}
                        </div>
                    </div>

                    <div className={"mid"}>
                        <p className={"anime_season"}>Season : {anime.season || "N/A"} {anime.year || "N/A"}</p>
                        <div className={"rating"}>
                            <FontAwesomeIcon icon={faStar} className={"star"} />
                            <p className="score">{anime.score || "N/A"}</p>
                        </div>
                    </div>
                    <p className={"poster_description"}>{anime.synopsis}</p>
                    <div className="btns">
                        <button className="btn green">Add To Watched</button>
                        <button className="btn green">Add To Watching</button>
                        <button className="btn pink">Add To Favourite</button>
                    </div>
                </div>
            </div>
            {anime.trailer.embed_url && (
                <div className="trailer">
                    <iframe
                        src={anime.trailer.embed_url}
                        title="Trailer"
                        allowFullScreen
                    ></iframe>
                </div>
            )}
            <AnimeSlider title={"Recommendations"} endpoint={'https://api.jikan.moe/v4/top/anime?filter=airing'}/>
        </div>
    )
}

export default AnimeDetails;