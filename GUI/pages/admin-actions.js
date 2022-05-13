import AdminMenu from "../components/menus/menuAdmin";
import StoreOwnerMenu from "../components/menus/menuStoreOwner";
import StoreManagerMenu from "../components/menus/menuStoreManager";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import axios from "axios";
import { useState, useEffect } from "react";
import Link from "next/link";
import { useRouter } from "next/router";

const AdminActions = () => {
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

    // const router = useRouter();
    // const onClick = (e) => {
    //   e.preventDefault();
    //   //need to do api to backend and get guestid
    //   router.push("/dashboard");
    // };
  
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
  
    /* TODO: 
     * Change links to the real pages! 
     * Add store history page
     * Add user history page
     */
    return (
      <>
      {menu}
        <div className="my-4" style={{ display: "flex", justifyContent: "center" }}>
            <h1>Admin Actions</h1>
        </div>
        <div className="m-5">
            <div className="row align-items-center justify-content-center">
                <li className="see-user-purchase-history-button nav-item m-2">
                    <Link href="/admin-view-user-puchase-history"> 
                        <a>See all purchase history of specific user</a>
                    </Link>
                </li>
                <li className="see-store-purchase-history-button nav-item m-2">
                    <Link href="/admin-view-store-puchase-history"> 
                        <a>See all purchase history of specific store</a>
                    </Link>
                </li>
            </div>
        </div>
        
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
      </>
    );
  };
  
  export default AdminActions;