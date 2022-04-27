package Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ExternalConnections.PurchasePolicies;
import ShoppingCart.InventoryProtector;
import GlobalSystemServices.IdGenerator;
import GlobalSystemServices.Log;
import ShoppingCart.ShoppingCart;
import User.*;

public class UserController {
    private List<Subscriber> user_list;
    private List<Guest> guest_list;
    private Subscriber admin;
   Log my_log = Log.getLogger();

    public UserController() throws IOException {
        user_list = new ArrayList<>();
        guest_list=new ArrayList<>();
        admin = new Subscriber("Admin_1","BigBoss");
        add_subscriber(admin);
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
    public int addProduct(String user_id,String productID, String storeID, int amount,boolean auctionOrBid) {
        if(get_subscriber(user_id)==null){
            throw new IllegalArgumentException("User doesn't exist");
        }
        if(checkIfUserIsLoggedIn(user_id)){
            throw new IllegalArgumentException("User is not logged in");
        }
     return get_subscriber(user_id).addProduct(productID,storeID,amount,auctionOrBid);
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
    public boolean recordPurchase (String user_id) {
        if(get_subscriber(user_id)==null){
            throw new IllegalArgumentException("User doesn't exist");
        }
        if(checkIfUserIsLoggedIn(user_id)){
            throw new IllegalArgumentException("User is not logged in");
        }
        return get_subscriber(user_id).recordPurchase();
    }

        //String sender_id, String message, LocalDate date,String storeName
    public void sendComplaint(String userId, String StoreName,String complaint ){
        if(get_subscriber(userId)==null){
            throw new IllegalArgumentException("User doesn't exist");
        }
        if(get_subscriber(userId)!=null&&userId!="Admin_1")
            admin.getBuffer().add(new Message(userId,complaint, LocalDate.now(),StoreName));
        else{
            throw new IllegalArgumentException("system admin can't send a complaint to himself or sending user doesn't exist");
        }
    }

    public void sign_up(String user_name, String password) {
        my_log.logger.info("Sign Up");
        if(get_subscriber(user_name)==null){
            Subscriber s = new Subscriber(user_name,password);
            add_subscriber(s);
        }
        else{
            throw new IllegalArgumentException("user already exists");
        }
    }

    public void login(String user_name, String password) {
        my_log.logger.info("login");
        if(get_subscriber(user_name)==null){
            throw new IllegalArgumentException("User doesn't exist");
        }
       else if(!get_subscriber(user_name).isLogged_in()&&check_password(user_name,password)) {
            get_subscriber(user_name).setLogged_in(true);
        }
        else{
            throw new IllegalArgumentException("user is already logged in");
        }

    }

    public void logout(String user_name) {
        my_log.logger.info("logout");
        if(get_subscriber(user_name)==null){
            throw new IllegalArgumentException("User doesn't exist");
        }
        else if(get_subscriber(user_name).isLogged_in()) {
            get_subscriber(user_name).setLogged_in(false);
        }
        else{
            throw new IllegalArgumentException("user is already logged out");
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


    public List<Guest> getGuest_list() {
        return guest_list;
    }


    public List<Subscriber> getUser_list() {
        return user_list;
    }


    public void add_subscriber(Subscriber s){
        user_list.add(s);
    }


    public Subscriber getSystemAdmin(){
        return admin;
    }

    public boolean checkIfUserIsLoggedIn(String id){
        return get_subscriber(id).isLogged_in();
    }


}
