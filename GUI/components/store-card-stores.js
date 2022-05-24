const StoreCardStores = (props) => {
  const onManageStore = (e) => {
    e.preventDefault();
  };

  const onShopStore = (e) => {
    e.preventDefault();
  };

  return (
    <div className="container d-flex justify-content-center ">
      <div className="card-body">
        <h3 className="card-title text-center">Store</h3>
        <p className="card-text text-center">
          With supporting text below as a natural lead-in to additional content.
        </p>
        <div className="d-flex justify-content-center">
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
