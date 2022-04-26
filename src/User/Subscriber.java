package User;

import java.util.ArrayList;
import java.util.List;


public class Subscriber extends User {
    private String password;
    private boolean logged_in = false;
    private List<String> Queries; //3.5
    private List<Message> buffer;
    Encrypt enc = new Encrypt();;

    public Subscriber(String user_name, String password) {
        super();
        this.password = enc.encrypt(password);
        this.name = user_name;
        Queries= new ArrayList<>();
        buffer = new ArrayList<>();
    }

    public String getName()
    {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLogged_in() {
        return logged_in;
    }

    public void setLogged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }

    public List<String> getQueries() {
        return Queries;
    }

    public Encrypt getEncryption(){return enc;}

    public List<Message> getBuffer(){return buffer;}
}
