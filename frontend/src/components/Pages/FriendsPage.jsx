import "./FriendsPage.css"
import UserIcon from "../Shared/UserIcon";
import {useLocation, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {authFetch} from "../Auth/AuthFetch";

const FriendsPage = ({setPopup}) => {
    const friendRequest = 9;
    const navigate = useNavigate();
    const location = useLocation();
    const [friends, setFriends] = useState([]);
    const handelClick = ()=>{
        if(location.pathname === '/friendRequests') {
            navigate('/friends');
        }
        else if(location.pathname === '/friends') {
            navigate('/friendRequests');
        }
    }

    useEffect(() => {
        const fetchFriends = async () => {
            try {

                const res = await ( location.pathname  === "/friends"
                        ? authFetch("http://localhost:8080/api/user/friend/listFriends", { method: "GET" })
                        : authFetch("http://localhost:8080/api/user/friend/listFriendRequests", { method: "GET" })
                );

                if (!res.ok) {
                    const errorText = await res.text();
                    console.error(`Request failed with status ${res.status}: ${errorText}`);
                    throw new Error(`Request failed with status ${res.status}`);
                }
                const json = await res.json();
                console.log(json);
                setFriends(json.data.friends);
            } catch (err) {
                console.error(err);
            }
        };

        fetchFriends();
    }, [location.pathname]);

    return (
        <div className="friends-page">
            <div className="friends-header" onClick={handelClick}>
                <span className="friends-page-title">{location.pathname === '/friendRequests'
                    ? 'Back To Friends' : 'Friend Requests' }</span>
            </div>
            <div className="friends-wrapper">
                {friends.length > 0
                    ? friends.map((friend, i) => (
                        <UserIcon key={i} user={friend} setPopup={setPopup} />
                    ))
                    : <h1 className="title">
                        {location.pathname === '/friendRequests'
                            ? 'You have no Friend Requests'
                            : 'You have no Friends yet'}
                    </h1>
                }
            </div>

        </div>
    )
}

export default FriendsPage