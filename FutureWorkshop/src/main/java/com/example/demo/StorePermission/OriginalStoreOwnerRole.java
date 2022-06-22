package com.example.demo.StorePermission;

import com.example.demo.Database.Service.DatabaseService;

import javax.naming.NoPermissionException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OriginalStoreOwnerRole extends StoreRoles{
    public OriginalStoreOwnerRole(String userId) {
        super(userId, new ArrayList<>());
        List<Permission> allPerm = Arrays.asList(Permission.values());
        storePermissions.addAll(allPerm);

        //save role
    }

    public StoreOwnerRole createOwner(String userId, List<Permission> givePerm, String storeId, DatabaseService databaseService) throws SQLException {
        for (Permission p
                : givePerm) {
            if (p == Permission.CLOSE_STORE  ||  p == Permission.OPEN_STORE) {
                throw new RuntimeException("only store manager can have permission to close store");
            }
        }
        StoreOwnerRole newManager = new StoreOwnerRole(userId,givePerm);
        createPermissionsTo.add(newManager);
        newManager.saveInDb(this.getUserId(),storeId,StoreRoleType.owner,databaseService,givePerm);
        return newManager;
    }

    @Override
    public StoreManager createManager(String userId, String storeId, DatabaseService databaseService) throws NoPermissionException, SQLException {
        StoreManager newStoreManager = new StoreManager(userId);
        createPermissionsTo.add(newStoreManager);
        newStoreManager.saveInDb(this.getUserId(),storeId,StoreRoleType.manager,databaseService,newStoreManager.getPermissions());
        return newStoreManager;
    }

    @Override
    public String getTitle() {
        return StoreRoleType.original_owner.toString();
    }
}
