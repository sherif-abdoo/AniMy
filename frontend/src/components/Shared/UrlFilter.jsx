const allowedExtensions = [".jpg", ".jpeg", ".png", ".gif", ".webp"];

const IsValidImageUrl = (url) => {
    return allowedExtensions.some(ext => url.toLowerCase().endsWith(ext));
};

export default IsValidImageUrl;