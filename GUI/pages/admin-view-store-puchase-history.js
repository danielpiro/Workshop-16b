import AdminMenu from "../components/menus/menuAdmin";
import SearchBar from "../components/search-bar";
import axios from "axios";
import { useState, useEffect } from "react";
import Card from "../components/card";

const AdminViewStorePurchaes = () => {
  //const [purchases, setProducts] = useState([]);
  //const [isLoading, setIsLoading] = useState(true);
  const [searchValue, setSearchValue] = useState("");
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
  // useEffect(() => {
  //   const fetchApi = async () => {
  //     const response = await axios.get("https://fakestoreapi.com/users");
  //     setIsLoading(!isLoading);
  //     setProducts(response.data);
  //     //TODO: Add logic to check if the user has any permission!
  //     //setUserPermission("Admin/StoreOwner/StoreManager");
  //   };
  //   fetchApi();
  // }, []);
  const onChange = (e) => {
    setSearchValue(e.target.value);
  };

  const searchUsernamePurchases = (e) => {
    e.preventDefault();
    console.log(e.target.value);
  }

  return (
    <>
    <AdminMenu />
    <div className="card-header">
        <h3>View all store's purchases</h3>
    </div>
    <div className="container">
      <nav className="navbar navbar-expand-lg bg-secondery align-items-center justify-content-center rounded-3">
        <form className="row form-inline" style={{ display: "flex", width: "60%" }}>
          <div className="main-search-bar">
            <input
              className="form-control mr-sm-2"
              type="search"
              placeholder="Enter store name"
              aria-label="Search"
              onChange={onChange}
            />
            <div>
              <button className="btn btn-primary mr-lg-3" onClick={searchUsernamePurchases}>
                Search
              </button>
            </div>
          </div>
        </form>
      </nav>
    </div>      
      
    <div className="my-4" style={{ display: "flex", justifyContent: "center" }}>
      <h1>Purchases</h1>
    </div>
    {/* {!isLoading ? (
      <div style={{ display: "table", width: "100%" }}>
        <ul className="list-group" style={{ display: "table-cell" }}>
          {purchases
            .filter((val) =>
              val.title.toLowerCase().includes(searchValue.toLowerCase())
            )
            .map((product) => {
              return (
                <li className=" list-group-item" key={product.id}>
                  <Card
                    value={product.id}
                    image={product.image}
                    title={product.title}
                    description={product.description}
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
    </>
  );
};

export default AdminViewStorePurchaes;
