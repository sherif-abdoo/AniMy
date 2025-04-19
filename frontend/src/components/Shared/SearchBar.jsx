import './SearchBar.css'
const SearchBar = ({placeHolder,onChange,Style}) => {
    return (
        <input
            type="text"
            placeholder={placeHolder}
            onChange={onChange}
            style={Style}
            className="search-bar"
        />
    )
}

export default SearchBar;