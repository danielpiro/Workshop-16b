import Menu from "../components/menu";
import { useState } from "react";
import api from "../components/api";
import { useCookies } from "react-cookie";
import createNotification from "../components/norification";
import Card from "../components/card";

const AdminViewUserPurchase = () => {
  const [purchases, setPurchases] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [searchValue, setSearchValue] = useState("");
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  const searchUsernamePurchases = async (e) => {
    e.preventDefault();
    if (searchValue !== "") {
      setIsLoading(!isLoading);
      await api
        .get(`/history/user/?userId=${searchValue}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            setPurchases(data.value);
            setIsLoading(!isLoading);
            createNotification(
              "success",
              "Displaying all user's purchases successfully"
            )();
          } else {
            createNotification("error", data.reason)();
          }
        })
        .catch((err) => console.log(err));
    } else {
      createNotification("error", "username was not valid, please try again")();
    }
  };

  return (
    <>
      <Menu />
      <div className="text-center my-5">
        <h3>View all user's purchases</h3>
      </div>
      <div className="container d-flex justify-content-center">
        <form className="row">
          <div className="main-search-bar">
            <input
              className="form-control mr-sm-2"
              type="search"
              placeholder="Enter username"
              aria-label="Search"
              onChange={(e) => setSearchValue(e.target.value)}
            />
            <div>
              <button
                className="btn btn-primary"
                onClick={searchUsernamePurchases}
              >
                Search
              </button>
            </div>
          </div>
        </form>
      </div>

      <div
        className="my-4"
        style={{ display: "flex", justifyContent: "center" }}
      >
        <h1>Purchases</h1>
      </div>
      {purchases.length > 0 ? (
        <div style={{ display: "table", width: "100%" }}>
          <ul className="list-group" style={{ display: "table-cell" }}>
            {purchases.map((product) => {
              return (
                <li className=" list-group-item" key={product.id}>
                  <div className="card-body">
                    <h4 className="card-title text-center">UserID: {purchase.userId}</h4>
                    <h4 className="card-title text-center">StoreID: {purchase.storeId}</h4>
                    <h4 className="card-title text-center">PurchaseID: {purchase.purchaseId}</h4>
                  </div>
                </li>
              );
            })}
          </ul>
        </div>
      ) : isLoading ? (
        <div className="container h-100 my-6">
          <div className="row align-items-center justify-content-center">
            <div className="spinner-border" />
          </div>
        </div>
      ) : null}
    </>
  );
};

export default AdminViewUserPurchase;
