import Menu from "../components/menu";
import axios from "axios";
import { useState, useEffect } from "react";
import createNotification from "../components/norification";
import { useRouter } from "next/router";
import Footer from "../components/footer";
import api from "../components/api";

const Statistics = () => {
  const router = useRouter();
  const [isLoading, setIsLoading] = useState(true);
  const [stats, setStats] = useState({
    subscribers: "",
    loggedInUsers: "",
  });
  //const [userPermission, setUserPermission] = useState("Admin"); 
  
  useEffect(() => {
    const fetchStats = async () => {
      await api
        .post(
          `/market/stats/` //TODO: Need the real path from backend
        )
        .then((res) => {
          if (res.status === 200) {
            const { data } = res;
            console.log(data);
            createNotification("success", "Display statistics done successfully", () =>
              router.push("/dashboard")
            )();
          } else {
            const { data } = res;
            console.log(data);
            //setStats(data.products);
            createNotification("error", "failure display statistics!")();
          }
        })
        .catch((err) => console.log("err"));
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
