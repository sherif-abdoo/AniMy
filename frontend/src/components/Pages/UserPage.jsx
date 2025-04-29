import './UserPage.css'
import UserInfo from "../Shared/UserInfo";
import AnimeSlider from "../Shared/AnimeSlider";
import {useAuth} from "../../hooks/useAuth";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {authFetch} from "../Auth/AuthFetch";

const UserPage = ({setPopup})=>{
    const { isLoggedIn , user:owner} = useAuth();
    const navigate = useNavigate();

    const [user, setUser] = useState({})
    const {username} = useParams();

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const url = isLoggedIn
                    ? `http://localhost:8080/api/user/friend/${username}`
                    : `http://localhost:8080/api/public/profile/${username}`;

                const res = await authFetch(url, {
                    method: "GET",
                });

                if (!res.ok) {
                    const errorText = await res.text();
                    console.error(`Request failed with status ${res.status}: ${errorText}`);
                    throw new Error(`Request failed with status ${res.status}`);
                }

                const json = await res.json();
                console.log(json);
                setUser(json.data.profile);
            } catch (err) {
                console.log(err);
            }
        };
        fetchProfile();
    }, [username]);

    useEffect(() => {
        if (!isLoggedIn) {
            navigate("/login");
        }
    }, [isLoggedIn, navigate]);

    return (
        <div className="user-page">
            <UserInfo
                user={user}
                isOwner={owner?.sub && username && owner.sub.toLowerCase() === username.toLowerCase()}
                setPopup={setPopup}
            />

            <div className="user-sliders">
                <AnimeSlider title={"Favorite"} animeList={user.favourite} />
                <AnimeSlider title={"Watching"} animeList={user.watching}/>
                <AnimeSlider title={"Watched"} animeList={user.watched}/>
            </div>
        </div>
    )
}

export default UserPage;