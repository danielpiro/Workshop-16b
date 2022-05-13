import { NotificationManager } from "react-notifications";
import "react-notifications/lib/notifications.css";

const createNotification = (type, message) => {
  return () => {
    switch (type) {
      case "info":
        NotificationManager.info(message, "", 3000);
        break;
      case "success":
        NotificationManager.success(message, "", 3000);
        break;
      case "warning":
        NotificationManager.warning(message, "", 3000);
        break;
      case "error":
        NotificationManager.error(message, "", 3000, () => {
          alert("callback");
        });
        break;
    }
  };
};

export default createNotification;
