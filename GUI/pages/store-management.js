import AdminMenu from "../components/menus/menuAdmin";
import StoreOwnerMenu from "../components/menus/menuStoreOwner";
import StoreManagerMenu from "../components/menus/menuStoreManager";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import axios from "axios";
import { useState, useEffect } from "react";
import Link from "next/link";

const StoreManagement = () => {
    const [userPermission, setUserPermission] = useState("Admin"); //= useState(""); //TODO: Need to change to Guest when logic is ready!
    
    useEffect(() => {
        const fetchPermission = async () => {
          const response = await axios.get("users/getUserPermission");
          setIsLoading(!isLoading);
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
  
    return (
      <>
      {menu}
        <div
          className="my-4"
          style={{ display: "flex", justifyContent: "center" }}
        >
            <h1>Store Management</h1>
        </div>    
        <div className="containter">
            <div className="row">
                <div className="col">
                    <div className="card" style={{ display: userPermission=="Admin" ? "block" : "none" }}>
                        <div className="card-body">
                            <Link href="/edit-store-supply"> 
                                <a>Edit store's supply/products</a>
                            </Link>
                        </div>
                    </div>
                </div>
                <div className="col">
                    <div className="card" style={{ display: userPermission=="Admin" ? "block" : "none" }}>
                        <div className="card-body">
                            <Link href="/change-policy"> 
                                <a>Change store's policy</a>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
            <br/>
            <div className="row">
                <div className="col">
                    <div className="card" style={{ display: userPermission=="Admin" ? "block" : "none" }}>
                        <div className="card-body">
                            <Link href="/hire-owner-manager-to-store"> 
                                <a>Hire new Store Owner/Manager to store</a>
                            </Link>
                        </div>
                    </div>
                </div>
                <div className="col">
                    <div className="card" style={{ display: userPermission=="Admin" ? "block" : "none" }}>
                        <div className="card-body">
                            <Link href="/change-store-manager-permissions"> 
                                <a>Change store's manager permissions</a>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
            <br/>
            <div className="row">
                <div className="col">
                    <div className="card" style={{ display: userPermission=="Admin" ? "block" : "none" }}>
                        <div className="card-body">
                            <Link href="/display-store-purchases"> 
                                <a>Display purchases of store</a>
                            </Link>
                        </div>
                    </div>
                </div>
                <div className="col">
                    <div className="card" style={{ display: userPermission=="Admin" ? "block" : "none" }}>
                        <div className="card-body">
                            <Link href="/close-store"> 
                                <a>Close a store</a>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
            <br/>
            <div className="row">
                <div className="col">
                    <div className="card" style={{ display: userPermission=="Admin" ? "block" : "none" }}>
                        <div className="card-body">
                            <Link href="/display-store-officials"> 
                                <a>Display officials info in store</a>
                            </Link>
                        </div>
                    </div>
                </div>
                <div className="col">
                    <div className="card" style={{ display: "none" }}>
                        <div className="card-body">
                            <Link href="/..."> 
                                <a>...</a>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
            <br/>
        </div>

        <div className="modal" tabindex="-1">
        <div className="modal-dialog">
            <div className="modal-content">
            <div className="modal-header">
                <h5 className="modal-title">Modal title</h5>
                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div className="modal-body">
                <p>Modal body text goes here.</p>
            </div>
            <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" className="btn btn-primary">Save changes</button>
            </div>
            </div>
        </div>
        </div>
      </>
    );
  };
  
  export default StoreManagement;
