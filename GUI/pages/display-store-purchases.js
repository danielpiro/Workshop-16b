import Menu from "../components/menu";
import SearchBar from "../components/search-bar";
import axios from "axios";
import { useState, useEffect } from "react";
import Card from "../components/card";
import api from "../components/api";
import { useCookies } from "react-cookie";
import createNotification from "../components/norification";

const DisplayStorePurchases = () => {
  const [purchases, setPurchases] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [singlePurchase, setSinglePurchase] = useState({});

  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  useEffect( async () => {
    setIsLoading(!isLoading);
      await api
        .get(`/history/store/?storeId=${window.location.href.split("?").pop()}&userId=${cookies.userId}`)
        .then((res) => {
            const { data } = res;
            console.log(data)
            if (data.success) {
                setPurchases(data.value);
                //setIsLoading(!isLoading);
                createNotification(
                "success",
                "Displaying all user's purchases successfully"
                )();
          }
        })
        .catch((err) => createNotification("error", data.reason)());
  }, []);

  const getSingleProduct = (id) => {
    purchases.map((purchase) => {
      if (purchase.id === id) {
        return setSinglePurchase(purchase);
      }
      return null;
    });
  };

  return (
    <>
      <Menu />
      <div className="card-header">
        <h3>Display Store Purchases</h3>
      </div>
      <div
        className="my-4"
        style={{ display: "flex", justifyContent: "center" }}
      ></div>
      {!isLoading ? (
        <div style={{ display: "table", width: "100%" }}>
          <ul className="list-group" style={{ display: "table-cell" }}>
            {purchases.map((purchase) => {
              return (
                <li className=" list-group-item" key={purchase.id}>
                  <div className="card-body">
                    <h4 className="card-title text-center">UserID: {purchase.userID}</h4>
                    <h4 className="card-title text-center">StoreID: {purchase.storeID}</h4>
                    <h4 className="card-title text-center">PurchaseID: {purchase.purchaseID}</h4>
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
    </>
  );
};

export default DisplayStorePurchases;
