package Controllers;

import GlobalSystemServices.IdGenerator;
import Store.Store;
import StorePermission.Permission;
import StorePermission.StoreRoles;
import Views.ProductView;

import javax.naming.NoPermissionException;
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

    public void openNewStore(String name,List<String> managers){
        String newId = IdGenerator.getInstance().getStoreId();
        Store newStore= new Store(name, newId, managers);
        stores.put(newId, newStore);
    }

//




    public void addNewProduct(String storeId, String userId, String productName, float price, int supply, String category) throws NoPermissionException {
        Store relevantStore = stores.get(storeId);
        relevantStore.addNewProduct(userId, productName, price, supply, category);

    }

    public void closeStore(String storeId, String userId) throws NoPermissionException {
        Store relevantStore = stores.get(storeId);
        relevantStore.closeStore(userId);
    }

    public void openStore(String storeId, String userId) throws NoPermissionException {
        Store relevantStore = stores.get(storeId);
        relevantStore.openStore(userId);
    }

    public List<StoreRoles> getInfoOnManagers(String storeId, String userId) throws NoPermissionException {
        Store relevantStore = stores.get(storeId);
        return relevantStore.getInfoOnManagers(userId);
    }

    public void deleteStore(){
        //TODO only the system admin can do this op
        //Admin_(num 1..2..3..4...)
    }



    public void editProduct(String storeId, String userId, String productId, int newSupply, String newName, float newPrice, String category) throws NoPermissionException {
        Store relevantStore = stores.get(storeId);
        relevantStore.editProductSupply(userId, productId,  newSupply, newName, newPrice, category);
    }
    public void deleteProduct(String storeId, String userId, String productId) throws NoPermissionException {
        Store relevantStore = stores.get(storeId);
        relevantStore.deleteProduct(userId, productId);
    }

    public void removePermissionTo(String storeId, String userIdRemoving,String UserAffectedId) throws NoPermissionException{
        Store relevantStore = stores.get(storeId);
        relevantStore.removePermissionTo(userIdRemoving, UserAffectedId);
    }
    public void givePermissionTo(String storeId, String userIdGiving,String UserGettingPermissionId,List<Permission> permissions) throws NoPermissionException {
        Store relevantStore = stores.get(storeId);
        relevantStore.givePermissionTo(userIdGiving, UserGettingPermissionId, permissions);
    }




    public void addReviewToProduct(String storeId, String userId, String productId, String Title, String Body, float rating){
        Store relevantStore = stores.get(storeId);
        relevantStore.addProductReview( userId, productId, Title, Body, rating);
    }

    public String addNewThreadToForum(String storeId,String title, String userId){
        return stores.get(storeId).addNewThreadToForum(title, userId);
    }

    public void postMessageToForum(String storeId, String threadId, String userId, String message) throws NoPermissionException {
        stores.get(storeId).postMessageToForum(threadId, userId, message);
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
