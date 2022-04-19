package DomainLayer;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<ShoppingBag> shopping_bag_list;

    public ShoppingCart(){
        shopping_bag_list = new ArrayList<>();
    }

    public List<ShoppingBag> getShopping_bag_list() {
        return shopping_bag_list;
    }

    public void setShopping_bag_list(List<ShoppingBag> shopping_bag_list) {
        this.shopping_bag_list = shopping_bag_list;
    }
}
