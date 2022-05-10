package Store.Policies;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.Policies.predicates.Predicate;
import Store.Product;

import java.util.HashMap;



public class TwoPredPolicy implements Policy {
    Predicate pPredicate;
    Predicate qPredicate;

    public TwoPredPolicy(Predicate pPredicate, Predicate qPredicate) {
        this.pPredicate = pPredicate;
        this.qPredicate = qPredicate;
    }

    //if IfPredicate holds it means you cant do ThenPredicate. so if the function return true you are violating store policy
    public boolean checkIfPolicyStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo){
        return pPredicate.predicateStands(ProductAmount,externalConnectionHolder,userInfo) && qPredicate.predicateStands(ProductAmount,externalConnectionHolder,userInfo);
    }

}
