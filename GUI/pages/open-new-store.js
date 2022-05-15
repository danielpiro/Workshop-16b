import AdminMenu from "../components/menus/menuAdmin";
import StoreOwnerMenu from "../components/menus/menuStoreOwner";
import StoreManagerMenu from "../components/menus/menuStoreManager";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import axios from "axios";
import { useState, useEffect } from "react";
import Link from "next/link";

const OpenNewStore = () => {
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
      <span className="text-center my-5">
        <h3>Open New Store</h3>
      </span>
      <div className="container d-flex justify-content-center btn-group-vertical align-items-center">
        <div className="col-4">
          <label for="formGroupExampleInput" className="form-label fs-4">
            New Store Name
          </label>
          <input
            type="text"
            className="form-control"
            id="formGroupExampleInput"
            placeholder="Store Name"
          />
        </div>
        <div className="col-4">
          <label for="formGroupExampleInput2" className="form-label fs-4">
            Other info of store
          </label>
          <input
            type="text"
            className="form-control"
            id="formGroupExampleInput2"
            placeholder="Other info of store..."
          />
        </div>
      </div>
      <div className="d-flex justify-content-center my-3">
        <button className="btn btn-primary">Open New Store</button>
      </div>
    </>
  );
};

export default OpenNewStore;
