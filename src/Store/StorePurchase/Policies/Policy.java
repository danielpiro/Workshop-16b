package Store.StorePurchase.Policies;

import ExternalConnections.ExternalConnectionHolder;
import GlobalSystemServices.IdGenerator;
import ShoppingCart.UserInfo;
import Store.Product;

import java.util.HashMap;

public abstract class Policy {
    private String policyId;

    public Policy() {
        this.policyId = IdGenerator.getInstance().getPolicyId();
    }

    public abstract boolean checkIfPolicyStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo);
    public String getPolicyId(){
        return policyId;
    }


}
