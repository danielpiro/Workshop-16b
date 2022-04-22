package User;

import java.util.ArrayList;
import java.util.List;

public class Guest extends User {
    private int id;

    public Guest(int id) {
        super();
        this.id=id;

    }
    public int getId() {
        return id;
    }
}
