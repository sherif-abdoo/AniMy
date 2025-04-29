import './AnimePage.css';
import {useParams, useNavigate} from "react-router-dom";
import {useAnimeContext} from "../context/AnimeContext";
import {useEffect, useState} from "react";
import axios from "axios";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faStar} from "@fortawesome/free-solid-svg-icons";
import AnimeSlider from "../Shared/AnimeSlider";
import PopUpMsg from "../Auth/PopUpMsg"; // 🛠 Capital P!
import {authFetch} from "../Auth/AuthFetch";
import {useAuth} from "../../hooks/useAuth";


const AnimeDetails = () => {
    const { id } = useParams();
    const { getAnimeById } = useAnimeContext();
    const [anime, setAnime] = useState(null);
    const [popup, setPopup] = useState(null);
    const [animeStatus, setAnimeStatus] = useState({});
    const {isLoggedIn } = useAuth();
    const navigate = useNavigate();

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

    const handelClick = async (type) => {
        if(!isLoggedIn){
            navigate("/login");
        }
        try {
            const res = await authFetch(`http://localhost:8080/api/user/animeList/${anime.mal_id}`, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    status: type
                }),
                credentials: 'include'
            });

            if (!res.ok) {
                const errorText = await res.text();
                console.error(`Request failed with status ${res.status}: ${errorText}`);
                throw new Error(`Request failed with status ${res.status}`);
            }

            const json = await res.json();



            setAnimeStatus(prev => ({
                ...prev,
                [anime.mal_id] : type,
            }));
            setPopup({
                show: true,
                status: json.status,
                message: json.data.message || "something went wrong",
                data: null,
            });

        } catch (err) {
            console.log(err);
        }
    };

    if (!anime) {
        return <h1 className="loading">Loading...</h1>;
    }

    return (
        <>
            {popup && popup.show && (
                <PopUpMsg
                    status={popup.status}
                    message={popup.message}
                    data={popup.data}
                    buttonText={"OK"}
                    onButtonClick={() => setPopup(null)}
                    onClose={() => setPopup(null)}
                />
            )}

            <div className="AnimeDetails">
                <div className="top_section">
                    <img src={anime.images.jpg.large_image_url} alt={anime.title} className="poster" />
                    <div className="poster_info">
                        <h1 className="poster_title">{anime.title_english || anime.title}</h1>

                        <div className="tags">
                            <div className="tag_row">
                                {anime.genres.map((genre) => (
                                    <span key={genre.mal_id} className="tag poster_genre">{genre.name}</span>
                                ))}
                            </div>
                            <div className="tag_row">
                                {anime.themes.map((theme) => (
                                    <span key={theme.mal_id} className="tag poster_theme">{theme.name}</span>
                                ))}
                            </div>
                        </div>

                        <div className="mid">
                            <p className="anime_season">Season: {anime.season || "N/A"} {anime.year || "N/A"}</p>
                            <div className="rating">
                                <FontAwesomeIcon icon={faStar} className="star" />
                                <p className="score">{anime.score || "N/A"}</p>
                            </div>
                        </div>

                        <p className="poster_description">{anime.synopsis}</p>

                        <div className="btns">
                            <button className="btn green"
                                    onClick={() => handelClick('WATCHED')}
                                    disabled={animeStatus[anime.mal_id] === "WATCHED"}
                            >
                                Add To Watched</button>
                            <button className="btn green"
                                    onClick={() => handelClick('WATCHING')}
                                    disabled={animeStatus[anime.mal_id] === "WATCHING"}
                            >
                                Add To Watching</button>
                            <button className="btn pink"
                                    onClick={() => handelClick('FAVORITE')}
                                    disabled={animeStatus[anime.mal_id] === "FAVORITE"}
                            >
                                Add To Favourite</button>
                        </div>
                    </div>
                </div>

                {anime.trailer?.embed_url && (
                    <div className="trailer">
                        <iframe src={anime.trailer.embed_url} title="Trailer" allowFullScreen></iframe>
                    </div>
                )}

                <AnimeSlider title={"Recommendations"} endpoint={'https://api.jikan.moe/v4/top/anime?filter=airing'} />
            </div>
        </>
    );
};

export default AnimeDetails;
