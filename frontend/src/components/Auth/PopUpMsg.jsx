import React, {useEffect} from 'react';
import './PopUpMsg.css';

const PopUpMsg = ({ status, message, data, onClose , buttonText, onButtonClick}) => {
    useEffect(() => {
        const handleClick = () => onClose();
        document.addEventListener('click', handleClick);

        return () => document.removeEventListener('click', handleClick);
    }, [onClose]);

    return (
        <div className={`popup-container ${status}`}>
            <div className="popup-content">
                <p className="msg">{message}</p>
                {data && <p className="data">{data.split('\n').map((line, i) => <span key={i}>{line}<br /></span>)}</p>}
                {buttonText && onButtonClick && (
                    <button className="popup-btn" onClick={(e) => { e.stopPropagation(); onButtonClick(); }}>
                        {buttonText}
                    </button>
                )}
            </div>
        </div>
    );
};

export default PopUpMsg;