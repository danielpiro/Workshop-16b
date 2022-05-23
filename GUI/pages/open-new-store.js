import Menu from "../components/menu";
import api from "../components/api";
import { useEffect, useState } from "react";
import { useRouter } from "next/router";
import createNotification from "../components/norification";
import { useCookies } from "react-cookie";

const OpenNewStore = () => {
  const router = useRouter();
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  const [isLoading, setIsLoading] = useState(false);
  const [openNewStoreInput, setOpenNewStoreInput] = useState({
    storename: "",
    additionalStoreOwnerUsername: "",
  });

  const onOpeningNewStore = async (e) => {
    e.preventDefault();
    if (
      openNewStoreInput.additionalStoreOwnerUsername !== "" &&
      openNewStoreInput.storename !== ""
    ) {
      await api
        .post(
          `/store/open/?userId=${cookies.username}&storeName=${openNewStoreInput.storename}`
        )
        .then((res) => {
          if (res.status === 200) {
            const { data } = res;
            console.log(data);
            createNotification("success", "Create new store successfully", () =>
              router.push("/dashboard")
            )();
          } else {
            const { data } = res;
            console.log(data);
            createNotification("error", "failure opening new store!")();
          }
        })
        .catch((err) => console.log("err"));
    } else {
      createNotification(
        "error",
        "storename or username was not valid, please try again"
      )();
    }
  };

  return (
    <>
      <Menu />
      <div className="text-center my-5">
        <h3>Open New Store</h3>
      </div>
      <div className="d-flex justify-content-center">
        <div className="list-group">
          <div className="mb-3">
            <label for="formGroupExampleInput" className="form-label fs-4">
              New Store Name
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
              Additional Store Owner Name
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
        </div>
      </div>
      <div className="d-flex justify-content-center">
        <button
          className="btn btn-primary mr-lg-3 m-2"
          onClick={onOpeningNewStore}
        >
          Open New Store
        </button>
      </div>
    </>
  );
};

export default OpenNewStore;
