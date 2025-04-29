import "./UserInfo.css";
import {useAuth} from "../../hooks/useAuth";
import {useEffect, useState} from "react";
import {authFetch} from "../Auth/AuthFetch";
import IsValidImageUrl from "./UrlFilter";
import {refreshToken} from "../Auth/RefreshToken";

const UserInfo = ({user,isOwner,setPopup})=>{
    const {logout} = useAuth();
    const [editingBio, setEditingBio] = useState(false);
    const [bioInput, setBioInput] = useState(user.bio || "");

    useEffect(() => {
        setBioInput(user.bio || "");
    }, [user]);

    const handleEditClick = ()=>{
        setEditingBio(true);
    };


    const handleSaveClick = async ()=>{
        try{
            const res = await authFetch(`http://localhost:8080/api/user/updateProfile/bio`, {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ bio: bioInput }),
            });

            const resJson = await res.json();
            console.log(resJson);

            setEditingBio(false);
        }catch (err){
            console.log(err);
        }
    }

    const handleUpload = async (e)=>{
        const file = e.target.files[0];
        if (!file) return;
        if(!IsValidImageUrl(file.name)){
            alert("Invalid file type. Please upload an image (jpg, jpeg, png, gif, webp).");
            return;
        }
        const formData = new FormData();
        formData.append("file", file);
        formData.append("upload_preset", "user_avatars");

        try{
            const res = await fetch("https://api.cloudinary.com/v1_1/dmqmxtwj1/image/upload", {
                method: "POST",
                body: formData,
            });
            const data = await res.json();
            console.log(data);

            const uploadedImageUrl = data.secure_url;


            await authFetch(`http://localhost:8080/api/user/updateProfile/avatar`, {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ avatar: uploadedImageUrl }),
            });


            await refreshToken();
            window.location.reload();
        }
        catch(err){
            console.log(err);
        }
    }

    const handleFriendRequest = async () =>{
        try{
            const res = await authFetch(`http://localhost:8080/api/user/friend/request/${user.username}`, {method: "POST"});
            const json = await res.json();
            console.log(json);
            window.location.reload();
        }catch(err){
            console.log(err);
        }

    }
    const handleFriendBlock = async () =>{
        try{
            const res = await authFetch(`http://localhost:8080/api/user/friend/block/${user.username}`, {method: "POST"});
            const json = await res.json();
            console.log(json);

            window.location.reload();
        }catch(err){
            console.log(err);
        }

    }

    const handleCancel = async () => {
        try {
            const res = await authFetch(`http://localhost:8080/api/user/friend/cancel/${user.username}`, {
                method: "POST",
            });
            const json = await res.json();
            console.log(json);

            window.location.reload();
        } catch (err) {
            console.log(err);
        }
    };

    const handleUnFriend = async () => {
        try {
            const res = await authFetch(`http://localhost:8080/api/user/friend/unfriend/${user.username}`, {
                method: "POST",
            });
            const json = await res.json();
            console.log(json);
            setPopup({
                show: true,
                status: res.ok ? 'success' : 'error',
                message: json.data.message,
                data: null,
            });
            window.location.reload(); // reload page after unfriending
        } catch (err) {
            console.log(err);
        }
    };

    const handleUnblock = async () => {
        try {
            const res = await authFetch(`http://localhost:8080/api/user/friend/unblock/${user.username}`, {
                method: "POST",
            });
            const json = await res.json();
            console.log(json);
            setPopup({
                show: true,
                status: res.ok ? 'success' : 'error',
                message: json.data.message,
                data: null,
            });
            window.location.reload();
        } catch (err) {
            console.log(err);
        }
    };


    return(

        <div className="profile-header">

            <div className="user-section">
                <div className="profileImage-wrapper">
                    {isOwner ? (
                        <label htmlFor="profile-upload" className="upload-label">
                            <img src={user.avatar} alt="profile image" className="profileImage" />
                            <div className="upload-overlay">Upload New Picture</div>
                        </label>
                    ) : (
                        <img src={user.avatar} alt="profile image" className="profileImage" />
                    )}
                    <input
                        type="file"
                        id="profile-upload"
                        accept="image/*"
                        onChange={handleUpload}
                        style={{ display: 'none' }}
                    />
                </div>

                <div className="user-right-section">
                    <div className="user-info">
                        <h1 className="user-name">{user.username}</h1>
                        <span className="profile-friends">Friends: {user.friendsCount ?? 0}</span>
                    </div>

                    <div className="user-animes">
                        <div className="user-anime">
                            <span className="user-anime-title">Favorite Animes</span>
                            <span className="user-anime-number">{user.favourite?.length ?? 0} Animes</span>
                        </div>
                        <div className="user-anime">
                            <span className="user-anime-title">Watching Animes</span>
                            <span className="user-anime-number">{user.watching?.length ?? 0} Animes</span>
                        </div>
                        <div className="user-anime">
                            <span className="user-anime-title">Watched Animes</span>
                            <span className="user-anime-number">{user.watched?.length ?? 0} Animes</span>
                        </div>
                    </div>

                    <div className="bio-section">
                        <h2 className={"bio"}>{bioInput}</h2>
                    </div>

                    <div className="user-btns">
                        {editingBio ? (
                            <>
                                <textarea
                                    value={bioInput}
                                    onChange={(e) => setBioInput(e.target.value)}
                                    className="bio-textarea"
                                />
                                <button onClick={handleSaveClick} className="btn save">Save</button>
                            </>
                        ) : (
                            <>
                                {isOwner ? (
                                    <>
                                        <button onClick={handleEditClick} className="btn edit">Edit Bio</button>
                                        <button onClick={logout} className="btn signOut">Sign Out</button>
                                    </>
                                ) : (
                                    <>
                                        {user.status === null && (
                                            <>
                                                <button className="btn request" onClick={handleFriendRequest}>Friend Request</button>
                                                <button className="btn block" onClick={handleFriendBlock}>Block</button>
                                            </>
                                        )}
                                        {user.status === "PENDING" && (
                                            <>
                                                <button className="btnn disabled" disabled>Request Sent</button>
                                                <button className="btn block" onClick={handleCancel}>Cancel Friend Request</button>
                                            </>
                                        )}
                                        {user.status === "ACCEPTED" && (
                                            <>
                                                <button className="btn unfriend" onClick={handleUnFriend}>Unfriend</button>
                                                <button className="btn block" onClick={handleFriendBlock}>Block</button>
                                            </>
                                        )}
                                        {user.status === "BLOCKED" && (
                                            <>
                                                <button className="btn unblock" onClick={handleUnblock}>Unblock</button>
                                            </>
                                        )}

                                    </>
                                )}
                            </>
                        )}
                    </div>
                </div>
            </div>
        </div>

    )
}

export default UserInfo;