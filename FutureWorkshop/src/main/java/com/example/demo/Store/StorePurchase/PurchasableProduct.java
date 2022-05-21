package com.example.demo.Store.StorePurchase;

import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.Store.ProductsCategories;

public interface PurchasableProduct {
    int getAmount();
    public String getId();
    public int getSupply();
    public void editSupply(int newSupply) throws SupplyManagementException;
    public void editPrice(float newPrice) throws SupplyManagementException;
    public float getPrice();
    public ProductsCategories getCategory();


}

