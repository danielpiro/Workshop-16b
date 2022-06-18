import Link from "next/link";
import { useRouter } from "next/router";

const StoreCardStores = ({ store, allowToManageStores, owningStores }) => {
  const router = useRouter();

  const onEnterStore = (e) => {
    e.preventDefault();
    router.push(`stores/view/${store.storeId}`);
  };

  return (
    <div className="container d-flex justify-content-center ">
      <div className="card-body">
        <p className="card-rating text-center">{store.storeState}</p>
        <h5 className="card-title text-center">Name: {store.storeName}</h5>
        <h5 className="card-text text-center">ID: {store.storeId}</h5>
        <p className="card-rating text-center">Rating: {store.storeRating}</p>
        <div className="d-flex justify-content-center">
          <button className="btn btn-primary me-3" onClick={onEnterStore}>
            Enter store
          </button>
          <Link href={{ pathname: "/store-management", query: store.storeId }}>
            <a
              className="btn btn-primary"
              hidden={
                !(
                  allowToManageStores.includes(store.storeId) ||
                  owningStores.includes(store.storeId)
                )
              }
            >
              Manage Store
            </a>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default StoreCardStores;
