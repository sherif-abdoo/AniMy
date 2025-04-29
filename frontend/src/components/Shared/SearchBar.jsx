import './SearchBar.css';
import {useEffect, useState} from "react";
import {useNavigate, useLocation} from "react-router-dom";
import axios from "axios";
import {bannedGenres} from "./BannedGernes";

const SearchBar = () => {
    const [query, setQuery] = useState('');
    const [results, setResults] = useState([]);
    const navigate = useNavigate();
    const location = useLocation();

    // Detect search type based on route
    const searchType = (location.pathname.startsWith('/friends') || location.pathname.startsWith('/friendRequests'))
        ? 'friends'
        : 'anime';

    // Set placeholder based on route
    const placeHolder = searchType === 'friends' ? 'Search Friends...' : 'Search Anime...';

    useEffect(() => {
        const delayDebounce = setTimeout(() => {
            if (query.trim() === '') {
                setResults([]);
                return;
            }

            if (searchType === 'anime') {
                axios.get("https://api.jikan.moe/v4/anime", {
                    params: {q: query, limit: 5},
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
            } else if (searchType === 'friends') {
                axios.get(`http://localhost:8080/api/public/profile/searchUsers?q=${query}`, { withCredentials: true })
                    .then(res => {
                        setResults(res.data.data.users.slice(0, 5));
                    })
                    .catch(err => console.log(err));
            }
        }, 50);

        return () => clearTimeout(delayDebounce);
    }, [query, searchType]);

    const handleChange = (item) => {
        if (searchType === "anime") {
            navigate(`/anime/${item.mal_id}`);
        } else if (searchType === "friends") {
            navigate(`/profile/${item.username}`);
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
                            key={searchType === "anime" ? item.mal_id : item.username}
                            className="search-result"
                            onClick={() => handleChange(item)}
                        >
                            <div className="search-img">
                                <img
                                    src={
                                        searchType === "anime"
                                            ? item.images.jpg.large_image_url
                                            : item.avatar
                                    }
                                    alt={searchType === "anime" ? (item.title_english || item.title) : item.username}
                                    className="result-img"
                                />
                            </div>
                            <span>
                                {searchType === "anime" ? (item.title_english || item.title) : item.username}
                            </span>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default SearchBar;
