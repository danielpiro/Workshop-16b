import Menu from "../components/menu";
import { useState } from "react";
import api from "../components/api";
import { useCookies } from "react-cookie";
import createNotification from "../components/norification";

const AdminViewUserPurchase = () => {
  const [purchases, setPurchases] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [searchValue, setSearchValue] = useState("");
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
    "session",
  ]);

  const searchUsernamePurchases = async (e) => {
    e.preventDefault();
    setIsLoading(!isLoading);
    if (searchValue !== "") {
      await api
        .get(`/history/user/?userId=${searchValue}`, {
          headers: {
            Auth: cookies.session,
          },
        })
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data.value);
            setPurchases(data.value);
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

      <div className="text-center m-5">
        <h1>Purchases</h1>
      </div>
      {purchases?.length > 0 ? (
        <div className="container d-table w-100 justify-content-center">
          <ul className="list-group d-table-cell">
            {purchases.map((purchase) => {
              return (
                <li className=" list-group-item mb-3" key={purchase.id}>
                  <div className="card-body">
                    <h4 className="card-title text-center">
                      UserID: {purchase.userId}
                    </h4>
                    <h4 className="card-title text-center">
                      StoreID: {purchase.storeId}
                    </h4>
                    <h4 className="card-title text-center">
                      PurchaseID: {purchase.purchaseId}
                    </h4>
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
