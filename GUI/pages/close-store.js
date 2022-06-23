import Menu from "../components/menu";
import { useState } from "react";
import { useCookies } from "react-cookie";
import api from "../components/api";
import createNotification from "../components/norification";

const CloseStore = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
    "session",
  ]);

  const onCloseStore = async (e) => {
    e.preventDefault();
    setIsLoading(!isLoading);
    let storeID = window.location.href.split("?").pop();
    if (storeID.charAt(storeID.length - 1) === "#") {
      storeID = storeID.slice(0, -1);
    }
    await api
      .post(
        `/store/unfreeze/?userId=${cookies.userId}&storeId=${storeID}`,
        null,
        {
          headers: {
            Authorization: cookies.session,
          },
        }
      )
      .then((res) => {
        const { data } = res;
        if (data.success) {
          createNotification("success", "Close store done successfully")();
        } else {
          createNotification(
            "error",
            `failure closing store! ${data.reason}`
          )();
        }
      })
      .catch((err) => console.log(err));
  };

  const onReOpenStore = async (e) => {
    e.preventDefault();
    setIsLoading(!isLoading);
    let storeID = window.location.href.split("?").pop();
    if (storeID.charAt(storeID.length - 1) === "#") {
      storeID = storeID.slice(0, -1);
    }
    await api
      .post(
        `/store/freeze/?userId=${cookies.userId}&storeId=${storeID}`,
        null,
        {
          headers: {
            Authorization: cookies.session,
          },
        }
      )
      .then((res) => {
        const { data } = res;
        if (data.success) {
          createNotification("success", "Close store done successfully")();
        } else {
          createNotification(
            "error",
            `failure closing store! ${data.reason}`
          )();
        }
      })
      .catch((err) => console.log(err));
  };

  return (
    <>
      <Menu />
      <div className="text-center m-5">
        <h3>Freeze/Unfreeze store</h3>
      </div>
      <div className="container d-flex justify-content-center">
        <div className="row">
          <div className="col">
            <span className="text-center">
              <h3>Close store</h3>
            </span>
            <span className="text-center">
              <h5>(Temporary close store actions)</h5>
            </span>
            <div className="d-flex justify-content-center">
              <button className="btn btn-primary" onClick={onCloseStore}>
                Temporary close Store
              </button>
            </div>
          </div>
          <div className="col">
            <span className="text-center">
              <h3>Re-Open store</h3>
            </span>
            <span className="text-center">
              <h5>(Unfreeze store actions)</h5>
            </span>
            <div className="d-flex justify-content-center">
              <button className="btn btn-primary" onClick={onReOpenStore}>
                Re-Open Store
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default CloseStore;
