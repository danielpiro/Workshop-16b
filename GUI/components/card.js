import createNotification from "./norification";

const Card = ({ value, title, category, description, price, discount }) => {
  const onClickBid = (e) => {
    e.preventDefault();
    createNotification("info", "Will be implemented next milestone...")();
  };

  const addProduct = (e) => {
    e.preventDefault();
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
