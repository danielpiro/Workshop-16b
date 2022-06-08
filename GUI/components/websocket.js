import { over } from "stompjs";
import SockJS from "sockjs-client";
import createNotification from "./norification";

var stompClient = null;

const WebSocket = (user) => {
  const connect = () => {
    const Sock = new SockJS("http://localhost:9191/ws");
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, (err) => console.log("is error", err));
  };
  const onConnected = () => {
    stompClient.subscribe("/chatroom/public", onMessageReceived);
    stompClient.subscribe("/user/" + user + "/private", onPrivateMessage);
  };

  const onMessageReceived = (payload) => {
    createNotification("info", payload.body)();
  };

  const onPrivateMessage = (payload) => {
    createNotification("info", payload.body)();
  };
  connect();
};

export default WebSocket;
