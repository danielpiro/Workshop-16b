package com.example.demo.Store.StorePurchase.predicates.PredImplementions;


import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;
import com.example.demo.Store.StorePurchase.predicates.PredicateProductType;


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
        boolean output = true;
        for (PurchasableProduct p1:ProductAmount){
            for(PurchasableProduct p2: products.keySet()){
                if(p1.getId().equals(p2.getId()) && p1.getAmount()<p2.getAmount()){
                    output = false;
                }
            }
        }
        return output;

    }


    @Override
    public boolean predicateStandsForProduct(PurchasableProduct ProductAmount) {
        switch (type){
            case General:
                return productWithoutAmount(ProductAmount);
            case Products_Above_Amount:
                return productWithAmount(ProductAmount);
        }
        throw new RuntimeException("PredicateProductType doesn't exist ");
    }

    private boolean productWithAmount(PurchasableProduct productAmount) {

        for(PurchasableProduct p2: products.keySet()){
            if(productAmount.getId().equals(p2.getId()) && productAmount.getAmount()<p2.getAmount()){
                return false;
            }
        }
        return true;
    }

    private boolean productWithoutAmount(PurchasableProduct productAmount) {
        return products.keySet().stream().anyMatch(
                productsCantBuy -> productsCantBuy.getId().equals(productAmount.getId())
        );
    }
}
