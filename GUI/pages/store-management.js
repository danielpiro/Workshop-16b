import AdminMenu from "../components/menus/menuAdmin";
import StoreOwnerMenu from "../components/menus/menuStoreOwner";
import StoreManagerMenu from "../components/menus/menuStoreManager";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import axios from "axios";
import { useState, useEffect } from "react";
import Link from "next/link";

const StoreManagement = () => {
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
  
    return (
      <>
      {menu}
        <div
          className="my-4"
          style={{ display: "flex", justifyContent: "center" }}
        >
            <h1>Store Management</h1>
        </div>
        {/* TODO: 
            * Change links to the real pages! 
            * Add all relevant pages!
          */}
          {/* <Container>
            <Row className="justify-content-md-center">
                <Col xs lg="2"> */}
                    <div style={{ display: userPermission=="Admin" ? "block" : "none" }}>
                        <li className="edit-store-supply-button nav-item m-2">
                            <Link href="/edit-store-supply"> 
                                <a>Edit store's supply</a>
                            </Link>
                        </li>
                    </div>
                    <div style={{ display: userPermission=="Admin" ? "block" : "none" }}>
                        <li className="change-policy-button nav-item m-2">
                            <Link href="/change-policy"> 
                                <a>Change store's policy</a>
                            </Link>
                        </li>
                    </div>
                    <div style={{ display: userPermission=="Admin" ? "block" : "none" }}>
                        <li className="hire-owner-to-store-button nav-item m-2">
                            <Link href="/hire-owner-manager-to-store"> 
                                <a>Hire new Store Owner/Manager to store</a>
                            </Link>
                        </li>
                    </div>
                    <div style={{ display: userPermission=="Admin" ? "block" : "none" }}>
                        <li className="change-manager-permissions-button nav-item m-2">
                            <Link href="/dashboard"> 
                                <a>Change store's manager permissions</a>
                            </Link>
                        </li>
                    </div>
                    <div style={{ display: userPermission=="Admin" ? "block" : "none" }}>
                        <li className="close-store-button nav-item m-2">
                            <Link href="/dashboard"> 
                                <a>Close a store</a>
                            </Link>
                        </li>
                    </div>
                    <div style={{ display: userPermission=="Admin" ? "block" : "none" }}>
                        <li className="officials-info-button nav-item m-2">
                            <Link href="/dashboard"> 
                                <a>Display officials info in store</a>
                            </Link>
                        </li>
                    </div>
                    <div style={{ display: userPermission=="Admin" ? "block" : "none" }}>
                        <li className="purchases-info-button nav-item m-2">
                            <Link href="/dashboard"> 
                                <a>Display purchases info in store</a>
                            </Link>
                        </li>
                    </div>
                {/* </Col>
                <Col xs lg="2">
                    {editStore}
                </Col>
            </Row>
        </Container> */}


        {/* {!isLoading ? (
          <div style={{ display: "table", width: "100%" }}>
            <h2>See All History</h2>
          </div>
        ) : (
          <div className="container h-100 my-6">
            <div className="row align-items-center justify-content-center">
              <div className="spinner-border" />
            </div>
          </div>
        )} */}

        <div class="modal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Modal title</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>Modal body text goes here.</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            </div>
        </div>
        </div>
      </>
    );
  };
  
  export default StoreManagement;