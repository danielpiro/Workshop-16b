import Menu from "../components/menu";
import { useState, useEffect } from "react";
import createNotification from "../components/norification";
import api from "../components/api";

const Statistics = () => {
  const [stats, setStats] = useState({
    subscribers: "",
    loggedInUsers: "",
  });

  useEffect(() => {
    const fetchStats = async () => {
      return await api
        .get("/statistics/online")
        .then((res) => {
          if (res.status === 200) {
            const { data } = res;
            setStats((prevState) => ({
              ...prevState,
              loggedInUsers: data.value,
            }));
          }
        })
        .then(async () => {
          return await api.get("/statistics/users").then((res) => {
            if (res.status === 200) {
              const { data } = res;
              setStats((prevState) => ({
                ...prevState,
                subscribers: data.value,
              }));
              createNotification(
                "success",
                "Display statistics done successfully"
              )();
            }
          });
        })
        .catch((err) => console.log(err));
    };
    fetchStats();
  }, []);

  return (
    <>
      <Menu />
      <div className="text-center my-5">
        <h3>Statistics</h3>
      </div>
      <div className="container d-flex justify-content-center">
        <div className="m-2">
          <h4>Subscribers: </h4>
          <input
            className="form-control"
            type="search"
            placeholder={stats.subscribers}
            aria-label="Search"
            disabled
          />
        </div>

        <br />
        <div className="m-2">
          <h4>Logged-in users: </h4>
          <input
            className="form-control"
            type="search"
            placeholder={stats.loggedInUsers}
            aria-label="Search"
            disabled
          />
        </div>
      </div>
    </>
  );
};

export default Statistics;
