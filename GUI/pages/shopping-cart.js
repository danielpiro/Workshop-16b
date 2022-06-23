import { useEffect, useState } from "react";
import Menu from "../components/menu";
import CartItem from "../components/cart-item.js";
import { useCookies } from "react-cookie";
import api from "../components/api";
import createNotification from "../components/norification";

const shoppingCart = () => {
  const [cart, setCart] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [priceBefore, setPriceBefore] = useState("");
  const [priceAfter, setPriceAfter] = useState("");
  const [deliveryAndPaymnetDetails, setDeliveryAndPaymnetDetails] = useState({
    delivery: "Delivery",
    payment: "Payment",
  });
  const [values, setValues] = useState({
    nameHolder: "",
    address: "",
    city: "",
    country: "",
    zip: "",
    holder: "",
    cardNumber: "",
    expireDate: "",
    cvv: "",
    id: "",
  });
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
    "session",
  ]);
  const fetchCart = async () => {
    return await api
      .post(`/cart/?user_Id=${cookies.userId}`, null, {
        headers: {
          Authorization: cookies.session,
        },
      })
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
            `/cart/price/?user_id=${cookies.userId}&payment=Visa&delivery=UPS`,
            null,
            {
              headers: {
                Authorization: cookies.session,
              },
            }
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
    console.log(values);
    api
      .post(
        `/cart/purchase/?userId=${cookies.userId}&payment=${
          deliveryAndPaymnetDetails.payment
        }&delivery=${deliveryAndPaymnetDetails.delivery}&nameHolder=${
          values.nameHolder
        }&address=${values.address}&city=${values.city}&country=${
          values.country
        }&zip=${values.zip}&holder=${values.holder}&cardNumber=${
          values.cardNumber
        }&expireDate=${values.expireDate}&cvv=${parseInt(values.cvv)}&id=${
          values.id
        }`,
        null,
        {
          headers: {
            Authorization: cookies.session,
          },
        }
      )
      .then((res) => {
        const { data } = res;
        if (data.success) {
          console.log(data.value);
          createNotification("success", "Purchase was successfull")();
          fetchCart();
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
              <div className="input-group d-flex justify-content-center">
                <input
                  className="input-medium mb-2 me-2"
                  value={values.address}
                  placeholder="enter address"
                  onChange={(e) =>
                    setValues((prevState) => ({
                      ...prevState,
                      address: e.target.value,
                    }))
                  }
                />
                <input
                  name="Card number"
                  className="input-medium mb-2 me-2"
                  value={values.cardNumber}
                  placeholder="enter cardNumber"
                  onChange={(e) =>
                    setValues((prevState) => ({
                      ...prevState,
                      cardNumber: e.target.value,
                    }))
                  }
                />
                <input
                  className="input-medium mb-2 me-2"
                  value={values.city}
                  placeholder="enter city"
                  onChange={(e) =>
                    setValues((prevState) => ({
                      ...prevState,
                      city: e.target.value,
                    }))
                  }
                />
                <input
                  className="input-medium mb-2 me-2"
                  value={values.country}
                  placeholder="enter country"
                  onChange={(e) =>
                    setValues((prevState) => ({
                      ...prevState,
                      country: e.target.value,
                    }))
                  }
                />
                <input
                  className="input-medium mb-2 me-2"
                  value={values.cvv}
                  placeholder="enter cvv"
                  onChange={(e) =>
                    setValues((prevState) => ({
                      ...prevState,
                      cvv: e.target.value,
                    }))
                  }
                />
                <input
                  className="input-medium mb-2 me-2"
                  value={values.expireDate}
                  placeholder="enter expireDate"
                  onChange={(e) =>
                    setValues((prevState) => ({
                      ...prevState,
                      expireDate: e.target.value,
                    }))
                  }
                />
                <input
                  className="input-medium mb-2 me-2"
                  value={values.holder}
                  placeholder="enter holder"
                  onChange={(e) =>
                    setValues((prevState) => ({
                      ...prevState,
                      holder: e.target.value,
                    }))
                  }
                />
                <input
                  className="input-medium mb-2 me-2"
                  value={values.id}
                  placeholder="enter id"
                  onChange={(e) =>
                    setValues((prevState) => ({
                      ...prevState,
                      id: e.target.value,
                    }))
                  }
                />
                <input
                  className="input-medium mb-2 me-2"
                  value={values.nameHolder}
                  placeholder="enter nameHolder"
                  onChange={(e) =>
                    setValues((prevState) => ({
                      ...prevState,
                      nameHolder: e.target.value,
                    }))
                  }
                />
                <input
                  className="input-medium mb-2 me-2"
                  value={values.zip}
                  placeholder="enter zip"
                  onChange={(e) =>
                    setValues((prevState) => ({
                      ...prevState,
                      zip: e.target.value,
                    }))
                  }
                />
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
