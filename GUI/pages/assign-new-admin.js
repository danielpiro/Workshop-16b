import Menu from "../components/menu";
import { useState } from "react";
import api from "../components/api";
import { useCookies } from "react-cookie";
import createNotification from "../components/norification";

const AssignNewAdmin = () => {
  const [username, setUsername] = useState("");

  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  const onAssignAdmin = async (e) => {
    e.preventDefault();
    if (username !== "") {
      await api
        .post(
          `/users/add/admin/?whoIsAdding=${cookies.userId}&user_toMakeAdmin=${username}`
        )
        .then((res) => {
          const { data } = res;
          if (data.success) {
            createNotification("success", `User ${username} is now Admin`)();
          } else {
            createNotification("error", data.reason)();
          }
        })
        .catch((err) => console.log(err));
    } else {
      createNotification("error", "username was not valid, please try again")();
    }
  };

  return (
    <>
      <Menu />
      <div className="text-center m-5" style={{ width: "90%" }}>
        <div className="container">
          <h2>Assign New Admin In The System</h2>
          <div className="row">
            <h5>Enter the username of the future admin: </h5>
          </div>
          <div className="row">
            <input
              className="form-control mr-sm-2 m-1"
              type="search"
              placeholder="Enter username"
              aria-label="Search"
              //value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>
          <div className="row">
            <button
              className="btn btn-primary mr-lg-3 m-1"
              onClick={onAssignAdmin}
            >
              Assign New Admin
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default AssignNewAdmin;
