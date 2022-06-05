import React from "react";
import Menu from "../components/menu";
import WebSocket from "../components/websocket";

const notificationPage = () => {
  return (
    <>
      <Menu />
      <WebSocket />
    </>
  );
};

export default notificationPage;
