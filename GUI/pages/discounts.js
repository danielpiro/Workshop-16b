import AdminMenu from "../components/menus/menuAdmin";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import axios from "axios";
import { useState, useEffect } from "react";
import Footer from "../components/footer";

const Discounts = () => {
    const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
    
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

    return(
        <>
        {menu}
        <div className="card-header">
            <h3>Edit store's discounts</h3>
        </div>
        <Footer/>
        </>
    );
};

export default Discounts;