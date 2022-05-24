import api from "../components/api";
import Menu from "../components/menu";
import axios from "axios";
import { useState, useEffect } from "react";
import createNotification from "../components/norification";
import { useRouter } from "next/router";
import Footer from "../components/footer";
import { useCookies } from "react-cookie";

const HireOwnerToStore = () => {
  const router = useRouter();
  //const [userPermission, setUserPermission] = useState("Admin");
  const [newOfficialInput, setNewOfficialInput] = useState({
    username: "",
    storename: "",
    ownerORmanager: "Manager/Owner",
  });

  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  const setManager = () => {
    setNewOfficialInput((prevState) => ({
      ...prevState,
      ownerORmanager: "Manager",
    }));
  };

  const setOwner = () => {
    setNewOfficialInput((prevState) => ({
      ...prevState,
      ownerORmanager: "Owner",
    }));
  };

  const onHiringOfficial = async (e) => {
    e.preventDefault();
    if (
      newOfficialInput.username != "" &&
      newOfficialInput.storename != "" &&
      newOfficialInput.ownerORmanager != ""
    ) {
      if (newOfficialInput.ownerORmanager == "Owner") {
        const newOwner = {
          storeId: window.location.href.split("/").pop(),
          userIdGiving: Cookies.username,
          userGettingPermission: newOfficialInput.username,
          permissions: ["VIEW_STORE_HISTORY"], //TODO: Not sure how we can get permissions here (should be done in backend) 
        };
        await api
        .post(
          `/owner/`, newOwner
        )
        .then((res) => {
          if (res.status === 200) {
            const { data } = res;
            console.log(data);
            createNotification("success", "Hired owner successfully", () =>
              router.push("/dashboard")
            )();
          } else {
            const { data } = res;
            console.log(data);
            createNotification("error", "failure hiring owner!")();
          }
        })
        .catch((err) => console.log("err"));
      }
      else if (newOfficialInput.ownerORmanager == "Manager") {
        const storeID = window.location.href.split("?").pop().slice(0, -1);
        await api
        .post(
          `/manager/?storeId=${storeID}&userIdGiving=${cookies.username}&UserGettingPermissionId=${newOfficialInput.username}`
        )
        .then((res) => {
          if (res.status === 200) {
            const { data } = res;
            console.log(data);
            createNotification("success", "Hired manager successfully", () =>
              router.push("/dashboard")
            )();
          } else {
            const { data } = res;
            console.log(data);
            createNotification("error", "failure hiring manager!")();
          }
        })
        .catch((err) => console.log("err"));
      }
    } else {
      createNotification(
        "error",
        "at least one of the inputs was not valid, please try again"
      )();
    }
  };

  return (
    <>
      <Menu />

      <div className="card-header">
        <h3>Hire new owner to a store</h3>
      </div>

      <div className="container">
        <div className="row" style={{ display: "flex", width: "50%" }}>
          <input
            className="form-control mr-sm-2 m-2"
            type="search"
            placeholder="Enter username of the new owner/manager"
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
        <div className="dropdown m-1">
          <button
            className="btn btn-secondary dropdown-toggle"
            type="button"
            id="dropdownMenuButton1"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            {newOfficialInput.ownerORmanager}
          </button>
          <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
            <li>
              <a className="dropdown-item" href="#" onClick={setManager}>
                Manager
              </a>
            </li>
            <li>
              <a className="dropdown-item" href="#" onClick={setOwner}>
                Owner
              </a>
            </li>
          </ul>
        </div>
        <br />
        <div className="row m-1" style={{ display: "flex", width: "15%" }}>
          <button
            className="btn btn-primary mr-lg-3"
            onClick={onHiringOfficial}
          >
            Hire new Manager/Owner
          </button>
        </div>
      </div>
    </>
  );
};

export default HireOwnerToStore;
