package com.example.demo.Controllers.Mock;

import com.example.demo.StorePermission.Permission;

import javax.validation.constraints.NotNull;
import java.util.List;

public class MockSmallPermission {

    @NotNull
    private String storeId;

    @NotNull
    private String userIdGiving;

    @NotNull
    private String UserGettingPermissionId;

    @NotNull
    private List<Permission> permissions;

    public MockSmallPermission(@NotNull String storeId, @NotNull String userIdGiving, @NotNull String userGettingPermissionId, @NotNull List<Permission> permissions) {
        this.storeId = storeId;
        this.userIdGiving = userIdGiving;
        UserGettingPermissionId = userGettingPermissionId;
        this.permissions = permissions;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getUserIdGiving() {
        return userIdGiving;
    }

    public String getUserGettingPermissionId() {
        return UserGettingPermissionId;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
}
