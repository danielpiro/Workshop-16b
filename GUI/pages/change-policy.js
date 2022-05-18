import AdminMenu from "../components/menus/menuAdmin";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import axios from "axios";
import { useState, useEffect } from "react";
import Link from "next/link";

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
            <h3>Change Policy</h3>
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

            <div className="row m-2">
                {/*displaying all searched stores - will be displayed in cards that contain the button "Close Store"*/}
                <div className="card w-75 m-1">
                    <div className="card-body">
                        <div className="row">
                            <div className="col">
                                <h5 className="card-title">Store A</h5>
                            </div>
                            <div className="col">
                                <p className="card-text">Store description...</p>
                            </div>
                            <div className="col">
                                <a href="#" className="btn btn-primary">Close Store</a>
                            </div>
                        </div>    
                    </div>
                </div>
                <div className="card w-75 m-1">
                    <div className="card-body">
                        <div className="row">
                            <div className="col">
                                <h5 className="card-title">Store B</h5>
                            </div>
                            <div className="col">
                                <p className="card-text">Store description...</p>
                            </div>
                            <div className="col">
                                <a href="#" className="btn btn-primary">Close Store</a>
                            </div>
                        </div>    
                    </div>
                </div>
                <div className="card w-75 m-1">
                    <div className="card-body">
                        <div className="row">
                            <div className="col">
                                <h5 className="card-title">Store A</h5>
                            </div>
                            <div className="col">
                                <p className="card-text">Store description...</p>
                            </div>
                            <div className="col">
                                <a href="#" className="btn btn-primary">Close Store</a>
                            </div>
                        </div>    
                    </div>
                </div>
            </div>

            <div className="row">
                <div className="col">
                    <div className="dropdown m-1">
                        <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                            Policy Type
                        </button>
                        <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                            <li><a className="dropdown-item" href="#">Total cart price</a></li>
                            <li><a className="dropdown-item" href="#">Quantity in cart</a></li>
                            <li><a className="dropdown-item" href="#">Product should be in cart</a></li>
                            <li><a className="dropdown-item" href="#">Product should be in cart with minimum quantity</a></li>
                            <li><a className="dropdown-item" href="#">Day Of Month</a></li>
                            <li><a className="dropdown-item" href="#">Day Of Week</a></li>
                            <li><a className="dropdown-item" href="#">Hour Of Day</a></li>
                            <li><a className="dropdown-item" href="#">Specific user</a></li>
                            <li><a className="dropdown-item" href="#">Age</a></li>
                        </ul>
                    </div>    
                </div>

                <div className="col">
                    <div className="dropdown m-1">
                        <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                            Product Type
                        </button>
                        <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                            <li><a className="dropdown-item" href="#">Drinks</a></li>
                            <li><a className="dropdown-item" href="#">Food</a></li>
                            <li><a className="dropdown-item" href="#">Others</a></li>
                        </ul>
                    </div>    
                </div>

                <div className="col">
                    <div className="dropdown m-1">
                        <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                            Product
                        </button>
                        <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                            <li><a className="dropdown-item" href="#">Product A</a></li>
                            <li><a className="dropdown-item" href="#">Product B</a></li>
                        </ul>
                    </div>    
                </div>

                <div className="col">
                    <div className="dropdown m-1">
                        <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                            Allowing/Restricting
                        </button>
                        <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                            <li><a className="dropdown-item" href="#">Allowing</a></li>
                            <li><a className="dropdown-item" href="#">Restricting</a></li>
                        </ul>
                    </div>    
                </div>

                <div className="row">
                    <div className="col">
                        <br />
                        <div className="input-group m-2">
                            <span className="input-group-text">Additional info to policy changes</span>
                            <textarea className="form-control" aria-label="Additional info"></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <button className="btn btn-primary mr-lg-3 m-2" style={{width: "20%"}}>
                Update Policy
            </button>
        </div>

        </>
    );
};

export default ChangePolicy;