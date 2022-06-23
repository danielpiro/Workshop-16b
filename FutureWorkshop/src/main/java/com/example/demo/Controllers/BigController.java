package com.example.demo.Controllers;

import com.example.demo.Controllers.model.realTimeNotification;
import com.example.demo.CustomExceptions.Exception.NotifyException;
import com.example.demo.CustomExceptions.Exception.StorePolicyViolatedException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.CustomExceptions.Exception.UserException;
import com.example.demo.ExternalConnections.Old.Delivery.DeliveryNames;
import com.example.demo.ExternalConnections.Old.Delivery.Payment.PaymentNames;
import com.example.demo.ExternalConnections.Old.ExternalConnectionHolder;
import com.example.demo.ExternalConnections.Old.ExternalConnections;
import com.example.demo.History.PurchaseHistory;
import com.example.demo.Mock.*;
import com.example.demo.CustomExceptions.ExceptionHandler.ReturnValue;

import com.example.demo.GlobalSystemServices.Log;
import com.example.demo.History.History;
import com.example.demo.NotificationsManagement.ComplaintNotification;
import com.example.demo.NotificationsManagement.NotificationManager;
import com.example.demo.NotificationsManagement.NotificationSubject;
import com.example.demo.NotificationsManagement.StoreNotification;
import com.example.demo.ShoppingCart.InventoryProtector;
import com.example.demo.ShoppingCart.ShoppingCart;
import com.example.demo.Store.Product;
import com.example.demo.Store.ProductsCategories;
import com.example.demo.Store.Store;
import com.example.demo.Store.StorePurchase.Discounts.Discount;
import com.example.demo.Store.StorePurchase.Discounts.DiscountBuilder;
import com.example.demo.Store.StorePurchase.Discounts.MaxDiscount;
import com.example.demo.Store.StorePurchase.Policies.Policy;
import com.example.demo.Store.StorePurchase.Policies.PolicyBuilder;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.StorePermission.Permission;
import com.example.demo.StorePermission.StoreRoles;
import com.example.demo.User.Guest;
import com.example.demo.User.Subscriber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.MediaType;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.naming.NoPermissionException;
import javax.transaction.NotSupportedException;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@EnableScheduling
@CrossOrigin(maxAge = 3600)
@RestController
@EnableWebMvc
@RequestMapping("/api")
public class BigController {
    private StoreController sc;
    private UserController us;
    private PolicyBuilder policyBuilder;
    Log my_log = Log.getLogger();


    //    public BigController(UserController us , StoreController sc) throws IOException {
//        this.us = us;
//        this.sc = sc;
//    }
    public BigController() throws IOException, UserException, NoPermissionException, SupplyManagementException, InterruptedException {
        this.us = new UserController();
        this.sc = new StoreController();
        this.policyBuilder = new PolicyBuilder();
        //initiateExternalConnections();
        NotificationManager.buildNotificationManager(us);
        initializeSystem();
        my_log.info("System Started");
    }
//
//    public void initiateExternalConnections() {
//        ExternalConnections externalConnections = ExternalConnections.getInstance();
//        externalConnections.addPayment(new com.example.demo.ExternalConnections.Old.Delivery.Payment.Visa());
//        externalConnections.addPayment(new com.example.demo.ExternalConnections.Old.Delivery.Payment.MasterCard());
//        externalConnections.addDelivery(new FedEx());
//        externalConnections.addDelivery(new UPS());
//    }

    @PostMapping("/users/add/admin")
    public ReturnValue addSystemAdminApi(@RequestHeader("Authorization") String sessionID, @RequestParam String whoIsAdding, @RequestParam String user_toMakeAdmin) throws UserException {
        if (!validateSessionID(sessionID, whoIsAdding)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        getUserController().addSystemAdmin(whoIsAdding, user_toMakeAdmin);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    private List<String> initializeUsers() throws UserException, InterruptedException {
        return getUserController().initialize();
    }

    /**
     * @param user_id
     * @param payment
     * @param delivery
     * @return price of cart   and doesn't buy the cart
     * @throws StorePolicyViolatedException
     * @throws UserException
     */
    @PostMapping("/cart/price")
    public ReturnValue getPriceOfCartDiscountApi(@RequestHeader("Authorization") String sessionID, @RequestParam String user_id, @RequestParam com.example.demo.ExternalConnections.Old.Delivery.Payment.PaymentNames payment,
                                                 @RequestParam DeliveryNames delivery) throws StorePolicyViolatedException, UserException, JsonProcessingException, InterruptedException {
        if (!validateSessionID(sessionID, user_id)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("current thread func" + Thread.currentThread().getId());
        float afterDiscount = getUserController().getPriceOfCartAfterDiscount(user_id, new ExternalConnectionHolder(delivery, payment));
        float beforeDiscount = getUserController().getPriceOfCartBeforeDiscount(user_id, new ExternalConnectionHolder(delivery, payment));

        String json = String.format("{\"priceBeforeDiscount\":\"%s\",\"priceAfterDiscount\":\"%s\"}", beforeDiscount, afterDiscount);
        return new ReturnValue(true, "", objectMapper.readTree(json));

    }

    @GetMapping("/online/amount")
    public ReturnValue getOnlineUsersNumApi(@RequestHeader("Authorization") String sessionID, @RequestParam String user_id) throws ExecutionException, InterruptedException, UserException {
        if (!validateSessionID(sessionID, user_id)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        return new ReturnValue(true, "", getUserController().getOnlineUsersNum());
    }

    @GetMapping("/registered/amount")
    public ReturnValue getRegisteredUsersNumApi(@RequestHeader("Authorization") String sessionID, @RequestParam String user_id) throws UserException {
        if (!validateSessionID(sessionID, user_id)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        ReturnValue rv = new ReturnValue(true, "", getUserController().getRegisteredUsersNum());
        return rv;
    }

    @PostMapping("/users/delete")
    public ReturnValue deleteUserApi(@RequestHeader("Authorization") String sessionID, @RequestParam String isDeleting,
                                     @RequestParam String whosBeingDeleted) throws NoPermissionException, UserException {
        if (!validateSessionID(sessionID, isDeleting)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        my_log.info("user" + isDeleting + "is trying to delete user" + whosBeingDeleted);
        //sc.removeRoleInHierarchy(whosBeingDeleted);
        if (checkIfUserHaveRoleInStore(whosBeingDeleted)) {
            throw new NoPermissionException("cant delete user with store role");
        }
        getUserController().deleteUser(isDeleting, whosBeingDeleted);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    @PostMapping("/users/signup")
    public ReturnValue signupApi(@RequestParam String user_name,
                                 @RequestParam String password) throws UserException {
        my_log.info("user " + user_name + " is trying to sign up");
        getUserController().sign_up(user_name, password);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/users/login")
    public ReturnValue loginUserApi(@RequestParam String userNameLogin,
                                    @RequestParam String password) throws UserException, InterruptedException {
        ReturnValue rv = new ReturnValue(true, "", getUserController().login(userNameLogin, password));
        return rv;
    }

    @PostMapping("/guest/login")
    public ReturnValue loginGuestApi(@RequestParam String guestId,
                                     @RequestParam String userNameLogin,
                                     @RequestParam String password) throws UserException, InterruptedException {
        ReturnValue rv = new ReturnValue(true, "", getUserController().login(guestId, userNameLogin, password));
        return rv;
    }

    @PostMapping("/users/logout")
    public ReturnValue logoutApi(@RequestParam String user_name) throws UserException, InterruptedException {
        ReturnValue rv = new ReturnValue(true, "", getUserController().logout(user_name));
        return rv;

    }

    @PostMapping("/market")
    public ReturnValue sendComplaintApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String StoreName, @RequestParam String complaint) throws UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    @GetMapping("/market/guest")
    public ReturnValue addGuestApi() {
        String guestName = getUserController().addGuest().name;
        ReturnValue rv = new ReturnValue(true, "", guestName.concat("#").concat(getUserController().getGuest(guestName).getSessionId()));
        return rv;
    }


    @PostMapping("/users")
    public ReturnValue GuestExitSystemApi(@RequestHeader("Authorization") String sessionID, @RequestParam String user_id, @RequestParam String guestId) throws UserException {
        if (!validateSessionID(sessionID, user_id)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        getUserController().GuestExitSystem(guestId);

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    private Subscriber getSystemAdminApi() {
        return getUserController().getSystemAdmin();
    }


    @PostMapping("/cart")
    public ReturnValue getShoppingCartApi(@RequestHeader("Authorization") String sessionID, @RequestParam String user_Id) throws Exception {
        if (!validateSessionID(sessionID, user_Id)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        ShoppingCart shoppingCart = getUserController().getShoppingCart(user_Id);

        List<Object> products = new ArrayList<>();
        List<Object> products2 = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (var basket : shoppingCart.basketCases.entrySet()) {
            for (var pamount : basket.getValue().productAmount.entrySet()) {
                Product product = sc.getProductById(basket.getKey(), pamount.getKey());

                String json = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"price\":\"%s\",\"amount\":\"%d\",\"category\":\"%s\",\"storeId\":\"%s\",\"quantity\":\"%s\"}", product.getId(), product.getName(), product.getPrice(), pamount.getValue(), product.getCategory(), basket.getKey(), product.getSupply());
                products.add(objectMapper.readTree(json));

            }
            if (!products.isEmpty()) {
                products2.add(objectMapper.readTree(String.format("{\"%s\":%s}", basket.getKey(), products)));
            }
            products.clear();

        }
        ReturnValue rv = new ReturnValue(true, "", products2);
        return rv;

    }


    public ShoppingCart getShoppingCartTests(String user_Id) throws Exception {
        return getUserController().getShoppingCart(user_Id);
    }


//    public boolean containsStore(String user_id, String storeID) {
//        return getUserController().containsStore(user_id, storeID);
//    }

    @PostMapping(value = "/cart/delete", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue removeProductFromCartApi(@RequestHeader("Authorization") String sessionID, @RequestParam String user_id, @Valid @RequestBody MockSmallProduct mockSmallProduct) throws UserException {
        if (!validateSessionID(sessionID, user_id)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        getUserController().removeProduct(mockSmallProduct.getUser_id(), mockSmallProduct.getProductID(), mockSmallProduct.getStoreID(), mockSmallProduct.getAmount());

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    //auction or bid false for now
    @PostMapping(value = "/cart/product", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue addProductFromCartApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestBody MockSmallProduct mockProduct,
                                             @RequestParam boolean auctionOrBid) throws UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        InventoryProtector inventoryProtector = sc.getInventoryProtector(mockProduct.getStoreID());
        getUserController().addProduct(mockProduct.getUser_id(), mockProduct.getProductID(), mockProduct.getStoreID(), mockProduct.getAmount(), inventoryProtector, auctionOrBid);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;

    }

    public boolean removePaymentService(com.example.demo.ExternalConnections.Old.Delivery.Payment.PaymentNames payment) {
        return ExternalConnections.getInstance().removePayment(payment);
    }

    //todo add check price
    @PostMapping("/cart/purchase")
    public ReturnValue purchaseCartApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId,
                                       @RequestParam com.example.demo.ExternalConnections.Old.Delivery.Payment.PaymentNames payment,
                                       @RequestParam DeliveryNames delivery, @RequestParam String nameHolder, @RequestParam String address,@RequestParam  String city, @RequestParam String country,@RequestParam  String zip ,
                                       @RequestParam String holder,@RequestParam  String cardNumber,@RequestParam  String expireDate,@RequestParam  int cvv, @RequestParam String id) throws Exception {

        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        float a = getUserController().purchaseCart(userId, new ExternalConnectionHolder(delivery, payment), nameHolder,  address,  city,  country,  zip ,
                holder,  cardNumber,  expireDate,  cvv,  id);
        ReturnValue rv = new ReturnValue(true, "", a);
        return rv;
    }


    /// Store controller
    @PostMapping(value = "/store", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue addNewProductToStoreApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestBody MockFullProduct mockProduct) throws NoPermissionException, SupplyManagementException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(mockProduct.getUserId());

        String ans = getStoreController().addNewProduct(mockProduct.getStoreId(), mockProduct.getUserId(), mockProduct.getProductName(), mockProduct.getPrice(), mockProduct.getSupply(), mockProduct.getCategory());

        ReturnValue rv = new ReturnValue(true, "", ans);
        return rv;

    }

    @PostMapping(value = "/permissions/delete", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue removeSomePermissionsApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestBody MockPermission mockPermission) throws NoPermissionException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(mockPermission.getUserIdRemoving());

        sc.removeSomePermissions(mockPermission.getStoreId(), mockPermission.getUserIdRemoving(), mockPermission.getUserAffectedId(), mockPermission.getPerToRemove());

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/store/open")
    public ReturnValue openNewStoreApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId,
                                       @RequestParam String storeName) throws NoPermissionException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);
        List<String> managers = new ArrayList<>();
        managers.add(userId);
        ReturnValue rv = new ReturnValue<String>(true, "", getStoreController().openNewStore(storeName, managers));
        return rv;

    }

    @PostMapping("/store/freeze")
    public ReturnValue unfreezeStoreApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId
    ) throws NoPermissionException, UserException, NotifyException, SupplyManagementException, IOException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);
        getStoreController().unfreezeStore(storeId, userId);

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/store/unfreeze")
    public ReturnValue freezeStoreApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId) throws NoPermissionException, UserException, NotifyException, SupplyManagementException, IOException {

        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        return unfreezeStore(userId, storeId);
    }


//not in use- if front want to use contact guy
//    /**
//     * @param storeId the store you want manager info about
//     * @param userIdRequesting  the requesting user, will make sure you are logged in
//     *                and that you have permission in the specific store
//     * @return
//     * @throws NoPermissionException
//     * @throws UserException
//     */

    public ReturnValue getInfoOnManagersOwnersForTests(String sessionID, String storeId,
                                                       String userIdRequesting) throws NoPermissionException, UserException {
        if (!validateSessionID(sessionID, userIdRequesting)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userIdRequesting);
        List<StoreRoles> storeRoles = getStoreController().getInfoOnManagersOwners(storeId, userIdRequesting);

        ReturnValue rv = new ReturnValue(true, "", storeRoles);
        return rv;
    }

    @GetMapping("store/allRoles")
    public ReturnValue getManagersOwnersOfStoreApi(@RequestHeader("Authorization") String sessionID, String storeId,
                                                   String userIdRequesting) throws NoPermissionException, UserException, JsonProcessingException {
        if (!validateSessionID(sessionID, userIdRequesting)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userIdRequesting);
        List<StoreRoles> storeRoles = getStoreController().getInfoOnManagersOwners(storeId, userIdRequesting);
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> managersUsers = new ArrayList<>();
        List<String> ownersUsers = new ArrayList<>();
        for (StoreRoles sr : storeRoles) {
            if (sr.getTitle().equals("manger")) {
                managersUsers.add(sr.getUserId());
            } else {
                ownersUsers.add(sr.getUserId());
            }
        }


        ReturnValue rv = new ReturnValue(true, "", objectMapper.readTree(
                String.format("{\"managers\":\"%s\",\"owners\":\"%s\"}", managersUsers, ownersUsers)));
        return rv;
    }

    /**
     * @param userId
     * @return store ids and permmitions of user in store (for managers)
     * @throws NoPermissionException
     * @throws UserException
     */
    @GetMapping("store/manager/permmitions")
    public ReturnValue getStoresManagedByUserApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws NoPermissionException, UserException, JsonProcessingException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        List<Object> storePermissions = getStorePermissionToReturn(userId, true);

        ReturnValue rv = new ReturnValue(true, "", storePermissions);
        return rv;
    }

    /**
     * @param userId
     * @param storeId
     * @return store id and permmitions of user in store (for managers)
     * @throws NoPermissionException
     * @throws UserException
     * @throws JsonProcessingException
     */
    @GetMapping("store/manager/permmitions/OfStore")
    public ReturnValue getStoresManagedByUserApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId) throws NoPermissionException, UserException, JsonProcessingException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        List<Object> storePermissions = getStorePermissionToReturn(userId, storeId, true);

        ReturnValue rv = new ReturnValue(true, "", storePermissions);
        return rv;
    }

    /**
     * @param userId
     * @return store ids and permmitions of user in store (for owners)
     * @throws NoPermissionException
     * @throws UserException
     */
    @GetMapping("store/owner/permmitions")
    public ReturnValue getStoresOwnedByUserApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws NoPermissionException, UserException, JsonProcessingException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        List<Object> storePermissions = getStorePermissionToReturn(userId, false);
        ReturnValue rv = new ReturnValue(true, "", storePermissions);
        return rv;
    }

    /**
     * @param userId
     * @param storeId
     * @return store  and permmitions of user in store (for owners)
     * @throws NoPermissionException
     * @throws UserException
     * @throws JsonProcessingException
     */
    @GetMapping("store/owner/permmitions/OfStore")
    public ReturnValue getStoresOwnedByUserApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId) throws NoPermissionException, UserException, JsonProcessingException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        List<Object> storePermissions = getStorePermissionToReturn(userId, storeId, false);
        ReturnValue rv = new ReturnValue(true, "", storePermissions);
        return rv;
    }

    private List<Object> getStorePermissionToReturn(String user, boolean ownerOrManager) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Object> storePermissions = new ArrayList<>();
        List<Store> stores = ownerOrManager ? sc.getStoreManagerByUser(user) : sc.getStoreOwnerByUser(user);
        for (Store store : stores) {
            List<Permission> permissions = sc.getUserPermission(store.getId(), user);
            storePermissions.add(
                    objectMapper.readTree(
                            String.format("{\"storeId\":\"%s\",\"permission\":\"%s\"}", store.getId(), permissions)));
        }
        return storePermissions;
    }

    private List<Object> getStorePermissionToReturn(String user, String storeId, boolean ownerOrManager) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Object> storePermissions = new ArrayList<>();
        Store store = ownerOrManager ? sc.getStoreManagerByUser(user, storeId) : sc.getStoreOwnerByUser(user, storeId);
        List<Permission> permissions = new ArrayList<>();
        if (store != null) {
            permissions = sc.getUserPermission(store.getId(), user);
        }
        storePermissions.add(
                objectMapper.readTree(
                        String.format("{\"storeId\":\"%s\",\"permission\":\"%s\"}", store.getId(), permissions)));

        return storePermissions;
    }

    @PostMapping(value = "/store/product", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue editProductApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String productId, @Valid @RequestBody MockFullProduct mockProduct) throws NoPermissionException, SupplyManagementException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(mockProduct.getUserId());
        getStoreController().editProduct(mockProduct.getStoreId(), mockProduct.getUserId(), productId, mockProduct.getSupply(), mockProduct.getProductName(), mockProduct.getPrice(), mockProduct.getCategory());
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    private List<Object> parseComplaints(List<ComplaintNotification> complaintNotifications) throws JsonProcessingException {
        List<Object> notifiParsed = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (ComplaintNotification cn : complaintNotifications) {
            String json = String.format("{\"sentFrom\":\"%s\",\"subject\":\"%s\",\"Title\":\"%s\",\"Body\":\"%s\",\"read\":\"%s\",\"id\":\"%s\"}", cn.getSentFrom(), cn.getSubject(), cn.getTitle(), cn.getBody(), cn.isRead(), cn.getId());
            notifiParsed.add(objectMapper.readTree(json));
        }
        return notifiParsed;
    }

    private List<Object> parseStoreNotification(List<StoreNotification> storeNotifications) throws JsonProcessingException {
        List<Object> notifiParsed = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (StoreNotification sn : storeNotifications) {
            String json = String.format("{\"sentFrom\":\"%s\",\"subject\":\"%s\",\"Title\":\"%s\",\"Body\":\"%s\",\"read\":\"%s\",\"id\":\"%s\"}", sn.getSentFrom(), sn.getSubject(), sn.getTitle(), sn.getBody(), sn.isRead(), sn.getId());
            notifiParsed.add(objectMapper.readTree(json));
        }
        return notifiParsed;
    }

    //    public void sendComplaintToAdmins(String senderId, ComplaintNotification complaintNotification) throws UserException {
// String sentFrom, NotificationSubject subject, String title, String body
    @PostMapping("/complaints")
    public ReturnValue sendComplaintToAdminsApi(@RequestHeader("Authorization") String sessionID, @RequestParam String senderId, @RequestParam String subject, @RequestParam String title, @RequestParam String body) throws UserException {
        if (!validateSessionID(sessionID, senderId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        getUserController().sendComplaintToAdmins(senderId, new ComplaintNotification(senderId, NotificationSubject.valueOf(subject), title, body));
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/store/product/delete")
    public ReturnValue deleteProductFromStoreApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId,
                                                 @RequestParam String productId) throws NoPermissionException, SupplyManagementException, UserException, NotifyException, IOException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);
        getStoreController().deleteProduct(storeId, userId, productId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/store/permissions/delete")
    public ReturnValue removePermissionToApi(@RequestHeader("Authorization") String sessionID, @RequestParam String storeId, @RequestParam String userIdRemoving, @RequestParam String UserAffectedId) throws NoPermissionException, UserException, NotifyException {
        if (!validateSessionID(sessionID, userIdRemoving)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        if (getUserController().checkIfUserExists(userIdRemoving) && getUserController().checkIfUserExists(UserAffectedId) && getUserController().checkIfUserIsLoggedIn(userIdRemoving))
            getStoreController().removeRoleInHierarchy(storeId, userIdRemoving, UserAffectedId);
        else
            throw new IllegalArgumentException("couldn't remove permission because the given userId doesn't exist or is not logged in");
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping(value = "/owner/create")
    public ReturnValue createOwnerApi(@RequestHeader("Authorization") String sessionID, @RequestParam String storeId, @RequestParam String userIdGiving, @RequestParam String UserGettingPermissionId, @RequestParam String permissions) throws NoPermissionException, UserException, NotifyException {
        if (!validateSessionID(sessionID, userIdGiving)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        if (getUserController().checkIfUserExists(userIdGiving) && getUserController().checkIfUserExists(UserGettingPermissionId.trim()) && getUserController().checkIfUserIsLoggedIn(userIdGiving)) {
            List<Permission> finalPermissions = new ArrayList<>();
            List<String> permissionsList = Arrays.asList(permissions.split(","));
            for (String perString : permissionsList) {
                if (perString.length() > 0) {
                    finalPermissions.add(Permission.valueOf(perString));
                }
            }
            getStoreController().createOwner(storeId, userIdGiving, UserGettingPermissionId.trim(), finalPermissions);
        } else
            throw new IllegalArgumentException("couldn't give permission because the given userId doesn't exist or is not logged in");
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/manager")
    public ReturnValue createManagerApi(@RequestHeader("Authorization") String sessionID, @RequestParam String storeId, @RequestParam String userIdGiving, @RequestParam String UserGettingPermissionId) throws NoPermissionException, UserException, NotifyException {
        if (!validateSessionID(sessionID, userIdGiving)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        if (getUserController().checkIfUserExists(userIdGiving) && getUserController().checkIfUserExists(UserGettingPermissionId) && getUserController().checkIfUserIsLoggedIn(userIdGiving)) {
            getStoreController().createManager(storeId, userIdGiving, UserGettingPermissionId);
        } else
            throw new IllegalArgumentException("couldn't give permission because the given userId doesn't exist or is not logged in");
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    @PostMapping(value = "/product", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue addReviewToProductApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestBody MockProductReview mockProd) throws NoPermissionException, SupplyManagementException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(mockProd.getUserId());
        getStoreController().addReviewToProduct(mockProd.getStoreId(), mockProd.getUserId(), mockProd.getProductId(), mockProd.getTitle(), mockProd.getBody(), mockProd.getRating());
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/forum/thread")
    public ReturnValue addNewThreadToForumApi(@RequestHeader("Authorization") String sessionID, @RequestParam String storeId, @RequestParam String title, @RequestParam String userId) throws NoPermissionException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);


        ReturnValue rv = new ReturnValue(true, "", getStoreController().addNewThreadToForum(storeId, title, userId));
        return rv;
    }

    /**
     * @param usersIds - create stores for users and add products for them
     * @return stores ids
     */
    private List<String> initializeStores(List<String> usersIds) throws NoPermissionException, SupplyManagementException {
        List<String> output = new ArrayList<>();
        for (String userId : usersIds) {
            List<String> owner = new ArrayList<>();
            owner.add(userId);
            String StoreId = sc.openNewStore(userId + " store", owner);
            sc.addNewProduct(StoreId, userId, "p1", 1, 1, ProductsCategories.Apps$Games.toString());
            sc.addNewProduct(StoreId, userId, "p2", 2, 2, ProductsCategories.Appliances.toString());
            sc.addNewProduct(StoreId, userId, "p3", 3, 3, ProductsCategories.Other.toString());
            sc.addNewProduct(StoreId, userId, "p4", 4, 4, ProductsCategories.Apps$Games.toString());
            History.getInstance().insertRecord(userId, StoreId, "p1-forTest", 1, 2, LocalDateTime.now());
            History.getInstance().insertRecord(userId, StoreId, "p1-forTest", 1, 2, LocalDateTime.now());
            //addPolicyToStore(userId, StoreId);
            output.add(StoreId);
        }
        return output;
    }

    private void addPolicyToStore(String userId, String StoreId) throws NoPermissionException {
        Policy CartPolicy = policyBuilder.newCartPolicy(2);
        List<ProductsCategories> pc = new ArrayList<>();
        pc.add(ProductsCategories.Baby);
        Policy CategoryPolicy = policyBuilder.newCategoryPolicy(pc);
        Policy PricePolicy = policyBuilder.newPricePredicate(3);
        Random rand = new Random();
        if (rand.nextInt(10) < 4) {
            sc.addNewPolicy(StoreId, userId, CategoryPolicy);
        } else if (rand.nextInt(10) >= 4 && rand.nextInt(10) < 7) {
            sc.addNewPolicy(StoreId, userId, policyBuilder.AndGatePolicy(CartPolicy, CategoryPolicy));
        } else {
            sc.addNewPolicy(StoreId, userId, policyBuilder.OrGatePolicy(PricePolicy, policyBuilder.AndGatePolicy(CartPolicy, CategoryPolicy)));
        }

    }

    @PostMapping("/in/dashboard")
    public ReturnValue isInDashboardApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws InterruptedException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        if (us.checkIfUserExists(userId)){
            for (int i = 0; i < us.get_subscriber(userId).getRealTimestoreNotifications().size(); i++) {
                //remove before stress test
                Thread.sleep(1000);
                NotificationController.getInstance().sendNotification(new realTimeNotification(userId, us.get_subscriber(userId).getRealTimestoreNotifications().get(i).getSentFrom().getStoreName(), us.get_subscriber(userId).getRealTimestoreNotifications().get(i).getSubject().toString(), us.get_subscriber(userId).getRealTimestoreNotifications().get(i).getTitle(), us.get_subscriber(userId).getRealTimestoreNotifications().get(i).getBody(), new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Calendar.getInstance().getTime())));
            }

            us.get_subscriber(userId).resetNotification();
        }

        return new ReturnValue(true, "", null);
    }

    @PutMapping("/initializeSystem")
    public ReturnValue initializeSystem() throws UserException, SupplyManagementException, NoPermissionException, InterruptedException {

        List<String> users = initializeUsers();
        initializeStores(users);
        ReturnValue rv = new ReturnValue(true, "", users);
        return rv;
    }

    @PostMapping("/forum/message")
    public ReturnValue postMessageToForumApi(@RequestHeader("Authorization") String sessionID, @RequestParam String storeId, @RequestParam String threadId, @RequestParam String userId, @RequestParam String message) throws NoPermissionException, NotifyException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);
        getStoreController().RolePostMessageToForum(storeId, threadId, userId, message);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;

    }

    public HashMap<String, List<Product>> getAllProductsAndStoresTest() {

        HashMap<String, List<Product>> ans = getStoreController().getAllProductsAndStores();
        return ans;
    }

    @GetMapping("/store-products/all")
    public ReturnValue getAllProductsAndStoresWebApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws UserException, SupplyManagementException, NoPermissionException, JsonProcessingException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        HashMap<String, List<Product>> allProductsAndStores = getStoreController().getAllProductsAndStores();
        List<Object> products = new ArrayList<>();
        List<Object> products2 = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (var entry : allProductsAndStores.entrySet()) {
            for (var product : entry.getValue()) {
                String json = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"price\":\"%s\",\"quantity\":\"%s\",\"category\":\"%s\",\"storeId\":\"%s\"}", product.getId(), product.getName(), product.getPrice(), product.getSupply(), product.getCategory(), entry.getKey());
                products.add(objectMapper.readTree(json));
            }
            products2.add(objectMapper.readTree(String.format("{\"%s\":%s}", entry.getKey(), products)));
            products.clear();
        }
        ReturnValue rv = new ReturnValue(true, "", products2);
        return rv;
    }

    private boolean validateSessionID(String sessionID, String username) throws UserException {
        if (us.checkIfUserExists(username)){
            return us.get_subscriber(username).validateWebSocket(sessionID);
        }
        return us.getGuest(username).getSessionId().equals(sessionID);
    }

    public HashMap<String, List<Product>> getAllProductsAndStores() {
        return sc.getAllProductsAndStores();
    }

    @GetMapping("/products/all")
    public ReturnValue getAllProductsApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws JsonProcessingException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        HashMap<String, List<Product>> allProductsAndStores = getStoreController().getAllProductsAndStores();
        List<Object> products = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (var entry : allProductsAndStores.entrySet()) {
            for (var product : entry.getValue()) {
                String json = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"price\":\"%s\",\"quantity\":\"%s\",\"category\":\"%s\",\"storeId\":\"%s\"}", product.getId(), product.getName(), product.getPrice(), product.getSupply(), product.getCategory(), entry.getKey());
                products.add(objectMapper.readTree(json));
            }
        }
        ReturnValue rv = new ReturnValue(true, "", products);
        return rv;
    }

    @GetMapping("/store/all")
    public ReturnValue getAllStoresApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws JsonProcessingException, InterruptedException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        List<Store> allStores = getStoreController().getAllStores();
        List<Object> stores = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();


        for (var store : allStores) {
            stores.add(
                    objectMapper.readTree(
                            String.format("{\"storeId\":\"%s\",\"storeName\":\"%s\",\"storeState\":\"%s\",\"storeRating\":\"%s\"}", store.getId(), store.getStoreName(), store.getStoreState(), store.getStoreRating())));
        }
        ReturnValue rv = new ReturnValue(true, "", stores);
        return rv;
    }


    @GetMapping("/store/products")
    public ReturnValue getStoreProductsApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId) throws JsonProcessingException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        HashMap<String, List<Product>> allProductsAndStores = getStoreController().getAllProductsAndStores();
        List<Object> products = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (var entry : allProductsAndStores.entrySet()) {
            if (entry.getKey().equals(storeId)) {
                for (var product : entry.getValue()) {
                    String json = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"price\":\"%s\",\"quantity\":\"%s\",\"category\":\"%s\",\"storeId\":\"%s\"}", product.getId(), product.getName(), product.getPrice(), product.getSupply(), product.getCategory(), entry.getKey());
                    products.add(objectMapper.readTree(json));
                }
            }

        }
        ReturnValue rv = new ReturnValue(true, "", products);
        return rv;
    }


    @GetMapping("/search/name")
    public ReturnValue SearchProductsAccordingNameApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String productName) throws UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        System.out.println("");
        if (!IsGuest(userId))
            userExistsAndLoggedIn(userId);

        ReturnValue rv = new ReturnValue(true, "", getStoreController().SearchProductsAccordingName(productName));

        return rv;
    }


//    @GetMapping(value = "/search/category", consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public List<Product> SearchProductsAccordingCategory(@RequestParam String userId, @Valid @RequestBody MockCategories mockCategories) throws UserException {
//
//        if (!IsGuest(userId))
//            userExistsAndLoggedIn(userId);
//        return getStoreController().SearchProductsAccordingCategory(mockCategories.getCategories());
//
//    }
//
//
//    @GetMapping("/search/price")
//    public List<Product> SearchProductsAccordingPrice(@RequestParam String userId, @RequestParam float fromPrice, @RequestParam float toPrice) throws UserException {
//
//        if (!IsGuest(userId))
//            userExistsAndLoggedIn(userId);
//        return getStoreController().SearchProductsAccordingPrice(fromPrice, toPrice);
//
//    }
//
//    /**
//     * @param userId
//     * @param productRating - the minimum rating of a product
//     * @return
//     */
//    @GetMapping("/search/rating")
//    public List<Product> SearchProductsAccordingRating(@RequestParam String userId, @RequestParam float productRating) throws UserException {
//
//        if (!IsGuest(userId))
//            userExistsAndLoggedIn(userId);
//        return getStoreController().SearchProductsAccordingRating(productRating);
//    }

    @PostMapping("/store/delete")
    public ReturnValue deleteStoreApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId) throws NoPermissionException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);
        if (!us.checkIfAdmin(userId)) {
            throw new NoPermissionException("User is not admin");
        }
        getStoreController().deleteStore(userId, storeId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;

    }

    private void userExistsAndLoggedIn(String userId) throws UserException {
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.warning("User doesn't exist or is not logged in");
            throw new IllegalArgumentException("User doesn't exist or is not logged in");
        }
    }

    private void userExists(String userId) throws UserException {
        if (!getUserController().checkIfUserExists(userId)) {
            my_log.warning("User doesn't exist");
            throw new IllegalArgumentException("User doesn't exist");
        }
    }


    @GetMapping("/history/store")
    public ReturnValue getStoreHistoryApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId) throws NoPermissionException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExists(userId);
        boolean isAdmin = us.checkIfAdmin(userId);
        ReturnValue rv = new ReturnValue(true, "", sc.getStoreHistory(storeId, userId, isAdmin));
        return rv;
    }

    @GetMapping("/history/user")
    public ReturnValue getUserHistoryApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws NoPermissionException, UserException, JsonProcessingException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExists(userId);
        List<PurchaseHistory> purchaseHistories = History.getInstance().getUserHistory(userId);
        List<Object> historyParsed = parsePurchaseHistories(purchaseHistories);
        ReturnValue rv = new ReturnValue(true, "", historyParsed);
        return rv;
    }


    @GetMapping("/history/store/user")
    public ReturnValue getStoreUserHistoryApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String userIdRequesting,
                                              @RequestParam String storeId
    ) throws NoPermissionException, UserException, JsonProcessingException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);
        List<PurchaseHistory> purchaseHistories = sc.getStoreHistory(userIdRequesting, storeId, userId);
        List<Object> historyParsed = parsePurchaseHistories(purchaseHistories);
        ReturnValue rv = new ReturnValue(true, "", historyParsed);
        return rv;
    }

    private List<Object> parsePurchaseHistories(List<PurchaseHistory> purchaseHistories) throws JsonProcessingException {
        List<Object> historyParsed = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (PurchaseHistory ph : purchaseHistories) {
            String json = String.format("{\"userId\":\"%s\",\"storeId\":\"%s\",\"purchaseId\":\"%s\",\"itemName\":\"%s\",\"itemPrice\":\"%s\",\"itemAmount\":\"%s\",\"timeOfTransaction\":\"%s\"}", ph.getUserID(), ph.getStoreID(), ph.getPurchaseID(), ph.getItemName(), ph.getItemPrice(), ph.getItemAmount(), ph.getTimeOfTransaction());
            historyParsed.add(objectMapper.readTree(json));
        }
        return historyParsed;
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
    public ReturnValue getPermissionTypeApi(@RequestParam String userId) {
        ReturnValue rv = new ReturnValue(true, "", getUserController().getPermissionType(userId));
        return rv;
    }

    @GetMapping("/notification/complaint")
    public ReturnValue readComplaintNotificationApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws UserException, JsonProcessingException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);
        List<ComplaintNotification> cn = us.getComplaintNotifications(userId);
        List<Object> complaintsParsed = parseComplaints(cn);
        ReturnValue rv = new ReturnValue(true, "", complaintsParsed);
        return rv;
    }

    @GetMapping("/store/notification")
    public ReturnValue readStoreNotificationApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws UserException, JsonProcessingException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);
        List<StoreNotification> sn = us.getStoreNotifications(userId);
        List<Object> complaintsParsed = parseStoreNotification(sn);
        ReturnValue rv = new ReturnValue(true, "", complaintsParsed);
        return rv;
    }

    @GetMapping("/notification/store/complaint")
    public ReturnValue readStoreNotificationApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId,
                                                @RequestParam int storeNotificaionId) throws UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);
        getUserController().readStoreNotification(userId, storeNotificaionId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    @PostMapping("/policy/add")
    public ReturnValue addNewPolicyApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId, @RequestParam String typeOfPolicy,
                                       @RequestParam String numOfProducts, @RequestParam String categories, @RequestParam String products,
                                       @RequestParam String productsAmount, @RequestParam String userIds, @RequestParam String startAge,
                                       @RequestParam String endAge, @RequestParam String startTime, @RequestParam String endTime,
                                       @RequestParam String price) throws Exception {
        //public ReturnValue addNewPolicy(@RequestParam String storeId, @RequestParam String userId, @RequestParam String typeOfPolicy, @RequestBody MockPolicy mockPolicy) throws Exception {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        Policy policy;
        userExistsAndLoggedIn(userId);

        switch (typeOfPolicy) {
            case "CartPolicy": //quantity in cart
                policy = policyBuilder.newCartPolicy(Integer.parseInt(numOfProducts));
                break;
            case "CategoryPolicy": //category
                List<ProductsCategories> categoriesList = new ArrayList<>();
                List<String> tempCategoriesList = Arrays.asList(categories.split(","));
                for (String cat : tempCategoriesList) {
                    categoriesList.add(ProductsCategories.valueOf(cat));
                }
                policy = policyBuilder.newCategoryPolicy(categoriesList);
                break;
            case "ProductWithoutAmountPolicy": //product should be in cart
                Store store = sc.getStoreById(storeId);
                List<Product> productsList = store.getProductsNameContains(products); //Arrays.asList(products.split(","));
                List<PurchasableProduct> lp = new LinkedList<>();
                List<String> productsListIds = new ArrayList<>();
                for (Product p : productsList) {
                    productsListIds.add(p.getId());
                }
                for (Product p : getProductById(storeId, productsListIds)) {
                    PurchasableProduct pp = p;
                    lp.add(pp);
                }
                policy = policyBuilder.newProductWithoutAmountPolicy(lp);
                break;
            case "ProductWithAmountPolicy": //product should be in cart with minimum quantity
                Store store2 = sc.getStoreById(storeId);
                List<Product> productsList2 = store2.getProductsNameContains(products);
                HashMap<PurchasableProduct, Integer> mp = new HashMap<>();
                for (Product p : productsList2) {
                    mp.put(p, Integer.valueOf(productsAmount));
                }
                policy = policyBuilder.newProductWithAmountPolicy(mp);
                break;
            case "UserIdPolicy": //specific user
                List<String> userIdsList = Arrays.asList(userIds.split(","));
                policy = policyBuilder.newUserIdPolicy(userIdsList);
                break;
            case "UseAgePolicy": //age
                policy = policyBuilder.newUseAgePolicy(Integer.parseInt(startAge), Integer.parseInt(endAge));
                break;
            case "OnHoursOfTheDayPolicy": //hour of day
                policy = policyBuilder.newOnHoursOfTheDayPolicy(LocalDateTime.parse(startTime), LocalDateTime.parse(endTime));
                break;
            case "OnDaysOfTheWeekPolicy": //day of week
                policy = policyBuilder.newOnDaysOfTheWeekPolicy(LocalDateTime.parse(startTime), LocalDateTime.parse(endTime));
                break;
            case "OnDayOfMonthPolicy": //day of month
                policy = policyBuilder.newOnDayOfMonthPolicy(LocalDateTime.parse(startTime), LocalDateTime.parse(endTime));
                break;
            case "PricePredicate": //total cart price
                policy = policyBuilder.newPricePredicate(Integer.parseInt(price));
                break;
            default:
                throw new IllegalStateException("Unexpected type of policy: " + typeOfPolicy);
        }

        ReturnValue rv = new ReturnValue(true, "", sc.addNewPolicy(storeId, userId, policy));
        return rv;
    }

    @PostMapping("/policy/combine")
    public ReturnValue combineTwoPolicyApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId,
                                           @RequestParam String typeOfCombination,
                                           @RequestParam String policyID1,
                                           @RequestParam String policyID2) throws NoPermissionException, UserException {

        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);
        Policy policy;

        switch (typeOfCombination) {
            case "And":
                policy = policyBuilder.AndGatePolicy(getPolicy(storeId, policyID1), getPolicy(storeId, policyID2));
                break;
            case "Or":
                policy = policyBuilder.OrGatePolicy(getPolicy(storeId, policyID1), getPolicy(storeId, policyID2));
                break;
            case "Xor": //Cond
                policy = policyBuilder.ConditioningGatePolicy(getPolicy(storeId, policyID1), getPolicy(storeId, policyID2));
                break;
            default:
                throw new IllegalStateException("Unexpected type of policy: " + typeOfCombination);
        }
        ReturnValue rv = new ReturnValue(true, "", sc.addNewPolicy(storeId, userId, policy));
        return rv;
    }


    private ReturnValue addNewDiscount(String storeId, String userId, Discount discount) throws NoPermissionException, UserException {// use policyBuilder to create policy
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        ReturnValue rv = new ReturnValue(true, "", sc.addNewDiscount(storeId, userId, discount));
        return rv;
    }

    @PostMapping("/Discount/PercentageDiscount")
    public ReturnValue addNewPercentageDiscountApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId, @RequestParam float percentage, @RequestParam String predicateOnProducts) throws NoPermissionException, UserException, SupplyManagementException, ParseException {// use policyBuilder to create policy
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        return addNewDiscount(storeId, userId, new DiscountBuilder().newPercentageDiscount(percentage, predicateOnProducts));
    }

    @PostMapping("/Discount/ConditionalPercentageDiscount")
    public ReturnValue addNewConditionalPercentageDiscountApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId, @RequestParam float percentage, @RequestParam String predicateOnProducts, @RequestParam String predicateOnCart) throws NoPermissionException, UserException, SupplyManagementException, ParseException {// use policyBuilder to create policy
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        return addNewDiscount(storeId, userId, new DiscountBuilder().newConditionalDiscount(percentage, predicateOnCart, predicateOnProducts));
    }
//    @PostMapping("/Discount/NewAdditionDiscount")
//    public ReturnValue addNewAdditionDiscount(@RequestParam String storeId,@RequestParam String userId, @RequestParam String discountId1,@RequestParam String discountId2) throws UserException, NotSupportedException {
//        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
//            my_log.warning("User doesn't exist or is not logged in or is not logged in");
//            return null;
//        }
//        Discount d1 = sc.getDiscount(storeId,userId,discountId1);
//        Discount d2 = sc.getDiscount(storeId,userId,discountId2);

    //
//        ReturnValue rv = new ReturnValue(true, "", sc.addNewDiscount(storeId,userId, new AdditionDiscount(d1,d2)));
//        return rv;
//    }
    @PostMapping("/Discount/NewMaxDiscount")
    public ReturnValue addNewMaxDiscountApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId, @RequestParam String discountId1, @RequestParam String discountId2) throws UserException, NotSupportedException, NoPermissionException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        Discount d1 = sc.getDiscount(storeId, userId, discountId1);
        Discount d2 = sc.getDiscount(storeId, userId, discountId2);
        ReturnValue rv = new ReturnValue(true, "", sc.addNewDiscount(storeId, userId, new MaxDiscount(d1, d2)));
        return rv;
    }

    @PostMapping("/Discount/deleteDiscount")
    public ReturnValue deleteDiscountApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId, @RequestParam String discountId) throws NoPermissionException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.warning("User doesn't exist or is not logged in or is not logged in");
        }
        sc.deleteDiscount(storeId, userId, discountId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @GetMapping("/Discount/getAll")
    public ReturnValue getAllDiscountsApi(@RequestHeader("Authorization") String sessionID, @RequestParam String storeId, @RequestParam String userId) throws NoPermissionException, JsonProcessingException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        List<String> discountsIds = new ArrayList<>();
        for (Discount discount : sc.getDiscounts(storeId, userId)) {
            discountsIds.add(discount.getDiscountId());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode output = objectMapper.readTree(
                String.format("{\"DiscountIds\":\"%s\"}", discountsIds));

        ReturnValue rv = new ReturnValue(true, "", output);
        return rv;


    }

    private boolean checkIfUserHaveRoleInStore(String userId) {
        return sc.checkIfUserHaveRoleInAnyStore(userId);
    }

//    @GetMapping("/store/user/OwnerStores")
//    public ReturnValue getStoreOwnerBuyUser(@RequestParam String userID){
//        ReturnValue rv = new ReturnValue(true, "", sc.getStoreOwnerBuyUser(userID));
//        return rv;
//    }
//    @GetMapping("/store/user/ManagerStores")
//    public ReturnValue getStoreManagerBuyUser(@RequestParam String userID){
//        ReturnValue rv = new ReturnValue(true, "", sc.getStoreManagerBuyUser(userID));
//        return rv;
//    }
//    @GetMapping("/store/user/ManagerOwnerStores")
//    public ReturnValue getStoreManagerOwnerBuyUser(@RequestParam String userID){
//        List<Store> managerOf=sc.getStoreManagerBuyUser(userID);
//        managerOf.addAll(sc.getStoreOwnerBuyUser(userID));
//        List<Store> managerAndOwnerOf = managerOf;
//        ReturnValue rv = new ReturnValue(true, "", managerAndOwnerOf);
//        return rv;
//    }

//    @GetMapping("/store/user/Permission")
//    public ReturnValue getUserPermissionInStore(@RequestParam String StoreId,
//                                                @RequestParam String userId) {
//
//        ReturnValue rv = new ReturnValue(true, "", sc.getUserPermission(StoreId, userId));
//        return rv;
//    }

    @GetMapping("/store/title")
    public ReturnValue getTitleInStoreApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, String StoreId) throws UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        ReturnValue rv = new ReturnValue(true, "", sc.getTitle(StoreId, userId));
        return rv;
    }

    @PostMapping("/policy/delete")
    public ReturnValue deletePolicyApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId,
                                       @RequestParam String policyId) throws NoPermissionException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);
        sc.deletePolicy(storeId, userId, policyId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    public List<Discount> getDiscounts(String storeId, String userId) throws NoPermissionException, UserException {
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return sc.getDiscounts(storeId, userId);
    }

    @GetMapping("Store/Polices")
    public ReturnValue getPolicesApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId) throws NoPermissionException, UserException, JsonProcessingException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        List<Policy> policies = sc.getPolices(storeId);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Object> storePolices = new ArrayList<>();
        for (Policy p : policies) {
            storePolices.add(
                    objectMapper.readTree(
                            String.format("{\"PolicyId\":\"%s\",\"PolicyDescription\":\"%s\"}", p.getPolicyId(), p)));
        }
        ReturnValue rv = new ReturnValue(true, "", storePolices);
        return rv;
    }

    private Policy getPolicy(String storeId, String policyId) throws NoPermissionException, UserException {

        return sc.getPolicy(storeId, policyId);

    }


    @GetMapping("/stores/all")
    public ReturnValue getAllStoresByStoreNameApi(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String name) throws UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);
        ReturnValue rv = new ReturnValue(true, "", sc.getAllStoresByStoreName(name));
        return rv;
    }

    public ReturnValue getProductById(String storeId, String productId) throws Exception {

        ReturnValue rv = new ReturnValue(true, "", sc.getProductById(storeId, productId));
        return rv;
    }

    private List<Product> getProductById(String storeId, List<String> productId) {
        return sc.getProductById(storeId, productId);

    }

    public List<Guest> getGuest_list() {
        return us.getGuest_list();
    }

    private UserController getUserController() {
        return us;
    }

    private StoreController getStoreController() {
        return sc;
    }


    public ReturnValue addSystemAdmin(String whoIsAdding, String user_toMakeAdmin) throws UserException {
        getUserController().addSystemAdmin(whoIsAdding, user_toMakeAdmin);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    public ReturnValue getRegisteredUsersNum() throws UserException {

        ReturnValue rv = new ReturnValue(true, "", getUserController().getRegisteredUsersNum());
        return rv;
    }

    public ReturnValue deleteUser(String isDeleting,
                                  String whosBeingDeleted) throws NoPermissionException, UserException {
        my_log.info("user" + isDeleting + "is trying to delete user" + whosBeingDeleted);
        //sc.removeRoleInHierarchy(whosBeingDeleted);
        if (checkIfUserHaveRoleInStore(whosBeingDeleted)) {
            throw new NoPermissionException("cant delete user with store role");
        }
        getUserController().deleteUser(isDeleting, whosBeingDeleted);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    public ReturnValue signup(String user_name,
                              String password) throws UserException {
        my_log.info("user " + user_name + " is trying to sign up");
        getUserController().sign_up(user_name, password);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    public ReturnValue logout(String user_name) throws UserException, InterruptedException {

        ReturnValue rv = new ReturnValue(true, "", getUserController().logout(user_name));
        return rv;

    }

    public ReturnValue sendComplaint(String userId, String StoreName, String complaint) {
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    public ReturnValue addGuest() {

        ReturnValue rv = new ReturnValue(true, "", getUserController().addGuest().name);
        return rv;
    }


    public ReturnValue GuestExitSystem(String guestId) {
        getUserController().GuestExitSystem(guestId);

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    public ReturnValue getShoppingCart(String user_Id) throws Exception {
        ShoppingCart shoppingCart = getUserController().getShoppingCart(user_Id);

        List<Object> products = new ArrayList<>();
        List<Object> products2 = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (var basket : shoppingCart.basketCases.entrySet()) {
            for (var pamount : basket.getValue().productAmount.entrySet()) {
                Product product = sc.getProductById(basket.getKey(), pamount.getKey());

                String json = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"price\":\"%s\",\"amount\":\"%d\",\"category\":\"%s\",\"storeId\":\"%s\",\"quantity\":\"%s\"}", product.getId(), product.getName(), product.getPrice(), pamount.getValue(), product.getCategory(), basket.getKey(), product.getSupply());
                products.add(objectMapper.readTree(json));

            }
            if (!products.isEmpty()) {
                products2.add(objectMapper.readTree(String.format("{\"%s\":%s}", basket.getKey(), products)));
            }
            products.clear();

        }
        ReturnValue rv = new ReturnValue(true, "", products2);
        return rv;

    }


//    public boolean containsStore(String user_id, String storeID) {
//        return getUserController().containsStore(user_id, storeID);
//    }

    public ReturnValue removeProductFromCart(MockSmallProduct mockSmallProduct) throws UserException {

        getUserController().removeProduct(mockSmallProduct.getUser_id(), mockSmallProduct.getProductID(), mockSmallProduct.getStoreID(), mockSmallProduct.getAmount());

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    //auction or bid false for now
    public ReturnValue addProductFromCart(MockSmallProduct mockProduct,
                                          boolean auctionOrBid) throws UserException {

        InventoryProtector inventoryProtector = sc.getInventoryProtector(mockProduct.getStoreID());
        getUserController().addProduct(mockProduct.getUser_id(), mockProduct.getProductID(), mockProduct.getStoreID(), mockProduct.getAmount(), inventoryProtector, auctionOrBid);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;

    }


    public ReturnValue purchaseCart(String user_id,
                                    PaymentNames payment,
                                    DeliveryNames delivery, String dann, String ringelblum, String beer_sheva, String israel, String s, String rotman_inc, String s1, String s2, int i, String s3) throws Exception {


        float a = getUserController().purchaseCart(user_id, new ExternalConnectionHolder(delivery, payment) , dann , ringelblum , beer_sheva,israel,s,rotman_inc,s1,s2,i,s3);
        ReturnValue rv = new ReturnValue(true, "", a);
        return rv;
    }


    /// Store controller
    public ReturnValue addNewProductToStore(MockFullProduct mockProduct) throws NoPermissionException, SupplyManagementException, UserException {
        userExistsAndLoggedIn(mockProduct.getUserId());

        String ans = getStoreController().addNewProduct(mockProduct.getStoreId(), mockProduct.getUserId(), mockProduct.getProductName(), mockProduct.getPrice(), mockProduct.getSupply(), mockProduct.getCategory());

        ReturnValue rv = new ReturnValue(true, "", ans);
        return rv;

    }

    public ReturnValue removeSomePermissions(MockPermission mockPermission) throws NoPermissionException, UserException {
        userExistsAndLoggedIn(mockPermission.getUserIdRemoving());

        sc.removeSomePermissions(mockPermission.getStoreId(), mockPermission.getUserIdRemoving(), mockPermission.getUserAffectedId(), mockPermission.getPerToRemove());

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    public ReturnValue openNewStore(String userId,
                                    String storeName) throws NoPermissionException, UserException {
        userExistsAndLoggedIn(userId);
        List<String> managers = new ArrayList<>();
        managers.add(userId);
        ReturnValue rv = new ReturnValue<String>(true, "", getStoreController().openNewStore(storeName, managers));
        return rv;

    }

    public ReturnValue unfreezeStore(String storeId,
                                     String userId) throws NoPermissionException, UserException, NotifyException, SupplyManagementException, IOException {
        userExistsAndLoggedIn(userId);
        getStoreController().unfreezeStore(storeId, userId);

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    public ReturnValue freezeStore(String storeId,
                                   String userId) throws NoPermissionException, UserException, NotifyException, SupplyManagementException, IOException {

        userExistsAndLoggedIn(userId);
        getStoreController().freezeStore(storeId, userId);

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


//not in use- if front want to use contact guy
//    /**
//     * @param storeId the store you want manager info about
//     * @param userIdRequesting  the requesting user, will make sure you are logged in
//     *                and that you have permission in the specific store
//     * @return
//     * @throws NoPermissionException
//     * @throws UserException
//     */

    public ReturnValue getInfoOnManagersOwnersForTests(String storeId,
                                                       String userIdRequesting) throws NoPermissionException, UserException {
        userExistsAndLoggedIn(userIdRequesting);
        List<StoreRoles> storeRoles = getStoreController().getInfoOnManagersOwners(storeId, userIdRequesting);

        ReturnValue rv = new ReturnValue(true, "", storeRoles);
        return rv;
    }

    public ReturnValue getManagersOwnersOfStore(String storeId,
                                                String userIdRequesting) throws NoPermissionException, UserException, JsonProcessingException {
        userExistsAndLoggedIn(userIdRequesting);
        List<StoreRoles> storeRoles = getStoreController().getInfoOnManagersOwners(storeId, userIdRequesting);
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> managersUsers = new ArrayList<>();
        List<String> ownersUsers = new ArrayList<>();
        for (StoreRoles sr : storeRoles) {
            if (sr.getTitle().equals("manger")) {
                managersUsers.add(sr.getUserId());
            } else {
                ownersUsers.add(sr.getUserId());
            }
        }


        ReturnValue rv = new ReturnValue(true, "", objectMapper.readTree(
                String.format("{\"managers\":\"%s\",\"owners\":\"%s\"}", managersUsers, ownersUsers)));
        return rv;
    }


    public ReturnValue getStoresManagedByUser(String user) throws NoPermissionException, UserException, JsonProcessingException {
        List<Object> storePermissions = getStorePermissionToReturn(user, true);

        ReturnValue rv = new ReturnValue(true, "", storePermissions);
        return rv;
    }


    public ReturnValue getStoresOwnedByUser(String user) throws NoPermissionException, UserException, JsonProcessingException {
        List<Object> storePermissions = getStorePermissionToReturn(user, false);
        ReturnValue rv = new ReturnValue(true, "", storePermissions);
        return rv;
    }
/*

        /*
        public ReturnValue getStoresOwnedByUser(String user, String storeId) throws NoPermissionException, UserException, JsonProcessingException {
            List<Object> storePermissions = getStorePermissionToReturn(user, storeId, false);
            ReturnValue rv = new ReturnValue(true, "", storePermissions);
            return rv;
        }
*/


    public ReturnValue editProduct(String productId, MockFullProduct mockProduct) throws NoPermissionException, SupplyManagementException, UserException {
        userExistsAndLoggedIn(mockProduct.getUserId());
        getStoreController().editProduct(mockProduct.getStoreId(), mockProduct.getUserId(), productId, mockProduct.getSupply(), mockProduct.getProductName(), mockProduct.getPrice(), mockProduct.getCategory());
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    //    public void sendComplaintToAdmins(String senderId, ComplaintNotification complaintNotification) throws UserException {
// String sentFrom, NotificationSubject subject, String title, String body
    public ReturnValue sendComplaintToAdmins(String senderId, String subject, String title, String body) throws UserException {
        getUserController().sendComplaintToAdmins(senderId, new ComplaintNotification(senderId, NotificationSubject.valueOf(subject), title, body));
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    public ReturnValue deleteProductFromStore(String storeId,
                                              String userId,
                                              String productId) throws NoPermissionException, SupplyManagementException, UserException, NotifyException, IOException {
        userExistsAndLoggedIn(userId);
        getStoreController().deleteProduct(storeId, userId, productId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    public ReturnValue removePermissionTo(String storeId, String userIdRemoving, String UserAffectedId) throws NoPermissionException, UserException, NotifyException {
        if (getUserController().checkIfUserExists(userIdRemoving) && getUserController().checkIfUserExists(UserAffectedId) && getUserController().checkIfUserIsLoggedIn(userIdRemoving))
            getStoreController().removeRoleInHierarchy(storeId, userIdRemoving, UserAffectedId);
        else
            throw new IllegalArgumentException("couldn't remove permission because the given userId doesn't exist or is not logged in");
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    public ReturnValue createOwner(String storeId, String userIdGiving, String UserGettingPermissionId, String permissions) throws NoPermissionException, UserException, NotifyException {
        if (getUserController().checkIfUserExists(userIdGiving) && getUserController().checkIfUserExists(UserGettingPermissionId.trim()) && getUserController().checkIfUserIsLoggedIn(userIdGiving)) {
            List<Permission> finalPermissions = new ArrayList<>();
            List<String> permissionsList = Arrays.asList(permissions.split(","));
            for (String perString : permissionsList) {
                if (perString.length() > 0) {
                    finalPermissions.add(Permission.valueOf(perString));
                }
            }
            getStoreController().createOwner(storeId, userIdGiving, UserGettingPermissionId.trim(), finalPermissions);
        } else
            throw new IllegalArgumentException("couldn't give permission because the given userId doesn't exist or is not logged in");
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    public ReturnValue createManager(String storeId, String userIdGiving, String UserGettingPermissionId) throws NoPermissionException, UserException, NotifyException {
        if (getUserController().checkIfUserExists(userIdGiving) && getUserController().checkIfUserExists(UserGettingPermissionId) && getUserController().checkIfUserIsLoggedIn(userIdGiving)) {
            getStoreController().createManager(storeId, userIdGiving, UserGettingPermissionId);
        } else
            throw new IllegalArgumentException("couldn't give permission because the given userId doesn't exist or is not logged in");
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    public ReturnValue addReviewToProduct(MockProductReview mockProd) throws NoPermissionException, SupplyManagementException, UserException {
        userExistsAndLoggedIn(mockProd.getUserId());
        getStoreController().addReviewToProduct(mockProd.getStoreId(), mockProd.getUserId(), mockProd.getProductId(), mockProd.getTitle(), mockProd.getBody(), mockProd.getRating());
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    public ReturnValue addNewThreadToForum(String storeId, String title, String userId) throws NoPermissionException, UserException {
        userExistsAndLoggedIn(userId);


        ReturnValue rv = new ReturnValue(true, "", getStoreController().addNewThreadToForum(storeId, title, userId));
        return rv;
    }


    public ReturnValue isInDashboard(String userId) throws InterruptedException {
        for (int i = 0; i < us.get_subscriber(userId).getStoreNotifications().size(); i++) {
            //remove before stress test
            Thread.sleep(1000);
            NotificationController.getInstance().sendNotification(new realTimeNotification(userId, us.get_subscriber(userId).getStoreNotifications().get(i).getSentFrom().getStoreName(), us.get_subscriber(userId).getStoreNotifications().get(i).getSubject().toString(), us.get_subscriber(userId).getStoreNotifications().get(i).getTitle(), us.get_subscriber(userId).getStoreNotifications().get(i).getBody(), new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Calendar.getInstance().getTime())));
        }
        us.get_subscriber(userId).resetNotification();
        return new ReturnValue(true, "", null);
    }


    public ReturnValue postMessageToForum(String storeId, String threadId, String userId, String message) throws NoPermissionException, NotifyException, UserException {
        userExistsAndLoggedIn(userId);
        getStoreController().RolePostMessageToForum(storeId, threadId, userId, message);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;

    }


    public ReturnValue getAllProductsAndStoresWeb() throws UserException, SupplyManagementException, NoPermissionException, JsonProcessingException {
        HashMap<String, List<Product>> allProductsAndStores = getStoreController().getAllProductsAndStores();
        List<Object> products = new ArrayList<>();
        List<Object> products2 = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (var entry : allProductsAndStores.entrySet()) {
            for (var product : entry.getValue()) {
                String json = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"price\":\"%s\",\"quantity\":\"%s\",\"category\":\"%s\",\"storeId\":\"%s\"}", product.getId(), product.getName(), product.getPrice(), product.getSupply(), product.getCategory(), entry.getKey());
                products.add(objectMapper.readTree(json));
            }
            products2.add(objectMapper.readTree(String.format("{\"%s\":%s}", entry.getKey(), products)));
            products.clear();
        }
        ReturnValue rv = new ReturnValue(true, "", products2);
        return rv;
    }


    public ReturnValue getAllProducts() throws JsonProcessingException {
        HashMap<String, List<Product>> allProductsAndStores = getStoreController().getAllProductsAndStores();
        List<Object> products = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (var entry : allProductsAndStores.entrySet()) {
            for (var product : entry.getValue()) {
                String json = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"price\":\"%s\",\"quantity\":\"%s\",\"category\":\"%s\",\"storeId\":\"%s\"}", product.getId(), product.getName(), product.getPrice(), product.getSupply(), product.getCategory(), entry.getKey());
                products.add(objectMapper.readTree(json));
            }
        }
        ReturnValue rv = new ReturnValue(true, "", products);
        return rv;
    }

    public ReturnValue getAllStores() throws JsonProcessingException, InterruptedException {
        List<Store> allStores = getStoreController().getAllStores();
        List<Object> stores = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();


        for (var store : allStores) {
            stores.add(
                    objectMapper.readTree(
                            String.format("{\"storeId\":\"%s\",\"storeName\":\"%s\",\"storeState\":\"%s\",\"storeRating\":\"%s\"}", store.getId(), store.getStoreName(), store.getStoreState(), store.getStoreRating())));
        }
        ReturnValue rv = new ReturnValue(true, "", stores);
        return rv;
    }


    public ReturnValue getStoreProducts(String storeId) throws JsonProcessingException {
        HashMap<String, List<Product>> allProductsAndStores = getStoreController().getAllProductsAndStores();
        List<Object> products = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (var entry : allProductsAndStores.entrySet()) {
            if (entry.getKey().equals(storeId)) {
                for (var product : entry.getValue()) {
                    String json = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"price\":\"%s\",\"quantity\":\"%s\",\"category\":\"%s\",\"storeId\":\"%s\"}", product.getId(), product.getName(), product.getPrice(), product.getSupply(), product.getCategory(), entry.getKey());
                    products.add(objectMapper.readTree(json));
                }
            }

        }
        ReturnValue rv = new ReturnValue(true, "", products);
        return rv;
    }


    public ReturnValue SearchProductsAccordingName(String userId, String productName) throws UserException {
        System.out.println("");
        if (!IsGuest(userId))
            userExistsAndLoggedIn(userId);

        ReturnValue rv = new ReturnValue(true, "", getStoreController().SearchProductsAccordingName(productName));

        return rv;
    }


//    @GetMapping(value = "/search/category", consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public List<Product> SearchProductsAccordingCategory(@RequestParam String userId, @Valid @RequestBody MockCategories mockCategories) throws UserException {
//
//        if (!IsGuest(userId))
//            userExistsAndLoggedIn(userId);
//        return getStoreController().SearchProductsAccordingCategory(mockCategories.getCategories());
//
//    }
//
//
//    @GetMapping("/search/price")
//    public List<Product> SearchProductsAccordingPrice(@RequestParam String userId, @RequestParam float fromPrice, @RequestParam float toPrice) throws UserException {
//
//        if (!IsGuest(userId))
//            userExistsAndLoggedIn(userId);
//        return getStoreController().SearchProductsAccordingPrice(fromPrice, toPrice);
//
//    }
//
//    /**
//     * @param userId
//     * @param productRating - the minimum rating of a product
//     * @return
//     */
//    @GetMapping("/search/rating")
//    public List<Product> SearchProductsAccordingRating(@RequestParam String userId, @RequestParam float productRating) throws UserException {
//
//        if (!IsGuest(userId))
//            userExistsAndLoggedIn(userId);
//        return getStoreController().SearchProductsAccordingRating(productRating);
//    }

    public ReturnValue deleteStore(String userId, String storeId) throws NoPermissionException, UserException {
        userExistsAndLoggedIn(userId);
        if (!us.checkIfAdmin(userId)) {
            throw new NoPermissionException("User is not admin");
        }
        getStoreController().deleteStore(userId, storeId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;

    }


    public ReturnValue getStoreHistory(String storeId, String userId) throws NoPermissionException, UserException {
        userExists(userId);
        boolean isAdmin = us.checkIfAdmin(userId);
        ReturnValue rv = new ReturnValue(true, "", sc.getStoreHistory(storeId, userId, isAdmin));
        return rv;
    }

    public ReturnValue getUserHistory(String userId) throws NoPermissionException, UserException, JsonProcessingException {
        userExists(userId);
        List<PurchaseHistory> purchaseHistories = History.getInstance().getUserHistory(userId);
        List<Object> historyParsed = parsePurchaseHistories(purchaseHistories);
        ReturnValue rv = new ReturnValue(true, "", historyParsed);
        return rv;
    }


    public ReturnValue getStoreUserHistory(String userIdRequesting,
                                           String storeId,
                                           String userId) throws NoPermissionException, UserException, JsonProcessingException {
        userExistsAndLoggedIn(userId);
        List<PurchaseHistory> purchaseHistories = sc.getStoreHistory(userIdRequesting, storeId, userId);
        List<Object> historyParsed = parsePurchaseHistories(purchaseHistories);
        ReturnValue rv = new ReturnValue(true, "", historyParsed);
        return rv;
    }


    public ReturnValue getPermissionType(String username) {

        ReturnValue rv = new ReturnValue(true, "", getUserController().getPermissionType(username));
        return rv;
    }

    public ReturnValue readComplaintNotification(String userId) throws UserException, JsonProcessingException {
        userExistsAndLoggedIn(userId);
        List<ComplaintNotification> cn = us.getComplaintNotifications(userId);
        List<Object> complaintsParsed = parseComplaints(cn);
        ReturnValue rv = new ReturnValue(true, "", complaintsParsed);
        return rv;
    }

    public ReturnValue readStoreNotification(String userId,
                                             int storeNotificaionId) throws UserException {
        userExistsAndLoggedIn(userId);
        getUserController().readStoreNotification(userId, storeNotificaionId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    public ReturnValue addNewPolicy(String storeId, String userId, String typeOfPolicy,
                                    String numOfProducts, String categories, String products,
                                    String productsAmount, String userIds, String startAge,
                                    String endAge, String startTime, String endTime,
                                    String price) throws Exception {
        //public ReturnValue addNewPolicy(@RequestParam String storeId, @RequestParam String userId, @RequestParam String typeOfPolicy, @RequestBody MockPolicy mockPolicy) throws Exception {
        Policy policy;
        userExistsAndLoggedIn(userId);

        switch (typeOfPolicy) {
            case "CartPolicy": //quantity in cart
                policy = policyBuilder.newCartPolicy(Integer.parseInt(numOfProducts));
                break;
            case "CategoryPolicy": //category
                List<ProductsCategories> categoriesList = new ArrayList<>();
                List<String> tempCategoriesList = Arrays.asList(categories.split(","));
                for (String cat : tempCategoriesList) {
                    categoriesList.add(ProductsCategories.valueOf(cat));
                }
                policy = policyBuilder.newCategoryPolicy(categoriesList);
                break;
            case "ProductWithoutAmountPolicy": //product should be in cart
                Store store = sc.getStoreById(storeId);
                List<Product> productsList = store.getProductsNameContains(products); //Arrays.asList(products.split(","));
                List<PurchasableProduct> lp = new LinkedList<>();
                List<String> productsListIds = new ArrayList<>();
                for (Product p : productsList) {
                    productsListIds.add(p.getId());
                }
                for (Product p : getProductById(storeId, productsListIds)) {
                    PurchasableProduct pp = p;
                    lp.add(pp);
                }
                policy = policyBuilder.newProductWithoutAmountPolicy(lp);
                break;
            case "ProductWithAmountPolicy": //product should be in cart with minimum quantity
                Store store2 = sc.getStoreById(storeId);
                List<Product> productsList2 = store2.getProductsNameContains(products);
                HashMap<PurchasableProduct, Integer> mp = new HashMap<>();
                for (Product p : productsList2) {
                    mp.put(p, Integer.valueOf(productsAmount));
                }
                policy = policyBuilder.newProductWithAmountPolicy(mp);
                break;
            case "UserIdPolicy": //specific user
                List<String> userIdsList = Arrays.asList(userIds.split(","));
                policy = policyBuilder.newUserIdPolicy(userIdsList);
                break;
            case "UseAgePolicy": //age
                policy = policyBuilder.newUseAgePolicy(Integer.parseInt(startAge), Integer.parseInt(endAge));
                break;
            case "OnHoursOfTheDayPolicy": //hour of day
                policy = policyBuilder.newOnHoursOfTheDayPolicy(LocalDateTime.parse(startTime), LocalDateTime.parse(endTime));
                break;
            case "OnDaysOfTheWeekPolicy": //day of week
                policy = policyBuilder.newOnDaysOfTheWeekPolicy(LocalDateTime.parse(startTime), LocalDateTime.parse(endTime));
                break;
            case "OnDayOfMonthPolicy": //day of month
                policy = policyBuilder.newOnDayOfMonthPolicy(LocalDateTime.parse(startTime), LocalDateTime.parse(endTime));
                break;
            case "PricePredicate": //total cart price
                policy = policyBuilder.newPricePredicate(Integer.parseInt(price));
                break;
            default:
                throw new IllegalStateException("Unexpected type of policy: " + typeOfPolicy);
        }

        ReturnValue rv = new ReturnValue(true, "", sc.addNewPolicy(storeId, userId, policy));
        return rv;
    }

    public ReturnValue combineTwoPolicy(String storeId,
                                        String userId,
                                        String typeOfCombination,
                                        String policyID1,
                                        String policyID2) throws NoPermissionException, UserException {

        userExistsAndLoggedIn(userId);
        Policy policy;

        switch (typeOfCombination) {
            case "And":
                policy = policyBuilder.AndGatePolicy(getPolicy(storeId, policyID1), getPolicy(storeId, policyID2));
                break;
            case "Or":
                policy = policyBuilder.OrGatePolicy(getPolicy(storeId, policyID1), getPolicy(storeId, policyID2));
                break;
            case "Xor": //Cond
                policy = policyBuilder.ConditioningGatePolicy(getPolicy(storeId, policyID1), getPolicy(storeId, policyID2));
                break;
            default:
                throw new IllegalStateException("Unexpected type of policy: " + typeOfCombination);
        }
        ReturnValue rv = new ReturnValue(true, "", sc.addNewPolicy(storeId, userId, policy));
        return rv;
    }


    public ReturnValue addNewPercentageDiscount(String storeId, String userId, float percentage, String predicateOnProducts) throws NoPermissionException, UserException, SupplyManagementException, ParseException {// use policyBuilder to create policy
        return addNewDiscount(storeId, userId, new DiscountBuilder().newPercentageDiscount(percentage, predicateOnProducts));
    }

    public ReturnValue addNewConditionalPercentageDiscount(String storeId, String userId, float percentage, String predicateOnProducts, String predicateOnCart) throws NoPermissionException, UserException, SupplyManagementException, ParseException {// use policyBuilder to create policy
        return addNewDiscount(storeId, userId, new DiscountBuilder().newConditionalDiscount(percentage, predicateOnCart, predicateOnProducts));
    }

    public ReturnValue addNewMaxDiscount(String storeId, String userId, String discountId1, String discountId2) throws UserException, NotSupportedException, NoPermissionException {
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        Discount d1 = sc.getDiscount(storeId, userId, discountId1);
        Discount d2 = sc.getDiscount(storeId, userId, discountId2);
        ReturnValue rv = new ReturnValue(true, "", sc.addNewDiscount(storeId, userId, new MaxDiscount(d1, d2)));
        return rv;
    }

    public ReturnValue deleteDiscount(String storeId, String userId, String discountId) throws NoPermissionException, UserException {
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.warning("User doesn't exist or is not logged in or is not logged in");
        }
        sc.deleteDiscount(storeId, userId, discountId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    public ReturnValue getAllDiscounts(String storeId, String userId) throws NoPermissionException, JsonProcessingException {
        List<String> discountsIds = new ArrayList<>();
        for (Discount discount : sc.getDiscounts(storeId, userId)) {
            discountsIds.add(discount.getDiscountId());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode output = objectMapper.readTree(
                String.format("{\"DiscountIds\":\"%s\"}", discountsIds));

        ReturnValue rv = new ReturnValue(true, "", output);
        return rv;


    }


    public ReturnValue getTitleInStore(String StoreId, String userId) {
        ReturnValue rv = new ReturnValue(true, "", sc.getTitle(StoreId, userId));
        return rv;
    }

    public ReturnValue deletePolicy(String storeId,
                                    String userId,
                                    String policyId) throws NoPermissionException, UserException {
        userExistsAndLoggedIn(userId);
        sc.deletePolicy(storeId, userId, policyId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    public ReturnValue getPolices(String storeId) throws NoPermissionException, UserException, JsonProcessingException {
        List<Policy> policies = sc.getPolices(storeId);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Object> storePolices = new ArrayList<>();
        for (Policy p : policies) {
            storePolices.add(
                    objectMapper.readTree(
                            String.format("{\"PolicyId\":\"%s\",\"PolicyDescription\":\"%s\"}", p.getPolicyId(), p)));
        }
        ReturnValue rv = new ReturnValue(true, "", storePolices);
        return rv;
    }


    public ReturnValue getAllStoresByStoreName(String userId, String name) throws UserException {
        userExistsAndLoggedIn(userId);
        ReturnValue rv = new ReturnValue(true, "", sc.getAllStoresByStoreName(name));
        return rv;
    }


}