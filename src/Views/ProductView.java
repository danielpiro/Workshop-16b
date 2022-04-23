package Views;

import Store.Review;

import java.util.List;

public class ProductView {
    public String id;
    public String name;
    public float price;
    public float rating;//star system changing according to reviews
    public List<Review> reviews;
    public int supply = 0;
}
