import AdminMenu from "../components/menus/menuAdmin";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import axios from "axios";
import { useState, useEffect } from "react";
import Footer from "../components/footer";

const ChangeStoreManagerPermissions = () => {
  // const [isLoading, setIsLoading] = useState(true);
  // const [searchValue, setSearchValue] = useState("");
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
  const [permission, setPermissions] = useState({
    p1: false, p2: false, p3: false, p4: false, p5: false, p6: false,
    p7: false, p8: false, p9: false, p10: false, p11: false, p12: false,
  });

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

//   useEffect(() => {
//     const fetchPermission = async () => {
//       const response = await axios.get("users/getUserPermission");
//       setUserPermission(response.data);
//     };
//     fetchPermission();
//   }, []);

//   useEffect(() => {
//     const fetchSpecifingPermission = async () => {
//       const response = await axios.get("users/getUserSpecificPermission");
//       setPermissions(response.data);
//     };
//     fetchSpecifingPermission();
//   }, []);

  const isEnablePermission = (permissionID) => {
    return document.getElementById(permissionID).disabled == false;
  }
  const isCheckPermission = (permissionID) => {
    return document.getElementById(permissionID).checked == true;
  }

  const enablePermission = (permissionID) => {
    document.getElementById(permissionID).disabled = false;
  }
  const disablePermission = (permissionID) => {
    document.getElementById(permissionID).disabled = true;
  }
  const uncheckPermission = (permissionID) => {
    document.getElementById(permissionID).checked = false;
  }
  const checkPermission = (permissionID) => {
    document.getElementById(permissionID).checked = true;
  }

  const onUpdatePermissions = (e) => {
    e.preventDefault();
    setTimeout(() => { 
        setPermissions((prevState) => ({...prevState, p1: isCheckPermission("Permission1"),}));
        setPermissions((prevState) => ({...prevState, p2: isCheckPermission("Permission2"),}));
        setPermissions((prevState) => ({...prevState, p3: isCheckPermission("Permission3"),}));
        setPermissions((prevState) => ({...prevState, p4: isCheckPermission("Permission4"),}));
        setPermissions((prevState) => ({...prevState, p5: isCheckPermission("Permission5"),}));
        setPermissions((prevState) => ({...prevState, p6: isCheckPermission("Permission6"),}));
        setPermissions((prevState) => ({...prevState, p7: isCheckPermission("Permission7"),}));
        setPermissions((prevState) => ({...prevState, p8: isCheckPermission("Permission8"),}));
        setPermissions((prevState) => ({...prevState, p9: isCheckPermission("Permission9"),}));
        setPermissions((prevState) => ({...prevState, p10: isCheckPermission("Permission10"),}));
        setPermissions((prevState) => ({...prevState, p11: isCheckPermission("Permission11"),}));
        setPermissions((prevState) => ({...prevState, p12: isCheckPermission("Permission12"),}));
        console.log(permission);
    }, 3000);
  };

  var menu;
  if (userPermission == "Admin") {
    menu = <AdminMenu />;
  } else if (userPermission == "Subscriber") {
    menu = <SubscriberMenu />;
  } else {
    menu = <GuestMenu />;
  }

  return (
    <>
      {menu}

      <div className="card-header">
        <h3>Change Store Manager's Permissions</h3>
      </div>
      <div className="container">
        <div>
          <div className="row">
            <input
              className="form-control mr-sm-2 m-2"
              type="search"
              placeholder="Enter username"
              aria-label="Search"
            />
          </div>
          <div className="row">
            <input
              className="form-control mr-sm-2 m-2"
              type="search"
              placeholder="Enter store name"
              aria-label="Search"
            />
          </div>
          <div className="row">
            <button className="btn btn-primary mr-lg-3">Search</button>
          </div>
        </div>

        <div className="row m-3">
          <div className="form-check form-switch">
            <input
              className="form-check-input"
              type="checkbox"
              id="Permission1"
            />
            <label className="form-check-label" for="flexSwitchCheckDefault">
              View store history
            </label>
          </div>
          <div className="form-check form-switch">
            <input
              className="form-check-input"
              type="checkbox"
              id="Permission2"
            />
            <label className="form-check-label" for="flexSwitchCheckChecked">
              Edit existing product
            </label>
          </div>
          <div className="form-check form-switch">
            <input
              className="form-check-input"
              type="checkbox"
              id="Permission3"
            />
            <label className="form-check-label" for="flexSwitchCheckDefault">
              Add new product
            </label>
          </div>
          <div className="form-check form-switch">
            <input
              className="form-check-input"
              type="checkbox"
              id="Permission4"
            />
            <label className="form-check-label" for="flexSwitchCheckChecked">
              Edit product
            </label>
          </div>
          <div className="form-check form-switch">
            <input
              className="form-check-input"
              type="checkbox"
              id="Permission5"
            />
            <label className="form-check-label" for="flexSwitchCheckDefault">
              Remove product
            </label>
          </div>
          <div className="form-check form-switch">
            <input
              className="form-check-input"
              type="checkbox"
              id="Permission6"
            />
            <label className="form-check-label" for="flexSwitchCheckChecked">
              Edit store discount
            </label>
          </div>
          <div className="form-check form-switch">
            <input
              className="form-check-input"
              type="checkbox"
              id="Permission7"
            />
            <label className="form-check-label" for="flexSwitchCheckDefault">
              Add review to product
            </label>
          </div>
          <div className="form-check form-switch">
            <input
              className="form-check-input"
              type="checkbox"
              id="Permission8"
            />
            <label className="form-check-label" for="flexSwitchCheckChecked">
              View forum
            </label>
          </div>
          <div className="form-check form-switch">
            <input
              className="form-check-input"
              type="checkbox"
              id="Permission9"
            />
            <label className="form-check-label" for="flexSwitchCheckDefault">
              Reply to forum
            </label>
          </div>
          <div className="form-check form-switch">
            <input
              className="form-check-input"
              type="checkbox"
              id="Permission10"
            />
            <label className="form-check-label" for="flexSwitchCheckChecked">
              Close store
            </label>
          </div>
          <div className="form-check form-switch">
            <input
              className="form-check-input"
              type="checkbox"
              id="Permission11"
            />
            <label className="form-check-label" for="flexSwitchCheckChecked">
              Open store
            </label>
          </div>
          <div className="form-check form-switch">
            <input
              className="form-check-input"
              type="checkbox"
              id="Permission12"
            />
            <label className="form-check-label" for="flexSwitchCheckChecked">
              Info of managers
            </label>
          </div>

          <button
            className="btn btn-primary mr-lg-3"
            style={{ width: "20%" }}
            onClick={onUpdatePermissions}
          >
            Update Permissions
          </button>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default ChangeStoreManagerPermissions;
