import Menu from "../components/menu";
import { useState } from "react";

const Discounts = () => {
  const [discountType, setDiscountType] = useState("DiscountType");
  const [combinationType, setCombinationType] = useState("And/Or/Xor");
  const [preds, setPreds] = useState({
    pred1: "Choose predicate #1",
    pred2: "Choose predicate #2",
  });
  const [predsFeatures1, setPredsFeature1] = useState({
    allowORforbid: "Allow/Forbid",
    minPrice: "",
    maxPrice: "",
    minQunatity: "",
    maxQunatity: "",
    productShouldBeInCart: "",
    productShouldBeInCartMinQunatity: "",
    dayOfMonth: "Day Of Month",
    dayOfWeek: "Day Of Week",
    startHourOfDay: "",
    endHourOfDay: "",
    specificUser: "",
    age: "",
  });
  const [predsFeatures2, setPredsFeature2] = useState({
    allowORforbid: "Allow/Forbid",
    minPrice: "",
    maxPrice: "",
    minQunatity: "",
    maxQunatity: "",
    productShouldBeInCart: "",
    productShouldBeInCartMinQunatity: "",
    dayOfMonth: "Day Of Month",
    dayOfWeek: "Day Of Week",
    hourOfDay: "Hour Of Day",
    specificUser: "",
    age: "",
  });

  const onUpdateDiscount = (e) => {
    e.preventDefault();
    console.log(discountType);
  };

  const onCreatePredicate1 = (e) => {
    e.preventDefault();
    console.log("creating new predicate");
  };

  const onCreatePredicate2 = (e) => {
    e.preventDefault();
    console.log("creating new predicate");
  };

  //TODO: Make sure we got storeID in advance...
  //TDOO: Check that al the permissions in the system are written

  // useEffect(() => {
  //   const fetchApi = async () => {
  //     const response = await axios.get("https://fakestoreapi.com/products");
  //     setIsLoading(!isLoading);
  //     setProducts(response.data);
  //     //TODO: Add logic to check if the user has any permission!
  //     //setUserPermission("Admin/StoreOwner/StoreManager");
  //   };
  //   fetchApi();
  // }, []);

  return (
    <>
      <Menu />
      <div className="text-center my-5">
        <h3>Discounts</h3>
      </div>
    </>
  );
};

export default Discounts;
