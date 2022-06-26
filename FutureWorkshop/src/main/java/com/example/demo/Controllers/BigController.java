package com.example.demo.Controllers;

import com.example.demo.Controllers.model.realTimeNotification;
import com.example.demo.CustomExceptions.Exception.NotifyException;
import com.example.demo.CustomExceptions.Exception.StorePolicyViolatedException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.CustomExceptions.Exception.UserException;
import com.example.demo.ExternalConnections.Old.Delivery.DeliveryNames;

import com.example.demo.ExternalConnections.Old.ExternalConnectionHolder;
import com.example.demo.ExternalConnections.Old.ExternalConnections;
import com.example.demo.CustomExceptions.Exception.*;
import com.example.demo.Database.DTOobjects.Store.PolicyDTO;
import com.example.demo.Database.DTOobjects.Store.Predicates.AllPredicateDTO;
import com.example.demo.Database.DTOobjects.Store.Predicates.CategoryPredicateDTO;
import com.example.demo.Database.DTOobjects.Store.Predicates.PredicatesTypes;
import com.example.demo.Database.Service.DatabaseService;
import com.example.demo.ExternalConnections.Old.Payment.PaymentNames;
import com.example.demo.GlobalSystemServices.IdGenerator;
import com.example.demo.History.PurchaseHistory;

import com.example.demo.Mock.*;
import com.example.demo.CustomExceptions.ExceptionHandler.ReturnValue;

import com.example.demo.GlobalSystemServices.Log;
import com.example.demo.History.History;
import com.example.demo.NotificationsManagement.ComplaintNotification;
import com.example.demo.NotificationsManagement.NotificationManager;
import com.example.demo.NotificationsManagement.NotificationSubject;
import com.example.demo.NotificationsManagement.StoreNotification;
import com.example.demo.NotificationsManagement.*;
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
import com.example.demo.Store.StorePurchase.Policies.PolicyType;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.CartPredicate;
import com.example.demo.Store.StorePurchase.predicates.PredicateTimeType;
import com.example.demo.StorePermission.Permission;
import com.example.demo.StorePermission.StoreRoles;
import com.example.demo.User.Guest;
import com.example.demo.User.Subscriber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.apache.tomcat.util.json.ParseException;
import com.example.demo.ExternalConnections.Old.ExternalConnectionHolder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.naming.NoPermissionException;
import javax.transaction.NotSupportedException;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@EnableScheduling
@EnableTransactionManagement
@CrossOrigin(maxAge = 3600)
@RestController
@EnableWebMvc
@RequestMapping("/api")

public class BigController {
    private StoreController sc;
    private UserController us;
    private PolicyBuilder policyBuilder;
    private boolean withDatabase;
    private DatabaseService databaseService;
    Log my_log = Log.getLogger();


    //    public BigController(UserController us , StoreController sc) throws IOException {
//        this.us = us;
//        this.sc = sc;
//    }

    @Autowired
    public BigController(DatabaseService databaseService) throws IOException, UserException, NoPermissionException, SupplyManagementException, InterruptedException, SQLException, NotifyException, ResourceNotFoundException {
        this.us = new UserController();
        this.sc = new StoreController(databaseService);
        this.databaseService = databaseService;
        IdGenerator.addDatabase(databaseService);
        this.policyBuilder = new PolicyBuilder();
        NotificationManager.buildNotificationManager(us);
//        NotificationManager.ForTestsOnlyBuildNotificationManager(new NotificationReceiver() {
//            @Override
//            public void sendNotificationTo(List<String> userIds, StoreNotification storeNotification) throws UserException, UserException {}
//            @Override
//            public void sendComplaintToAdmins(String senderId, ComplaintNotification complaintNotification) throws UserException {}
//        });//todo delete this  !!!for testing only!!! notifications doesnt work with this
      //initializeSystem();
        my_log.info("System Started");

        withDatabase = true;

        us.initSystem(databaseService);
    }





//    public BigController() throws IOException, UserException, NoPermissionException, SupplyManagementException {
//        this.us = new UserController();
//        this.sc = new StoreController();
//        this.policyBuilder = new PolicyBuilder();
//        initiateExternalConnections();
//        NotificationManager.buildNotificationManager(us);
//        initializeSystem();
//        my_log.info("System Started");
//        withDatabase = false;
//    }

    public void setWithDatabase(boolean withDatabase) {
        this.withDatabase = withDatabase;
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
    public ReturnValue addSystemAdmin(@RequestHeader("Authorization") String sessionID, @RequestParam String whoIsAdding, @RequestParam String user_toMakeAdmin) throws UserException {
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
    public ReturnValue getPriceOfCartDiscount(@RequestHeader("Authorization") String sessionID, @RequestParam String user_id, @RequestParam PaymentNames payment,
                                                 @RequestParam DeliveryNames delivery) throws StorePolicyViolatedException, UserException, JsonProcessingException, InterruptedException {
        if (!validateSessionID(sessionID, user_id)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("current thread func" + Thread.currentThread().getId());
        ExternalConnectionHolder externalConnectionHolder = new ExternalConnectionHolder(delivery, payment);
        float afterDiscount = getUserController().getPriceOfCartAfterDiscount(user_id, externalConnectionHolder);
        float beforeDiscount = getUserController().getPriceOfCartBeforeDiscount(user_id, new ExternalConnectionHolder(delivery, payment));

        String json = String.format("{\"priceBeforeDiscount\":\"%s\",\"priceAfterDiscount\":\"%s\"}", beforeDiscount, afterDiscount);
        return new ReturnValue(true, "", objectMapper.readTree(json));

    }

    @GetMapping("/online/amount")
    public ReturnValue getOnlineUsersNum(@RequestHeader("Authorization") String sessionID, @RequestParam String user_id) throws ExecutionException, InterruptedException, UserException {
        if (!validateSessionID(sessionID, user_id)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        return new ReturnValue(true, "", getUserController().getOnlineUsersNum());
    }

    @GetMapping("/registered/amount")
    public ReturnValue getRegisteredUsersNum(@RequestHeader("Authorization") String sessionID, @RequestParam String user_id) throws UserException {
        if (!validateSessionID(sessionID, user_id)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        ReturnValue rv = new ReturnValue(true, "", getUserController().getRegisteredUsersNum());
        return rv;
    }

    @PostMapping("/users/delete")
    public ReturnValue deleteUser(@RequestHeader("Authorization") String sessionID, @RequestParam String isDeleting,
                                     @RequestParam String whosBeingDeleted) throws NoPermissionException, UserException {
        if (!validateSessionID(sessionID, isDeleting)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        my_log.info("user" + isDeleting + "is trying to delete user" + whosBeingDeleted);
        //sc.removeRoleInHierarchy(whosBeingDeleted);
        if (checkIfUserHaveRoleInStore(whosBeingDeleted)) {
            throw new NoPermissionException("cant delete user with store role");
        }
        ReturnValue rv = new ReturnValue(true, "", getUserController().deleteUser(isDeleting, whosBeingDeleted));
        if(withDatabase && rv.isSuccess())
            databaseService.deleteUserByName(whosBeingDeleted);
        return rv;
    }


    @PostMapping("/users/signup")
    public ReturnValue signup(@RequestParam String user_name,
                                 @RequestParam String password) throws UserException {
        my_log.info("user " + user_name + " is trying to sign up");
        ReturnValue rv = new ReturnValue(true, "", getUserController().sign_up(user_name, password));
        if(withDatabase && rv.isSuccess())
            databaseService.saveUser(getUserController().get_subscriber(user_name));

        return rv;
    }

    @PostMapping("/users/login")
    public ReturnValue loginUser(@RequestParam String userNameLogin,
                                    @RequestParam String password) throws UserException, InterruptedException {
        ReturnValue rv = new ReturnValue(true, "", getUserController().login(userNameLogin, password));
        if(withDatabase && rv.isSuccess())
            databaseService.saveUser(getUserController().get_subscriber(userNameLogin));

        return rv;
    }

    @PostMapping("/guest/login")
    public ReturnValue loginGuest(@RequestParam String guestId,
                                     @RequestParam String userNameLogin,
                                     @RequestParam String password) throws UserException, InterruptedException {
        ReturnValue rv = new ReturnValue(true, "", getUserController().login(guestId, userNameLogin, password));
        return rv;
    }

    @PostMapping("/users/logout")
    public ReturnValue logout(@RequestParam String user_name) throws UserException, InterruptedException {
        ReturnValue rv = new ReturnValue(true, "", getUserController().logout(user_name));

        if(withDatabase && rv.isSuccess())
            databaseService.saveUser(getUserController().get_subscriber(user_name));

        return rv;

    }

    @PostMapping("/market")
    public ReturnValue sendComplaint(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String StoreName, @RequestParam String complaint) throws UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    @GetMapping("/market/guest")
    public ReturnValue addGuest() {
        String guestName = getUserController().addGuest().name;
        ReturnValue rv = new ReturnValue(true, "", guestName.concat("#").concat(getUserController().getGuest(guestName).getSessionId()));
        return rv;
    }


    @PostMapping("/users")
    public ReturnValue GuestExitSystem(@RequestHeader("Authorization") String sessionID, @RequestParam String user_id, @RequestParam String guestId) throws UserException {
        if (!validateSessionID(sessionID, user_id)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        getUserController().GuestExitSystem(guestId);

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    private Subscriber getSystemAdmin() {
        return getUserController().getSystemAdmin();
    }


    @PostMapping("/cart")
    public ReturnValue getShoppingCart(@RequestHeader("Authorization") String sessionID, @RequestParam String user_Id) throws Exception {
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
    public ReturnValue removeProductFromCart(@RequestHeader("Authorization") String sessionID, @RequestParam String user_id, @Valid @RequestBody MockSmallProduct mockSmallProduct) throws UserException {
        if (!validateSessionID(sessionID, user_id)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        getUserController().removeProduct(mockSmallProduct.getUser_id(), mockSmallProduct.getProductID(), mockSmallProduct.getStoreID(), mockSmallProduct.getAmount());

        ReturnValue rv = new ReturnValue(true, "", null);
        if(withDatabase && rv.isSuccess())
            databaseService.saveShoppingCart(getUserController().get_subscriber(mockSmallProduct.getUser_id()).getShoppingCart());


        return rv;
    }

    //auction or bid false for now
    @PostMapping(value = "/cart/product", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue addProductFromCart(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestBody MockSmallProduct mockProduct,
                                             @RequestParam boolean auctionOrBid) throws UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        InventoryProtector inventoryProtector = sc.getInventoryProtector(mockProduct.getStoreID());
        getUserController().addProduct(mockProduct.getUser_id(), mockProduct.getProductID(), mockProduct.getStoreID(), mockProduct.getAmount(), inventoryProtector, auctionOrBid);
        ReturnValue rv = new ReturnValue(true, "", null);
        databaseService.saveShoppingCart(getUserController().get_subscriber(mockProduct.getUser_id()).getShoppingCart());

        return rv;

    }

    public boolean removePaymentService(PaymentNames payment) {
        return ExternalConnections.getInstance().removePayment(payment);
    }
    //todo database add
    @PostMapping("/cart/purchase")
    public ReturnValue purchaseCart(@RequestHeader("Authorization") String sessionID, @RequestParam String userId,
                                       @RequestParam PaymentNames payment,
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
    public ReturnValue addNewProductToStore(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestBody MockFullProduct mockProduct) throws NoPermissionException, SupplyManagementException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(mockProduct.getUserId());

        String ans = getStoreController().addNewProduct(mockProduct.getStoreId(), mockProduct.getUserId(), mockProduct.getProductName(), mockProduct.getPrice(), mockProduct.getSupply(), mockProduct.getCategory());

        ReturnValue rv = new ReturnValue(true, "", ans);
        return rv;

    }

    @PostMapping(value = "/permissions/delete", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue removeSomePermissions(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestBody MockPermission mockPermission) throws NoPermissionException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(mockPermission.getUserIdRemoving());

        sc.removeSomePermissions(mockPermission.getStoreId(), mockPermission.getUserIdRemoving(), mockPermission.getUserAffectedId(), mockPermission.getPerToRemove());

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/store/open")
    public ReturnValue openNewStore(@RequestHeader("Authorization") String sessionID, @RequestParam String userId,
                                       @RequestParam String storeName) throws NoPermissionException, UserException, SQLException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);
        List<String> managers = new ArrayList<>();
        managers.add(userId);
        ReturnValue rv = new ReturnValue<String>(true, "", getStoreController().openNewStore(storeName, managers, databaseService));
        return rv;

    }

    @PostMapping("/store/freeze")
    public ReturnValue unfreezeStore(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId
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
    public ReturnValue freezeStore(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId) throws NoPermissionException, UserException, NotifyException, SupplyManagementException, IOException {
      
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);
        getStoreController().freezeStore(storeId, userId);

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }
    @GetMapping("/user/websocket/notification")
    public ReturnValue getNotification(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }

        return new ReturnValue(true , "",getUserController().get_subscriber(userId).getStoreNotifications());
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
    public ReturnValue getManagersOwnersOfStore(@RequestHeader("Authorization") String sessionID, String storeId,
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
    public ReturnValue getStoresManagedByUser(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws NoPermissionException, UserException, JsonProcessingException {
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
    public ReturnValue getStoresManagedByUser(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId) throws NoPermissionException, UserException, JsonProcessingException {
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
    public ReturnValue getStoresOwnedByUser(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws NoPermissionException, UserException, JsonProcessingException {
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
    public ReturnValue getStoresOwnedByUser(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId) throws NoPermissionException, UserException, JsonProcessingException {
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
    public ReturnValue editProduct(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String productId, @Valid @RequestBody MockFullProduct mockProduct) throws NoPermissionException, SupplyManagementException, UserException {
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
    public ReturnValue sendComplaintToAdmins(@RequestHeader("Authorization") String sessionID, @RequestParam String senderId, @RequestParam String subject, @RequestParam String title, @RequestParam String body) throws UserException {
        if (!validateSessionID(sessionID, senderId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        ComplaintNotification cn = new ComplaintNotification(senderId, NotificationSubject.valueOf(subject), title, body);
        getUserController().sendComplaintToAdmins(senderId,cn );
        ReturnValue rv = new ReturnValue(true, "", null);
        if(withDatabase)
            databaseService.saveComplaint(cn.getDTO(senderId));
        return rv;
    }

    @PostMapping("/store/product/delete")
    public ReturnValue deleteProductFromStore(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId,
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
    public ReturnValue removePermissionTo(@RequestHeader("Authorization") String sessionID, @RequestParam String storeId, @RequestParam String userIdRemoving, @RequestParam String UserAffectedId) throws NoPermissionException, UserException, NotifyException {
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
    public ReturnValue createOwner(@RequestHeader("Authorization") String sessionID, @RequestParam String storeId, @RequestParam String userIdGiving, @RequestParam String UserGettingPermissionId, @RequestParam String permissions) throws NoPermissionException, UserException, NotifyException, SQLException {
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
    public ReturnValue createManager(@RequestHeader("Authorization") String sessionID, @RequestParam String storeId, @RequestParam String userIdGiving, @RequestParam String UserGettingPermissionId) throws NoPermissionException, UserException, NotifyException, SQLException {
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
    public ReturnValue addReviewToProduct(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestBody MockProductReview mockProd) throws NoPermissionException, SupplyManagementException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(mockProd.getUserId());
        getStoreController().addReviewToProduct(mockProd.getStoreId(), mockProd.getUserId(), mockProd.getProductId(), mockProd.getTitle(), mockProd.getBody(), mockProd.getRating());
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/forum/thread")
    public ReturnValue addNewThreadToForum(@RequestHeader("Authorization") String sessionID, @RequestParam String storeId, @RequestParam String title, @RequestParam String userId) throws NoPermissionException, UserException {
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
    private List<String> initializeStores(List<String> usersIds) throws NoPermissionException, SupplyManagementException, SQLException, UserException, NotifyException {
        List<String> output = new ArrayList<>();
        String owner = usersIds.get(usersIds.size()-1);
        for (String userId : usersIds) {
            List<String> originalOwner = new ArrayList<>();
            originalOwner.add(userId);
            String StoreId = sc.openNewStore(userId + " store", originalOwner, databaseService);
            sc.addNewProduct(StoreId, userId, "p1", 1, 1, ProductsCategories.Apps$Games.toString());
            sc.addNewProduct(StoreId, userId, "p2", 2, 2, ProductsCategories.Appliances.toString());
            sc.addNewProduct(StoreId, userId, "p3", 3, 3, ProductsCategories.Other.toString());
            sc.addNewProduct(StoreId, userId, "p4", 4, 4, ProductsCategories.Apps$Games.toString());
            History.getInstance().insertRecord(userId, StoreId, "p1-forTest", 1, 2, LocalDateTime.now());
            History.getInstance().insertRecord(userId, StoreId, "p1-forTest", 1, 2, LocalDateTime.now());
            //addPolicyToStore(userId, StoreId);
            output.add(StoreId);
            Permission[] permissions = {Permission.REMOVE_PRODUCT,Permission.REPLY_TO_FORUM};
            sc.createOwner(StoreId,userId,owner,Arrays.asList(permissions));
            //sc.createManager(StoreId,userId,owner);
            owner = userId;
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
    public ReturnValue isInDashboard(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws InterruptedException, UserException {
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
    public ReturnValue initializeSystem() throws UserException, SupplyManagementException, NoPermissionException, InterruptedException, SQLException, NotifyException {
        List<String> users = initializeUsers();
        initializeStores(users);
        ReturnValue rv = new ReturnValue(true, "", users);
        return rv;
    }

    @PostMapping("/forum/message")
    public ReturnValue postMessageToForum(@RequestHeader("Authorization") String sessionID, @RequestParam String storeId, @RequestParam String threadId, @RequestParam String userId, @RequestParam String message) throws NoPermissionException, NotifyException, UserException {
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
    public ReturnValue getAllProductsAndStoresWeb(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws UserException, SupplyManagementException, NoPermissionException, JsonProcessingException {
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
    public ReturnValue getAllProducts(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws JsonProcessingException, UserException {
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
    public ReturnValue getAllStores(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws JsonProcessingException, InterruptedException, UserException {
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
    public ReturnValue getStoreProducts(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId) throws JsonProcessingException, UserException {
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
    public ReturnValue SearchProductsAccordingName(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String productName) throws UserException {
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
    public ReturnValue deleteStore(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId) throws NoPermissionException, UserException {
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

    //todo is it okay if its a different object with just different id? (for front)
    @GetMapping("/history/store")
    public ReturnValue getStoreHistory(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId) throws NoPermissionException, UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExists(userId);
        boolean isAdmin = us.checkIfAdmin(userId);

        // databaseService.findHistoryByStoreId(storeId)

        ReturnValue rv = new ReturnValue(true, "", sc.getStoreHistory(storeId, userId, isAdmin));

        return rv;
    }

    @GetMapping("/history/user")
    public ReturnValue getUserHistory(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws NoPermissionException, UserException, JsonProcessingException {
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
    public ReturnValue getStoreUserHistory(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String userIdRequesting,
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
    public ReturnValue getPermissionType(@RequestParam String userId) {
        ReturnValue rv = new ReturnValue(true, "", getUserController().getPermissionType(userId));
        return rv;
    }

    @GetMapping("/notification/complaint")
    public ReturnValue readComplaintNotification(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws UserException, JsonProcessingException {
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
    public ReturnValue readStoreNotification(@RequestHeader("Authorization") String sessionID, @RequestParam String userId) throws UserException, JsonProcessingException {
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
    public ReturnValue readStoreNotification(@RequestHeader("Authorization") String sessionID, @RequestParam String userId,
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
    public ReturnValue addNewPolicy(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId, @RequestParam String typeOfPolicy,
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
        String policyId;
        switch (typeOfPolicy) {
            case "CartPolicy": //quantity in cart
                policy = policyBuilder.newCartPolicy(Integer.parseInt(numOfProducts));
                policyId = sc.addNewPolicy(storeId, userId, policy);
                AllPredicateDTO cartPredicateDTO= new AllPredicateDTO(PredicatesTypes.CartPredicate.toString(),Integer.parseInt(numOfProducts));
                databaseService.saveAllPredicateDTOPolicy(storeId, cartPredicateDTO,policy);
                break;
            case "CategoryPolicy": //category
                List<ProductsCategories> categoriesList = new ArrayList<>();
                List<String> tempCategoriesList = Arrays.asList(categories.split(","));
                for (String cat : tempCategoriesList) {
                    categoriesList.add(ProductsCategories.valueOf(cat));
                }
                policy = policyBuilder.newCategoryPolicy(categoriesList);
                policyId = sc.addNewPolicy(storeId, userId, policy);
                AllPredicateDTO categoryPred = new AllPredicateDTO(PredicatesTypes.CategoryPredicate.toString());
                databaseService.saveCategoryPredicateDTOPolicy(storeId, categoryPred,categoriesList,policy);
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
                policyId = sc.addNewPolicy(storeId, userId, policy);
                AllPredicateDTO productPred = new AllPredicateDTO(PredicatesTypes.ProductPredicate.toString());
                databaseService.saveProductPredicateDTOPolicy(storeId, productPred,lp,policy);
                break;
            case "ProductWithAmountPolicy": //product should be in cart with minimum quantity
                Store store2 = sc.getStoreById(storeId);
                List<Product> productsList2 = store2.getProductsNameContains(products);
                HashMap<PurchasableProduct, Integer> mp = new HashMap<>();
                for (Product p : productsList2) {
                    mp.put(p, Integer.valueOf(productsAmount));
                }
                policy = policyBuilder.newProductWithAmountPolicy(mp);
                policyId = sc.addNewPolicy(storeId, userId, policy);
                AllPredicateDTO productPredWith = new AllPredicateDTO(PredicatesTypes.ProductPredicate.toString());
                databaseService.saveProductPredicateDTOPolicy(storeId, productPredWith,mp,policy);
                break;
            case "UserIdPolicy": //specific user
                List<String> userIdsList = Arrays.asList(userIds.split(","));
                policy = policyBuilder.newUserIdPolicy(userIdsList);
                policyId = sc.addNewPolicy(storeId, userId, policy);
                AllPredicateDTO userPredicate = new AllPredicateDTO(PredicatesTypes.UserIdPredicate.toString());
                databaseService.saveUserPredicateDTOPolicy(storeId, userPredicate,userIdsList,policy);
                break;
            case "UseAgePolicy": //age
                policy = policyBuilder.newUseAgePolicy(Integer.parseInt(startAge), Integer.parseInt(endAge));
                policyId = sc.addNewPolicy(storeId, userId, policy);
                AllPredicateDTO useAgerPredicate = new AllPredicateDTO(PredicatesTypes.UserAgePredicate.toString());
                databaseService.saveAllPredicateDTOPolicy(storeId, useAgerPredicate,policy);
                break;
            case "OnHoursOfTheDayPolicy": //hour of day
                policy = policyBuilder.newOnHoursOfTheDayPolicy(LocalDateTime.parse(startTime), LocalDateTime.parse(endTime));
                policyId = sc.addNewPolicy(storeId, userId, policy);
                AllPredicateDTO TimeHoursPredicateDTO= new AllPredicateDTO(PredicatesTypes.TimePredicate.toString(),startTime, endTime, PredicateTimeType.OnHoursOfTheDay.toString());
                databaseService.saveAllPredicateDTOPolicy(storeId, TimeHoursPredicateDTO,policy);
                break;
            case "OnDaysOfTheWeekPolicy": //day of week
                policy = policyBuilder.newOnDaysOfTheWeekPolicy(LocalDateTime.parse(startTime), LocalDateTime.parse(endTime));
                policyId = sc.addNewPolicy(storeId, userId, policy);
                AllPredicateDTO TimeWeekPredicateDTO= new AllPredicateDTO(PredicatesTypes.TimePredicate.toString(),startTime, endTime, PredicateTimeType.OnDaysOfTheWeek.toString());
                databaseService.saveAllPredicateDTOPolicy(storeId, TimeWeekPredicateDTO,policy);

                break;
            case "OnDayOfMonthPolicy": //day of month
                policy = policyBuilder.newOnDayOfMonthPolicy(LocalDateTime.parse(startTime), LocalDateTime.parse(endTime));
                policyId = sc.addNewPolicy(storeId, userId, policy);
                AllPredicateDTO TimePredicateDTO= new AllPredicateDTO(PredicatesTypes.TimePredicate.toString(),startTime, endTime, PredicateTimeType.OnDayOfMonth.toString());
                databaseService.saveAllPredicateDTOPolicy(storeId, TimePredicateDTO,policy);
                break;
            case "PricePredicate": //total cart price
                policy = policyBuilder.newPricePredicate(Integer.parseInt(price));
                policyId = sc.addNewPolicy(storeId, userId, policy);
                AllPredicateDTO pricePredicateDTO= new AllPredicateDTO(PredicatesTypes.pricePredicate.toString(),Float.parseFloat(price));
                databaseService.saveAllPredicateDTOPolicy(storeId, pricePredicateDTO,policy);
                break;
            default:
                throw new IllegalStateException("Unexpected type of policy: " + typeOfPolicy);
        }


        ReturnValue rv = new ReturnValue(true, "", policyId);
        return rv;
    }

    @PostMapping("/policy/combine")
    public ReturnValue combineTwoPolicy(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId,
                                           @RequestParam String typeOfCombination,
                                           @RequestParam String policyID1,
                                           @RequestParam String policyID2) throws NoPermissionException, UserException {

        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        userExistsAndLoggedIn(userId);
        Policy policy;
        String policyId;
        switch (typeOfCombination) {
            case "And":
                policy = policyBuilder.AndGatePolicy(getPolicy(storeId, policyID1), getPolicy(storeId, policyID2));
                policyId = sc.addNewPolicy(storeId, userId, policy);
                PolicyDTO andPolicyDTO=new PolicyDTO(storeId, policyId, PolicyType.AndGatePolicy.toString(),policyID1,policyID2);
                databaseService.savePolicyComp(andPolicyDTO);
                break;
            case "Or":
                policy = policyBuilder.OrGatePolicy(getPolicy(storeId, policyID1), getPolicy(storeId, policyID2));
                policyId = sc.addNewPolicy(storeId, userId, policy);
                PolicyDTO orPolicyDTO=new PolicyDTO(storeId, policyId, PolicyType.OrGatePolicy.toString(),policyID1,policyID2);
                databaseService.savePolicyComp(orPolicyDTO);
                break;
            case "Xor": //Cond
                policy = policyBuilder.ConditioningGatePolicy(getPolicy(storeId, policyID1), getPolicy(storeId, policyID2));
                policyId = sc.addNewPolicy(storeId, userId, policy);
                PolicyDTO condPolicyDTO=new PolicyDTO(storeId, policyId, PolicyType.conditioningPolicy.toString(),policyID1,policyID2);
                databaseService.savePolicyComp(condPolicyDTO);
                break;
            default:
                throw new IllegalStateException("Unexpected type of policy: " + typeOfCombination);
        }
        ReturnValue rv = new ReturnValue(true, "", policyId);
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
    public ReturnValue addNewPercentageDiscount(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId, @RequestParam float percentage, @RequestParam String predicateOnProducts) throws NoPermissionException, UserException, SupplyManagementException, ParseException {// use policyBuilder to create policy
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        return addNewDiscount(storeId, userId, new DiscountBuilder().newPercentageDiscount(percentage, predicateOnProducts));
    }

    @PostMapping("/Discount/ConditionalPercentageDiscount")
    public ReturnValue addNewConditionalPercentageDiscount(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId, @RequestParam float percentage, @RequestParam String predicateOnProducts, @RequestParam String predicateOnCart) throws NoPermissionException, UserException, SupplyManagementException, ParseException {// use policyBuilder to create policy
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
    public ReturnValue addNewMaxDiscount(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId, @RequestParam String discountId1, @RequestParam String discountId2) throws UserException, NotSupportedException, NoPermissionException {
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
    public ReturnValue deleteDiscount(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId, @RequestParam String discountId) throws NoPermissionException, UserException {
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
    public ReturnValue getAllDiscounts(@RequestHeader("Authorization") String sessionID, @RequestParam String storeId, @RequestParam String userId) throws NoPermissionException, JsonProcessingException, UserException {
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

    @GetMapping("/store/title")
    public ReturnValue getTitleInStore(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, String StoreId) throws UserException {
        if (!validateSessionID(sessionID, userId)) {
            return new ReturnValue(false, "Not authorized", null);
        }
        ReturnValue rv = new ReturnValue(true, "", sc.getTitle(StoreId, userId));
        return rv;
    }

    @PostMapping("/policy/delete")
    public ReturnValue deletePolicy(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId,
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
    public ReturnValue getPolices(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String storeId) throws NoPermissionException, UserException, JsonProcessingException {
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
    public ReturnValue getAllStoresByStoreName(@RequestHeader("Authorization") String sessionID, @RequestParam String userId, @RequestParam String name) throws UserException {
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
}
