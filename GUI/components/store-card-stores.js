const StoreCardStores = (props) => {
  const onManageStore = (e) => {
    e.preventDefault();
  };

  const onShopStore = (e) => {
    e.preventDefault();
  };

  return (
    <div className="container d-flex justify-content-center ">
      <div className="card text-center">
        <div className="card-body">
          <h3 className="card-title">Store</h3>
          <p className="card-text">
            With supporting text below as a natural lead-in to additional
            content.
          </p>
          <button className="btn btn-primary me-3" onClick={onShopStore}>
            Shop store
          </button>
          <button className="btn btn-primary" onClick={onManageStore}>
            Manage Store
          </button>
        </div>
      </div>
    </div>
  );
};

export default StoreCardStores;
