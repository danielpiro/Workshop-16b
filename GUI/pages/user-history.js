import Menu from "../components/menu";
import { useEffect, useState } from "react";
import api from "../components/api";
import { useCookies } from "react-cookie";
import createNotification from "../components/norification";

const UserHistory = () => {
  const [purchases, setPurchases] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
    "session",
  ]);

  const fetch = async () => {
    setIsLoading(!isLoading);
    return await api
      .get(`/history/user/?userId=${cookies.userId}`, {
        headers: {
          Authorization: cookies.session,
        },
      })
      .then((res) => {
        const { data } = res;
        if (data.success) {
          setPurchases(data.value);
          setIsLoading(!isLoading);
          console.log(data);
          createNotification(
            "success",
            "Displaying all user's purchases successfully"
          )();
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
  };
  useEffect(() => {
    return fetch();
  }, []);

  return (
    <>
      <Menu />
      <div className="text-center my-5">
        <h3>View all my purchases</h3>
      </div>
      {!isLoading ? (
        <div className="d-flex justify-content-center table-borderless">
          <ul className="list-group w-50" style={{ display: "table-cell" }}>
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

export default UserHistory;
