import { useState } from "react";
const SearchBar = ({ setSearchProducts, setSearchBy }) => {
  const [value, setValue] = useState("");
  const [searchOption, setSearchOption] = useState("Filter");
  const onChange = (e) => {
    setValue(e.target.value);
  };

  const onSearch = (e) => {
    e.preventDefault();
    setSearchProducts(value);
  };

  const onSearchOption = (e) => {
    e.preventDefault();
    setSearchBy(e.target.id);
    setSearchOption(e.target.id);
  };

  return (
    <div className="container">
      <nav className="navbar navbar-expand-lg bg-secondery align-items-center justify-content-center rounded-3">
        <form className="row form-inline d-flex w-50">
          <div className="main-search-bar">
            <input
              className="form-control mr-sm-2"
              type="search"
              placeholder="Search for anything"
              aria-label="Search"
              onChange={onChange}
            />
            <div>
              <button className="btn btn-primary mr-lg-3" onClick={onSearch}>
                Search
              </button>
            </div>
            <div className="dropdown">
              <button
                className="btn btn-primary dropdown-toggle"
                type="button"
                id="dropdownMenuButton1"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                {searchOption}
              </button>
              <ul
                className="dropdown-menu"
                aria-labelledby="dropdownMenuButton1"
              >
                <li key={"name"}>
                  <a
                    className="dropdown-item"
                    href="#"
                    id="name"
                    onClick={onSearchOption}
                  >
                    Name
                  </a>
                </li>
                <li key={"category"}>
                  <a
                    className="dropdown-item"
                    href="#"
                    id="category"
                    onClick={onSearchOption}
                  >
                    Category
                  </a>
                </li>
                <li key={"price"}>
                  <a
                    className="dropdown-item"
                    href="#"
                    id="price"
                    onClick={onSearchOption}
                  >
                    Price
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </form>
      </nav>
    </div>
  );
};

export default SearchBar;
