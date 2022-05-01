package Controllers;

import ExternalConnections.Delivery.DeliveryNames;
import ExternalConnections.ExternalConnectionHolder;
import ExternalConnections.Payment.PaymentNames;
import History.PurchaseHistory;
import ShoppingCart.InventoryProtector;
import ShoppingCart.ShoppingCart;
import Store.Product;
import StorePermission.Permission;
import StorePermission.StoreRoles;
import User.Guest;
import User.Subscriber;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

public class Service {

    private ExecutorService executorService;

    private BigController bigController;

    public Service() throws IOException {
        this.executorService = Executors.newFixedThreadPool(10);
        bigController = new BigController();
    }

    public Future<Boolean> sign_up(String user_name, String password) {
        Future<Boolean> future = executorService.submit(() -> bigController.sign_up(user_name,password));
        return future;
    }

    public Future<Boolean> login(String user_name, String password) {
        Future<Boolean> future = executorService.submit(() -> bigController.login(user_name,password));
        return future;
    }

    public Future<Boolean> logout(String user_name) {
        Future<Boolean> future = executorService.submit(() -> bigController.logout(user_name));
        return future;

    }
    public Future sendComplaint(String userId, String StoreName,String complaint ) {
        Future future = executorService.submit(() -> bigController.sendComplaint(userId,StoreName,complaint));
        return future;

    }

    public Future getGuest(String id) {
        Future future = executorService.submit(() -> bigController.getGuest(id));
        return future;

    }

    public Future addGuest() {
        Future future = executorService.submit(() -> bigController.addGuest());
        return future;
    }
    public Future GuestExitSystem(String name) {
        Future future = executorService.submit(() -> bigController.GuestExitSystem(name));
        return future;

    }

    public Future getSystemAdmin() {
        Future future = executorService.submit(() -> bigController.getSystemAdmin());
        return future;

    }

    public Future add_subscriber(Subscriber s) {
        Future future = executorService.submit(() -> bigController.add_subscriber(s));
        return future;

    }
    public Future getGuest_list() {
        Future future = executorService.submit(() -> bigController.getGuest_list());
        return future;
    }

    public Future getUser_list() {
        Future future = executorService.submit(() -> bigController.getUser_list());
        return future;
    }
    public Future Add_Query(String user_name,String query) {
        Future future = executorService.submit(() -> bigController.Add_Query(user_name,query));
        return future;

    }
    public Future get_subscriber(String user_name) {
        Future future = executorService.submit(() -> bigController.get_subscriber(user_name));
        return future;
    }
    public Future getShoppingCart(String user_Id){
        Future future = executorService.submit(() -> bigController.getShoppingCart(user_Id));
        return future;
    }
    public Future containsStore(String user_id,String storeID) {
        Future future = executorService.submit(() -> bigController.containsStore(user_id,storeID));
        return future;

    }
    public Future removeProductFromCart(String user_id,String productID, String storeID, int amount) {
        Future future = executorService.submit(() -> bigController.removeProductFromCart(user_id,productID,storeID,amount));
        return future;
    }

    public Future addProductFromCart(String user_id, String productID, String storeID, int amount, boolean auctionOrBid) {
        Future future = executorService.submit(() -> bigController.addProductFromCart(user_id,productID,storeID,amount,auctionOrBid));
        return future;
    }
    public Future getCartInventory(String user_id) {
        Future future = executorService.submit(() -> bigController.getCartInventory(user_id));
        return future;

    }
    public Future purchaseCart(String user_id, PaymentNames payment, DeliveryNames delivery) {
        Future future = executorService.submit(() -> {
            try {
                return bigController.purchaseCart(user_id, payment, delivery);
            } catch (Exception e) {
                return -1f;
            }
        });
        return future;

    }

    /// Store controller

    public Future addNewProductToStore(String storeId, String userId, String productName, float price, int supply, String category) throws NoPermissionException {

            Future future = executorService.submit(() -> {
                try {
                    bigController.addNewProductToStore(storeId, userId, productName, price, supply, category);
                    return true;
                } catch (NoPermissionException e) {
                    e.printStackTrace();
                    return false;
                }
            });
            return future;

        }

    public Future openNewStore( String userId, String storeName){
        Future future = executorService.submit(() -> bigController.openNewStore(userId, storeName));
        return future;

    }

    public Future unfreezeStore(String storeId, String userId) throws NoPermissionException {
        Future future = executorService.submit(() -> {
            try {
                bigController.unfreezeStore(storeId,userId);
                return true;
            } catch (NoPermissionException e) {
                return false;

            }
        });
        return future;
        }


    public Future freezeStore(String storeId, String userId) throws NoPermissionException {
        Future future = executorService.submit(() -> {
            try {
                bigController.freezeStore(storeId,userId);
                return true;
            } catch (NoPermissionException e) {
                e.printStackTrace();
                return false;
            }
        });
        return future;
    }
    public Future getInfoOnManagersOwners(String storeId, String userId) throws NoPermissionException {
        Future future = executorService.submit(() -> bigController.getInfoOnManagersOwners(storeId,userId));
        return future;

    }
    public Future editProduct(String storeId, String userId, String productId, int newSupply, String newName, float newPrice, String category) throws NoPermissionException {

        Future future = executorService.submit(() -> {
            try {
                bigController.editProduct(storeId,userId,productId,newSupply,newName,newPrice,category);
                return true;
            } catch (NoPermissionException e) {
                e.printStackTrace();
                return false;
            }
        });
        return future;
         }

    public Future deleteProduct(String storeId, String userId, String productId) throws NoPermissionException {

        Future future = executorService.submit(() -> {
            try {
                bigController.deleteProductFromStore(storeId,userId,productId);
                return true;
            } catch (NoPermissionException e) {
                e.printStackTrace();
                return false;
            }
        });
        return future;
          }


    public Future removePermissionTo(String storeId, String userIdRemoving,String UserAffectedId) throws NoPermissionException {
        Future future = executorService.submit(() -> {
            try {
                bigController.removePermissionTo(storeId,userIdRemoving,UserAffectedId);
                return true;
            } catch (NoPermissionException e) {
                e.printStackTrace();
                return false;
            }
        });
        return future;
         }

    public Future createOwner(String storeId, String userIdGiving, String UserGettingPermissionId, List<Permission> permissions) throws NoPermissionException {
        Future future = executorService.submit(() -> {
            try {
                bigController.createOwner(storeId,userIdGiving,UserGettingPermissionId,permissions);
                return true;
            } catch (NoPermissionException e) {
                e.printStackTrace();
                return false;
            }
        });
        return future;

        }
    public Future createManager(String storeId, String userIdGiving, String UserGettingPermissionId) throws NoPermissionException {
        Future future = executorService.submit(() -> {
            try {
                bigController.createManager(storeId,userIdGiving,UserGettingPermissionId);
                return true;
            } catch (NoPermissionException e) {
                e.printStackTrace();
                return false;
            }
        });
        return future;
           }




    public Future addReviewToProduct(String storeId, String userId, String productId, String Title, String Body, float rating){
        Future future = executorService.submit(() -> bigController.addReviewToProduct(storeId,userId,productId,Title,Body,rating));
        return future;

    }

    public Future addNewThreadToForum(String storeId,String title, String userId){
        Future future = executorService.submit(() -> bigController.addNewThreadToForum(storeId,title,userId));
        return future;

    }

    public Future postMessageToForum(String storeId, String threadId, String userId, String message) throws NoPermissionException {
        Future future = executorService.submit(() -> {
            try {
                bigController.postMessageToForum(storeId,threadId,userId,message);
                return true;
            } catch (NoPermissionException e) {
                e.printStackTrace();
                return false;
            }
        });
        return future;

    }


    public Future getAllProductsAndStores(String userId){
        Future future = executorService.submit(() -> bigController.getAllProductsAndStores(userId));
        return future;

    }

    public Future SearchProductsAccordingName(String userId,String productName){
        Future future = executorService.submit(() -> bigController.SearchProductsAccordingName( userId, productName));
        return future;

    }

    public Future SearchProductsAccordingCategory(String userId,List<String> categories ){
        Future future = executorService.submit(() -> bigController.SearchProductsAccordingCategory( userId, categories ));
        return future;


    }
    public Future SearchProductsAccordingPrice(String userId, float fromPrice, float toPrice ){
        Future future = executorService.submit(() -> bigController.SearchProductsAccordingPrice( userId,  fromPrice,  toPrice ));
        return future;


    }

    public Future SearchProductsAccordingRating(String userId, float productRating){
        Future future = executorService.submit(() -> bigController.SearchProductsAccordingRating(userId, productRating));
        return future;

    }

    public Future deleteStore(String userId, String storeId) {
        Future future = executorService.submit(() -> bigController.deleteStore(userId,storeId));
        return future;

    }

    public Future getStoreHistory(String storeId, String userId) throws NoPermissionException{
        Future future = executorService.submit(() -> bigController.getStoreHistory(storeId, userId));
        return future;
    }

    public void shutdown(){
        executorService.shutdown();
    }

}


