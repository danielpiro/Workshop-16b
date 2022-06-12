import api from "../components/api";
import Menu from "../components/menu";
import { useState } from "react";
import createNotification from "../components/norification";
import { useRouter } from "next/router";
import { useCookies } from "react-cookie";
import sendNotification from "../components/send-notification";

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
    //console.log(usernameFired);
    if (usernameFired !== "") {
      let storeID = window.location.href.split("?").pop();
      if(storeID.charAt(storeID.length-1) === '#'){
        storeID = storeID.slice(0, -1);
      }
      await api
        .post(
          `/store/permissions/delete/?storeId=${storeID}&userIdRemoving=${cookies.userId}&UserAffectedId=${usernameFired}`
        )
        .then((res) => {
          const { data } = res;
          if (data.success) {
            const notification = {
              senderName: cookies.userId,
              subject: "StoreAppointment",
              title: "title",
              body: window.location.href.split("?").pop().slice(0, -1),
            };
            sendNotification(notification);
            createNotification("success", "User fired successfully", () =>
              router.push("/dashboard")
            )();
          } else {
            createNotification("error", data.reason)();
          }
        })
        .catch((err) => console.log(err));
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
