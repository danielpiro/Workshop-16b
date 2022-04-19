package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username;
    private String password;
    private boolean isLoggedIn;
    private HashMap<String, Integer> ShoppingCart;  // <productName, quantity>
    private String storeManagedByMe;

    private String storeOwnedByMe;
    private permission permissionLevel;
    public enum permission {Visitor, Buyer, Registered, ShopManager, ShopOwner, SystemManager, SystemFounder};

    public User(String username, String password, permission permissionLevel) {
        this.username = username;
        this.password = password;
        this.isLoggedIn = false;
        this.ShoppingCart = new HashMap<>();
        this.storeManagedByMe = null;
        this.storeOwnedByMe = null;
        this.permissionLevel = permissionLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public HashMap<String, Integer> getShoppingCart() {
        return ShoppingCart;
    }

    public void setShoppingCart(HashMap<String, Integer> shoppingCart) {
        ShoppingCart = shoppingCart;
    }

    public String getStoreManagedByMe() {
        return storeManagedByMe;
    }

    public void setStoreManagedByMe(String storeManagedByMe) {
        this.storeManagedByMe = storeManagedByMe;
    }

    public String getStoreOwnedByMe() {
        return storeOwnedByMe;
    }

    public void setStoreOwnedByMe(String storeOwnedByMe) {
        this.storeOwnedByMe = storeOwnedByMe;
    }

    public permission getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(permission permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

}
