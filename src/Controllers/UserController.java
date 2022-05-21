package Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import CustomExceptions.UserException;
import ExternalConnections.ExternalConnectionHolder;
import NotificationsManagement.ComplaintNotification;
import NotificationsManagement.StoreNotification;
import NotificationsManagement.NotificationReceiver;
import ShoppingCart.InventoryProtector;
import GlobalSystemServices.IdGenerator;
import GlobalSystemServices.Log;
import ShoppingCart.ShoppingCart;
import User.*;

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



    public UserController() throws IOException {
        user_list = new ArrayList<>();
        guest_list=new ArrayList<>();
        system_admins = new ArrayList<>();
        admin = new Subscriber(IdGenerator.getInstance().getAdminId(),"BigBoss");
        add_subscriber(admin);
        add_admin(admin);
    }

    //WE DECIDED THAT ALL THE SYSTEM ADMINS WILL HAVE A SIMILAR USERNAME AS FOLLOWS:
    // Admin_1 Admin_2 Admin_3 and so on
    public void addSystemAdmin(String whoIsAdding,String user_toMakeAdmin) {
        my_log.logger.info("user "+whoIsAdding+ "is trying to add "+user_toMakeAdmin+ "as admin");
        if (!getUser_list().contains(get_subscriber(whoIsAdding))) {
            my_log.error_logger.warning("the user that we treated as admin is not a user at all!!");
            throw new IllegalArgumentException("the user that we treated as admin is not a user at all!!");
        }
        synchronized (get_subscriber(whoIsAdding).getLock()) {
            if (!getSystemAdmins().contains(get_subscriber(whoIsAdding))) {
                my_log.error_logger.warning("the user that is trying to add in not an admin");
                throw new IllegalArgumentException("the user that is trying to add in not an admin");
            }
            synchronized (get_subscriber(user_toMakeAdmin).getLock()) {
                if (getSystemAdmins().contains(get_subscriber(user_toMakeAdmin))) {
                    my_log.error_logger.warning("this user we are trying to add is already a system admin");
                    throw new IllegalArgumentException("this user we are trying to add is already a system admin");
                }
                else if (!getUser_list().contains(get_subscriber(user_toMakeAdmin))) {
                    my_log.error_logger.warning("the given user name is not a valid name for an existing user in the system");
                    throw new IllegalArgumentException("the given user name is not a valid name for an existing user in the system");
                }
                else if (!checkIfUserIsLoggedIn(whoIsAdding)) {
                    my_log.error_logger.warning("the admin that is trying to add another admin is not logged in");
                    throw new IllegalArgumentException("the admin that is trying to add another admin is not logged in");
                }
                getSystemAdmins().add(get_subscriber(user_toMakeAdmin));
            }
        }
    }
    public ShoppingCart getShoppingCart(String user_Id) throws UserException {
        if(get_subscriber(user_Id)==null){
            my_log.error_logger.warning("User "+ user_Id +" doesn't exist");
            throw new UserException("User " +user_Id+ "doesn't exist");
        }
            if (!checkIfUserIsLoggedIn(user_Id)) {
                my_log.error_logger.warning("User "+user_Id+ " is not logged in");
                throw new UserException("User "+user_Id+ "is not logged in");
            }
            return get_subscriber(user_Id).getShoppingCart();
    }

    public boolean containsStore(String user_id,String storeID) throws UserException {
        if(get_subscriber(user_id)==null){
            my_log.error_logger.warning("User "+ user_id +" doesn't exist");
            throw new UserException("User "+ user_id+ "doesn't exist");
        }
            if (!checkIfUserIsLoggedIn(user_id)) {
                my_log.error_logger.warning("User "+ user_id +" is not logged in");
                throw new UserException("User "+user_id+ "is not logged in");
            }
            return get_subscriber(user_id).containsStore(storeID);
    }

    public int removeProduct(String user_id,String productID, String storeID, int amount) throws UserException {
        if (get_subscriber(user_id) == null) {
            my_log.error_logger.warning("User "+ user_id +" doesn't exist");
            throw new UserException("User "+user_id+ "doesn't exist");
        }
            return get_subscriber(user_id).removeProduct(productID, storeID, amount);
        }

    public int addProduct(String user_id, String productID, String storeID, int amount, InventoryProtector inventoryProtector, boolean auctionOrBid) throws UserException {
        if (get_subscriber(user_id) == null) {
            throw new UserException("User "+user_id+ "doesn't exist");
        }
            if (!checkIfUserIsLoggedIn(user_id)) {
                throw new UserException("User "+user_id+ "is not logged in");
            }
            return get_subscriber(user_id).addProduct(productID, storeID, amount, inventoryProtector, auctionOrBid);
    }


    public String getCartInventory(String user_id) throws UserException {
        if(get_subscriber(user_id)==null){
            my_log.error_logger.warning("user "+user_id + " doesn't exist");
            throw new UserException("User " +user_id + "doesn't exist");
        }
            if (checkIfUserIsLoggedIn(user_id)) {
                my_log.error_logger.warning("user "+user_id + " is not logged in");
                throw new UserException("User " +user_id + "is not logged in");
            }
            return get_subscriber(user_id).getCartInventory();
    }
    public float purchaseCart(String user_id, ExternalConnectionHolder externalConnectionHolder) throws UserException {
        if(get_subscriber(user_id)==null){
            throw new UserException("User "+user_id + "doesn't exist");
        }
            if (!checkIfUserIsLoggedIn(user_id)) {
                my_log.error_logger.warning("user "+user_id + " is not logged in");
                throw new UserException("User "+user_id +"is not logged in");
            }
            return get_subscriber(user_id).purchaseCart(externalConnectionHolder);

    }

        //String sender_id, String message, LocalDate date,String storeName
    /*
    public void sendComplaint(String userId, String StoreName,String complaint ){
        if(get_subscriber(userId)==null){
            my_log.logger.warning("user "+userId + " does not exist");
            throw new IllegalArgumentException("User doesn't exist");
        }
            if (!IdGenerator.getInstance().checkIfAdmin(userId))
                admin.getBuffer().add(new Message(StoreName, complaint)); // TODO
            else {
                my_log.logger.warning("user "+userId + "is a system admin, system admin can't send a complaint to himself or sending user doesn't exist");
                throw new IllegalArgumentException("system admin can't send a complaint to himself or sending user doesn't exist");
            }
    }
*/
    public boolean sign_up(String guest_id,String user_name, String password){
        my_log.logger.info("a guest with id: "+guest_id+ " is trying to sign up with user name : "+user_name);
        if(getGuest(guest_id)==null){
            my_log.error_logger.warning("the id "+guest_id + " is not a valid guest id in the system - failed to sign up");
            return false;
        }
        if (get_subscriber(user_name) != null) {
            my_log.error_logger.warning("user "+user_name + " already exists in the system - failed to sign up");
            return false;
        }
        if(user_name == null){
            my_log.error_logger.warning("user "+user_name + " is null - failed to sign up");
            return false;
        }
        if(password == null){
            my_log.error_logger.warning("user "+user_name + " password's is null - failed to sign up");
            return false;
        }
        if(user_name.isEmpty()){
            my_log.error_logger.warning("user "+user_name + " is empty - failed to sign up");
            return false;
        }
        if(password.isEmpty()){
            my_log.error_logger.warning("user "+user_name + " password's is empty - failed to sign up");
            return false;
        }
        if(user_name.length()<2){
            my_log.error_logger.warning("user "+user_name + " length is less than 2 - failed to sign up");
            return false;
        }
        if(password.length()<2){
            my_log.error_logger.warning("user "+user_name + " password's lenth is less than 2 - failed to sign up");
            return false;
        }
        synchronized (signUpLock) {
                Subscriber s = new Subscriber(user_name, password);
                add_subscriber(s);
               s.setShoppingCart(getGuestShoppingCart(guest_id));
                removeGuest(guest_id);
                return true;
        }
    }

    public boolean login(String user_name, String password) {
        my_log.logger.info("user "+ user_name+ " is trying to login");
        if (checkIfUserExists(user_name)) {
            synchronized (get_subscriber(user_name).getLock()) {
                if (checkIfUserExists(user_name)) {
                if (get_subscriber(user_name) == null) {
                    my_log.error_logger.warning("user "+user_name+ " failed to login");
                    return false;
                } else if (get_subscriber(user_name).isLogged_in() && check_password(user_name, password)) {
                    my_log.error_logger.warning("user "+user_name+ " is already logged in");
                    return false;
                } else if(!get_subscriber(user_name).isLogged_in() && check_password(user_name, password)) {
                    get_subscriber(user_name).setLogged_in(true);
                    return true;
                }
            }
                else {
                    my_log.error_logger.warning("user "+user_name + " doesn't exist -- failed to login");
                    return false;
                   // ("user has been deleted") // add logger
                }
            }

        }
        my_log.error_logger.warning("user "+user_name + " failed login");
        return false;
    }

    public boolean logout(String user_name) {
        my_log.logger.info("user "+ user_name+ " is trying to logout");
            if (get_subscriber(user_name) == null) {
                my_log.error_logger.warning("failed to logout because " +user_name + " is null");
                return false;
            }
                synchronized (get_subscriber(user_name).getLock()) {
                    if (!get_subscriber(user_name).isLogged_in()) {
                        my_log.error_logger.warning("failed to logout - user "+user_name + " is not logged in");
                        return false;
                    } else {
                        my_log.logger.info("user "+user_name + " successfully logged out");
                        get_subscriber(user_name).setLogged_in(false);
                       Guest g = addGuest();
                       g.setShoppingCart(getSubscriberCart(user_name));
                        return true;
                    }
                }

        }


    public  boolean check_password(String user_name, String password) {
        if (get_subscriber(user_name) == null) {
            my_log.error_logger.warning("User "+user_name + " doesn't exist");
            return false;
        }
            if (password.length() > 2) {
                Subscriber subscriber = get_subscriber(user_name);
                return subscriber.getPassword().equals(password);
            }
            my_log.error_logger.warning("invalid password for user "+user_name );
            return false;
    }

    public Subscriber get_subscriber(String user_name){
        Subscriber subscriber = null;
        for (Subscriber user : user_list) {
            if (user.getName().equals(user_name)) {
                subscriber = user;
            }
        }
        return subscriber;
    }
    public boolean checkIfUserExists(String userID) {
        if (get_subscriber(userID) == null) {
            my_log.error_logger.warning(" user "+userID + "doesn't exist");
            return false;
        }
        synchronized (get_subscriber(userID).getLock()) {
            return true;
        }
    }

    public Guest getGuest(String id){
        for(Guest g : getGuest_list()){
            if(g.getId()==id)
                return g;
        }
        return null;
    }

    public Guest addGuest(){
        synchronized (guest) {
            Guest g = new Guest(IdGenerator.getInstance().getGuestId());
            getGuest_list().add(g);
            return g;
        }
    }
    public String GuestExitSystem(String name) {
            for (Guest g : getGuest_list())
                if (g.name.equals(name)) {
                    getGuest_list().remove(g);
                    return g.name;
                }
            return null;
    }
    public void Add_Query(String user_name,String query) throws UserException {
        if (get_subscriber(user_name) == null) {
            my_log.error_logger.warning(" user "+user_name+ "doesn't exist");
            throw new UserException("User " +user_name + "doesn't exist");
        }
        if (!get_subscriber(user_name).isLogged_in()) {
            my_log.error_logger.warning(" user "+user_name+ "is offline");
            throw new UserException("User " +user_name + "is offline");
        }
            Subscriber subscriber = get_subscriber(user_name);
            my_log.error_logger.warning(" user "+user_name+ "successfully added a query ");
            subscriber.getQueries().add(query);
    }

    public boolean deleteUser(String whosDeleting,String whosBeingDeleted) {
        synchronized (deleteUser) {
            if (checkIfUserExists(whosDeleting) && checkIfUserIsLoggedIn(whosDeleting) && IdGenerator.getInstance().checkIfAdmin(whosDeleting)) {
                if (checkIfUserExists(whosDeleting) && !IdGenerator.getInstance().checkIfAdmin(whosBeingDeleted)) {
                    for (Subscriber s : getUser_list()) {
                        if (s.getName().equals(whosBeingDeleted)) {
                            getUser_list().remove(s);
                            return true;
                        }
                    }
                }
            }
            my_log.logger.info(" user " + whosBeingDeleted + "failed to delete user " + whosBeingDeleted);
            return false;
        }
    }

    public boolean checkIfGuestExists(String guestId){
        for(Guest g : getGuest_list()){
            if(g.name.equals(guestId))
                return true;
        }
        return false;
    }
    public void removeGuest(String guestId){
        guest_list.removeIf(guest -> guest.name.equals(guestId));
    }
    public ShoppingCart getGuestShoppingCart(String guestId){
        for(Guest g : getGuest_list()){
            if (g.name.equals(guestId))
                return g.getShoppingCart();
        }
        return null;
    }
    public ShoppingCart getSubscriberCart(String subId){
        for(Subscriber s : getUser_list())
            if(s.getName().equals(subId))
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
    public boolean checkIfAdmin(String userId){
        if(getSystemAdmins().contains(get_subscriber(userId)))
        return true;
        return false;
    }

    @Override
    public void sendNotificationTo(List<String> userIds, StoreNotification storeNotification) throws UserException {
        my_log.logger.info("sending store notification for some users");
        for (String s : userIds) {
            if (!checkIfUserExists(s)) {
                my_log.error_logger.warning("can't send store notification because user " + s + "doesn't exist");
                throw new UserException("can't send store notification because user " + s + "doesn't exist");
            }
              get_subscriber(s).addNotification(storeNotification.getDeepCopy());
        }
    }

    @Override
    public void sendComplaintTo(String senderId,List<String> adminIds, ComplaintNotification complaintNotification) throws UserException {
        my_log.logger.info("user "+senderId+" is wants to send a complaint to system admins");
        if(get_subscriber(senderId)==null)
            throw new UserException("user "+senderId + "doesn't exist");
        if(!get_subscriber(senderId).isLogged_in()) {
            my_log.error_logger.warning("the sender " + senderId + " is not online");
            throw new UserException("the sender " + senderId + " is not online");
        }
        for (String s : adminIds) {
            if (!checkIfUserExists(s))
                throw new UserException("user " +s + "doesn't exist");
            if (!checkIfAdmin(s)) {
                my_log.error_logger.warning("trying to send a complaint to a user " + s + "which is not an admin");
                throw new UserException("trying to send a complaint to a user " + s + "which is not an admin");
            }
                get_subscriber(s).addComplaint(complaintNotification.getDeepCopy());
        }
    }
    public void readStoreNotification(String userid,int storeNotificaionId) throws UserException {
        my_log.logger.info("user "+userid+" wants to read store notification with id "+ storeNotificaionId);
        if (get_subscriber(userid)==null) {
            my_log.error_logger.warning("user " + userid + " does not exist");
            throw new UserException("user " + userid + " does not exist");
        }
        if (!get_subscriber(userid).isLogged_in()) {
            my_log.error_logger.warning("user " + userid + " is not online");
            throw new UserException("user " + userid + " is not online");
        }
        if(storeNotificaionId<0||storeNotificaionId>get_subscriber(userid).getStoreNotifications().size()-1) {
            my_log.error_logger.warning("invalid store notification id");
            throw new UserException("invalid store notification id");
        }
            get_subscriber(userid).getComplaintNotifications().get(storeNotificaionId).setReadTrue();

    }
    public void readComplaintNotification(String userid,int complaintNotificaionId) throws UserException {
        my_log.logger.info("user "+userid+" wants to read store notification with id "+ complaintNotificaionId);
        if (get_subscriber(userid)==null) {
            my_log.error_logger.warning("user " + userid + " does not exist");
            throw new UserException("user " + userid + " does not exist");
        }
        if (!get_subscriber(userid).isLogged_in()) {
            my_log.error_logger.warning("user " + userid + " is not online");
            throw new UserException("user " + userid + " is not online");
        }
        if (!getSystemAdmins().contains(get_subscriber(userid))) {
            my_log.error_logger.warning("user " + userid + " is not admin(only admins can recieve and read complaints)");
            throw new UserException("user " + userid + " is not admin(only admins can recieve and read complaints)");
        }
        if(complaintNotificaionId<0||complaintNotificaionId>get_subscriber(userid).getComplaintNotifications().size()-1) {
            my_log.error_logger.warning("invalid complaint notification id");
            throw new UserException("invalid complaint notification id");
        }
            get_subscriber(userid).getComplaintNotifications().get(complaintNotificaionId).setReadTrue();
    }

    public String getPermissionType(String username){
        if(getSystemAdmins().contains(get_subscriber(username)))
            return "admin";
        if (get_subscriber(username)!=null)
            return "subscriber";
        if (getGuest(username)!=null)
            return "guest";
        return null;
    }

}
