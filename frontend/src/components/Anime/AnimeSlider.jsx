import {useEffect, useRef, useState} from "react";
import axios from "axios";
import AnimeCard from "./AnimeCard";
import './AnimeSlider.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronLeft, faChevronRight } from '@fortawesome/free-solid-svg-icons';
import {bannedGenres} from "../Shared/BannedGernes";

const AnimeSlider = ({endpoint,title}) => {
    const [animes, setAnime] = useState([]);
    const [page, setPage] = useState(1);
    const sliderRef = useRef(null);

    useEffect(()=>{
        fetchAnime(page);
    }, [page]);


    const scroll = (direction) => {
        const scrollAmount = 300;
        if (direction === "left") {
            sliderRef.current.scrollBy({ left: -scrollAmount, behavior: "smooth" });
        } else {
            sliderRef.current.scrollBy({ left: scrollAmount, behavior: "smooth" });
        }
    };

    const fetchAnime = async (pageNum) => {
        try{
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
                return unique.slice(0, 20);
            });
        }catch(err){
            console.log("fetch err:" , err);
        }
    }

    const handleScroll = () => {
        const el = sliderRef.current;
        if (!el) return;
        const atStart = el.scrollLeft <= 10;
        const atEnd = el.scrollLeft + el.clientWidth >= el.scrollWidth - 10;
        if (el.scrollLeft + el.clientWidth >= el.scrollWidth - 100 && animes.length < 20) {
            setPage(prev => prev + 1);
        }
    };

    return(
        <div className = "container">
            <h1 className={"title"}>{title || "Trending"}</h1>

            <div className="arrow left" onClick={() => scroll("left")}>
                <FontAwesomeIcon icon={faChevronLeft} />
            </div>

            <div className = "slider_wrapper">
                <div className={"anime_slider"}  ref={sliderRef} onScroll={handleScroll}>
                    {animes.map((anime) =>(
                        <AnimeCard key = {anime.mal_id} anime = {anime}/>
                    ))}
                </div>
            </div>

            <div className="arrow right" onClick={() => scroll("right")}>
                <FontAwesomeIcon icon={faChevronRight} />
            </div>

        </div>
    )


}

export default AnimeSlider;