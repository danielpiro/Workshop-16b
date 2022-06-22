import React, { useEffect, useState } from "react";
import api from "../components/api";
import Menu from "../components/menu";
import { useCookies } from "react-cookie";
import createNotification from "../components/norification";
import NotificationCard from "../components/notification-card";

const notificationPage = () => {
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
    "session",
  ]);
  const [complaints, setComplaints] = useState([]);
  const fetch = async () => {
    return await api
      .get(
        `/notification/complaint/?sessionID=${cookies.session}&userId=${cookies.userId}`
      )
      .then((res) => {
        const { data } = res;
        if (data.success) {
          console.log(data.value);
          createNotification("success", "Get complaints successfully")();
          setComplaints(data.value);
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => console.log(err));
  };
  useEffect(() => {
    return fetch();
  }, []);

  return (
    <>
      <Menu />
      <div className="text-center m-5">
        <h1>Notification</h1>
      </div>
      <div className="container d-flex justify-content-center">
        <div className="row">
          <ul className="list-group">
            {complaints.map((complaint) => {
              return (
                <div className="mb-3">
                  <li>
                    <NotificationCard
                      sentFrom={complaint.sentFrom}
                      subject={complaint.subject}
                      Title={complaint.Title}
                    />
                  </li>
                </div>
              );
            })}
          </ul>
        </div>
      </div>
    </>
  );
};

export default notificationPage;
