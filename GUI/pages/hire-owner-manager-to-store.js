import api from "../components/api";
import Menu from "../components/menu";
import { useState, useEffect } from "react";
import createNotification from "../components/norification";
import { useRouter } from "next/router";
import { useCookies } from "react-cookie";

const HireOwnerToStore = () => {
  const router = useRouter();
  const [newOfficialInput, setNewOfficialInput] = useState({
    username: "",
    ownerORmanager: "Manager/Owner",
  });

  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  const [permission, setPermissions] = useState({
    viewStoreHistory: false,
    editExistingProduct: false,
    addNewProduct: false,
    editProduct: false,
    removeProduct: false,
    editStorePolicy: false,
    editStoreDiscount: false,
    addReviewToProduct: false,
    viewForum: false,
    ReplyToForum: false,
    closeStore: false,
    openStore: false,
    infoOfManagers: false,
  });

  const checkAndSetPermissions = (perm) => {
    switch (perm) {
      case "VIEW_STORE_HISTORY":
        setPermissions((prevState) => ({...prevState, viewStoreHistory: true,}));
        break;
      case "EDIT_EXISTING_PRODUCT":
        setPermissions((prevState) => ({...prevState, editExistingProduct: true,}));
        break;
      case "ADD_NEW_PRODUCT":
        setPermissions((prevState) => ({...prevState, addNewProduct: true,}));
        break;
      case "EDIT_PRODUCT":
        setPermissions((prevState) => ({...prevState, editProduct: true,}));
        break;
      case "REMOVE_PRODUCT":
        setPermissions((prevState) => ({...prevState, removeProduct: true,}));
        break;
      case "EDIT_STORE_POLICY":
        setPermissions((prevState) => ({...prevState, editStorePolicy: true,}));
        break;
      case "EDIT_STORE_DISCOUNT":
        setPermissions((prevState) => ({...prevState, editStoreDiscount: true,}));
        break;
      case "ADD_REVIEW_TO_PRODUCT":
        setPermissions((prevState) => ({...prevState, addReviewToProduct: true,}));
        break;
      case "VIEW_FORUM":
        setPermissions((prevState) => ({...prevState, viewForum: true,}));
        break;
      case "REPLY_TO_FORUM":
        setPermissions((prevState) => ({...prevState, ReplyToForum: true,}));
        break;
      case "CLOSE_STORE":
        setPermissions((prevState) => ({...prevState, closeStore: true,}));
        break;
      case "OPEN_STORE":
        setPermissions((prevState) => ({...prevState, openStore: true,}));
        break;
      case "INFO_OF_MANAGERS":
        setPermissions((prevState) => ({...prevState, infoOfManagers: true,}));
        break;
      default:
        createNotification("error","Error loading permissions")();
    }
  }

  useEffect(() => {
    let storeID = window.location.href.split("?").pop();
    if(storeID.charAt(storeID.length-1) === '#'){
      storeID = storeID.slice(0, -1);
    }
    const fetchData = async () => {
      return await api
        .get(`store/owner/permmitions?user=${cookies.userId}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            //console.log("before", data.value, permission);
            data.value.map((store) => {
              if (store.storeId === storeID) {
                let perArray = store.permission.slice(1, -1).split(",");
                perArray.map((per) => {
                  let edittedPer = per.trim();
                  checkAndSetPermissions(edittedPer)
                  //console.log("after", data.value, permission);
                });
              }
            });
          } else {
            createNotification("error", data.reason)();
          }
        })
        .then(async () => {
          //Added by Amit
          return await api
            .get(`store/manager/permmitions?user=${cookies.userId}`)
            .then((res) => {
              const { data } = res;
              if (data.success) {
                data.value.map((store) => {
                  if (store.storeId === storeID) {
                    let perArray = store.permission.slice(1, -1).split(",");
                    perArray.map((per) => {
                      let edittedPer = per.trim();
                      checkAndSetPermissions(edittedPer)
                    });
                  }
                });
              } else {
                createNotification("error", data.reason)();
              }
            });
        })
        // Testing checked/disabled...
        // .then(async () => {
        //   setPermissions((prevState) => ({...prevState, openStore: false,}));
        //   setPermissions((prevState) => ({...prevState, infoOfManagers: false,}));
        // })
        .catch((err) => console.log(err));
    };
    fetchData();
  }, []);

  const setManager = () => {
    setNewOfficialInput((prevState) => ({
      ...prevState,
      ownerORmanager: "Manager",
    }));
  };

  const setOwner = () => {
    setNewOfficialInput((prevState) => ({
      ...prevState,
      ownerORmanager: "Owner",
    }));
  };

  const onHiringOfficial = async (e) => {
    e.preventDefault();
    if (newOfficialInput.username != "" && newOfficialInput.ownerORmanager != "") {
      let storeID = window.location.href.split("?").pop();
      if(storeID.charAt(storeID.length-1) === '#'){
        storeID = storeID.slice(0, -1);
      }
      let newOwnerPermissions = [];
      if (newOfficialInput.ownerORmanager == "Owner") {
        if (permission.viewStoreHistory) newOwnerPermissions.push("VIEW_STORE_HISTORY");
        if (permission.editExistingProduct) newOwnerPermissions.push("EDIT_EXISTING_PRODUCT");
        if (permission.addNewProduct) newOwnerPermissions.push("ADD_NEW_PRODUCT");
        if (permission.editProduct) newOwnerPermissions.push("EDIT_PRODUCT");
        if (permission.removeProduct) newOwnerPermissions.push("REMOVE_PRODUCT");
        if (permission.editStorePolicy) newOwnerPermissions.push("EDIT_STORE_POLICY");
        if (permission.editStoreDiscount) newOwnerPermissions.push("EDIT_STORE_DISCOUNT");
        if (permission.addReviewToProduct) newOwnerPermissions.push("ADD_REVIEW_TO_PRODUCT");
        if (permission.viewForum) newOwnerPermissions.push("VIEW_FORUM");
        if (permission.ReplyToForum) newOwnerPermissions.push("REPLY_TO_FORUM");
        if (permission.closeStore) newOwnerPermissions.push("CLOSE_STORE");
        if (permission.openStore) newOwnerPermissions.push("OPEN_STORE");
        if (permission.infoOfManagers) newOwnerPermissions.push("INFO_OF_MANAGERS");
        const newOwner = {
          storeId: storeID,
          userIdGiving: cookies.username,
          userGettingPermission: newOfficialInput.username,
          permissions: newOwnerPermissions,
        };
        //console.log(newOwner)
        await api
          .post(`/owner/`, newOwner)
          .then((res) => {
            const { data } = res;
            console.log(data);
            if (data.success) {
              createNotification("success", "Hired owner successfully", () =>
                router.push("/dashboard")
              )();
            } else {
              console.log("error is here");
              createNotification("error", data.reason)(); //failed to hire new owner
            }
          })
          .catch((err) => console.log(err, "failed to hire new owner"));
      } else if (newOfficialInput.ownerORmanager == "Manager") {
        await api
          .post(
            `/manager/?storeId=${storeID}&userIdGiving=${cookies.username}&UserGettingPermissionId=${newOfficialInput.username}`
          )
          .then((res) => {
            const { data } = res;
            if (data.success) {
              //console.log(data);
              createNotification("success", "Hired manager successfully", () =>
                router.push("/dashboard")
              )();
            } else {
              createNotification("error", data.reason)(); //"failure hiring manager!"
            }
          })
          .catch((err) => console.log("failure hiring manager!", err));
      }
    } else {
      createNotification(
        "error",
        "at least one of the inputs was not valid, please try again"
      )();
    }
  };

  return (
    <>
      <Menu />

      <div className="card-header">
        <h3>Hire new owner/manager to the store</h3>
      </div>

      <div className="container">
        <div className="row">
          <div className="col">
            <input
              className="form-control mr-sm-2 m-1"
              type="search"
              placeholder="Enter username of the new owner/manager"
              aria-label="Search"
              value={newOfficialInput.username}
              onChange={(e) =>
                setNewOfficialInput((prevState) => ({
                  ...prevState,
                  username: e.target.value,
                }))
              }
            />
            <div className="dropdown m-1">
              <button
                className="btn btn-secondary dropdown-toggle"
                type="button"
                id="dropdownMenuButton1"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                {newOfficialInput.ownerORmanager}
              </button>
              <ul
                className="dropdown-menu"
                aria-labelledby="dropdownMenuButton1"
              >
                <li>
                  <a className="dropdown-item" href="#" onClick={setManager}>
                    Manager
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" href="#" onClick={setOwner}>
                    Owner
                  </a>
                </li>
              </ul>
            </div>
            <br />
            <div className="row m-1" style={{ display: "flex", width: "30%" }}>
              <button
                className="btn btn-primary mr-lg-3"
                onClick={onHiringOfficial}
              >
                Hire new Manager/Owner
              </button>
            </div>
          </div>

          <div className="col m-2" hidden={newOfficialInput.ownerORmanager !== "Owner"}>
            <h4>
              <u>Permission for new owner</u>
            </h4>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="VIEW_STORE_HISTORY"
                disabled={!permission.viewStoreHistory}
                checked={permission.viewStoreHistory}
              />
              <label
                className="form-check-label"
                htmlFor="flexSwitchCheckDefault"
              >
                View store history
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="EDIT_EXISTING_PRODUCT"
                disabled={!permission.editExistingProduct}
                checked={permission.editExistingProduct}
              />
              <label
                className="form-check-label"
                htmlFor="flexSwitchCheckChecked"
              >
                Edit existing product
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="ADD_NEW_PRODUCT"
                disabled={!permission.addNewProduct}
                checked={permission.addNewProduct}
              />
              <label
                className="form-check-label"
                htmlFor="flexSwitchCheckDefault"
              >
                Add new product
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="EDIT_PRODUCT"
                disabled={!permission.editProduct}
                checked={permission.editProduct}
              />
              <label
                className="form-check-label"
                htmlFor="flexSwitchCheckChecked"
              >
                Edit product
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="REMOVE_PRODUCT"
                disabled={!permission.removeProduct}
                checked={permission.removeProduct}
              />
              <label
                className="form-check-label"
                htmlFor="flexSwitchCheckDefault"
              >
                Remove product
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="EDIT_STORE_POLICY"
                disabled={!permission.editStorePolicy}
                checked={permission.editStorePolicy}
              />
              <label
                className="form-check-label"
                htmlFor="flexSwitchCheckChecked"
              >
                Edit store policy
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="EDIT_STORE_DISCOUNT"
                disabled={!permission.editStoreDiscount}
                checked={permission.editStoreDiscount}
              />
              <label
                className="form-check-label"
                htmlFor="flexSwitchCheckChecked"
              >
                Edit store discount
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="ADD_REVIEW_TO_PRODUCT"
                disabled={!permission.addReviewToProduct}
                checked={permission.addReviewToProduct}
              />
              <label
                className="form-check-label"
                htmlFor="flexSwitchCheckDefault"
              >
                Add review to product
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="VIEW_FORUM"
                disabled={!permission.viewForum}
                checked={permission.viewForum}
              />
              <label
                className="form-check-label"
                htmlFor="flexSwitchCheckChecked"
              >
                View forum
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="REPLY_TO_FORUM"
                disabled={!permission.ReplyToForum}
                checked={permission.ReplyToForum}
              />
              <label
                className="form-check-label"
                htmlFor="flexSwitchCheckDefault"
              >
                Reply to forum
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="CLOSE_STORE"
                disabled={!permission.closeStore}
                checked={permission.closeStore}
              />
              <label
                className="form-check-label"
                htmlFor="flexSwitchCheckChecked"
              >
                Close store
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="OPEN_STORE"
                disabled={!permission.openStore}
                checked={permission.openStore}
              />
              <label
                className="form-check-label"
                htmlFor="flexSwitchCheckChecked"
              >
                Open store
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="INFO_OF_MANAGERS"
                disabled={!permission.infoOfManagers}
                checked={permission.infoOfManagers}
              />
              <label
                className="form-check-label"
                htmlFor="flexSwitchCheckChecked"
              >
                Info of managers
              </label>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default HireOwnerToStore;
