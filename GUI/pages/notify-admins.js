import Menu from "../components/menu";
import { useState } from "react";
import api from "../components/api";
import { useCookies } from "react-cookie";
import createNotification from "../components/norification";

const NotifyAdmins = () => {
  const [userInput, setUserInput] = useState("");
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);
  const onNotify = async (e) => {
    e.preventDefault();

    if (userInput !== "") {
      console.log(userInput);
      return await api
        .post(
          `/market/?userId=${cookies.userId}&StoreName=fakeStoreName&complaint=${userInput}` //TODO: Need the real path from backend
        )
        .then((res) => {
            const { data } = res;
            if (data.success) {
            console.log(data);
            createNotification("success", "Notify admin done successfully")();
          }
        })
        .catch((err) => console.log(err));
    } else {
      createNotification(
        "error",
        "User input cannot be empty , please try again"
      )();
    }
  };

  return (
    <>
      <Menu />
      <div className="text-center m-5">
        <h3>Notify Admins</h3>
      </div>
      <div className="container d-flex justify-content-center">
        <div className="input-group m-2 w-25">
          <textarea
            className="form-control"
            aria-label="With textarea"
            placeholder="Notify Admin Message..."
            onChange={(e) => setUserInput(e.target.value)}
          />
        </div>
        <button className="btn btn-primary m-2" onClick={onNotify}>
          Submit
        </button>
      </div>
    </>
  );
};

export default NotifyAdmins;
