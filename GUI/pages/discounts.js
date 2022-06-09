import Menu from "../components/menu";
import { useState } from "react";

const Discounts = () => {
  const [discountTypePercentage1, setDiscountTypePercentage1] = useState("DiscountType");
  const [discountTypePercentage2, setDiscountTypePercentage2] = useState("DiscountType");
  const [discountTypePercentage3, setDiscountTypePercentage3] = useState("DiscountType");
  const [discountTypePercentage4, setDiscountTypePercentage4] = useState("DiscountType");
  const [discountTypeCond1, setDiscountTypeCond1] = useState("DiscountType");
  const [discountTypeCond2, setDiscountTypeCond2] = useState("DiscountType");
  const [discountTypeCond3, setDiscountTypeCond3] = useState("DiscountType");
  const [discountTypeCond4, setDiscountTypeCond4] = useState("DiscountType");
  const discountTypes = ["Category", "Price", "Product With Amount", "Product Without Amount"];
  const [operatorPercentage1, setOperatorPercentage1] = useState("And/Or/Xor/None");
  const [operatorPercentage2, setOperatorPercentage2] = useState("And/Or/Xor/None");
  const [operatorPercentage3, setOperatorPercentage3] = useState("And/Or/Xor/None");
  const [operatorCond1, setOperatorCond1] = useState("And/Or/Xor/None");
  const [operatorCond2, setOperatorCond2] = useState("And/Or/Xor/None");
  const [operatorCond3, setOperatorCond3] = useState("And/Or/Xor/None");
  const operators = ["And", "Or", "Xor", "None"];
  const [combinationType, setCombinationType] = useState("Max/Addition");
  const combinationTypes = ["Max", "Addition"];
  // const [allowForbidOption, setAllowForbidOptions] = useState("Allow/Forbid");
  // const allowForbidOptions = ["Allow", "Forbid"];
  const [discount, setDiscount] = useState("Create new discount");
  const discounts = ["Create new precentage discount", "Create new conditional discount"];
  const [policies, setPolicies] = useState(["..."]);
  const [policyType, setPolicyType] = useState("Select Policy Type");
  const policyTypes = ["CartPolicy", "CategoryPolicy", "ProductWithoutAmountPolicy", "ProductWithAmountPolicy", "UserIdPolicy",
                      "UseAgePolicy", "OnHoursOfTheDayPolicy", "OnDaysOfTheWeekPolicy", "OnDayOfMonthPolicy", "PricePredicate"];
  const daysOfWeek = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
  const daysOfMonth = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31];
  const categories = ["Other", "Appliances", "Apps$Games", "Handmade", "Baby"];

  const [preds, setPreds] = useState({
    pred1: "Choose discount #1",
    pred2: "Choose discount #2",
  });
  const [precDiscount1, setPrecDiscount1] = useState({
    precentage: "",
    category: "Select a category",
    minPrice: "",
    productName: "",
    minAmountOfProduct: "",
  });
  const [precDiscount2, setPrecDiscount2] = useState({
    precentage: "",
    category: "Select a category",
    minPrice: "",
    productName: "",
    minAmountOfProduct: "",
  });
  const [condDiscount1, setCondDiscount1] = useState({
    policy: "Select conditional discount policy",
    discount: "Select discount",
  });
  const [condDiscount2, setCondDiscount2] = useState({
    policy: "Select conditional discount policy",
    discount: "Select discount",
  });

  const onCreateCombinedDiscount = (e) => {
    e.preventDefault();
  };

  const onCreatePrecentageDiscount1 = (e) => {
    e.preventDefault();
  };

  const onCreatePrecentageDiscount2 = (e) => {
    e.preventDefault();
  };

  const onCreateConditionalDiscount1 = (e) => {
    e.preventDefault();
  };

  const onCreateConditionalDiscount2 = (e) => {
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

          <div className="col m-2">
            <div hidden={preds.pred1 !== "Create new precentage discount" && preds.pred2 !== "Create new precentage discount"}>
              <h5><u>Select Precentage Discount Type</u></h5>
                <div className="input-group mb-3">
                  {/* <h2>(((</h2> */}
                  <div className="dropdown m-1">
                    <button className="btn btn-secondary dropdown-toggle" type="button"
                      id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        {discountTypePercentage1}
                    </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                      {discountTypes.map((disType) => {
                        return (
                          <li>
                            <a className="dropdown-item" onClick={() => setDiscountTypePercentage1(disType)}>{disType}</a>
                          </li>
                        )
                      })}
                    </ul>
                  </div>
                  <div className="dropdown m-1">
                    <button className="btn btn-secondary dropdown-toggle" type="button"
                      id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        {operatorPercentage1}
                    </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                      {operators.map((oper) => {
                        return (
                          <li>
                            <a className="dropdown-item" onClick={() => setOperatorPercentage1(oper)}>{oper}</a>
                          </li>
                        )
                      })}
                    </ul>
                  </div>
                  <div className="dropdown m-1">
                    <button className="btn btn-secondary dropdown-toggle" type="button"
                      id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        {discountTypePercentage2}
                    </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                      {discountTypes.map((disType) => {
                        return (
                          <li>
                            <a className="dropdown-item" onClick={() => setDiscountTypePercentage2(disType)}>{disType}</a>
                          </li>
                        )
                      })}
                    </ul>
                  </div>
                  {/* <h2>)</h2> */}
                </div>
              
                <div className="input-group mb-3">
                  <div className="dropdown m-1">
                    <button className="btn btn-secondary dropdown-toggle" type="button"
                      id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        {operatorPercentage2}
                    </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                      {operators.map((oper) => {
                        return (
                          <li>
                            <a className="dropdown-item" onClick={() => setOperatorPercentage2(oper)}>{oper}</a>
                          </li>
                        )
                      })}
                    </ul>
                  </div>

                  <div className="dropdown m-1">
                    <button className="btn btn-secondary dropdown-toggle" type="button"
                      id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        {discountTypePercentage3}
                    </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                      {discountTypes.map((disType) => {
                        return (
                          <li>
                            <a className="dropdown-item" onClick={() => setDiscountTypePercentage3(disType)}>{disType}</a>
                          </li>
                        )
                      })}
                    </ul>
                  </div>
                  {/* <h2>)</h2> */}
                </div>
              
                <div className="input-group mb-3">
                  <div className="dropdown m-1">
                    <button className="btn btn-secondary dropdown-toggle" type="button"
                      id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        {operatorPercentage3}
                    </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                      {operators.map((oper) => {
                        return (
                          <li>
                            <a className="dropdown-item" onClick={() => setOperatorPercentage3(oper)}>{oper}</a>
                          </li>
                        )
                      })}
                    </ul>
                  </div>

                  <div className="dropdown m-1">
                    <button className="btn btn-secondary dropdown-toggle" type="button"
                      id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        {discountTypePercentage4}
                    </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                      {discountTypes.map((disType) => {
                        return (
                          <li>
                            <a className="dropdown-item" onClick={() => setDiscountTypePercentage4(disType)}>{disType}</a>
                          </li>
                        )
                      })}
                    </ul>
                  </div>
                  {/* <h2>)</h2> */}
                </div>
            </div>

            <div hidden={preds.pred1 !== "Create new conditional discount" && preds.pred2 !== "Create new conditional discount"}>
            <h5><u>Select Conditional Discount Type</u></h5>
              <div className="input-group mb-3">
                <div className="dropdown m-1">
                  <button className="btn btn-secondary dropdown-toggle" type="button"
                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                      {discountTypeCond1}
                  </button>
                  <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {policyTypes.map((disType) => {
                      return (
                        <li>
                          <a className="dropdown-item" onClick={() => setDiscountTypeCond1(disType)}>{disType}</a>
                        </li>
                      )
                    })}
                  </ul>
                </div>
                
                <div className="dropdown m-1">
                  <button className="btn btn-secondary dropdown-toggle" type="button"
                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                      {operatorCond1}
                  </button>
                  <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {operators.map((oper) => {
                      return (
                        <li>
                          <a className="dropdown-item" onClick={() => setOperatorCond1(oper)}>{oper}</a>
                        </li>
                      )
                    })}
                  </ul>
                </div>
                <div className="dropdown m-1">
                  <button className="btn btn-secondary dropdown-toggle" type="button"
                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                      {discountTypeCond2}
                  </button>
                  <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {policyTypes.map((disType) => {
                      return (
                        <li>
                          <a className="dropdown-item" onClick={() => setDiscountTypeCond2(disType)}>{disType}</a>
                        </li>
                      )
                    })}
                  </ul>
                </div>
              </div>
              <div className="input-group mb-3">
                <div className="dropdown m-1">
                  <button className="btn btn-secondary dropdown-toggle" type="button"
                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                      {operatorCond2}
                  </button>
                  <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {operators.map((oper) => {
                      return (
                        <li>
                          <a className="dropdown-item" onClick={() => setOperatorCond2(oper)}>{oper}</a>
                        </li>
                      )
                    })}
                  </ul>
                </div>

                <div className="dropdown m-1">
                  <button className="btn btn-secondary dropdown-toggle" type="button"
                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                      {discountTypeCond3}
                  </button>
                  <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {policyTypes.map((disType) => {
                      return (
                        <li>
                          <a className="dropdown-item" onClick={() => setDiscountTypeCond3(disType)}>{disType}</a>
                        </li>
                      )
                    })}
                  </ul>
                </div>
              </div>
              
              <div className="input-group mb-3">
                <div className="dropdown m-1">
                  <button className="btn btn-secondary dropdown-toggle" type="button"
                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                      {operatorCond3}
                  </button>
                  <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {operators.map((oper) => {
                      return (
                        <li>
                          <a className="dropdown-item" onClick={() => setOperatorCond3(oper)}>{oper}</a>
                        </li>
                      )
                    })}
                  </ul>
                </div>

                <div className="dropdown m-1">
                  <button className="btn btn-secondary dropdown-toggle" type="button"
                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                      {discountTypeCond4}
                  </button>
                  <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {policyTypes.map((disType) => {
                      return (
                        <li>
                          <a className="dropdown-item" onClick={() => setDiscountTypeCond4(disType)}>{disType}</a>
                        </li>
                      )
                    })}
                  </ul>
                </div>
              </div>  
            </div>
          </div>
          
          <div className="col m-2">
            <div className="row" hidden={preds.pred1 !== "Create new precentage discount"}> 
              <h5><u>Create precentage discount #1</u></h5>
              <div className="input-group mb-3">
                <span className="input-group-text" id="basic-addon1" hidden={discountTypePercentage1 === "DiscountType" && 
                        discountTypePercentage2 === "DiscountType" && discountTypePercentage3 === "DiscountType" && discountTypePercentage4 === "DiscountType"}>
                  Enter Discount Precentage
                </span>
                <input type="text" className="form-control" placeholder="0-100"
                  aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType" && 
                        discountTypePercentage2 === "DiscountType" && discountTypePercentage3 === "DiscountType" && discountTypePercentage4 === "DiscountType"}
                  onChange={(e) =>
                    setPrecDiscount1((prevState) => ({
                      ...prevState,
                      precentage: e.target.value,
                    }))
                  }
                />
              </div>
              
              <div hidden={discountTypePercentage1 !== "Category" && discountTypePercentage2 !== "Category" && discountTypePercentage3 !== "Category" && discountTypePercentage4 !== "Category"}>
              <div className="dropdown m-1">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {precDiscount1.category}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    {categories.map((cat) => {
                      return (
                        <li>
                          <a className="dropdown-item"
                             onClick={(e) =>
                              setPrecDiscount1((prevState) => ({
                                ...prevState,
                                category: cat,
                              }))
                            }
                          >
                            {cat}
                          </a>
                        </li>
                      )
                    })} 
                  </ul>
                </div>
              </div>
              <div hidden={discountTypePercentage1 !== "Price" && discountTypePercentage2 !== "Price" && discountTypePercentage3 !== "Price" && discountTypePercentage4 !== "Price"}>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}>Min Price For Dicount</span>
                  <input type="text" className="form-control" placeholder="Price"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount1((prevState) => ({
                        ...prevState,
                        minPrice: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div hidden={discountTypePercentage1 !== "Product With Amount" && discountTypePercentage2 !== "Product With Amount" && discountTypePercentage3 !== "Product With Amount" && discountTypePercentage4 !== "Product With Amount"}>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">Product Name</span>
                  <input type="text" className="form-control" placeholder="Product Name With Amount"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount1((prevState) => ({
                        ...prevState,
                        productName: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">Min Amount Of Product In Cart</span>
                  <input type="text" className="form-control" placeholder="Amount"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount1((prevState) => ({
                        ...prevState,
                        minAmountOfProduct: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div hidden={discountTypePercentage1 !== "Product Without Amount" && discountTypePercentage2 !== "Product Without Amount" && 
                      discountTypePercentage3 !== "Product Without Amount" && discountTypePercentage4 !== "Product Without Amount"}>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">Product Name</span>
                  <input type="text" className="form-control" placeholder="Product Name Without Amount"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount1((prevState) => ({
                        ...prevState,
                        productName: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <button className="btn btn-primary mr-lg-3 m-2" style={{ width: "50%" }}
              onClick={onCreatePrecentageDiscount1}>
                Create Discount #1
              </button>
            </div>
            <div className="row" hidden={preds.pred2 !== "Create new precentage discount"}>
            <h5><u>Create precentage discount #2</u></h5>
              <div className="input-group mb-3">
                <span className="input-group-text" id="basic-addon1" hidden={discountTypePercentage1 === "DiscountType" && discountTypePercentage2 === "DiscountType" &&
                       discountTypePercentage3 === "DiscountType" && discountTypePercentage4 === "DiscountType"}>
                  Enter Discount Precentage
                </span>
                <input type="text" className="form-control" placeholder="0-100"
                  aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType" && 
                        discountTypePercentage2 === "DiscountType" && discountTypePercentage3 === "DiscountType" && discountTypePercentage4 === "DiscountType"}
                  onChange={(e) =>
                    setPrecDiscount2((prevState) => ({
                      ...prevState,
                      precentage: e.target.value,
                    }))
                  }
                />
              </div>
              
              <div hidden={discountTypePercentage1 !== "Category" && discountTypePercentage2 !== "Category" && discountTypePercentage3 !== "Category" && discountTypePercentage4 !== "Category"}>
              <div className="dropdown m-1">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {precDiscount2.category}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    {categories.map((cat) => {
                      return (
                        <li>
                          <a className="dropdown-item"
                             onClick={(e) =>
                              setPrecDiscount2((prevState) => ({
                                ...prevState,
                                category: cat,
                              }))
                            }
                          >
                            {cat}
                          </a>
                        </li>
                      )
                    })} 
                  </ul>
                </div>
              </div>
              <div hidden={discountTypePercentage1 !== "Price" && discountTypePercentage2 !== "Price" && discountTypePercentage3 !== "Price" && discountTypePercentage4 !== "Price"}>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">Min Price For Dicount</span>
                  <input type="text" className="form-control" placeholder="Price"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount2((prevState) => ({
                        ...prevState,
                        minPrice: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div hidden={discountTypePercentage1 !== "Product With Amount" && discountTypePercentage2 !== "Product With Amount" && discountTypePercentage3 !== "Product With Amount" && discountTypePercentage4 !== "Product With Amount"}>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">Product Name</span>
                  <input type="text" className="form-control" placeholder="Product Name With Amount"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount2((prevState) => ({
                        ...prevState,
                        productName: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">Min Amount Of Product In Cart</span>
                  <input type="text" className="form-control" placeholder="Amount"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount2((prevState) => ({
                        ...prevState,
                        minAmountOfProduct: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div hidden={discountTypePercentage1 !== "Product Without Amount" && discountTypePercentage2 !== "Product Without Amount" && 
                      discountTypePercentage3 !== "Product Without Amount" && discountTypePercentage4 !== "Product Without Amount"}>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">Product Name</span>
                  <input type="text" className="form-control" placeholder="Product Name Without Amount"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount2((prevState) => ({
                        ...prevState,
                        productName: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <button className="btn btn-primary mr-lg-3 m-2" style={{ width: "50%" }}
              onClick={onCreatePrecentageDiscount2}>
                Create Discount #2
              </button>
            </div>

            <div className="row" hidden={preds.pred1 !== "Create new conditional discount"}>
              <h5><u>Create conditional discount #1</u></h5>
              <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {condDiscount1.policy}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {policies.map((pol) => {
                  return (
                    <li>
                      <a className="dropdown-item"
                        onClick={() =>
                          setCondDiscount1((prevState) => ({
                            ...prevState,
                            policy: pol,
                          }))
                        }
                      >
                        {pol}
                      </a>
                    </li>
                  )
                })}
              </ul>
            </div>
            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {condDiscount1.discount}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {discounts.map((dis) => {
                  return (
                    <li>
                      <a className="dropdown-item"
                        onClick={() =>
                          setCondDiscount1((prevState) => ({
                            ...prevState,
                            discount: dis,
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
              
              <button className="btn btn-primary mr-lg-3 m-2" style={{ width: "50%" }}
              onClick={onCreateConditionalDiscount1}>
                Create Discount #1
              </button>
            </div>
            <div className="row" hidden={preds.pred2 !== "Create new conditional discount"}>
              <h5><u>Create conditional conditional #2</u></h5>
              <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {condDiscount1.policy}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {policies.map((pol) => {
                  return (
                    <li>
                      <a className="dropdown-item"
                        onClick={() =>
                          setCondDiscount1((prevState) => ({
                            ...prevState,
                            policy: pol,
                          }))
                        }
                      >
                        {pol}
                      </a>
                    </li>
                  )
                })}
              </ul>
            </div>
            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {condDiscount2.discount}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {discounts.map((dis) => {
                  return (
                    <li>
                      <a className="dropdown-item"
                        onClick={() =>
                          setCondDiscount2((prevState) => ({
                            ...prevState,
                            discount: dis,
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
              <button className="btn btn-primary mr-lg-3 m-2" style={{ width: "50%" }}
              onClick={onCreateConditionalDiscount2}>
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
