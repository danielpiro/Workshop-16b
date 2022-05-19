import Link from "next/link";
import createNotification from "../norification";

const Menu = () => {
  return (
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
              {/* <li className="store-management-button nav-item">
                <Link href="/store-management">
                  <a className="nav-link ms-4">Store Management</a>
                </Link>
              </li> */}
              <li className="store-management-button nav-item">
                <Link href="/stores">
                  <a className="nav-link ms-4">Stores</a>
                </Link>
              </li>
              <li className="admin-actions-button nav-item">
                <Link href="/admin-actions">
                  <a className="nav-link ms-4">Admin Actions</a>
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
              <li className="notify-admins-button nav-item">
                  <Link href="/notify-admins">
                    <a className="nav-link ms-4">Notify Admins</a>
                  </Link>
              </li>
              <li className="notifications-button nav-item">
                  <Link href="#">
                    <a
                      className="nav-link ms-4"
                      onClick={createNotification("info", "message")}
                    >
                      Notifications
                    </a>
                  </Link>
              </li>
              <li className="statistics-button nav-item">
                <Link href="/statistics">
                  <a className="nav-link ms-4">Statistics</a>
                </Link>
              </li>
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
  );
};

export default Menu;
