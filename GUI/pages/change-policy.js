import Menu from "../components/menu";
import { useEffect, useState } from "react";
import api from "../components/api";
import createNotification from "../components/norification";
import { useCookies } from "react-cookie";

const ChangePolicy = () => {
  const [policyType, setPolicyType] = useState("PolicyType");
  const [combinationType, setCombinationType] = useState("And/Or/Xor");
  const [preds, setPreds] = useState({
    pred1: "Choose predicate #1",
    pred2: "Choose predicate #2",
    predToDelete: "Choose existing predicate to delete",
  });
  const [predsFeatures1, setPredsFeature1] = useState({
    //allowORforbid: "Allow/Forbid",
    minPrice: "",
    category: "Category",
    minQunatity: "",
    productShouldBeInCart: "",
    productShouldBeInCartMinQunatity: "",
    dayOfMonth: "01",
    dayOfWeek: "Sunday",
    startHourOfDay: "00:00",
    endHourOfDay: "00:00",
    specificUser: "",
    minAge: "",
    maxAge: "",
  });
  const [predsFeatures2, setPredsFeature2] = useState({
    //allowORforbid: "Allow/Forbid",
    minPrice: "",
    category: "Category",
    minQunatity: "",
    productShouldBeInCart: "",
    productShouldBeInCartMinQunatity: "",
    dayOfMonth: "01",
    dayOfWeek: "Sunday",
    startHourOfDay: "00:00",
    endHourOfDay: "00:00",
    specificUser: "",
    minAge: "",
    maxAge: "",
  });
  const daysOfWeek = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
  const daysOfMonth = ["01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"];
  const categories = ["Other", "Appliances", "Apps$Games", "Handmade", "Baby"];
  const [policies, setPolicies] = useState(["Create new predicate"]);

  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  useEffect(() => {
    const fetchData = async () => {
      //get all the existing policies in the store...
      let storeID = window.location.href.split("?").pop();
      if(storeID.charAt(storeID.length-1) === '#'){
        storeID = storeID.slice(0, -1);
      }
      return await api
        .get(`Store/Polices?storeId=${storeID}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            //fill policies list:
            data.value.map((pol) => {
              policies.push(`ID=${pol.PolicyId}`);
              //policies.push(`ID=${pol.PolicyId} / Desc.=${pol.PolicyDescription.split('=')[1]}=${pol.PolicyDescription.split('=')[2].slice(0,-1)}`);
            })
          } else {
            createNotification("error", data.reason)();
          }
        })
        .catch((err) => createNotification("error", err)());
    };
    fetchData();
  }, []);

  const onUpdatePolicy = async (e) => { //for combined policy 
    e.preventDefault();
    console.log("creating combined policy");
    let storeID = window.location.href.split("?").pop();
    if(storeID.charAt(storeID.length-1) === '#'){
      storeID = storeID.slice(0, -1);
    }
    const polID1 = preds.pred1.split("/")[0].slice(3);
    const polID2 = preds.pred2.split("/")[0].slice(3);
    console.log(`/policy/combine?storeId=${storeID}&userId=${cookies.userId}&typeOfCombination=${combinationType}&policyID1=${polID1}&policyID2=${polID2}`)
    return await api
        .post(`/policy/combine?storeId=${storeID}&userId=${cookies.userId}&typeOfCombination=${combinationType}&policyID1=${polID1}&policyID2=${polID2}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            // Add each policy to policies...
            policies.push(`ID=${data.value} / Desc.= Combination of ${polID1} & ${polID2}`);
            createNotification("success", "Policy has been created successfully")();
          } else {
            createNotification("error", data.reason)();
          }
        })
  };

  const onCreatePredicate1 = async (e) => { //for policy #1 in page
    e.preventDefault();
    console.log("creating new predicate #1"); //Create one pred policy from pred1 values...
    let storeID = window.location.href.split("?").pop();
    if(storeID.charAt(storeID.length-1) === '#'){
      storeID = storeID.slice(0, -1);
    }
    const defaultYear = "2022";
    const defaultMonth = "01";   
    const startLocalDateTime = `${defaultYear}-${defaultMonth}-${predsFeatures1.dayOfMonth}T${predsFeatures1.startHourOfDay}`;
    const endLocalDateTime = `${defaultYear}-${defaultMonth}-${predsFeatures1.dayOfMonth}T${predsFeatures1.endHourOfDay}`;

    return await api
        .post(`/policy/add?storeId=${storeID}&userId=${cookies.userId}&typeOfPolicy=${policyType}&numOfProducts=${predsFeatures1.minQunatity}&categories=${predsFeatures1.category}&products=${predsFeatures1.productShouldBeInCart}&productsAmount=${predsFeatures1.minQunatity}&userIds=${predsFeatures1.specificUser}&startAge=${predsFeatures1.minAge}&endAge=${predsFeatures1.maxAge}&startTime=${startLocalDateTime}&endTime=${endLocalDateTime}&price=${predsFeatures1.minPrice}`)
        //.post(`/policy/add?storeId=${storeID}&userId=${cookies.userId}&typeOfCombination=${combinationType}` , mockPolicy)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            policies.push(data.value); // Add new policy to policies...
            createNotification("success", "Policy has been created successfully")();
          } else {
            createNotification("error", data.reason)();
          }
        })
  };

  const onCreatePredicate2 = async (e) => { //for policy #1 in page
    e.preventDefault();
    console.log("creating new predicate #2"); //Create one pred policy from pred1 values...
    let storeID = window.location.href.split("?").pop();
    if(storeID.charAt(storeID.length-1) === '#'){
      storeID = storeID.slice(0, -1);
    }
    const defaultYear = "2022";
    const defaultMonth = "01";
    const startLocalDateTime = `${defaultYear}-${defaultMonth}-${predsFeatures2.dayOfMonth}T${predsFeatures2.startHourOfDay}`;
    const endLocalDateTime = `${defaultYear}-${defaultMonth}-${predsFeatures2.dayOfMonth}T${predsFeatures2.endHourOfDay}`;

    return await api
        .post(`/policy/add?storeId=${storeID}&userId=${cookies.userId}&typeOfPolicy=${policyType}&numOfProducts=${predsFeatures2.minQunatity}&categories=${predsFeatures2.category}&products=${predsFeatures2.productShouldBeInCart}&productsAmount=${predsFeatures2.minQunatity}&userIds=${predsFeatures2.specificUser}&startAge=${predsFeatures2.minAge}&endAge=${predsFeatures2.maxAge}&startTime=${startLocalDateTime}&endTime=${endLocalDateTime}&price=${predsFeatures2.minPrice}`)
        //.post(`/policy/add?storeId=${storeID}&userId=${cookies.userId}&typeOfCombination=${combinationType}` , mockPolicy)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            // Add new policy to policies...
            policies.push(data.value);
            createNotification("success", "Policy has been created successfully")();
          } else {
            createNotification("error", data.reason)();
          }
        })
  };

  const onDeletePolicy = async (e) => {
    e.preventDefault();
    console.log("Deleting a policy");
    let storeID = window.location.href.split("?").pop();
    if(storeID.charAt(storeID.length-1) === '#'){
      storeID = storeID.slice(0, -1);
    }
    console.log(`/policy/delete?storeId=${storeID}&userId=${cookies.userId}&policyId=${preds.predToDelete}`);

    return await api
        .post(`/policy/delete?storeId=${storeID}&userId=${cookies.userId}&policyId=${preds.predToDelete}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            policies = policies.filter((pol) => pol !== preds.predToDelete)
            createNotification("success", "Policy has been deleted successfully")();
          } else {
            createNotification("error", data.reason)();
          }
        })
        .catch((err) => createNotification("error", err)());
  }

  return (
    <>
      <Menu />
      <div className="text-center my-5">
        <h3>Change/Create Policy</h3>
      </div>

      <div className="container">
        <div className="row">
          <div className="col">
            <h4><u>Select policy type</u></h4>
            
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
                  <a className="dropdown-item" onClick={() => setPolicyType("PricePredicate")}>
                    Price Policy
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("CategoryPolicy")}>
                    Category Policy
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("CartPolicy")}>
                    Cart Policy
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("ProductWithoutAmountPolicy")}>
                    Product Without Amount Policy
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("ProductWithAmountPolicy")}>
                    Product With Amount Policy
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("OnDayOfMonthPolicy")}>
                    On Day Of Month Policy
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("OnDaysOfTheWeekPolicy")}>
                    On Days Of The Week Policy
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("OnHoursOfTheDayPolicy")}>
                    On Hours Of The Day Policy
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("UserIdPolicy")}>
                    UserId Policy
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" onClick={() => setPolicyType("UseAgePolicy")}>
                    Age Policy
                  </a>
                </li>
              </ul>
            </div>
          </div>
          <div className="col">
            <h4>
              <u>Create policy (Combined)</u>
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
                  display: policyType == "PricePredicate" ? "block" : "none",
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
              </div>
              <div
                style={{
                  display: policyType == "CategoryPolicy" ? "block" : "none",
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
                    {predsFeatures1.category}
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
                              setPredsFeature1((prevState) => ({
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
              <div
                style={{
                  display: policyType == "CartPolicy" ? "block" : "none",
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
              </div>
              <div
                style={{
                  display:
                    policyType == "ProductWithoutAmountPolicy"
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
                    "ProductWithAmountPolicy"
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
                  display: policyType == "OnDayOfMonthPolicy" ? "block" : "none",
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
                  display: policyType == "OnDaysOfTheWeekPolicy" ? "block" : "none",
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
                  display: policyType == "OnHoursOfTheDayPolicy" ? "block" : "none",
                }}
              >
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
                  display: policyType == "UserIdPolicy" ? "block" : "none",
                }}
              >
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
              <div style={{ display: policyType == "UseAgePolicy" ? "block" : "none" }}>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    MinAge
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter min age"
                    aria-label="specificUser"
                    aria-describedby="basic-addon1"
                    value={predsFeatures1.minAge}
                    onChange={(e) =>
                      setPredsFeature1((prevState) => ({
                        ...prevState,
                        minAge: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    MaxAge
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter max age"
                    aria-label="specificUser"
                    aria-describedby="basic-addon1"
                    value={predsFeatures1.maxAge}
                    onChange={(e) =>
                      setPredsFeature1((prevState) => ({
                        ...prevState,
                        maxAge: e.target.value,
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
                Create Predicate #1
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
                  display: policyType == "PricePredicate" ? "block" : "none",
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
              </div>
              <div
                style={{
                  display: policyType == "CategoryPolicy" ? "block" : "none",
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
                    {predsFeatures2.category}
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
                              setPredsFeature2((prevState) => ({
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
              <div
                style={{
                  display: policyType == "CartPolicy" ? "block" : "none",
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
              </div>
              <div
                style={{
                  display:
                    policyType == "ProductWithoutAmountPolicy"
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
                    "ProductWithAmountPolicy"
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
                  display: policyType == "OnDayOfMonthPolicy" ? "block" : "none",
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
                  display: policyType == "OnDaysOfTheWeekPolicy" ? "block" : "none",
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
                  display: policyType == "OnHoursOfTheDayPolicy" ? "block" : "none",
                }}
              >
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
                  display: policyType == "UserIdPolicy" ? "block" : "none",
                }}
              >
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
              <div style={{ display: policyType == "UseAgePolicy" ? "block" : "none" }}>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    MinAge
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter min age"
                    aria-label="specificUser"
                    aria-describedby="basic-addon1"
                    value={predsFeatures2.minAge}
                    onChange={(e) =>
                      setPredsFeature2((prevState) => ({
                        ...prevState,
                        minAge: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">
                    MaxAge
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter max age"
                    aria-label="specificUser"
                    aria-describedby="basic-addon1"
                    value={predsFeatures2.maxAge}
                    onChange={(e) =>
                      setPredsFeature2((prevState) => ({
                        ...prevState,
                        maxAge: e.target.value,
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

        <div className="row m-1">
          <div className="text-center my-5">
            <h3>Delete Policy</h3>
          </div>
        
          <div className="dropdown m-1 text-center">
            <button
              className="btn btn-secondary dropdown-toggle"
              type="button"
              id="dropdownMenuButton1"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              {preds.predToDelete}
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
                          predToDelete: policy,
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
          <button
            className="btn btn-primary mr-lg-3"
            style={{ width: "100%" }}
            onClick={onDeletePolicy}
          >
            Delete a Policy
          </button>
        </div>
      </div>
    </>
  );
};

export default ChangePolicy;
