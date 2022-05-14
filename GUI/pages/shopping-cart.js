import { useEffect, useState } from "react";
import AdminMenu from "../components/menus/menuAdmin";
import StoreOwnerMenu from "../components/menus/menuStoreOwner";
import StoreManagerMenu from "../components/menus/menuStoreManager";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import CartItem from "../components/cart-item.js";
import { useCookies } from "react-cookie";

const shoppingCart = () => {
  const [cart, setCart] = useState([]);
  const [storeList, setStoreList] = useState([]);
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
    setCart([1, 2, 3, 4]);
    setStoreList([1, 2, 3, 4]);
    console.log(cookies.username);
  }, []);
  return (
    <>
      {menu}
      <div className="text-center my-3">
        <h3>Cart</h3>
      </div>
      <div class="container card" style={{ width: "45rem" }}>
        <ul class="list-group list-group-flush">
          {storeList.map((item) => {
            return (
              <div className="mb-5">
                <div className="card-header mb-3">Featured</div>
                <ul>
                  {cart.map((item2) => {
                    return <CartItem />;
                  })}
                </ul>
              </div>
            );
          })}
        </ul>
      </div>
    </>
  );
};

export default shoppingCart;
