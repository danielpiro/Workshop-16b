import { useEffect, useState } from "react";
import Menu from "../components/menu";
import CartItem from "../components/cart-item.js";
import { useCookies } from "react-cookie";
import api from "../components/api";

const shoppingCart = () => {
  const [cart, setCart] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  useEffect(() => {
    const fetchCart = async () => {
      return await api
        .post(`/cart/?user_Id=${cookies.userId}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            setCart(data.value);
            setIsLoading(!isLoading);
          }
        })
        .catch((err) => console.log(err));
    };
    fetchCart();
  }, []);

  const onBuy = (e) => {
    e.preventDefault();
    api
      .post(`/cart/purchase/?user_id=GuestID_0&payment=Visa&delivery=UPS`)
      .then((res) => {
        if (res.status === 200) {
          const { data } = res;
          console.log("200", data);
        } else {
          const { data } = res;
          console.log("not 200", data);
        }
      })
      .catch((err) => console.log(err));
    ///send cart to back to purcash
  };
  return (
    <>
      <Menu />
      <div className="text-center my-5">
        <h3>Cart</h3>
      </div>
      <div className="container">
        <div className="row">
          <div className="col-sm">
            <ul className="list-group list-group-flush">
              {cart.map((item) => {
                return (
                  <div
                    className="container card mb-5"
                    style={{ width: "45rem" }}
                  >
                    <h3 className="text-center mt-3">{Object.keys(item)}</h3>
                    <hr />
                    <ul>
                      {Object.values(item).map((productList) =>
                        productList.map((product) => {
                          return (
                            <CartItem
                              id={product.id}
                              amount={product.amount}
                              category={product.category}
                              name={product.name}
                              price={product.price}
                              storeId={Object.keys(item)[0]}
                            />
                          );
                        })
                      )}
                    </ul>
                  </div>
                );
              })}
            </ul>
          </div>
          <div className="col-sm">
            <form className="row g-3 sticky-sm-top">
              <div className="text-center">Subtotal: Price from back</div>
              <div className="text-center">Discount: Price from back</div>
              <div className="text-center">Total: total to pay</div>
              <div className="col-12 d-flex justify-content-center mt-3">
                <button
                  type="button"
                  className="btn btn-primary w-25"
                  onClick={onBuy}
                >
                  Buy
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
};

export default shoppingCart;
