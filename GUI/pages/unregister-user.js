import Menu from "../components/menu";
import axios from "axios";
import { useState, useEffect } from "react";
import createNotification from "../components/norification";
import { useRouter } from "next/router";
import Footer from "../components/footer";

const UnregisterUser = () => {
  const router = useRouter();
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
  const [username, setUsername] = useState("");

  const onUnregistered = (e) => {
    e.preventDefault();
    if (username != "") {
      const userToUnregister = axios.post(`users/unregister/${username}`);
      //console.log(isOpened.status);

      if (userToUnregister) {
        createNotification("success", "Unregistered successfully", () =>
          router.push("/dashboard")
        )();
      } else {
        createNotification("error", "failure in Unregister!")();
      }
    } else {
      createNotification("error", "username was not valid, please try again")();
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
        <br />
        <div className="row" style={{ display: "flex", width: "80%" }}>
          <h4>Note: you can unregister a user which isn't owner/manager!</h4>
        </div>
        <div className="row" style={{ display: "flex", width: "50%" }}>
          <input
            className="form-control mr-sm-2 m-2"
            type="search"
            placeholder="Enter username of the future unregistered user"
            aria-label="Search"
            value={username}
            onChange={(e) =>
              setNewOfficialInput((prevState) => ({
                ...prevState,
                username: e.target.value,
              }))
            }
          />
        </div>

        <div className="row m-1" style={{ display: "flex", width: "15%" }}>
          <button className="btn btn-primary mr-lg-3" onClick={onUnregistered}>
            Unregister user
          </button>
        </div>
      </div>
    </>
  );
};

export default UnregisterUser;
