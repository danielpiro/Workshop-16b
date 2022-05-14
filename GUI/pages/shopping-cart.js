import { useEffect, useState } from "react";
import AdminMenu from "../components/menus/menuAdmin";
import StoreOwnerMenu from "../components/menus/menuStoreOwner";
import StoreManagerMenu from "../components/menus/menuStoreManager";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
//import Menu from "../components/menu";
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
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
                                                                 //      + Edit using new method "setUserPermission"
  var menu;
  if (userPermission == "Admin"){
    menu = <AdminMenu />;
  }
  else if (userPermission == "Owner"){
    menu = <StoreOwnerMenu />;
  }
  else if (userPermission == "Manager"){
    menu = <StoreManagerMenu />;
  }
  else if (userPermission == "Subscriber"){
    menu = <SubscriberMenu />;
  }
  else{
    menu = <GuestMenu />;
  }

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
      {/* <Menu /> */}
      {menu}

      <div className="container">
        <div className="row justify-content-md-center">
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
