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
    "session",
  ]);

  useEffect(async () => {
    setIsLoading(!isLoading);
    let storeID = window.location.href.split("?").pop();
    if (storeID.charAt(storeID.length - 1) === "#") {
      storeID = storeID.slice(0, -1);
    }
    await api
      .get(`/history/store/?userId=${cookies.userId}&storeId=${storeID}`, {
        headers: {
          Authorization: cookies.session,
        },
      })
      .then((res) => {
        const { data } = res;
        console.log(data);
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

  return (
    <>
      <Menu />
      <div className="text-center m-5">
        <h3>Display Store Purchases</h3>
      </div>
      {!isLoading ? (
        <div className="container d-flex justify-content-center">
          <ul className="list-group" style={{ display: "table-cell" }}>
            {purchases.map((purchase) => {
              return (
                <li className=" list-group-item mb-3" key={purchase.id}>
                  <div className="card-body">
                    <h4 className="card-title text-center">
                      UserID: {purchase.userID}
                    </h4>
                    <h4 className="card-title text-center">
                      StoreID: {purchase.storeID}
                    </h4>
                    <h4 className="card-title text-center">
                      PurchaseID: {purchase.purchaseID}
                    </h4>
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
