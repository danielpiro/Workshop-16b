import Link from "next/link";
import logout from "./logout";
const Menu = () => {
  return (
    <div className="flex">
      <div className="navbar navbar-expand-lg navbar-dark bg-primary">
        <Link href="/dashboard">
          <a>
            <h3>
              <strong>Marketplace</strong>
            </h3>
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
              <li className="nav-item">
                <a href="/shopping-cart" className="nav-link">
                  Shopping Cart
                </a>
              </li>
              <li className="nav-item">
                <a href="/store-management" className="nav-link ms-4">
                  Store Management
                </a>
              </li>
              <li className="nav-item">
                <a href="/admin-actions" className="nav-link ms-4">
                  Admin Action
                </a>
              </li>
              <li className="nav-item">
                <a href="/user-bids" className="nav-link ms-4">
                  My Bids
                </a>
              </li>
              <li className="nav-item">
                <a href="/user-history" className="nav-link ms-4">
                  History
                </a>
              </li>
              <li className="nav-item">
                <a href="#" className="nav-link ms-4">
                  Statistics
                </a>
              </li>
              <li className="logout-button nav-item">
                <Link href="/login">
                  <a
                    className="nav-link ms-4"
                    onClick={(e) => logout(window.userid)}
                  >
                    Logout
                  </a>
                </Link>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Menu;
