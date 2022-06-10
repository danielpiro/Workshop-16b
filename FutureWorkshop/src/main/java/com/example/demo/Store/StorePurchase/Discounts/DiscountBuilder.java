package com.example.demo.Store.StorePurchase.Discounts;

import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.Store.Product;
import com.example.demo.Store.ProductsCategories;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.AlwaysTrue;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.CategoryPredicate;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.PricePredicate;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.ProductPredicate;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite.DiscountPredicateAnd;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite.DiscountPredicateOr;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite.DiscountPredicateXor;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.apache.tomcat.util.json.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class DiscountBuilder {

    public PercentageDiscount newPercentageDiscount(float discountPercent,String predicateJson) throws ParseException, SupplyManagementException {
        return new PercentageDiscount(discountPercent, parseProductPredicate(predicateJson));
    }
    public ConditionalPercentageDiscount newConditionalDiscount(float discountPercent,String cartPredicateJson, String productPredicateJson) throws ParseException, SupplyManagementException {
        PercentageDiscount percentageDiscount = new PercentageDiscount(discountPercent,parseProductPredicate(productPredicateJson));
        return new ConditionalPercentageDiscount(percentageDiscount,parseCartPredicate(cartPredicateJson));
    }
    public AdditionDiscount newAdditionDiscount(  PercentageDiscount first,   PercentageDiscount second){
        return newAdditionDiscount(first,second);
    }

    public MaxDiscount newMaxDiscount( PercentageDiscount first,   PercentageDiscount second){
        return newMaxDiscount(first,second);
    }

    public DiscountPredicate parseProductPredicate(String predicateJson) throws ParseException, SupplyManagementException {
        DiscountPredicate outputPred;
        JsonObject gson = new JsonParser().parse(predicateJson).getAsJsonObject();

        JsonElement type1 = gson.get("type1");
        if(type1.isJsonNull()){
            throw  new ParseException("need to have at least one predicate");
        }
        DiscountPredicate predicate1 = parseOnePredicate(type1.getAsJsonObject());
        outputPred = predicate1;

        JsonElement op1 = gson.get("op1");
        JsonElement type2 = gson.get("type2");
        if(op1== null || type2== null){
            return outputPred;
        }
        DiscountPredicate predicate2 = parseOnePredicate(type2.getAsJsonObject());
        outputPred =parseOperatorForTwoPredicates(outputPred,predicate2,op1);


        JsonElement op2 = gson.get("op2");
        JsonElement type3 = gson.get("type3");
        if(op2 == null || type3 == null){
            return outputPred;
        }
        DiscountPredicate predicate3 = parseOnePredicate(type3.getAsJsonObject());
        outputPred =parseOperatorForTwoPredicates(outputPred,predicate3,op2);


        JsonElement op3 = gson.get("op3");
        JsonElement type4 = gson.get("type3");
        if(op3== null || type4== null){
            return outputPred;
        }
        DiscountPredicate predicate4 = parseOnePredicate(type4.getAsJsonObject());
        outputPred =parseOperatorForTwoPredicates(outputPred,predicate4,op3);


        return outputPred;

    }
    private DiscountPredicate parseOperatorForTwoPredicates(DiscountPredicate pred1, DiscountPredicate pred2, JsonElement operatorJson) throws ParseException {
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

    private DiscountPredicate parseOnePredicate(JsonObject predicateJson ) throws SupplyManagementException, ParseException {
        JsonElement type = predicateJson.get("type");
        switch (type.getAsString()){
            case ("category"):
                JsonElement categoriesJsonList = predicateJson.get("data1");

                Iterator<JsonElement> categoryIterator = categoriesJsonList.getAsJsonArray().iterator();
                List<ProductsCategories> categoriesList = new ArrayList<>();
                while (categoryIterator.hasNext()){
                    JsonElement cat = categoryIterator.next();
                    categoriesList.add(ProductsCategories.valueOf(cat.getAsString()));
                }
                return new CategoryPredicate(categoriesList);

            case ("price"):
                JsonElement jsonPrice = predicateJson.get("data1");
                float price = jsonPrice.getAsFloat();
                return new PricePredicate(price);

            case ("productWithoutAmount"):
                JsonElement productsJsonList = predicateJson.get("data1");

                Iterator<JsonElement> productsIterator = productsJsonList.getAsJsonArray().iterator();
                List<PurchasableProduct> productsList = new ArrayList<>();
                while (productsIterator.hasNext()){
                    JsonElement priceElement = productsIterator.next();
                    productsList.add(new Product(priceElement.getAsString(),"notRealProduct",0,0,ProductsCategories.Other.toString()));
                }
                return new ProductPredicate(productsList);

            case ("productWithAmount"):
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

            case ("Always"):
                return new AlwaysTrue();

        }
        throw new ParseException("no predicate with this type");
    }
    private PolicyPredicate parseCartPredicate(String predicateJson){
        return new AlwaysTrue();
    }


}
