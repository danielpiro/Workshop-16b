import Menu from "../components/menu";
import { useEffect, useState } from "react";
import api from "../components/api";
import { useCookies } from "react-cookie";
import createNotification from "../components/norification";
import Card from "../components/card";

const UserHistory = () => {
  const [purchases, setPurchases] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  //const [searchValue, setSearchValue] = useState("");
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  useEffect( async () => {
    setIsLoading(!isLoading);
      await api
        .get(`/history/user/?userId=${cookies.userId}`)
        .then((res) => {
            const { data } = res;
            console.log(data)
            if (data.success) {
                setPurchases(data.value);
                setIsLoading(!isLoading);
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
      <div className="text-center my-5">
        <h3>View all my purchases</h3>
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
            {purchases.map((purchase) => {
              return (
                <li className=" list-group-item" key={purchase.id}>
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

export default UserHistory;
