import Menu from "../components/menu";
import axios from "axios";
import { useState, useEffect } from "react";
import createNotification from "../components/norification";
import { useRouter } from "next/router";
import Footer from "../components/footer";

const FireOwnerToStore = () => {
  const router = useRouter();
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
  const [newOfficialInput, setNewOfficialInput] = useState({
    username: "",
    storename: "",
  });

  const onHiringOfficial = async (e) => {
    e.preventDefault();
    if (newOfficialInput.username != "" && newOfficialInput.storename != "") {
      await api
        .post(
          `owner/fire/?userId=${newOfficialInput.username}&storeId=${newOfficialInput.storename}` //TODO: Need the real path from backend
        )
        .then((res) => {
          if (res.status === 200) {
            const { data } = res;
            console.log(data);
            createNotification("success", "User fired successfully", () =>
              router.push("/dashboard")
            )();
          } else {
            const { data } = res;
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
        <h3>Fire new owner to a store</h3>
      </div>

      <div className="container">
        <div className="row" style={{ display: "flex", width: "50%" }}>
          <input
            className="form-control mr-sm-2 m-2"
            type="search"
            placeholder="Enter username of the future fired owner/manager"
            aria-label="Search"
            value={newOfficialInput.username}
            onChange={(e) =>
              setNewOfficialInput((prevState) => ({
                ...prevState,
                username: e.target.value,
              }))
            }
          />
        </div>
        <div className="row" style={{ display: "flex", width: "50%" }}>
          <input
            className="form-control mr-sm-2 m-2"
            type="search"
            placeholder="Enter the store name"
            aria-label="Search"
            value={newOfficialInput.storename}
            onChange={(e) =>
              setNewOfficialInput((prevState) => ({
                ...prevState,
                storename: e.target.value,
              }))
            }
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
