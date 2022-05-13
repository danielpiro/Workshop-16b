import AdminMenu from "../components/menus/menuAdmin";
import StoreOwnerMenu from "../components/menus/menuStoreOwner";
import StoreManagerMenu from "../components/menus/menuStoreManager";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import SearchBar from "../components/search-bar";
import axios from "axios";
import { useState, useEffect } from "react";

const EditStoreSupply = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [searchValue, setSearchValue] = useState("");
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
  useEffect(() => {
    const fetchApi = async () => {
      const response = await axios.get("https://fakestoreapi.com/products");
      setIsLoading(!isLoading);
      setProducts(response.data);
      //TODO: Add logic to check if the user has any permission!
      //setUserPermission("Admin/StoreOwner/StoreManager");
    };
    fetchApi();
  }, []);

  const onChange = (event) => {
    props.setSearchValue(event.target.value);
  };

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
       
      <div className="row justify-content-center align-items-center" style={{display: "inline-block"}}>
        <h4>Search products in store to edit:</h4> 
        <SearchBar setSearchValue={setSearchValue} />
        <button>Search</button> 
      </div>
      
     
      {/*
      <div className="container">
        <h7>Product Name: </h7>
        <nav className="navbar navbar-expand-lg bg-secondery rounded-3">
          <form className="row form-inline" style={{ display: "flex", width: "40%" }}>
            <div className="main-search-bar">
              <input className="form-control mr-sm-2" type="search"
                placeholder={setSearchValue} aria-label="Search" onChange={onChange}/>
            </div>
          </form>
        </nav>

        <h7>Product Price: </h7>
        <nav className="navbar navbar-expand-lg bg-secondery rounded-3">
          <form className="row form-inline" style={{ display: "flex", width: "40%" }}>
            <div className="main-search-bar">
              <input className="form-control mr-sm-2" type="search"
                placeholder={setSearchValue} aria-label="Search" onChange={onChange}/>
            </div>
          </form>
        </nav>
        <h7>Product Quantity: </h7>
        <nav className="navbar navbar-expand-lg bg-secondery rounded-3">
          <form className="row form-inline" style={{ display: "flex", width: "40%" }}>
            <div className="main-search-bar">
              <input className="form-control mr-sm-2" type="search"
                placeholder={setSearchValue} aria-label="Search" onChange={onChange}/>
            </div>
          </form>
        </nav>
    </div> */}
    </>
  );
};

export default EditStoreSupply;
