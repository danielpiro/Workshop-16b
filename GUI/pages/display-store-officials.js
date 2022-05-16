import AdminMenu from "../components/menus/menuAdmin";
import StoreOwnerMenu from "../components/menus/menuStoreOwner";
import StoreManagerMenu from "../components/menus/menuStoreManager";
import SubscriberMenu from "../components/menus/menuSubscriber";
import GuestMenu from "../components/menus/menuGuest";
import SearchBar from "../components/search-bar";
import axios from "axios";
import { useState, useEffect } from "react";
import Card from "../components/card";
import Footer from "../components/footer";

const DisplayStoreOfficials = () => {
  const [officials, setOfficials] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!
  
  useEffect(() => {
      const fetchPermission = async () => {
        const response = await axios.get("users/getUserPermission");
        setUserPermission(response.data);
      };
      fetchPermission();
  }, []);

  useEffect(() => {
    const fetchApi = async () => {
      const response = await axios.get("https://dummyjson.com/products");
      setIsLoading(!isLoading);
      const { data } = response;
      setOfficials(data.products);
    };
    fetchApi();
  }, []);

  var menu;
  if (userPermission == "Admin"){
    menu = <AdminMenu />;
  }
  else if (userPermission == "Owner"){
    menu = <StoreOwnerMenu />;
  }
  else if (userPermission == "Manager"){
    menu = <StoreManagerMenu />;
  }
  else if (userPermission == "Subscriber"){
    menu = <SubscriberMenu />;
  }
  else{
    menu = <GuestMenu />;
  }

  return (
    <>
      {menu}
      <div className="card-header">
        <h3>Display Store Officials</h3>
      </div>
      <div className="my-4">
        <SearchBar setOfficials={setOfficials} />
      </div>
      <div
        className="my-4"
        style={{ display: "flex", justifyContent: "center" }}
      ></div>
      {!isLoading ? (
        <div style={{ display: "table", width: "100%" }}>
          <ul className="list-group-item" style={{ display: "table-cell" }}>
            <div className="row m-3">
                <div className="col">
                    <h3 className="card-title">Official ID</h3>
                </div>
                <div className="col">
                    <h3 className="card-desc">Official Name</h3>
                </div>
                <div className="col">
                    <h3 className="card-price">Official Permission</h3>
                </div>
            </div> 
            {officials.map((official) => {
              return (
                <li className=" list-group-item" key={official.id}>
                  <div className="card w-100 m-1">
                    <div className="card-body">
                        <div className="row">
                            <div className="col">
                                <h5 className="card-title">{official.id}</h5>
                            </div>
                            <div className="col">
                                <p className="card-desc">{official.title}</p>
                            </div>
                            <div className="col">
                                <p className="card-price">{official.description}</p>
                            </div>
                        </div>    
                    </div>
                  </div>
                </li>
              );
            })}
          </ul>
        </div>
      ) : (
        <div className="container h-100 my-6">
          <div className="row align-items-center justify-content-center">
            <div className="spinner-border" />
          </div>
        </div>
      )}
      <Footer />
    </>
  );
};

export default DisplayStoreOfficials;
