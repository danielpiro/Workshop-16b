package main.java.com.example.demo.Store.StorePurchase.predicates.PredImplementions;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import main.java.com.example.demo.Store.StorePurchase.PurchasableProduct;
import main.java.com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;
import main.java.com.example.demo.Store.StorePurchase.predicates.PredicateUserType;

import java.util.ArrayList;
import java.util.List;

public class UserPredicate implements PolicyPredicate {
    private List<String> userIds = new ArrayList<>(); // user id case
    private PredicateUserType type;
    private int startAge = -1; //for age case;
    private int endAge = -1; //for age case;

    public UserPredicate(PredicateUserType type, int startAge, int endAge) {
        this.type = type;
        this.startAge = startAge;
        this.endAge = endAge;
    }

    public UserPredicate(List<String> userIds, PredicateUserType type) {
        this.userIds = userIds;
        this.type = type;
    }

    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
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
