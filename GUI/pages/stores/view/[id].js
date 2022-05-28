import { useEffect, useState } from "react";
import Menu from "../../../components/menu";
import Card from "../../../components/card";
import api from "../../../components/api";
import { useRouter } from "next/router";
import createNotification from "../../../components/norification";

const StoreDetails = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const router = useRouter();
  useEffect(() => {
    const fetch = async () => {
      return await api
        .get(`/store/products/?storeId=${router.query.id}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            setProducts(data.value);
            setIsLoading(!isLoading);
          } else {
            createNotification("error", data.reason)();
          }
        })
        .catch((err) => console.log(err));
    };
    fetch();
  }, []);
  return (
    <>
      <Menu />
      <div className="text-center my-5">
        <h1>{router.query.id}</h1>
      </div>
      <div className="row my-4 d-flex justify-content-center">
        {!isLoading ? (
          <div className="w-100">
            <ul className="list-group-dashboard">
              {products.map((product) => {
                return (
                  <li className=" list-group-item" key={product.id}>
                    <Card
                      value={product.id}
                      title={product.name}
                      price={product.price}
                      quantity={product.quantity}
                      category={product.category}
                      storeId={router.query.id}
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

export default StoreDetails;
