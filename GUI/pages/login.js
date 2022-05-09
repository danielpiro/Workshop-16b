import { useState } from "react";
import api from "../components/api";
import { useRouter } from "next/router";

const Login = () => {
  const [input, setInput] = useState({
    username: "",
    password: "",
  });
  const [error, setError] = useState("");
  const router = useRouter();
  const onClickLogin = (e) => {
    e.preventDefault();
    setError("");
    api
      .post("/login", JSON.stringify(input))
      .then((res) => {
        if (res.status !== 200) {
          setError(res.data.message);
          return;
        }
        const { data } = res;
        window.userid = data.id;
        console.log("data", response);
      })
      .catch((err) => console.error(err));
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
