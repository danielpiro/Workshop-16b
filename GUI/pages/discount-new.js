import Menu from "../components/menu";
import { useEffect, useState } from "react";
import api from "../components/api";
import createNotification from "../components/norification";
import { useCookies } from "react-cookie";

const DiscountNew = () => {
  const [firstCombDis, setFirstCombDis] = useState("");
  const [secondCombDis, setSecondCombDis] = useState("");
  const [typeCombDis, setTypeCombDis] = useState("");
  const [showList, setShowList] = useState(false);
  const [listTypes, setListTypes] = useState([
    {
      type: "Discount type",
      op: "",
    },
  ]);
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);
  //let storeID = window.location.href.split("?").pop();
  let storeID = "";
  if (storeID.charAt(storeID.length - 1) === "#") {
    storeID = storeID.slice(0, -1);
  }
  const policyTypes = [
    "cart",
    "category",
    "productWithoutAmount",
    "productWithAmount",
    "userId",
    "userAge",
    "OnHoursOfTheDay",
    "OnDaysOfTheWeek",
    "OnDayOfMonth",
    "price",
    "AlwaysTrue",
  ];
  const types = ["Max", "Addition"];
  const daysOfWeek = [
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
  ];
  const daysOfMonth = [
    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
    22, 23, 24, 25, 26, 27, 28, 29, 30, 31,
  ];
  const categories = ["Other", "Appliances", "Apps$Games", "Handmade", "Baby"];

  useEffect(() => {
    const fetchData = async () => {
      //get all the existing policies in the store...
      let storeID = window.location.href.split("?").pop();
      if (storeID.charAt(storeID.length - 1) === "#") {
        storeID = storeID.slice(0, -1);
      }
      return await api
        .get(`Store/Polices?storeId=${storeID}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            data.value.map((pol) => {
              //fill policies list:
              policies.push(`ID=${pol.PolicyId}`);
            });
          } else {
            createNotification("error", data.reason)();
          }
        })
        .then(async () => {
          return await api
            .get(`/Discount/getAll?storeId=${storeID}&userId=${cookies.userId}`)
            .then((res) => {
              const { data } = res;
              if (data.success) {
                console.log(data);
                data.value.DiscountIds.slice(1, -1)
                  .split(",")
                  .map((disc) => {
                    discounts.push(disc);
                  });
              } else {
                createNotification("error", data.reason)();
              }
            });
        })
        .catch((err) => createNotification("error", `fail ${err}`)());
    };
    fetchData();
  }, []);

  const onClickCombined = (e) => {
    e.preventDefault();
    if (typeCombDis === "Max") {
      api
        .post(
          `/Discount/NewMaxDiscount/?storeId=${storeID}&userId=${cookies.userId}&discountId1=${firstCombDis}&discountId2=${secondCombDis}`
        )
        .then((res) => {
          const { data } = res;
          if (data.success) {
            fetchData();
          } else {
            createNotification("error", data.reason)();
          }
        })
        .catch((err) => console.log(err));
    } else {
      api
        .post(
          `/Discount/NewAdditionDiscount/?storeId=${storeID}&userId=${cookies.userId}&discountId1=${firstCombDis}&discountId2=${secondCombDis}`
        )
        .then((res) => {
          const { data } = res;
          if (data.success) {
            fetchData();
          } else {
            createNotification("error", data.reason)();
          }
        })
        .catch((err) => console.log(err));
    }
  };

  const onClickPrecentage = (e) => {
    e.preventDefault();
    setShowList(!showList);
  };

  const onAdd = (e) => {
    e.preventDefault();
    const obj = {
      type: "Dicount type",
      op: "Operator",
    };
    setListTypes((prev) => [...prev, obj]);
  };
  return (
    <>
      <Menu />
      <div className="container">
        <div className="text-center m-5">
          <h3>Discount</h3>
        </div>
        <div>
          <h4>Create discount</h4>
        </div>
        <div className="d-flex justify-content-center">
          <button className="btn btn-outline-primary" onClick={onAdd}>
            +
          </button>
        </div>
        <div className="dropdown mb-3">
          <button
            className="dropdown-toggle btn btn-primary"
            data-bs-toggle="dropdown"
            id="dropdownMenuButton"
            type="button"
          >
            Create
          </button>
          <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton">
            {policyTypes.map((type) => {
              return (
                <li key={type}>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={(e) => setFirstCombDis(type)}
                  >
                    {type}
                  </a>
                </li>
              );
            })}
          </ul>
        </div>
        <div className="dropdown mb-3">
          <button
            className="dropdown-toggle btn btn-primary"
            data-bs-toggle="dropdown"
            id="dropdownMenuButton"
            type="button"
          >
            Type
          </button>
          <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton">
            {types.map((type) => {
              return (
                <li key={type}>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={(e) => setTypeCombDis(type)}
                  >
                    {type}
                  </a>
                </li>
              );
            })}
          </ul>
        </div>
        <div className="dropdown mb-3">
          <button
            className="dropdown-toggle btn btn-primary"
            data-bs-toggle="dropdown"
            id="dropdownMenuButton"
            type="button"
          >
            Create
          </button>
          <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton">
            {policyTypes.map((type) => {
              return (
                <li key={type}>
                  <a
                    className="dropdown-item"
                    href="#"
                    onClick={(e) => setSecondCombDis(type)}
                  >
                    {type}
                  </a>
                </li>
              );
            })}
          </ul>
        </div>
        <div className="btn-group-vertical">
          <button className="btn btn-primary mb-3" onClick={onClickCombined}>
            Combined
          </button>
          <button className="btn btn-primary mb-3" onClick={onClickPrecentage}>
            Precentage
          </button>
          <button className="btn btn-primary mb-3">Cond</button>
        </div>
        {showList
          ? listTypes?.map((item) => {
              return (
                <>
                  <div className="dropdown mb-3">
                    <button
                      className="dropdown-toggle btn btn-primary"
                      data-bs-toggle="dropdown"
                      id="dropdownMenuButton"
                      type="button"
                    >
                      {item.type}
                    </button>
                    <ul
                      className="dropdown-menu"
                      aria-labelledby="dropdownMenuButton"
                    >
                      {policyTypes.map((type) => {
                        return (
                          <li key={type}>
                            <a
                              className="dropdown-item"
                              href="#"
                              onClick={(e) => setSecondCombDis(type)}
                            >
                              {type}
                            </a>
                          </li>
                        );
                      })}
                    </ul>
                  </div>
                  {item?.op !== "" ? (
                    <div className="dropdown mb-3">
                      <button
                        className="dropdown-toggle btn btn-primary"
                        data-bs-toggle="dropdown"
                        id="dropdownMenuButton"
                        type="button"
                      >
                        {item.type}
                      </button>
                      <ul
                        className="dropdown-menu"
                        aria-labelledby="dropdownMenuButton"
                      >
                        {policyTypes.map((type) => {
                          return (
                            <li key={type}>
                              <a
                                className="dropdown-item"
                                href="#"
                                onClick={(e) => setSecondCombDis(type)}
                              >
                                {type}
                              </a>
                            </li>
                          );
                        })}
                      </ul>
                    </div>
                  ) : null}
                </>
              );
            })
          : null}
      </div>
    </>
  );
};

export default DiscountNew;
