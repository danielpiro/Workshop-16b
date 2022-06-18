package com.example.demo.Store.StorePurchase.Discounts;

import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.Store.Product;
import com.example.demo.Store.ProductsCategories;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.*;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite.*;
import com.example.demo.Store.StorePurchase.predicates.PredicateTimeType;
import com.example.demo.Store.StorePurchase.predicates.PredicateUserType;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.tomcat.util.json.ParseException;

import java.time.LocalDateTime;
import java.util.*;


public class DiscountBuilder {

    public PercentageDiscount newPercentageDiscount(float discountPercent,String predicateJson) throws ParseException, SupplyManagementException {
        return new PercentageDiscount(discountPercent, parseProductPredicate(predicateJson));
    }
    public ConditionalPercentageDiscount newConditionalDiscount(float discountPercent,String cartPredicateJson, String productPredicateJson) throws ParseException, SupplyManagementException {
        PercentageDiscount percentageDiscount = new PercentageDiscount(discountPercent,parseProductPredicate(productPredicateJson));
        return new ConditionalPercentageDiscount(percentageDiscount, parsePolicyPredicate(cartPredicateJson));
    }
    public AdditionDiscount newAdditionDiscount(  ComposableDiscounts first,   ComposableDiscounts second){
        return newAdditionDiscount(first,second);
    }

    public MaxDiscount newMaxDiscount( ComposableDiscounts first,   ComposableDiscounts second){
        return newMaxDiscount(first,second);
    }

    private DiscountPredicate parseProductPredicate(String predicateJson) throws ParseException, SupplyManagementException {
        DiscountPredicate outputPred;
        JsonObject gson = new JsonParser().parse(predicateJson).getAsJsonObject();

        JsonElement type1 = gson.get("type1");
        if(type1.isJsonNull()){
            throw  new ParseException("need to have at least one predicate");
        }
        DiscountPredicate predicate1 = parseOneDiscountPredicate(type1.getAsJsonObject());
        outputPred = predicate1;

        JsonElement op1 = gson.get("op1");
        JsonElement type2 = gson.get("type2");
        if(op1== null || type2== null){
            return outputPred;
        }
        DiscountPredicate predicate2 = parseOneDiscountPredicate(type2.getAsJsonObject());
        outputPred =parseOperatorForTwoDiscountPredicates(outputPred,predicate2,op1);


        JsonElement op2 = gson.get("op2");
        JsonElement type3 = gson.get("type3");
        if(op2 == null || type3 == null){
            return outputPred;
        }
        DiscountPredicate predicate3 = parseOneDiscountPredicate(type3.getAsJsonObject());
        outputPred =parseOperatorForTwoDiscountPredicates(outputPred,predicate3,op2);


        JsonElement op3 = gson.get("op3");
        JsonElement type4 = gson.get("type3");
        if(op3== null || type4== null){
            return outputPred;
        }
        DiscountPredicate predicate4 = parseOneDiscountPredicate(type4.getAsJsonObject());
        outputPred =parseOperatorForTwoDiscountPredicates(outputPred,predicate4,op3);


        return outputPred;

    }
    private DiscountPredicate parseOperatorForTwoDiscountPredicates(DiscountPredicate pred1, DiscountPredicate pred2, JsonElement operatorJson) throws ParseException {
        String opString = operatorJson.getAsString();
        switch (opString){
            case ("And"):
                return new DiscountPredicateAnd(pred1,pred2);
            case ("Or"):
                return new DiscountPredicateOr(pred1,pred2);
            case ("Xor"):
                return new DiscountPredicateXor(pred1,pred2);
        }
        throw  new ParseException("no operator with this type");
    }

    private DiscountPredicate parseOneDiscountPredicate(JsonObject predicateJson ) throws SupplyManagementException, ParseException { //TODO: Amit
        JsonElement type = predicateJson.get("type");
        switch (type.getAsString()){
            case ("category"):
                List<ProductsCategories> categoriesList = getProductsCategories(predicateJson);
                return new CategoryPredicate(categoriesList);

            case ("price"):
                return getPricePredicate(predicateJson);

            case ("productWithoutAmount"):
                return getProductWithoutAmountPredicate(predicateJson);

            case ("productWithAmount"):
                return getProductWithAmountPredicate(predicateJson);

            case ("Always"):
                return new AlwaysTrue();

        }
        throw new ParseException("no predicate with this type");
    }

    private ProductPredicate getProductWithAmountPredicate(JsonObject predicateJson) throws SupplyManagementException {
        JsonElement productsWithAmountJsonList = predicateJson.get("data1");

        Iterator<JsonElement> productsWithAmountIterator = productsWithAmountJsonList.getAsJsonArray().iterator();
        HashMap<PurchasableProduct,Integer> productsWithAmountList = new HashMap<>();
        while (productsWithAmountIterator.hasNext()){
            JsonElement priceAmountElement = productsWithAmountIterator.next();
            JsonElement priceElement = priceAmountElement.getAsJsonArray().get(0);
            JsonElement AmountElement = priceAmountElement.getAsJsonArray().get(1);
            productsWithAmountList.put(new Product(priceElement.getAsString(),"notRealProduct",0,0,ProductsCategories.Other.toString()),AmountElement.getAsInt());
        }
        return new ProductPredicate(productsWithAmountList);
    }

    private ProductPredicate getProductWithoutAmountPredicate(JsonObject predicateJson) throws SupplyManagementException {
        JsonElement productsJsonList = predicateJson.get("data1");

        Iterator<JsonElement> productsIterator = productsJsonList.getAsJsonArray().iterator();
        List<PurchasableProduct> productsList = new ArrayList<>();
        while (productsIterator.hasNext()){
            JsonElement priceElement = productsIterator.next();
            productsList.add(new Product(priceElement.getAsString(),"notRealProduct",0,0,ProductsCategories.Other.toString()));
        }
        return new ProductPredicate(productsList);
    }

    private PricePredicate getPricePredicate(JsonObject predicateJson) {
        JsonElement jsonPrice = predicateJson.get("data1");
        float price = jsonPrice.getAsFloat();
        return new PricePredicate(price);
    }

    private List<ProductsCategories> getProductsCategories(JsonObject predicateJson) {
        JsonElement categoriesJsonList = predicateJson.get("data1");

        Iterator<JsonElement> categoryIterator = categoriesJsonList.getAsJsonArray().iterator();
        List<ProductsCategories> categoriesList = new ArrayList<>();
        while (categoryIterator.hasNext()){
            JsonElement cat = categoryIterator.next();
            categoriesList.add(ProductsCategories.valueOf(cat.getAsString()));
        }
        return categoriesList;
    }

    private PolicyPredicate parsePolicyPredicate(String predicateJson) throws ParseException, SupplyManagementException {

        PolicyPredicate outputPred;
        JsonObject gson = new JsonParser().parse(predicateJson).getAsJsonObject();

        JsonElement type1 = gson.get("type1");
        if(type1.isJsonNull()){
            throw  new ParseException("need to have at least one predicate");
        }
        PolicyPredicate predicate1 = parseOnePolicyPredicate(type1.getAsJsonObject());
        outputPred = predicate1;

        JsonElement op1 = gson.get("op1");
        JsonElement type2 = gson.get("type2");
        if(op1== null || type2== null){
            return predicate1;
        }
        PolicyPredicate predicate2 = parseOnePolicyPredicate(type2.getAsJsonObject());
        outputPred =parseOperatorForTwoPolicyPredicates(outputPred,predicate2,op1);


        JsonElement op2 = gson.get("op2");
        JsonElement type3 = gson.get("type3");
        if(op2 == null || type3 == null){
            return outputPred;
        }
        PolicyPredicate predicate3 = parseOnePolicyPredicate(type3.getAsJsonObject());
        outputPred =parseOperatorForTwoPolicyPredicates(outputPred,predicate3,op2);


        JsonElement op3 = gson.get("op3");
        JsonElement type4 = gson.get("type3");
        if(op3== null || type4== null){
            return outputPred;
        }
        PolicyPredicate predicate4 = parseOnePolicyPredicate(type4.getAsJsonObject());
        outputPred =parseOperatorForTwoPolicyPredicates(outputPred,predicate4,op3);


        return outputPred;
    }

    private PolicyPredicate parseOperatorForTwoPolicyPredicates(PolicyPredicate pred1, PolicyPredicate pred2, JsonElement operatorJson) throws ParseException {
        String opString = operatorJson.getAsString();
        switch (opString){
            case ("And"):
                return new PolicyPredicateAnd(pred1,pred2);
            case ("Or"):
                return new PolicyPredicateOr(pred1,pred2);
            case ("Xor"):
                return new PolicyPredicateXor(pred1,pred2);
        }
        throw  new ParseException("no operator with this type");
    }

    private PolicyPredicate parseOnePolicyPredicate(JsonObject predicateJson) throws SupplyManagementException, ParseException { //TODO: Amit
        JsonElement type = predicateJson.get("type");
        switch (type.getAsString()){
            case "cart":
                JsonElement jsonPrice = predicateJson.get("data1");
                int minNum = jsonPrice.getAsInt();
                return new CartPredicate(minNum);

            case "category":
                List<ProductsCategories> categoriesList = getProductsCategories(predicateJson);
                return new CategoryPredicate(categoriesList);

            case "productWithoutAmount":
                return getProductWithoutAmountPredicate(predicateJson);

            case "productWithAmount":
                return getProductWithAmountPredicate(predicateJson);

            case "userId":
                JsonElement userIdsJsonList = predicateJson.get("data1");

                Iterator<JsonElement> userIdIterator = userIdsJsonList.getAsJsonArray().iterator();
                List<String> userIds = new ArrayList<>();
                while (userIdIterator.hasNext()){
                    JsonElement cat = userIdIterator.next();
                    userIds.add(cat.getAsString());
                }
                return new UserPredicate(userIds, PredicateUserType.OnUserId);

            case "userAge":
                JsonElement startJson = predicateJson.get("data1");
                JsonElement endAgeJson = predicateJson.get("data2");
                return new UserPredicate(PredicateUserType.UserAge,startJson.getAsInt(),endAgeJson.getAsInt());

            case "OnHoursOfTheDay":
                JsonElement startDateJson = predicateJson.get("data1");
                JsonElement endDateJson = predicateJson.get("data2");
                String startDate = startDateJson.getAsString();
                String endDate = endDateJson.getAsString();
                return new TimePredicate(LocalDateTime.parse(startDate),LocalDateTime.parse(endDate), PredicateTimeType.OnHoursOfTheDay);
            case "OnDaysOfTheWeek":
                JsonElement startWeekDateJson = predicateJson.get("data1");
                JsonElement endWeekDateJson = predicateJson.get("data2");
                String startWeekDate = startWeekDateJson.getAsString();
                String endWeekDate = endWeekDateJson.getAsString();
                return new TimePredicate(LocalDateTime.parse(startWeekDate),LocalDateTime.parse(endWeekDate), PredicateTimeType.OnDaysOfTheWeek);
            case "OnDayOfMonth":
                JsonElement startMonthDateJson = predicateJson.get("data1");
                JsonElement endMonthDateJson = predicateJson.get("data2");
                String startMonthDate = startMonthDateJson.getAsString();
                String endMonthDate = endMonthDateJson.getAsString();
                return new TimePredicate(LocalDateTime.parse(startMonthDate),LocalDateTime.parse(endMonthDate), PredicateTimeType.OnDayOfMonth);

            case "price":
                return getPricePredicate(predicateJson);
            case ("AlwaysTrue"):
                return new AlwaysTrue();
            default:
                throw new ParseException("no predicate with this type");
        }
    }


}
