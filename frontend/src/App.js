import './App.css';
import SignUp from './components/Auth/SignUp';
import { Routes, Route } from 'react-router-dom';
import PopUpMsg from "./components/PopUpMsg";
import {useState} from "react";

function App() {
  const [popup, setPopup] = useState({ show: false, status: '', message: '', data: '' });

  return (
      <>
        {/* Global Routes */}
        <Routes>
          <Route path="/signup" element={<SignUp setPopup={setPopup} />} />
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
