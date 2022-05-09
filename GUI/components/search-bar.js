import { useState } from "react";
import api from "./api";
const SearchBar = (props) => {
  const [isProduct, setIsProduct] = useState(false);
  const [isStore, setisStore] = useState(false);
  const [current, setCurrent] = useState("Type");
  const [value, setValue] = useState("");
  const onChange = (e) => {
    setValue(e.target.value);
  };

  const onSearch = (e) => {
    e.preventDefault();
    if (isProduct || isStore) {
      api
        .get(
          `/search/${
            isProduct
              ? "products/" + value
              : isStore
              ? "stores/" + value
              : value
          }`
        )
        .then((res) => {
          const { data } = res;
          props.setProducts(data);
        });
    }
  };

  const onProducts = () => {
    setCurrent("Products");
    setIsProduct(!isProduct);
  };

  const onStores = () => {
    setCurrent("Stores");
    setisStore(!isStore);
  };
  return (
    <div className="container">
      <nav className="navbar navbar-expand-lg bg-secondery align-items-center justify-content-center rounded-3">
        <form
          className="row form-inline"
          style={{ display: "flex", width: "40%" }}
        >
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
          </div>
        </form>
        <div className="dropdown">
          <button
            className="btn btn-primary dropdown-toggle"
            type="button"
            id="dropdownMenu2"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            {current}
          </button>
          <ul className="dropdown-menu" aria-labelledby="dropdownMenu2">
            <li>
              <button
                className="dropdown-item"
                type="button"
                onClick={onProducts}
              >
                Products
              </button>
            </li>
            <li>
              <button
                className="dropdown-item"
                type="button"
                onClick={onStores}
              >
                Stores
              </button>
            </li>
          </ul>
        </div>
      </nav>
    </div>
  );
};

export default SearchBar;
