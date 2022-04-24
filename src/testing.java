import Controllers.StoreController;
import StorePermissin.User;
import StorePermissin.fakeUser;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.List;

public class testing {
    public static void main(String[] args) {
        try {
            StoreController con = new StoreController();

            User guy = new fakeUser();
            List<String> managers = new ArrayList<>();
            managers.add(guy.getUserId());
            con.openNewStore("guys store", managers);

            con.addNewProduct("StoreID_0", guy.getUserId(), "firstProduct", 2f, 4, "Other");
        } catch (NoPermissionException e) {
            e.printStackTrace();
        }
    }
}


