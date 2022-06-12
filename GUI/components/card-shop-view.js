import api from "./api";
import createNotification from "./norification";
import { useCookies } from "react-cookie";

const CardShopView = ({
  userID,
  itemAmount,
  itemName,
  itemPrice,
  purchaseID,
  storeID,
  timeOfTransaction,
}) => {
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);
  const addProduct = async (e) => {
    e.preventDefault();
    const obj = {
      user_id: cookies.userId,
      productID: value,
      storeID: storeId,
      amount: 1,
    };
    return await api
      .post(`/cart/product/?auctionOrBid=${false}`, obj)
      .then((res) => {
        const { data } = res;
        if (data.success) {
          createNotification("success", "Added product successfully")();
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
  };
  return (
    <div className="card-body">
      <h4 className="card-text text-center">ID: {userID}</h4>
      <h4 className="card-text text-center">Name: {itemName}</h4>
      <h5 className="card-text text-center mb-2">Quantity: {itemAmount}</h5>
      <h5 className="card-text text-center mb-2">itemPrice: {itemPrice}$</h5>
      <h5 className="card-text text-center mb-2">purchaseID: {purchaseID}</h5>
      <h5 className="card-text text-center mb-2">storeID: {storeID}</h5>
      <h5 className="card-text text-center mb-2">
        timeOfTransaction:{" "}
        {timeOfTransaction[2] +
          "/" +
          timeOfTransaction[1] +
          "/" +
          timeOfTransaction[0] +
          " " +
          timeOfTransaction[3] +
          ":" +
          timeOfTransaction[4]}
      </h5>
    </div>
  );
};

export default CardShopView;
