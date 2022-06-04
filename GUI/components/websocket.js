import { over } from "stompjs";
import SockJS from "sockjs-client";

export const WebSocket = ({ id, message }) => {
  const connect = () => {
    console.log("in connect");
    const Sock = new SockJS("http://localhost:9191/ws");
    const stompClient = over(Sock);
    stompClient.connect({}, onConnected, (err) => console.log("is error", err));
  };

  connect();

  const onConnected = () => {
    console.log("in connected");
    stompClient.subscribe("/chatroom/public", onMessageReceived);
    stompClient.subscribe("/user/" + id + "/private", onPrivateMessage);
    userJoin();
  };

  const userJoin = () => {
    console.log("in userjoin");
    let chatMessage = {
      senderName: id,
      status: "JOIN",
    };
    stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
    sendValue();
  };

  const onMessageReceived = (payload) => {
    console.log("recieved", payload);
  };

  const onPrivateMessage = (payload) => {
    console.log("send", payload);
  };

  const sendValue = () => {
    console.log("in sendvalue");
    if (stompClient) {
      let chatMessage = {
        senderName: id,
        message: message,
        status: "MESSAGE",
      };
      console.log(chatMessage);
      stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
    }
  };
};
