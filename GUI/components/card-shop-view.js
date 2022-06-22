const CardShopView = ({
  userID,
  itemAmount,
  itemName,
  itemPrice,
  purchaseID,
  storeID,
  timeOfTransaction,
}) => {
  return (
    <div className="card-body">
      <h4 className="card-text text-center">ID: {userID}</h4>
      <h4 className="card-text text-center">Name: {itemName}</h4>
      <h5 className="card-text text-center mb-2">Quantity: {itemAmount}</h5>
      <h5 className="card-text text-center mb-2">itemPrice: {itemPrice}$</h5>
      <h5 className="card-text text-center mb-2">purchaseID: {purchaseID}</h5>
      <h5 className="card-text text-center mb-2">storeID: {storeID}</h5>
      <h5 className="card-text text-center mb-2">
        timeOfTransaction:{" "}
        {timeOfTransaction[2] +
          "/" +
          timeOfTransaction[1] +
          "/" +
          timeOfTransaction[0] +
          " " +
          timeOfTransaction[3] +
          ":" +
          timeOfTransaction[4]}
      </h5>
    </div>
  );
};

export default CardShopView;
