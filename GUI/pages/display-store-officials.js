import Menu from "../components/menu";
import SearchBar from "../components/search-bar";
import axios from "axios";
import { useState, useEffect } from "react";
import Card from "../components/card";
import Footer from "../components/footer";

const DisplayStoreOfficials = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [singleProduct, setSingleProduct] = useState({});
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

  const getSingleProduct = (id) => {
    products.map((product) => {
      if (product.id === id) {
        return setSingleProduct(product);
      }
      return null;
    });
  };

  return (
    <>
      <Menu />
      <div className="card-header">
        <h3>Display Store Officials</h3>
      </div>
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
                    image={product.images[0]}
                    title={product.title}
                    category={product.category}
                    description={product.description}
                    price={product.price}
                    discount={product.discountPercentage}
                    getSingleProduct={getSingleProduct}
                    singleProduct={singleProduct}
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
    </>
  );
};

export default DisplayStoreOfficials;
