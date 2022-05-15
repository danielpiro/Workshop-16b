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
    const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
    
    // const router = useRouter();
    // const onClickOfUser = (e) =>{
    //   e.preventDefault();
    // }

    // const onClickOfStore = (e) =>{
    //   e.preventDefault();
    // }
  
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
      {menu} {/*Should be Admin Only!!!*/}
        <div className="my-4" style={{ display: "flex", justifyContent: "center" }}>
            <h1>Admin Actions</h1>
        </div>

        <div class="card">
          <div class="card-body">
            <Link href="/admin-view-user-puchase-history"> 
                <a>See all purchase history of specific user</a>
            </Link>
          </div>
        </div>
        <br/>
        <div class="card">
          <div class="card-body">
            <Link href="/admin-view-store-puchase-history" disabled> 
                <a>See all purchase history of specific store</a>
            </Link>
          </div>
        </div>
      </>
    );
  };
  
export default AdminActions;