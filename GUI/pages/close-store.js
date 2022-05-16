import AdminMenu from "../components/menus/menuAdmin";
import StoreOwnerMenu from "../components/menus/menuStoreOwner";
import StoreManagerMenu from "../components/menus/menuStoreManager";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import axios from "axios";
import { useState, useEffect } from "react";
import createNotification from "../components/norification";
import { useRouter } from "next/router";
import Footer from "../components/footer";

const CloseStore = () => {
    const router = useRouter();
    const [isLoading, setIsLoading] = useState(true);
    const [stores, setStores] = useState([]);
    const [selectedStore, setSelectedStore] = useState({
        storeId: "",
        storename: "",
    });
    const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
    
    const onCloseStore = (e) => {
        e.preventDefault();
        
        setSelectedStore((prevState) => ({ ...prevState, storeId: e.data,})) //TODO: Fix setting these params!
        setSelectedStore((prevState) => ({ ...prevState, storename: e.data,}))

        console.log(selectedStore.storeId); 
        console.log(selectedStore.storename);

        if(selectedStore.storeId != "" && selectedStore.storename != ""){
            const isClosed = axios.post(`store/${selectedStore.storeId}/${selectedStore.storename}`); 
            // console.log(isClosed);
            if(isClosed){
                createNotification("success", "Closed store successfully", () =>
                router.push("/dashboard")
                )();
            }
            else{
                createNotification("error", "failure closing store!")();
            }     
        }
        else{
            createNotification("error", "storename or storeID was not valid, please try again")();
        }
    }

    useEffect(() => {
        const fetchPermission = async () => {
          const response = await axios.get("users/getUserPermission");
          setUserPermission(response.data);
        };
        fetchPermission();
    }, []);

    useEffect(() => {
        const fetchApi = async () => {
          const response = await axios.get("https://dummyjson.com/products");
          setIsLoading(!isLoading);
          const { data } = response;
          setStores(data.products);
        };
        fetchApi();
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
            <h3>Close Store</h3>
        </div>

        <div className="container m-2">
            <div className="row">
                <div className="col">
                    <input
                        className="form-control mr-sm-2 m-2"
                        type="search"
                        placeholder="Enter storename"
                        aria-label="Search"
                    />
                </div>
                <div className="col m-2">
                    <button className="btn btn-primary mr-lg-3">
                        Search
                    </button>
                </div>
            </div>
            <br/>
            <div className="row m-2">
                {/*displaying all searched stores - will be displayed in cards that contain the button "Close Store"*/}
                {!isLoading ? (
                    <div style={{ display: "table", width: "100%" }}>
                    <ul className="list-group-item" style={{ display: "table-cell" }}> 
                        <div className="row m-3">
                            <div className="col">
                                <h3 className="card-title">Store ID</h3>
                            </div>
                            <div className="col">
                                <h3 className="card-desc">Store Name</h3>
                            </div>
                            <div className="col">
                                <h3 className="card-price">Select Store</h3>
                            </div>
                        </div>    
                        {stores.map((store) => {
                        return (
                            <li className=" list-group-item" key={store.id}>
                            <div className="card w-100 m-1">
                                <div className="card-body">
                                    <div className="row">
                                        <div className="col">
                                            <h5 className="card-title">{store.id}</h5>
                                        </div>
                                        <div className="col">
                                            <p className="card-desc">{store.title}</p>
                                        </div>
                                        <div className="row m-1" style={{ display: "flex", width: "20%" }}>
                                            <button className="btn btn-primary mr-lg-3" onClick={onCloseStore}>
                                                Close Store
                                            </button>
                                        </div>
                                    </div>    
                                </div>
                            </div>
                            </li>
                        );
                        })}
                    </ul>
                    </div>
                ) : (
                    <div className="container h-100 my-6">
                    <div className="row align-items-center justify-content-center">
                        <div className="spinner-border" />
                    </div>
                    </div>
                )}
      
            </div>
        </div>
        <Footer />
        </>
    );
};

export default CloseStore;