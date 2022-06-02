import api from "../components/api";
import Menu from "../components/menu";
import axios from "axios";
import { useState, useEffect } from "react";
import createNotification from "../components/norification";
import { useRouter } from "next/router";
import { useCookies } from "react-cookie";

const HireOwnerToStore = () => {
  const router = useRouter();
  //const [userPermission, setUserPermission] = useState("Admin");
  const [newOfficialInput, setNewOfficialInput] = useState({
    username: "",
    //storename: "",
    ownerORmanager: "Manager/Owner",
  });

  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  const [permission, setPermissions] = useState({
    viewStoreHistory: false, editExistingProduct: false, addNewProduct: false, editProduct: false, 
    removeProduct: false, editStorePolicy: false, editStoreDiscount: false, addReviewToProduct: false,
    viewForum: false, ReplyToForum: false, closeStore: false, openStore: false, infoOfManagers: false,
  });

  useEffect(() => {
    const storeID = window.location.href.split("?").pop().slice(0, -1);
    const fetchData = async () => { 
      return await api.get(`store/owner/permmitions?user=${cookies.userId}`).then((res) => {
        const { data } = res;
        if (data.success) {
          data.value.map(store => {
            if(store.storeId === storeID){
              //console.log(store.permission)
              let perArray = store.permission.slice(1, -1).split(","); //continue here
              perArray.map(per => {
                let edittedPer = per.trim();
                //console.log(edittedPer);
                switch(edittedPer){
                  case 'VIEW_STORE_HISTORY':
                    checkPermission(edittedPer);
                    setPermissions((prevState) => ({...prevState, viewStoreHistory: true,}));
                    break;
                  case 'EDIT_EXISTING_PRODUCT':
                    checkPermission(edittedPer);
                    setPermissions((prevState) => ({...prevState, editExistingProduct: true,}));
                    break;
                  case 'ADD_NEW_PRODUCT':
                    checkPermission(edittedPer);
                    setPermissions((prevState) => ({...prevState, addNewProduct: true,}));
                    break;
                  case 'EDIT_PRODUCT':
                    checkPermission(edittedPer);
                    setPermissions((prevState) => ({...prevState, editProduct: true,}));
                    break;
                  case 'REMOVE_PRODUCT':
                    checkPermission(edittedPer);
                    setPermissions((prevState) => ({...prevState, removeProduct: true,}));
                    break;
                  case 'EDIT_STORE_POLICY':
                    checkPermission(edittedPer);
                    setPermissions((prevState) => ({...prevState, editStorePolicy: true,}));
                    break;
                  case 'EDIT_STORE_DISCOUNT':
                    checkPermission(edittedPer);
                    setPermissions((prevState) => ({...prevState, editStoreDiscount: true,}));
                    break;
                  case 'ADD_REVIEW_TO_PRODUCT':
                    checkPermission(edittedPer);
                    setPermissions((prevState) => ({...prevState, addReviewToProduct: true,}));
                    break;
                  case 'VIEW_FORUM':
                    checkPermission(edittedPer);
                    setPermissions((prevState) => ({...prevState, viewForum: true,}));
                    break;
                  case 'REPLY_TO_FORUM':
                    checkPermission(edittedPer);
                    setPermissions((prevState) => ({...prevState, ReplyToForum: true,}));
                    break;
                  case 'CLOSE_STORE':
                    checkPermission(edittedPer);
                    setPermissions((prevState) => ({...prevState, closeStore: true,}));
                    break;
                  case 'OPEN_STORE':
                    checkPermission(edittedPer);
                    setPermissions((prevState) => ({...prevState, openStore: true,}));
                    break;
                  case 'INFO_OF_MANAGERS':
                    checkPermission(edittedPer);
                    setPermissions((prevState) => ({...prevState, infoOfManagers: true,}));
                    break;
                  default:
                    console.log("Error loading permissions");
                }
              })
            }          
          });
        } else {
          console.log(data.reason);
        }
      })
      .then(async () => { //Added by Amit
        return await api.get(`store/manager/permmitions?user=${cookies.userId}`).then((res) => {
          const { data } = res;
          if (data.success) {
            data.value.map(store => {
              if(store.storeId === storeID){
                //console.log(store.permission)
                let perArray = store.permission.slice(1, -1).split(","); //continue here
                perArray.map(per => {
                  let edittedPer = per.trim();
                  //console.log(edittedPer);
                  switch(edittedPer){
                    case 'VIEW_STORE_HISTORY':
                      checkPermission(edittedPer);
                      setPermissions((prevState) => ({...prevState, viewStoreHistory: true,}));
                      break;
                    case 'EDIT_EXISTING_PRODUCT':
                      checkPermission(edittedPer);
                      setPermissions((prevState) => ({...prevState, editExistingProduct: true,}));
                      break;
                    case 'ADD_NEW_PRODUCT':
                      checkPermission(edittedPer);
                      setPermissions((prevState) => ({...prevState, addNewProduct: true,}));
                      break;
                    case 'EDIT_PRODUCT':
                      checkPermission(edittedPer);
                      setPermissions((prevState) => ({...prevState, editProduct: true,}));
                      break;
                    case 'REMOVE_PRODUCT':
                      checkPermission(edittedPer);
                      setPermissions((prevState) => ({...prevState, removeProduct: true,}));
                      break;
                    case 'EDIT_STORE_POLICY':
                      checkPermission(edittedPer);
                      setPermissions((prevState) => ({...prevState, editStorePolicy: true,}));
                      break;
                    case 'EDIT_STORE_DISCOUNT':
                      checkPermission(edittedPer);
                      setPermissions((prevState) => ({...prevState, editStoreDiscount: true,}));
                      break;
                    case 'ADD_REVIEW_TO_PRODUCT':
                      checkPermission(edittedPer);
                      setPermissions((prevState) => ({...prevState, addReviewToProduct: true,}));
                      break;
                    case 'VIEW_FORUM':
                      checkPermission(edittedPer);
                      setPermissions((prevState) => ({...prevState, viewForum: true,}));
                      break;
                    case 'REPLY_TO_FORUM':
                      checkPermission(edittedPer);
                      setPermissions((prevState) => ({...prevState, ReplyToForum: true,}));
                      break;
                    case 'CLOSE_STORE':
                      checkPermission(edittedPer);
                      setPermissions((prevState) => ({...prevState, closeStore: true,}));
                      break;
                    case 'OPEN_STORE':
                      checkPermission(edittedPer);
                      setPermissions((prevState) => ({...prevState, openStore: true,}));
                      break;
                    case 'INFO_OF_MANAGERS':
                      checkPermission(edittedPer);
                      setPermissions((prevState) => ({...prevState, infoOfManagers: true,}));
                      break;
                    default:
                      console.log("Error loading permissions");
                  }
                })
              }          
            });
          } else {
            console.log(data.reason);
          }
        });
      })
      .catch((err) => console.log(err));
    };
    fetchData();
    // const disableUnallowedPermissions = () => {
    //   console.log(permission)
    //   //disabled the unallowed permissions
    //   if(!permission.viewStoreHistory) disablePermission("VIEW_STORE_HISTORY");
    //   if(!permission.editExistingProduct) disablePermission("EDIT_EXISTING_PRODUCT");
    //   if(!permission.addNewProduct) disablePermission("ADD_NEW_PRODUCT");
    //   if(!permission.editProduct) disablePermission("EDIT_PRODUCT");
    //   if(!permission.removeProduct) disablePermission("REMOVE_PRODUCT");
    //   if(!permission.editStorePolicy) disablePermission("EDIT_STORE_POLICY");
    //   if(!permission.editStoreDiscount) disablePermission("EDIT_STORE_DISCOUNT");
    //   if(!permission.addReviewToProduct) disablePermission("ADD_REVIEW_TO_PRODUCT");
    //   if(!permission.viewForum) disablePermission("VIEW_FORUM");
    //   if(!permission.ReplyToForum) disablePermission("REPLY_TO_FORUM");
    //   if(!permission.closeStore) disablePermission("CLOSE_STORE");
    //   if(!permission.openStore) disablePermission("OPEN_STORE");
    //   if(!permission.infoOfManagers) disablePermission("INFO_OF_MANAGERS");
    //   console.log(permission)
    // }
    // setTimeout(disableUnallowedPermissions, 5000)       
  }, []);

  // const isEnablePermission = (permissionID) => {
  //   return document.getElementById(permissionID).disabled == false;
  // }
  // const isCheckPermission = (permissionID) => {
  //   return document.getElementById(permissionID).checked == true;
  // }
  // const enablePermission = (permissionID) => {
  //   document.getElementById(permissionID).disabled = false;
  // }
  // const uncheckPermission = (permissionID) => {
  //   document.getElementById(permissionID).checked = false;
  // }
  const disablePermission = (permissionID) => {
    document.getElementById(permissionID).disabled = true;
  }
  const checkPermission = (permissionID) => {
    document.getElementById(permissionID).checked = true;
  }

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
    if (
      newOfficialInput.username != "" &&
      newOfficialInput.ownerORmanager != ""
    ) {
      if (newOfficialInput.ownerORmanager == "Owner") {
        let newOwnerPermissions = [];
        if(permission.viewStoreHistory) newOwnerPermissions.push("VIEW_STORE_HISTORY");
        if(permission.editExistingProduct) newOwnerPermissions.push("EDIT_EXISTING_PRODUCT");
        if(permission.addNewProduct) newOwnerPermissions.push("ADD_NEW_PRODUCT");
        if(permission.editProduct) newOwnerPermissions.push("EDIT_PRODUCT");
        if(permission.removeProduct) newOwnerPermissions.push("REMOVE_PRODUCT");
        if(permission.editStorePolicy) newOwnerPermissions.push("EDIT_STORE_POLICY");
        if(permission.editStoreDiscount) newOwnerPermissions.push("EDIT_STORE_DISCOUNT");
        if(permission.addReviewToProduct) newOwnerPermissions.push("ADD_REVIEW_TO_PRODUCT");
        if(permission.viewForum) newOwnerPermissions.push("VIEW_FORUM");
        if(permission.ReplyToForum) newOwnerPermissions.push("REPLY_TO_FORUM");
        if(permission.closeStore) newOwnerPermissions.push("CLOSE_STORE");
        if(permission.openStore) newOwnerPermissions.push("OPEN_STORE");
        if(permission.infoOfManagers) newOwnerPermissions.push("INFO_OF_MANAGERS");
        const newOwner = {
          storeId: window.location.href.split("?").pop().slice(0, -1),
          userIdGiving: cookies.username,
          userGettingPermission: newOfficialInput.username,
          permissions: newOwnerPermissions,
        };
        await api
        .post(
          `/owner/`, newOwner
        )
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            createNotification("success", "Hired owner successfully", () =>
              router.push("/dashboard")
            )();
          } else {
            const { data } = res;
            console.log(data);
            createNotification("error", data.reason)(); //failed to hire new owner
          }
        })
        .catch((err) => console.log("failed to hire new owner"));
      }
      else if (newOfficialInput.ownerORmanager == "Manager") {
        const storeID = window.location.href.split("?").pop().slice(0, -1);
        await api
        .post(
          `/manager/?storeId=${storeID}&userIdGiving=${cookies.username}&UserGettingPermissionId=${newOfficialInput.username}`
        )
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            createNotification("success", "Hired manager successfully", () =>
              router.push("/dashboard")
            )();
          } else {
            console.log(data);
            createNotification("error", data.reason)(); //"failure hiring manager!"
          }
        })
        .catch((err) => console.log("failure hiring manager!"));
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
            <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
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
          
          <div className="col m-2" style={{display: newOfficialInput.ownerORmanager === "Owner" ? "block" : "none"}}>
            <h4><u>Permission for new owner</u></h4>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="VIEW_STORE_HISTORY"
              />
              <label className="form-check-label" for="flexSwitchCheckDefault">
                View store history
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="EDIT_EXISTING_PRODUCT"
              />
              <label className="form-check-label" for="flexSwitchCheckChecked">
                Edit existing product
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="ADD_NEW_PRODUCT"
              />
              <label className="form-check-label" for="flexSwitchCheckDefault">
                Add new product
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="EDIT_PRODUCT"
              />
              <label className="form-check-label" for="flexSwitchCheckChecked">
                Edit product
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="REMOVE_PRODUCT"
              />
              <label className="form-check-label" for="flexSwitchCheckDefault">
                Remove product
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="EDIT_STORE_POLICY"
              />
              <label className="form-check-label" for="flexSwitchCheckChecked">
                Edit store policy
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="EDIT_STORE_DISCOUNT"
              />
              <label className="form-check-label" for="flexSwitchCheckChecked">
                Edit store discount
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="ADD_REVIEW_TO_PRODUCT"
              />
              <label className="form-check-label" for="flexSwitchCheckDefault">
                Add review to product
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="VIEW_FORUM"
              />
              <label className="form-check-label" for="flexSwitchCheckChecked">
                View forum
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="REPLY_TO_FORUM"
              />
              <label className="form-check-label" for="flexSwitchCheckDefault">
                Reply to forum
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="CLOSE_STORE"
              />
              <label className="form-check-label" for="flexSwitchCheckChecked">
                Close store
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="OPEN_STORE"
              />
              <label className="form-check-label" for="flexSwitchCheckChecked">
                Open store
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="INFO_OF_MANAGERS"
              />
              <label className="form-check-label" for="flexSwitchCheckChecked">
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
