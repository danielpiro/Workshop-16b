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
            {purchases.map((product) => {
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
