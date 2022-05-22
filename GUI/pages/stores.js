import Menu from "../components/menu";
import SearchBar from "../components/search-bar";
import axios from "axios";
import { useState, useEffect } from "react";
import { useRouter } from "next/router";
import StoreCardStores from "../components/store-card-stores";

const Stores = () => {
  const router = useRouter();
  const [stores, setStores] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  //const [page, setPage] = useState(10);
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
  //      + Edit using new method "setUserPermission"

  useEffect(() => {
    const fetchApi = async () => {
      const response = await axios.get("https://dummyjson.com/products");
      setIsLoading(!isLoading);
      const { data } = response;
      setStores(data.products); //TODO: Set up all stores
    };
    fetchApi();
  }, []);

  return (
    <>
      <Menu />
      <div className="my-4">
        <SearchBar setStores={setStores} />
      </div>
      {!isLoading ? (
        <div className="row">
          <ul className="list-group-dashboard">
            {stores.map((store) => {
              return (
                <li className="list-group-item" key={store.id}>
                  <StoreCardStores />
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
    </>
  );
};

export default Stores;
