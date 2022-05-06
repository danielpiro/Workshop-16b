import Menu from "../components/menu";
import SearchBar from "../components/searchbar";
import axios from "axios";
import { useState, useEffect } from "react";
import Card from "../components/card";
import Footer from "../components/footer";

const Dashboard = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  useEffect(() => {
    const fetchApi = async () => {
      const response = await axios.get("https://fakestoreapi.com/products");
      setIsLoading(!isLoading);
      const { data } = response;
      setProducts(data);
    };
    fetchApi();
  }, []);

  return (
    <>
      <Menu />
      <div className="my-4">
        <SearchBar setProducts={setProducts} />
      </div>
      <div
        className="my-4"
        style={{ display: "flex", justifyContent: "center" }}
      ></div>
      {!isLoading ? (
        <div style={{ display: "table", width: "100%" }}>
          <ul className="list-group" style={{ display: "table-cell" }}>
            {products.map((product) => {
              return (
                <li className=" list-group-item" key={product.id}>
                  <Card
                    value={product.id}
                    image={product.image}
                    title={product.title}
                    description={product.description}
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
