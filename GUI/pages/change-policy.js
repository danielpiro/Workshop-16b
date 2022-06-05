import Menu from "../components/menu";
import { useEffect, useState } from "react";

const ChangePolicy = () => {
  const [policyType, setPolicyType] = useState("PolicyType");
  const [combinationType, setCombinationType] = useState("And/Or/Xor");
  const [preds, setPreds] = useState({
    pred1: "Choose predicate #1",
    pred2: "Choose predicate #2",
  });
  const [predsFeatures1, setPredsFeature1] = useState({
    allowORforbid: "Allow/Forbid",
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
  const [predsFeatures2, setPredsFeature2] = useState({
    allowORforbid: "Allow/Forbid",
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
  const daysOfWeek = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
  const daysOfMonth = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31];
  const [policies, setPolicies] = useState(["Create new predicate", "Choose Existing predicate"]);

  useEffect(() => {
    const fetchData = async () => {

    };
    fetchData();
  }, []);

  const onUpdatePolicy = (e) => {
    e.preventDefault();
    console.log(policyType);
  };

  const onCreatePredicate1 = (e) => {
    e.preventDefault();
    console.log("creating new predicate");
  };

  const onCreatePredicate2 = (e) => {
    e.preventDefault();
    console.log("creating new predicate");
  };

  return (
    <>
      <Menu />
      <div className="text-center my-5">
        <h3>Change Policy</h3>
      </div>

      <div className="container">
        <div className="row">
          <div className="col d-flex justify-content-center">
            <div className="dropdown m-1">
              <button
                className="btn btn-secondary dropdown-toggle"
                type="button"
                id="dropdownMenuButton1"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                {policyType}
              </button>
              <ul
                className="dropdown-menu"
                aria-labelledby="dropdownMenuButton1"
              >
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("PolicyType")}></a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("Total cart price")}>
                    Total cart price
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("Quantity in cart")}>
                    Quantity in cart
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("Product should be in cart")}>
                    Product should be in cart
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("Product should be in cart with minimum quantity")}>
                    Product should be in cart with minimum quantity
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("Day Of Month")}>
                    Day Of Month
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("Day Of Week")}>
                    Day Of Week
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("Hour Of Day")}>
                    Hour Of Day
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("Specific user")}>
                    Specific user
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("Age")}>
                    Age
                  </a>
                </li>
              </ul>
            </div>
          </div>
          <div className="col">
            <h4>
              <u>Create policy</u>
            </h4>
            <div className="dropdown m-1">
              <button
                className="btn btn-secondary dropdown-toggle"
                type="button"
                id="dropdownMenuButton1"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                {preds.pred1}
              </button>
              <ul
                className="dropdown-menu"
                aria-labelledby="dropdownMenuButton1"
              >
                {policies.map((policy) => {
                  return (
                    <li>
                      <a className="dropdown-item"
                        onClick={() =>
                          setPreds((prevState) => ({
                            ...prevState,
                            pred1: policy,
                          }))
                        }
                      >
                        {policy}
                      </a>
                    </li>
                  )
                })}
              </ul>
            </div>
            <div className="dropdown m-1">
              <button
                className="btn btn-secondary dropdown-toggle"
                type="button"
                id="dropdownMenuButton1"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                {combinationType}
              </button>
              <ul
                className="dropdown-menu"
                aria-labelledby="dropdownMenuButton1"
              >
                <li>
                  <a className="dropdown-item"
                     onClick={() => {
                      setCombinationType("None of the above");
                      setPreds((prevState) => ({
                        ...prevState,
                        pred2: "",
                        }))
                      }  
                    }
                  >
                    None of the above
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setCombinationType("And")}>
                    And
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setCombinationType("Or")}>
                    Or
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setCombinationType("Xor")}>
                    Xor
                  </a>
                </li>
              </ul>
            </div>
            <div className="dropdown m-1" style={{display: combinationType=="None of the above"? "none" : "block"}}>
              <button
                className="btn btn-secondary dropdown-toggle"
                type="button"
                id="dropdownMenuButton1"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                {preds.pred2}
              </button>
              <ul
                className="dropdown-menu"
                aria-labelledby="dropdownMenuButton1"
              >
                {policies.map((policy) => {
                  return (
                    <li>
                      <a className="dropdown-item"
                        onClick={() =>
                          setPreds((prevState) => ({
                            ...prevState,
                            pred2: policy,
                          }))
                        }
                      >
                        {policy}
                      </a>
                    </li>
                  )
                })}
              </ul>
            </div>
          </div>
          <div className="col">
            <div
              className="row"
              style={{
                display:
                  preds.pred1 == "Create new predicate" ? "block" : "none",
              }}
            >
              <h4>
                <u>Create Single Predicate Policy #1</u>
              </h4>
              <div
                style={{
                  display: policyType == "Total cart price" ? "block" : "none",
                }}
              >
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Min Price
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter price in NIS"
                    aria-label="MinPrice"
                    aria-describedby="basic-addon1"
                    value={predsFeatures1.minPrice}
                    onChange={(e) =>
                      setPredsFeature1((prevState) => ({
                        ...prevState,
                        minPrice: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Max Price
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter price in NIS"
                    aria-label="MaxPrice"
                    aria-describedby="basic-addon1"
                    value={predsFeatures1.maxPrice}
                    onChange={(e) =>
                      setPredsFeature1((prevState) => ({
                        ...prevState,
                        maxPrice: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div
                style={{
                  display: policyType == "Quantity in cart" ? "block" : "none",
                }}
              >
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Min Quantity
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter minimum quantity"
                    aria-label="MinQuantity"
                    aria-describedby="basic-addon1"
                    value={predsFeatures1.minQunatity}
                    onChange={(e) =>
                      setPredsFeature1((prevState) => ({
                        ...prevState,
                        minQunatity: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Max Quantity
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter maximum quantity"
                    aria-label="MaxQuantity"
                    aria-describedby="basic-addon1"
                    value={predsFeatures1.maxQunatity}
                    onChange={(e) =>
                      setPredsFeature1((prevState) => ({
                        ...prevState,
                        maxQunatity: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div
                style={{
                  display:
                    policyType == "Product should be in cart"
                      ? "block"
                      : "none",
                }}
              >
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Product Name
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter product name"
                    aria-label="ProductName"
                    aria-describedby="basic-addon1"
                    value={predsFeatures1.productShouldBeInCart}
                    onChange={(e) =>
                      setPredsFeature1((prevState) => ({
                        ...prevState,
                        productShouldBeInCart: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div
                style={{
                  display:
                    policyType ==
                    "Product should be in cart with minimum quantity"
                      ? "block"
                      : "none",
                }}
              >
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Product Name
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter product name"
                    aria-label="ProductName"
                    aria-describedby="basic-addon1"
                    value={predsFeatures1.productShouldBeInCart}
                    onChange={(e) =>
                      setPredsFeature1((prevState) => ({
                        ...prevState,
                        productShouldBeInCart: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Min Quantity
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter minimum quantity"
                    aria-label="ProductMinQuantity"
                    aria-describedby="basic-addon1"
                    value={predsFeatures1.productShouldBeInCartMinQunatity}
                    onChange={(e) =>
                      setPredsFeature1((prevState) => ({
                        ...prevState,
                        productShouldBeInCartMinQunatity: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div
                style={{
                  display: policyType == "Day Of Month" ? "block" : "none",
                }}
              >
                <div className="dropdown m-1">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {predsFeatures1.allowORforbid}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            allowORforbid: "Allow",
                          }))
                        }
                      >
                        Allow
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            allowORforbid: "Forbid",
                          }))
                        }
                      >
                        Forbid
                      </a>
                    </li>
                  </ul>
                </div>
                <div className="dropdown m-1">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {predsFeatures1.dayOfMonth}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    {daysOfMonth.map((day) => {
                      return (
                        <li>
                          <a className="dropdown-item"
                             onClick={(e) =>
                              setPredsFeature1((prevState) => ({
                                ...prevState,
                                dayOfMonth: day,
                              }))
                            }
                          >
                            {day}
                          </a>
                        </li>
                      )
                    })} 
                  </ul>
                </div>
              </div>
              <div
                style={{
                  display: policyType == "Day Of Week" ? "block" : "none",
                }}
              >
                <div className="dropdown m-1">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {predsFeatures1.allowORforbid}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    <li>
                      <a className="dropdown-item"
                         onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            allowORforbid: "Allow",
                          }))
                        }
                      >
                        Allow
                      </a>
                    </li>
                    <li>
                      <a className="dropdown-item"
                         onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            allowORforbid: "Forbid",
                          }))
                        }
                      >
                        Forbid
                      </a>
                    </li>
                  </ul>
                </div>
                <div className="dropdown m-1">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {predsFeatures1.dayOfWeek}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    {daysOfWeek.map((day) => {
                      return (
                        <li>
                          <a className="dropdown-item"
                             onClick={(e) =>
                              setPredsFeature1((prevState) => ({
                                ...prevState,
                                dayOfWeek: day,
                              }))
                            }
                          >
                            {day}
                          </a>
                        </li>
                      )
                    })} 
                  </ul>
                </div>
              </div>
              <div
                style={{
                  display: policyType == "Hour Of Day" ? "block" : "none",
                }}
              >
                <div className="dropdown">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {predsFeatures1.allowORforbid}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            allowORforbid: "Allow",
                          }))
                        }
                      >
                        Allow
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            allowORforbid: "Forbid",
                          }))
                        }
                      >
                        Forbid
                      </a>
                    </li>
                  </ul>
                </div>
                <br />
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Start Hour
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter hour (Format- <hh:mm>)"
                    aria-label="startHour"
                    aria-describedby="basic-addon1"
                    value={predsFeatures1.startHourOfDay}
                    onChange={(e) =>
                      setPredsFeature1((prevState) => ({
                        ...prevState,
                        startHourOfDay: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    End Hour
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter hour (Format- <hh:mm>)"
                    aria-label="endHour"
                    aria-describedby="basic-addon1"
                    value={predsFeatures1.endHourOfDay}
                    onChange={(e) =>
                      setPredsFeature1((prevState) => ({
                        ...prevState,
                        endHourOfDay: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div
                style={{
                  display: policyType == "Specific user" ? "block" : "none",
                }}
              >
                <div className="dropdown">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {predsFeatures1.allowORforbid}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            allowORforbid: "Allow",
                          }))
                        }
                      >
                        Allow
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            allowORforbid: "Forbid",
                          }))
                        }
                      >
                        Forbid
                      </a>
                    </li>
                  </ul>
                </div>
                <br />
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Username
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter username"
                    aria-label="specificUser"
                    aria-describedby="basic-addon1"
                    value={predsFeatures1.specificUser}
                    onChange={(e) =>
                      setPredsFeature1((prevState) => ({
                        ...prevState,
                        specificUser: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div style={{ display: policyType == "Age" ? "block" : "none" }}>
                <div className="dropdown">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {predsFeatures1.allowORforbid}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            allowORforbid: "Allow",
                          }))
                        }
                      >
                        Allow
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            allowORforbid: "Forbid",
                          }))
                        }
                      >
                        Forbid
                      </a>
                    </li>
                  </ul>
                </div>
                <br />
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Age
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter username"
                    aria-label="specificUser"
                    aria-describedby="basic-addon1"
                    value={predsFeatures1.age}
                    onChange={(e) =>
                      setPredsFeature1((prevState) => ({
                        ...prevState,
                        age: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
            </div>
            <div
              className="row m-1"
              style={{
                display:
                preds.pred1 == "Create new predicate" &&
                combinationType != ""
                    ? "block"
                    : "none",
              }}
            >
              <button
                className="btn btn-primary mr-lg-3"
                style={{ width: "100%" }}
                onClick={onCreatePredicate1}
              >
                Create Predicate 1
              </button>
            </div>
            <div
              className="row"
              style={{
                display:
                  preds.pred2 == "Create new predicate" ? "block" : "none",
              }}
            >
              <h4>
                <u>Create predicate #2</u>
              </h4>
              <div
                style={{
                  display: policyType == "Total cart price" ? "block" : "none",
                }}
              >
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Min Price
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter price in NIS"
                    aria-label="MinPrice"
                    aria-describedby="basic-addon1"
                    value={predsFeatures2.minPrice}
                    onChange={(e) =>
                      setPredsFeature2((prevState) => ({
                        ...prevState,
                        minPrice: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Max Price
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter price in NIS"
                    aria-label="MaxPrice"
                    aria-describedby="basic-addon1"
                    value={predsFeatures2.maxPrice}
                    onChange={(e) =>
                      setPredsFeature2((prevState) => ({
                        ...prevState,
                        maxPrice: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div
                style={{
                  display: policyType == "Quantity in cart" ? "block" : "none",
                }}
              >
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Min Quantity
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter minimum quantity"
                    aria-label="MinQuantity"
                    aria-describedby="basic-addon1"
                    value={predsFeatures2.minQunatity}
                    onChange={(e) =>
                      setPredsFeature2((prevState) => ({
                        ...prevState,
                        minQunatity: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Max Quantity
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter maximum quantity"
                    aria-label="MaxQuantity"
                    aria-describedby="basic-addon1"
                    value={predsFeatures2.maxQunatity}
                    onChange={(e) =>
                      setPredsFeature2((prevState) => ({
                        ...prevState,
                        maxQunatity: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div
                style={{
                  display:
                    policyType == "Product should be in cart"
                      ? "block"
                      : "none",
                }}
              >
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Product Name
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter product name"
                    aria-label="ProductName"
                    aria-describedby="basic-addon1"
                    value={predsFeatures2.productShouldBeInCart}
                    onChange={(e) =>
                      setPredsFeature2((prevState) => ({
                        ...prevState,
                        productShouldBeInCart: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div
                style={{
                  display:
                    policyType ==
                    "Product should be in cart with minimum quantity"
                      ? "block"
                      : "none",
                }}
              >
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Product Name
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter product name"
                    aria-label="ProductName"
                    aria-describedby="basic-addon1"
                    value={predsFeatures2.productShouldBeInCart}
                    onChange={(e) =>
                      setPredsFeature2((prevState) => ({
                        ...prevState,
                        productShouldBeInCart: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Min Quantity
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter minimum quantity"
                    aria-label="ProductMinQuantity"
                    aria-describedby="basic-addon1"
                    value={predsFeatures2.productShouldBeInCartMinQunatity}
                    onChange={(e) =>
                      setPredsFeature2((prevState) => ({
                        ...prevState,
                        productShouldBeInCartMinQunatity: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div
                style={{
                  display: policyType == "Day Of Month" ? "block" : "none",
                }}
              >
                <div className="dropdown m-1">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {predsFeatures2.allowORforbid}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            allowORforbid: "Allow",
                          }))
                        }
                      >
                        Allow
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            allowORforbid: "Forbid",
                          }))
                        }
                      >
                        Forbid
                      </a>
                    </li>
                  </ul>
                </div>
                <div className="dropdown m-1">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {predsFeatures1.dayOfMonth}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    {daysOfMonth.map((day) => {
                      return (
                        <li>
                          <a className="dropdown-item"
                             onClick={(e) =>
                              setPredsFeature2((prevState) => ({
                                ...prevState,
                                dayOfMonth: day,
                              }))
                            }
                          >
                            {day}
                          </a>
                        </li>
                      )
                    })}
                  </ul>
                </div>
              </div>
              <div
                style={{
                  display: policyType == "Day Of Week" ? "block" : "none",
                }}
              >
                <div className="dropdown m-1">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {predsFeatures2.allowORforbid}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            allowORforbid: "Allow",
                          }))
                        }
                      >
                        Allow
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            allowORforbid: "Forbid",
                          }))
                        }
                      >
                        Forbid
                      </a>
                    </li>
                  </ul>
                </div>
                <div className="dropdown m-1">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {predsFeatures2.dayOfWeek}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    {daysOfWeek.map((day) => {
                      return (
                        <li>
                          <a className="dropdown-item"
                             onClick={(e) =>
                              setPredsFeature2((prevState) => ({
                                ...prevState,
                                dayOfWeek: day,
                              }))
                            }
                          >
                            {day}
                          </a>
                        </li>
                      )
                    })} 
                  </ul>
                </div>
              </div>
              <div
                style={{
                  display: policyType == "Hour Of Day" ? "block" : "none",
                }}
              >
                <div className="dropdown">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {predsFeatures2.allowORforbid}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            allowORforbid: "Allow",
                          }))
                        }
                      >
                        Allow
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            allowORforbid: "Forbid",
                          }))
                        }
                      >
                        Forbid
                      </a>
                    </li>
                  </ul>
                </div>
                <br />
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Start Hour
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter hour (Format- <hh:mm>)"
                    aria-label="startHour"
                    aria-describedby="basic-addon1"
                    value={predsFeatures2.startHourOfDay}
                    onChange={(e) =>
                      setPredsFeature2((prevState) => ({
                        ...prevState,
                        startHourOfDay: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    End Hour
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter hour (Format- <hh:mm>)"
                    aria-label="endHour"
                    aria-describedby="basic-addon1"
                    value={predsFeatures2.endHourOfDay}
                    onChange={(e) =>
                      setPredsFeature2((prevState) => ({
                        ...prevState,
                        endHourOfDay: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div
                style={{
                  display: policyType == "Specific user" ? "block" : "none",
                }}
              >
                <div className="dropdown">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {predsFeatures2.allowORforbid}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            allowORforbid: "Allow",
                          }))
                        }
                      >
                        Allow
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            allowORforbid: "Forbid",
                          }))
                        }
                      >
                        Forbid
                      </a>
                    </li>
                  </ul>
                </div>
                <br />
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Username
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter username"
                    aria-label="specificUser"
                    aria-describedby="basic-addon1"
                    value={predsFeatures2.specificUser}
                    onChange={(e) =>
                      setPredsFeature2((prevState) => ({
                        ...prevState,
                        specificUser: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div style={{ display: policyType == "Age" ? "block" : "none" }}>
                <div className="dropdown">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {predsFeatures2.allowORforbid}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            allowORforbid: "Allow",
                          }))
                        }
                      >
                        Allow
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            allowORforbid: "Forbid",
                          }))
                        }
                      >
                        Forbid
                      </a>
                    </li>
                  </ul>
                </div>
                <br />
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    Age
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter username"
                    aria-label="specificUser"
                    aria-describedby="basic-addon1"
                    value={predsFeatures2.age}
                    onChange={(e) =>
                      setPredsFeature2((prevState) => ({
                        ...prevState,
                        age: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
            </div>
            <div
              className="row m-1"
              style={{
                display:
                preds.pred2 == "Create new predicate" &&
                combinationType != ""
                    ? "block"
                    : "none",
              }}
            >
              <button
                className="btn btn-primary mr-lg-3"
                style={{ width: "100%" }}
                onClick={onCreatePredicate2}
              >
                Create Single Predicate Policy #2
              </button>
            </div>
          </div>
        </div>

        <br />
        <div className="row m-1">
          <button
            className="btn btn-primary mr-lg-3"
            style={{ width: "100%" }}
            onClick={onUpdatePolicy}
          >
            Update New Policy
          </button>
        </div>
      </div>
    </>
  );
};

export default ChangePolicy;
