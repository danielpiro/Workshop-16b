import Menu from "../components/menu";
import SearchBar from "../components/search-bar";
import axios from "axios";
import { useState, useEffect } from "react";
import Card from "../components/card";
import api from "../components/api";

const AdminViewStorePurchaes = () => {
  const [purchases, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [searchValue, setSearchValue] = useState("");
  //const [userPermission, setUserPermission] = useState("Admin");

  const searchUsernamePurchases = async (e) => {
    e.preventDefault();
    //console.log(searchValue);
    if (searchValue !== "") {
      await api
        .get(
          `/history/store/?storeId=${searchValue}&userId=${""}` //TODO: ask backend for storeIDs!
        )
        .then((res) => {
          if (res.status === 200) {
            const { data } = res;
            console.log(data);
            createNotification("success", "Displaying all store's purchases successfully", () =>
              router.push("/dashboard")
            )();
          } else {
            const { data } = res;
            console.log(data);
            createNotification("error", "failure displaying all store's purchases!")();
          }
        })
        .catch((err) => console.log("err"));
    } else {
      createNotification(
        "error",
        "storename was not valid, please try again"
      )();
    }
  };

  return (
    <>
      <Menu />
      <div className="card-header">
        <h3>View all store's purchases</h3>
      </div>
      <div className="container">
        <nav className="navbar navbar-expand-lg bg-secondery align-items-center justify-content-center rounded-3">
          <form
            className="row form-inline"
            style={{ display: "flex", width: "60%" }}
          >
            <div className="main-search-bar">
              <input
                className="form-control mr-sm-2"
                type="search"
                placeholder="Enter store name"
                aria-label="Search" 
                onChange={(e) =>
                  setSearchValue((prevState) => ({
                    ...prevState,
                    searchValue: e.target.value,
                  }))
                }
              />
              <div>
                <button
                  className="btn btn-primary mr-lg-3"
                  onClick={searchUsernamePurchases}
                >
                  Search
                </button>
              </div>
            </div>
          </form>
        </nav>
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

export default AdminViewStorePurchaes;
