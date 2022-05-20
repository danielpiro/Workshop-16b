import Menu from "../components/menu";
import axios from "axios";
import { useState, useEffect } from "react";
import Footer from "../components/footer";

const Discounts = () => {
  const [userPermission, setUserPermission] = useState("Admin"); //TODO: Need to change to Guest when logic is ready!

  return (
    <>
      <Menu />
      <div className="card-header">
        <h3>Edit store's discounts</h3>
      </div>
      <Footer />
    </>
  );
};

export default Discounts;
