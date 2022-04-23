package Controllers;

import GlobalSystemServices.IdGenerator;
import Store.Store;
import StorePermissin.User;
import Views.ProductView;
import Views.StoreView;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

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

    public void addNewProduct(String storeId, String userId, String productName, float price, int howMuch) throws NoPermissionException {
        Store relevantStore = stores.get(storeId);
        relevantStore.addNewProduct(userId, productName, price, howMuch);

    }

    public void addSupplyToProduct(String storeId, String userId, String productId, int howMuch) throws NoPermissionException {
        Store relevantStore = stores.get(storeId);
        relevantStore.addToExistingProduct(userId, productId, howMuch);
    }

    public void addReviewToProduct(String storeId, String userId, String productId, String Title, String Body, float rating){
        Store relevantStore = stores.get(storeId);
        relevantStore.addProductReview( userId, productId, Title, Body, rating);
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
