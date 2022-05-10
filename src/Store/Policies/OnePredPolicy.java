package Store.Policies;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.Policies.predicates.Predicate;
import Store.Product;

import java.util.HashMap;

public class OnePredPolicy implements Policy {
    Predicate pPredicate;


    public OnePredPolicy(Predicate pPredicate) {
        this.pPredicate = pPredicate;
        }

    //if ThenPredicate holds function return true you are violating store policy
    public boolean checkIfPolicyStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo){
        return pPredicate.predicateStands(ProductAmount,externalConnectionHolder,userInfo);
    }
}
