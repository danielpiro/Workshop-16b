import { useState } from "react";
import Store from "./store";

const StoreCardStores = ({ store, storeMap }) => {
  const [showStore, setShowStore] = useState(false);
  const onManageStore = (e) => {
    e.preventDefault();
  };

  const onShopStore = (e) => {
    e.preventDefault();
    setShowStore(!showStore);
  };

  return (
    <div className="container d-flex justify-content-center ">
      <div className="card-body">
        <h3 className="card-title text-center">Store</h3>
        <p className="card-text text-center">Store ID: {store}</p>
        <div className="d-flex justify-content-center">
          <button className="btn btn-primary me-3" onClick={onShopStore}>
            Enter store
          </button>
          <button className="btn btn-primary" onClick={onManageStore}>
            Manage Store
          </button>
        </div>
        {showStore ? <Store store={store} storeMap={storeMap} /> : null}
      </div>
    </div>
  );
};

export default StoreCardStores;
