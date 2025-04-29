import {useEffect, useRef, useState} from "react";
import axios from "axios";
import AnimeCard from "./AnimeCard";
import './AnimeSlider.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronLeft, faChevronRight } from '@fortawesome/free-solid-svg-icons';
import {bannedGenres} from "./BannedGernes";
import { useAnimeContext } from "../context/AnimeContext";
import {authFetch} from "../Auth/AuthFetch";

const AnimeSlider = ({endpoint,title,animeList}) => {
    const [animes, setAnime] = useState([]);
    const [page, setPage] = useState(1);
    const sliderRef = useRef(null);
    const { storeAnimeList } = useAnimeContext();
    const [arrows , setArrows] = useState(true);



    const scroll = (direction) => {
        const scrollAmount = 300;
        if (direction === "left") {
            sliderRef.current.scrollBy({ left: -scrollAmount, behavior: "smooth" });
        } else {
            sliderRef.current.scrollBy({ left: scrollAmount, behavior: "smooth" });
        }
    };

    useEffect(() => {
        setAnime([]);
        const fetch = async () => {
            if (animeList && Array.isArray(animeList) && animeList.length > 0) {
                const ids = animeList.map(item => typeof item === 'object' ? item.animeId : item);
                console.log("Fetching animes by IDs from clean array...", ids);
                await fetchAnimesByIds(ids);
                setArrows(true)
            } else if (!animeList && page === 1 && endpoint) {
                await fetchAnime(page);
                setArrows(true)
            }
            else {
                setArrows(false);
            }
        };
        fetch();
    }, [animeList]);

    const fetchAnimesByIds = async (ids) => {
        try {
            const animePromises = ids.map(id =>
                axios.get(`https://api.jikan.moe/v4/anime/${id}`).then(r => r.data.data)
            );

            const animeResults = await Promise.allSettled(animePromises);

            const animesDetails = animeResults
                .filter(result => result.status === 'fulfilled')
                .map(result => result.value);

            const validAnimes = animesDetails.filter(anime => anime !== null && anime !== undefined);

            setAnime(validAnimes);

            console.log("Fetched animes by IDs successfully ✅");

            setTimeout(() => {
                storeAnimeList(validAnimes);
            }, 0);
        } catch (err) {
            console.error("Error fetching animes by IDs:", err);
        }
    };




    const fetchAnime = async (pageNum) => {
        try{
            if(endpoint){
                const res = await axios.get(endpoint,{
                    params: {
                        page: pageNum,
                        limit: pageNum === 1 ? 15 : 5,
                    },
                });

                const newAnimes = res.data.data.filter(anime =>
                    !anime.genres.concat(anime.themes || [], anime.demographics || []).some(genre =>
                        bannedGenres.includes(genre.name.toLowerCase())
                    )
                );

                setAnime(prev => {
                    const combined = [...prev, ...newAnimes];
                    const unique = combined.filter((anime, index, self) =>
                        index === self.findIndex(a => a.mal_id === anime.mal_id)
                    );
                    const re = unique.slice(0, 20);
                    storeAnimeList(re);
                    return re;
                });
            }
            else{
                const res = await authFetch(`http://localhost:8080/api/user/animeList?status=${title.toUpperCase()}`, {
                    method: "GET",
                });

                const resJson = await res.json();
                console.log(resJson);
                const animesIds = resJson.data.animes.map(anime => Number(anime.animeId));

                fetchAnimesByIds(animesIds);

            }
        }catch(err){
            console.log("fetch err:" , err);
        }
    }

    const handleScroll = () => {
        const el = sliderRef.current;
        if (!el) return;
        if (el.scrollLeft + el.clientWidth >= el.scrollWidth - 100 && animes.length < 20) {
            setPage(prev => prev + 1);
        }
    };

    return(
        <div className = "container">
            <h1 className={"title"}>{title || "Trending"}</h1>

            {arrows &&
                (<div className="arrow left" onClick={() => scroll("left")}>
                    <FontAwesomeIcon icon={faChevronLeft} />
                </div>)
            }

            <div className = "slider_wrapper">
                <div className={"anime_slider"}  ref={sliderRef} onScroll={handleScroll}>
                    {animes.map((anime) =>(
                        <AnimeCard key = {anime.mal_id} anime = {anime}/>
                    ))}
                </div>
            </div>

            {arrows &&
                (<div className="arrow right" onClick={() => scroll("right")}>
                    <FontAwesomeIcon icon={faChevronRight} />
                </div>)
            }

        </div>
    )


}

export default AnimeSlider;