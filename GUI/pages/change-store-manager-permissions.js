import AdminMenu from "../components/menus/menuAdmin";
import StoreOwnerMenu from "../components/menus/menuStoreOwner";
import StoreManagerMenu from "../components/menus/menuStoreManager";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import axios from "axios";
import { useState, useEffect } from "react";
import Link from "next/link";

const ChangeStoreManagerPermissions = () => {
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
            <h3>Change Store Manager's Permissions</h3>
        </div>
        <div className="container">
            <div className="row">
                <div className="col">
                    <input
                        className="form-control mr-sm-2 m-2"
                        type="search"
                        placeholder="Enter username"
                        aria-label="Search"
                    />
                </div>
                <div className="col m-2">
                    <button className="btn btn-primary mr-lg-3">
                        Search
                    </button>
                </div>
            </div>

            <div className="row m-3">
                {/* <div className="form-check form-switch">
                <input className="form-check-input" type="checkbox" id="flexSwitchCheckDisabled" disabled />
                <label className="form-check-label" for="flexSwitchCheckDisabled">Disabled switch checkbox input</label>
                </div>
                <div className="form-check form-switch">
                <input className="form-check-input" type="checkbox" id="flexSwitchCheckCheckedDisabled" checked disabled />
                <label className="form-check-label" for="flexSwitchCheckCheckedDisabled">Disabled checked switch checkbox input</label>
                </div> */}
                <div className="form-check form-switch">
                    <input className="form-check-input" type="checkbox" id="flexSwitchCheckDefault" checked/>
                    <label className="form-check-label" for="flexSwitchCheckDefault">Permission #1</label>
                </div>
                <div className="form-check form-switch">
                    <input className="form-check-input" type="checkbox" id="flexSwitchCheckChecked" />
                    <label className="form-check-label" for="flexSwitchCheckChecked">Permission #2</label>
                </div>
                <div className="form-check form-switch">
                    <input className="form-check-input" type="checkbox" id="flexSwitchCheckDefault" checked/>
                    <label className="form-check-label" for="flexSwitchCheckDefault">Permission #3</label>
                </div>
                <div className="form-check form-switch">
                    <input className="form-check-input" type="checkbox" id="flexSwitchCheckChecked" />
                    <label className="form-check-label" for="flexSwitchCheckChecked">Permission #4</label>
                </div>
                <div className="form-check form-switch">
                    <input className="form-check-input" type="checkbox" id="flexSwitchCheckDefault" checked/>
                    <label className="form-check-label" for="flexSwitchCheckDefault">Permission #5</label>
                </div>
                <div className="form-check form-switch">
                    <input className="form-check-input" type="checkbox" id="flexSwitchCheckChecked" />
                    <label className="form-check-label" for="flexSwitchCheckChecked">Permission #6</label>
                </div>
                <div className="form-check form-switch">
                    <input className="form-check-input" type="checkbox" id="flexSwitchCheckDefault" checked/>
                    <label className="form-check-label" for="flexSwitchCheckDefault">Permission #7</label>
                </div>
                <div className="form-check form-switch">
                    <input className="form-check-input" type="checkbox" id="flexSwitchCheckChecked" />
                    <label className="form-check-label" for="flexSwitchCheckChecked">Permission #8</label>
                </div>
                <div className="form-check form-switch">
                    <input className="form-check-input" type="checkbox" id="flexSwitchCheckDefault" checked/>
                    <label className="form-check-label" for="flexSwitchCheckDefault">Permission #9</label>
                </div>
                <div className="form-check form-switch">
                    <input className="form-check-input" type="checkbox" id="flexSwitchCheckChecked" />
                    <label className="form-check-label" for="flexSwitchCheckChecked">Permission #10</label>
                </div>

                <button className="btn btn-primary mr-lg-3" style={{width: "20%"}}>
                    Update Permissions
                </button>
            </div>
        </div>

        </>
    );
};

export default ChangeStoreManagerPermissions;