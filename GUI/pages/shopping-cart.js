import { useEffect, useState } from "react";
import Menu from "../components/menu";
import CartItem from "../components/cart-item.js";

const shoppingCart = () => {
  const [cart, setCart] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  useEffect(() => {
    // const fetchCart = async () => {
    //   return await api.get("/cart/" + window.id).then((res) => {
    //     if (res.status === 200) {
    //       const { data } = res;
    //       setIsLoading(!isLoading);
    //       setCart(JSON.parse(data));
    //     }
    //   });
    // };
    // fetchCart();
  }, []);

  return (
    <>
      <Menu />
      <div className="container me-5 my-5">
        <h3>Shopping Cart</h3>
        <div className="me-lg-5">
          {cart.map((item) => {
            return (
              <div>
                <CartItem props={item} />
              </div>
            );
          })}
        </div>
      </div>
    </>
  );
};

export default shoppingCart;
