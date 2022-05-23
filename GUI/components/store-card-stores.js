import Link from "next/link";
import { useRouter } from "next/router";

const StoreCardStores = ({ store }) => {
  const router = useRouter();

  const onEnterStore = (e) => {
    e.preventDefault();
    router.push(`stores/view/${store}`);
  };

  return (
    <div className="container d-flex justify-content-center ">
      <div className="card-body">
        <h3 className="card-title text-center">Store</h3>
        <p className="card-text text-center">Store ID: {store}</p>
        <div className="d-flex justify-content-center">
          <button className="btn btn-primary me-3" onClick={onEnterStore}>
            Enter store
          </button>
          <Link href={{ pathname: "/store-management", query: store }}>
            <a className="btn btn-primary">Manage Store</a>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default StoreCardStores;
