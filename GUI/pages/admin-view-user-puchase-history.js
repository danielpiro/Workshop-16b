import AdminMenu from "../components/menus/menuAdmin";
import SearchBar from "../components/search-bar";
import axios from "axios";
import { useState, useEffect } from "react";
import Card from "../components/card";

const AdminViewUserPurchaes = () => {
  const [purchases, setPurchases] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [searchValue, setSearchValue] = useState("");
  const [userPermission, setUserPermission] = useState("Admin"); //=useState("Guest"); //TODO: Need to change to Guest when logic is ready!

  const searchUsernamePurchases = (e) => {
    e.preventDefault();
    setIsLoading(true);
    const fetchPurchases = async () => {
      const response = await axios.get("history/user");
      setIsLoading(!isLoading);
      setPurchases(response.data);
    };
    fetchPurchases();
  }

  useEffect(() => {
    const fetchPermission = async () => {
      const response = await axios.get("users/getUserPermission");
      setIsLoading(!isLoading);
      setUserPermission(response.data);
    };
    fetchPermission();
  }, []);

  return (
    <>
    <AdminMenu />
    <div className="card-header">
        <h3>View all user's purchases</h3>
    </div>
    <div className="container">
      <nav className="navbar navbar-expand-lg bg-secondery align-items-center justify-content-center rounded-3">
        <form className="row form-inline" style={{ display: "flex", width: "60%" }}>
          <div className="main-search-bar">
            <input
              className="form-control mr-sm-2"
              type="search"
              placeholder="Enter username"
              aria-label="Search"
              value={searchValue.data}
              onChange={(e) =>
                setSearchValue((prevState) => ({
                    ...prevState,
                    searchValue: e.target.value,
                  }))
              }
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

    {!isLoading ? (
      <div style={{ display: "table", width: "100%" }}>
        <ul className="list-group" style={{ display: "table-cell" }}>
          {purchases
            .filter((val) =>
              val.title.toLowerCase().includes(searchValue.toLowerCase())
            )
            .map((purchase) => {
              return (
                <li className=" list-group-item" key={purchase.id}>
                  <Card
                    value={purchase.id}
                    title={purchase.title}
                    description={purchase.description}
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
    )}
    </>
  );
};

export default AdminViewUserPurchaes;
