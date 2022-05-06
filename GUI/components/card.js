const Card = ({ value, image, title, description }) => {
  const onClick = (event) => {
    console.log(event.target.value);
  };
  return (
    <div className="card d-flex flex-fill h-100">
      <img src={image} className="card-img-top" alt="pic" />
      <div className="card-body">
        <h5 className="card-title">{title}</h5>
        <p className="card-text">{description}</p>
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
          onClick={onClick}
        >
          Add to cart
        </button>
      </div>
    </div>
  );
};

export default Card;
