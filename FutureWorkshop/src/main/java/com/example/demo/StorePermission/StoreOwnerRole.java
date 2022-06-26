package com.example.demo.StorePermission;

import com.example.demo.Database.Service.DatabaseService;

import javax.naming.NoPermissionException;
import java.sql.SQLException;
import java.util.List;

public class StoreOwnerRole extends StoreRoles{

    public StoreOwnerRole(String userId, List<Permission> permissions) {
        super(userId, permissions);
    }


    public StoreOwnerRole createOwner(String userId, List<Permission> givePerm, String storeId, DatabaseService databaseService) throws SQLException {
        for (Permission Perm: givePerm) {
            if(!storePermissions.contains(Perm)){
                throw new IllegalArgumentException("this owner"+ userId +" don't have the permission he is giving");
            }
        }
        StoreOwnerRole newOwner = new StoreOwnerRole(userId, givePerm);
        createPermissionsTo.add(newOwner);
        newOwner.saveInDb(this.getUserId(),storeId,StoreRoleType.owner,databaseService,givePerm);
        return newOwner;
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
        return StoreRoleType.owner.toString();
    }
}
