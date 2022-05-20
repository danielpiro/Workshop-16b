import Link from "next/link";
import createNotification from "../norification";
import { useEffect, useState } from "react";

const Menu = () => {
  const [registerInput, setRegisterInput] = useState({
    username: "",
    password: "",
  });

  const onSumbitRegister = (e) => {
    e.preventDefault();
    let encryptedPassword = 0;
    const encrypted = async () =>
      await bcrypt.hashSync(registerInput.password, 8);
    encrypted().then((res) => (encryptedPassword = res));
    api
      .post(`/register/${registerInput.username}/${encryptedPassword}`)
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
                <li className="my-bids-button nav-item">
                  <Link href="#">
                    <a
                      className="nav-link ms-4"
                      onClick={createNotification("info", "message")}
                    >
                      My Bids
                    </a>
                  </Link>
                </li>
                <li className="history-button nav-item">
                  <Link href="#">
                    <a
                      className="nav-link ms-4"
                      onClick={createNotification("info", "message")}
                    >
                      History
                    </a>
                  </Link>
                </li>
                <li>
                  <Link href="#">
                    <a
                      type="button"
                      className="nav-link ms-4"
                      data-bs-toggle="modal"
                      data-bs-target="#register"
                    >
                      Become a member
                    </a>
                  </Link>
                </li>
                {/* <li className="become-a-member-button nav-item">
                  <Link href="#">
                    <a className="nav-link ms-4">Become a member</a>
                  </Link>
                </li> */}
                <li className="logout-button nav-item">
                  <Link href="/login">
                    <a className="nav-link ms-4">Logout</a>
                  </Link>
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
    </div>
  );
};

export default Menu;
