package Store.DiscountAndPolicies;

import Store.ProductsCategories;

import java.util.List;

public class Policy {
    Predicate policyPredicate;
    List<ProductsCategories> categories;
    List<String> productsIds;


    public boolean checkIfPolicyStands(){
        return true;
    }
}
