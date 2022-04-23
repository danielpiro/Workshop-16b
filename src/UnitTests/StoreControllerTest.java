package UnitTests;

;

import Controllers.StoreController;
import Store.Product;
import StorePermissin.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoreControllerTest {
    StoreController storeController=new StoreController();


    @org.junit.jupiter.api.Test
    void openNewStore() {

        storeController.openNewStore("guys store", guy);

    }
}