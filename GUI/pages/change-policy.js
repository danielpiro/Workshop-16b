import Menu from "../components/menu";
import { useState } from "react";

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

  //TODO: Make sure we got storeID in advance...
  //TDOO: Check that al the permissions in the system are written

  // useEffect(() => {
  //   const fetchApi = async () => {
  //     const response = await axios.get("https://fakestoreapi.com/products");
  //     setIsLoading(!isLoading);
  //     setProducts(response.data);
  //     //TODO: Add logic to check if the user has any permission!
  //     //setUserPermission("Admin/StoreOwner/StoreManager");
  //   };
  //   fetchApi();
  // }, []);

  // useEffect(() => {
  //     const fetchPermission = async () => {
  //       const response = await axios.get("users/getUserPermission");
  //       setUserPermission(response.data);
  //     };
  //     fetchPermission();
  // }, []);

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
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() => setPolicyType("PolicyType")}
                  ></a>
                </li>
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() => setPolicyType("Total cart price")}
                  >
                    Total cart price
                  </a>
                </li>
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() => setPolicyType("Quantity in cart")}
                  >
                    Quantity in cart
                  </a>
                </li>
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() => setPolicyType("Product should be in cart")}
                  >
                    Product should be in cart
                  </a>
                </li>
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() =>
                      setPolicyType(
                        "Product should be in cart with minimum quantity"
                      )
                    }
                  >
                    Product should be in cart with minimum quantity
                  </a>
                </li>
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() => setPolicyType("Day Of Month")}
                  >
                    Day Of Month
                  </a>
                </li>
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() => setPolicyType("Day Of Week")}
                  >
                    Day Of Week
                  </a>
                </li>
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() => setPolicyType("Hour Of Day")}
                  >
                    Hour Of Day
                  </a>
                </li>
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() => setPolicyType("Specific user")}
                  >
                    Specific user
                  </a>
                </li>
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() => setPolicyType("Age")}
                  >
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
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() =>
                      setPreds((prevState) => ({
                        ...prevState,
                        pred1: "",
                      }))
                    }
                  >
                    {/* Empty choice */}
                  </a>
                </li>
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() =>
                      setPreds((prevState) => ({
                        ...prevState,
                        pred1: "Create new predicate",
                      }))
                    }
                  >
                    Create new predicate
                  </a>
                </li>
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() =>
                      setPreds((prevState) => ({
                        ...prevState,
                        pred1: "Choose Existing predicate",
                      }))
                    }
                  >
                    Choose Existing predicate
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
                {combinationType}
              </button>
              <ul
                className="dropdown-menu"
                aria-labelledby="dropdownMenuButton1"
              >
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
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
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() => setCombinationType("And")}
                  >
                    And
                  </a>
                </li>
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() => setCombinationType("Or")}
                  >
                    Or
                  </a>
                </li>
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() => setCombinationType("Xor")}
                  >
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
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() =>
                      setPreds((prevState) => ({
                        ...prevState,
                        pred2: "",
                      }))
                    }
                  >
                    {/* Empty choice */}
                  </a>
                </li>
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() =>
                      setPreds((prevState) => ({
                        ...prevState,
                        pred2: "Create new predicate",
                      }))
                    }
                  >
                    Create new predicate
                  </a>
                </li>
                <li>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={() =>
                      setPreds((prevState) => ({
                        ...prevState,
                        pred2: "Choose Existing predicate",
                      }))
                    }
                  >
                    Choose Existing predicate
                  </a>
                </li>
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
                <u>Create predicate #1</u>
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
                            allowORforbid: "Allow/Forbid",
                          }))
                        }
                      >
                        {/* Allow/Forbid */}
                      </a>
                    </li>
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
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "Day Of Month",
                          }))
                        }
                      >
                        {/* Day Of Month */}
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "1",
                          }))
                        }
                      >
                        1
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "2",
                          }))
                        }
                      >
                        2
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "3",
                          }))
                        }
                      >
                        3
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "4",
                          }))
                        }
                      >
                        4
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "5",
                          }))
                        }
                      >
                        5
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "6",
                          }))
                        }
                      >
                        6
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "7",
                          }))
                        }
                      >
                        7
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "8",
                          }))
                        }
                      >
                        8
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "9",
                          }))
                        }
                      >
                        9
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "10",
                          }))
                        }
                      >
                        10
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "11",
                          }))
                        }
                      >
                        11
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "12",
                          }))
                        }
                      >
                        12
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "13",
                          }))
                        }
                      >
                        13
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "14",
                          }))
                        }
                      >
                        14
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "15",
                          }))
                        }
                      >
                        15
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "16",
                          }))
                        }
                      >
                        16
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "17",
                          }))
                        }
                      >
                        17
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "18",
                          }))
                        }
                      >
                        18
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "19",
                          }))
                        }
                      >
                        19
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "20",
                          }))
                        }
                      >
                        20
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "21",
                          }))
                        }
                      >
                        21
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "22",
                          }))
                        }
                      >
                        22
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "23",
                          }))
                        }
                      >
                        23
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "24",
                          }))
                        }
                      >
                        24
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "25",
                          }))
                        }
                      >
                        25
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "26",
                          }))
                        }
                      >
                        26
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "27",
                          }))
                        }
                      >
                        27
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "28",
                          }))
                        }
                      >
                        28
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "29",
                          }))
                        }
                      >
                        29
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "30",
                          }))
                        }
                      >
                        30
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfMonth: "31",
                          }))
                        }
                      >
                        31
                      </a>
                    </li>
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
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            allowORforbid: "Allow/Forbid",
                          }))
                        }
                      >
                        {/* Allow/Forbid */}
                      </a>
                    </li>
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
                    {predsFeatures1.dayOfWeek}
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
                            dayOfWeek: "Day Of Week",
                          }))
                        }
                      >
                        {/* Day Of Week */}
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfWeek: "Sunday",
                          }))
                        }
                      >
                        Sunday
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfWeek: "Monday",
                          }))
                        }
                      >
                        Monday
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfWeek: "Tuesday",
                          }))
                        }
                      >
                        Tuesday
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfWeek: "Wednesday",
                          }))
                        }
                      >
                        Wednesday
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfWeek: "Thursday",
                          }))
                        }
                      >
                        Thursday
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfWeek: "Friday",
                          }))
                        }
                      >
                        Friday
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature1((prevState) => ({
                            ...prevState,
                            dayOfWeek: "Saturday",
                          }))
                        }
                      >
                        Saturday
                      </a>
                    </li>
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
                            allowORforbid: "Allow/Forbid",
                          }))
                        }
                      >
                        {/* Allow/Forbid */}
                      </a>
                    </li>
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
                            allowORforbid: "Allow/Forbid",
                          }))
                        }
                      >
                        {/* Allow/Forbid */}
                      </a>
                    </li>
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
                            allowORforbid: "Allow/Forbid",
                          }))
                        }
                      >
                        {/* Allow/Forbid */}
                      </a>
                    </li>
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
                            allowORforbid: "Allow/Forbid",
                          }))
                        }
                      >
                        {/* Allow/Forbid */}
                      </a>
                    </li>
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
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "Day Of Month",
                          }))
                        }
                      >
                        {/* Day Of Month */}
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "1",
                          }))
                        }
                      >
                        1
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "2",
                          }))
                        }
                      >
                        2
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "3",
                          }))
                        }
                      >
                        3
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "4",
                          }))
                        }
                      >
                        4
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "5",
                          }))
                        }
                      >
                        5
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "6",
                          }))
                        }
                      >
                        6
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "7",
                          }))
                        }
                      >
                        7
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "8",
                          }))
                        }
                      >
                        8
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "9",
                          }))
                        }
                      >
                        9
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "10",
                          }))
                        }
                      >
                        10
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "11",
                          }))
                        }
                      >
                        11
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "12",
                          }))
                        }
                      >
                        12
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "13",
                          }))
                        }
                      >
                        13
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "14",
                          }))
                        }
                      >
                        14
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "15",
                          }))
                        }
                      >
                        15
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "16",
                          }))
                        }
                      >
                        16
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "17",
                          }))
                        }
                      >
                        17
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "18",
                          }))
                        }
                      >
                        18
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "19",
                          }))
                        }
                      >
                        19
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "20",
                          }))
                        }
                      >
                        20
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "21",
                          }))
                        }
                      >
                        21
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "22",
                          }))
                        }
                      >
                        22
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "23",
                          }))
                        }
                      >
                        23
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "24",
                          }))
                        }
                      >
                        24
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "25",
                          }))
                        }
                      >
                        25
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "26",
                          }))
                        }
                      >
                        26
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "27",
                          }))
                        }
                      >
                        27
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "28",
                          }))
                        }
                      >
                        28
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "29",
                          }))
                        }
                      >
                        29
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "30",
                          }))
                        }
                      >
                        30
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfMonth: "31",
                          }))
                        }
                      >
                        31
                      </a>
                    </li>
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
                            allowORforbid: "Allow/Forbid",
                          }))
                        }
                      >
                        {/* Allow/Forbid */}
                      </a>
                    </li>
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
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfWeek: "Day Of Week",
                          }))
                        }
                      >
                        {/* Day Of Week */}
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfWeek: "Sunday",
                          }))
                        }
                      >
                        Sunday
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfWeek: "Monday",
                          }))
                        }
                      >
                        Monday
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfWeek: "Tuesday",
                          }))
                        }
                      >
                        Tuesday
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfWeek: "Wednesday",
                          }))
                        }
                      >
                        Wednesday
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfWeek: "Thursday",
                          }))
                        }
                      >
                        Thursday
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfWeek: "Friday",
                          }))
                        }
                      >
                        Friday
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        href="#"
                        onClick={(e) =>
                          setPredsFeature2((prevState) => ({
                            ...prevState,
                            dayOfWeek: "Saturday",
                          }))
                        }
                      >
                        Saturday
                      </a>
                    </li>
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
                            allowORforbid: "Allow/Forbid",
                          }))
                        }
                      >
                        {/* Allow/Forbid */}
                      </a>
                    </li>
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
                            allowORforbid: "Allow/Forbid",
                          }))
                        }
                      >
                        {/* Allow/Forbid */}
                      </a>
                    </li>
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
                            allowORforbid: "Allow/Forbid",
                          }))
                        }
                      >
                        {/* Allow/Forbid */}
                      </a>
                    </li>
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
                Create Predicate 2
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
            Update Policy
          </button>
        </div>
      </div>
    </>
  );
};

export default ChangePolicy;
