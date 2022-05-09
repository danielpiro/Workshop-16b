package Store.Policies.predicates;




import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.Product;

import java.time.LocalDateTime;
import java.util.HashMap;

public class OnTimeDaysOfWeekPredicate implements IfPredicate {



    private LocalDateTime StartTime;
    private LocalDateTime endTime;

    @Override
    public boolean predicateStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        int today = LocalDateTime.now().getDayOfWeek().getValue();
        return today >= StartTime.getDayOfWeek().getValue() &&  today <= endTime.getDayOfWeek().getValue() ;
    }

}
