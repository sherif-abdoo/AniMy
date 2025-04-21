import {createContext , useContext , useState} from "react";

const AnimeContext = createContext();

export const AnimeProvider   = ({children}) =>{
    const [animeMap , setAnimeMap] = useState({});

    const storeAnimeList = (list) =>{
        const newMap = {};
        list.forEach((anime)=>{
            newMap[anime.mal_id] = anime;
        });
        setAnimeMap((prev) => ({ ...prev, ...newMap }));
    };
    const getAnimeById = (id) => animeMap[id];
    return (
        <AnimeContext.Provider value={{storeAnimeList , getAnimeById}}>
            {children}
        </AnimeContext.Provider>
    )
}

export const useAnimeContext = () => useContext(AnimeContext);