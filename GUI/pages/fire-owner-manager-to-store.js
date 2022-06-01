import api from "../components/api";
import Menu from "../components/menu";
import axios from "axios";
import { useState, useEffect } from "react";
import createNotification from "../components/norification";
import { useRouter } from "next/router";
import Footer from "../components/footer";
import { useCookies } from "react-cookie";

const FireOwnerToStore = () => {
  const router = useRouter();
  const [usernameFired, setUsernameFired] = useState("");

  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  const onHiringOfficial = async (e) => {
    e.preventDefault();
    if (newOfficialInput.username != "" && newOfficialInput.storename != "") {
      await api
        .post(
          `owner/fire/?userId=${usernameFired}&storeId=${window.location.href.split("?").pop().slice(0, -1)}` 
        )
        .then((res) => {
          const { data } = res;
            if (data.success) {
              console.log(data);
              createNotification("success", "User fired successfully", () =>
                router.push("/dashboard")
              )();
          } else {
            //const { data } = res;
            console.log(data);
            createNotification("error", "failure firing user!")();
          }
        })
        .catch((err) => console.log("err"));
    } else {
      createNotification(
        "error",
        "storename or username was not valid, please try again"
      )();
    }
  };

  return (
    <>
      <Menu />

      <div className="card-header">
        <h3>Fire new owner fron the store</h3>
      </div>

      <div className="container">
        <div className="row" style={{ display: "flex", width: "50%" }}>
          <input
            className="form-control mr-sm-2 m-2"
            type="search"
            placeholder="Enter username of the future fired owner/manager"
            aria-label="Search"
            onChange={(e) => setUsernameFired(e.target.value)}
          />
        </div>
        
        <br />
        <div className="row m-1" style={{ display: "flex", width: "15%" }}>
          <button
            className="btn btn-primary mr-lg-3"
            onClick={onHiringOfficial}
          >
            Fire Manager/Owner
          </button>
        </div>
      </div>
    </>
  );
};

export default FireOwnerToStore;
