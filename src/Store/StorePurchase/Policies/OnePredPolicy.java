package Store.StorePurchase.Policies;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.StorePurchase.PurchasableProduct;
import Store.StorePurchase.predicates.PolicyPredicate;
import Store.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OnePredPolicy extends Policy {
    PolicyPredicate pPredicate;


    public OnePredPolicy(PolicyPredicate pPredicate) {
        super();
        this.pPredicate = pPredicate;
        }

    //if ThenPredicate holds function return true you are violating store policy
    public boolean checkIfPolicyStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo){
        List<PurchasableProduct> PurchasableAmount = new ArrayList<>();
        for(Product p:ProductAmount.keySet()){
            PurchasableAmount.add(new ProductBox(p,ProductAmount.get(p)));
        }
        return pPredicate.predicateStands(PurchasableAmount,externalConnectionHolder,userInfo);
    }
}
