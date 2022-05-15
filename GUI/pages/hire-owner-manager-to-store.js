import AdminMenu from "../components/menus/menuAdmin";
import StoreOwnerMenu from "../components/menus/menuStoreOwner";
import StoreManagerMenu from "../components/menus/menuStoreManager";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import axios from "axios";
import { useState, useEffect } from "react";

const HireOwnerToStore = () => {
    const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
    const [newOfficialInput, setNewOfficialInput] = useState({
        username: "",
        storename: "",
        ownerORmanager: ""
    });
    
    const onHiringOfficial = (e) => {
        e.preventDefault();
        // console.log(newOfficialInput.username);
        // console.log(newOfficialInput.storename);
        // console.log(newOfficialInput.ownerORmanager);
        if(newOfficialInput.username != "" && newOfficialInput.storename != "" && newOfficialInput.ownerORmanager != ""){
            if(newOfficialInput.ownerORmanager == "Owner"){
                const isOpened = axios.post(`owner/${openNewStoreInput.storeOwnerUsername}/${openNewStoreInput.storename}`); 
            }
            if(newOfficialInput.ownerORmanager == "Manager"){
                const isOpened = axios.post(`manager/${openNewStoreInput.storeOwnerUsername}/${openNewStoreInput.storename}`); 
            } 
            
            if(isOpened){
                createNotification("success", "Create new store successfully", () =>
                router.push("/dashboard")
                )();
            }
            else{
                createNotification("error", "failure opening new store!")();
            }     
        }
        else{
            createNotification("error", "storename or username was not valid, please try again")();
        }
    }

    useEffect(() => {
        const fetchPermission = async () => {
          const response = await axios.get("users/getUserPermission");
          setUserPermission(response.data);
        };
        fetchPermission();
    }, []);

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
                    value={newOfficialInput.username}
                    onChange={(e) =>
                        setNewOfficialInput((prevState) => ({
                            ...prevState,
                            username: e.target.value,
                        }))
                    }
                />
            </div>
            <div className="row" style={{ display: "flex", width: "50%" }}>
                <input
                    className="form-control mr-sm-2 m-2"
                    type="search"
                    placeholder="Enter the store name"
                    aria-label="Search"
                    value={newOfficialInput.storename}
                    onChange={(e) =>
                        setNewOfficialInput((prevState) => ({
                            ...prevState,
                            storename: e.target.value,
                        }))
                    }
                />
            </div>
            <div className="dropdown m-1">
                <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                    Owner/Manager
                </button>
                <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1" value={newOfficialInput.ownerORmanager}
                    onChange={(e) => setNewOfficialInput((prevState) => ({
                                        ...prevState,
                                        ownerORmanager: e.target.value,
                        }))
                    }>
                    <li><a className="dropdown-item" href="#">Manager</a></li>
                    <li><a className="dropdown-item" href="#">Owner</a></li>
                </ul>
            </div>
            <br />
            <div className="row m-1" style={{ display: "flex", width: "15%" }}>
                <button className="btn btn-primary mr-lg-3" onClick={onHiringOfficial}>
                    Hire new Manager/Owner
                </button>
            </div>    
        </div>
        
        </>
    );
};

export default HireOwnerToStore;