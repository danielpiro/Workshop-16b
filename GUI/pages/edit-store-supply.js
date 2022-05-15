import AdminMenu from "../components/menus/menuAdmin";
import StoreOwnerMenu from "../components/menus/menuStoreOwner";
import StoreManagerMenu from "../components/menus/menuStoreManager";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import { useState, useEffect } from "react";
import api from "../components/api";
import StoreCard from "../components/store-card";

const EditStoreSupply = () => {
  const [store, setStore] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [searchValue, setSearchValue] = useState("");
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!

  const onSearch = (e) => {
    e.preventDefault();
    setIsLoading(!isLoading);
    api.get("/search/store", { params: { name: searchValue } }).then((res) => {
      if (res.status === 200) {
        setStore([res.data]);
        setIsLoading(!isLoading);
      }
    });
  };

  const onChange = (e) => {
    e.preventDefault();
    setSearchValue(e.target.value);
  };

  var menu;
  if (userPermission == "Admin") {
    menu = <AdminMenu />;
  } else if (userPermission == "Owner") {
    menu = <StoreOwnerMenu />;
  } else if (userPermission == "Manager") {
    menu = <StoreManagerMenu />;
  } else if (userPermission == "Subscriber") {
    menu = <SubscriberMenu />;
  } else {
    menu = <GuestMenu />;
  }

  return (
    <>
      {menu}
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
        <ul>
          <StoreCard />
          {!isLoading ? (
            store.map((shop) => {
              return (
                <div>
                  <li key={shop.id}>{shop}</li>
                </div>
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
    </>
  );
};

export default EditStoreSupply;
