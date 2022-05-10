package Store.Policies;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.Product;

import java.util.HashMap;

public class AndGatePolicy implements Policy {
    Policy left;
    Policy right;

    public AndGatePolicy(Policy left, Policy right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean checkIfPolicyStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        return left.checkIfPolicyStands(ProductAmount,externalConnectionHolder,userInfo) && right.checkIfPolicyStands(ProductAmount,externalConnectionHolder,userInfo);
    }
}
