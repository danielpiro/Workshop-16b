import api from "./api";
import { useCookies } from "react-cookie";
import createNotification from "./norification";
import { useState } from "react";
const CartItem = ({
  id,
  name,
  amount,
  price,
  category,
  storeId,
  fetchCart,
}) => {
  const [currentAmount, setCurrentAmount] = useState(parseInt(amount));
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  const onRemove = async (e) => {
    e.preventDefault();
    const obj = {
      user_id: cookies.userId,
      productID: id,
      storeID: storeId,
      amount: currentAmount,
    };
    return await api
      .post("/cart/delete", obj)
      .then((res) => {
        const { data } = res;
        if (data.success) {
          fetchCart();
          createNotification("success", "Remove products successfully")();
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
  };
  const onDec = async (e) => {
    e.preventDefault();
    const obj = {
      user_id: cookies.userId,
      productID: id,
      storeID: storeId,
      amount: 1,
    };
    return await api
      .post("/cart/delete", obj)
      .then((res) => {
        const { data } = res;
        if (data.success) {
          setCurrentAmount(currentAmount - 1);
          fetchCart();
          createNotification("success", "Remove products successfully")();
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
  };
  const onInc = async (e) => {
    e.preventDefault();
    const obj = {
      user_id: cookies.userId,
      productID: id,
      storeID: storeId,
      amount: 1,
    };
    return await api
      .post(`/cart/product/?auctionOrBid=${false}`, obj)
      .then((res) => {
        const { data } = res;
        if (data.success) {
          setCurrentAmount(currentAmount + 1);
          createNotification("success", "Added product successfully")();
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
  };
  return (
    <>
      <div className="row">
        <div className="col-sm-8">
          <div className="card-body">
            <h5 className="card-title">ID: {id}</h5>
            <p className="card-text">
              Name: {name}
              <br />
              Category: {category}
              <br />
              Price: {price}
            </p>
          </div>
        </div>
        <div className="col-sm-4">
          <button className="btn btn-primary" onClick={onRemove}>
            Remove
          </button>
          <button className="btn btn-primary ms-3" onClick={onDec}>
            -
          </button>
          <button className="btn btn-primary ms-3" onClick={onInc}>
            +
          </button>
          <div className="text-center mt-3">{currentAmount}</div>
        </div>
      </div>
    </>
  );
};

export default CartItem;
