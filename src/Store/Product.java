package Store;

import Views.ProductView;

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
        editPrice(price);
        this.reviews = new ArrayList<Review>();
        editSupply(supply);
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

    public void editSupply(int newSupply) {
        if(supply >=0)
            this.supply = newSupply;
        else
            throw new RuntimeException("supply cant be negative");
    }
    public void editPrice(float newPrice) {
        if(price >=0)
            this.price = newPrice;
        else
            throw new RuntimeException("price cant be negative");
    }
    public void editName(String newName) {
        this.name = newName;
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


    public ProductView getProductView() {
        ProductView newProduct = new ProductView();
        newProduct.id = id;
        newProduct.name = name;
        newProduct.price = price;
        newProduct.reviews = reviews;
        newProduct.supply = supply;
        newProduct.rating = rating;
        return newProduct;
    }
}
