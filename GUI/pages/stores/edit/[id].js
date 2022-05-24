import { useEffect, useState } from "react";
import Menu from "../../../components/menu";
import StoreProduct from "../../../components/store-product";
import api from "../../../components/api";

const StoreEdit = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetch = async () => {
      return await api
        .get(
          `/store/products/?storeId=${window.location.href.split("/").pop()}`
        )
        .then((res) => {
          if (res.status === 200) {
            setProducts(res.data.value);
            setIsLoading(!isLoading);
          }
        });
    };
    fetch();
  }, []);
  return (
    <>
      <Menu />
      <div className="text-center my-5">
        <h1>{window.location.href.split("/").pop()}</h1>
      </div>
      <div className="row my-4 d-flex justify-content-center">
        {!isLoading ? (
          <div className="w-100">
            <ul className="list-group-dashboard">
              {products.map((product) => {
                return (
                  <li className=" list-group-item" key={product.id}>
                    <StoreProduct
                      value={product.id}
                      title={product.name}
                      price={product.price}
                      quantity={product.quantity}
                      category={product.category}
                      products={products}
                      setProducts={setProducts}
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
      </div>
    </>
  );
};

export default StoreEdit;
