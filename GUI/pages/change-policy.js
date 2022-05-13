import AdminMenu from "../components/menus/menuAdmin";
import StoreOwnerMenu from "../components/menus/menuStoreOwner";
import StoreManagerMenu from "../components/menus/menuStoreManager";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import axios from "axios";
import { useState, useEffect } from "react";
import Link from "next/link";

// import Container from "react-bootstrap/Container"
// import Row from "react-bootstrap/Row"
// import Col from "react-bootstrap/Col"

const ChangePolicy = () => {
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

        <div class="container">
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
                {/* <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" id="flexSwitchCheckDisabled" disabled />
                <label class="form-check-label" for="flexSwitchCheckDisabled">Disabled switch checkbox input</label>
                </div>
                <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" id="flexSwitchCheckCheckedDisabled" checked disabled />
                <label class="form-check-label" for="flexSwitchCheckCheckedDisabled">Disabled checked switch checkbox input</label>
                </div> */}
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" checked/>
                    <label class="form-check-label" for="flexSwitchCheckDefault">Permission #1</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckChecked" />
                    <label class="form-check-label" for="flexSwitchCheckChecked">Permission #2</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" checked/>
                    <label class="form-check-label" for="flexSwitchCheckDefault">Permission #3</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckChecked" />
                    <label class="form-check-label" for="flexSwitchCheckChecked">Permission #4</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" checked/>
                    <label class="form-check-label" for="flexSwitchCheckDefault">Permission #5</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckChecked" />
                    <label class="form-check-label" for="flexSwitchCheckChecked">Permission #6</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" checked/>
                    <label class="form-check-label" for="flexSwitchCheckDefault">Permission #7</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckChecked" />
                    <label class="form-check-label" for="flexSwitchCheckChecked">Permission #8</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" checked/>
                    <label class="form-check-label" for="flexSwitchCheckDefault">Permission #9</label>
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckChecked" />
                    <label class="form-check-label" for="flexSwitchCheckChecked">Permission #10</label>
                </div>

                <button className="btn btn-primary mr-lg-3" style={{width: "20%"}}>
                    Update Permissions
                </button>
            </div>
        </div>

        </>
    );
};

export default ChangePolicy;