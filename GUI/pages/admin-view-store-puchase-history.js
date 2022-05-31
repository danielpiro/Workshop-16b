import Menu from "../components/menu";
import { useState } from "react";
import api from "../components/api";
import { useCookies } from "react-cookie";
import Card from "../components/card";

const AdminViewStorePurchaes = () => {
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
        .get(
          `/history/store/?storeId=${searchValue}&userId=${cookies.userId}`
        )
        .then((res) => {
          const { data } = res;
            if (data.success) {
              setPurchases(data.value);
              setIsLoading(!isLoading);
              createNotification(
                "success",
                "Displaying all store's purchases successfully"
              )();
          }
        })
        .catch((err) => console.log(err));
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
      <div className="text-center my-5">
        <h3>View all store's purchases</h3>
      </div>
      <div className="container d-flex justify-content-center">
        <form className="row">
          <div className="main-search-bar">
            <input
              className="form-control mr-sm-2"
              type="search"
              placeholder="Enter store name"
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
                  <Card
                    value={product.id}
                    title={product.name}
                    price={product.price}
                    quantity={product.quantity}
                    category={product.category}
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

export default AdminViewStorePurchaes;
