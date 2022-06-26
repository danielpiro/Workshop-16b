package com.example.demo.StorePermission;


import com.example.demo.Database.DTOobjects.Store.Permissions.StoreRoleDTO;
import com.example.demo.Database.Service.DatabaseService;
import com.example.demo.GlobalSystemServices.IdGenerator;

import javax.naming.NoPermissionException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class  StoreRoles {
    private String userId ;
    protected List<Permission> storePermissions;
    protected List<StoreRoles> createPermissionsTo; //all the users the object gave permissions

    public StoreRoles(String userId,List<Permission> permissions){
        this.userId = userId;
        this.storePermissions = permissions;
        createPermissionsTo = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public abstract StoreOwnerRole createOwner(String userId, List<Permission> givePerm, String storeId, DatabaseService databaseService) throws NoPermissionException, SQLException;
    public abstract StoreManager createManager(String userId, String storeId, DatabaseService databaseService) throws NoPermissionException, SQLException;

    public List<Permission> getPermissions(){
        return Collections.unmodifiableList(storePermissions);
    }

    private List<String> removeAllRoles(){
        List<String> managersRemoved = new ArrayList<>();
        for (StoreRoles sr : createPermissionsTo) {
            managersRemoved.addAll(sr.removeAllRoles());
            managersRemoved.add(sr.getUserId());
        }
        return managersRemoved;
    }
    public List<String> removeRole(String userAffected){
        int indexToRemove = -1;
        for (int i = 0; i < createPermissionsTo.size(); i++){
            try{
                return createPermissionsTo.get(i).removeRole(userAffected);
            }
            catch (IllegalArgumentException e) {
                if (IdGenerator.getInstance().isIdEqual(
                        createPermissionsTo.get(i).getUserId(),
                        userAffected
                )
                ) {
                    indexToRemove = i;
                }
            }
        }
        if(indexToRemove == -1){
            throw new IllegalArgumentException("cant delete this user permission because userId- "+userId+" didn't gave him his permission");
        }
        List<String> managersIdsRemoved =createPermissionsTo.get(indexToRemove).removeAllRoles();
        managersIdsRemoved.add(createPermissionsTo.get(indexToRemove).getUserId());
        createPermissionsTo.remove(indexToRemove);

        return managersIdsRemoved;
    }

    private void removePermissionToRole(List<Permission> PerToRemove){
        for (StoreRoles sr : createPermissionsTo) {
            sr.removePermissionToRole(PerToRemove);

        }
        storePermissions.removeIf(x-> PerToRemove.stream().anyMatch(p-> p.equals(x)));
    }

    public void removePerToRole(String userAffected, List<Permission> PerToRemove){
        int indexToRemove = -1;
        for (int i = 0; i < createPermissionsTo.size(); i++){
            try{
                createPermissionsTo.get(i).removePerToRole(userAffected,  PerToRemove);
            }
            catch (IllegalArgumentException e) {
                if (IdGenerator.getInstance().isIdEqual(
                        createPermissionsTo.get(i).getUserId(),
                        userAffected
                )
                ) {
                    indexToRemove = i;
                }
            }
        }
        if(indexToRemove == -1){
            throw new IllegalArgumentException("cant delete this user permission because userId- "+userId+" didn't gave him his permission");
        }

        createPermissionsTo.get(indexToRemove).storePermissions.removeIf(x-> PerToRemove.stream().anyMatch(p-> p.equals(x)));//remove permissions from user

        createPermissionsTo.get(indexToRemove).removePermissionToRole(PerToRemove);//delete permission to the sub roles



    }

    public abstract String getTitle();

    public void saveInDb(String userIdCreatedMe,String StoreId, StoreRoleType storeRoleType, DatabaseService databaseService, List<Permission> permissions) throws SQLException {
        StoreRoleDTO storeRoleDTO = new StoreRoleDTO(userId,StoreId,storeRoleType.toString());
        databaseService.saveStoreRolePermissionAndSaveStoreRoleToStoreRoleAndSaveStoreRole(storeRoleDTO,permissions,userIdCreatedMe);
    }

    public void addToCreatedList(StoreRoles userToAdd) {
        createPermissionsTo.add(userToAdd);
    }
//    public void saveInDb(String StoreId, StoreRoleType storeRoleType, DatabaseService databaseService, List<Permission> permissions) throws SQLException {
//        StoreRoleDTO storeRoleDTO = new StoreRoleDTO(userId,StoreId,storeRoleType.toString());
//        databaseService.saveStoreRolePermissionAndSaveStoreRole(storeRoleDTO,permissions);
//    }

}
