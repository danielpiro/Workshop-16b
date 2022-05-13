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
  const [error, setError] = useState("");
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
  ]);
  const [isSuccess, setIsSuccess] = useState("");
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
    setError("");
    const passwordRegex = /(?=.*[0-9])/;
    const usernameRegex = /(?=.*[A-Za-z])/;
    if (
      loginInput.password.length < 6 ||
      !loginInput.password ||
      !loginInput.username ||
      !passwordRegex.test(loginInput.password) ||
      !usernameRegex.test(loginInput.username)
    ) {
      setError("One of the login information provided is invalid");
      return;
    }
    setCookie("username", loginInput.username, { path: "/", sameSite: true });
    setCookie("password", loginInput.password, { path: "/", sameSite: true });
    const encrypted = async () => await bcrypt.hashSync(loginInput.password, 8);
    encrypted().then((res) => (encryptedPassword = res));
    const post = "post";
    await api.post(`http://eu.httpbin.org/${post}`).then((res) => {
      console.log(res);
    });
    let encryptedPassword = 0;
    // await api
    //   .post(`/login/${loginInput.username}/${encryptedPassword}`)
    //   .then((res) => {
    //     if (res.status === 200) {
    //       const { data } = res;
    //       setCookie("userId", data, { path: "/", sameSite: true });
    //     }
    //   })
    //   .catch((err) => console.log(err));
  };

  const onClickGuest = (e) => {
    e.preventDefault();
    // api.post(`/guest/`).then((res) => {
    //   setCookie("userId", JSON.parse(res.data), { path: "/", sameSite: true });
    // });
    router.push("/dashboard");
  };

  const onSumbitRegister = (e) => {
    e.preventDefault();
    const temp = createNotification("info");
    console.log(temp());

    let encryptedPassword = 0;
    const encrypted = async () =>
      await bcrypt.hashSync(registerInput.password, 8);
    encrypted().then((res) => (encryptedPassword = res));
    // api
    //   .post(`/register/${registerInput.username}/${encryptedPassword}`)
    //   .then((res) => {
    //     if (res.status !== 200) {
    //       setError(res.data);
    //     } else {
    //     }
    //   });
  };

  return (
    <div className="container vh-100">
      <div className="row justify-content-center align-items-center h-100">
        <form className="main-login-form col-4 p-5">
          <h1 className="main-title">Marketplace</h1>
          <input
            type="username"
            className="form-control mt-1"
            placeholder="Enter username"
            name="username"
            value={loginInput.username}
            onChange={(e) =>
              setLoginInput((prevState) => ({
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
            value={loginInput.password}
            onChange={(e) =>
              setLoginInput((prevState) => ({
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
                  type="button"
                  className="btn btn-primary my-1 mt-2"
                  style={{ padding: "5px 56px" }}
                  onClick={onClickLogin}
                >
                  Login
                </button>
              </li>
              <li>
                <button
                  type="button"
                  className="btn btn-primary my-1"
                  style={{ padding: "5px 47px" }}
                  data-bs-toggle="modal"
                  data-bs-target="#register"
                >
                  Register
                </button>
              </li>
              <li>
                <button
                  type="button"
                  className="btn btn-primary my-1"
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
    </div>
  );
};

export default Login;
