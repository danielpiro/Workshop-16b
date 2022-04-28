package com.company;

import java.util.HashMap;

public class Store {
    private String storeName;
    private HashMap<Product, Integer> products; // <ProductName+ProductPrice, Quantity>
    private String policy;
    private boolean isOpen;
    private HashMap<String, User.permission> storeOfficials;

    private HashMap<Product, Integer> purchaseHistory;

    public Store(String storeName, HashMap<Product, Integer> products) {
        this.storeName = storeName;
        this.products = products;
        this.policy = "Regular policy";
        this.isOpen = true;
        this.storeOfficials = new HashMap<>();
        this.purchaseHistory = new HashMap<>();
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Product, Integer> products) {
        this.products = products;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public HashMap<String, User.permission> getStoreOfficials() {
        return storeOfficials;
    }

    public void setStoreOfficials(HashMap<String, User.permission> storeOfficials) {
        this.storeOfficials = storeOfficials;
    }

    public HashMap<Product, Integer> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(HashMap<Product, Integer> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }
}
