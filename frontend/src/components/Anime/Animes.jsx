import AnimeSlider from "../Shared/AnimeSlider";
import './Animes.css'
const Animes = ()=> {

    return (
        <div className="animes">
            <AnimeSlider
                key = {"trending"}
                title = {"Trending"}
                endpoint={'https://api.jikan.moe/v4/top/anime?filter=airing'}
            />

            <AnimeSlider
                key = {"topRated"}
                title = {"Top Rated"}
                endpoint={'https://api.jikan.moe/v4/top/anime'}
            />

            <AnimeSlider
                key = {"thisSeason"}
                title = {"This Season"}
                endpoint={'https://api.jikan.moe/v4/seasons/now'}
            />

        </div>
    )
}

export default Animes;