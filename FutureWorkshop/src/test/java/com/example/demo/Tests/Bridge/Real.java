
package com.example.demo.Tests.Bridge;


import com.example.demo.Controllers.BigController;
import com.example.demo.CustomExceptions.Exception.NotifyException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.CustomExceptions.Exception.UserException;
import com.example.demo.CustomExceptions.ExceptionHandler.ReturnValue;
import com.example.demo.ExternalConnections.Delivery.Delivery;
import com.example.demo.ExternalConnections.Delivery.DeliveryNames;
import com.example.demo.ExternalConnections.ExternalConnections;
import com.example.demo.ExternalConnections.Payment.Payment;
import com.example.demo.ExternalConnections.Payment.PaymentNames;
import com.example.demo.History.History;
import com.example.demo.History.PurchaseHistory;
import com.example.demo.Mock.MockFullProduct;

import com.example.demo.Mock.MockPermission;
import com.example.demo.Mock.MockSmallPermission;
import com.example.demo.Mock.MockSmallProduct;
import com.example.demo.NotificationsManagement.NotificationManager;
import com.example.demo.NotificationsManagement.StoreNotification;
import com.example.demo.Store.Product;
import com.example.demo.StorePermission.Permission;
import com.example.demo.User.Guest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


public class Real   {

    private BigController bigController;
    //private Service service;



    public Real() throws IOException {
        //service = new Service();
    }

   public Real(BigController msApp) {
        this.bigController = msApp;
   }

   public BigController getBigController() {
        return bigController;
  }

    /** requirement 1.b in V1
     * @return*/
//    public String parallelUse() throws ExecutionException, InterruptedException { //Need to implement thread-base system
//        Future future1 = service.sign_up("dan","rotman");
//        Future future2 = service.sign_up("guy","porat");
//        future1.get();
//        future2.get();
//
//        Future future3 = service.login("dan","rotman");
//        Future future4 = service.login("guy","porat");
//        future3.get();
//        future4.get();
//
//        return "true";
//
//
//
//    }

    public String parallelUse() throws ExecutionException, InterruptedException {
        return null;
    }

    /** requirement 1.c in V1 */
    public String systemLogging() { //Need to create log file (containing error logs)
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.1 */
    public String openingMarket(){
        try {
            this.bigController = new BigController();
            NotificationManager.ForTestsOnlyBuildNotificationManager(bigController.getUserController());
            return "system opened successfully";
        }
        catch (Exception e){
            return "failed to open market system";
        }
    }

    /** System requirement - I.2 - Header*/
    public String changeExternalService(int currServiceCode, String currServiceName,
                                        int newServiceCode, String newServiceName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.2 - Header*/
    public String switchExternalService(int currServiceCode, String currServiceName,
                                 int newServiceCode, String newServiceName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.2 - Header*/
    public String addExternalService(int serviceCode, String serviceName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    //TODO: talk about external connections how to make it better.

    /** System requirement - I.2 */
    public boolean removePayment(PaymentNames paymentRemove){
        return ExternalConnections.getInstance().removePayment(paymentRemove);
    }

    /** System requirement - I.2 */
    public boolean AddPayment(Payment payment){
        return ExternalConnections.getInstance().addPayment(payment);
    }

    /** System requirement - I.2 */
    public boolean removeDelivery(DeliveryNames deliveryRemove){
        return ExternalConnections.getInstance().removeDelivery(deliveryRemove);
    }

    /** System requirement - I.2 */
    public boolean addDelivery(Delivery delivery){
        return ExternalConnections.getInstance().addDelivery(delivery);
    }

    /** System requirement - I.3 */
    public int payment(PaymentNames payment, float total)
    {
        Payment paymentObject=null;
        boolean gotPayment=false;
        for (int i = 0; i < 20 && !gotPayment; i++) {
            try {
                paymentObject = ExternalConnections.getInstance().getCertainPayment(payment);
                gotPayment=true;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
       return paymentObject.payment(total);
    }

    /** System requirement - I.4 */
    public int delivery(DeliveryNames delivery, float weight){
        Delivery deliveryObject=null;
        boolean gotDelivery=false;
        for (int i = 0; i < 20 && !gotDelivery; i++) {

           try {
                deliveryObject = ExternalConnections.getInstance().getCertainDelivery(delivery);
                gotDelivery=true;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return deliveryObject.delivery(weight);
    }

    /** System requirement - I.5 */
    /** System requirement - I.6 */
    public void markAsReadStoreNotification(String userId,
                                       int storeNotificationId){
        try {
            bigController.readStoreNotification(userId,storeNotificationId);
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
    }

    public List<StoreNotification> getAllStoreNotificationsOf(String userId){
        try {
            ReturnValue<List<StoreNotification>> returnValue =bigController.getUserStoreNotifications(userId);
            return returnValue.getValue();
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
    }

    /** System requirement - I.5 */
    public String realtimeNotificationProductBought(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.5 */
    public String realtimeNotificationStoreClosed(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.5 */
    public String realtimeNotificationStoreReopened(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.5 */
    public String realtimeNotificationUserPermissionUpdate(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.6 */
    public String offlineNotificationProductBought(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.6 */
    public String offlineNotificationStoreClosed(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.6*/
    public String offlineNotificationStoreReopened(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.6*/
    public String offlineNotificationUserPermissionUpdate(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    public String getInToTheSystem() {
        return null;
    }

    /** User requirement - II.1.1 */
    //todo fix
//    public String getInToTheSystem(){
//        return getBigController().addGuest();
//    }

    /** User requirement - II.1.2 */
    public String getOutFromTheSystem(String name){
        ReturnValue<String> returnValue = getBigController().GuestExitSystem(name);
        return returnValue.getValue();
    }

    /** User requirement - II.1.3 */
    public boolean register(String username, String password){
        boolean  ans=false;
        try {
             ans = (Boolean) getBigController().signup(username, password).getValue();
        }catch (Exception e){

        }
            return  ans;



    }

    /** User requirement - II.1.4 */
    public boolean login(String username, String password)  {
        try {
            ReturnValue<Boolean> returnValue = getBigController().login(username, password);
            return returnValue.isSuccess();
        }catch (Exception e){
            return false;
        }
    }


    /** User requirement - II.2.1 */
    public String receiveSystemInfo(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.2.2 */
    public List<Product> searchProduct(String userId, String productName) {

        try {
            ReturnValue<List<Product>> rv = getBigController().SearchProductsAccordingName(userId, productName);
            return rv.getValue();
        }catch (Exception e){
            return null;
        }

    }


    /** User requirement - II.2.3  => its the same as II.2.4 */
    public boolean saveProductFromStoreToShoppingCart(String user_id,String productID, String storeID, int amount,boolean auctionOrBid){
        return increaseProductQuantityInShoppingCart(user_id, productID,  storeID,  amount, auctionOrBid);
    }


    /** User requirement - II.2.4 */
    public boolean increaseProductQuantityInShoppingCart(String user_id,String productID, String storeID, int amount,boolean auctionOrBid )  {
        MockSmallProduct msp = new MockSmallProduct(user_id,productID,storeID,amount);
        int ans =-1;
        try {
             ans = (Integer) bigController.addProductFromCart(msp, auctionOrBid).getValue();
        }catch (Exception e) {
            return false;
        }
        return ans >= 0;
    }

    /** User requirement - II.2.4 */
    public boolean decreaseProductQuantityInShoppingCart(String userId,String productID, String storeID, int amount){
        int ans =-1;
        try {
             ans = (Integer) bigController.removeProductFromCart( new MockSmallProduct(userId,productID,storeID,amount)).getValue();
        }catch (Exception e) {
            return false;
        }
        return ans == 0;

    }



    /** User requirement - II.2.5 */
    public boolean purchaseShoppingCart(String userID,PaymentNames payment,DeliveryNames delivery){

        float ans =-1;
        try {
            ans = (Float) bigController.purchaseCart(userID,payment,delivery).getValue();
        }catch (Exception e) {
            return false;
        }
        return ans != -1;
    }


    /** User requirement - II.3.1 */
    public boolean logout(String userid){
        try {
            return (Boolean) getBigController().logout(userid).getValue();
        }catch (Exception e) {
            return false;
        }

    }

    /** User requirement - II.3.2 */
    public String openStore(String userID, String storeName){
        try {
            return (String) bigController.openNewStore(userID, storeName).getValue();
        }
        catch (Exception e){
            return "failed to open store";
        }
    }
    public boolean openStore(String userID, String storeName, HashMap<Product, Integer> products){
        try {
            String storeId = (String) bigController.openNewStore(storeName, userID).getValue();
            for (Product p : products.keySet()) {
                addProductToStore(storeId, userID , p.getName(), p.getPrice(), products.get(p), p.getCategory().toString());
            }
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    /** User requirement - II.4.1 */
    public String addProductToStore(String storeId, String userId, String productName, float price, int supply, String category){
        try {
            MockFullProduct mfp = new MockFullProduct(storeId,userId,productName,price,supply,category);
            String m = (String) bigController.addNewProductToStore(mfp).getValue();
            return m;
        }
        catch (Exception e) {
            
            return "";
        }
    }

    /** User requirement - II.4.1 */
    public boolean removeProductFromStore(String storeId,String userId,String productId){
        try {
            bigController.deleteProductFromStore(storeId,userId, productId);
            return true;
        }
        catch (NoPermissionException | SupplyManagementException | UserException | NotifyException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** User requirement - II.4.1 */
    public boolean editProductInStore(String storeId, String userId, String productId,
                                     int newSupply, String newName, float newPrice, String category){
        try {
            bigController.editProduct(productId,new MockFullProduct( storeId,userId, newName, newPrice,newSupply,   category));
            return true;
        }
        catch (NoPermissionException | SupplyManagementException | UserException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** User requirement - II.4.2 */
    public String changeStorePolicy(String storeName, String newStorePolicy){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }


    /** User requirement - II.4.4 */
    public boolean addNewStoreOwner(String storeId, String userIdGiving, String UserGettingPermissionId, List<Permission> permissions){
        try {
            bigController.createOwner(new MockSmallPermission(storeId, userIdGiving,UserGettingPermissionId,permissions));
            return true;
        }
        catch (NoPermissionException | UserException | NotifyException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** User requirement - II.4.6 */
    public boolean addNewStoreManager(String storeId, String userIdGiving, String UserGettingPermissionId){
        try {
            bigController.createManager(storeId, userIdGiving, UserGettingPermissionId);
            return true;
        }
        catch (NoPermissionException | UserException | NotifyException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** User requirement - II.4.7 */
    public boolean changeStoreManagerPermissions(String storeId, String userIdRemoving, String UserAffectedId, List<String> PerToRemove) throws NoPermissionException {
        try {
            bigController.removeSomePermissions(new MockPermission(storeId, userIdRemoving, UserAffectedId, PerToRemove));
            return true;
        } catch (UserException e) {
            return false;
        }

    }

    /** User requirement - II.4.9 */
    public boolean freezeStoreByOwner(String storeId, String userId){
        try {
            bigController.freezeStore(storeId,userId);
            return true;
        }
        catch (NotifyException|UserException|NoPermissionException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** User requirement - II.4.10 */
    public boolean unfreezeStoreByOwner(String storeId, String userId){//todo tell amit i added this
        try {
            bigController.unfreezeStore(storeId,userId);
            return true;
        }
        catch (NotifyException|UserException|NoPermissionException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** User requirement - II.4.11 */
    public boolean showStoreOfficials(String storeId, String userId){
        try {
            bigController.getInfoOnManagersOwners(storeId,userId);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** User requirement - II.4.13 */
    public List<PurchaseHistory> showStorePurchaseHistory(String storeId){
        return History.getInstance().getStoreHistory(storeId);
    }

    /** User requirement - II.4.13 */
    public List<PurchaseHistory> showUserPurchaseHistory(String userID){
        return History.getInstance().getUserHistory(userID);
    }

    /** User requirement - II.6.4 */
    public String showPurchaseHistoryForSystemFounder(String storeOrUser, String name) {
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    //Helper Methods
    public HashMap<String,List<Product>> getAllProductsAndStores(String userId){
        return bigController.getAllProductsAndStores();
    }

    public List<Guest> getGuest_list(){
        return bigController.getGuest_list();
    }

}
