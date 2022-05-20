import Menu from "../components/menu";
import axios from "axios";
import { useState, useEffect } from "react";
import Link from "next/link";
import Footer from "../components/footer";

const StoreManagement = () => {
  const [userPermission, setUserPermission] = useState("Admin"); //= useState(""); //TODO: Need to change to Guest when logic is ready!

  useEffect(() => {
    const fetchPermission = async () => {
      const response = await axios.get("users/getUserPermission");
      setIsLoading(!isLoading);
      setUserPermission(response.data);
    };
    fetchPermission();
  }, []);

  return (
    <>
      <Menu />
      <div
        className="my-4"
        style={{ display: "flex", justifyContent: "center" }}
      >
        <h1>Stores</h1>
      </div>
      <div className="containter">
        <div className="row">
          <div className="col">
            <div
              className="card"
              style={{ display: userPermission == "Admin" ? "block" : "none" }}
            >
              <div className="card-body">
                <Link href="/add-new-store-supply">
                  <a>Add new store's supply/products</a>
                </Link>
              </div>
            </div>
          </div>
          <div className="col">
            <div
              className="card"
              style={{ display: userPermission == "Admin" ? "block" : "none" }}
            >
              <div className="card-body">
                <Link href="/edit-store-supply">
                  <a>Edit store's supply/products</a>
                </Link>
              </div>
            </div>
          </div>
        </div>
        <br />
        <div className="row">
          <div className="col">
            <div
              className="card"
              style={{ display: userPermission == "Admin" ? "block" : "none" }}
            >
              <div className="card-body">
                <Link href="/fire-owner-manager-to-store">
                  <a>Fire existing Store Manager in store</a>
                </Link>
              </div>
            </div>
          </div>
          <div className="col">
            <div
              className="card"
              style={{ display: userPermission == "Admin" ? "block" : "none" }}
            >
              <div className="card-body">
                <Link href="/change-policy">
                  <a>Change store's policy</a>
                </Link>
              </div>
            </div>
          </div>
        </div>
        <br />
        <div className="row">
          <div className="col">
            <div
              className="card"
              style={{ display: userPermission == "Admin" ? "block" : "none" }}
            >
              <div className="card-body">
                <Link href="/hire-owner-manager-to-store">
                  <a>Hire new Store Owner/Manager to store</a>
                </Link>
              </div>
            </div>
          </div>
          <div className="col">
            <div
              className="card"
              style={{ display: userPermission == "Admin" ? "block" : "none" }}
            >
              <div className="card-body">
                <Link href="/change-store-manager-permissions">
                  <a>Change store's manager permissions</a>
                </Link>
              </div>
            </div>
          </div>
        </div>
        <br />
        <div className="row">
          <div className="col">
            <div
              className="card"
              style={{ display: userPermission == "Admin" ? "block" : "none" }}
            >
              <div className="card-body">
                <Link href="/display-store-purchases">
                  <a>Display purchases of store</a>
                </Link>
              </div>
            </div>
          </div>
          <div className="col">
            <div
              className="card"
              style={{ display: userPermission == "Admin" ? "block" : "none" }}
            >
              <div className="card-body">
                <Link href="/close-store">
                  <a>Close a store</a>
                </Link>
              </div>
            </div>
          </div>
        </div>
        <br />
        <div className="row">
          <div className="col">
            <div
              className="card"
              style={{ display: userPermission == "Admin" ? "block" : "none" }}
            >
              <div className="card-body">
                <Link href="/display-store-officials">
                  <a>Display officials info in store</a>
                </Link>
              </div>
            </div>
          </div>
          <div className="col">
            <div
              className="card"
              style={{ display: userPermission == "Admin" ? "block" : "none" }}
            >
              <div className="card-body">
                <Link href="/discounts">
                  <a>Add/Edit discounts</a>
                </Link>
              </div>
            </div>
          </div>
        </div>
        <br />
      </div>

      <div className="modal" tabindex="-1">
        <div className="modal-dialog">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title">Modal title</h5>
              <button
                type="button"
                className="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div className="modal-body">
              <p>Modal body text goes here.</p>
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
                Save changes
              </button>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default StoreManagement;
