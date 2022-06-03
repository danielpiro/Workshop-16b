import api from "./api";
import createNotification from "./norification";
import { useCookies } from "react-cookie";
import { useState } from "react";

const Card = ({ value, title, price, quantity, category, storeId }) => {
  const [cart, setCart] = useState([]);
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);
  const onClickBid = (e) => {
    e.preventDefault();
    createNotification("info", "Will be implemented next milestone...")();
  };

  const addProduct = async (e) => {
    e.preventDefault();
    const obj = {
      user_id: cookies.userId,
      productID: value,
      storeID: storeId,
      amount: 1,
    };
    return await api
      .post(`/cart/?user_Id=${cookies.userId}`)
      .then((res) => {
        const { data } = res;
        if (data.success) {
          setCart(data.value);
        }
      })
      .then(() =>
        api.post(`/cart/product/?auctionOrBid=${false}`, obj).then((res) => {
          const { data } = res;
          if (data.success) {
            createNotification("success", "Added product successfully")();
          } else {
            createNotification("error", data.reason)();
          }
        })
      )
      .catch((err) => console.log(err));
  };
  return (
    <div className="card-body">
      <h4 className="card-title text-center">ID: {value}</h4>
      <h4 className="card-title text-center">Name: {title}</h4>
      <h5 className="card-description text-center mb-2">
        Quantity: {quantity}
      </h5>
      <h5 className="card-category text-center mb-2">Category: {category}</h5>
      <h5 className="card-price text-center mb-4">Price: {price}$</h5>

      <div className="d-flex justify-content-center">
        <button
          className="add-cart-buttom btn btn-outline-primary w-25 mb-3"
          value={value}
          onClick={addProduct}
        >
          Add to cart
        </button>
        <button
          className="add-cart-buttom btn btn-outline-primary w-25 mb-3 ms-3"
          data-bs-toggle="modal"
          data-bs-target="#productDetails"
          value={value}
          onClick={onClickBid}
        >
          Bid
        </button>
      </div>
    </div>
  );
};

export default Card;
