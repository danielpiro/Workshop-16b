import { useState } from "react";
import StoreProduct from "./store-product";

const Store = (props) => {
  const [products, setProducts] = useState([
    1, 2, 2, 3, 4, 5, 6, 7, 8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
    1, 1, 1, 1, 1,
  ]);

  return (
    <>
      <div className="container">
        <div className="row">
          <ul>
            {products.map((item) => (
              <li key={item}>
                <StoreProduct />
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
