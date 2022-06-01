import Menu from "../components/menu";
import { useState } from "react";
import Link from "next/link";
import { useRouter } from "next/router";
import api from "../components/api";
import createNotification from "../components/norification";
import { useCookies } from "react-cookie";

const StoreManagement = () => {
  const [userPermission, setUserPermission] = useState("Admin");
  const router = useRouter();
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);
  const removeStore = async (e) => {
    e.preventDefault();
    const id = Object.keys(router.query)[0];
    return await api
      .post(`/store/delete/?userId=${cookies.userId}&storeId=${id}`)
      .then((res) => {
        const { data } = res;
        console.log(cookies);
        if (data.success) {
          createNotification(
            "success",
            "Remove " + id + " successfully",
            () => {
              return router.push("/stores");
            }
          )();
        } else {
          createNotification("error", data.reason)();
        }
      });
  };
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
                <a href="#" onClick={removeStore}>
                  Remove store
                </a>
              </div>
            </div>
          </div>
          <div className="col">
            <div
              className="card"
              style={{ display: userPermission == "Admin" ? "block" : "none" }}
            >
              <div className="card-body">
                <Link
                  href={`stores/edit/${Object.keys(router.query)[0]}`}
                  key={Object.keys(router.query)[0]}
                >
                  <a>Add/Edit store's products</a>
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
                <Link
                  href={{
                    pathname: "/fire-owner-manager-to-store",
                    query: Object.keys(router.query)[0],
                  }}
                >
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
                <Link
                  href={{
                    pathname: "/change-policy",
                    query: Object.keys(router.query)[0],
                  }}
                >
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
                <Link
                  href={{
                    pathname: "/hire-owner-manager-to-store",
                    query: Object.keys(router.query)[0],
                  }}
                >
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
                <Link
                  href={{
                    pathname: "/change-store-manager-permissions",
                    query: Object.keys(router.query)[0],
                  }}
                >
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
                <Link
                  href={{
                    pathname: "/display-store-purchases",
                    query: Object.keys(router.query)[0],
                  }}
                >
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
                <Link
                  href={{
                    pathname: "/close-store",
                    query: Object.keys(router.query)[0],
                  }}
                >
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
                <Link
                  href={{
                    pathname: "/display-store-officials",
                    query: Object.keys(router.query)[0],
                  }}
                >
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
                <Link
                  href={{
                    pathname: "/discounts",
                    query: Object.keys(router.query)[0],
                  }}
                >
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
    </>
  );
};

export default StoreManagement;
