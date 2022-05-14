import AdminMenu from "../components/menus/menuAdmin";
import StoreOwnerMenu from "../components/menus/menuStoreOwner";
import StoreManagerMenu from "../components/menus/menuStoreManager";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import axios from "axios";
import { useState, useEffect } from "react";
import Link from "next/link";

const HireOwnerToStore = () => {
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
            <h3>Hire new owner to a store</h3>
        </div>

        <div className="container">
            <div className="row" style={{ display: "flex", width: "50%" }}>
                <input
                    className="form-control mr-sm-2 m-2"
                    type="search"
                    placeholder="Enter username of the new owner/manager"
                    aria-label="Search"
                />
            </div>
            <div className="row" style={{ display: "flex", width: "50%" }}>
                <input
                    className="form-control mr-sm-2 m-2"
                    type="search"
                    placeholder="Enter the store name"
                    aria-label="Search"
                />
            </div>
            <div className="dropdown m-1">
                <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                    Owner/Manager
                </button>
                <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    <li><a className="dropdown-item" href="#">Manager</a></li>
                    <li><a className="dropdown-item" href="#">Owner</a></li>
                </ul>
            </div>
            <br />
            <div className="row m-1" style={{ display: "flex", width: "15%" }}>
                <button className="btn btn-primary mr-lg-3">
                    Search
                </button>
            </div>    
        </div>
        
        </>
    );
};

export default HireOwnerToStore;