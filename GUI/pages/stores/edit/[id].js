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
    productName: "",
    price: "",
    supply: "",
    category: "",
  });
  useEffect(() => {
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
    fetch();
  }, []);

  const onAdd = async (e) => {
    e.preventDefault();
    const product = {
      storeId: router.query.id,
      userId: cookies.userId,
      productName: newProduct.productName,
      price: newProduct.price,
      supply: newProduct.supply,
      category: newProduct.category,
    };
    console.log(product);
    return api
      .post("/store", product)
      .then((res) => {
        const { data } = res;
        if (data.success) {
          console.log(data);
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
                className="btn btn-primary"
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
                    Name:{" "}
                    <input
                      value={newProduct.productName}
                      onChange={(e) =>
                        setNewProduct((prevState) => ({
                          ...prevState,
                          productName: e.target.value,
                        }))
                      }
                    />
                  </div>
                  <div className="text-center">
                    Category:{" "}
                    <input
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
                    Supply:{" "}
                    <input
                      value={newProduct.supply}
                      onChange={(e) =>
                        setNewProduct((prevState) => ({
                          ...prevState,
                          supply: e.target.value,
                        }))
                      }
                    />
                  </div>
                  <div className="text-center">
                    Price:{" "}
                    <input
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

export default StoreEdit;
