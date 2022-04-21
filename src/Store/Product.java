package Store;

import java.util.List;

public class Product {
    private int id;
    private String name;
    private int price;
    private List<Review> reviews;
    private int supply = 0;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }


    public void addToSupply(int howMach) {
        supply += howMach;
    }
}
