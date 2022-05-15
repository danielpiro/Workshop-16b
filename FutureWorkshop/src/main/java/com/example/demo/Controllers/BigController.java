package com.example.demo.Controllers;

import com.example.demo.Controllers.Mock.MockFullProduct;
import com.example.demo.Controllers.Mock.MockPermission;
import com.example.demo.Controllers.Mock.MockSmallPermission;
import com.example.demo.Controllers.Mock.MockSmallProduct;
import com.example.demo.ExternalConnections.Delivery.DeliveryNames;
import com.example.demo.ExternalConnections.Delivery.FedEx;
import com.example.demo.ExternalConnections.Delivery.UPS;
import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ExternalConnections.ExternalConnections;
import com.example.demo.ExternalConnections.Payment.MasterCard;
import com.example.demo.ExternalConnections.Payment.PaymentNames;
import com.example.demo.ExternalConnections.Payment.Visa;
import com.example.demo.GlobalSystemServices.Log;
import com.example.demo.History.PurchaseHistory;
import com.example.demo.ShoppingCart.InventoryProtector;
import com.example.demo.ShoppingCart.ShoppingCart;
import com.example.demo.Store.Product;
import com.example.demo.StorePermission.Permission;
import com.example.demo.StorePermission.StoreRoles;
import com.example.demo.User.Guest;
import com.example.demo.User.Subscriber;
//import com.example.demo.dto.AdminDto;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api")
public class BigController {
    private StoreController sc;
    private UserController us;
    Log my_log = Log.getLogger();



    //todo add Exception handler
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
    @DeleteMapping ("/users")
    public boolean deleteUser(@RequestParam String dan,
                              @RequestParam String whosBeingDeleted) throws NoPermissionException {

        my_log.logger.info("user" + dan + "is trying to delete user" + whosBeingDeleted);
        sc.removeRoleInHierarchy(whosBeingDeleted);

        return getUserController().deleteUser(dan, whosBeingDeleted);
    }

    @Async
    @PostMapping ("/users/signup")
    public boolean sign_up(@RequestParam String user_name,
                           @RequestParam String password) {
        my_log.logger.info("user " + user_name + " is trying to sign up");
        return getUserController().sign_up(user_name, password);
    }

    @Async
    @PostMapping ("/users/login")
    public boolean login(@RequestParam String user_name_login,
                         @RequestParam String password) {
        return getUserController().login(user_name_login, password);
    }


    @PostMapping ("/users/logout")
    public boolean logout(@RequestParam String user_name) {
        return getUserController().logout(user_name);
    }

    @PostMapping ("/market")
    public void sendComplaint(@RequestParam String userId, @RequestParam String StoreName, @RequestParam String complaint) {
        getUserController().sendComplaint(userId, StoreName, complaint);
    }



    //todo return guest id, not the guest itself
    private Guest getGuest(  String id) {
        return getUserController().getGuest(id);
    }

//    public String addGuest() {
//        return getUserController().addGuest().name;
//    }


    @PostMapping ("/users")
    public String GuestExitSystem(@RequestParam String guestId) {
        return getUserController().GuestExitSystem(guestId);
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

    @PostMapping ("/cart")
    public ShoppingCart getShoppingCart(@RequestParam String user_Id) {
        return getUserController().getShoppingCart(user_Id);
    }

//    public boolean containsStore(String user_id, String storeID) {
//        return getUserController().containsStore(user_id, storeID);
//    }

    @DeleteMapping ("/cart")
    public int removeProductFromCart(@Valid @RequestBody MockSmallProduct mockSmallProduct) {

        return getUserController().removeProduct(mockSmallProduct.getUser_id(), mockSmallProduct.getProductID(), mockSmallProduct.getStoreID(), mockSmallProduct.getAmount());
    }

    //auction or bid false for now
    @PostMapping ("/cart/product")
    public int addProductFromCart(@Valid @RequestBody MockSmallProduct mockProduct,
                                  @RequestParam boolean auctionOrBid) {
        InventoryProtector inventoryProtector = sc.getInventoryProtector(mockProduct.getStoreID());
        return getUserController().addProduct(mockProduct.getUser_id(), mockProduct.getProductID(), mockProduct.getStoreID(), mockProduct.getAmount(), inventoryProtector, auctionOrBid);
    }


//    public String getCartInventory(String user_id) {
//        return getUserController().getCartInventory(user_id);
//    }

    @PostMapping ("/cart/purchase")
    public float purchaseCart(@RequestParam String user_id,
                              @RequestParam PaymentNames payment,
                              @RequestParam DeliveryNames delivery) {
        return getUserController().purchaseCart(user_id, new ExternalConnectionHolder(delivery, payment));
    }

    /// Store controller

    @PostMapping ("/store")
    public void addNewProductToStore(@Valid @RequestBody MockFullProduct mockProduct) throws NoPermissionException {
        if (getUserController().checkIfUserExists(mockProduct.getUserId()) && getUserController().checkIfUserIsLoggedIn(mockProduct.getUserId()))
            getStoreController().addNewProduct(mockProduct.getStoreId(), mockProduct.getUserId(), mockProduct.getProductName(), mockProduct.getPrice(), mockProduct.getSupply(), mockProduct.getCategory());
        else
            throw new IllegalArgumentException("couldn't add new product because the given userId doesn't exist or is not logged in");
    }

    @DeleteMapping ("/permissions")
    public void removeSomePermissions(@Valid @RequestBody MockPermission mockPermission) throws NoPermissionException {
        if (getUserController().checkIfUserExists(mockPermission.getUserIdRemoving()) && getUserController().checkIfUserIsLoggedIn(mockPermission.getUserIdRemoving())) {
            sc.removeSomePermissions(mockPermission.getStoreId(), mockPermission.getUserIdRemoving(), mockPermission.getUserAffectedId(), mockPermission.getPerToRemove());
        } else
            throw new IllegalArgumentException("couldn't add new product because the given userId doesn't exist or is not logged in");
    }

    @PostMapping ("/store/open")
    public String openNewStore(@RequestParam String userId,
                               @RequestParam String storeName) {
        if (getUserController().checkIfUserExists(userId) && getUserController().checkIfUserIsLoggedIn(userId)) {
            List<String> managers = new ArrayList<>();
            managers.add(userId);
            return getStoreController().openNewStore(storeName, managers);
        } else
            throw new IllegalArgumentException("couldn't open store because the given userId doesn't exist or is not logged in");
    }

    @PostMapping ("/store/freeze")
    public void unfreezeStore(@RequestParam String storeId,
                              @RequestParam String userId) throws NoPermissionException {
        if (getUserController().checkIfUserExists(userId) && getUserController().checkIfUserIsLoggedIn(userId))
            getStoreController().unfreezeStore(storeId, userId);
        else
            throw new IllegalArgumentException("couldn't close store because the given userId doesn't exist or is not logged in");
    }

    @PostMapping ("/store/unfreeze")
    public void freezeStore( @RequestParam String storeId,
                             @RequestParam  String userId) throws NoPermissionException {
        if (getUserController().checkIfUserExists(userId) && getUserController().checkIfUserIsLoggedIn(userId))
            getStoreController().freezeStore(storeId, userId);
        else
            throw new IllegalArgumentException("couldn't open store because the given userId doesn't exist or is not logged in");
    }

    public List<StoreRoles> getInfoOnManagersOwners(@RequestParam String storeId,
                                                    @RequestParam String userId) throws NoPermissionException {
        if (!getUserController().checkIfUserExists(userId) && getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return getStoreController().getInfoOnManagersOwners(storeId, userId);
    }

    @PostMapping ("/store/product")
        public void editProduct( @RequestParam String productId, @Valid @RequestBody MockFullProduct mockProduct) throws NoPermissionException {
        if (getUserController().checkIfUserExists(mockProduct.getUserId()) && getUserController().checkIfUserIsLoggedIn(mockProduct.getUserId()))
            getStoreController().editProduct(mockProduct.getStoreId(), mockProduct.getUserId(), productId, mockProduct.getSupply(), mockProduct.getProductName(), mockProduct.getPrice(), mockProduct.getCategory());
        else
            throw new IllegalArgumentException("couldn't edit product  because the given userId doesn't exist or is not logged in");
    }


    @DeleteMapping ("/store/product")
    public void deleteProductFromStore(@RequestParam String storeId,
                                       @RequestParam String userId,
                                       @RequestParam  String productId) throws NoPermissionException {
        if (getUserController().checkIfUserExists(userId) && getUserController().checkIfUserIsLoggedIn(userId))
            getStoreController().deleteProduct(storeId, userId, productId);
        else
            throw new IllegalArgumentException("couldn't delete product  because the given userId doesn't exist or is not logged in");
    }

    @DeleteMapping ("/store/permissions")
    public void removePermissionTo(@RequestParam String storeId, @RequestParam String userIdRemoving,@RequestParam  String UserAffectedId) throws NoPermissionException {
        if (getUserController().checkIfUserExists(userIdRemoving) && getUserController().checkIfUserExists(UserAffectedId) && getUserController().checkIfUserIsLoggedIn(userIdRemoving))
            getStoreController().removeRoleInHierarchy(storeId, userIdRemoving, UserAffectedId);
        else
            throw new IllegalArgumentException("couldn't remove permission because the given userId doesn't exist or is not logged in");
    }

    @PostMapping ("/owner")
    public void createOwner(@Valid @RequestBody MockSmallPermission mockPermission) throws NoPermissionException {
        if (getUserController().checkIfUserExists(mockPermission.getUserIdGiving()) && getUserController().checkIfUserExists(mockPermission.getUserGettingPermissionId()) && getUserController().checkIfUserIsLoggedIn(mockPermission.getUserIdGiving())) {
            getStoreController().createOwner(mockPermission.getStoreId(), mockPermission.getUserIdGiving(), mockPermission.getUserGettingPermissionId(), mockPermission.getPermissions());
        } else
            throw new IllegalArgumentException("couldn't give permission because the given userId doesn't exist or is not logged in");
    }

    @PostMapping ("/manager")
    public void createManager(@RequestParam String storeId,@RequestParam String userIdGiving,@RequestParam String UserGettingPermissionId) throws NoPermissionException {
        if (getUserController().checkIfUserExists(userIdGiving) && getUserController().checkIfUserExists(UserGettingPermissionId) && getUserController().checkIfUserIsLoggedIn(userIdGiving)) {
            getStoreController().createManager(storeId, userIdGiving, UserGettingPermissionId);
        } else
            throw new IllegalArgumentException("couldn't give permission because the given userId doesn't exist or is not logged in");
    }


    @PostMapping ("/product")

    public void addReviewToProduct(@RequestParam String storeId,@RequestParam String userId,@RequestParam String productId, @RequestParam String Title,@RequestParam String Body,@RequestParam float rating) {
        if (!getUserController().checkIfUserExists(userId) && getUserController().checkIfUserIsLoggedIn(userId))
            throw new IllegalArgumentException("User doesn't exist or is not logged in or is not logged in");
        getStoreController().addReviewToProduct(storeId, userId, productId, Title, Body, rating);
    }

    @PostMapping ("/forum/thread")
    public String addNewThreadToForum(@RequestParam String storeId,@RequestParam String title,@RequestParam String userId) {
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return getStoreController().addNewThreadToForum(storeId, title, userId);
    }

    @PostMapping ("/forum/message")
    public void postMessageToForum(@RequestParam String storeId,@RequestParam String threadId,@RequestParam String userId,@RequestParam String message) throws NoPermissionException {
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
        }
        getStoreController().RolePostMessageToForum(storeId, threadId, userId, message);
    }


    //todo why iterate over guest list???
    public HashMap<String, List<Product>> getAllProductsAndStores(String userId) {
        for (Guest g : getGuest_list()) {
            if (g.name.equals(userId)) {
                return getStoreController().getAllProductsAndStores();
            }
        }
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return getStoreController().getAllProductsAndStores();
    }

    public List<Product> SearchProductsAccordingName(String userId, String productName) {
        for (Guest g : getGuest_list()) {
            if (g.name.equals(userId)) {
                getStoreController().SearchProductsAccordingName(productName);
            }
        }
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            return null;
        }
        return getStoreController().SearchProductsAccordingName(productName);
    }

    public List<Product> SearchProductsAccordingCategory(String userId, List<String> categories) {
        for (Guest g : getGuest_list()) {
            if (g.name.equals(userId)) {
                getStoreController().SearchProductsAccordingCategory(categories);
            }
        }
        if (!getUserController().checkIfUserExists(userId) && getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return getStoreController().SearchProductsAccordingCategory(categories);

    }

    public List<Product> SearchProductsAccordingPrice(String userId, float fromPrice, float toPrice) {
        for (Guest g : getGuest_list()) {
            if (g.name.equals(userId)) {
                getStoreController().SearchProductsAccordingPrice(fromPrice, toPrice);
            }
        }
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            return null;
        }
        return getStoreController().SearchProductsAccordingPrice(fromPrice, toPrice);

    }

    public List<Product> SearchProductsAccordingRating(String userId, float productRating) {
        for (Guest g : getGuest_list()) {
            if (g.name.equals(userId)) {
                return getStoreController().SearchProductsAccordingRating(productRating);
            }
        }
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return getStoreController().SearchProductsAccordingRating(productRating);
    }

    @DeleteMapping ("/store")
    public void deleteStore(@RequestParam String userId,@RequestParam  String storeId) {
        getStoreController().deleteStore(userId, storeId);
    }


    //todo add user version!
    @GetMapping ("/history")
    public List<PurchaseHistory> getStoreHistory(@RequestParam String storeId, @RequestParam String userId) throws NoPermissionException {
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return sc.getStoreHistory(storeId, userId);
    }


    private UserController getUserController() {
        return us;
    }

    private StoreController getStoreController() {
        return sc;
    }
}
