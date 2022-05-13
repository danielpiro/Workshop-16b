import AdminMenu from "../components/menus/menuAdmin";
import StoreOwnerMenu from "../components/menus/menuStoreOwner";
import StoreManagerMenu from "../components/menus/menuStoreManager";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import SearchBar from "../components/search-bar";
import axios from "axios";
import { useState, useEffect } from "react";
import Card from "../components/card";
import Footer from "../components/footer";

const Dashboard = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [page, setPage] = useState(10);
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
  //      + Edit using new method "setUserPermission"
  useEffect(() => {
    const fetchApi = async () => {
      const response = await axios.get("https://dummyjson.com/products");
      setIsLoading(!isLoading);
      const { data } = response;
      setProducts(data.products);
    };
    fetchApi();
  }, []);

  let menu;
  if (userPermission == "Admin") {
    menu = <AdminMenu />;
  } else if (userPermission == "Owner") {
    menu = <StoreOwnerMenu />;
  } else if (userPermission == "Manager") {
    menu = <StoreManagerMenu />;
  } else if (userPermission == "Subscriber") {
    menu = <SubscriberMenu />;
  } else {
    menu = <GuestMenu />;
  }

  return (
    <>
      {menu}
      <div className="my-4">
        <SearchBar setProducts={setProducts} />
      </div>
      <div
        className="my-4"
        style={{ display: "flex", justifyContent: "center" }}
      ></div>
      {!isLoading ? (
        <div style={{ display: "table", width: "100%" }}>
          <ul className="list-group-dashboard">
            {products.map((product) => {
              return (
                <li className=" list-group-item" key={product.id}>
                  <Card
                    value={product.id}
                    image={product.images[0]}
                    title={product.title}
                    category={product.category}
                    description={product.description}
                    price={product.price}
                    discount={product.discountPercentage}
                  />
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

export default Dashboard;
