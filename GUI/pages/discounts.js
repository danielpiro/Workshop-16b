import Menu from "../components/menu";
import { useState } from "react";

const Discounts = () => {
  const [discountType, setDiscountType] = useState("DiscountType");
  const discountTypes = ["Type A", "Type B"];
  const [combinationType, setCombinationType] = useState("Max/Addition");
  const combinationTypes = ["Max", "Addition"];
  const [allowForbidOption, setAllowForbidOptions] = useState("Allow/Forbid");
  const [discounts, setDiscounts] = useState(["Create new discount", "Discount A", "Discount B"]);
  const allowForbidOptions = ["Allow", "Forbid"];
  const [preds, setPreds] = useState({
    pred1: "Choose predicate #1",
    pred2: "Choose predicate #2",
  });
  const [predFeatures1, setPredFeatures1] = useState({
    allowORforbid: allowForbidOption,
    minPrice: "",
    maxPrice: "",
    minQunatity: "",
    maxQunatity: "",
    productShouldBeInCart: "",
    productShouldBeInCartMinQunatity: "",
    dayOfMonth: "Day Of Month",
    dayOfWeek: "Day Of Week",
    startHourOfDay: "",
    endHourOfDay: "",
    specificUser: "",
    age: "",
  });
  const [predFeatures2, setPredFeatures2] = useState({
    allowORforbid: allowForbidOption,
    minPrice: "",
    maxPrice: "",
    minQunatity: "",
    maxQunatity: "",
    productShouldBeInCart: "",
    productShouldBeInCartMinQunatity: "",
    dayOfMonth: "Day Of Month",
    dayOfWeek: "Day Of Week",
    hourOfDay: "Hour Of Day",
    specificUser: "",
    age: "",
  });

  const onCreateCombinedDiscount = (e) => {
    e.preventDefault();
  };

  const onCreateDiscount1 = (e) => {
    e.preventDefault();
  };

  const onCreateDiscount2 = (e) => {
    e.preventDefault();
  };

  // useEffect(() => {
  //   const fetchApi = async () => {
      
  //   };
  //   fetchApi();
  // }, []);

  return (
    <>
      <Menu />
      <div className="text-center my-5">
        <h3>Discounts</h3>
      </div>

      <div className="container">
        <div className="row">
          <div className="col m-2">
            <h5><u>Combine Discounts</u></h5>
            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {preds.pred1}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {discounts.map((dis) => {
                  return (
                    <li>
                      <a className="dropdown-item"
                        onClick={() =>
                          setPreds((prevState) => ({
                            ...prevState,
                            pred1: dis,
                          }))
                        }
                      >
                        {dis}
                      </a>
                    </li>
                  )
                })}
              </ul>
            </div>
            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {combinationType}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {combinationTypes.map((comType) => {
                  return (
                    <li>
                      <a className="dropdown-item" onClick={() => setCombinationType(comType)}>
                        {comType}
                      </a>
                    </li>
                  )
                })}
              </ul>
            </div>
            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {preds.pred2}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {discounts.map((dis) => {
                  return (
                    <li>
                      <a className="dropdown-item"
                        onClick={() =>
                          setPreds((prevState) => ({
                            ...prevState,
                            pred2: dis,
                          }))
                        }
                      >
                        {dis}
                      </a>
                    </li>
                  )
                })}
              </ul>
            </div>

            <button className="btn btn-primary mr-lg-3" style={{ width: "50%" }}
            onClick={onCreateCombinedDiscount}>
              Create Combined Discount
            </button>
          </div>

          <div className="col m-2">
            <h5><u>Select Discount Type</u></h5>
            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {discountType}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {discountTypes.map((disType) => {
                  return (
                    <li>
                      <a className="dropdown-item" onClick={() => setDiscountType(disType)}>{disType}</a>
                    </li>
                  )
                })}
              </ul>
            </div>
          </div>
          
          <div className="col m-2">
            <div className="row" hidden={preds.pred1 !== "Create new discount"}>
              <h5><u>Create a single discount #1</u></h5>
              <br/>
              <h2>To be continued...</h2>
              <br/>
              <button className="btn btn-primary mr-lg-3 m-2" style={{ width: "50%" }}
              onClick={onCreateDiscount1}>
                Create Discount #1
              </button>
            </div>

            <div className="row" hidden={preds.pred2 !== "Create new discount"}>
              <h5><u>Create a single discount #2</u></h5>
              <br/>
              <h2>To be continued...</h2>
              <br/>
              <button className="btn btn-primary mr-lg-3 m-2" style={{ width: "50%" }}
              onClick={onCreateDiscount1}>
                Create Discount #2
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Discounts;
