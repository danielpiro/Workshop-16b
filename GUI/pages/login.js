import { useEffect, useState } from "react";
import api from "../components/api";
import { useRouter } from "next/router";
import bcrypt from "bcryptjs/dist/bcrypt";
import { useCookies } from "react-cookie";

const Login = () => {
  const [input, setInput] = useState({
    username: "",
    password: "",
  });
  const [error, setError] = useState("");
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
  ]);
  const router = useRouter();
  useEffect(() => {
    if (cookies.username === "asdasd" && cookies.password === "123123") {
      router.push("/dashboard");
    }
  }, []);

  const onClickLogin = async (e) => {
    e.preventDefault();
    setError("");
    const passwordRegex = /(?=.*[0-9])/;
    const usernameRegex = /(?=.*[A-Za-z])/;
    if (
      input.password.length < 6 ||
      !input.password ||
      !input.username ||
      !passwordRegex.test(input.password) ||
      !usernameRegex.test(input.username)
    ) {
      setError("One of the login information provided is invalid");
      return;
    }
    setCookie("username", input.username, { path: "/", sameSite: true });
    setCookie("password", input.password, { path: "/", sameSite: true });
    const encrypted = async () => await bcrypt.hashSync(input.password, 8);
    await api
      .post(
        "/login",
        JSON.stringify({
          username: input.username,
          password: encrypted,
        })
      )
      .then((res) => {
        const { data } = res;
        console.log(data);
      })
      .catch((err) => console.log(err));
  };

  const onClickGuest = (e) => {
    e.preventDefault();
    //need to do api to backend and get guestid
    router.push("/dashboard");
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
                      placeholder="username"
                      value={input.username}
                      onChange={(e) =>
                        setInput((prevState) => ({
                          ...prevState,
                          username: e.target.value,
                        }))
                      }
                    />
                  </div>
                  <div>
                    <input
                      placeholder="password"
                      value={input.password}
                      onChange={(e) =>
                        setInput((prevState) => ({
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
                  <button type="button" className="btn btn-primary">
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
