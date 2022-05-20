import Menu from "../components/menu";
import axios from "axios";
import { useEffect, useState } from "react";
import { useRouter } from "next/router";
import { useCookies } from "react-cookie";
import createNotification from "../components/norification";
import Footer from "../components/footer";

const OpenNewStore = () => {
  const router = useRouter();

  const [isLoading, setIsLoading] = useState(true);
  const [userPermission, setUserPermission] = useState("Admin"); //= useState(""); //TODO: Need to change to Guest when logic is ready!
  const [openNewStoreInput, setOpenNewStoreInput] = useState({
    storename: "",
    storeOwnerUsername: "",
    additionalStoreOwnerUsername: "",
  });

  const onOpeningNewStore = (e) => {
    e.preventDefault();
    if (
      openNewStoreInput.storeOwnerUsername != "" &&
      openNewStoreInput.storename != ""
    ) {
      const isOpened = axios.post(
        `store/open/${openNewStoreInput.storeOwnerUsername}/${openNewStoreInput.storename}`
      );
      if (isOpened) {
        createNotification("success", "Create new store successfully", () =>
          router.push("/dashboard")
        )();
      } else {
        createNotification("error", "failure opening new store!")();
      }
    } else {
      createNotification(
        "error",
        "storename or username was not valid, please try again"
      )();
    }
  };

  useEffect(() => {
    const fetchPermission = async () => {
      const response = await axios.get("users/getUserPermission");
      setIsLoading(!isLoading);
      setUserPermission(response.data);
    };
    fetchPermission();
  }, []);

  return (
    <>
      <Menu />
      <div className="card-header">
        <h3>Open New Store</h3>
      </div>

      <div className="container m-2">
        <br />
        <div className="mb-3">
          <label for="formGroupExampleInput" className="form-label fs-4">
            New Store Name:
          </label>
          <input
            type="storename"
            className="form-control"
            placeholder="Enter Store Name"
            name="StoreName"
            value={openNewStoreInput.storename}
            onChange={(e) =>
              setOpenNewStoreInput((prevState) => ({
                ...prevState,
                storename: e.target.value,
              }))
            }
          />
        </div>
        <div className="mb-3">
          <label for="formGroupExampleInput2" className="form-label fs-4">
            Additional Store Owner Name:
          </label>
          <input
            type="additionalStoreOwner"
            className="form-control"
            placeholder="Enter Additional Store Owner Username"
            name="AdditionalStoreOwnerName"
            value={openNewStoreInput.additionalStoreOwnerUsername}
            onChange={(e) =>
              setOpenNewStoreInput((prevState) => ({
                ...prevState,
                additionalStoreOwnerUsername: e.target.value,
              }))
            }
          />
        </div>

        <button
          className="btn btn-primary mr-lg-3 m-2"
          style={{ width: "20%" }}
          onClick={onOpeningNewStore}
        >
          Open New Store
        </button>
      </div>
      <Footer />
    </>
  );
};

export default OpenNewStore;
