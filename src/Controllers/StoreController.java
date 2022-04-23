package Controllers;

import GlobalSystemServices.IdGenerator;
import Store.Store;
import StorePermissin.Permission;
import Views.ProductView;

import javax.naming.NoPermissionException;
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


    public HashMap<String,List<ProductView>> getAllProductsAndStores(){
        HashMap<String,List<ProductView>> ProductsAndStores = new HashMap<>();
        for (Store store :
                stores.values()) {
            ProductsAndStores.put(store.getId(),store.getAllProducts());
        }
        return ProductsAndStores;
    }

    public void addNewProduct(String storeId, String userId, String productName, float price, int supply) throws NoPermissionException {
        Store relevantStore = stores.get(storeId);
        relevantStore.addNewProduct(userId, productName, price, supply);

    }



    public void editProduct(String storeId, String userId, String productId, int newSupply, String newName, float newPrice) throws NoPermissionException {
        Store relevantStore = stores.get(storeId);
        relevantStore.editProductSupply(userId, productId,  newSupply, newName, newPrice);
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




//    public List<ProductView> SearchProductsAccordingName(String productName){
//        return new ArrayList<>();
//
//    }
//    public List<ProductView> SearchProductsAccordingCategory(List<ProductsCategories> categories ){
//        return new ArrayList<>();
//
//    }
//    public List<ProductView> SearchProductsAccordingKeyword( String keyword){
//        return new ArrayList<>();
//
//    }
//    public List<ProductView> SearchProductsAccordingPrice( float fromPrice, float toPrice ){
//        return new ArrayList<>();
//
//    }
//    public List<ProductView> SearchProductsAccordingRating(float productRating){
//        return new ArrayList<>();
//
//    }
//
//

}
