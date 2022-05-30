import Menu from "../components/menu";
import { useState } from "react";
import api from "../components/api";
import StoreCard from "../components/store-card";
import { useCookies } from "react-cookie";

const EditStoreSupply = () => {
  const [store, setStore] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [searchValue, setSearchValue] = useState("");
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);
  const onSearch = (e) => {
    e.preventDefault();
    setIsLoading(!isLoading);
    api
      .get(`/stores/all/?userId=${cookies.userId}&name=${searchValue}`)
      .then((res) => {
        const { data } = res;
        if (data.success) {
          setStore([res.data]);
          setIsLoading(!isLoading);
        }
      })
      .catch((err) => console.log(err));
  };

  const onChange = (e) => {
    e.preventDefault();
    setSearchValue(e.target.value);
  };
  return (
    <>
      <Menu />
      <div className="container m-auto w-100">
        <span className="text-center my-4">
          <h3>Edit store's supply</h3>
        </span>
        <nav className="navbar navbar-expand-lg bg-secondery d-flex justify-content-center">
          <form className="row form-inline col-4">
            <input
              className="form-control mr-sm-2 m-2"
              type="search"
              placeholder="Enter store name"
              aria-label="Search"
              onChange={onChange}
            />

            <div className="d-flex justify-content-center">
              <button className="btn btn-primary my-3" onClick={onSearch}>
                Search
              </button>
            </div>
          </form>
        </nav>
        <div className="container">
          <div className="row">
            <ul>
              {!isLoading ? (
                store.map((shop) => {
                  return (
                    <li key={shop.id} className="mb-3">
                      <StoreCard store={shop} />
                    </li>
                  );
                })
              ) : (
                <div className="container">
                  <div className="d-flex justify-content-center">
                    <div className="spinner-border my-5 me-4" />
                  </div>
                </div>
              )}
            </ul>
          </div>
        </div>
      </div>
    </>
  );
};

export default EditStoreSupply;
