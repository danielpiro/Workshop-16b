import { allowedStatusCodes } from "next/dist/lib/load-custom-routes";
import api from "./api";
import createNotification from "./norification";

const Card = ({ value, title, category, description, price, discount }) => {
  const onClickBid = (e) => {
    e.preventDefault();
    createNotification("info", "Will be implemented next milestone...")();
  };

  const addProduct = async (e) => {
    e.preventDefault();
    const fake = {
      user_id: "123",
      productID: "123",
      storeID: "123",
      amount: 123,
    };
    api
      .post(
        `/cart/product/?auctionOrBid=false
`,
        fake
      )
      .then((res) => {
        if (res.status === 200) {
          const { data } = res;
          console.log(data);
        }
      })
      .catch((err) => console.log(err));
  };
  return (
    <div className="card-body">
      <h4 className="card-title text-center">{title}</h4>
      <h5 className="card-category text-center">{category}</h5>
      <p className="card-description text-center">{description}</p>
      <h5 className="card-price me-2 text-center mb-5">Price: {price}$</h5>

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
