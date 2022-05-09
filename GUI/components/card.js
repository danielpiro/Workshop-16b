const Card = ({
  value,
  image,
  title,
  category,
  description,
  price,
  discount,
  getSingleProduct,
  singleProduct,
}) => {
  const onClick = (e) => {
    console.log(e.target.value);
  };

  const onClickDetails = (e) => {
    e.preventDefault();
    alert("not working WIP");
    console.log("in details", e.target.value);
    getSingleProduct(e.target.value);
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
        <p className="card-price">
          <span className="me-4">
            <u>Price:</u>
          </span>
          <span className="prev-price">
            {Math.round(price / ((100 - discount) / 100))}$
          </span>{" "}
          <strong>{price}$</strong> , {discount}% discount
        </p>
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
            onClick={onClick}
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
            onClick={onClickDetails}
          >
            Details
          </button>
        </div>
        <div
          className="modal fade bd-example-modal-lg"
          tabIndex="-1"
          role="dialog"
          aria-labelledby="myLargeModalLabel"
          aria-hidden="true"
          id="productDetails"
        >
          <div className="modal-dialog modal-lg">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="exampleModalLongTitle">
                  Product details
                </h5>
              </div>
              <div className="modal-body">
                Title : {singleProduct.title}
                Category : {singleProduct.category}
                Description : {singleProduct.description}
              </div>
              <div className="modal-footer">
                <button
                  type="button"
                  className="btn btn-secondary"
                  data-bs-dismiss="modal"
                >
                  Close
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Card;
