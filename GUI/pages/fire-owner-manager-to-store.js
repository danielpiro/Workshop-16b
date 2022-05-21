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

  const onHiringOfficial = (e) => {
    e.preventDefault();
    if (newOfficialInput.username != "" && newOfficialInput.storename != "") {
      const userToFire = axios.post(
        `owner/fire/${newOfficialInput.username}/${newOfficialInput.storename}`
      );
      //console.log(isOpened.status);

      if (userToFire) {
        createNotification("success", "Fire owner/manager successfully", () =>
          router.push("/dashboard")
        )();
      } else {
        createNotification("error", "failure firing owner/manager!")();
      }
    } else {
      createNotification(
        "error",
        "storename or username was not valid, please try again"
      )();
    }
  };

  useEffect(() => {
    const fetchPermission = async () => {
      const response = await axios.get("users/getUserPermission");
      setUserPermission(response.data);
    };
    fetchPermission();
  }, []);

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
