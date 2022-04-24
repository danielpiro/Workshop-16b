package StorePermission;

import GlobalSystemServices.IdGenerator;

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
    }

    public String getUserId() {
        return userId;
    }
    public abstract StoreManagerRole createAnotherManager(String userId,List<Permission> givePerm);
    public List<Permission> getPermissions(){
        return Collections.unmodifiableList(storePermissions);
    }

    public List<String> removeAllManagers(){
        List<String> managersRemoved = new ArrayList<>();
        for (StoreRoles sr :
                createPermissionsTo) {
            managersRemoved.addAll(removeManager(sr.getUserId()));
        }
        return managersRemoved;
    }
    public List<String> removeManager(String userAffected){
        int indexToRemove = -1;
        for (int i = 0; i < createPermissionsTo.size(); i++){
            if(IdGenerator.getInstance().isIdEqual(
                    createPermissionsTo.get(i).getUserId(),
                    userAffected)
                ){
                indexToRemove = i;
            }
        }
        if(indexToRemove == -1){
            throw new IllegalArgumentException("cant delete this user permission because userId- "+userId+" didnt gave him his permission");
        }
        List<String> managersIdsRemoved =createPermissionsTo.get(indexToRemove).removeAllManagers();
        managersIdsRemoved.add(createPermissionsTo.get(indexToRemove).getUserId());

        createPermissionsTo.remove(indexToRemove);

        return managersIdsRemoved;
    }
}
