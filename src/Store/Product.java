package Store;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private final String id;
    private String name;
    private float price;
    private float rating;//star system changing according to reviews
    private List<Review> reviews;
    private int supply = 0;



    public Product(String id, String name, float price, int supply) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.reviews = new ArrayList<Review>();
        this.supply = supply;
        this.rating = 0;
    }

    public void addReview(float rating, String userId, String title, String body){
        reviews.add(new Review(rating, userId, title, body));
        float sum = 0;
        for (Review rev :
                reviews) {
            sum = rev.getRating()+sum;
        }
        this.rating = sum / (reviews.size());
    }

    public String getId() {
        return id;
    }

    public int getSupply() {
        return supply;
    }

    public void addToSupply(int howMach) {
        supply += howMach;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float newPrice) {
        this.price = newPrice;
    }

    public float getRating() {
        return rating;
    }


}
