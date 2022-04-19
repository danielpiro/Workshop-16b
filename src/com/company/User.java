package com.company;

public class User {
    private String username;
    private String password;
    private permission permissionLevel;
    public enum permission {Visitor, Buyer, Registered, ShopManager, ShopOwner, SystemManager, SystemFounder};

    public User(String username, String password, permission permissionLevel) {
        this.username = username;
        this.password = password;
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

    public permission getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(permission permissionLevel) {
        this.permissionLevel = permissionLevel;
    }
}
