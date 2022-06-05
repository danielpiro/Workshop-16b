import { over } from "stompjs";
import SockJS from "sockjs-client";
import { useEffect, useState } from "react";
import { useCookies } from "react-cookie";

var stompClient = null;

const WebSocket = () => {
  const [message, setMessage] = useState("");
  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);
  useEffect(() => {
    const connect = () => {
      const Sock = new SockJS("http://localhost:9191/ws");
      stompClient = over(Sock);
      stompClient.connect({}, onConnected, (err) =>
        console.log("is error", err)
      );
    };
    const onConnected = () => {
      stompClient.subscribe("/chatroom/public", onMessageReceived);
      stompClient.subscribe(
        "/user/" + cookies.userId + "/private",
        onPrivateMessage
      );
    };

    const onMessageReceived = (payload) => {
      setMessage(payload.body);
      console.log("recieved", typeof payload.body);
    };

    const onPrivateMessage = (payload) => {
      console.log("send", payload.body);
    };
    connect();
  }, []);

  return <div>{message}</div>;
};

export default WebSocket;
