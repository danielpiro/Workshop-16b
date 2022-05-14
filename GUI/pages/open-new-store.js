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
    if (userPermission == "Admin"){
        menu = <AdminMenu />;
    }
    else if (userPermission == "Owner"){
        menu = <StoreOwnerMenu />;
    }
    else if (userPermission == "Manager"){
        menu = <StoreManagerMenu />;
    }
    else if (userPermission == "Subscriber"){
        menu = <SubscriberMenu />;
    }
    else{
        menu = <GuestMenu />;
    }

    return(
        <>
        {menu}

        <div className="card-header">
            <h3>Open New Store</h3>
        </div>

        <div className="container m-2">
            <br />
            <div className="mb-3">
                <label for="formGroupExampleInput" className="form-label fs-4">New Store Name:</label>
                <input type="text" className="form-control" id="formGroupExampleInput" placeholder="Store Name" />
            </div>
            <div className="mb-3">
                <label for="formGroupExampleInput2" className="form-label fs-4">Other info of store:</label>
                <input type="text" className="form-control" id="formGroupExampleInput2" placeholder="Other info of store..." />
            </div>

            
            <button className="btn btn-primary mr-lg-3 m-2" style={{width: "20%"}}>
                Open New Store
            </button>
        </div>

        </>
    );
};

export default OpenNewStore;