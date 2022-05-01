package Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.InventoryProtector;
import GlobalSystemServices.IdGenerator;
import GlobalSystemServices.Log;
import ShoppingCart.ShoppingCart;
import User.*;

public class UserController {
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
        if (!getUser_list().contains(get_subscriber(whoIsAdding))) {
            my_log.logger.warning("the user that we treated as admin is not a user at all!!");
            throw new IllegalArgumentException("the user that we treated as admin is not a user at all!!");
        }
        synchronized (get_subscriber(whoIsAdding).getLock()) {
            if (!getSystemAdmins().contains(get_subscriber(whoIsAdding))) {
                my_log.logger.warning("the user that is trying to add in not an admin");
                throw new IllegalArgumentException("the user that is trying to add in not an admin");
            }
            synchronized (get_subscriber(user_toMakeAdmin).getLock()) {
                if (getSystemAdmins().contains(get_subscriber(user_toMakeAdmin))) {
                    my_log.logger.warning("this user we are trying to add is already a system admin");
                    throw new IllegalArgumentException("this user we are trying to add is already a system admin");
                }
                else if (!getUser_list().contains(get_subscriber(user_toMakeAdmin))) {
                    my_log.logger.warning("the given user name is not a valid name for an existing user in the system");
                    throw new IllegalArgumentException("the given user name is not a valid name for an existing user in the system");
                }
                else if (!checkIfUserIsLoggedIn(whoIsAdding)) {
                    my_log.logger.warning("the admin that is trying to add another admin is not logged in");
                    throw new IllegalArgumentException("the admin that is trying to add another admin is not logged in");
                }
                getSystemAdmins().add(get_subscriber(user_toMakeAdmin));
            }
        }
    }
    public ShoppingCart getShoppingCart(String user_Id){
        if(get_subscriber(user_Id)==null){
            my_log.logger.warning("User "+ user_Id +" doesn't exist");
            throw new IllegalArgumentException("User doesn't exist");
        }
            if (!checkIfUserIsLoggedIn(user_Id)) {
                my_log.logger.warning("User "+user_Id+ " is not logged in");
                throw new IllegalArgumentException("User is not logged in");
            }
            return get_subscriber(user_Id).getShoppingCart();
    }

    public boolean containsStore(String user_id,String storeID) {
        if(get_subscriber(user_id)==null){
            my_log.logger.warning("User "+ user_id +" doesn't exist");
            throw new IllegalArgumentException("User doesn't exist");
        }
            if (!checkIfUserIsLoggedIn(user_id)) {
                my_log.logger.warning("User "+ user_id +" is not logged in");
                throw new IllegalArgumentException("User is not logged in");
            }
            return get_subscriber(user_id).containsStore(storeID);
    }

    public int removeProduct(String user_id,String productID, String storeID, int amount) {
        if (get_subscriber(user_id) == null) {
            my_log.logger.warning("User "+ user_id +" doesn't exist");
            throw new IllegalArgumentException("User doesn't exist");
        }
            return get_subscriber(user_id).removeProduct(productID, storeID, amount);
        }

    public int addProduct(String user_id, String productID, String storeID, int amount, InventoryProtector inventoryProtector, boolean auctionOrBid) {
        if (get_subscriber(user_id) == null) {
            throw new IllegalArgumentException("User doesn't exist");
        }
            if (!checkIfUserIsLoggedIn(user_id)) {
                throw new IllegalArgumentException("User is not logged in");
            }
            return get_subscriber(user_id).addProduct(productID, storeID, amount, inventoryProtector, auctionOrBid);
    }


    public String getCartInventory(String user_id) {
        if(get_subscriber(user_id)==null){
            my_log.logger.warning("user "+user_id + " doesn't exist");
            throw new IllegalArgumentException("User doesn't exist");
        }
            if (checkIfUserIsLoggedIn(user_id)) {
                my_log.logger.warning("user "+user_id + " is not logged in");
                throw new IllegalArgumentException("User is not logged in");
            }
            return get_subscriber(user_id).getCartInventory();
    }
    public float purchaseCart(String user_id, ExternalConnectionHolder externalConnectionHolder) {
        if(get_subscriber(user_id)==null){
            throw new IllegalArgumentException("User doesn't exist");
        }
            if (!checkIfUserIsLoggedIn(user_id)) {
                my_log.logger.warning("user "+user_id + " is not logged in");
                throw new IllegalArgumentException("User is not logged in");
            }
            return get_subscriber(user_id).purchaseCart(externalConnectionHolder);

    }

        //String sender_id, String message, LocalDate date,String storeName
    public void sendComplaint(String userId, String StoreName,String complaint ){
        if(get_subscriber(userId)==null){
            my_log.logger.warning("user "+userId + " does not exist");
            throw new IllegalArgumentException("User doesn't exist");
        }
            if (!IdGenerator.getInstance().checkIfAdmin(userId))
                admin.getBuffer().add(new Message(userId, complaint, LocalDate.now(), StoreName));
            else {
                my_log.logger.warning("user "+userId + "is a system admin, system admin can't send a complaint to himself or sending user doesn't exist");
                throw new IllegalArgumentException("system admin can't send a complaint to himself or sending user doesn't exist");
            }
    }

    public boolean sign_up(String user_name, String password) {
        synchronized (signUpLock) {
            my_log.logger.info("Sign Up");
            if (get_subscriber(user_name) == null) {
                Subscriber s = new Subscriber(user_name, password);
                add_subscriber(s);
                return true;
            }
        }
        my_log.logger.warning("user "+user_name + "failed to sign up");
        return false;
    }

    public boolean login(String user_name, String password) {
        if (checkIfUserExists(user_name)) {
            synchronized (get_subscriber(user_name).getLock()) {
                if (checkIfUserExists(user_name)) {
                my_log.logger.info("login");
                if (get_subscriber(user_name) == null) {
                    return false;
                } else if (get_subscriber(user_name).isLogged_in() && check_password(user_name, password)) {
                    return false;
                } else if(!get_subscriber(user_name).isLogged_in() && check_password(user_name, password)) {
                    get_subscriber(user_name).setLogged_in(true);
                    return true;
                }
            }
                else {
                    my_log.logger.warning("user "+user_name + "doesn't exist -- failed to login");
                    return false;
                   // ("user has been deleted") // add logger
                }
            }

        }
        my_log.logger.warning("user "+user_name + "failed login");
        return false;
    }

    public boolean logout(String user_name) {
            if (get_subscriber(user_name) == null) {
                my_log.logger.warning(user_name + " is null");
                return false;
            }
                synchronized (get_subscriber(user_name).getLock()) {
                    if (!get_subscriber(user_name).isLogged_in()) {
                        my_log.logger.warning("user "+user_name + " is not logged in");
                        return false;
                    } else {
                        my_log.logger.info("user "+user_name + " successfully logged out");
                        get_subscriber(user_name).setLogged_in(false);
                        return true;
                    }
                }

        }


    public  boolean check_password(String user_name, String password) {
        if (get_subscriber(user_name) == null) {
            my_log.logger.warning("User "+user_name + " doesn't exist");
            return false;
        }
            if (password.length() > 2) {
                Subscriber subscriber = get_subscriber(user_name);
                Encrypt enc = subscriber.getEncryption();
                String password_dyc = enc.decrypt(subscriber.getPassword());
                return password_dyc.equals(password);
            }
            my_log.logger.warning("invalid password for user "+user_name );
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
            my_log.logger.warning(" user "+userID + "doesn't exist");
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
            Guest guest = new Guest(IdGenerator.getInstance().getGuestId());
            getGuest_list().add(guest);
            return guest;
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
    public void Add_Query(String user_name,String query) { //3.5
        if (get_subscriber(user_name) == null) {
            my_log.logger.warning(" user "+user_name+ "doesn't exist");
            throw new IllegalArgumentException("User doesn't exist");
        }
            Subscriber subscriber = get_subscriber(user_name);
            my_log.logger.warning(" user "+user_name+ "successfully added a query ");
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

}
