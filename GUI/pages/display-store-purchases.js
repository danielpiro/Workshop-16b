import Menu from "../components/menu";
import SearchBar from "../components/search-bar";
import axios from "axios";
import { useState, useEffect } from "react";
import Card from "../components/card";

const DisplayStorePurchases = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [singleProduct, setSingleProduct] = useState({});
  //const [userPermission, setUserPermission] = useState("Admin");

  useEffect(() => {
    const fetchApi = async () => {
      const response = await axios.get("https://dummyjson.com/products");
      // const response =
      //     await api
      //       .post(
      //         `/history/store/?storeId=${""}&userId=${""}`
      //       )
      //       .then((res) => {
      //         if (res.status === 200) {
      //           const { data } = res;
      //           console.log(data);
      //           createNotification("success", "Displaying all purchases successfully", () =>
      //             router.push("/dashboard")
      //           )();
      //         } else {
      //           const { data } = res;
      //           console.log(data);
      //           createNotification("error", "failure displaying all purchases!")();
      //         }
      //       })
      //       .catch((err) => console.log("err"));
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
        <h3>Display Store Purchases</h3>
      </div>
      <div className="my-4">
        <SearchBar setProducts={setProducts} />{" "}
        {/*TODO: Check search button and setSearchValue*/}
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

export default DisplayStorePurchases;
