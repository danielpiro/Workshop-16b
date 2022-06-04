import Menu from "../components/menu";
import SearchBar from "../components/search-bar";
import axios from "axios";
import { useState, useEffect } from "react";
import Card from "../components/card";
import api from "../components/api";
import { useCookies } from "react-cookie";
import createNotification from "../components/norification";

const DisplayStoreOfficials = () => {
  const [owners, setOwners] = useState([]);
  const [managers, setManagers] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  //const [singlePurchase, setSinglePurchase] = useState({});

  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  useEffect(() => {
    const fetchApi = async () => {
      setIsLoading(!isLoading);
      await api
        .get(`/store/allRoles/?storeId=${window.location.href.split("?").pop()}&userIdRequesting=${cookies.userId}`)
        .then((res) => {
            const { data } = res;
            if (data.success) {
                //console.log(data.value);
                let owners = data.value.owners.slice(1,-1).split(',');
                let managers = data.value.managers.slice(1,-1).split(',');
                setOwners(owners);
                setManagers(managers);
                createNotification("success", "Displaying all user's purchases successfully")();
            }
            else{
              createNotification("error", data.reason)();
            }
        })
        .catch((err) => createNotification(err)());
    };
    fetchApi();
  }, []);

  return ( //TODO!!!
    <>
      <Menu />
      <div className="card-header">
        <h3>Display Store Officials</h3>
      </div>
      <div
        className="my-4"
        style={{ display: "flex", justifyContent: "center" }}
      >
        <h3><u>{window.location.href.split("?").pop()}</u></h3>        
      </div>
      <div className="my-4 m-4" style={{justifyContent: "center"}}>
        <div className="row">
          <div className="col">
            <h4><u>Store Owners:</u></h4>
            {owners.map((owner) => {
              return (
                <li className=" list-group-item m-1">
                  <p>{owner}</p>
                </li>
              );
            })}
          </div>

          <div className="col">
            <h4><u>Store Managers:</u></h4>
            {managers.map((man) => {
              return (
                <li className=" list-group-item m-1">
                  <p>{man}</p>
                </li>
              );
            })}
          </div> 
        </div>
      </div>
    </>
  );
};

export default DisplayStoreOfficials;
