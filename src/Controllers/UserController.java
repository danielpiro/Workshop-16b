package Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import CustomExceptions.UserDeleted;
import ExternalConnections.PurchasePolicies;
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
    final Object signUpLock =new Object();

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
    public void addSystemAdmin(String whoIsAdding,String user_toMakeAdmin){
        if(!getUser_list().contains(get_subscriber(whoIsAdding)))
            throw new IllegalArgumentException("the user that we treated as admin is not a user at all!!");
        else if(!getSystemAdmins().contains(get_subscriber(whoIsAdding)))
            throw new IllegalArgumentException("the user that is trying to add in not an admin");
        else if(getSystemAdmins().contains(get_subscriber(user_toMakeAdmin)))
            throw new IllegalArgumentException("this user we are trying to add is already a system admin");
        else if(!getUser_list().contains(get_subscriber(user_toMakeAdmin)))
            throw new IllegalArgumentException("the given user name is not a valid name for an existing user in the system");
        else if(!checkIfUserIsLoggedIn(whoIsAdding))
            throw new IllegalArgumentException("the admin that is trying to add another admin is not logged in");
        getSystemAdmins().add(get_subscriber(user_toMakeAdmin));
    }

    public ShoppingCart getShoppingCart(String user_Id){
        if(get_subscriber(user_Id)==null){
            throw new IllegalArgumentException("User doesn't exist");
        }
        if(checkIfUserIsLoggedIn(user_Id)){
            throw new IllegalArgumentException("User is not logged in");
        }
        return get_subscriber(user_Id).getShoppingCart();
    }
    public boolean containsStore(String user_id,String storeID) {
        if(get_subscriber(user_id)==null){
            throw new IllegalArgumentException("User doesn't exist");
        }
        if(checkIfUserIsLoggedIn(user_id)){
            throw new IllegalArgumentException("User is not logged in");
        }
        return get_subscriber(user_id).containsStore(storeID);
    }

    public int removeProduct(String user_id,String productID, String storeID, int amount) {
        if(get_subscriber(user_id)==null){
            throw new IllegalArgumentException("User doesn't exist");
        }
    return get_subscriber(user_id).removeProduct(productID,storeID,amount);
    }

    public int addProduct(String user_id, String productID, String storeID, int amount, InventoryProtector inventoryProtector, boolean auctionOrBid) {
        if(get_subscriber(user_id)==null){
            throw new IllegalArgumentException("User doesn't exist");
        }
        if(checkIfUserIsLoggedIn(user_id)){
            throw new IllegalArgumentException("User is not logged in");
        }
        return get_subscriber(user_id).addProduct(productID,storeID,amount,inventoryProtector,auctionOrBid);
    }



    public String getCartInventory(String user_id) {
        if(get_subscriber(user_id)==null){
            throw new IllegalArgumentException("User doesn't exist");
        }
        if(checkIfUserIsLoggedIn(user_id)){
            throw new IllegalArgumentException("User is not logged in");
        }
    return get_subscriber(user_id).getCartInventory();
    }
    public float purchaseCart(String user_id,PurchasePolicies purchasePolicies) {
        if(get_subscriber(user_id)==null){
            throw new IllegalArgumentException("User doesn't exist");
        }
        if(checkIfUserIsLoggedIn(user_id)){
            throw new IllegalArgumentException("User is not logged in");
        }
     return get_subscriber(user_id).purchaseCart(purchasePolicies);
    }


        //String sender_id, String message, LocalDate date,String storeName
    public void sendComplaint(String userId, String StoreName,String complaint ){
        if(get_subscriber(userId)==null){
            throw new IllegalArgumentException("User doesn't exist");
        }
        if(get_subscriber(userId)!=null&&IdGenerator.getInstance().checkIfAdmin(userId))
            admin.getBuffer().add(new Message(userId,complaint, LocalDate.now(),StoreName));
        else{
            throw new IllegalArgumentException("system admin can't send a complaint to himself or sending user doesn't exist");
        }
    }

    public boolean sign_up(String user_name, String password) {
        synchronized(signUpLock){
            my_log.logger.info("Sign Up");
            if (get_subscriber(user_name) == null) {
                Subscriber s = new Subscriber(user_name, password);
                add_subscriber(s);
                return true;
            }
            return false;
        }
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
                    return false;
                   // ("user has been deleted") // add logger
                }
            }

        }
      return false;
    }

    public boolean logout(String user_name) {
        my_log.logger.info("logout");
        if(get_subscriber(user_name)==null){
            return false;
        }
        else if(!get_subscriber(user_name).isLogged_in()) {
            return false;
        }
        else{
            return true;
        }
    }

    public  boolean check_password(String user_name, String password) {
        if(get_subscriber(user_name)==null){
            throw new IllegalArgumentException("User doesn't exist");
        }
        if(password.length()>2) {
            Subscriber subscriber = get_subscriber(user_name);
            Encrypt enc = subscriber.getEncryption();
            String password_dyc = enc.decrypt(subscriber.getPassword());
            return password_dyc.equals(password);
        }
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
    public boolean checkIfUserExists(String userID){
        if (get_subscriber(userID)==null)
            return false;
        return true;
    }

    public Guest getGuest(String id){
        for(Guest g : getGuest_list()){
            if(g.getId()==id)
                return g;
        }
        return null;
    }

    public Guest addGuest(){
        Guest guest=new Guest(IdGenerator.getInstance().getGuestId());
        getGuest_list().add(guest);
        return guest;
    }
    public String GuestExitSystem(String name){
        for(Guest g: getGuest_list())
            if(g.name.equals(name)){
                getGuest_list().remove(g);
                return g.name;
            }
                return null;
    }

    public void Add_Query(String user_name,String query) { //3.5
        if(get_subscriber(user_name)==null){
            throw new IllegalArgumentException("User doesn't exist");
        }
        if(user_name==null||user_name.isEmpty()){
            throw new IllegalArgumentException("invalid user name");
        }
        Subscriber subscriber = get_subscriber(user_name);
        subscriber.getQueries().add(query);
    }

    public boolean deleteUser(String whosDeleting,String whosBeingDeleted) {

        if (checkIfUserExists(whosBeingDeleted)) {
            synchronized (get_subscriber(whosBeingDeleted).getLock()) {
                if (checkIfUserExists(whosBeingDeleted) &&
                        checkIfUserIsLoggedIn(whosDeleting) && IdGenerator.getInstance().checkIfAdmin(whosDeleting)  && !IdGenerator.getInstance().checkIfAdmin(whosDeleting) ) {

                    for (Subscriber s : getUser_list()) {
                        if (s.getName().equals(whosBeingDeleted)) {
                            getUser_list().remove(s);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    public List<Guest> getGuest_list() {
        return guest_list;
    }


    public List<Subscriber> getUser_list() {
        return user_list;
    }


    public void add_subscriber(Subscriber s){
        user_list.add(s);
    }

    public void add_admin(Subscriber s){
        system_admins.add(s);
    }

    public List<Subscriber> getSystemAdmins(){return system_admins;}

    public Subscriber getSystemAdmin(){
        return admin;
    }

    public boolean checkIfUserIsLoggedIn(String id){
        return get_subscriber(id).isLogged_in();
    }


}
