import api from "./api";
import createNotification from "./norification";
import { useCookies } from "react-cookie";
import { useEffect, useState } from "react";

const CardStore = ({ store, item }) => {
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);
  const [products, setProducts] = useState([]);
  useEffect(() => {
    const fetch = () => {
      return getProducts();
    };
    console.log("card-store", item);
    console.log("card-store", store);
    fetch();
  }, []);
  const onClickBid = (e) => {
    e.preventDefault();
    createNotification("info", "Will be implemented next milestone...")();
  };
  let storeId = "";
  const getShopId = (productId) => {
    storeMap.map((item) => {
      Object.values(item)[0].map((obj) => {
        if (obj.id === productId) {
          storeId = Object.keys(item)[0].toString();
          return;
        }
      });
    });
  };

  const getProducts = () => {
    storeMap.map((item) => {
      if (Object.keys(item)[0] === store) {
        setProducts(Object.values(item));
        return;
      }
    });
  };

  const addProduct = async (e) => {
    e.preventDefault();
    getShopId(value);
    const obj = {
      user_id: cookies.userId,
      productID: value,
      storeID: storeId,
      amount: 1,
    };
    api
      .post("/cart/product/?auctionOrBid=false", obj)
      .then((res) => {
        if (res.status === 200) {
          const { data } = res;
          console.log("is 200", data);
        }
      })
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
      <h5 className="card-price text-center mb-2">Price: {price}$</h5>

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

export default CardStore;