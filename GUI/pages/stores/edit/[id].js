import { useEffect, useState } from "react";
import Menu from "../../../components/menu";
import StoreProduct from "../../../components/store-product";
import api from "../../../components/api";
import createNotification from "../../../components/norification";
import { useRouter } from "next/router";
import { useCookies } from "react-cookie";

const StoreEdit = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);
  const router = useRouter();
  const [newProduct, setNewProduct] = useState({
    name: "",
    price: "",
    quantity: "",
    category: "",
  });
  const fetch = async () => {
    return await api
      .get(`/store/products/?storeId=${router.query.id}`)
      .then((res) => {
        const { data } = res;
        if (data.success) {
          setProducts(res.data.value);
          setIsLoading(!isLoading);
        } else {
          createNotification("error", data.reason)();
        }
      });
  };
  useEffect(() => {
    return fetch();
  }, []);

  const onAdd = async (e) => {
    e.preventDefault();
    const product = {
      storeId: router.query.id,
      userId: cookies.userId,
      productName: newProduct.name,
      price: newProduct.price,
      supply: newProduct.quantity,
      category: newProduct.category,
    };
    return api
      .post("/store", product)
      .then((res) => {
        const { data } = res;
        if (data.success) {
          createNotification("success", "Added product successfully")();
          const productDashboard = {
            storeId: router.query.id,
            userId: cookies.userId,
            name: newProduct.name,
            price: newProduct.price,
            quantity: newProduct.quantity,
            category: newProduct.category,
          };
          setProducts([...products, productDashboard]);
          setNewProduct({
            name: "",
            price: "",
            quantity: "",
            category: "",
          });
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
  };
  return (
    <>
      <Menu />
      <div className="text-center my-5">
        <h1>{router.query.id}</h1>
      </div>
      <div className="row my-4 d-flex justify-content-center">
        {!isLoading ? (
          <>
            <div className="container d-flex justify-content-center">
              <button
                className="btn btn-primary mb-3"
                type="button"
                data-bs-toggle="modal"
                data-bs-target="#exampleModal"
              >
                Add
              </button>
            </div>
            <div className="w-100">
              <ul className="list-group-dashboard">
                {products.map((product) => {
                  return (
                    <li className=" list-group-item" key={product.id}>
                      <StoreProduct
                        value={product.id}
                        title={product.name}
                        price={product.price}
                        quantity={product.quantity}
                        category={product.category}
                        products={products}
                        setProducts={setProducts}
                        storeId={product.storeId}
                      />
                    </li>
                  );
                })}
              </ul>
            </div>
          </>
        ) : (
          <div className="container h-100 my-6">
            <div className="row align-items-center justify-content-center">
              <div className="spinner-border" />
            </div>
          </div>
        )}

        <div
          class="modal fade"
          id="exampleModal"
          tabindex="-1"
          aria-labelledby="exampleModalLabel"
          aria-hidden="true"
        >
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">
                  Add new product
                </h5>
                <button
                  type="button"
                  class="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div class="modal-body">
                <form className="form-control">
                  <div className="text-center">
                    Name:
                    <input
                      className="ms-3"
                      value={newProduct.name}
                      onChange={(e) =>
                        setNewProduct((prevState) => ({
                          ...prevState,
                          name: e.target.value,
                        }))
                      }
                    />
                  </div>
                  <div className="text-center">
                    Category:
                    <input
                      className="ms-3"
                      value={newProduct.category}
                      onChange={(e) =>
                        setNewProduct((prevState) => ({
                          ...prevState,
                          category: e.target.value,
                        }))
                      }
                    />
                  </div>
                  <div className="text-center">
                    Supply:
                    <input
                      className="ms-3"
                      value={newProduct.quantity}
                      onChange={(e) =>
                        setNewProduct((prevState) => ({
                          ...prevState,
                          quantity: e.target.value,
                        }))
                      }
                    />
                  </div>
                  <div className="text-center">
                    Price:
                    <input
                      className="ms-3"
                      value={newProduct.price}
                      onChange={(e) =>
                        setNewProduct((prevState) => ({
                          ...prevState,
                          price: e.target.value,
                        }))
                      }
                    />
                  </div>
                </form>
              </div>
              <div class="modal-footer">
                <button
                  type="button"
                  class="btn btn-secondary"
                  data-bs-dismiss="modal"
                >
                  Close
                </button>
                <button
                  type="button"
                  class="btn btn-primary"
                  onClick={onAdd}
                  data-bs-dismiss="modal"
                >
                  Save
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export function getServerSideProps(context) {
  return { props: {} };
}

export default StoreEdit;
