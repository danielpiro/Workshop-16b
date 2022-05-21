package com.example.demo.Controllers;

import com.example.demo.CustomExceptions.Exception.NotifyException;
import com.example.demo.CustomExceptions.Exception.StorePolicyViolatedException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.CustomExceptions.Exception.UserException;
import com.example.demo.History.PurchaseHistory;
import com.example.demo.Mock.*;
import com.example.demo.CustomExceptions.ExceptionHandler.ReturnValue;
import com.example.demo.ExternalConnections.Delivery.DeliveryNames;
import com.example.demo.ExternalConnections.Delivery.FedEx;
import com.example.demo.ExternalConnections.Delivery.UPS;
import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ExternalConnections.ExternalConnections;
import com.example.demo.ExternalConnections.Payment.MasterCard;
import com.example.demo.ExternalConnections.Payment.PaymentNames;
import com.example.demo.ExternalConnections.Payment.Visa;
import com.example.demo.GlobalSystemServices.Log;
import com.example.demo.History.History;
import com.example.demo.ShoppingCart.InventoryProtector;
import com.example.demo.Store.Product;
import com.example.demo.Store.Store;
import com.example.demo.Store.StorePurchase.Policies.Policy;
import com.example.demo.StorePermission.Permission;
import com.example.demo.User.Guest;
import com.example.demo.User.Subscriber;
//import com.example.demo.dto.AdminDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.naming.NoPermissionException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@CrossOrigin(maxAge = 3600)
@RestController
@EnableWebMvc
@RequestMapping("/api")
public class BigController {
    private StoreController sc;
    private UserController us;
    Log my_log = Log.getLogger();


    //    public BigController(UserController us , StoreController sc) throws IOException {
//        this.us = us;
//        this.sc = sc;
//    }
    public BigController() throws IOException {
        this.us = new UserController();
        this.sc = new StoreController();
        initiateExternalConnections();
        my_log.logger.info("System Started");
    }

    public void initiateExternalConnections() {
        ExternalConnections externalConnections = ExternalConnections.getInstance();
        externalConnections.addPayment(new Visa());
        externalConnections.addPayment(new MasterCard());
        externalConnections.addDelivery(new FedEx());
        externalConnections.addDelivery(new UPS());
    }

    //    @PostMapping(value = "/add/admin" , consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public void addSystemAdmin(@RequestBody(required = true)  whoIsAdding, String user_toMakeAdmin) {
//        getUserController().addSystemAdmin(whoIsAdding,user_toMakeAdmin);
//    }


    //todo wrap ReturnValue with Response Entity
    //todo guy change by 2.d Version 2.

    @DeleteMapping("/users")
    public ReturnValue deleteUser(@RequestParam String isDeleting,
                                  @RequestParam String whosBeingDeleted) throws NoPermissionException {


        my_log.logger.info("user" + isDeleting + "is trying to delete user" + whosBeingDeleted);
        sc.removeRoleInHierarchy(whosBeingDeleted);

        ReturnValue rv = new ReturnValue(true, "", getUserController().deleteUser(isDeleting, whosBeingDeleted));


        //ResponseEntity re = new ResponseEntity(rv,)

        return rv;
    }


    @PostMapping("/users/signup")
    public ResponseEntity signup(@RequestParam String guest_id,
                                 @RequestParam String user_name,
                                 @RequestParam String password) {
        my_log.logger.info("user " + user_name + " is trying to sign up");
        ReturnValue rv = new ReturnValue(true, "", getUserController().sign_up(guest_id, user_name, password));
        return new ResponseEntity(rv, HttpStatus.OK);
    }

    @PostMapping("/users/login")
    public ReturnValue login(@RequestParam String userNameLogin,
                             @RequestParam String password) {
        System.out.println(userNameLogin);
        System.out.println(password);
        ReturnValue rv = new ReturnValue(true, "", getUserController().login(userNameLogin, password));
        return rv;
    }


    @PostMapping("/users/logout")
    public ReturnValue logout(@RequestParam String user_name) {

        ReturnValue rv = new ReturnValue(true, "", getUserController().logout(user_name));
        return rv;

    }

    @PostMapping("/market")
    public ReturnValue sendComplaint(@RequestParam String userId, @RequestParam String StoreName, @RequestParam String complaint) {
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    @GetMapping("/market/guest")
    public ReturnValue addGuest() {

        ReturnValue rv = new ReturnValue(true, "", getUserController().addGuest().name);
        return rv;
    }


    @PostMapping("/users")
    public ReturnValue GuestExitSystem(@RequestParam String guestId) {
        getUserController().GuestExitSystem(guestId);

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    private Subscriber getSystemAdmin() {
        return getUserController().getSystemAdmin();
    }


    @PostMapping("/cart")
    public ReturnValue getShoppingCart(@RequestParam String user_Id) throws UserException {
        ReturnValue rv = new ReturnValue(true, "", getUserController().getShoppingCart(user_Id));
        return rv;
    }

//    public boolean containsStore(String user_id, String storeID) {
//        return getUserController().containsStore(user_id, storeID);
//    }

    @DeleteMapping("/cart")
    public ReturnValue removeProductFromCart(@Valid @RequestBody MockSmallProduct mockSmallProduct) throws UserException {

        getUserController().removeProduct(mockSmallProduct.getUser_id(), mockSmallProduct.getProductID(), mockSmallProduct.getStoreID(), mockSmallProduct.getAmount());

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    //auction or bid false for now
    @PostMapping(value = "/cart/product" , consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue addProductFromCart(@Valid @RequestBody MockSmallProduct mockProduct,
                                          @RequestParam boolean auctionOrBid) throws UserException {

        InventoryProtector inventoryProtector = sc.getInventoryProtector(mockProduct.getStoreID());
        getUserController().addProduct(mockProduct.getUser_id(), mockProduct.getProductID(), mockProduct.getStoreID(), mockProduct.getAmount(), inventoryProtector, auctionOrBid);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;

    }


    @PostMapping("/cart/purchase")
    public ReturnValue purchaseCart(@RequestParam String user_id,
                                    @RequestParam PaymentNames payment,
                                    @RequestParam DeliveryNames delivery) throws SupplyManagementException, StorePolicyViolatedException, UserException {


        float a = getUserController().purchaseCart(user_id, new ExternalConnectionHolder(delivery, payment));
        ReturnValue rv = new ReturnValue(true, "", a);
        return rv;
    }

    /// Store controller

    @PostMapping("/store")
    public ReturnValue addNewProductToStore(@Valid @RequestBody MockFullProduct mockProduct) throws NoPermissionException, SupplyManagementException {
        userExistsAndLoggedIn(mockProduct.getUserId());

        getStoreController().addNewProduct(mockProduct.getStoreId(), mockProduct.getUserId(), mockProduct.getProductName(), mockProduct.getPrice(), mockProduct.getSupply(), mockProduct.getCategory());

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;

    }

    @DeleteMapping("/permissions")
    public ReturnValue removeSomePermissions(@Valid @RequestBody MockPermission mockPermission) throws NoPermissionException {
        userExistsAndLoggedIn(mockPermission.getUserIdRemoving());

        sc.removeSomePermissions(mockPermission.getStoreId(), mockPermission.getUserIdRemoving(), mockPermission.getUserAffectedId(), mockPermission.getPerToRemove());

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/store/open")
    public ReturnValue openNewStore(@RequestParam String userId,
                                    @RequestParam String storeName) throws NoPermissionException {
        userExistsAndLoggedIn(userId);
        List<String> managers = new ArrayList<>();
        managers.add(userId);
        ReturnValue rv = new ReturnValue(true, "", getStoreController().openNewStore(storeName, managers));
        return rv;

    }

    @PostMapping("/store/freeze")
    public ReturnValue unfreezeStore(@RequestParam String storeId,
                                     @RequestParam String userId) throws NoPermissionException {
        userExistsAndLoggedIn(userId);
        getStoreController().unfreezeStore(storeId, userId);

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/store/unfreeze")
    public ReturnValue freezeStore(@RequestParam String storeId,
                                   @RequestParam String userId) throws NoPermissionException {

        userExistsAndLoggedIn(userId);
        getStoreController().freezeStore(storeId, userId);

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    public ReturnValue getInfoOnManagersOwners(@RequestParam String storeId,
                                               @RequestParam String userId) throws NoPermissionException {
        userExistsAndLoggedIn(userId);


        ReturnValue rv = new ReturnValue(true, "", getStoreController().getInfoOnManagersOwners(storeId, userId));
        return rv;
    }

    @PostMapping("/store/product")
    public ReturnValue editProduct(@RequestParam String productId, @Valid @RequestBody MockFullProduct mockProduct) throws NoPermissionException, SupplyManagementException {
        userExistsAndLoggedIn(mockProduct.getUserId());
        getStoreController().editProduct(mockProduct.getStoreId(), mockProduct.getUserId(), productId, mockProduct.getSupply(), mockProduct.getProductName(), mockProduct.getPrice(), mockProduct.getCategory());
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    @DeleteMapping("/store/product")
    public ReturnValue deleteProductFromStore(@RequestParam String storeId,
                                              @RequestParam String userId,
                                              @RequestParam String productId) throws NoPermissionException, SupplyManagementException {
        userExistsAndLoggedIn(userId);
        getStoreController().deleteProduct(storeId, userId, productId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @DeleteMapping("/store/permissions")
    public ReturnValue removePermissionTo(@RequestParam String storeId, @RequestParam String userIdRemoving, @RequestParam String UserAffectedId) throws NoPermissionException {
        if (getUserController().checkIfUserExists(userIdRemoving) && getUserController().checkIfUserExists(UserAffectedId) && getUserController().checkIfUserIsLoggedIn(userIdRemoving))
            getStoreController().removeRoleInHierarchy(storeId, userIdRemoving, UserAffectedId);
        else
            throw new IllegalArgumentException("couldn't remove permission because the given userId doesn't exist or is not logged in");
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/owner")
    public ReturnValue createOwner(@Valid @RequestBody MockSmallPermission mockPermission) throws NoPermissionException {
        if (getUserController().checkIfUserExists(mockPermission.getUserIdGiving()) && getUserController().checkIfUserExists(mockPermission.getUserGettingPermissionId()) && getUserController().checkIfUserIsLoggedIn(mockPermission.getUserIdGiving())) {
            getStoreController().createOwner(mockPermission.getStoreId(), mockPermission.getUserIdGiving(), mockPermission.getUserGettingPermissionId(), mockPermission.getPermissions());
        } else
            throw new IllegalArgumentException("couldn't give permission because the given userId doesn't exist or is not logged in");
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/manager")
    public ReturnValue createManager(@RequestParam String storeId, @RequestParam String userIdGiving, @RequestParam String UserGettingPermissionId) throws NoPermissionException {
        if (getUserController().checkIfUserExists(userIdGiving) && getUserController().checkIfUserExists(UserGettingPermissionId) && getUserController().checkIfUserIsLoggedIn(userIdGiving)) {
            getStoreController().createManager(storeId, userIdGiving, UserGettingPermissionId);
        } else
            throw new IllegalArgumentException("couldn't give permission because the given userId doesn't exist or is not logged in");
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    @PostMapping("/product")
    public ReturnValue addReviewToProduct(@Valid @RequestBody MockProductReview mockProd) throws NoPermissionException, SupplyManagementException {
        userExistsAndLoggedIn(mockProd.getUserId());
        getStoreController().addReviewToProduct(mockProd.getStoreId(), mockProd.getUserId(), mockProd.getProductId(), mockProd.getTitle(), mockProd.getBody(), mockProd.getRating());
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/forum/thread")
    public ReturnValue addNewThreadToForum(@RequestParam String storeId, @RequestParam String title, @RequestParam String userId) throws NoPermissionException {
        userExistsAndLoggedIn(userId);


        ReturnValue rv = new ReturnValue(true, "", getStoreController().addNewThreadToForum(storeId, title, userId));
        return rv;
    }

    @PostMapping("/forum/message")
    public ReturnValue postMessageToForum(@RequestParam String storeId, @RequestParam String threadId, @RequestParam String userId, @RequestParam String message) throws NoPermissionException, NotifyException, UserException {
        userExistsAndLoggedIn(userId);
        getStoreController().RolePostMessageToForum(storeId, threadId, userId, message);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;

    }

    @GetMapping("/store/all")
    public HashMap<String, List<Product>> getAllProductsAndStores() {

        return getStoreController().getAllProductsAndStores();
    }


    @GetMapping("/search/name")
    public CompletableFuture<ReturnValue> SearchProductsAccordingName(@RequestParam String userId, @RequestParam String productName) {
        System.out.println("");
        if (!IsGuest(userId))
            userExistsAndLoggedIn(userId);

        ReturnValue rv = new ReturnValue(true, "", getStoreController().SearchProductsAccordingName(productName));

        return CompletableFuture.completedFuture(rv);
    }


    @GetMapping("/search/category")

    public List<Product> SearchProductsAccordingCategory(@RequestParam String userId, @Valid @RequestBody MockCategories mockCategories) {

        if (!IsGuest(userId))
            userExistsAndLoggedIn(userId);
        return getStoreController().SearchProductsAccordingCategory(mockCategories.getCategories());

    }


    @GetMapping("/search/price")

    public List<Product> SearchProductsAccordingPrice(@RequestParam String userId, @RequestParam float fromPrice, @RequestParam float toPrice) {

        if (!IsGuest(userId))
            userExistsAndLoggedIn(userId);
        return getStoreController().SearchProductsAccordingPrice(fromPrice, toPrice);

    }

    /**
     * @param userId
     * @param productRating - the minimum rating of a product
     * @return
     */

    @GetMapping("/search/rating")
    public List<Product> SearchProductsAccordingRating(@RequestParam String userId, @RequestParam float productRating) {

        if (!IsGuest(userId))
            userExistsAndLoggedIn(userId);
        return getStoreController().SearchProductsAccordingRating(productRating);
    }

    @DeleteMapping("/store")
    public ReturnValue deleteStore(@RequestParam String userId, @RequestParam String storeId) throws NoPermissionException {
        userExistsAndLoggedIn(userId);
        getStoreController().deleteStore(userId, storeId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;

    }

    private void userExistsAndLoggedIn(String userId) {
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.logger.warning("User doesn't exist or is not logged in");
            throw new IllegalArgumentException("User doesn't exist or is not logged in");

        }
    }


    @GetMapping("/history/store")
    public ReturnValue getStoreHistory(@RequestParam String storeId, @RequestParam String userId) throws NoPermissionException {
        userExistsAndLoggedIn(userId);

        ReturnValue rv = new ReturnValue(true, "", sc.getStoreHistory(storeId, userId));
        return rv;
    }

    @GetMapping("/history/user")
    public ReturnValue getUserHistory(@RequestParam String userId) throws NoPermissionException {
        userExistsAndLoggedIn(userId);

        ReturnValue rv = new ReturnValue(true, "", History.getInstance().getUserHistory(userId));
        return rv;
    }

    @GetMapping("/history/store/user")
    public ReturnValue getStoreUserHistory(@RequestParam String userIdRequesting,
                                           @RequestParam String storeId,
                                           @RequestParam String userId) throws NoPermissionException {
        userExistsAndLoggedIn(userId);
        ReturnValue rv = new ReturnValue(true, "", sc.getStoreHistory(userIdRequesting, storeId, userId));
        return rv;
    }

    private boolean IsGuest(String userId) {
        for (Guest g : us.getGuest_list()) {
            if (g.name.equals(userId)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/permission/type")
    public ReturnValue getPermissionType(@RequestParam String username) {

        ReturnValue rv = new ReturnValue(true, "", getUserController().getPermissionType(username));
        return rv;
    }

    @GetMapping("/notification/complaint")
    public ReturnValue readComplaintNotification(@RequestParam String userId,
                                                 @RequestParam int complaintNotificaionId) throws UserException {
        userExistsAndLoggedIn(userId);
        getUserController().readComplaintNotification(userId, complaintNotificaionId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @GetMapping("/notification/store/complaint")
    public ReturnValue readStoreNotification(@RequestParam String userId,
                                             @RequestParam int storeNotificaionId) throws UserException {
        userExistsAndLoggedIn(userId);
        getUserController().readStoreNotification(userId, storeNotificaionId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    //todo need to use policyBuilder to create policy
    public String addNewPolicy(String storeId, String userId, Policy policy) throws NoPermissionException {
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return sc.addNewPolicy(storeId, userId, policy);
    }

    @DeleteMapping("/policy")
    public ReturnValue deletePolicy(@RequestParam String storeId,
                                    @RequestParam String userId,
                                    @RequestParam String policyId) throws NoPermissionException {
        userExistsAndLoggedIn(userId);
        sc.deletePolicy(storeId, userId, policyId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    private void checkIfUserHaveRoleInStore() {
        //todo
    }

    //    public List<Permission> getUserPermission(String StoreId, String userId){
//        return sc.getUserPermission(StoreId,userId);
//    }
    @GetMapping("/title")
    public ReturnValue getTitle(@RequestParam String userId,
                                @RequestParam String StoreId,
                                @RequestParam String userIf) {
        userExistsAndLoggedIn(userId);
        ReturnValue rv = new ReturnValue(true, "", sc.getTitle(StoreId, userIf));
        return rv;
    }

    @GetMapping("/policy")
    public ReturnValue getPolices(@RequestParam String storeId,
                                  @RequestParam String userId) throws NoPermissionException {
        userExistsAndLoggedIn(userId);
        ReturnValue rv = new ReturnValue(true, "", sc.getPolices(storeId, userId));
        return rv;
    }

//    public List<PurchaseHistory> getStoreUserHistory(String userIdRequesting, String storeId, String userId) throws NoPermissionException{
//        if(!getUserController().checkIfUserExists(userId)||!getUserController().checkIfUserIsLoggedIn(userId)){
//            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
//            return null;
//        }
//        return sc.getStoreHistory(userIdRequesting, storeId, userId);
//    }

    @GetMapping("/stores")
    public ReturnValue getAllStoresByStoreName(@RequestParam String userId, @RequestParam String name) {
        userExistsAndLoggedIn(userId);
        ReturnValue rv = new ReturnValue(true, "", sc.getAllStoresByStoreName(name));
        return rv;
    }

    private UserController getUserController() {
        return us;
    }

    private StoreController getStoreController() {
        return sc;
    }
}
