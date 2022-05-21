const CartItem = (props) => {
  const onRemove = (e) => {
    e.preventDefault();
  };
  return (
    <>
      <div className="row">
        <div className="col-sm-8">
          <div className="card-body">
            <h5 className="card-title">Card title</h5>
            <p className="card-text">
              This is a wider card with supporting text below as a natural
              lead-in to additional content. This content is a little bit
              longer.
            </p>
          </div>
        </div>
        <div className="col-sm-4">
          <button className="btn btn-primary" onClick={onRemove}>
            Remove
          </button>
        </div>
      </div>
    </>
  );
};

export default CartItem;
