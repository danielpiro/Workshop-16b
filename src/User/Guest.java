package User;

import java.util.ArrayList;
import java.util.List;

public class Guest extends User {
    private String id;

    public Guest(String id) {
        super();
        this.id=id;

    }
    public String getId() {
        return id;
    }
}
