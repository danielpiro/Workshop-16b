import Menu from "../components/menu";
import { useState } from "react";

const Discounts = () => {
  const [discountType1, setDiscountType1] = useState("DiscountType");
  const [discountType2, setDiscountType2] = useState("DiscountType");
  const [discountType3, setDiscountType3] = useState("DiscountType");
  const [discountType4, setDiscountType4] = useState("DiscountType");
  const discountTypes = ["Category", "Price", "Product With Amount", "Product Without Amount"];
  const [operator1, setOperator1] = useState("And/Or/Xor/None");
  const [operator2, setOperator2] = useState("And/Or/Xor/None");
  const [operator3, setOperator3] = useState("And/Or/Xor/None");
  const operators = ["And", "Or", "Xor", "None"];
  const [combinationType, setCombinationType] = useState("Max/Addition");
  const combinationTypes = ["Max", "Addition"];
  // const [allowForbidOption, setAllowForbidOptions] = useState("Allow/Forbid");
  // const allowForbidOptions = ["Allow", "Forbid"];
  const [discount, setDiscount] = useState("Create new discount");
  const discounts = ["Create new precentage discount", "Create new conditional discount", "..."];
  const [policy, setPolicy] = useState("Select Policy");
  const policies = ["..."];

  const [preds, setPreds] = useState({
    pred1: "Choose predicate #1",
    pred2: "Choose predicate #2",
  });
  const [precDiscount1, setPrecDiscount1] = useState({
    precentage: "",
    
  });
  const [precDiscount2, setPrecDiscount2] = useState({
    precentage: "",

  });
  const [condDiscount1, setCondDiscount1] = useState({
    precentage: "",

  });
  const [condDiscount2, setCondDiscount2] = useState({
    precentage: "",

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
            <h5><u>Create Combined Discount</u></h5>
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

          <div className="col m-2" hidden={preds.pred1 !== "Create new precentage discount" && preds.pred2 !== "Create new precentage discount" &&
                                          preds.pred1 !== "Create new conditional discount" && preds.pred2 !== "Create new conditional discount"}
          >
            <h5><u>Select Discount Type</u></h5>
            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {discountType1}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {discountTypes.map((disType) => {
                  return (
                    <li>
                      <a className="dropdown-item" onClick={() => setDiscountType1(disType)}>{disType}</a>
                    </li>
                  )
                })}
              </ul>
            </div>
            
            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {operator1}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {operators.map((oper) => {
                  return (
                    <li>
                      <a className="dropdown-item" onClick={() => setOperator1(oper)}>{oper}</a>
                    </li>
                  )
                })}
              </ul>
            </div>
            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {discountType2}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {discountTypes.map((disType) => {
                  return (
                    <li>
                      <a className="dropdown-item" onClick={() => setDiscountType2(disType)}>{disType}</a>
                    </li>
                  )
                })}
              </ul>
            </div>

            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {operator2}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {operators.map((oper) => {
                  return (
                    <li>
                      <a className="dropdown-item" onClick={() => setOperator2(oper)}>{oper}</a>
                    </li>
                  )
                })}
              </ul>
            </div>

            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {discountType3}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {discountTypes.map((disType) => {
                  return (
                    <li>
                      <a className="dropdown-item" onClick={() => setDiscountType3(disType)}>{disType}</a>
                    </li>
                  )
                })}
              </ul>
            </div>

            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {operator3}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {operators.map((oper) => {
                  return (
                    <li>
                      <a className="dropdown-item" onClick={() => setOperator3(oper)}>{oper}</a>
                    </li>
                  )
                })}
              </ul>
            </div>

            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {discountType4}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {discountTypes.map((disType) => {
                  return (
                    <li>
                      <a className="dropdown-item" onClick={() => setDiscountType4(disType)}>{disType}</a>
                    </li>
                  )
                })}
              </ul>
            </div>
          </div>
          
          <div className="col m-2">
            <div className="row" hidden={preds.pred1 !== "Create new precentage discount"}>
              <h5><u>Create precentage discount #1</u></h5>
              <input type="text" className="form-control m-1" placeholder="Enter discount precentage"
                aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountType1 === "DiscountType"}
                onChange={(e) =>
                  setPrecDiscount1((prevState) => ({
                    ...prevState,
                    precentage: e.target.value,
                  }))
                }
              />
              <div hidden={discountType1 !== "Category"}>
                Category
              </div>
              <div hidden={discountType1 !== "Price"}>
                Price
              </div>
              <div hidden={discountType1 !== "Product With Amount"}>
                Product With Amount
              </div>
              <div hidden={discountType1 !== "Product Without Amount"}>
                Product Without Amount
              </div>
              <button className="btn btn-primary mr-lg-3 m-2" style={{ width: "50%" }}
              onClick={onCreateDiscount1}>
                Create Discount #1
              </button>
            </div>
            <div className="row" hidden={preds.pred2 !== "Create new precentage discount"}>
              <h5><u>Create precentage discount #2</u></h5>
              <input type="text" className="form-control m-1" placeholder="Enter discount precentage"
                aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountType2 === "DiscountType"}
                onChange={(e) =>
                  setPrecDiscount2((prevState) => ({
                    ...prevState,
                    precentage: e.target.value,
                  }))
                }
              />
              <div>
                Category
              </div>
              <div>
                Price
              </div>
              <div>
                Product With Amount
              </div>
              <div>
                Product Without Amount
              </div>
              <button className="btn btn-primary mr-lg-3 m-2" style={{ width: "50%" }}
              onClick={onCreateDiscount1}>
                Create Discount #2
              </button>
            </div>

            <div className="row" hidden={preds.pred1 !== "Create new conditional discount"}>
              <h5><u>Create conditional discount #1</u></h5>
              <input type="text" className="form-control m-1" placeholder="Enter discount precentage"
                aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountType3 === "DiscountType"}
                onChange={(e) =>
                  setCondDiscount1((prevState) => ({
                    ...prevState,
                    precentage: e.target.value,
                  }))
                }
              />
              <div>
                Category
              </div>
              <div>
                Price
              </div>
              <div>
                Product With Amount
              </div>
              <div>
                Product Without Amount
              </div>
              <button className="btn btn-primary mr-lg-3 m-2" style={{ width: "50%" }}
              onClick={onCreateDiscount1}>
                Create Discount #1
              </button>
            </div>
            <div className="row" hidden={preds.pred2 !== "Create new conditional discount"}>
              <h5><u>Create conditional conditional #2</u></h5>
              <input type="text" className="form-control m-1" placeholder="Enter discount precentage"
                aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountType4 === "DiscountType"}
                onChange={(e) =>
                  setCondDiscount2((prevState) => ({
                    ...prevState,
                    precentage: e.target.value,
                  }))
                }
              />
              <div>
                <h2>To be continued...</h2>
              </div>
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
