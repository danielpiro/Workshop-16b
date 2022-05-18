import AdminMenu from "../components/menus/menuAdmin";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import SearchBar from "../components/search-bar";
import axios from "axios";
import { useState, useEffect } from "react";
import Card from "../components/card";
import Footer from "../components/footer";
import { useRouter } from "next/router";

const Stores = () => {
  const router = useRouter();
  const [stores, setStores] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  //const [page, setPage] = useState(10);
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
  //      + Edit using new method "setUserPermission"

  const onShopStore = (e) => {
    router.push("/dashboard") //TODO: Add page of a store with its products
  }

  const onManageStore = (e) => {
    router.push("/store-management")
}

  useEffect(() => {
    const fetchApi = async () => {
      const response = await axios.get("https://dummyjson.com/products");
      setIsLoading(!isLoading);
      const { data } = response;
      setStores(data.products); //TODO: Set up all stores
    };
    fetchApi();
  }, []);

  let menu;
  if (userPermission == "Admin") {
    menu = <AdminMenu />;
  } else if (userPermission == "Subscriber") {
    menu = <SubscriberMenu />;
  } else {
    menu = <GuestMenu />;
  }

  return (
    <>
      {menu}
      <div className="my-4">
        <SearchBar setStores={setStores} />
      </div>
      <div
        className="my-4"
        style={{ display: "flex", justifyContent: "center" }}
      ></div>
      {!isLoading ? (
        <div style={{ display: "table", width: "100%" }}>
          <ul className="list-group-dashboard">
            {stores.map((store) => {
              return (
                <li className=" list-group-item" key={store.id}>
                   <div className="card w-100 m-1">
                        <div className="card-body">
                            <div className="row">
                                <div className="col">
                                    <h5 className="card-title">{store.id}</h5>
                                </div>
                                <div className="col">
                                    <p className="card-desc">{store.title}</p>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col m-1" style={{ display: "flex", width: "40%" }}>
                                    <button className="btn btn-primary mr-lg-3" onClick={onShopStore}>
                                        Shop Now
                                    </button>
                                </div>
                                <div className="col m-1" style={{ display: "flex", width: "40%" }}>
                                    <button className="btn btn-primary mr-lg-3" onClick={onManageStore}>
                                        Manage Store
                                    </button>
                                </div>
                            </div>    
                        </div>
                    </div>
                </li>
              );
            })}
          </ul>
        </div>
      ) : (
        <div className="container h-100 my-6">
          <div className="row align-items-center justify-content-center">
            <div className="spinner-border" />
          </div>
        </div>
      )}
      <Footer />
    </>
  );
};

export default Stores;
