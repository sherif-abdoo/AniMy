import './SearchBar.css'
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {bannedGenres} from "./BannedGernes";
const SearchBar = ({placeHolder,type}) => {
    const [query, setQuery] = useState('');
    const [results, setResults] = useState([]);
    const navigate = useNavigate();


    useEffect(() => {
        const delayDebounce = setTimeout(() => {
            if(query.trim() === ''){
                setResults([]);
                return;
            }

            if(type === 'anime'){
                axios.get("https://api.jikan.moe/v4/anime" , {
                    params: {q : query , limit : 5},
                })
                .then(res => {
                    const filtered = res.data.data.filter(anime =>
                        !anime.genres.some(genre =>
                            bannedGenres.includes(genre.name.toLowerCase())
                        )
                    );
                    setResults(filtered.slice(0, 5));
                })
                .catch(err => console.log(err));
            }},50);
            return () => clearTimeout(delayDebounce);
    },[query,type]);



    const handleChange = (item) => {
        if(type === "anime"){
            navigate(`/anime/${item.mal_id}`);
        }else if(type === "friends"){
            navigate(`/users/item.id`)
        }
        setQuery('');
        setResults([]);

    };

    return (
        <div className="search-wrapper">
            <input
                type="text"
                placeholder={placeHolder}
                className="search-bar"
                value={query}
                onChange={(e) => setQuery(e.target.value)}
            />
            {results.length > 0 && (
                <div className="search-results">
                    {results.map((item) => (
                        <div
                            key={type === "anime" ? item.mal_id : item.id}
                            className="search-result"
                            onClick={() => handleChange(item)}
                        >
                            <div className="search-img">
                                <img
                                    src={
                                        type === "anime"
                                            ? item.images.jpg.large_image_url
                                            : item.profilePic
                                    }
                                    alt={item.title || item.username}
                                    className="result-img"
                                />
                            </div>
                            <span>
                                {type === "anime" ? item.title_english || item.title : item.username}
                            </span>
                        </div>
                    ))}
                </div>
            )}
        </div>

    )
}

export default SearchBar;