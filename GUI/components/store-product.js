import api from "./api";
import createNotification from "./norification";
import { useCookies } from "react-cookie";
import { useState } from "react";
const StoreProduct = ({
  id,
  title,
  price,
  quantity,
  category,
  products,
  setProducts,
  storeId,
}) => {
  const [isEdit, setIsEdit] = useState(false);
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);
  const [details, setDetails] = useState({
    name: title,
    price: price,
    quantity: quantity,
    category: category,
  });
  const [originalDetails, setOriginalDetails] = useState({
    name: title,
    price: price,
    quantity: quantity,
    category: category,
  });
  const onEdit = async (e) => {
    e.preventDefault();
    setIsEdit(!isEdit);
  };
  const onRemove = async (e) => {
    e.preventDefault();
    return await api
      .post(
        `/store/product/delete/?storeId=${storeId}&userId=${cookies.userId}&productId=${id}`
      )
      .then((res) => {
        const { data } = res;
        if (data.success) {
          const updateProducts = products.filter((item) => item.id !== id);
          setProducts(updateProducts);
          createNotification(
            "success",
            `product ID: ${id} is been removed...`
          )();
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
  };
  const onSave = async (e) => {
    e.preventDefault();
    const obj = {
      storeId: storeId,
      userId: cookies.userId,
      productName: details.name,
      price: details.price,
      supply: details.quantity,
      category: details.category,
    };
    console.log(id);
    return await api
      .post(`/store/product/?productId=${id}`, obj)
      .then((res) => {
        const { data } = res;
        if (data.success) {
          setOriginalDetails(details);
          createNotification(
            "success",
            `product ID: ${id} is been updated...`,
            () => setIsEdit(!isEdit)
          )();
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
  };
  const onCancel = (e) => {
    e.preventDefault();
    setDetails(originalDetails);
    setIsEdit(!isEdit);
  };

  return (
    <div className="card-body">
      <div className="text-center">
        <h4 className="card-body mb-3">ID: {id}</h4>
      </div>
      <form className="text-center">
        Name:
        <br />
        <input
          className="card-body text-center mb-3 ms-3"
          type="text"
          value={details.name}
          disabled={!isEdit}
          onChange={(e) =>
            setDetails((prevState) => ({
              ...prevState,
              name: e.target.value,
            }))
          }
        />
      </form>
      <form className="text-center">
        Category:
        <input
          className="card-body text-center mb-3 ms-3"
          value={details.category}
          type="text"
          disabled={!isEdit}
          onChange={(e) =>
            setDetails((prevState) => ({
              ...prevState,
              category: e.target.value,
            }))
          }
        />
      </form>
      <form className="text-center">
        Quantity:
        <input
          className="card-body text-center mb-3 ms-3"
          value={details.quantity}
          type="text"
          disabled={!isEdit}
          onChange={(e) =>
            setDetails((prevState) => ({
              ...prevState,
              quantity: e.target.value,
            }))
          }
        />
      </form>
      <form className="text-center">
        Price:
        <input
          className="card-body text-center mb-3 ms-3"
          value={details.price}
          type="text"
          disabled={!isEdit}
          onChange={(e) =>
            setDetails((prevState) => ({
              ...prevState,
              price: e.target.value,
            }))
          }
        />
      </form>
      <div className="d-flex justify-content-center">
        <button
          className="add-cart-buttom btn btn-outline-primary w-25 mb-3"
          onClick={onEdit}
        >
          Edit product
        </button>
        <button
          className="add-cart-buttom btn btn-outline-primary w-25 mb-3 ms-3"
          onClick={onRemove}
        >
          Remove product
        </button>
        {isEdit ? (
          <>
            <button
              className="add-cart-buttom btn btn-outline-primary w-25 mb-3 ms-3"
              onClick={onSave}
            >
              Save
            </button>
            <button
              className="add-cart-buttom btn btn-outline-primary w-25 mb-3 ms-3"
              onClick={onCancel}
            >
              Cancel
            </button>
          </>
        ) : null}
      </div>
    </div>
  );
};

export default StoreProduct;
