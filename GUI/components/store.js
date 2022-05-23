import { useEffect, useState } from "react";
import StoreProduct from "./store-product";

const Store = ({ store, storeMap }) => {
  const [products, setProducts] = useState([]);
  // useEffect(() => {
  //   const fetch = () => {
  //     return getProducts();
  //   };
  //   fetch();
  // }, []);

  // const getProducts = () => {
  //   storeMap.map((item) => {
  //     if (Object.keys(item)[0] === store) {
  //       setProducts(Object.values(item));
  //       return;
  //     }
  //   });
  // };

  return (
    <>
      <div className="container ">
        <div className="row ">
          <ul>
            {products.map((item) => (
              <li key={item.id}>
                <Card item={item} />
              </li>
            ))}
          </ul>
        </div>
      </div>
      <div
        data-bs-spy="scroll"
        data-bs-target="#list-example"
        data-bs-offset="0"
        class="scrollspy-example"
        tabIndex="0"
      />
    </>
  );
};

export default Store;
