package com.example.demo.Controllers;

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
import com.example.demo.User.Guest;
import com.example.demo.User.Subscriber;
//import com.example.demo.dto.AdminDto;
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


@RestController
@EnableWebMvc
@EnableAsync
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

    @Async
    @DeleteMapping("/users")
    public ReturnValue deleteUser(@RequestParam String isDeleting,
                                  @RequestParam String whosBeingDeleted) throws NoPermissionException {

        my_log.logger.info("user" + isDeleting + "is trying to delete user" + whosBeingDeleted);
        sc.removeRoleInHierarchy(whosBeingDeleted);

        ReturnValue rv = new ReturnValue(true, "", getUserController().deleteUser(isDeleting, whosBeingDeleted));
        return rv;
    }

    @Async
    @PostMapping("/users/signup")
    public ReturnValue sign_up(@RequestParam String user_name,
                               @RequestParam String password) {
        my_log.logger.info("user " + user_name + " is trying to sign up");
        ReturnValue rv = new ReturnValue(true, "", getUserController().sign_up(user_name, password));
        return rv;
    }

    @Async
    @PostMapping("/users/login")
    public ReturnValue login(@RequestParam String user_name_login,
                             @RequestParam String password) {
        ReturnValue rv = new ReturnValue(true, "", getUserController().login(user_name_login, password));
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


    //todo return guest id, not the guest itself
    private Guest getGuest(String id) {
        return getUserController().getGuest(id);
    }

//    public String addGuest() {
//        return getUserController().addGuest().name;
//    }


    @PostMapping("/users")
    public ReturnValue GuestExitSystem(@RequestParam String guestId) {
        getUserController().GuestExitSystem(guestId);

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    private Subscriber getSystemAdmin() {
        return getUserController().getSystemAdmin();
    }
//
//    public void add_subscriber(Subscriber s) {
//       getUserController().add_subscriber(s);
//    }

    //todo get a random guest - dont want the front to know our implementation
    public List<Guest> getGuest_list() {

        return getUserController().getGuest_list();
    }

//    public List<Subscriber> getUser_list() {
//        return getUserController().getUser_list();
//    }

    //todo what is this?
    public void Add_Query(String user_name, String query) {
        getUserController().Add_Query(user_name, query);
    }


//    public Subscriber get_subscriber(String user_name) {
//        return getUserController().get_subscriber(user_name);
//    }

    //todo add guest to all functions!!!
    @PostMapping("/cart")
    public ReturnValue getShoppingCart(@RequestParam String user_Id) {
        ReturnValue rv = new ReturnValue(true, "", getUserController().getShoppingCart(user_Id));
        return rv;
    }

//    public boolean containsStore(String user_id, String storeID) {
//        return getUserController().containsStore(user_id, storeID);
//    }

    @DeleteMapping("/cart")
    public ReturnValue removeProductFromCart(@Valid @RequestBody MockSmallProduct mockSmallProduct) {

        getUserController().removeProduct(mockSmallProduct.getUser_id(), mockSmallProduct.getProductID(), mockSmallProduct.getStoreID(), mockSmallProduct.getAmount());

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    //auction or bid false for now
    @PostMapping("/cart/product")
    public ReturnValue addProductFromCart(@Valid @RequestBody MockSmallProduct mockProduct,
                                          @RequestParam boolean auctionOrBid) {

        InventoryProtector inventoryProtector = sc.getInventoryProtector(mockProduct.getStoreID());
        getUserController().addProduct(mockProduct.getUser_id(), mockProduct.getProductID(), mockProduct.getStoreID(), mockProduct.getAmount(), inventoryProtector, auctionOrBid);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;

    }


//    public String getCartInventory(String user_id) {
//        return getUserController().getCartInventory(user_id);
//    }

    @PostMapping("/cart/purchase")
    public ReturnValue purchaseCart(@RequestParam String user_id,
                                    @RequestParam PaymentNames payment,
                                    @RequestParam DeliveryNames delivery) {

        float a = getUserController().purchaseCart(user_id, new ExternalConnectionHolder(delivery, payment));
        ReturnValue rv = new ReturnValue(true, "", a);
        return rv;
    }

    /// Store controller

    @PostMapping("/store")
    public ReturnValue addNewProductToStore(@Valid @RequestBody MockFullProduct mockProduct) throws NoPermissionException {
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
                                    @RequestParam String storeName) {

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
    public ReturnValue editProduct(@RequestParam String productId, @Valid @RequestBody MockFullProduct mockProduct) throws NoPermissionException {
        userExistsAndLoggedIn(mockProduct.getUserId());
        getStoreController().editProduct(mockProduct.getStoreId(), mockProduct.getUserId(), productId, mockProduct.getSupply(), mockProduct.getProductName(), mockProduct.getPrice(), mockProduct.getCategory());
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    @DeleteMapping("/store/product")
    public ReturnValue deleteProductFromStore(@RequestParam String storeId,
                                              @RequestParam String userId,
                                              @RequestParam String productId) throws NoPermissionException {
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
    public ReturnValue addReviewToProduct(@Valid @RequestBody MockProductReview mockProd) {
        userExistsAndLoggedIn(mockProd.getUserId());
        getStoreController().addReviewToProduct(mockProd.getStoreId(), mockProd.getUserId(), mockProd.getProductId(), mockProd.getTitle(), mockProd.getBody(), mockProd.getRating());
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/forum/thread")
    public ReturnValue addNewThreadToForum(@RequestParam String storeId, @RequestParam String title, @RequestParam String userId) {
        userExistsAndLoggedIn(userId);


        ReturnValue rv = new ReturnValue(true, "", getStoreController().addNewThreadToForum(storeId, title, userId));
        return rv;
    }

    @PostMapping("/forum/message")
    public ReturnValue postMessageToForum(@RequestParam String storeId, @RequestParam String threadId, @RequestParam String userId, @RequestParam String message) throws NoPermissionException {
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
    public List<Product> SearchProductsAccordingName(@RequestParam String userId,@RequestParam String productName) {
        if (!IsGuest(userId))
            userExistsAndLoggedIn(userId);

        return getStoreController().SearchProductsAccordingName(productName);
    }


    @GetMapping("/search/category")

    public List<Product> SearchProductsAccordingCategory(@RequestParam String userId, @Valid @RequestBody MockCategories mockCategories) {

        if (!IsGuest(userId))
            userExistsAndLoggedIn(userId);
        return getStoreController().SearchProductsAccordingCategory(mockCategories.getCategories());

    }


    @GetMapping("/search/price")

    public List<Product> SearchProductsAccordingPrice(@RequestParam String userId,@RequestParam float fromPrice,@RequestParam float toPrice) {

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
    public ReturnValue deleteStore(@RequestParam String userId, @RequestParam String storeId) {
        userExistsAndLoggedIn(userId);
        getStoreController().deleteStore(userId, storeId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;

    }

    private void userExistsAndLoggedIn( String userId) {
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
    public ReturnValue getUserHistory( @RequestParam String userId) throws NoPermissionException {
        userExistsAndLoggedIn(userId);

        ReturnValue rv = new ReturnValue(true, "", History.getInstance().getUserHistory(userId));
        return rv;
    }
    private boolean IsGuest(@RequestParam String userId) {
        for (Guest g : getGuest_list()) {
            if (g.name.equals(userId)) {
                return true;
            }
        }
        return false;
    }


    private UserController getUserController() {
        return us;
    }

    private StoreController getStoreController() {
        return sc;
    }
}
