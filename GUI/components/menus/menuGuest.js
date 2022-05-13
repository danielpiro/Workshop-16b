import Link from "next/link";

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
              <li className="my-bids-button nav-item">
                <Link href="/my-bids">
                  <a className="nav-link ms-4">My Bids</a>
                </Link>
              </li>
              <li className="history-button nav-item">
                <Link href="/user-history">
                  <a className="nav-link ms-4">History</a>
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
