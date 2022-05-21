package main.java.com.example.demo.Store.StorePurchase.predicates.PredImplementions;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.Product;
import main.java.com.example.demo.Store.StorePurchase.PurchasableProduct;
import main.java.com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import main.java.com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;
import main.java.com.example.demo.Store.StorePurchase.predicates.PredicateProductType;

import java.util.HashMap;
import java.util.List;

public class ProductPredicate implements PolicyPredicate, DiscountPredicate {
    HashMap<PurchasableProduct,Integer> products;
    PredicateProductType type;
    public ProductPredicate(List<PurchasableProduct> products){
        type = PredicateProductType.General;
        this.products = new HashMap<>();
        for(PurchasableProduct p: products){
          this.products.put(p,0);
        }
    }
    public ProductPredicate(HashMap<PurchasableProduct,Integer> products){
        type = PredicateProductType.Products_Above_Amount;
        this.products = products;

    }
    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        switch (type){
            case General:
                return productWithoutAmount(ProductAmount);
            case Products_Above_Amount:
                return productWithAmount(ProductAmount);
        }
        throw new RuntimeException("PredicateProductType doesn't exist ");
    }
    public boolean productWithoutAmount(List<PurchasableProduct> ProductAmount){
        return ProductAmount.stream().anyMatch(
                product -> products.keySet().stream().anyMatch(
                        productsCantBuy -> productsCantBuy.getId().equals(product.getId())
                ));
    }
    public boolean productWithAmount(List<PurchasableProduct> ProductAmount){
        for (PurchasableProduct p1:ProductAmount){
            for(PurchasableProduct p2: products.keySet()){
                if(p1.getId().equals(p2.getId()) && p1.getAmount()<p2.getAmount()){
                    return false;
                }
            }
        }
        return true;

    }


}
