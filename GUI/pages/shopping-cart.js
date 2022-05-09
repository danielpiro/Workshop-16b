import { useEffect, useState } from "react";
import api from "../components/api.js";
import Menu from "../components/menu";

const shoppingCart = (props) => {
  const [cart, setCart] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  useEffect(() => {
    const fetchCart = async () => {
      return await api.get("/cart/" + window.id).then((res) => {
        if (res.status === 200) {
          const { data } = res;
          setIsLoading(!isLoading);
          setCart(JSON.parse(data));
        }
      });
    };
    fetchCart();
    //need to remove this on prod
    setIsLoading(!isLoading);
  }, []);

  return (
    <>
      <Menu />
      <div className="container me-5 my-5">
        <h3>Shopping Cart</h3>
        {isLoading ? <div className="spinner-border m-5 ms-5" /> : null}
      </div>
    </>
  );
};

export default shoppingCart;
