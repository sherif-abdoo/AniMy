import {useLocation} from "react-router-dom";
import NavBar from "./NavBar";


const Layout = ({ children }) => {
    const location = useLocation();
    const path = location.pathname;

    const hideNav = path === '/login' || path === '/signup' || path === '/test';
    return (
        <>
            {!hideNav && <NavBar/>}
            {children}
        </>
    )
}

export default Layout;