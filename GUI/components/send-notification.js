import { over } from "stompjs";
import SockJS from "sockjs-client";

var stompClient = null;
const sendNotification = (notification) => {
  const connect = () => {
    console.log("in connect");
    const Sock = new SockJS("http://localhost:9191/ws");
    stompClient = over(Sock);
    stompClient.connect({}, sendValue, (err) => console.log("is error", err));
  };
  const sendValue = () => {
    console.log("in sendvalue");
    if (stompClient) {
      //StoreAppointment, StoreState, StoreDeleted, DeliveryDidntArrive, PaymentFailed, ProductShortage
      stompClient.send("/app/message", {}, JSON.stringify(notification));
    }
  };
  connect();
};

export default sendNotification;
