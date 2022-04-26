package Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import GlobalSystemServices.IdGenerator;
import GlobalSystemServices.Log;
import User.*;

public class UserController {
    private List<Subscriber> user_list;
    private List<Guest> guest_list;
    private int nextGuestId;
    private Subscriber admin;
    Log my_log = Log.getLogger();

    public UserController() throws IOException {
        user_list = new ArrayList<>();
        guest_list=new ArrayList<>();
        admin = new Subscriber("Admin_1","BigBoss");
        add_subscriber(admin);
    }
    //String sender_id, String message, LocalDate date,String storeName
    public void sendComplaint(String userId, String StoreName,String complaint ){
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
        if(!get_subscriber(user_name).isLogged_in()&&check_password(user_name,password)) {
            get_subscriber(user_name).setLogged_in(true);
        }
        else{
            throw new IllegalArgumentException("user is already logged in");
        }

    }

    public void logout(String user_name) {
        my_log.logger.info("logout");
        if(get_subscriber(user_name).isLogged_in()) {
            get_subscriber(user_name).setLogged_in(false);
        }
        else{
            throw new IllegalArgumentException("user is already logged out");
        }
    }
    // 1234   // **1234**
    public  boolean check_password(String user_name, String password) {
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
        for (Subscriber user : user_list){
            if(user.getName().equals(user_name)) subscriber = user;
        }
        return subscriber;
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
        if(user_name==null||user_name.isEmpty()){
            throw new IllegalArgumentException("invalid user name");
        }
        Subscriber subscriber = get_subscriber(user_name);
        subscriber.getQueries().add(query);
    }


    public List<Guest> getGuest_list() {
        return guest_list;
    }

    public int getNextGuestId() {
        return nextGuestId;
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


}
