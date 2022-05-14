const Card = ({
  value,
  image,
  title,
  category,
  description,
  price,
  discount,
}) => {
  const onClick = (e) => {
    console.log(e.target.value);
  };

  const onClickBid = (e) => {
    e.preventDefault();
  };

  const addProduct = (e) => {
    e.preventDefault();
  };
  return (
    <>
      <div className="card d-flex flex-fill h-100">
        <img src={image} className="card-img-top" alt="pic" />
        <div className="card-body">
          <p className="card-title">
            <u>Name:</u> {title}
          </p>
          <p className="card-category">
            <u>Category:</u> {category}
          </p>
          <p className="card-description">
            <u>Description:</u> {description}
          </p>
        </div>
        <div className="text-center">
          <p className="card-price me-2">
            <u>Price:</u>
            <span className="prev-price me-2" style={{ marginLeft: "10px" }}>
              {Math.round(price / ((100 - discount) / 100))}$
            </span>
            <strong>{price}$</strong> , {discount}% discount
          </p>
        </div>

        <div
          style={{
            display: "flex",
            alignContent: "center",
            justifyContent: "center",
          }}
        >
          <button
            className="add-cart-buttom btn btn-outline-primary w-25 mb-3"
            value={value}
            onClick={addProduct}
          >
            Add to cart
          </button>
          <button
            className="add-cart-buttom btn btn-outline-primary w-25 mb-3 ms-3"
            value={value}
            onClick={onClick}
          >
            Buy now
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
    </>
  );
};

export default Card;
