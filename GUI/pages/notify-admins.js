import Menu from "../components/menu";
import { useState } from "react";
import api from "../components/api";
import { useCookies } from "react-cookie";
import createNotification from "../components/norification";
import { ReactSearchAutocomplete } from "react-search-autocomplete";

const NotifyAdmins = () => {
  const [userInput, setUserInput] = useState({
    subject: "",
    title: "",
    body: "",
  });
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
    "session",
  ]);
  const subjects = [
    { id: 1, name: "StoreForum" },
    { id: 2, name: "StoreAppointment" },
    { id: 3, name: "StoreDeleted" },
    { id: 4, name: "DeliveryDidntArrive" },
    { id: 5, name: "PaymentFailed" },
    { id: 6, name: "ProductShortage" },
  ];
  const onNotify = async (e) => {
    e.preventDefault();
    if (
      userInput.subject === "" ||
      userInput.body === "" ||
      userInput.title === ""
    ) {
      return createNotification("error", "Please fill all the form fields")();
    }
    return await api
      .post(
        `/complaints/?sessionID=${cookies.session}&senderId=${cookies.userId}&subject=${userInput.subject}&title=${userInput.title}&body=${userInput.body}`
      )
      .then((res) => {
        const { data } = res;
        if (data.success) {
          createNotification("success", "Send complaint successfully")();
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
  };
  const handleOnSelect = (item) => {
    setUserInput((prev) => ({ ...prev, subject: item.name }));
  };

  return (
    <>
      <Menu />
      <div className="text-center m-5">
        <h3>Notify Admins</h3>
      </div>
      <div className="container d-flex justify-content-center">
        <form>
          <div className="mb-3">
            <label htmlFor="exampleInputEmail1" className="form-label">
              Subject
            </label>
            <ReactSearchAutocomplete
              items={subjects}
              onSelect={handleOnSelect}
            />
          </div>
          <div className="mb-3">
            <label htmlFor="exampleInputPassword1" className="form-label">
              Title
            </label>
            <input
              type="text"
              className="form-control"
              onChange={(e) =>
                setUserInput((prev) => ({ ...prev, title: e.target.value }))
              }
            />
          </div>
          <div className="mb-3">
            <label htmlFor="exampleInputPassword1" className="form-label">
              Body
            </label>
            <textarea
              class="form-control"
              id="exampleFormControlTextarea1"
              rows="3"
              onChange={(e) =>
                setUserInput((prev) => ({ ...prev, body: e.target.value }))
              }
            />
          </div>
          <button type="submit" className="btn btn-primary" onClick={onNotify}>
            Submit
          </button>
        </form>
      </div>
    </>
  );
};

export default NotifyAdmins;
