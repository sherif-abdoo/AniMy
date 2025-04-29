import './App.css';
import SignUp from './components/Auth/SignUp';
import SignIn from './components/Auth/SignIn';
import {Routes, Route} from 'react-router-dom';
import {useState} from "react";
import {useAuth} from "./hooks/useAuth";
import PopUpMsg from "./components/Auth/PopUpMsg";
import Home from "./components/Pages/Home";
import Layout from "./components/Layout/Layout";
import Animes from "./components/Anime/Animes";
import AnimePage from "./components/Pages/AnimePage";
import {AnimeProvider} from "./components/context/AnimeContext";
import ScrollToTop from "./components/Layout/ScrollToTop";
import UserInfo from "./components/Shared/UserInfo";
import UserPage from "./components/Pages/UserPage";
import FriendsPage from "./components/Pages/FriendsPage";
import UserIcon from "./components/Shared/UserIcon";


function App() {
  const [popup, setPopup] = useState({ show: false, status: '', message: '', data: '' });
  const {isLoggedIn} = useAuth();
  return (
      <>
        <AnimeProvider>
            <ScrollToTop/>
                <Layout>
                        <Routes>
                            <Route path = "/" element={<Home />} />
                            <Route path = "/anime/:id" element={<AnimePage />} />
                            <Route path = "/animes" element={<Animes/>}/>
                            <Route path="/signup" element={<SignUp setPopup={setPopup} />} />
                            <Route path="/login" element={<SignIn setPopup={setPopup} />} />
                            <Route path="/profile" element={<UserPage/>} />
                            <Route path="/profile/:username" element={<UserPage setPopup={setPopup}/>} />
                            <Route path="/friends" element={<FriendsPage setPopup={setPopup}/>} />
                            <Route path="/friendRequests" element={<FriendsPage/>} />
                        </Routes>
                </Layout>
        </AnimeProvider>


          {popup.show && (
              <PopUpMsg
                  status={popup.status}
                  message={popup.message}
                  data={popup.data}
                  onClose={() => setPopup({ ...popup, show: false })}
                  buttonText={popup.buttonText}
                  onButtonClick={popup.onButtonClick}
              />
          )}
      </>
  );
}

export default App;
