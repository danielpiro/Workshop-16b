package com.example.demo.Store.StorePurchase.Policies;

import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.Store.ProductsCategories;
import com.example.demo.Store.StorePurchase.PurchasableProduct;

public class ProductBox implements PurchasableProduct {
    private PurchasableProduct myProduct;
    private int Amount;


    public ProductBox(PurchasableProduct myProduct,int amount) {
        this.myProduct = myProduct;
        this.Amount = amount;

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
        myProduct.editPrice(newPrice);
    }

    @Override
    public float getPrice() {
        return myProduct.getPrice();
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
