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

  const onChangeStoreName = (event) => {
    //props.setSearchValue(event.target.value);
  };

  const onChangeProductName = (event) => {
    //props.setSearchValue(event.target.value);
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

    <div className="card-header">
        <h3>Edit store's supply</h3>
    </div>
       
    <div className="container">
      <nav className="navbar navbar-expand-lg bg-secondery align-items-left rounded-3">
        <form className="row form-inline" style={{ display: "flex", width: "50%" }}>
            <input
              className="form-control mr-sm-2 m-2"
              type="search"
              placeholder="Enter store name"
              aria-label="Search"
              onChange={onChangeStoreName}
            />
            
            <input
              className="form-control mr-sm-2 m-2"
              type="search"
              placeholder="Enter product name"
              aria-label="Search"
              onChange={onChangeProductName}
            />
            <div>
              <button className="btn btn-primary mr-lg-3">
                Search
              </button>
            </div>
        </form>
      </nav>
    </div>      
      
    <br></br>  
    <div className="container">
      <nav className="navbar navbar-expand-lg bg-secondery align-items-left rounded-3">
        <form className="row form-inline" style={{ display: "flex", width: "30%" }}>
            <input
              className="form-control mr-sm-2 m-2"
              type="search"
              placeholder="Enter new product name"
              aria-label="Search"
              onChange={onChangeStoreName}
            />
            
            <input
              className="form-control mr-sm-2 m-2"
              type="search"
              placeholder="Enter new product price"
              aria-label="Search"
              onChange={onChangeProductName}
            />

            <input
              className="form-control mr-sm-2 m-2"
              type="search"
              placeholder="Enter new product quantity"
              aria-label="Search"
              onChange={onChangeProductName}
            />
            <div>
              <button className="btn btn-primary mr-lg-3">
                Edit
              </button>
            </div>
        </form>
      </nav>
    </div>
    </>
  );
};

export default EditStoreSupply;
