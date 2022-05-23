import { useState } from "react";

const StoreProduct = ({ item }) => {
  const [isEdit, setIsEdit] = useState(false);
  const [details, setDetails] = useState({
    name: "",
    quantity: "",
    price: "",
  });

  const onEdit = (e) => {
    e.preventDefault();
    setIsEdit(!isEdit);
  };

  const onSave = (e) => {
    e.preventDefault;
    setIsEdit(!isEdit);
    ///send to back....
  };

  const onRemove = (e) => {
    e.preventDefault;
    ///....send request to back to remove
  };
  return (
    <div className="form mb-4">
      <div className="input-group-lg">
        <input
          className="card-body"
          type="text"
          value={details.name === "" ? "Name" : details.name}
          disabled={!isEdit}
          onChange={(e) =>
            setDetails((prevState) => ({
              ...prevState,
              name: e.target.value,
            }))
          }
        />
        <input
          className="card-body"
          type="text"
          value={details.quantity === "" ? "Quantity" : details.quantity}
          disabled={!isEdit}
          onChange={(e) =>
            setDetails((prevState) => ({
              ...prevState,
              quantity: e.target.value,
            }))
          }
        />
        <input
          className="card-body mb-3"
          type="text"
          value={details.price === "" ? "Price" : details.price}
          disabled={!isEdit}
          onChange={(e) =>
            setDetails((prevState) => ({
              ...prevState,
              price: e.target.value,
            }))
          }
        />
      </div>
      <button className="btn btn-primary me-2" title="edit" onClick={onEdit}>
        Edit
      </button>
      <button className="btn btn-primary me-2" title="remove">
        Remove
      </button>
      <button
        className="btn btn-primary me-2"
        title="save"
        hidden={!isEdit}
        onClick={onSave}
      >
        Save
      </button>
    </div>
  );
};

export default StoreProduct;
