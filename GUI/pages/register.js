import { useState } from "react";
import Link from "next/link";
const register = () => {
  const [input, setInput] = useState({
    username: "",
    password: "",
  });
  const [toLogin, setToLogin] = useState(false);
  const onClickRegister = (event) => {
    event.preventDefault();
    if (toLogin) {
      onClickToLogin();
      return;
    }
    console.log(input.username, input.password);
  };
  const onClickToLogin = () => {
    console.log("to login");
    setToLogin(!toLogin);
  };
  return (
    <div className="container vh-100">
      <div className="row justify-content-center align-items-center h-100">
        <form className="main-login-form col-4 p-5" onSubmit={onClickRegister}>
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
          </div>
          <div className="container">
            <ul className="login-button-list">
              <li>
                <button
                  type="submit"
                  className="btn btn-primary my-1 mt-2"
                  style={{ padding: "5px 39px" }}
                >
                  Register
                </button>
              </li>
              <li>
                <Link href="/login">
                  <button
                    type="submit"
                    className="btn btn-primary my-1"
                    onClick={() => setToLogin(!toLogin)}
                  >
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
