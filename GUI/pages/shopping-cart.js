import { useEffect, useState } from "react";
import api from "../components/api.js";
import shoppingCartList from "../components/shoppingCart.js";
const shoppingCart = (props) => {
  const [cart, setCart] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  useEffect(() => {
    const fetchCart = async () => {
      await api.get("/cart/" + window.id).then((res) => {
        if (res.status === 200) {
          const { data } = res;
          setIsLoading(!isLoading);
          setCart(JSON.parse(data));
        }
      });
    };
    fetchCart();
  }, []);

  return (
    <div className="container me-5 my-5">
      <h3>Shopping Cart</h3>
      {isLoading ? (
        <div className="row align-items-center justify-content-center">
          <div className="spinner-border" />
        </div>
      ) : null}
    </div>
  );
};

export default shoppingCart;
