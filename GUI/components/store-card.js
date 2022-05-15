import { useState } from "react";

const StoreCard = (props) => {
  const [showModal, setShowModal] = useState(false);

  const onClick = (e) => {
    e.preventDefault();
  };

  return (
    <div class="card text-center">
      <div class="card-body">
        <h3 class="card-title">bla lba</h3>
        <p class="card-text">
          With supporting text below as a natural lead-in to additional content.
        </p>
        <button class="btn btn-primary" onClick={onClick}>
          Enter store
        </button>
      </div>
    </div>
  );
};

export default StoreCard;
