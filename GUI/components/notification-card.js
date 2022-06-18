import React from "react";

const NotificationCard = ({ sentFrom, subject, Title }) => {
  return (
    <div className="container">
      <div className="card" style={{ width: "25rem" }}>
        <div className="card-body">
          <h5 className="card-title text-center">Title: {Title}</h5>
          <p className="card-text text-center">Subject: {subject}</p>
          <p className="card-text text-center">Sent from: {sentFrom}</p>
        </div>
      </div>
    </div>
  );
};

export default NotificationCard;
