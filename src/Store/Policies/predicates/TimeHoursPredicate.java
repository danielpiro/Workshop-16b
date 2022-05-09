package Store.Policies.predicates;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.Product;

import java.time.LocalTime;
import java.util.HashMap;

public class TimeHoursPredicate implements IfPredicate {
    private LocalTime StartTime;
    private LocalTime endTime;

    @Override
    public boolean predicateStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        LocalTime now = LocalTime.now();
        return now.isAfter(StartTime)  &&  now.isBefore(endTime) ;
    }
}
