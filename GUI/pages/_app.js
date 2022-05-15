import "bootstrap/dist/css/bootstrap.css";
import "../styles/globals.css";
import Script from "next/script";
import "../styles/signin.css";
import "../styles/form-validation.css";
import { NotificationContainer } from "react-notifications";
const MyApp = ({ Component, pageProps }) => {
  return (
    <>
      <Script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" />
      <Component {...pageProps} />;
      <NotificationContainer />
    </>
  );
};

export default MyApp;
