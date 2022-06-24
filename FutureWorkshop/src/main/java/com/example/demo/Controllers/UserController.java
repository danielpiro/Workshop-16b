package com.example.demo.Controllers;

import com.example.demo.CustomExceptions.Exception.CantPurchaseException;
import com.example.demo.CustomExceptions.Exception.StorePolicyViolatedException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.CustomExceptions.Exception.UserException;
import com.example.demo.Database.DTOobjects.User.UserDTO;
import com.example.demo.Database.Service.DatabaseService;
import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.GlobalSystemServices.IdGenerator;
import com.example.demo.GlobalSystemServices.Log;
import com.example.demo.NotificationsManagement.ComplaintNotification;
import com.example.demo.NotificationsManagement.NotificationReceiver;
import com.example.demo.NotificationsManagement.StoreNotification;
import com.example.demo.ShoppingCart.InventoryProtector;
import com.example.demo.ShoppingCart.ShoppingCart;
import com.example.demo.User.Encryption;
import com.example.demo.User.Guest;
import com.example.demo.User.Subscriber;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class UserController implements NotificationReceiver {
    private List<Subscriber> user_list;
    private List<Guest> guest_list;
    private List<Subscriber> system_admins;
    private Subscriber admin;
    Log my_log = Log.getLogger();
    Object signUpLock = new Object();
    Object addingSubscriber = new Object();
    Object guest = new Object();
    Object deleteUser = new Object();
    private int onlineUsers;
    private int registeredUsers;


    public UserController() {
        user_list = new ArrayList<>();
        guest_list = new ArrayList<>();
        system_admins = new ArrayList<>();
        admin = new Subscriber("admin", Encryption.encryptThisString("admin"));
        add_subscriber(admin);
        add_admin(admin);
        onlineUsers = 0;
        registeredUsers = 0;
    }

    public void initSystem(DatabaseService databaseService){

        for(UserDTO userDTO: databaseService.allUsers()){
            user_list.add( new Subscriber(userDTO.getName(),userDTO.getPassword(),databaseService.getShoppingCart(userDTO.getName())));
            registeredUsers++;

        }

    }

    public List<String> initialize() throws UserException, InterruptedException {
        Guest g1 = addGuest();
        Guest g2 = addGuest();
        Guest g3 = addGuest();
        Guest g4 = addGuest();
        Guest g5 = addGuest();
        Guest g6 = addGuest();
        Guest g7 = addGuest();
        Guest g8 = addGuest();
        sign_up("amit", "12345");
        sign_up("guy", "12345678");
        sign_up("pam123", "12345000");
        sign_up("alex12", "0202020");
        sign_up("ronn", "11111");
        sign_up("delphin", "1234");
        sign_up("alan", "4321");
        sign_up("rotman", "3333");
        sign_up("dashy", "55555");
        sign_up("crim", "asdfqwe");
        sign_up("karma", "qwerty");
        sign_up("damon", "nade123");
        sign_up("tjhaly", "asdfdasd");
        sign_up("spart", "sswwwss");
        sign_up("yoav", "yoav123");
        sign_up("pamaj", "pam14444");
        sign_up("spratt", "raint42");
        login("amit", "12345");
        login("guy", "12345678");
        login("pam123", "12345000");
        login("alex12", "0202020");
        login("ronn", "11111");
        login("rotman", "3333");
        login("dashy", "55555");
        login("crim", "asdfqwe");
        login("karma", "qwerty");
        login("damon", "nade123");
        login("tjhaly", "asdfdasd");
        login("spart", "sswwwss");
        login("yoav", "yoav123");

        //user_list.get(0).

        login(g1.name, "delphin", "1234");
        login(g2.name, "alan", "4321");
        login(g3.name, "pamaj", "pam14444");
        login(g4.name, "spratt", "raint42");
        List<String> userIds = new ArrayList<>();
        userIds.add("amit");
        userIds.add("guy");
        userIds.add("pam123");
        userIds.add("alex12");
        userIds.add("ronn");
        userIds.add("delphin");
        userIds.add("alan");
        userIds.add("rotman");
        userIds.add("dashy");
        userIds.add("crim");
        return userIds;
    }

    //WE DECIDED THAT ALL THE SYSTEM ADMINS WILL HAVE A SIMILAR USERNAME AS FOLLOWS:
    // Admin_1 Admin_2 Admin_3 and so on
    public void addSystemAdmin(String whoIsAdding, String user_toMakeAdmin) throws UserException {
        my_log.info("user " + whoIsAdding + "is trying to add " + user_toMakeAdmin + "as admin");
        if (!getUser_list().contains(get_subscriber(whoIsAdding))) {
            my_log.warning("the user that we treated as admin is not a user at all!!");
            throw new UserException("the user that we treated as admin is not a user at all!!");
        }
        synchronized (get_subscriber(whoIsAdding).getLock()) {
            if (!getSystemAdmins().contains(get_subscriber(whoIsAdding))) {
                my_log.warning("the user that is trying to add in not an admin");
                throw new UserException("the user that is trying to add in not an admin");
            }
            synchronized (get_subscriber(user_toMakeAdmin).getLock()) {
                if (getSystemAdmins().contains(get_subscriber(user_toMakeAdmin))) {
                    my_log.warning("this user we are trying to add is already a system admin");
                    throw new UserException("this user we are trying to add is already a system admin");
                } else if (!getUser_list().contains(get_subscriber(user_toMakeAdmin))) {
                    my_log.warning("the given user name is not a valid name for an existing user in the system");
                    throw new UserException("the given user name is not a valid name for an existing user in the system");
                } else if (!checkIfUserIsLoggedIn(whoIsAdding)) {
                    my_log.warning("the admin that is trying to add another admin is not logged in");
                    throw new UserException("the admin that is trying to add another admin is not logged in");
                }
                getSystemAdmins().add(get_subscriber(user_toMakeAdmin));
            }
        }
    }

    public ShoppingCart getShoppingCart(String user_Id) throws UserException {
        if (getGuest(user_Id) != null)
            return getGuest(user_Id).getShoppingCart();
        else if (get_subscriber(user_Id) == null) {
            my_log.warning("User " + user_Id + " doesn't exist");
            throw new UserException("User " + user_Id + " doesn't exist");
        } else if (!checkIfUserIsLoggedIn(user_Id)) {
            my_log.warning("User " + user_Id + " is not logged in");
            throw new UserException("User " + user_Id + "is not logged in");
        }
        return get_subscriber(user_Id).getShoppingCart();
    }

    public boolean containsStore(String user_id, String storeID) throws UserException {
        if ((getGuest(user_id) != null))
            return getGuest(user_id).containsStore(storeID);
        if (get_subscriber(user_id) == null) {
            my_log.warning("User " + user_id + " doesn't exist");
            throw new UserException("User " + user_id + "doesn't exist");
        }
        if (!checkIfUserIsLoggedIn(user_id)) {
            my_log.warning("User " + user_id + " is not logged in");
            throw new UserException("User " + user_id + "is not logged in");
        }
        return get_subscriber(user_id).containsStore(storeID);
    }

    public void removeProduct(String user_id, String productID, String storeID, int amount) throws UserException {
        if ((getGuest(user_id) != null)){
            getGuest(user_id).removeProduct(productID, storeID, amount);
            return;
        }
        else if (get_subscriber(user_id) == null && getGuest(user_id) == null) {
            my_log.warning("User " + user_id + " doesn't exist");
            throw new UserException("User " + user_id + " doesn't exist");
        } else if (get_subscriber(user_id) != null) {
            if (!checkIfUserIsLoggedIn(user_id)) {
                throw new UserException("User " + user_id + " is not logged in");
            }
        }
        get_subscriber(user_id).removeProduct(productID, storeID, amount);
    }

    public void addProduct(String user_id, String productID, String storeID, int amount, InventoryProtector inventoryProtector, boolean auctionOrBid) throws UserException {
        if ((getGuest(user_id) != null)) {
            getGuest(user_id).addProduct(productID, storeID, amount, inventoryProtector, auctionOrBid);
        } else if (get_subscriber(user_id) == null && getGuest(user_id) == null) {
            throw new UserException("User " + user_id + " doesn't exist");
        } else if (get_subscriber(user_id) != null) {
            if (!checkIfUserIsLoggedIn(user_id)) {
                throw new UserException("User " + user_id + " is not logged in");
            }
            get_subscriber(user_id).addProduct(productID, storeID, amount, inventoryProtector, auctionOrBid);
        }
    }


    public String getCartInventory(String user_id) throws UserException {
        if ((getGuest(user_id) != null))
            return getGuest(user_id).getCartInventory();
        else if (get_subscriber(user_id) == null) {
            my_log.warning("user " + user_id + " doesn't exist");
            throw new UserException("User " + user_id + "doesn't exist");
        } else if (!checkIfUserIsLoggedIn(user_id)) {
            my_log.warning("user " + user_id + " is not logged in");
            throw new UserException("User " + user_id + " is not logged in");
        }
        return get_subscriber(user_id).getCartInventory();
    }

    public float purchaseCart(String user_id, ExternalConnectionHolder externalConnectionHolder) throws SupplyManagementException, StorePolicyViolatedException, CantPurchaseException, UserException {
        if ((getGuest(user_id) != null))
            return getGuest(user_id).purchaseCart(externalConnectionHolder);
        else if (get_subscriber(user_id) == null) {
            throw new UserException("User " + user_id + " doesn't exist");
        } else if (!checkIfUserIsLoggedIn(user_id)) {
            my_log.warning("user " + user_id + " is not logged in");
            throw new UserException("User " + user_id + " is not logged in");
        }
        return get_subscriber(user_id).purchaseCart(externalConnectionHolder);

    }

    //String sender_id, String message, LocalDate date,String storeName
    /*
    public void sendComplaint(String userId, String StoreName,String complaint ){
        if(get_subscriber(userId)==null){
            my_log.warning("user "+userId + " does not exist");
            throw new IllegalArgumentException("User doesn't exist");
        }
            if (!IdGenerator.getInstance().checkIfAdmin(userId))
                admin.getBuffer().add(new Message(StoreName, complaint)); // TODO
            else {
                my_log.warning("user "+userId + "is a system admin, system admin can't send a complaint to himself or sending user doesn't exist");
                throw new IllegalArgumentException("system admin can't send a complaint to himself or sending user doesn't exist");
            }
    }
*/
    public boolean sign_up(String user_name, String password) throws UserException {
        my_log.info("is trying to sign up with user name : " + user_name);
        if (get_subscriber(user_name) != null) {
            my_log.warning("user " + user_name + " already exists in the system - failed to sign up");
            throw new UserException("user " + user_name + " already exists in the system - failed to sign up");
        }
        if (user_name == null) {
            my_log.warning("user " + user_name + " is null - failed to sign up");
            throw new UserException("user " + user_name + " is null - failed to sign up");
        }
        if (password == null) {
            my_log.warning("user " + user_name + " password's is null - failed to sign up");
            throw new UserException("user " + user_name + " password's is null - failed to sign up");
        }
        if (user_name.isEmpty()) {
            my_log.warning("user " + user_name + " is empty - failed to sign up");
            throw new UserException("user " + user_name + " is empty - failed to sign up");
        }
        if (password.isEmpty()) {
            my_log.warning("user " + user_name + " password's is empty - failed to sign up");
            throw new UserException("user " + user_name + " password's is empty - failed to sign up");
        }
        if (user_name.length() < 2) {
            my_log.warning("user " + user_name + " length is less than 2 - failed to sign up");
            throw new UserException("user " + user_name + " length is less than 2 - failed to sign up");
        }
        if (password.length() < 2) {
            my_log.warning("user " + user_name + " password's lenth is less than 2 - failed to sign up");
            throw new UserException("user " + user_name + " password's lenth is less than 2 - failed to sign up");
        }
        synchronized (signUpLock) {
            Subscriber s = new Subscriber(user_name, Encryption.encryptThisString(password));
            add_subscriber(s);
            registeredUsers++;
            return true;
        }
    }

    public boolean login(String guestId, String user_name, String password) throws UserException, InterruptedException {
        my_log.info("user " + user_name + " is trying to login");
        if (getGuest(guestId) == null)
            throw new UserException("guest " + guestId + " does not exist in the system");
        if (checkIfUserExists(user_name)) {
            synchronized (get_subscriber(user_name).getLock()) {
                if (checkIfUserExists(user_name)) {
                    if (get_subscriber(user_name) == null) {
                        my_log.warning("user " + user_name + " failed to login");
                        throw new UserException("user " + user_name + " failed to login");
                    } else if (get_subscriber(user_name).isLogged_in() && check_password(user_name, password)) {
                        my_log.warning("user " + user_name + " is already logged in");
                        throw new UserException("user " + user_name + " is already logged in");
                    } else if (!get_subscriber(user_name).isLogged_in() && check_password(user_name, password)) {
                        get_subscriber(user_name).setLogged_in(true);
                        get_subscriber(user_name).setShoppingCart(getGuestShoppingCart(guestId));
                        removeGuest(guestId);
                        onlineUsers++;
                        return true;
                    }
                } else {
                    my_log.warning("user " + user_name + " doesn't exist -- failed to login");
                    return false;
                    // ("user has been deleted") // add logger
                }
            }

        }
        my_log.warning("user " + user_name + " failed login");
        throw new UserException("user " + user_name + " failed login");
    }

    public boolean login(String user_name, String password) throws UserException, InterruptedException {
        my_log.info("user " + user_name + " is trying to login");
        if (checkIfUserExists(user_name)) {
            synchronized (get_subscriber(user_name).getLock()) {
                if (checkIfUserExists(user_name)) {
                    if (get_subscriber(user_name) == null) {
                        my_log.warning("user " + user_name + " failed to login");
                        throw new UserException("user " + user_name + " failed to login");
                    } else if (get_subscriber(user_name).isLogged_in() && check_password(user_name, password)) {
                        my_log.warning("user " + user_name + " is already logged in");
                        throw new UserException("user " + user_name + " is already logged in");
                    } else if (!get_subscriber(user_name).isLogged_in() && check_password(user_name, password)) {
                        get_subscriber(user_name).setLogged_in(true);
                        onlineUsers++;
                        return true;
                    }
                } else {
                    my_log.warning("user " + user_name + " doesn't exist -- failed to login");
                    return false;
                    // ("user has been deleted") // add logger
                }
            }

        }
        my_log.warning("user " + user_name + " failed login");
        throw new UserException("user " + user_name + " failed login");
    }

    public boolean logout(String user_name) throws UserException, InterruptedException {
        my_log.info("user " + user_name + " is trying to logout");
        if (get_subscriber(user_name) == null) {
            my_log.warning("failed to logout because " + user_name + " is null");
            throw new UserException("failed to logout because " + user_name + " is null");
        }
        synchronized (get_subscriber(user_name).getLock()) {
            if (!get_subscriber(user_name).isLogged_in()) {
                my_log.warning("failed to logout - user " + user_name + " is not logged in");
                throw new UserException("failed to logout - user " + user_name + " is not logged in");
            } else {
                my_log.info("user " + user_name + " successfully logged out");
                get_subscriber(user_name).setLogged_in(false);
                Guest g = addGuest();
                g.setShoppingCart(getSubscriberCart(user_name));
                onlineUsers--;
                return true;
            }
        }

    }


    public boolean check_password(String user_name, String password) throws UserException {
        if (get_subscriber(user_name) == null) {
            my_log.warning("User " + user_name + " doesn't exist");
            throw new UserException("User " + user_name + " doesn't exist");
        }
        if (password.length() > 2) {
            Subscriber subscriber = get_subscriber(user_name);
            return subscriber.getPassword().equals(Encryption.encryptThisString(password));
        }
        my_log.warning("invalid password for user " + user_name);
        throw new UserException("invalid password for user " + user_name);
    }

    public Subscriber get_subscriber(String user_name) {
        Subscriber subscriber = null;
        for (Subscriber user : user_list) {
            if (user.getName().equals(user_name)) {
                subscriber = user;
            }
        }
        return subscriber;
    }

    public boolean checkIfUserExists(String userID) throws UserException {
        if (get_subscriber(userID) == null) {
            my_log.warning(" user " + userID + " doesn't exist");
            return false; //throw new UserException(" user "+userID + " doesn't exist");
        }
        synchronized (get_subscriber(userID).getLock()) {
            return true;
        }
    }

    public Guest getGuest(String id) {
        for (Guest g : getGuest_list()) {
            if (g.getId().equals(id))
                return g;
        }
        return null;
    }

    public Guest addGuest() {
        synchronized (guest) {
            Guest g = new Guest(IdGenerator.getInstance().getGuestId());
            getGuest_list().add(g);
            return g;
        }
    }

    public void GuestExitSystem(String name) throws NoSuchElementException {
        for (Guest g : getGuest_list())
            if (g.name.equals(name)) {
                getGuest_list().remove(g);
                return;
            }
        throw new NoSuchElementException("Guest does not exist");
    }

    public void Add_Query(String user_name, String query) throws UserException { //3.5
        if (get_subscriber(user_name) == null) {
            my_log.warning(" user " + user_name + " doesn't exist");
            throw new UserException("User " + user_name + " doesn't exist");
        }
        if (!get_subscriber(user_name).isLogged_in()) {
            my_log.warning(" user " + user_name + " is offline");
            throw new UserException("User " + user_name + " is offline");
        }
        Subscriber subscriber = get_subscriber(user_name);
        my_log.warning(" user " + user_name + " successfully added a query ");
        subscriber.getQueries().add(query);
    }

    public boolean deleteUser(String whosDeleting, String whosBeingDeleted) throws UserException {
        synchronized (deleteUser) {
            if (checkIfUserExists(whosDeleting) && checkIfUserIsLoggedIn(whosDeleting) && checkIfAdmin(whosDeleting)) {
                if (checkIfUserExists(whosBeingDeleted) && !checkIfAdmin(whosBeingDeleted)) {
                    for (Subscriber s : getUser_list()) {
                        if (s.getName().equals(whosBeingDeleted)) {
                            getUser_list().remove(s);
                            return true;
                        }
                    }
                }
            }
            my_log.info(" user " + whosBeingDeleted + " failed to delete user " + whosBeingDeleted);
            throw new UserException(" user " + whosBeingDeleted + " failed to delete user " + whosBeingDeleted);
        }
    }

    public boolean checkIfGuestExists(String guestId) {
        for (Guest g : getGuest_list()) {
            if (g.name.equals(guestId))
                return true;
        }
        return false;
    }

    public void removeGuest(String guestId) {
        guest_list.removeIf(guest -> guest.name.equals(guestId));
    }

    public ShoppingCart getGuestShoppingCart(String guestId) {
        for (Guest g : getGuest_list()) {
            if (g.name.equals(guestId))
                return g.getShoppingCart();
        }
        return null;
    }

    public ShoppingCart getSubscriberCart(String subId) {
        for (Subscriber s : getUser_list())
            if (s.getName().equals(subId))
                return s.getShoppingCart();
        return null;
    }

    public List<Guest> getGuest_list() {
        return guest_list;
    }

    public List<Subscriber> getUser_list() {
        return user_list;
    }

    public void add_subscriber(Subscriber s) {
        synchronized (addingSubscriber) {
            user_list.add(s);
        }
    }

    public void add_admin(Subscriber s) {
        system_admins.add(s);
    }

    public List<Subscriber> getSystemAdmins() {
        return system_admins;
    }

    public Subscriber getSystemAdmin() {
        return admin;
    }

    public boolean checkIfUserIsLoggedIn(String id) {
        synchronized (get_subscriber(id).getLock()) {
            return get_subscriber(id).isLogged_in();
        }
    }

    public boolean checkIfAdmin(String userId) throws UserException {
        if (getSystemAdmins().contains(get_subscriber(userId)))
            return true;
        return false; //throw new UserException(userId + " is not an admin");
    }

    @Override
    public void sendNotificationTo(List<String> userIds, StoreNotification storeNotification) throws UserException {
        my_log.info("sending store notification for some users");
        for (String s : userIds) {
            if (!checkIfUserExists(s)) {
                my_log.warning("can't send store notification because user " + s + " doesn't exist");
                throw new UserException("can't send store notification because user " + s + " doesn't exist");
            }

            get_subscriber(s).addNotification(storeNotification.getDeepCopy());
        }
    }

    @Override
    public void sendComplaintToAdmins(String senderId, ComplaintNotification complaintNotification) throws UserException {
        my_log.info("user " + senderId + " is wants to send a complaint to system admins");
        if (get_subscriber(senderId) == null)
            throw new UserException("user " + senderId + " doesn't exist");
        if (!get_subscriber(senderId).isLogged_in()) {
            my_log.warning("the sender " + senderId + " is not online");
            throw new UserException("the sender " + senderId + " is not online");
        }
        for (Subscriber s : getSystemAdmins()) {
            //if (!checkIfUserExists(s))
            //  throw new UserException("user " +s + " doesn't exist");
            //if (!checkIfAdmin(s)) {
            // my_log.warning("trying to send a complaint to a user " + s + " which is not an admin");
            //  throw new UserException("trying to send a complaint to a user " + s + " which is not an admin");
            //}
            get_subscriber(s.getName()).addComplaint(complaintNotification.getDeepCopy());
        }
    }

    public void readStoreNotification(String userid, int storeNotificaionId) throws UserException {
        my_log.info("user " + userid + " wants to read store notification with id " + storeNotificaionId);
        if (get_subscriber(userid) == null) {
            my_log.warning("user " + userid + " does not exist");
            throw new UserException("user " + userid + " does not exist");
        }
        if (!get_subscriber(userid).isLogged_in()) {
            my_log.warning("user " + userid + " is not online");
            throw new UserException("user " + userid + " is not online");
        }
        if (storeNotificaionId < 0 || storeNotificaionId > get_subscriber(userid).getStoreNotifications().size() - 1) {
            my_log.warning("invalid store notification id");
            throw new UserException("invalid store notification id");
        }
        get_subscriber(userid).getComplaintNotifications().get(storeNotificaionId).setReadTrue();

    }

    public List<ComplaintNotification> getComplaintNotifications(String userid) throws UserException {
        if (get_subscriber(userid) == null) {
            my_log.warning("user " + userid + " does not exist");
            throw new UserException("user " + userid + " does not exist");
        }
        return get_subscriber(userid).getComplaintNotifications();
    }

    public List<ComplaintNotification> getAdminComplaintNotifications(String userId) throws UserException { //Abed changes
        my_log.info("getting complaint notifications for user " + userId);
        if (get_subscriber(userId) == null) {
            my_log.warning("user " + userId + " does not exist - failed to get notifications");
            throw new UserException("user " + userId + " does not exist - failed to get notifications");
        }
        if (!get_subscriber(userId).isLogged_in()) {
            my_log.warning("user " + userId + " is not online - failed to get notifications");
            throw new UserException("user " + userId + " is not online - failed to get notifications");
        }
        if (!checkIfAdmin(userId)) {
            my_log.warning("user " + userId + " is not admin - failed to get complaint notifications");
            throw new UserException("user " + userId + " is not admin - failed to get complaint notifications");
        }
        return get_subscriber(userId).getComplaintNotifications();
    }

    public String getPermissionType(String username) {
        if (getSystemAdmins().contains(get_subscriber(username)))
            return "admin";
        if (get_subscriber(username) != null)
            return "subscriber";
        if (getGuest(username) != null)
            return "guest";
        return null;
    }

    // TODO: 09/06/2022 dan this is working example for simple variables
    @Async
    public CompletableFuture<Integer> getOnlineUsersNum() {
        return CompletableFuture.completedFuture(onlineUsers);
    }

    public int getRegisteredUsersNum() {
        return registeredUsers;
    }

    // TODO: 09/06/2022 dan this example with outside function also work
    @Async
    public CompletableFuture<Float> getPriceOfCartAfterDiscount(String user_id, ExternalConnectionHolder externalConnectionHolder) throws StorePolicyViolatedException, UserException, InterruptedException {
        if ((getGuest(user_id) != null)){
           return CompletableFuture.completedFuture(getGuest(user_id).getPriceOfCartAfterDiscount(externalConnectionHolder));
        }
        if (get_subscriber(user_id) == null) {
            throw new UserException("User " + user_id + " doesn't exist");
        }
        if (!checkIfUserIsLoggedIn(user_id)) {
            my_log.warning("user " + user_id + " is not logged in");
            throw new UserException("User " + user_id + " is not logged in");
        }
        return CompletableFuture.completedFuture(get_subscriber(user_id).getPriceOfCartAfterDiscount(externalConnectionHolder));
    }

    @Async
    public CompletableFuture<Float> getPriceOfCartBeforeDiscount(String user_id, ExternalConnectionHolder externalConnectionHolder) throws StorePolicyViolatedException, UserException {
        if ((getGuest(user_id) != null)){
            return CompletableFuture.completedFuture(getGuest(user_id).getPriceOfCartAfterDiscount(externalConnectionHolder));
        }
        if (get_subscriber(user_id) == null) {
            throw new UserException("User " + user_id + " doesn't exist");
        }
        if (!checkIfUserIsLoggedIn(user_id)) {
            my_log.warning("user " + user_id + " is not logged in");
            throw new UserException("User " + user_id + " is not logged in");
        }
        return CompletableFuture.completedFuture(get_subscriber(user_id).getPriceOfCartAfterDiscount(externalConnectionHolder));
    }


    public void initializeLogoutAll() throws UserException, InterruptedException {
        logout("amit");
        logout("guy");
        logout("pam123");
        logout("alex12");
        logout("ronn");
        logout("rotman");
        logout("dashy");
        logout("crim");
        logout("karma");
        logout("damon");
        logout("tjhaly");
        logout("spart");
        logout("yoav");
    }
}
