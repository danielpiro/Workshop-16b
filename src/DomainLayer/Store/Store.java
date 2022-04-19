package DomainLayer.Store;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreManager;
import DomainLayer.Roles.StoreManager;
import DomainLayer.Roles.StoreOwner;

import java.util.ArrayList;
import java.util.List;

public class Store {


    private PurchasePolicy purchasePolicy;
    private DiscountPolicy discountPolicy;
    private String name;
    private boolean is_open = true;
    private List<Product> product_list = new ArrayList<>();
    private List<PurchaseProcess> purchase_process_list = new ArrayList<>();
    private List<Role> roles = new ArrayList<>();

    public Store(String name) {
        this.name = name;
    }

    public PurchasePolicy getPurchasePolicy() {
        return purchasePolicy;
    }

    public void setPurchasePolicy(PurchasePolicy purchasePolicy) {
        this.purchasePolicy = purchasePolicy;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_open() {
        return is_open;
    }

    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
    }

    public List<Product> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<Product> product_list) {
        this.product_list = product_list;
    }

    public List<PurchaseProcess> getPurchase_process_list() {
        return purchase_process_list;
    }

    public void setPurchase_process_list(List<PurchaseProcess> purchase_process_list) {
        this.purchase_process_list = purchase_process_list;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    public void removeRole(List<Role> roles) {
        this.roles = roles;
    }
    public Product getProduct(String product_name){
        Product product = null;
        for(Product p : product_list){
            if(product_name.equals(p.getName())){
                product = p;
            }
        }
        return product;
    }

    public StoreOwner find_store_owner_by_name(String user_name){
        for(Role role : roles){
            if(role instanceof StoreOwner && ((StoreOwner)role).user.getName().equals(user_name)) return (StoreOwner) role;
        }
        return null;
    }

    public StoreManager find_store_manager_by_name(String user_name){
        for(Role role : roles){
            if(role instanceof StoreManager && ((StoreManager)role).user.getName().equals(user_name)) return (StoreManager) role;
        }
        return null;
    }

}
