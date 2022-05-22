import Menu from "../components/menu";
import axios from "axios";
import { useState, useEffect } from "react";
import { useRouter } from "next/router";

const NotifyAdmins = () => {
  const router = useRouter();
  const [userInput, setUserInput] = useState("");
  //const [userPermission, setUserPermission] = useState("Admin"); 

  const onNotify = async (e) => {
    e.preventDefault();
    //console.log(userInput);
    if (userInput !== "") {
      await api
        .post(
          `/market/?userId=${""}&message=${userInput}` //TODO: Need the real path from backend
        )
        .then((res) => {
          if (res.status === 200) {
            const { data } = res;
            console.log(data);
            createNotification("success", "Notify admin done successfully", () =>
              router.push("/dashboard")
            )();
          } else {
            const { data } = res;
            console.log(data);
            createNotification("error", "failure notify admin!")();
          }
        })
        .catch((err) => console.log("err"));
    } else {
      createNotification(
        "error",
        "user input was not valid, please try again"
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
        <div className="input-group m-2 w-50">
          <span className="input-group-text">Write your message here</span>
          <textarea
            className="form-control"
            aria-label="With textarea"
            placeholder="Notify Admin Message..."
            onChange={(e) =>
              setUserInput((prevState) => ({
                ...prevState,
                userInput: e.target.value,
              }))
            }
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
