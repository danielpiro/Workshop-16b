package Store.Policies;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.Policies.predicates.IfPredicate;
import Store.Policies.predicates.ThenPredicate;
import Store.Product;

import java.util.HashMap;



public class Policy {
    IfPredicate pPredicate;
    ThenPredicate qPredicate;




    //if pPredicate holds it means you cant do qPredicate. so if the function return true you are violating store policy
    public boolean checkIfPolicyStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo){
        return pPredicate.predicateStands(ProductAmount,externalConnectionHolder,userInfo) && !qPredicate.predicateStands(ProductAmount,externalConnectionHolder,userInfo);
    }

}
