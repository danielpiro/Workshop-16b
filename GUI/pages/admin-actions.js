import AdminMenu from "../components/menus/menuAdmin";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import axios from "axios";
import { useState, useEffect } from "react";
import Link from "next/link";
import { useRouter } from "next/router";
import Footer from "../components/footer";

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

        <div className="card">
          <div className="card-body">
            <Link href="/admin-view-user-puchase-history"> 
                <a>See all purchase history of specific user</a>
            </Link>
          </div>
        </div>
        <br/>
        <div className="card">
          <div className="card-body">
            <Link href="/admin-view-store-puchase-history"> 
                <a>See all purchase history of specific store</a>
            </Link>
          </div>
        </div>
        <br/>
        <div className="card">
          <div className="card-body">
            <Link href="/unregister-user"> 
                <a>Unregister user</a>
            </Link>
          </div>
        </div>
        <Footer/>
      </>
    );
  };
  
export default AdminActions;