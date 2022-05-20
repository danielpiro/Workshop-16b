import Menu from "../components/menu";
import axios from "axios";
import { useState, useEffect } from "react";
import createNotification from "../components/norification";
import { useRouter } from "next/router";
import Footer from "../components/footer";

const Statistics = () => {
  const router = useRouter();
  const [isLoading, setIsLoading] = useState(true);
  const [stats, setStats] = useState({
    subscribers: "",
    loggedInUsers: "",
  });
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!

  useEffect(() => {
    const fetchPermission = async () => {
      const response = await axios.get("users/getUserPermission");
      setUserPermission(response.data);
    };
    fetchPermission();
  }, []);

  useEffect(() => {
    const fetchApi = async () => {
      const response = await axios.get("admin/logged-in&registered"); //TODO: Check if the function exists
      setIsLoading(!isLoading);
      const { data } = response;
      //setStats(data.products);
    };
    fetchApi();
  }, []);

  return (
    <>
      <Menu />

      <div className="card-header">
        <h3>Statistics</h3>
      </div>

      <br />
      <div className="m-2" style={{ width: "20%" }}>
        <h4>Subscribers: </h4>
        <input
          className="form-control"
          type="search"
          placeholder={stats.subscribers}
          aria-label="Search"
          disabled
          readonly
        />
      </div>

      <br />
      <div className="m-2" style={{ width: "20%" }}>
        <h4>Logged-in users: </h4>
        <input
          className="form-control"
          type="search"
          placeholder={stats.loggedInUsers}
          aria-label="Search"
          disabled
          readonly
        />
      </div>

      <Footer />
    </>
  );
};

export default Statistics;
