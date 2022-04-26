package ShoppingCartTests;

import ShoppingCart.FakeInventoryManager;
import ShoppingCart.ShoppingBasket;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingBasketTest {

    @org.junit.jupiter.api.Test
    void addProduct() {

        FakeInventoryManager fakeInventoryManager = new FakeInventoryManager();
        ShoppingBasket shoppingBasket = new ShoppingBasket("store_1", fakeInventoryManager);
        shoppingBasket.addProduct("Product_1",5);
    }
}