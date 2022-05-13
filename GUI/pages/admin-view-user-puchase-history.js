import AdminMenu from "../components/menus/menuAdmin";
import SearchBar from "../components/search-bar";
import axios from "axios";
import { useState, useEffect } from "react";
import Card from "../components/card";

const AdminViewUserPurchaes = () => {
  //const [purchases, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [searchValue, setSearchValue] = useState("");
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
  useEffect(() => {
    const fetchApi = async () => {
      const response = await axios.get("https://fakestoreapi.com/users");
      setIsLoading(!isLoading);
      setProducts(response.data);
      //TODO: Add logic to check if the user has any permission!
      //setUserPermission("Admin/StoreOwner/StoreManager");
    };
    fetchApi();
  }, []);

  return (
    <>
    <AdminMenu />
      <div className="my-4">
        <h4>Enter username:</h4> 
        <SearchBar setSearchValue={setSearchValue} />
      </div>
      <div
        className="my-4"
        style={{ display: "flex", justifyContent: "center" }}
      >
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

export default AdminViewUserPurchaes;
