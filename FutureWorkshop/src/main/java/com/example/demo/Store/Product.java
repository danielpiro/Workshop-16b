package com.example.demo.Store;


import com.example.demo.Store.BuyinfOptions.BuyOption;
import com.example.demo.Store.BuyinfOptions.ImmediateBuy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Product {
    private final String id;
    private String name;
    private float price;
    private float rating;//star system changing according to reviews
    private List<Review> reviews;
    private int supply = 0;
    private BuyOption buyOption;
    private int reservedSupply = 0;

    private ProductsCategories category;

    public Product(String id, String name, float price, int supply ,String category) {
        this.id = id;
        this.name = name;
        editPrice(price);
        this.reviews = new ArrayList<>();
        editSupply(supply);
        this.rating = 0;
        this.category = ProductsCategories.valueOf(category);
        buyOption = new ImmediateBuy();
    }

    public void addReview(float rating, String userId, String title, String body){

            reviews.add(new Review(rating, userId, title, body));
            float sum = 0;
        synchronized (reviews) {
            for (Review rev : reviews) {
                sum = rev.getRating() + sum;
            }
            this.rating = sum / (reviews.size());
        }
    }

    public BuyOption getBuyOption() {
        return buyOption;
    }

    public String getId() {
        return id;
    }

    public int getSupply() {
        return supply;
    }

    public void editSupply(int newSupply) {
        if(newSupply < 0)
            throw new RuntimeException("supply cant be negative");
        else
            this.supply = newSupply;
    }
    public void editPrice(float newPrice) {
        if(newPrice >=0)
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


    public ProductsCategories getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = ProductsCategories.valueOf(category);
    }

    public int getReservedSupply() {
        return reservedSupply;
    }

    public void setReservedSupply(int reservedSupply)  {
        this.reservedSupply = reservedSupply;
    }

    public List<Review> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    public void moveFromSupplyToReserved(int howMuch){


    }
}
