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

  // useEffect(() => {
  //   const fetchPermission = async () => {
  //     const response = await axios.get("users/getUserPermission");
  //     setUserPermission(response.data);
  //   };
  //   fetchPermission();
  // }, []);

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
