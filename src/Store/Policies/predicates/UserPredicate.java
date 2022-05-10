package Store.Policies.predicates;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserPredicate implements Predicate{
    private List<String> userIds = new ArrayList<>(); // user id case
    private PredicateUserType type;
    private int startAge = -1; //for age case;
    private int endAge = -1; //for age case;

    public UserPredicate(List<String> userIds, PredicateUserType type, int startAge, int endAge) {
        this.userIds = userIds;
        this.type = type;
        this.startAge = startAge;
        this.endAge = endAge;
    }

    @Override
    public boolean predicateStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        switch (type){
            case OnUserId:
                return OnUserId(userInfo);
            case UserAge:
                return UserAge(userInfo);

        }
        throw new RuntimeException("PredicateUserType doesn't exist ");
    }

    private boolean OnUserId(UserInfo userInfo) {
        return userIds.stream().anyMatch(user -> user.equals(userInfo.getUserId()));
    }

    private boolean UserAge(UserInfo userInfo) {
        return startAge<=userInfo.getAge() && userInfo.getAge()<= endAge;
    }

}
