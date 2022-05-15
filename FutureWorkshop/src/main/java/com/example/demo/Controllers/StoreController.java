package com.example.demo.Controllers;


import com.example.demo.GlobalSystemServices.IdGenerator;
import com.example.demo.GlobalSystemServices.Log;
import com.example.demo.History.PurchaseHistory;
import com.example.demo.ShoppingCart.InventoryProtector;
import com.example.demo.Store.Product;
import com.example.demo.Store.Store;
import com.example.demo.Store.StoreState;
import com.example.demo.StorePermission.Permission;
import com.example.demo.StorePermission.StoreRoles;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

//todo add view history function
@RestController
public class StoreController {
    private ConcurrentHashMap<String, Store> stores; // storeId and the store

    public StoreController(ConcurrentHashMap<String, Store> stores) {
        this.stores = stores;
    }

    public StoreController(){
        stores = new ConcurrentHashMap<String, Store>();
    }

    public String openNewStore(String name,List<String> managers){

        if (managers.stream().anyMatch(this::checkIfGuest)) {
           throw new RuntimeException("guest cant do this action");
        }

        String newId = IdGenerator.getInstance().getStoreId();
        Store newStore= new Store(name, newId, managers);

        stores.put(newId, newStore);

        return newId;
    }

    private boolean checkIfGuest(String userId){
        return userId.startsWith("GuestID");
    }

    public void addNewProduct(String storeId, String userId, String productName, float price, int supply, String category) throws NoPermissionException {
        if(checkIfGuest(userId)){
            throw new RuntimeException("guest cant do this action");
        }
        Store relevantStore = stores.get(storeId);
        relevantStore.addNewProduct(userId, productName, price, supply, category);

    }

    public void unfreezeStore(String storeId, String userId) throws NoPermissionException {
        if(checkIfGuest(userId)){
            throw new RuntimeException("guest cant do this action");
        }
        Store relevantStore = stores.get(storeId);
        relevantStore.closeStore(userId);
    }

    public void freezeStore(String storeId, String userId) throws NoPermissionException {
        if(checkIfGuest(userId)){
            throw new RuntimeException("guest cant do this action");
        }
        Store relevantStore = stores.get(storeId);
        relevantStore.openStore(userId);
    }

    public List<StoreRoles> getInfoOnManagersOwners(String storeId, String userId) throws NoPermissionException {
        if(checkIfGuest(userId)){
            throw new RuntimeException("guest cant do this action");
        }
        Store relevantStore = stores.get(storeId);
        return relevantStore.getInfoOnManagersOwners(userId);
    }

    public void deleteStore(String userId, String storeId){
        if(!IdGenerator.getInstance().checkIfAdmin(userId)){
            throw new RuntimeException("only admin can do this action");
        }
        stores.remove(storeId);
    }

    

    public void editProduct(String storeId, String userId, String productId, int newSupply, String newName, float newPrice, String category) throws NoPermissionException {
        if(checkIfGuest(userId)){
            throw new RuntimeException("guest cant do this action");
        }
        Store relevantStore = stores.get(storeId);
        relevantStore.editProduct(userId, productId,  newSupply, newName, newPrice, category);
    }
    public void deleteProduct(String storeId, String userId, String productId) throws NoPermissionException {
        if(checkIfGuest(userId)){
            throw new RuntimeException("guest cant do this action");
        }
        Store relevantStore = stores.get(storeId);
        relevantStore.deleteProduct(userId, productId);
    }

    public void removeRoleInHierarchy(String storeId, String userIdRemoving, String UserAffectedId) throws NoPermissionException{
        Store relevantStore = stores.get(storeId);
        relevantStore.removeRoleInHierarchy(userIdRemoving, UserAffectedId);
    }
    public void removeRoleInHierarchy(String UserId) throws NoPermissionException{
        for (Store store : stores.values()) {
            store.removeRoleInHierarchy(UserId);
        }
    }

    public void removeSomePermissions(String storeId, String userIdRemoving, String UserAffectedId, List<String> PerToRemove) throws NoPermissionException {
        Store relevantStore = stores.get(storeId);
        relevantStore.removeSomePermissions(userIdRemoving,UserAffectedId, PerToRemove);
    }
    public void createOwner(String storeId, String userIdGiving, String UserGettingPermissionId, List<Permission> permissions) throws NoPermissionException {
        Store relevantStore = stores.get(storeId);
        relevantStore.createOwner(userIdGiving, UserGettingPermissionId, permissions);
    }
    public void createManager(String storeId, String userIdGiving, String UserGettingPermissionId) throws NoPermissionException {
        Store relevantStore = stores.get(storeId);
        relevantStore.createManager(userIdGiving, UserGettingPermissionId);
    }
    public void editPermissions(){
        //todo add option to edit permitions
    }
    public InventoryProtector getInventoryProtector(String storeId){
        Store relevantStore = stores.get(storeId);
        return relevantStore.getInventoryProtector();
    }



    public void addReviewToProduct(String storeId, String userId, String productId, String Title, String Body, float rating){
        if(checkIfGuest(userId)){
            throw new RuntimeException("guest cant do this action");
        }
        Store relevantStore = stores.get(storeId);
        relevantStore.addProductReview( userId, productId, Title, Body, rating);
    }

    public String addNewThreadToForum(String storeId,String title, String userId){
        if(checkIfGuest(userId)){
            throw new RuntimeException("guest cant do this action");
        }
        return stores.get(storeId).addNewThreadToForum(title, userId);
    }

    public void RolePostMessageToForum(String storeId, String threadId, String userId, String message) throws NoPermissionException {
        if(checkIfGuest(userId)){
            throw new RuntimeException("guest cant do this action");
        }
        stores.get(storeId).RolePostMessageToForum(threadId, userId, message);
    }
    public void  userPostMessageToForum(String storeId, String threadId, String userId, String message) throws NoPermissionException{
        Store relevantStore = stores.get(storeId);
        relevantStore.userPostMessageToForum(threadId, userId, message);
    }
    private boolean checkIfProductExists(String storeId, String productId) throws IOException {
        Store relevantStore =  stores.get(storeId);
        try {
            relevantStore.getProduct(productId);
            return true;
        } catch (Exception e) {
            Log.getLogger().logger.severe(e.getMessage());
            return false;
        }
    }
    /**return key=store, value=productList*/
    public HashMap<String,List<Product>> getAllProductsAndStores(){
        HashMap<String,List<Product>> ProductsAndStores = new HashMap<>();
        for (Store store : stores.values()) {
            if(store.getStoreState().equals(StoreState.ACTIVE)){
                ProductsAndStores.put(store.getId(),store.getAllProducts());

            }
        }
        return ProductsAndStores;
    }

    public List<Product> SearchProductsAccordingName(String productName){
        List<Product> filtered=  new ArrayList<>();
        for (Store store : stores.values()) {
            if(store.getStoreState().equals(StoreState.ACTIVE)) {
                filtered.addAll(store.getProductsNameContains(productName));
            }
        }
        return filtered;
    }

    public List<Product> SearchProductsAccordingCategory(List<String> categories ){
        List<Product> filtered=  new ArrayList<>();
        for (Store store : stores.values()) {
            if(store.getStoreState().equals(StoreState.ACTIVE)){
                filtered.addAll(store.getAllProductsCategory(categories));
            }
        }
        return filtered;

    }
    public List<Product> SearchProductsAccordingPrice(float fromPrice, float toPrice ){
        List<Product> filtered=  new ArrayList<>();
        for (Store store : stores.values()) {
            if(store.getStoreState().equals(StoreState.ACTIVE)){
                filtered.addAll(store.getProductsPriceContains(fromPrice, toPrice));
            }
        }
        return filtered;


    }
    public List<Product> SearchProductsAccordingRating(float productRating){
        List<Product> filtered=  new ArrayList<>();
        for (Store store : stores.values()) {
            if(store.getStoreState().equals(StoreState.ACTIVE)) {
                filtered.addAll(store.getAllProductsRating(productRating, 5f));
            }
        }
        return filtered;
    }
    public List<PurchaseHistory> getStoreHistory(String storeId, String userId) throws NoPermissionException{
        Store relevantStore = stores.get(storeId);
        return relevantStore.getStoreHistory(userId);
    }


}
