import { useEffect, useState } from "react";
import api from "../components/api";
import { useRouter } from "next/router";
import { useCookies } from "react-cookie";
import createNotification from "../components/norification";
import WebSocket from "../components/websocket";

const Login = () => {
  const [loginInput, setLoginInput] = useState({
    username: "",
    password: "",
  });
  const [registerInput, setRegisterInput] = useState({
    username: "",
    password: "",
  });
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
    "session",
  ]);
  const router = useRouter();
  useEffect(() => {
    if (cookies.username && cookies.password) {
      setLoginInput(() => ({
        username: cookies.username,
        password: cookies.password,
      }));
    }
  }, []);

  const onClickLogin = async (e) => {
    e.preventDefault();
    return await api
      .post(
        `/users/login/?userNameLogin=${loginInput.username}&password=${loginInput.password}`
      )
      .then((res) => {
        const { data } = res;
        if (data.success) {
          setCookie("userId", loginInput.username, {
            path: "/",
            sameSite: true,
          });
          setCookie("username", loginInput.username, {
            path: "/",
            sameSite: true,
          });
          setCookie("password", loginInput.password, {
            path: "/",
            sameSite: true,
          });
          setCookie("session", data.value, {
            path: "/",
            sameSite: true,
          });
          createNotification("success", "Logged in successfully", () =>
            router.push("/dashboard")
          )();
          WebSocket(loginInput.username, true);
        } else {
          createNotification("error", data.reason)();
        }
      })
      .then(async () => {
        return await api
          .get(`/permission/type/?userId=${loginInput.username}`)
          .then((res) => {
            const { data } = res;
            if (data.success) {
              console.log(data);
              setCookie("type", data.value, {
                path: "/",
                sameSite: true,
              });
            }
          });
      })
      .catch((err) => console.log(err));
  };

  const onClickGuest = async (e) => {
    e.preventDefault();
    setCookie("type", "guest", { path: "/", sameSite: true });
    return await api
      .get(`/market/guest`)
      .then((res) => {
        const { data } = res;
        if (data.success) {
          setCookie("userId", data.value, {
            path: "/",
            sameSite: true,
          });
          setCookie("session", data.value, {
            path: "/",
            sameSite: true,
          });
          createNotification(
            "success",
            "Create guest account successfully",
            () => router.push("/dashboard")
          )();
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
  };

  const onClickRegister = async (e) => {
    e.preventDefault();
    return await api
      .post(
        `/users/signup/?user_name=${registerInput.username}&password=${registerInput.password}`
      )
      .then((res) => {
        const { data } = res;
        if (data.success) {
          createNotification("success", data.reason)();
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
  };
  return (
    <div className="form-signin w-100 m-auto">
      <form>
        <h1 className="h3 mb-4 fw-normal">Sign in</h1>
        <div className="form-floating">
          <input
            type="email"
            className="form-control"
            id="floatingInput"
            placeholder="Enter username"
            value={loginInput.username}
            onChange={(e) =>
              setLoginInput((prevState) => ({
                ...prevState,
                username: e.target.value,
              }))
            }
          />
          <label htmlFor="floatingInput">Username</label>
        </div>
        <div className="form-floating">
          <input
            type="password"
            className="form-control mt-1"
            id="floatingPassword"
            placeholder="Password"
            value={loginInput.password}
            onChange={(e) =>
              setLoginInput((prevState) => ({
                ...prevState,
                password: e.target.value,
              }))
            }
          />
          <label htmlFor="floatingPassword">Password</label>
        </div>
        <div className="container col-auto">
          <ul className="login-button-list">
            <li>
              <button
                type="submit"
                className="w-100 btn btn-lg btn-primary mb-2"
                style={{ padding: "5px 56px" }}
                onClick={onClickLogin}
              >
                Login
              </button>
            </li>
            <li>
              <button
                type="button"
                className="w-100 btn btn-lg btn-primary mb-2"
                style={{ padding: "5px 47px" }}
                data-bs-toggle="modal"
                data-bs-target="#register"
              >
                Register
              </button>
            </li>
            <li>
              <button
                type="submit"
                className="w-100 btn btn-lg btn-primary"
                onClick={onClickGuest}
              >
                Continue as guest
              </button>
            </li>
          </ul>
        </div>
        <div
          className="modal fade"
          id="register"
          tabIndex="-1"
          role="dialog"
          aria-labelledby="registerTitle"
          aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-centered" role="document">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="registerTitle">
                  Register
                </h5>
              </div>
              <div className="modal-body">
                <div>
                  <input
                    placeholder="Enter username"
                    type="text"
                    value={registerInput.username}
                    onChange={(e) =>
                      setRegisterInput((prevState) => ({
                        ...prevState,
                        username: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="mt-2">
                  <input
                    placeholder="Enter password"
                    type="password"
                    value={registerInput.password}
                    onChange={(e) =>
                      setRegisterInput((prevState) => ({
                        ...prevState,
                        password: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div className="modal-footer">
                <button
                  type="button"
                  className="btn btn-secondary"
                  data-bs-dismiss="modal"
                >
                  Close
                </button>
                <button
                  type="button"
                  className="btn btn-primary"
                  data-bs-dismiss="modal"
                  onClick={onClickRegister}
                >
                  Submit
                </button>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  );
};

export default Login;
