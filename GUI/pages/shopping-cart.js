import { useEffect, useState } from "react";
import Menu from "../components/menu";
import CartItem from "../components/cart-item.js";
import { useCookies } from "react-cookie";
import api from "../components/api";
import createNotification from "../components/norification";

const shoppingCart = () => {
  const [cart, setCart] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [deliveryAndPaymnetDetails, setDeliveryAndPaymnetDetails] = useState({
    delivery: "Delivery",
    payment: "Payment",
  });
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);
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
  useEffect(() => {
    fetchCart();
  }, [fetchCart]);

  const onBuy = (e) => {
    e.preventDefault();
    if (deliveryAndPaymnetDetails.delivery === "Delivery") {
      return createNotification("error", "Please select delivery option")();
    }
    if (deliveryAndPaymnetDetails.payment === "Payment") {
      return createNotification("error", "Please select payment option")();
    }
    if (cart.length === 0) {
      return createNotification(
        "error",
        "Please add product before checkout"
      )();
    }
    api
      .post(
        `/cart/purchase/?user_id=${cookies.userId}&payment=${deliveryAndPaymnetDetails.payment}&delivery=${deliveryAndPaymnetDetails.delivery}`
      )
      .then((res) => {
        const { data } = res;
        if (data.success) {
          createNotification("success", "Purcash was successfull")();
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
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
                    <div>
                      <h3 className="text-center mt-3">{Object.keys(item)}</h3>
                      <hr />
                    </div>

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
                              fetchCart={fetchCart}
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
                <div className="form-control">
                  <h5>Delivery option</h5>
                  <div className="dropdown">
                    <button
                      className="btn btn-secondary dropdown-toggle"
                      type="button"
                      id="dropdownMenuButton1"
                      data-bs-toggle="dropdown"
                      aria-expanded="false"
                    >
                      {deliveryAndPaymnetDetails.delivery}
                    </button>
                    <ul
                      className="dropdown-menu"
                      aria-labelledby="dropdownMenuButton1"
                    >
                      <li>
                        <button
                          className="dropdown-item"
                          id="UPS"
                          onClick={(e) => {
                            e.preventDefault();
                            return setDeliveryAndPaymnetDetails(
                              (prevState) => ({
                                ...prevState,
                                delivery: e.target.id,
                              })
                            );
                          }}
                        >
                          UPS
                        </button>
                      </li>
                      <li>
                        <button
                          className="dropdown-item"
                          id="FedEx"
                          onClick={(e) => {
                            e.preventDefault();
                            setDeliveryAndPaymnetDetails((prevState) => ({
                              ...prevState,
                              delivery: e.target.id,
                            }));
                          }}
                        >
                          FedEx
                        </button>
                      </li>
                    </ul>
                  </div>
                </div>
                <div className="form-control">
                  <h5>Payment type</h5>
                  <div className="dropdown">
                    <button
                      className="btn btn-secondary dropdown-toggle"
                      type="button"
                      id="dropdownMenuButton1"
                      data-bs-toggle="dropdown"
                      aria-expanded="false"
                    >
                      {deliveryAndPaymnetDetails.payment}
                    </button>
                    <ul
                      className="dropdown-menu"
                      aria-labelledby="dropdownMenuButton1"
                    >
                      <li>
                        <button
                          class="dropdown-item"
                          id="Visa"
                          onClick={(e) => {
                            e.preventDefault();
                            setDeliveryAndPaymnetDetails((prevState) => ({
                              ...prevState,
                              payment: e.target.id,
                            }));
                          }}
                        >
                          Visa
                        </button>
                      </li>
                      <li>
                        <button
                          className="dropdown-item"
                          id="MasterCard"
                          onClick={(e) => {
                            e.preventDefault();
                            setDeliveryAndPaymnetDetails((prevState) => ({
                              ...prevState,
                              payment: e.target.id,
                            }));
                          }}
                        >
                          MasterCard
                        </button>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
              <div className="d-flex justify-content-center">
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
