import './App.css';
import SignUp from './components/Auth/SignUp';
import SignIn from './components/Auth/SignIn';
import HeroSection from './components/Section/HeroSection';
import AnimeList from './components/Anime/AnimeList';
import {Routes, Route, Navigate} from 'react-router-dom';
import {useState} from "react";
import {useAuth} from "./hooks/useAuth";
import PopUpMsg from "./components/Auth/PopUpMsg";
import Home from "./components/Pages/Home";

function App() {
  const [popup, setPopup] = useState({ show: false, status: '', message: '', data: '' });
  const {isLoggedIn} = useAuth();
  return (
      <>
        {/* Global Routes */}
        <Routes>
            <Route path = "/" element={<Home />} />
            <Route path = "/animes" element={<AnimeList/>}/>
            <Route path="/signup" element={<SignUp setPopup={setPopup} />} />
            <Route path="/login" element={<SignIn setPopup={setPopup} />} />
          {/* add more routes */}
        </Routes>

        {/* Global Popup Renderer */}
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
