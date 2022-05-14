package Store.StorePurchase.Policies;

import Store.StorePurchase.predicates.*;
import Store.Product;
import Store.ProductsCategories;
import Store.StorePurchase.predicates.PredImplementions.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
// todo add the missing policies
public class PolicyBuilder {

    /**
     * @param numOfProducts - minimum products in cart
     * @return policy
     */
    public Policy newCartPolicy(int numOfProducts){
        PolicyPredicate p=new CartPredicate(numOfProducts);
        Policy policy = new OnePredPolicy(p);
        return policy;
    }
    /**
     * @param categories - categories that cant buy in store
     * @return policy
     */
    public Policy newCategoryPolicy(List<ProductsCategories> categories){
        PolicyPredicate p=new CategoryPredicate(categories);
        Policy policy = new OnePredPolicy(p);
        return policy;
    }

    /**
     * @param products - products in store you cant buy
     * @return policy
     */
    public Policy newProductWithoutAmountPolicy(List<Product> products){
        PolicyPredicate p = new ProductPredicate(products);
        Policy policy  = new OnePredPolicy(p);
        return policy;
    }
    /**
     * @param products - products in store you cant buy
     * @return policy
     */
    public Policy newProductWithAmountPolicy(HashMap<Product,Integer> products){
        PolicyPredicate p = new ProductPredicate(products);
        Policy policy  = new OnePredPolicy(p);
        return policy;
    }

    /**
     * @param userIds - users cant buy in store
     * @return policy
     */
    public Policy newUserIdPolicy(List<String> userIds){
        PolicyPredicate p = new UserPredicate(userIds,PredicateUserType.OnUserId);
        Policy policy  = new OnePredPolicy(p);
        return policy;
    }


    /**
     * @implNote  user cant buy between age startAge to endAge
     * @return policy
     */
    public Policy newUseAgePolicy(int startAge, int endAge){
        PolicyPredicate p = new UserPredicate(PredicateUserType.UserAge, startAge,endAge);
        Policy policy  = new OnePredPolicy(p);
        return policy;
    }

    /**
     * @param startTime - cant buy from hour
     * @param endTime - cant buy until hour
     * @return policy
     */
    public Policy newOnHoursOfTheDayPolicy(LocalDateTime startTime, LocalDateTime endTime){
        PolicyPredicate p = new TimePredicate(startTime,endTime,PredicateTimeType.OnHoursOfTheDay);
        Policy policy  = new OnePredPolicy(p);
        return policy;
    }

    /**
     * @param startTime - cant buy from day on week
     * @param endTime - cant buy until day on  week
     * @return policy
     */
    public Policy newOnDaysOfTheWeekPolicy(LocalDateTime startTime, LocalDateTime endTime){
        PolicyPredicate p = new TimePredicate(startTime,endTime,PredicateTimeType.OnDaysOfTheWeek);
        Policy policy  = new OnePredPolicy(p);
        return policy;
    }

    /**
     * @param startTime - cant buy from day on month
     * @param endTime - cant buy until day on  month
     * @return policy
     */
    public Policy newOnDayOfMonthPolicy(LocalDateTime startTime, LocalDateTime endTime){
        PolicyPredicate p = new TimePredicate(startTime,endTime,PredicateTimeType.OnDayOfMonth);
        Policy policy  = new OnePredPolicy(p);
        return policy;
    }


    /**
     * @param p1 - first policy
     * @param p2 - second policy
     * @return
     */
    public Policy AndGatePolicy(Policy p1,Policy p2){
        Policy policy  = new AndGatePolicy(p1,p2);
        return policy;
    }

    /**
     * @param p1 - first policy
     * @param p2 - second policy
     * @return
     */
    public Policy OrGatePolicy(Policy p1,Policy p2){
        Policy policy  = new OrGatePolicy(p1,p2);
        return policy;
    }


    /**
     * @param p1 - first policy
     * @param p2 - second policy
     * @return
     */
    public Policy ConditioningGatePolicy(Policy p1,Policy p2){
        Policy policy  = new conditioningPolicy(p1,p2);
        return policy;
    }





}
