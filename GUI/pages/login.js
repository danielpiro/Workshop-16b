import { useEffect, useState } from "react";
import api from "../components/api";
import { useRouter } from "next/router";
import { useCookies } from "react-cookie";
import createNotification from "../components/norification";

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
        console.log(data.value);
        if (res.status === 200) {
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
          setCookie("type", data.value, {
            path: "/",
            sameSite: true,
          });
          createNotification(
            "success",
            "Login done successfully",
            () => router.push("/dashboard")
          )();
          router.push("/dashboard");
        }
        else {
          console.log("amit")
          createNotification(
            "error",
            "Cannot Login, please try again"
          )();
        }
      })
      .catch((err) => createNotification(
            "error",
            "Cannot Login, please try again\n" + err
          )());
  };

  const onClickGuest = async (e) => {
    e.preventDefault();
    setCookie("type", "guest", { path: "/", sameSite: true });
    return await api
      .get(`/market/guest`)
      .then((res) => {
        if (res.status === 200) {
          const { data } = res;
          setCookie("userId", data.value, {
            path: "/",
            sameSite: true,
          });
          createNotification(
            "success",
            "Create guest account successfully",
            () => router.push("/dashboard")
          )();
        } else {
          createNotification(
            "error",
            "Cannot create guest account , please try again"
          )();
        }
      })
      .catch((err) => createNotification("error", err.message)());
  };

  const onSumbitRegister = async (e) => {
    e.preventDefault();
    return await api
      .post(
        `/users/signup/?user_name=${registerInput.username}&password=${registerInput.password}`
      )
      .then((res) => {
        if (res.status === 200) {
          createNotification("success", "Register successfully")();
        } else {
          createNotification(
            "error",
            "Username and password was not valid , please try again"
          )();
        }
      });
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
                  onClick={onSumbitRegister}
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
