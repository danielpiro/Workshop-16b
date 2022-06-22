package com.example.demo.StorePermission;

import com.example.demo.Database.Service.DatabaseService;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.List;

public class StoreManager extends StoreRoles {
    public StoreManager(String userId) {
        super(userId, new ArrayList<>());
        storePermissions.add(Permission.VIEW_STORE_HISTORY);
        storePermissions.add(Permission.REPLY_TO_FORUM);
        storePermissions.add(Permission.VIEW_FORUM);
    }

    @Override
    public StoreOwnerRole createOwner(String userId, List<Permission> givePerm, String storeId, DatabaseService databaseService) throws NoPermissionException {
        throw new NoPermissionException("manager cant give permissions");
    }

    @Override
    public StoreManager createManager(String userId, String storeId, DatabaseService databaseService) throws NoPermissionException {
        throw new NoPermissionException("manager cant give permissions");
    }

    @Override
    public String getTitle() {
        return StoreRoleType.manager.toString();
    }


}
