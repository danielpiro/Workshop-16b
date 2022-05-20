import Menu from "../components/menu";
import axios from "axios";
import { useState, useEffect } from "react";
import Footer from "../components/footer";

const ChangeStoreManagerPermissions = () => {
  // const [isLoading, setIsLoading] = useState(true);
  // const [searchValue, setSearchValue] = useState("");
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
  const [permission, setPermissions] = useState({
    p1: true,
    p2: true,
    p3: true,
    p4: true,
    p5: true,
    p6: true,
    p7: true,
    p8: true,
    p9: true,
    p10: true,
    p11: true,
    p12: true,
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

  useEffect(() => {
    const fetchPermission = async () => {
      const response = await axios.get("users/getUserPermission");
      setUserPermission(response.data);
    };
    fetchPermission();
  }, []);

  const onUpdatePermissions = (e) => {
    e.preventDefault();
    document.getElementById("Permission1").checked = true;
    document.getElementById("Permission1").disabled = true;
    document.getElementById("Permission2").checked = true;
    document.getElementById("Permission3").checked = true;
  };

  return (
    <>
      <Menu />

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
          {/* <div className="form-check form-switch">
                <input className="form-check-input" type="checkbox" id="flexSwitchCheckDisabled" disabled />
                <label className="form-check-label" for="flexSwitchCheckDisabled">Disabled switch checkbox input</label>
                </div>
                <div className="form-check form-switch">
                <input className="form-check-input" type="checkbox" id="flexSwitchCheckCheckedDisabled" checked disabled />
                <label className="form-check-label" for="flexSwitchCheckCheckedDisabled">Disabled checked switch checkbox input</label>
                </div> */}
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
    </>
  );
};

export default ChangeStoreManagerPermissions;
