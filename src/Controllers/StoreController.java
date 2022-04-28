package Controllers;

import GlobalSystemServices.IdGenerator;
import GlobalSystemServices.Log;
import ShoppingCart.InventoryProtector;
import Store.Store;
import StorePermission.Permission;
import StorePermission.StoreRoles;
import Views.ProductView;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreController {
    private HashMap<String,Store> stores; // storeId and the store

    public StoreController(HashMap<String, Store> stores) {
        this.stores = stores;
    }

    public StoreController(){
        stores = new HashMap<String, Store>();
    }

    public String openNewStore(String name,List<String> managers){
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

    public void closeStore(String storeId, String userId) throws NoPermissionException {
        if(checkIfGuest(userId)){
            throw new RuntimeException("guest cant do this action");
        }
        Store relevantStore = stores.get(storeId);
        relevantStore.closeStore(userId);
    }

    public void openStore(String storeId, String userId) throws NoPermissionException {
        if(checkIfGuest(userId)){
            throw new RuntimeException("guest cant do this action");
        }
        Store relevantStore = stores.get(storeId);
        relevantStore.openStore(userId);
    }

    public List<StoreRoles> getInfoOnManagers(String storeId, String userId) throws NoPermissionException {
        if(checkIfGuest(userId)){
            throw new RuntimeException("guest cant do this action");
        }
        Store relevantStore = stores.get(storeId);
        return relevantStore.getInfoOnManagers(userId);
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
        relevantStore.editProductSupply(userId, productId,  newSupply, newName, newPrice, category);
    }
    public void deleteProduct(String storeId, String userId, String productId) throws NoPermissionException {
        if(checkIfGuest(userId)){
            throw new RuntimeException("guest cant do this action");
        }
        Store relevantStore = stores.get(storeId);
        relevantStore.deleteProduct(userId, productId);
    }

    public void removePermissionTo(String storeId, String userIdRemoving,String UserAffectedId) throws NoPermissionException{
        Store relevantStore = stores.get(storeId);
        relevantStore.removePermissionTo(userIdRemoving, UserAffectedId);
    }
    public void removeAllPermissionTo(String UserId) throws NoPermissionException{
        for (Store store : stores.values()) {
            store.removePermissionTo(UserId);
        }
    }
    public void givePermissionTo(String storeId, String userIdGiving,String UserGettingPermissionId,List<Permission> permissions) throws NoPermissionException {
        Store relevantStore = stores.get(storeId);
        relevantStore.givePermissionTo(userIdGiving, UserGettingPermissionId, permissions);
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

    public void postMessageToForum(String storeId, String threadId, String userId, String message) throws NoPermissionException {
        if(checkIfGuest(userId)){
            throw new RuntimeException("guest cant do this action");
        }
        stores.get(storeId).postMessageToForum(threadId, userId, message);
    }

    public boolean checkIfProductExists(String storeId, String productId) throws IOException {
        Store relevantStore =  stores.get(storeId);
        try {
            relevantStore.getProduct(productId);
            return true;
        } catch (Exception e) {
            Log.getLogger().logger.severe(e.getMessage());
            return false;
        }
    }

    public HashMap<String,List<ProductView>> getAllProductsAndStores(){
        HashMap<String,List<ProductView>> ProductsAndStores = new HashMap<>();
        for (Store store : stores.values()) {
            ProductsAndStores.put(store.getId(),store.getAllProducts());
        }
        return ProductsAndStores;
    }

    public List<ProductView> SearchProductsAccordingName(String productName){
        List<ProductView> filtered=  new ArrayList<>();
        for (Store store : stores.values()) {
            filtered.addAll(store.getProductsNameContains(productName));
        }
        return filtered;
    }

    public List<ProductView> SearchProductsAccordingCategory(List<String> categories ){
        List<ProductView> filtered=  new ArrayList<>();
        for (Store store : stores.values()) {
            filtered.addAll(store.getAllProductsCategory(categories));
        }
        return filtered;

    }
    public List<ProductView> SearchProductsAccordingPrice( float fromPrice, float toPrice ){
        List<ProductView> filtered=  new ArrayList<>();
        for (Store store : stores.values()) {
            filtered.addAll(store.getProductsPriceContains(fromPrice, toPrice));
        }
        return filtered;


    }
    public List<ProductView> SearchProductsAccordingRating(float productRating){
        List<ProductView> filtered=  new ArrayList<>();
        for (Store store : stores.values()) {
            filtered.addAll(store.getAllProductsRating(productRating, 5f));
        }
        return filtered;
    }



}
