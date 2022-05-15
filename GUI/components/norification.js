import { NotificationManager } from "react-notifications";
import "react-notifications/lib/notifications.css";

const createNotification = (type, message, onNotification = () => {}) => {
  return () => {
    switch (type) {
      case "info":
        NotificationManager.info(message, "Info", 3000, onNotification());
        break;
      case "success":
        NotificationManager.success(message, "Success", 3000, onNotification());
        break;
      case "warning":
        NotificationManager.warning(message, "Warning", 3000, onNotification());
        break;
      case "error":
        NotificationManager.error(message, "Error", 3000, onNotification());
        break;
    }
  };
};

export default createNotification;
