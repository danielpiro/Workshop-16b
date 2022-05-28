import api from "./api";
import createNotification from "./norification";
import { useCookies } from "react-cookie";
import { useState } from "react";
const StoreProduct = ({
  value,
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
    productName: title,
    price: price,
    supply: quantity,
    category: category,
  });
  const [originalDetails, setOriginalDetails] = useState({
    productName: title,
    price: price,
    supply: quantity,
    category: category,
  });
  const onEdit = async (e) => {
    e.preventDefault();
    setIsEdit(!isEdit);
  };
  const onRemove = async (e) => {
    e.preventDefault();
    return await api
      .delete(
        `/store/product/?storeId=${storeId}&userId=${cookies.userId}&productId=${value}`
      )
      .then((res) => {
        const { data } = res;
        if (data.success) {
          // const updateProducts = products.filter((item) => item.id !== value);
          // setProducts(updateProducts);
          createNotification(
            "success",
            `product ID: ${value} is been removed...`
          )();
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
  };
  const onSave = async (e) => {
    e.preventDefault();
    const body = {
      storeId: storeId,
      userId: cookies.userId,
      productName: details.title,
      price: details.price,
      supply: details.quantity,
      category: details.category,
    };
    return await api
      .post(`/store/product/?productId=${value}`, body)
      .then((res) => {
        const { data } = res;
        if (data.success) {
          // setOriginalDetails(details);
          createNotification(
            "success",
            `product ID: ${value} is been updated...`,
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
        <h4 className="card-body mb-3">ID: {value}</h4>
      </div>
      <form className="text-center">
        Name:
        <br />
        <input
          className="card-body text-center mb-3 ms-3"
          type="text"
          value={details.productName}
          disabled={!isEdit}
          onChange={(e) =>
            setDetails((prevState) => ({
              ...prevState,
              productName: e.target.value,
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
          value={details.supply}
          type="text"
          disabled={!isEdit}
          onChange={(e) =>
            setDetails((prevState) => ({
              ...prevState,
              supply: e.target.value,
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
            </button>{" "}
          </>
        ) : null}
      </div>
    </div>
  );
};

export default StoreProduct;
