import "bootstrap/dist/css/bootstrap.css";
import "../styles/globals.css";
import Script from "next/script";

const MyApp = ({ Component, pageProps }) => {
  return (
    <>
      <Script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" />
      <Component {...pageProps} />;
    </>
  );
};

export default MyApp;
