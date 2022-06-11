import Menu from "../components/menu";
import { useEffect, useState } from "react";
import api from "../components/api";
import createNotification from "../components/norification";
import { useCookies } from "react-cookie";

const Discounts = () => {
  const [discountTypePercentage1, setDiscountTypePercentage1] = useState("DiscountType");
  const [discountTypePercentage2, setDiscountTypePercentage2] = useState("DiscountType");
  const [discountTypePercentage3, setDiscountTypePercentage3] = useState("DiscountType");
  const [discountTypePercentage4, setDiscountTypePercentage4] = useState("DiscountType");
  const [discountTypeCond1, setDiscountTypeCond1] = useState("DiscountType");
  const [discountTypeCond2, setDiscountTypeCond2] = useState("DiscountType");
  const [discountTypeCond3, setDiscountTypeCond3] = useState("DiscountType");
  const [discountTypeCond4, setDiscountTypeCond4] = useState("DiscountType");
  const discountTypes = ["Category", "Price", "Product With Amount", "Product Without Amount", "Always True"];
  const [operatorPercentage1, setOperatorPercentage1] = useState("And/Or/Xor/None");
  const [operatorPercentage2, setOperatorPercentage2] = useState("And/Or/Xor/None");
  const [operatorPercentage3, setOperatorPercentage3] = useState("And/Or/Xor/None");
  const [operatorCond1, setOperatorCond1] = useState("And/Or/Xor/None");
  const [operatorCond2, setOperatorCond2] = useState("And/Or/Xor/None");
  const [operatorCond3, setOperatorCond3] = useState("And/Or/Xor/None");
  const operators = ["And", "Or", "Xor", "None"];
  const [combinationType, setCombinationType] = useState("Max/Addition");
  const combinationTypes = ["Max", "Addition"];
  // const [allowForbidOption, setAllowForbidOptions] = useState("Allow/Forbid");
  // const allowForbidOptions = ["Allow", "Forbid"];
  const [discount, setDiscount] = useState("Create new discount");
  const discounts = ["Create new precentage discount", "Create new conditional discount"];
  const [policies, setPolicies] = useState(["..."]);
  const [policyType, setPolicyType] = useState("Select Policy Type");
  const policyTypes = ["cart", "category", "productWithoutAmount", "productWithAmount", "userId",
                      "userAge", "OnHoursOfTheDay", "OnDaysOfTheWeek", "OnDayOfMonth", "price", "AlwaysTrue"];
  const daysOfWeek = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
  const daysOfMonth = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31];
  const categories = ["Other", "Appliances", "Apps$Games", "Handmade", "Baby"];

  const [preds, setPreds] = useState({
    pred1: "Choose discount #1",
    pred2: "Choose discount #2",
    predToDelete: "Choose discount to delete",
  });
  const [precDiscount1, setPrecDiscount1] = useState({
    precentage: "",
    category: "Select a category",
    minPrice: "",
    productNameWithoutAmount: "",
    productName: "",
    minAmountOfProduct: "",
  });
  const [precDiscount2, setPrecDiscount2] = useState({
    precentage: "",
    category: "Select a category",
    minPrice: "",
    productNameWithoutAmount: "",
    productName: "",
    minAmountOfProduct: "",
  });
  const [condDiscount1, setCondDiscount1] = useState({
    policy: "Select conditional discount policy",
    discount: "Select discount",
  });
  const [condDiscount2, setCondDiscount2] = useState({
    policy: "Select conditional discount policy",
    discount: "Select discount",
  });

  const [cookies, setCookie, removeCookie] = useCookies([
    "username",
    "password",
    "userId",
    "type",
  ]);

  const onCreateCombinedDiscount = async (e) => {
    e.preventDefault();
    let storeID = window.location.href.split("?").pop();
    if(storeID.charAt(storeID.length-1) === '#'){
      storeID = storeID.slice(0, -1);
    }

    if(combinationType === "Max"){
      return await api
        .post(`/Discount/NewMaxDiscount?storeId=${storeID}&userId=${cookies.userId}&discountId1=${preds.pred1}&discountId2=${preds.pred2}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            discounts.push(data.value);
            createNotification("success", "Max Combined discount has been created successfully")();
          } else {
            createNotification("error", data.reason)();
          }
        })
        .catch((err) => createNotification("error", err)())
    }
    else if(combinationType === "Addition"){
      return await api
        .post(`/Discount/NewAdditionDiscount?storeId=${storeID}&userId=${cookies.userId}&discountId1=${preds.pred1}&discountId2=${preds.pred2}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            discounts.push(data.value);
            createNotification("success", "Addition Combined discount has been created successfully")();
          } else {
            createNotification("error", data.reason)();
          }
        })
        .catch((err) => createNotification("error", err)())
    }
    else {
      createNotification("error", "Combination type hasn't been chosen")();
    }
  };

  const onCreatePrecentageDiscount1 = async (e) => {
    e.preventDefault();
    let storeID = window.location.href.split("?").pop();
    if(storeID.charAt(storeID.length-1) === '#'){
      storeID = storeID.slice(0, -1);
    }
    let percentageProductPredicate = "";
    //Zero Operator Product Predicate
    if((operatorPercentage1 === "And/Or/Xor/None" || operatorPercentage1 === "None") &&
      (operatorPercentage2 === "And/Or/Xor/None" || operatorPercentage2 === "None") &&
      (operatorPercentage3 === "And/Or/Xor/None" || operatorPercentage3 === "None")) {
        if(discountTypePercentage1 === "Category") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount1.category}]}}`;
        else if(discountTypePercentage1 === "Price") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: ${precDiscount1.minPrice}}}`;
        else if(discountTypePercentage1 === "Product Without Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount1.productNameWithoutAmount}]}}`;
        else if(discountTypePercentage1 === "Product With Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount1.productName},${precDiscount1.minAmountOfProduct}]}}`;
        else percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}}}`; //Always True
    }
    //One Operator Product Predicate
    else if((operatorPercentage1 !== "And/Or/Xor/None" && operatorPercentage1 !== "None") &&
      (operatorPercentage2 === "And/Or/Xor/None" || operatorPercentage2 === "None") &&
      (operatorPercentage3 === "And/Or/Xor/None" || operatorPercentage3 === "None")) {
        if(discountTypePercentage1 === "Category") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount1.category}]} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Price") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: ${precDiscount1.minPrice}} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Product Without Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount1.productNameWithoutAmount}]} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Product With Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount1.productName},${precDiscount1.minAmountOfProduct}]} , op1: ${operatorPercentage1} , `;
        else percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}} , op1: ${operatorPercentage1} , `; //Always True

        if(discountTypePercentage2 === "Category") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount1.category}]}}`;
        else if(discountTypePercentage2 === "Price") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: ${precDiscount1.minPrice}}}`;
        else if(discountTypePercentage2 === "Product Without Amount") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount1.productNameWithoutAmount}]}}`;
        else if(discountTypePercentage2 === "Product With Amount") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount1.productName},${precDiscount1.minAmountOfProduct}]}}`;
        else percentageProductPredicate=`type2: {type: ${discountTypePercentage2}}}`; //Always True
    }
    //Two Operator Product Predicate
    else if((operatorPercentage1 !== "And/Or/Xor/None" && operatorPercentage1 !== "None") &&
     (operatorPercentage2 !== "And/Or/Xor/None" && operatorPercentage2 !== "None") &&
     (operatorPercentage3 === "And/Or/Xor/None" && operatorPercentage3 === "None")) {
        if(discountTypePercentage1 === "Category") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount1.category}]} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Price") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: ${precDiscount1.minPrice}} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Product Without Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount1.productNameWithoutAmount}]} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Product With Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount1.productName},${precDiscount1.minAmountOfProduct}]} , op1: ${operatorPercentage1} , `;
        else percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}} , op1: ${operatorPercentage1} , `; //Always True

        if(discountTypePercentage2 === "Category") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount1.category}]} , op2: ${operatorPercentage2} , `;
        else if(discountTypePercentage2 === "Price") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: ${precDiscount1.minPrice}} , op2: ${operatorPercentage2} , `;
        else if(discountTypePercentage2 === "Product Without Amount") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount1.productNameWithoutAmount}]} , op2: ${operatorPercentage2} , `;
        else if(discountTypePercentage2 === "Product With Amount") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount1.productName},${precDiscount1.minAmountOfProduct}]} , op2: ${operatorPercentage2} , `;
        else percentageProductPredicate=`type2: {type: ${discountTypePercentage2}} , op2: ${operatorPercentage2} , `; //Always True

        if(discountTypePercentage3 === "Category") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: [${precDiscount1.category}]}}`;
        else if(discountTypePercentage3 === "Price") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: ${precDiscount1.minPrice}}}`;
        else if(discountTypePercentage3 === "Product Without Amount") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: [${precDiscount1.productNameWithoutAmount}]}}`;
        else if(discountTypePercentage3 === "Product With Amount") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: [${precDiscount1.productName},${precDiscount1.minAmountOfProduct}]}}`;
        else percentageProductPredicate=`type3: {type: ${discountTypePercentage3}}}`; //Always True
    }
    //Three Operator Product Predicate
    else if((operatorPercentage1 !== "And/Or/Xor/None" && operatorPercentage1 !== "None") &&
      (operatorPercentage2 !== "And/Or/Xor/None" && operatorPercentage2 !== "None") &&
      (operatorPercentage3 !== "And/Or/Xor/None" && operatorPercentage3 !== "None")) {
        if(discountTypePercentage1 === "Category") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount1.category}]} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Price") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: ${precDiscount1.minPrice}} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Product Without Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount1.productNameWithoutAmount}]} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Product With Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount1.productName},${precDiscount1.minAmountOfProduct}]} , op1: ${operatorPercentage1} , `;
        else percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}} , op1: ${operatorPercentage1} , `; //Always True

        if(discountTypePercentage2 === "Category") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount1.category}]} , op2: ${operatorPercentage2} , `;
        else if(discountTypePercentage2 === "Price") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: ${precDiscount1.minPrice}} , op2: ${operatorPercentage2} , `;
        else if(discountTypePercentage2 === "Product Without Amount") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount1.productNameWithoutAmount}]} , op2: ${operatorPercentage2} , `;
        else if(discountTypePercentage2 === "Product With Amount") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount1.productName},${precDiscount1.minAmountOfProduct}]} , op2: ${operatorPercentage2} , `;
        else percentageProductPredicate=`type2: {type: ${discountTypePercentage2}} , op2: ${operatorPercentage2} , `; //Always True

        if(discountTypePercentage3 === "Category") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: [${precDiscount1.category}]} , op3: ${operatorPercentage2} , `;
        else if(discountTypePercentage3 === "Price") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: ${precDiscount1.minPrice}} , op3: ${operatorPercentage2} , `;
        else if(discountTypePercentage3 === "Product Without Amount") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: [${precDiscount1.productNameWithoutAmount}]} , op3: ${operatorPercentage2} , `;
        else if(discountTypePercentage3 === "Product With Amount") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: [${precDiscount1.productName},${precDiscount1.minAmountOfProduct}]} , op3: ${operatorPercentage2} , `;
        else percentageProductPredicate=`type3: {type: ${discountTypePercentage3}} , op3: ${operatorPercentage2} , `; //Always True

        if(discountTypePercentage4 === "Category") percentageProductPredicate=`type4: {type: ${discountTypePercentage4}, data4: [${precDiscount1.category}]}}`;
        else if(discountTypePercentage4 === "Price") percentageProductPredicate=`type4: {type: ${discountTypePercentage4}, data4: ${precDiscount1.minPrice}}}`;
        else if(discountTypePercentage4 === "Product Without Amount") percentageProductPredicate=`type4: {type: ${discountTypePercentage4}, data4: [${precDiscount1.productNameWithoutAmount}]}}`;
        else if(discountTypePercentage4 === "Product With Amount") percentageProductPredicate=`type4: {type: ${discountTypePercentage4}, data4: [${precDiscount1.productName},${precDiscount1.minAmountOfProduct}]}}`;
        else percentageProductPredicate=`type4: {type: ${discountTypePercentage4}}}`; //Always True
    }

    return await api
        .post(`/Discount/PercentageDiscount?storeId=${storeID}&userId=${cookies.userId}&percentage=${precDiscount1.precentage}&predicateOnProducts=${percentageProductPredicate}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            discounts.push(data.value);
            createNotification("success", "Percentage discount #1 has been created successfully")();
          } else {
            createNotification("error", data.reason)();
          }
        })
        .catch((err) => createNotification("error", err)())
  };

  const onCreatePrecentageDiscount2 = async (e) => {
    e.preventDefault();
    let storeID = window.location.href.split("?").pop();
    if(storeID.charAt(storeID.length-1) === '#'){
      storeID = storeID.slice(0, -1);
    }
    let percentageProductPredicate = "";
    //Zero Operator Product Predicate
    if((operatorPercentage1 === "And/Or/Xor/None" || operatorPercentage1 === "None") &&
      (operatorPercentage2 === "And/Or/Xor/None" || operatorPercentage2 === "None") &&
      (operatorPercentage3 === "And/Or/Xor/None" || operatorPercentage3 === "None")) {
        if(discountTypePercentage1 === "Category") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount2.category}]}}`;
        else if(discountTypePercentage1 === "Price") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: ${precDiscount2.minPrice}}}`;
        else if(discountTypePercentage1 === "Product Without Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount2.productNameWithoutAmount}]}}`;
        else if(discountTypePercentage1 === "Product With Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount2.productName},${precDiscount2.minAmountOfProduct}]}}`;
        else percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}}}`; //Always True
    }
    //One Operator Product Predicate
    else if((operatorPercentage1 !== "And/Or/Xor/None" && operatorPercentage1 !== "None") &&
      (operatorPercentage2 === "And/Or/Xor/None" || operatorPercentage2 === "None") &&
      (operatorPercentage3 === "And/Or/Xor/None" || operatorPercentage3 === "None")) {
        if(discountTypePercentage1 === "Category") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount2.category}]} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Price") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: ${precDiscount2.minPrice}} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Product Without Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount2.productNameWithoutAmount}]} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Product With Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount2.productName},${precDiscount2.minAmountOfProduct}]} , op1: ${operatorPercentage1} , `;
        else percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}} , op1: ${operatorPercentage1} , `; //Always True

        if(discountTypePercentage2 === "Category") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount2.category}]}}`;
        else if(discountTypePercentage2 === "Price") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: ${precDiscount2.minPrice}}}`;
        else if(discountTypePercentage2 === "Product Without Amount") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount2.productNameWithoutAmount}]}}`;
        else if(discountTypePercentage2 === "Product With Amount") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount2.productName},${precDiscount2.minAmountOfProduct}]}}`;
        else percentageProductPredicate=`type2: {type: ${discountTypePercentage2}}}`; //Always True
    }
    //Two Operator Product Predicate
    else if((operatorPercentage1 !== "And/Or/Xor/None" && operatorPercentage1 !== "None") &&
     (operatorPercentage2 !== "And/Or/Xor/None" && operatorPercentage2 !== "None") &&
     (operatorPercentage3 === "And/Or/Xor/None" && operatorPercentage3 === "None")) {
        if(discountTypePercentage1 === "Category") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount2.category}]} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Price") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: ${precDiscount2.minPrice}} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Product Without Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount2.productNameWithoutAmount}]} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Product With Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount2.productName},${precDiscount2.minAmountOfProduct}]} , op1: ${operatorPercentage1} , `;
        else percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}} , op1: ${operatorPercentage1} , `; //Always True

        if(discountTypePercentage2 === "Category") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount2.category}]} , op2: ${operatorPercentage2} , `;
        else if(discountTypePercentage2 === "Price") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: ${precDiscount2.minPrice}} , op2: ${operatorPercentage2} , `;
        else if(discountTypePercentage2 === "Product Without Amount") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount2.productNameWithoutAmount}]} , op2: ${operatorPercentage2} , `;
        else if(discountTypePercentage2 === "Product With Amount") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount2.productName},${precDiscount2.minAmountOfProduct}]} , op2: ${operatorPercentage2} , `;
        else percentageProductPredicate=`type2: {type: ${discountTypePercentage2}} , op2: ${operatorPercentage2} , `; //Always True

        if(discountTypePercentage3 === "Category") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: [${precDiscount2.category}]}}`;
        else if(discountTypePercentage3 === "Price") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: ${precDiscount2.minPrice}}}`;
        else if(discountTypePercentage3 === "Product Without Amount") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: [${precDiscount2.productNameWithoutAmount}]}}`;
        else if(discountTypePercentage3 === "Product With Amount") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: [${precDiscount2.productName},${precDiscount2.minAmountOfProduct}]}}`;
        else percentageProductPredicate=`type3: {type: ${discountTypePercentage3}}}`; //Always True
    }
    //Three Operator Product Predicate
    else if((operatorPercentage1 !== "And/Or/Xor/None" && operatorPercentage1 !== "None") &&
      (operatorPercentage2 !== "And/Or/Xor/None" && operatorPercentage2 !== "None") &&
      (operatorPercentage3 !== "And/Or/Xor/None" && operatorPercentage3 !== "None")) {
        if(discountTypePercentage1 === "Category") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount2.category}]} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Price") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: ${precDiscount2.minPrice}} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Product Without Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount2.productNameWithoutAmount}]} , op1: ${operatorPercentage1} , `;
        else if(discountTypePercentage1 === "Product With Amount") percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}, data1: [${precDiscount2.productName},${precDiscount2.minAmountOfProduct}]} , op1: ${operatorPercentage1} , `;
        else percentageProductPredicate=`{type1: {type: ${discountTypePercentage1}} , op1: ${operatorPercentage1} , `; //Always True

        if(discountTypePercentage2 === "Category") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount2.category}]} , op2: ${operatorPercentage2} , `;
        else if(discountTypePercentage2 === "Price") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: ${precDiscount2.minPrice}} , op2: ${operatorPercentage2} , `;
        else if(discountTypePercentage2 === "Product Without Amount") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount2.productNameWithoutAmount}]} , op2: ${operatorPercentage2} , `;
        else if(discountTypePercentage2 === "Product With Amount") percentageProductPredicate=`type2: {type: ${discountTypePercentage2}, data2: [${precDiscount2.productName},${precDiscount2.minAmountOfProduct}]} , op2: ${operatorPercentage2} , `;
        else percentageProductPredicate=`type2: {type: ${discountTypePercentage2}} , op2: ${operatorPercentage2} , `; //Always True

        if(discountTypePercentage3 === "Category") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: [${precDiscount2.category}]} , op3: ${operatorPercentage2} , `;
        else if(discountTypePercentage3 === "Price") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: ${precDiscount2.minPrice}} , op3: ${operatorPercentage2} , `;
        else if(discountTypePercentage3 === "Product Without Amount") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: [${precDiscount2.productNameWithoutAmount}]} , op3: ${operatorPercentage2} , `;
        else if(discountTypePercentage3 === "Product With Amount") percentageProductPredicate=`type3: {type: ${discountTypePercentage3}, data3: [${precDiscount2.productName},${precDiscount2.minAmountOfProduct}]} , op3: ${operatorPercentage2} , `;
        else percentageProductPredicate=`type3: {type: ${discountTypePercentage3}} , op3: ${operatorPercentage2} , `; //Always True

        if(discountTypePercentage4 === "Category") percentageProductPredicate=`type4: {type: ${discountTypePercentage4}, data4: [${precDiscount2.category}]}}`;
        else if(discountTypePercentage4 === "Price") percentageProductPredicate=`type4: {type: ${discountTypePercentage4}, data4: ${precDiscount2.minPrice}}}`;
        else if(discountTypePercentage4 === "Product Without Amount") percentageProductPredicate=`type4: {type: ${discountTypePercentage4}, data4: [${precDiscount2.productNameWithoutAmount}]}}`;
        else if(discountTypePercentage4 === "Product With Amount") percentageProductPredicate=`type4: {type: ${discountTypePercentage4}, data4: [${precDiscount2.productName},${precDiscount2.minAmountOfProduct}]}}`;
        else percentageProductPredicate=`type4: {type: ${discountTypePercentage4}}}`; //Always True
    }

    return await api
        .post(`/Discount/PercentageDiscount?storeId=${storeID}&userId=${cookies.userId}&percentage=${precDiscount2.precentage}&predicateOnProducts=${percentageProductPredicate}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            discounts.push(data.value);
            createNotification("success", "Percentage discount #2 has been created successfully")();
          } else {
            createNotification("error", data.reason)();
          }
        })
        .catch((err) => createNotification("error", err)())
  };

  const onCreateConditionalDiscount1 = async (e) => {
    e.preventDefault();
    let storeID = window.location.href.split("?").pop();
    if(storeID.charAt(storeID.length-1) === '#'){
      storeID = storeID.slice(0, -1);
    }
    let conditionalProductPredicate = `{cond1: {policyId: ${condDiscount1.policy}, discountId: ${condDiscount1.discount}}}`;

    return await api
        .post(`/Discount/ConditionalPercentageDiscount?storeId=${storeID}&userId=${cookies.userId}&percentage=${condDiscount1.precentage}&predicateOnProducts=${conditionalProductPredicate}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            discounts.push(data.value);
            createNotification("success", "Conditional discount #1 has been created successfully")();
          } else {
            createNotification("error", data.reason)();
          }
        })
        .catch((err) => createNotification("error", err)())
  };

  const onCreateConditionalDiscount2 = async (e) => {
    e.preventDefault();
    let storeID = window.location.href.split("?").pop();
    if(storeID.charAt(storeID.length-1) === '#'){
      storeID = storeID.slice(0, -1);
    }
    let conditionalProductPredicate = `{cond2: {policyId: ${condDiscount2.policy}, discountId: ${condDiscount2.discount}}}`;

    return await api
        .post(`/Discount/ConditionalPercentageDiscount?storeId=${storeID}&userId=${cookies.userId}&percentage=${condDiscount2.precentage}&predicateOnProducts=${conditionalProductPredicate}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            discounts.push(data.value);
            createNotification("success", "Conditional discount #2 has been created successfully")();
          } else {
            createNotification("error", data.reason)();
          }
        })
        .catch((err) => createNotification("error", err)())
  };

  const onDeleteDiscount = async (e) => {
    e.preventDefault();
    console.log("Deleting a discount");
    let storeID = window.location.href.split("?").pop();
    if(storeID.charAt(storeID.length-1) === '#'){
      storeID = storeID.slice(0, -1);
    }

    return await api
      .post(`/Discount/deleteDiscount?storeId=${storeID}&userId=${cookies.userId}&discountId=${preds.predToDelete}`)
      .then((res) => {
        const { data } = res;
        if (data.success) {
          console.log(data);
          discounts = discounts.filter((disc) => disc !== preds.predToDelete)
          createNotification("success", "Discount has been deleted successfully")();
        } else {
          createNotification("error", data.reason)();
        }
      })
      .catch((err) => createNotification("error", err)())
  }

  useEffect(() => {
    const fetchData = async () => {
      //get all the existing policies in the store...
      let storeID = window.location.href.split("?").pop();
      if(storeID.charAt(storeID.length-1) === '#'){
        storeID = storeID.slice(0, -1);
      }
      return await api
        .get(`Store/Polices?storeId=${storeID}`)
        .then((res) => {
          const { data } = res;
          if (data.success) {
            console.log(data);
            data.value.map((pol) => { //fill policies list:
              policies.push(`ID=${pol.PolicyId}`);
            })
          } else {
            createNotification("error", data.reason)();
          }
        })
        .then(async () => {
          return await api
            .get(`/Discount/getAll?storeId=${storeID}&userId=${cookies.userId}`)
            .then((res) => {
              const { data } = res;
              if (data.success) {
                console.log(data);
                data.value.DiscountIds.slice(1,-1).split(',').map((disc) => {
                  discounts.push(disc);
                });
              } else {
                createNotification("error", data.reason)();
              }
            });
        })
        .catch((err) => createNotification("error", `fail ${err}`)());
    };
    fetchData();
  }, []);

  return (
    <>
      <Menu />
      <div className="text-center my-5">
        <h3>Discounts</h3>
      </div>

      <div className="container">
        <div className="row">
          <div className="col m-2">
            <h5><u>Create Combined Discount</u></h5>
            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {preds.pred1}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {discounts.map((dis) => {
                  return (
                    <li>
                      <a className="dropdown-item"
                        onClick={() =>
                          setPreds((prevState) => ({
                            ...prevState,
                            pred1: dis,
                          }))
                        }
                      >
                        {dis}
                      </a>
                    </li>
                  )
                })}
              </ul>
            </div>
            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {combinationType}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {combinationTypes.map((comType) => {
                  return (
                    <li>
                      <a className="dropdown-item" onClick={() => setCombinationType(comType)}>
                        {comType}
                      </a>
                    </li>
                  )
                })}
              </ul>
            </div>
            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {preds.pred2}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {discounts.map((dis) => {
                  return (
                    <li>
                      <a className="dropdown-item"
                        onClick={() =>
                          setPreds((prevState) => ({
                            ...prevState,
                            pred2: dis,
                          }))
                        }
                      >
                        {dis}
                      </a>
                    </li>
                  )
                })}
              </ul>
            </div>

            <button className="btn btn-primary mr-lg-3" style={{ width: "50%" }}
            onClick={onCreateCombinedDiscount}>
              Create Combined Discount
            </button>
          </div>

          <div className="col m-2">
            <div hidden={preds.pred1 !== "Create new precentage discount" && preds.pred2 !== "Create new precentage discount"}>
              <h5><u>Select Precentage Discount Type</u></h5>
                <div className="input-group mb-3">
                  {/* <h2>(((</h2> */}
                  <div className="dropdown m-1">
                    <button className="btn btn-secondary dropdown-toggle" type="button"
                      id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        {discountTypePercentage1}
                    </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                      {discountTypes.map((disType) => {
                        return (
                          <li>
                            <a className="dropdown-item" onClick={() => setDiscountTypePercentage1(disType)}>{disType}</a>
                          </li>
                        )
                      })}
                    </ul>
                  </div>
                  <div className="dropdown m-1">
                    <button className="btn btn-secondary dropdown-toggle" type="button"
                      id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        {operatorPercentage1}
                    </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                      {operators.map((oper) => {
                        return (
                          <li>
                            <a className="dropdown-item" onClick={() => setOperatorPercentage1(oper)}>{oper}</a>
                          </li>
                        )
                      })}
                    </ul>
                  </div>
                  <div className="dropdown m-1">
                    <button className="btn btn-secondary dropdown-toggle" type="button"
                      id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        {discountTypePercentage2}
                    </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                      {discountTypes.map((disType) => {
                        return (
                          <li>
                            <a className="dropdown-item" onClick={() => setDiscountTypePercentage2(disType)}>{disType}</a>
                          </li>
                        )
                      })}
                    </ul>
                  </div>
                  {/* <h2>)</h2> */}
                </div>
              
                <div className="input-group mb-3">
                  <div className="dropdown m-1">
                    <button className="btn btn-secondary dropdown-toggle" type="button"
                      id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        {operatorPercentage2}
                    </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                      {operators.map((oper) => {
                        return (
                          <li>
                            <a className="dropdown-item" onClick={() => setOperatorPercentage2(oper)}>{oper}</a>
                          </li>
                        )
                      })}
                    </ul>
                  </div>

                  <div className="dropdown m-1">
                    <button className="btn btn-secondary dropdown-toggle" type="button"
                      id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        {discountTypePercentage3}
                    </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                      {discountTypes.map((disType) => {
                        return (
                          <li>
                            <a className="dropdown-item" onClick={() => setDiscountTypePercentage3(disType)}>{disType}</a>
                          </li>
                        )
                      })}
                    </ul>
                  </div>
                  {/* <h2>)</h2> */}
                </div>
              
                <div className="input-group mb-3">
                  <div className="dropdown m-1">
                    <button className="btn btn-secondary dropdown-toggle" type="button"
                      id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        {operatorPercentage3}
                    </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                      {operators.map((oper) => {
                        return (
                          <li>
                            <a className="dropdown-item" onClick={() => setOperatorPercentage3(oper)}>{oper}</a>
                          </li>
                        )
                      })}
                    </ul>
                  </div>

                  <div className="dropdown m-1">
                    <button className="btn btn-secondary dropdown-toggle" type="button"
                      id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        {discountTypePercentage4}
                    </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                      {discountTypes.map((disType) => {
                        return (
                          <li>
                            <a className="dropdown-item" onClick={() => setDiscountTypePercentage4(disType)}>{disType}</a>
                          </li>
                        )
                      })}
                    </ul>
                  </div>
                  {/* <h2>)</h2> */}
                </div>
            </div>

            <div hidden={preds.pred1 !== "Create new conditional discount" && preds.pred2 !== "Create new conditional discount"}>
            <h5><u>Select Conditional Discount Type</u></h5>
              <div className="input-group mb-3">
                <div className="dropdown m-1">
                  <button className="btn btn-secondary dropdown-toggle" type="button"
                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                      {discountTypeCond1}
                  </button>
                  <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {policyTypes.map((disType) => {
                      return (
                        <li>
                          <a className="dropdown-item" onClick={() => setDiscountTypeCond1(disType)}>{disType}</a>
                        </li>
                      )
                    })}
                  </ul>
                </div>
                
                <div className="dropdown m-1">
                  <button className="btn btn-secondary dropdown-toggle" type="button"
                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                      {operatorCond1}
                  </button>
                  <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {operators.map((oper) => {
                      return (
                        <li>
                          <a className="dropdown-item" onClick={() => setOperatorCond1(oper)}>{oper}</a>
                        </li>
                      )
                    })}
                  </ul>
                </div>
                <div className="dropdown m-1">
                  <button className="btn btn-secondary dropdown-toggle" type="button"
                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                      {discountTypeCond2}
                  </button>
                  <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {policyTypes.map((disType) => {
                      return (
                        <li>
                          <a className="dropdown-item" onClick={() => setDiscountTypeCond2(disType)}>{disType}</a>
                        </li>
                      )
                    })}
                  </ul>
                </div>
              </div>
              <div className="input-group mb-3">
                <div className="dropdown m-1">
                  <button className="btn btn-secondary dropdown-toggle" type="button"
                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                      {operatorCond2}
                  </button>
                  <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {operators.map((oper) => {
                      return (
                        <li>
                          <a className="dropdown-item" onClick={() => setOperatorCond2(oper)}>{oper}</a>
                        </li>
                      )
                    })}
                  </ul>
                </div>

                <div className="dropdown m-1">
                  <button className="btn btn-secondary dropdown-toggle" type="button"
                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                      {discountTypeCond3}
                  </button>
                  <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {policyTypes.map((disType) => {
                      return (
                        <li>
                          <a className="dropdown-item" onClick={() => setDiscountTypeCond3(disType)}>{disType}</a>
                        </li>
                      )
                    })}
                  </ul>
                </div>
              </div>
              
              <div className="input-group mb-3">
                <div className="dropdown m-1">
                  <button className="btn btn-secondary dropdown-toggle" type="button"
                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                      {operatorCond3}
                  </button>
                  <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {operators.map((oper) => {
                      return (
                        <li>
                          <a className="dropdown-item" onClick={() => setOperatorCond3(oper)}>{oper}</a>
                        </li>
                      )
                    })}
                  </ul>
                </div>

                <div className="dropdown m-1">
                  <button className="btn btn-secondary dropdown-toggle" type="button"
                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                      {discountTypeCond4}
                  </button>
                  <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {policyTypes.map((disType) => {
                      return (
                        <li>
                          <a className="dropdown-item" onClick={() => setDiscountTypeCond4(disType)}>{disType}</a>
                        </li>
                      )
                    })}
                  </ul>
                </div>
              </div>  
            </div>
          </div>
          
          <div className="col m-2">
            <div className="row" hidden={preds.pred1 !== "Create new precentage discount"}> 
              <h5><u>Create precentage discount #1</u></h5>
              <div className="input-group mb-3">
                <span className="input-group-text" id="basic-addon1" hidden={discountTypePercentage1 === "DiscountType" && 
                        discountTypePercentage2 === "DiscountType" && discountTypePercentage3 === "DiscountType" && discountTypePercentage4 === "DiscountType"}>
                  Enter Discount Precentage
                </span>
                <input type="text" className="form-control" placeholder="0-100"
                  aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType" && 
                        discountTypePercentage2 === "DiscountType" && discountTypePercentage3 === "DiscountType" && discountTypePercentage4 === "DiscountType"}
                  onChange={(e) =>
                    setPrecDiscount1((prevState) => ({
                      ...prevState,
                      precentage: e.target.value,
                    }))
                  }
                />
              </div>
              
              <div hidden={discountTypePercentage1 !== "Category" && discountTypePercentage2 !== "Category" && discountTypePercentage3 !== "Category" && discountTypePercentage4 !== "Category"}>
              <div className="dropdown m-1">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {precDiscount1.category}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    {categories.map((cat) => {
                      return (
                        <li>
                          <a className="dropdown-item"
                             onClick={(e) =>
                              setPrecDiscount1((prevState) => ({
                                ...prevState,
                                category: cat,
                              }))
                            }
                          >
                            {cat}
                          </a>
                        </li>
                      )
                    })} 
                  </ul>
                </div>
              </div>
              <div hidden={discountTypePercentage1 !== "Price" && discountTypePercentage2 !== "Price" && discountTypePercentage3 !== "Price" && discountTypePercentage4 !== "Price"}>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}>Min Price For Dicount</span>
                  <input type="text" className="form-control" placeholder="Price"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount1((prevState) => ({
                        ...prevState,
                        minPrice: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div hidden={discountTypePercentage1 !== "Product With Amount" && discountTypePercentage2 !== "Product With Amount" && discountTypePercentage3 !== "Product With Amount" && discountTypePercentage4 !== "Product With Amount"}>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">Product Name</span>
                  <input type="text" className="form-control" placeholder="Product Name With Amount"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount1((prevState) => ({
                        ...prevState,
                        productName: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">Min Amount Of Product In Cart</span>
                  <input type="text" className="form-control" placeholder="Amount"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount1((prevState) => ({
                        ...prevState,
                        minAmountOfProduct: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div hidden={discountTypePercentage1 !== "Product Without Amount" && discountTypePercentage2 !== "Product Without Amount" && 
                      discountTypePercentage3 !== "Product Without Amount" && discountTypePercentage4 !== "Product Without Amount"}>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">Product Name</span>
                  <input type="text" className="form-control" placeholder="Product Name Without Amount"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount1((prevState) => ({
                        ...prevState,
                        productNameWithoutAmount: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <button className="btn btn-primary mr-lg-3 m-2" style={{ width: "50%" }}
              onClick={onCreatePrecentageDiscount1}>
                Create Discount #1
              </button>
            </div>
            <div className="row" hidden={preds.pred2 !== "Create new precentage discount"}>
            <h5><u>Create precentage discount #2</u></h5>
              <div className="input-group mb-3">
                <span className="input-group-text" id="basic-addon1" hidden={discountTypePercentage1 === "DiscountType" && discountTypePercentage2 === "DiscountType" &&
                       discountTypePercentage3 === "DiscountType" && discountTypePercentage4 === "DiscountType"}>
                  Enter Discount Precentage
                </span>
                <input type="text" className="form-control" placeholder="0-100"
                  aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType" && 
                        discountTypePercentage2 === "DiscountType" && discountTypePercentage3 === "DiscountType" && discountTypePercentage4 === "DiscountType"}
                  onChange={(e) =>
                    setPrecDiscount2((prevState) => ({
                      ...prevState,
                      precentage: e.target.value,
                    }))
                  }
                />
              </div>
              
              <div hidden={discountTypePercentage1 !== "Category" && discountTypePercentage2 !== "Category" && discountTypePercentage3 !== "Category" && discountTypePercentage4 !== "Category"}>
              <div className="dropdown m-1">
                  <button
                    className="btn btn-secondary dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton1"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {precDiscount2.category}
                  </button>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="dropdownMenuButton1"
                  >
                    {categories.map((cat) => {
                      return (
                        <li>
                          <a className="dropdown-item"
                             onClick={(e) =>
                              setPrecDiscount2((prevState) => ({
                                ...prevState,
                                category: cat,
                              }))
                            }
                          >
                            {cat}
                          </a>
                        </li>
                      )
                    })} 
                  </ul>
                </div>
              </div>
              <div hidden={discountTypePercentage1 !== "Price" && discountTypePercentage2 !== "Price" && discountTypePercentage3 !== "Price" && discountTypePercentage4 !== "Price"}>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">Min Price For Dicount</span>
                  <input type="text" className="form-control" placeholder="Price"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount2((prevState) => ({
                        ...prevState,
                        minPrice: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div hidden={discountTypePercentage1 !== "Product With Amount" && discountTypePercentage2 !== "Product With Amount" && discountTypePercentage3 !== "Product With Amount" && discountTypePercentage4 !== "Product With Amount"}>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">Product Name</span>
                  <input type="text" className="form-control" placeholder="Product Name With Amount"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount2((prevState) => ({
                        ...prevState,
                        productName: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">Min Amount Of Product In Cart</span>
                  <input type="text" className="form-control" placeholder="Amount"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount2((prevState) => ({
                        ...prevState,
                        minAmountOfProduct: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div hidden={discountTypePercentage1 !== "Product Without Amount" && discountTypePercentage2 !== "Product Without Amount" && 
                      discountTypePercentage3 !== "Product Without Amount" && discountTypePercentage4 !== "Product Without Amount"}>
                <div className="input-group mb-3">
                  <span className="input-group-text" id="basic-addon1">Product Name</span>
                  <input type="text" className="form-control" placeholder="Product Name Without Amount"
                    aria-label="Precentage" aria-describedby="basic-addon1" hidden={discountTypePercentage1 === "DiscountType"}
                    onChange={(e) =>
                      setPrecDiscount2((prevState) => ({
                        ...prevState,
                        productNameWithoutAmount: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <button className="btn btn-primary mr-lg-3 m-2" style={{ width: "50%" }}
              onClick={onCreatePrecentageDiscount2}>
                Create Discount #2
              </button>
            </div>

            <div className="row" hidden={preds.pred1 !== "Create new conditional discount"}>
              <h5><u>Create conditional discount #1</u></h5>
              <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {condDiscount1.policy}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {policies.map((pol) => {
                  return (
                    <li>
                      <a className="dropdown-item"
                        onClick={() =>
                          setCondDiscount1((prevState) => ({
                            ...prevState,
                            policy: pol,
                          }))
                        }
                      >
                        {pol}
                      </a>
                    </li>
                  )
                })}
              </ul>
            </div>
            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {condDiscount1.discount}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {discounts.map((dis) => {
                  return (
                    <li>
                      <a className="dropdown-item"
                        onClick={() =>
                          setCondDiscount1((prevState) => ({
                            ...prevState,
                            discount: dis,
                          }))
                        }
                      >
                        {dis}
                      </a>
                    </li>
                  )
                })}
              </ul>
            </div>
              
              <button className="btn btn-primary mr-lg-3 m-2" style={{ width: "50%" }}
              onClick={onCreateConditionalDiscount1}>
                Create Discount #1
              </button>
            </div>
            <div className="row" hidden={preds.pred2 !== "Create new conditional discount"}>
              <h5><u>Create conditional conditional #2</u></h5>
              <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {condDiscount1.policy}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {policies.map((pol) => {
                  return (
                    <li>
                      <a className="dropdown-item"
                        onClick={() =>
                          setCondDiscount1((prevState) => ({
                            ...prevState,
                            policy: pol,
                          }))
                        }
                      >
                        {pol}
                      </a>
                    </li>
                  )
                })}
              </ul>
            </div>
            <div className="dropdown m-1">
              <button className="btn btn-secondary dropdown-toggle" type="button"
                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                  {condDiscount2.discount}
              </button>
              <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                {discounts.map((dis) => {
                  return (
                    <li>
                      <a className="dropdown-item"
                        onClick={() =>
                          setCondDiscount2((prevState) => ({
                            ...prevState,
                            discount: dis,
                          }))
                        }
                      >
                        {dis}
                      </a>
                    </li>
                  )
                })}
              </ul>
            </div>
              <button className="btn btn-primary mr-lg-3 m-2" style={{ width: "50%" }}
              onClick={onCreateConditionalDiscount2}>
                Create Discount #2
              </button>
            </div>
          </div>
        </div>

        <div className="row m-1">
          <div className="text-center my-5">
            <h3>Delete Policy</h3>
          </div>
        
          <div className="dropdown m-1 text-center">
            <button
              className="btn btn-secondary dropdown-toggle"
              type="button"
              id="dropdownMenuButton1"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              {preds.predToDelete}
            </button>
            <ul
              className="dropdown-menu"
              aria-labelledby="dropdownMenuButton1"
            >
              {discounts.map((disc) => {
                return (
                  <li>
                    <a className="dropdown-item"
                      onClick={() =>
                        setPreds((prevState) => ({
                          ...prevState,
                          predToDelete: disc,
                        }))
                      }
                    >
                      {disc}
                    </a>
                  </li>
                )
              })}
            </ul>
          </div>
          <button
            className="btn btn-primary mr-lg-3"
            style={{ width: "100%" }}
            onClick={onDeleteDiscount}
          >
            Delete a Policy
          </button>
        </div>
      </div>
    </>
  );
};

export default Discounts;
