import "./UserIcon.css"
import {useLocation, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faCheck, faXmark } from "@fortawesome/free-solid-svg-icons";
import {authFetch} from "../Auth/AuthFetch";


const UserIcon = ({user , setPopup}) => {

    const navigate = useNavigate();
    const [friendRequests, setFriendRequests] = useState(false);
    const location = useLocation();

    const handleClick = () => {
        navigate(`/profile/${user.username}`);
    }

    useEffect(() => {
        const url = location.pathname === "/friendRequests";
        setFriendRequests(url);
    })

    const handelAccept = async  ()=>{
        try{
            const res = await authFetch(`http://localhost:8080/api/user/friend/accept/${user.username}`,{
                method: "POST"
            })
            window.location.reload()
        }
        catch(err){
            console.log(err);
        }
    }

    const handelReject = async ()=>{
        try{
            const res = await authFetch(`http://localhost:8080/api/user/friend/reject/${user.username}`,{
                method: "POST"
            })
            window.location.reload()
        }
        catch(err){
            console.log(err);
        }
    }

    return (
        <div className="UserIcon" >
            <div className="user-icon-header" onClick={handleClick}>
                <img className="user-icon-img" src={user.avatar} alt="" />
                <div className="user-icon-name">{user.username}</div>
            </div>
            {friendRequests &&(
                <>
                    <FontAwesomeIcon icon={faCheck} className={"icon accept"} onClick={handelAccept}/>
                    <FontAwesomeIcon icon={faXmark} className={"icon reject"} onClick={handelReject}/>
                </>
            )}
        </div>
    )
}

export default UserIcon