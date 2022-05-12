import { useEffect, useState } from "react";
import Menu from "../components/menu";
import CartItem from "../components/cart-item.js";
import { useCookies } from "react-cookie";

const shoppingCart = () => {
  const [cart, setCart] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
  ]);
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
    setCart([1, 2, 3, 4, 5, 6, 6]);
    console.log(cookies.username);
  }, []);

  return (
    <>
      <Menu />
      <div class="container">
        <div class="row justify-content-md-center">
          <div
            className="m-5"
            style={{ display: "flex", justifyContent: "center" }}
          >
            <h3>Shopping Cart</h3>
          </div>
          {cart.map((item) => {
            return <CartItem props={item} />;
          })}
        </div>
      </div>
    </>
  );
};

export default shoppingCart;
