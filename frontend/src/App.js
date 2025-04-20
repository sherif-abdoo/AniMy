import './App.css';
import SignUp from './components/Auth/SignUp';
import SignIn from './components/Auth/SignIn';
import AnimeList from './components/Anime/AnimeList';
import {Routes, Route} from 'react-router-dom';
import {useState} from "react";
import {useAuth} from "./hooks/useAuth";
import PopUpMsg from "./components/Auth/PopUpMsg";
import Home from "./components/Pages/Home";
import Layout from "./components/Layout/Layout";
import AnimeSlider from "./components/Anime/AnimeSlider";
import Animes from "./components/Anime/Animes";

function App() {
  const [popup, setPopup] = useState({ show: false, status: '', message: '', data: '' });
  const {isLoggedIn} = useAuth();
  return (
      <>
        {/* Global Routes */}
        <Layout>
            <Routes>
                <Route path = "/" element={<Home />} />
                {/*<Route path = "/test" element={<AnimeSlider />} />*/}
                <Route path = "/animes" element={<Animes/>}/>
                <Route path="/signup" element={<SignUp setPopup={setPopup} />} />
                <Route path="/login" element={<SignIn setPopup={setPopup} />} />

            </Routes>
        </Layout>


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
