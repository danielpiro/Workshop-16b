import AdminMenu from "../components/menus/menuAdmin";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import axios from "axios";
import { useState, useEffect } from "react";

const EditStoreSupply = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [searchValue, setSearchValue] = useState({
    storename: "",
    productname: "",
  });
  const [editValues, setEditValues] = useState({
    productname: "",
    productprice: "",
    productquantity: "",
  });
  const [hideEditor, setHideEditor] = useState(true);
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!

  useEffect(() => {
    const fetchPermission = async () => {
      const response = await axios.get("users/getUserPermission");
      setIsLoading(!isLoading);
      setUserPermission(response.data);
    };
    fetchPermission();
  }, []);

  useEffect(() => {
    const fetchApi = async () => {
      const response = await axios.get("https://fakestoreapi.com/products"); //TODO: Need to get all products!
      setIsLoading(!isLoading);
      setProducts(response.data);
    };
    fetchApi();
  }, []);

  const searchingProduct = (e) => {
    e.preventDefault();
    if(searchValue.storename != "" && searchValue.productname != ""){
      setHideEditor(false);
    }
    //products...
  };

  const onEdit = (e) => {
    e.preventDefault();
    //props.setSearchValue(event.target.value);
  };

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
              value={searchValue.storename}
              onChange={(e) =>
                  setSearchValue((prevState) => ({
                    ...prevState,
                    storename: e.target.value,
                  }))
              }
            />
            
            <input
              className="form-control mr-sm-2 m-2"
              type="search"
              placeholder="Enter product name"
              aria-label="Search"
              value={searchValue.storename}
              onChange={(e) =>
                  setSearchValue((prevState) => ({
                    ...prevState,
                    productname: e.target.value,
                  }))
              }
            />
            <div>
              <button className="btn btn-primary mr-lg-3" onClick={searchingProduct}>
                Search
              </button>
            </div>
        </form>
      </nav>
    </div>      
    
    {/* TODO: Add card to each pruduct that in the results! */}
    {/* {!isLoading ? (
        <div style={{ display: "table", width: "100%" }}>
          <ul className="list-group-dashboard">
            {products.map((product) => {
              return (
                <li className=" list-group-item" key={product.id}>
                  <Card
                    value={product.id}
                    image={product.images[0]}
                    title={product.title}
                    category={product.category}
                    description={product.description}
                    price={product.price}
                    discount={product.discountPercentage}
                  />
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
      )} */}

    <br></br>  
    <div className="container" style={{ display: hideEditor ? "none" : "block"}}>
      <nav className="navbar navbar-expand-lg bg-secondery align-items-left rounded-3">
        <form className="row form-inline" style={{ display: "flex", width: "30%" }}>
            <input
              className="form-control mr-sm-2 m-2"
              type="search"
              placeholder="Enter product name"
              aria-label="Search"
              value={searchValue.productname}
              onChange={(e) =>
                  setEditValues((prevState) => ({
                    ...prevState,
                    productname: e.target.value,
                  }))
              }
            />
            
            <input
              className="form-control mr-sm-2 m-2"
              type="search"
              placeholder="Enter product price"
              aria-label="Search"
              value={searchValue.productprice}
              onChange={(e) =>
                  setEditValues((prevState) => ({
                    ...prevState,
                    productprice: e.target.value,
                  }))
              }
            />

            <input
              className="form-control mr-sm-2 m-2"
              type="search"
              placeholder="Enter product quantity"
              aria-label="Search"
              value={searchValue.productquantity}
              onChange={(e) =>
                  setEditValues((prevState) => ({
                    ...prevState,
                    productquantity: e.target.value,
                  }))
              }
            />
            <div>
              <button className="btn btn-primary mr-lg-3" onClick={onEdit}>
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
