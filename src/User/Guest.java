package User;

import java.util.ArrayList;
import java.util.List;

public class Guest extends User {

    public Guest(String id) {
        super();
        this.name=id;

    }
    public String getId() {
        return name;
    }
}
