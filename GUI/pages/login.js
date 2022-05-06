import { useState } from "react";
import Link from "next/link";
import api from "../components/api";
const login = () => {
  const [input, setInput] = useState({
    username: "",
    password: "",
  });
  const [error, setError] = useState("");
  const onClickLogin = (e) => {
    e.preventDefault();
    setError("");
    const regex =
      /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    if (regex.test(input.username) === false) {
      setError("Please insert a valid email");
      return;
    }
    api
      .post("/login", JSON.stringify(input))
      .then((res) => {
        if (res.status !== 200) {
          setError("cannot login");
          return;
        }
        const response = res.data;
        window.userid = res.data.id;
        console.log("data", response);
      })
      .catch((err) => console.error(err));
  };

  return (
    <div className="container vh-100">
      <div className="row justify-content-center align-items-center h-100">
        <form className="main-login-form col-4 p-5">
          <h1 className="main-title">Marketplace</h1>
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
          <input
            type="password"
            className="form-control mt-1"
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
          <div className="container col-auto">
            <ul className="login-button-list">
              <li>
                <button
                  type="submit"
                  className="btn btn-primary my-1 mt-2"
                  style={{ padding: "5px 56px" }}
                  onClick={onClickLogin}
                >
                  Login
                </button>
              </li>
              <li>
                <Link href="/register">
                  <button
                    type="register"
                    className="btn btn-primary my-1"
                    style={{ padding: "5px 47px" }}
                  >
                    Register
                  </button>
                </Link>
              </li>
              <li>
                <Link href="/dashboard">
                  <button type="guest" className="btn btn-primary my-1">
                    Continue as guest
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

export default login;
