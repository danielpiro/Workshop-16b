import { useEffect, useState } from "react";
import api from "../components/api";
import { useRouter } from "next/router";
import bcrypt from "bcryptjs/dist/bcrypt";
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
    let encryptedPassword = 0;
    const encrypted = async () => await bcrypt.hashSync(loginInput.password, 8);
    await encrypted()
      .then((res) => (encryptedPassword = res))
      .then(() => {
        return api
          .post(
            `/users/login/?userNameLogin=${loginInput.username}&password=${encryptedPassword}`
          )
          .then((res) => {
            if (res.status === 200) {
              const { data } = res;
              console.log(data);
              // setCookie("userId", data, { path: "/", sameSite: true });
              // setCookie("username", loginInput.username, {
              //   path: "/",
              //   sameSite: true,
              // });
              // setCookie("password", loginInput.password, {
              //   path: "/",
              //   sameSite: true,
              // });
            } else {
              const { reason } = res.data;
              createNotification(
                "error",
                "Username or password not match , please try again."
              )();
            }
          })
          .catch((err) => console.log(err));
      });
  };

  const onClickGuest = (e) => {
    e.preventDefault();
    setCookie("type", "guest", { path: "/", sameSite: true });
    api
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

  const onSumbitRegister = (e) => {
    e.preventDefault();
    let encryptedPassword = 0;
    const encrypted = async () =>
      await bcrypt.hashSync(registerInput.password, 8);
    encrypted()
      .then((res) => (encryptedPassword = res))
      .then(() => {
        return api
          .post(
            `/users/signup/?guest_id=${""}&user_name=${
              registerInput.username
            }&password=${encryptedPassword}`
          )
          .then((res) => {
            if (res.status === 200) {
              const { data } = res;
              console.log(data);
              //need to add type and userid to cookies
              createNotification("success", "Register successfully")();
            } else {
              createNotification(
                "error",
                "Username and password was not valid , please try again"
              )();
            }
          });
      });
  };
  return (
    <div className="form-signin w-100 m-auto">
      <form>
        <h1 className="h3 mb-4 fw-normal">Sign in</h1>
        <div class="form-floating">
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
          <label for="floatingInput">Username</label>
        </div>
        <div class="form-floating">
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
          <label for="floatingPassword">Password</label>
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
