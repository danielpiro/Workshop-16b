import Menu from "../components/menu";
import axios from "axios";
import { useState, useEffect } from "react";
import { useRouter } from "next/router";
import Footer from "../components/footer";

const NotifyAdmins = () => {
  const router = useRouter();
  const [userInput, setUserInput] = useState("");
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!

  const onNotify = (e) => {
    e.preventDefault();
    console.log(userInput);
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
        <h3>Notify Admins</h3>
      </div>

      <br />
      <div className="input-group m-2" style={{ width: "85%" }}>
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
      <button
        className="btn btn-primary mr-lg-3 m-2"
        style={{ width: "10%" }}
        onClick={onNotify}
      >
        Submit
      </button>

      <Footer />
    </>
  );
};

export default NotifyAdmins;
