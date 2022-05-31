import Menu from "../components/menu";
import axios from "axios";
import { useState, useEffect } from "react";
import Link from "next/link";
import { useCookies } from "react-cookie";
import api from "../components/api";
//import { useRouter } from "next/router";
import createNotification from "../components/norification";

const CloseStore = () => {
  //const router = useRouter();
  //const [store, setStore] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [searchValue, setSearchValue] = useState("");
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  const onCloseStore = async (e) => {
    e.preventDefault();
    setIsLoading(!isLoading);
    await api
      .post(
        `/store/unfreeze/?storeId=${window.location.href.split("?").pop()}&userId=${cookies.userId}`
      )
      .then((res) => {
        const { data } = res;
          if (data.success) {
            console.log(data);
            createNotification("success", "Close store done successfully", () =>
              router.push("/dashboard")
            )();
        } else {
          // const { data } = res;
          console.log(data.reason);
          createNotification("error", `failure closing store! ${data.reason}`)();
        }
      })
      .catch((err) => createNotification("error", data.reason)()); //console.log("err"));
  };

  const onReOpenStore = async (e) => {
    e.preventDefault();
    setIsLoading(!isLoading);
    await api
      .post(
        `/store/freeze/?storeId=${window.location.href.split("?").pop()}&userId=${cookies.userId}`
      )
      .then((res) => {
        const { data } = res;
          if (data.success) {
            console.log(data);
            createNotification("success", "Close store done successfully", () =>
              router.push("/dashboard")
            )();
        } else {
          // const { data } = res;
          console.log(data.reason);
          createNotification("error", `failure closing store! ${data.reason}`)();
        }
      })
      .catch((err) => createNotification("error", data.reason)()); //console.log("err"));
  };

  return (
    <>
      <Menu />
      <div className="container m-auto w-100">
        <div className="row">
          <div className="col">
            <span className="text-center m-3">
              {" "}
              <h3>Close store</h3>{" "}
            </span>
            <span className="text-center">
              {" "}
              <h5>(Temporary close store actions)</h5>{" "}
            </span>

            <div className="d-flex justify-content-center">
              <button className="btn btn-primary my-3" onClick={onCloseStore}>
              Temporary close Store
              </button>
            </div>
          </div>

          <div className="col">
            <span className="text-center m-3">
              {" "}
              <h3>Re-Open store</h3>{" "}
            </span>
            <span className="text-center">
              {" "}
              <h5>(Unfreeze store actions)</h5>{" "}
            </span>

            <div className="d-flex justify-content-center">
              <button className="btn btn-primary my-3" onClick={onReOpenStore}>
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
