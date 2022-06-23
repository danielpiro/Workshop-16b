import Link from "next/link";
import { useCookies } from "react-cookie";
import { useState } from "react";
import createNotification from "./norification";
import api from "./api";
import { useRouter } from "next/router";
import WebSocket from "./websocket";

const Menu = () => {
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  const [registerInput, setRegisterInput] = useState({
    username: "",
    password: "",
  });
  const router = useRouter();

  const onUpgrade = async (e) => {
    e.preventDefault();
    return await api
      .post(
        `/users/signup/?user_name=${registerInput.username}&password=${registerInput.password}`,
        null,
        {
          headers: {
            Auth: cookies.session,
          },
        }
      )
      .then((res) => {
        const { data } = res;
        if (!data.success) {
          return createNotification("error", data.reason)();
        }
      })
      .then(async () => {
        return await api
          .post(
            `/guest/login/?guestId=${cookies.userId}&userNameLogin=${registerInput.username}&password=${registerInput.password}`
          )
          .then((res) => {
            const { data } = res;
            if (data.success) {
              setCookie("userId", registerInput.username, {
                path: "/",
                sameSite: true,
              });
              setCookie("username", registerInput.username, {
                path: "/",
                sameSite: true,
              });
              setCookie("password", registerInput.password, {
                path: "/",
                sameSite: true,
              });
              setCookie("type", "subscriber", {
                path: "/",
                sameSite: true,
              });
              createNotification(
                "success",
                "Upgrade successfully , Thanks for becoming a member"
              )();
            }
          });
      })
      .catch((err) => console.log(err));
  };

  const logoutGuest = async (e) => {
    e.preventDefault();
    return await api
      .post(`/users/?guestId=${cookies.userId}`)
      .then((res) => {
        const { data } = res;
        if (data.success) {
          WebSocket(cookies.userId, false);
          return createNotification(
            "success",
            `${cookies.userId} logged out successfully`,
            () => {
              return router.push("/login");
            }
          )();
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
  };

  const logout = async (e) => {
    e.preventDefault();
    return await api
      .post(`/users/logout/?user_name=${cookies.username}`)
      .then((res) => {
        const { data } = res;
        if (data.success) {
          WebSocket(cookies.userId, false);
          return createNotification(
            "success",
            `${cookies.username} logged out successfully`,
            () => {
              return router.push("/login");
            }
          )();
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
  };

  return (
    <>
      {cookies.type === "admin" ? ( //Admin Menu
        <div className="flex">
          <div className="navbar navbar-expand-lg navbar-dark bg-primary">
            <Link href="/dashboard">
              <a className="ms-5">
                <h2>Marketplace</h2>
              </a>
            </Link>
            <div className="container">
              <button
                className="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navmenu"
              >
                <span className="navbar-toggler-icon" />
              </button>
              <div className="collapse navbar-collapse justify-content-center align-content-center">
                <ul className="navbar-nav" id="navmenu">
                  <li className="shopping-cart-button nav-item" key="cart">
                    <Link href="/shopping-cart">
                      <a className="nav-link ms-4">Shopping Cart</a>
                    </Link>
                  </li>
                  <li
                    className="open-new-store-button nav-item"
                    key="new store"
                  >
                    <Link href="/open-new-store">
                      <a className="nav-link ms-4">Open New Store</a>
                    </Link>
                  </li>
                  <li className="store-management-button nav-item" key="stores">
                    <Link href="/stores">
                      <a className="nav-link ms-4">Stores</a>
                    </Link>
                  </li>
                  <li
                    className="admin-actions-button nav-item"
                    key="admin action"
                  >
                    <Link href="/admin-actions">
                      <a className="nav-link ms-4">Admin Actions</a>
                    </Link>
                  </li>
                  <li className="history-button nav-item" key="history">
                    <Link href="/user-history">
                      <a className="nav-link ms-4">History</a>
                    </Link>
                  </li>
                  <li
                    className="notifications-button nav-item"
                    key="notifications"
                  >
                    <Link href="/notifications-page">
                      <a className="nav-link ms-4">Notifications</a>
                    </Link>
                  </li>
                  <li className="statistics-button nav-item" key="stats">
                    <Link href="/statistics">
                      <a className="nav-link ms-4">Statistics</a>
                    </Link>
                  </li>
                  <li className="logout-button nav-item" key="logout">
                    <a href="#" className="nav-link ms-4" onClick={logout}>
                      Logout
                    </a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      ) : cookies.type === "guest" ? ( //Guest Menu
        <div>
          <div className="flex">
            <div className="navbar navbar-expand-lg navbar-dark bg-primary">
              <Link href="/dashboard">
                <a className="ms-5">
                  <h2>Marketplace</h2>
                </a>
              </Link>
              <div className="container">
                <button
                  className="navbar-toggler"
                  type="button"
                  data-bs-toggle="collapse"
                  data-bs-target="#navmenu"
                >
                  <span className="navbar-toggler-icon" />
                </button>
                <div className="collapse navbar-collapse justify-content-center align-content-center">
                  <ul className="navbar-nav" id="navmenu">
                    <li className="shopping-cart-button nav-item">
                      <Link href="/shopping-cart">
                        <a className="nav-link ms-4">Shopping Cart</a>
                      </Link>
                    </li>
                    <li className="store-management-button nav-item">
                      <Link href="/stores">
                        <a className="nav-link ms-4">Stores</a>
                      </Link>
                    </li>
                    <li>
                      <a
                        type="button"
                        className="nav-link ms-4"
                        data-bs-toggle="modal"
                        data-bs-target="#register"
                      >
                        Become a member
                      </a>
                    </li>
                    <li className="logout-button nav-item">
                      <a
                        href="#"
                        className="nav-link ms-4"
                        onClick={logoutGuest}
                      >
                        Logout
                      </a>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
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
                    Become a member
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
                      type="password"
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
                    onClick={onUpgrade}
                  >
                    Submit
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      ) : (
        //Subscriber Menu
        <div className="flex">
          <div className="navbar navbar-expand-lg navbar-dark bg-primary">
            <Link href="/dashboard">
              <a className="ms-5">
                <h2>Marketplace</h2>
              </a>
            </Link>
            <div className="container">
              <button
                className="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navmenu"
              >
                <span className="navbar-toggler-icon" />
              </button>
              <div className="collapse navbar-collapse justify-content-center align-content-center">
                <ul className="navbar-nav" id="navmenu">
                  <li className="shopping-cart-button nav-item">
                    <Link href="/shopping-cart">
                      <a className="nav-link ms-4">Shopping Cart</a>
                    </Link>
                  </li>
                  <li className="open-new-store-button nav-item">
                    <Link href="/open-new-store">
                      <a className="nav-link ms-4">Open New Store</a>
                    </Link>
                  </li>
                  <li className="store-management-button nav-item">
                    <Link href="/stores">
                      <a className="nav-link ms-4">Stores</a>
                    </Link>
                  </li>
                  <li className="history-button nav-item">
                    <Link href="/user-history">
                      <a className="nav-link ms-4">History</a>
                    </Link>
                  </li>
                  <li className="notify-admins-button nav-item">
                    <Link href="/notify-admins">
                      <a className="nav-link ms-4">Notify Admins</a>
                    </Link>
                  </li>
                  <li className="logout-button nav-item">
                    <a href="" className="nav-link ms-4" onClick={logout}>
                      Logout
                    </a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default Menu;
