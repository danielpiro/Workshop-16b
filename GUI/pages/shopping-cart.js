import { useEffect, useState } from "react";
import Menu from "../components/menu";
import CartItem from "../components/cart-item.js";
import { useCookies } from "react-cookie";
import api from "../components/api";
import createNotification from "../components/norification";
import sendNotification from "../components/send-notification";

const shoppingCart = () => {
  const [cart, setCart] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [priceBefore, setPriceBefore] = useState("");
  const [priceAfter, setPriceAfter] = useState("");
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
      .then(async () => {
        return await api
          .post(
            `/cart/price/?user_id=${cookies.userId}&payment=Visa&delivery=UPS`
          )
          .then((res) => {
            const { data } = res;
            if (data.success) {
              setPriceBefore(data.value.priceBeforeDiscount);
              setPriceAfter(data.value.priceAfterDiscount);
            } else {
              createNotification("error", data.reason)();
            }
          });
      })
      .catch((err) => console.log(err));
  };
  useEffect(() => {
    fetchCart();
  }, []);

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
          createNotification("success", "Purchase was successfull")();
          fetchCart();
        } else {
          const notification = {
            senderName: cookies.userId,
            subject: "PaymentFailed",
            title: "title",
            body: "",
          };
          sendNotification(notification);
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
                          console.log(product);
                          return (
                            <CartItem
                              id={product.id}
                              amount={product.amount}
                              category={product.category}
                              name={product.name}
                              price={product.price}
                              storeId={Object.keys(item)[0]}
                              fetchCart={fetchCart}
                              quantity={product.quantity}
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
            <form className="form-control-plaintext row sticky-sm-top mb-5">
              <div className="text-center mb-2">
                <h5>Subtotal: {priceBefore}$</h5>
              </div>
              <div className="text-center mb-3">
                <h5>Total: {priceAfter}$</h5>
              </div>
              <div className="d-flex justify-content-center">
                <div className="dropdown">
                  <button
                    className="btn btn-primary dropdown-toggle"
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
                          return setDeliveryAndPaymnetDetails((prevState) => ({
                            ...prevState,
                            delivery: e.target.id,
                          }));
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
                <div className="d-flex justify-content-center ms-3 mb-3">
                  <div className="dropdown">
                    <button
                      className="btn btn-primary dropdown-toggle"
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
                          className="dropdown-item"
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
