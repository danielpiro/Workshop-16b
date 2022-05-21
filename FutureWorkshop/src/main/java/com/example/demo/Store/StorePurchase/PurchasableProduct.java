package Store.StorePurchase;

import CustomExceptions.SupplyManagementException;
import Store.ProductsCategories;

public interface PurchasableProduct {
    int getAmount();
    public String getId();
    public int getSupply();
    public void editSupply(int newSupply) throws SupplyManagementException;
    public void editPrice(float newPrice) throws SupplyManagementException;
    public float getPrice();
    public ProductsCategories getCategory();


}

