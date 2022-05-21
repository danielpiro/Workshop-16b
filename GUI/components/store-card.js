import { useState } from "react";
import Store from "../components/store";
const StoreCard = (props) => {
  const [showModal, setShowModal] = useState(false);

  const onClick = (e) => {
    e.preventDefault();
    setShowModal(!showModal);
  };

  return (
    <div className="d-flex justify-content-center">
      <div class="card text-center w-50">
        <div class="card-body">
          <h3 class="card-title">Store</h3>
          <p class="card-text">
            With supporting text below as a natural lead-in to additional
            content.
          </p>
          <button
            class="btn btn-primary"
            data-bs-toggle="modal"
            data-bs-target="#staticBackdrop"
            onClick={onClick}
          >
            Enter store
          </button>
        </div>
      </div>

      <div
        class="modal fade"
        id="staticBackdrop"
        data-bs-backdrop="static"
        data-bs-keyboard="false"
        tabIndex="-1"
        aria-labelledby="staticBackdropLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog modal-dialog-scrollable">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="staticBackdropLabel">
                Store
              </h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              <Store />
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default StoreCard;
