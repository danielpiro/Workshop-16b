package main.java.com.example.demo.Store.StorePurchase.Discounts;

import CustomExceptions.SupplyManagementException;
import Store.ProductsCategories;
import main.java.com.example.demo.Store.StorePurchase.PurchasableProduct;

public class DiscountBox implements PurchasableProduct {
    private PurchasableProduct myProduct;
    private float updatedPrice; //price after discount for this purchase
    private int Amount;


    public DiscountBox(PurchasableProduct myProduct, float updatedPrice, int amount) {
        this.myProduct= myProduct;
        this.Amount = amount;
        this.updatedPrice = updatedPrice;
    }

    @Override
    public String getId() {
        return myProduct.getId();
    }
    @Override
    public int getSupply() {
        return myProduct.getSupply();
    }

    @Override
    public void editSupply(int newSupply) throws SupplyManagementException {
        myProduct.editSupply(newSupply);
    }

    @Override
    public void editPrice(float newPrice) throws SupplyManagementException {
        updatedPrice = newPrice;
    }

    @Override
    public float getPrice() {
        return updatedPrice;
    }

    @Override
    public ProductsCategories getCategory() {
        return myProduct.getCategory();
    }
    @Override
    public int getAmount() {
        return Amount;
    }


}
