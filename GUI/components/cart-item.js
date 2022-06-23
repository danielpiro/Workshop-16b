import api from "./api";
import { useCookies } from "react-cookie";
import createNotification from "./norification";
const CartItem = ({
  id,
  name,
  amount,
  price,
  category,
  storeId,
  fetchCart,
  quantity,
}) => {
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
    "session",
  ]);

  const onRemove = async (e) => {
    e.preventDefault();
    const obj = {
      user_id: cookies.userId,
      productID: id,
      storeID: storeId,
      amount: amount,
    };
    return await api
      .post(`/cart/delete/?user_id=${cookies.userId}`, obj, {
        headers: {
          Auth: cookies.session,
        },
      })
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
      .post(`/cart/delete/?user_id=${cookies.userId}`, obj, {
        headers: {
          Auth: cookies.session,
        },
      })
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
  const onInc = async (e) => {
    e.preventDefault();
    if (amount === quantity) {
      return createNotification(
        "error",
        "Cannot add more , reached max stock limit"
      )();
    }
    const obj = {
      user_id: cookies.userId,
      productID: id,
      storeID: storeId,
      amount: 1,
    };
    return await api
      .post(
        `/cart/product/?userId=${cookies.userId}&auctionOrBid=${false}`,
        obj,
        {
          headers: {
            Auth: cookies.session,
          },
        }
      )
      .then((res) => {
        const { data } = res;
        if (data.success) {
          fetchCart();
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
              quantity: {quantity}
              <br />
              Price: {price}
            </p>
          </div>
        </div>
        <div className="col-sm-4 mt-4">
          <div className="text-center">
            <input
              className="input-group-text text-center w-25"
              disabled="true"
              value={amount}
            />
          </div>
          <div className="mt-5">
            <button className="btn btn-primary" onClick={onRemove}>
              Remove
            </button>
            <button className="btn btn-primary ms-3" onClick={onDec}>
              -
            </button>
            <button className="btn btn-primary ms-3" onClick={onInc}>
              +
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default CartItem;
