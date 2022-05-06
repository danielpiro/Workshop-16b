import { useState } from "react";
import Link from "next/link";
import api from "../components/api";
const register = () => {
  const [input, setInput] = useState({
    username: "",
    password: "",
  });
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const onClickRegister = (e) => {
    e.preventDefault();
    setError("");
    console.log("input", input);
    const regex =
      /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    if (regex.test(input.username) === false) {
      setError("Please insert a valid email");
      return;
    }
    api
      .post("/register", input)
      .then((res) => {
        if (res.status !== 200) {
          setError(res.data);
          return;
        }
        setSuccess(
          "Successfully registered to system , please login via login page"
        );
      })
      .catch((err) => console.error(err));
  };
  return (
    <div className="container vh-100">
      <div className="row justify-content-center align-items-center h-100">
        <form className="main-login-form col-4 p-5">
          <h1 className="main-title">Register</h1>
          <div className="form-group">
            <input
              type="email"
              className="form-control mt-1"
              placeholder="Enter email"
              name="username"
              value={input.username}
              onChange={(e) =>
                setInput((prevState) => ({
                  ...prevState,
                  username: e.target.value,
                }))
              }
            />
          </div>
          <div className="form-group mt-1">
            <input
              type="password"
              className="form-control"
              placeholder="Password"
              name="password"
              value={input.password}
              onChange={(e) =>
                setInput((prevState) => ({
                  ...prevState,
                  password: e.target.value,
                }))
              }
            />
            {error ? <div>{error}</div> : null}
            {success ? <div>{success}</div> : null}
          </div>
          <div className="container">
            <ul className="login-button-list">
              <li>
                <button
                  type="submit"
                  className="btn btn-primary my-1 mt-2"
                  style={{ padding: "5px 39px" }}
                  onClick={onClickRegister}
                >
                  Register
                </button>
              </li>
              <li>
                <Link href="/login">
                  <button type="submit" className="btn btn-primary my-1">
                    Return To Login
                  </button>
                </Link>
              </li>
            </ul>
          </div>
        </form>
      </div>
    </div>
  );
};

export default register;
