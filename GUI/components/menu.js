import Link from "next/link";
import logout from "./logout";

const Menu = () => {
  return (
    <div className="flex">
      <div className="navbar-brand navbar navbar-expand-lg navbar-dark bg-primary">
        <Link href="/dashboard">
          <a className="ms-5">
            <h2>Marketplace</h2>
          </a>
        </Link>
        <div className="container">
          <div className="collapse navbar-collapse justify-content-center align-content-center">
            <ul className="navbar-nav" id="navmenu">
              <li className="nav-item">
                <a href="/shopping-cart" className="nav-link">
                  Shopping cart
                </a>
              </li>
              <li className="nav-item">
                <a href="#" className="nav-link ms-4">
                  Store management
                </a>
              </li>
              <li className="nav-item">
                <a href="#" className="nav-link ms-4">
                  Admin action
                </a>
              </li>
              <li className="nav-item">
                <a href="#" className="nav-link ms-4">
                  My bids
                </a>
              </li>
              <li className="nav-item">
                <a href="#" className="nav-link ms-4">
                  History
                </a>
              </li>
              <li className="nav-item">
                <a href="#" className="nav-link ms-4">
                  Statistics
                </a>
              </li>
              <li className="logout-button nav-item">
                <a
                  className="nav-link ms-4"
                  onClick={(e) => logout(e, window.userid)}
                >
                  Logout
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Menu;
