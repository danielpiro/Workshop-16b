import AdminMenu from "../components/menus/menuAdmin";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import axios from "axios";
import { useState, useEffect } from "react";
import Link from "next/link";

const CloseStore = () => {
  // const [isLoading, setIsLoading] = useState(true);
  // const [searchValue, setSearchValue] = useState("");
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
  // useEffect(() => {
  //   const fetchApi = async () => {
  //     const response = await axios.get("https://fakestoreapi.com/products");
  //     setIsLoading(!isLoading);
  //     setProducts(response.data);
  //     //TODO: Add logic to check if the user has any permission!
  //     //setUserPermission("Admin/StoreOwner/StoreManager");
  //   };
  //   fetchApi();
  // }, []);

  var menu;
  if (userPermission == "Admin") {
    menu = <AdminMenu />;
  } else if (userPermission == "Subscriber") {
    menu = <SubscriberMenu />;
  } else {
    menu = <GuestMenu />;
  }

  return (
    <>
      {menu}

      <div className="card-header">
        <h3>Close Store</h3>
      </div>

      <div className="container m-2">
        <div className="row">
          <div className="col">
            <input
              className="form-control mr-sm-2 m-2"
              type="search"
              placeholder="Enter storename"
              aria-label="Search"
            />
          </div>
          <div className="col m-2">
            <button className="btn btn-primary mr-lg-3">Search</button>
          </div>
        </div>
        <br />
        <div className="row m-2">
          {/*displaying all searched stores - will be displayed in cards that contain the button "Close Store"*/}
          <div className="card w-75 m-1">
            <div className="card-body">
              <div className="row">
                <div className="col">
                  <h5 className="card-title">Store A</h5>
                </div>
                <div className="col">
                  <p className="card-text">Store description...</p>
                </div>
                <div className="col">
                  <a href="#" className="btn btn-primary">
                    Close Store
                  </a>
                </div>
              </div>
            </div>
          </div>
          <div className="card w-75 m-1">
            <div className="card-body">
              <div className="row">
                <div className="col">
                  <h5 className="card-title">Store B</h5>
                </div>
                <div className="col">
                  <p className="card-text">Store description...</p>
                </div>
                <div className="col">
                  <a href="#" className="btn btn-primary">
                    Close Store
                  </a>
                </div>
              </div>
            </div>
          </div>
          <div className="card w-75 m-1">
            <div className="card-body">
              <div className="row">
                <div className="col">
                  <h5 className="card-title">Store A</h5>
                </div>
                <div className="col">
                  <p className="card-text">Store description...</p>
                </div>
                <div className="col">
                  <a href="#" className="btn btn-primary">
                    Close Store
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default CloseStore;
