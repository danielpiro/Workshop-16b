import Menu from "../components/menu";
import axios from "axios";
import { useState, useEffect } from "react";
import Link from "next/link";

const CloseStore = () => {
  const [store, setStore] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [searchValue, setSearchValue] = useState("");

  const onSearch = async (e) => {
    e.preventDefault();
    setIsLoading(!isLoading);
    if (searchValue !== "") {
      await api
        .post(`/store/freeze/storeId=${store}?&userId=${""}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            const { data } = res;
            console.log(data);
            createNotification("success", "Close store done successfully", () =>
              router.push("/dashboard")
            )();
          } else {
            createNotification("error", data.reason)();
          }
        })
        .catch((err) => console.log(err));
    } else {
      createNotification(
        "error",
        "store name is not valid, please try again!"
      )();
    }
  };

  const onChange = (e) => {
    e.preventDefault();
    setSearchValue(e.target.value);
  };

  const onCloseStore = (e) => {
    e.preventDefault();
    console.log(e.target.value);
  };

  return (
    <>
      <Menu />
      <div className="container m-auto w-100">
        <span className="text-center m-3">
          {" "}
          <h3>Close store</h3>{" "}
        </span>
        <span className="text-center">
          {" "}
          <h5>(freezing store actions)</h5>{" "}
        </span>

        <nav className="navbar navbar-expand-lg bg-secondery d-flex justify-content-center">
          <form className="row form-inline col-4">
            <input
              className="form-control mr-sm-2 m-2"
              type="search"
              placeholder="Enter store name"
              aria-label="Search"
              onChange={onChange}
            />

            <div className="d-flex justify-content-center">
              <button className="btn btn-primary my-3" onClick={onSearch}>
                Search
              </button>
            </div>
          </form>
        </nav>
        <ul>
          <div class="card text-center">
            <div class="card-body">
              <h3 class="card-title">Store # ...</h3>
              <p class="card-text">
                With supporting text below as a natural lead-in to additional
                content.
              </p>
              <button class="btn btn-primary" onClick={onCloseStore}>
                Close store
              </button>
            </div>
          </div>
          {!isLoading ? (
            store.map((shop) => {
              return (
                <div>
                  <li key={shop.id}>{shop}</li>
                </div>
              );
            })
          ) : (
            <div className="container">
              <div className="d-flex justify-content-center">
                <div className="spinner-border my-5 me-4" />
              </div>
            </div>
          )}
        </ul>
      </div>
    </>
  );
};

export default CloseStore;
