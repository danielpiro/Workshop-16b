import AdminMenu from "../components/menus/menuAdmin";
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
  const [page, setPage] = useState(0);
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
  //      + Edit using new method "setUserPermission"
  useEffect(() => {
    const fetchApi = async () => {
      const response = await axios.get("https://dummyjson.com/products");
      const { data } = response;
      setIsLoading(!isLoading);
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

  const onNext = (e) => {
    e.preventDefault();
    console.log("on next", page);
    if (page + 1 > products.length / 12) return;
    setPage(page + 1);
  };

  const onPrevious = (e) => {
    e.preventDefault();
    if (page - 1 < 0) return;
    setPage(page - 1);
  };
  return (
    <>
      {menu}
      <div className="my-4">
        <SearchBar setProducts={setProducts} />
      </div>
      <div className="my-4 d-flex justify-content-center">
        {!isLoading ? (
          <div style={{ display: "table", width: "100%" }}>
            <ul className="list-group-dashboard">
              {products.slice(12 * page, 12 * (page + 1)).map((product) => {
                return (
                  <li className=" list-group-item" key={product.id}>
                    <Card
                      value={product.id}
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
            <nav aria-label="Search results pages">
              <ul className="pagination justify-content-center">
                <li className="page-item">
                  <button
                    className="page-link"
                    onClick={onPrevious}
                    disabled={page === 0 ? true : false}
                  >
                    Previous
                  </button>
                </li>
                <li className="page-item">
                  <button
                    className="page-link ms-3"
                    onClick={onNext}
                    disabled={page === products.length / 12 ? true : false}
                  >
                    Next
                  </button>
                </li>
              </ul>
            </nav>
            <Footer />
          </div>
        ) : (
          <div className="container h-100 my-6">
            <div className="row align-items-center justify-content-center">
              <div className="spinner-border" />
            </div>
          </div>
        )}
      </div>
    </>
  );
};

export default Dashboard;
