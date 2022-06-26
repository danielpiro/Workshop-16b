package com.example.demo.Store;

import com.example.demo.CustomExceptions.Exception.CantPurchaseException;
import com.example.demo.CustomExceptions.Exception.StorePolicyViolatedException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.Database.Service.DatabaseService;
import com.example.demo.ExternalConnections.Old.ExternalConnectionHolder;
import com.example.demo.GlobalSystemServices.IdGenerator;
import com.example.demo.GlobalSystemServices.Log;
import com.example.demo.ShoppingCart.InventoryProtector;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.Discounts.Discount;
import com.example.demo.Store.StorePurchase.Discounts.DiscountBox;
import com.example.demo.Store.StorePurchase.Policies.Policy;
import com.example.demo.Store.StorePurchase.PurchasableProduct;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public class InventoryManager  implements InventoryProtector {
    private ConcurrentHashMap<String, Product> products;
    private CopyOnWriteArrayList<Discount> discounts;
    private CopyOnWriteArrayList<Policy> policies;


    public InventoryManager() {
        this.products = new ConcurrentHashMap<String, Product>();
        this.discounts = new CopyOnWriteArrayList<>();
        policies = new CopyOnWriteArrayList<>();
    }
    public InventoryManager(ConcurrentHashMap<String,Product> products, CopyOnWriteArrayList<Policy> policies) {
        this.products = products;
        this.discounts = new CopyOnWriteArrayList<>();
        this.policies = policies;
    }

    private void checksIfStorePoliciesMet(HashMap<String, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) throws StorePolicyViolatedException {
        HashMap<Product, Integer> newProductAmount = new HashMap<Product, Integer>();
        for(String productId : ProductAmount.keySet()){
            newProductAmount.put(products.get(productId),ProductAmount.get(productId));
        }

        for (Policy p : policies){
            if(!p.checkIfPolicyStands(newProductAmount, externalConnectionHolder, userInfo)){
                throw new  StorePolicyViolatedException(p.getPolicyId());
            }
        }

    }

    public void editProduct(String productId, int newSupply, String newName, float newPrice, String category) throws SupplyManagementException {
        Product Op = products.get(productId);
        if(Op == null){
            throw new  SupplyManagementException("no product with this id- "+productId);
        }
        Op.editSupply(newSupply);
        Op.editPrice(newPrice);
        Op.editName(newName);
        Op.setCategory(category);
    }

    public String addNewProduct(String productName, float price, int howMuch, String category) throws  SupplyManagementException {
        Optional<Product> Op = products.values().stream().filter(np-> productName.equals(np.getName())).findAny();
        if(Op.isPresent()){
            throw new  SupplyManagementException("product with this name- "+productName+" already exist");
        }
        String newId = IdGenerator.getInstance().getProductId();
        products.put(newId,new Product(newId,productName,price,howMuch,category));
        return newId;
    }


    public List<Product> getAllProducts(Predicate<Product> filter) {
        List<Product> PV= new ArrayList<>();
        for (Product p : products.values()) {
            if(filter.test(p)){
                PV.add(p);
            }

        }
        return PV;
    }

    public void addProductReview(String productId, String userId, String title, String body, float rating, DatabaseService databaseService) throws  SupplyManagementException {
        Product Op = products.get(productId);
        if(Op == null){
            throw new  SupplyManagementException("no product with this id- "+productId);
        }
        synchronized (Op) {
            Review newReview = Op.addReview(rating, userId, title, body);
            databaseService.saveReviewByProduct(newReview,productId);
        }
    }

    public void deleteProduct(String productId) throws  SupplyManagementException {
        Product ret = products.remove(productId);
        if(ret == null){
            throw new  SupplyManagementException("product "+ productId+" failed to delete");
        }
    }
    public static <T,S> HashMap<T, S> deepCopyWorkAround(HashMap<T, S> original)
    {
        HashMap<T, S> copy = new HashMap<>();
        for (Map.Entry<T, S> entry : original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue() );
        }
        return copy;
    }

    private float calculatePriceWithDiscount(HashMap<String, Integer> ProductIdAmount,  ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo){
        //convert products to PurchasableProduct
        List<PurchasableProduct> productsConverted = new ArrayList<>();
        for (String Id : ProductIdAmount.keySet()) {
            Product p = products.get(Id);
            PurchasableProduct pp = new DiscountBox(p,p.getPrice(),ProductIdAmount.get(Id));
            productsConverted.add(pp);
        }

        for (Discount d: discounts){
            productsConverted = d.applyDiscount(productsConverted,externalConnectionHolder,userInfo);
        }

        float finalPrice = 0f;
        for (PurchasableProduct product: productsConverted) {
            finalPrice += product.getPrice() * product.getAmount();
        }
        return finalPrice;
    }
    private float calculatePriceWithoutDiscount(HashMap<String, Integer> ProductIdAmount){
        //convert products to PurchasableProduct
        List<PurchasableProduct> productsConverted = new ArrayList<>();
        for (String Id : ProductIdAmount.keySet()) {
            Product p = products.get(Id);
            PurchasableProduct pp = new DiscountBox(p,p.getPrice(),ProductIdAmount.get(Id));
            productsConverted.add(pp);
        }
        float finalPrice = 0f;
        for (PurchasableProduct product: productsConverted) {
            finalPrice += product.getPrice() * product.getAmount();
        }
        return finalPrice;
    }

    public String addNewPolicy(Policy policy){
        policies.add(policy);
        return policy.getPolicyId();
    }
    public void deletePolicy(String policyId) {
        for(Policy p : policies){
            if(p.getPolicyId().equals(policyId)){
                policies.remove(p);
                return;
            }
        }
        Log.getLogger().warning("cant delete, no Policy with this Id");
        throw new RuntimeException("no Policy with this Id");
    }
    public List<Policy> getPolicies() {
        return policies;
    }
    public String addNewDiscount(Discount discount){
        discounts.add(discount);
        return discount.getDiscountId();
    }
    public void deleteDiscount(String discountId) {
        for(Discount d : discounts){
            if(d.getDiscountId().equals(discountId)){
                discounts.remove(d);
                return;
            }
        }
        Log.getLogger().warning("cant delete, no Policy with this Id");
        throw new RuntimeException("no Policy with this Id");
    }
    public List<Discount> getDiscounts(){
        return discounts;
    }
    public Discount getDiscount(String discountId1) {
        for (Discount d: discounts){
            if(d.getDiscountId().equals(discountId1)){
                return d;
            }
        }
        throw new IllegalArgumentException("no discount with this id");
    }
    @Override
    public String getProductName(String productID) {
        return products.get(productID).getName();

    }

    @Override
    public float getProductPrice(String productID) {
        return products.get(productID).getPrice();
    }

    @Override
    public void purchaseSuccessful(HashMap<String, Integer> ProductAmount, boolean success) throws  SupplyManagementException {
        if (success) {
            for (String Id : ProductAmount.keySet()) {
                synchronized (products.get(Id)) {
                    products.get(Id).purchaseCompleted(ProductAmount.get(Id));
                }
            }
        } else {
            for (String Id : ProductAmount.keySet()) {
                synchronized (products.get(Id)) {
                    products.get(Id).purchaseCompleted(ProductAmount.get(Id));

                    //because purchase was unsuccessful we need to add the supply that were subtracted
                    int newSupply = products.get(Id).getSupply() + ProductAmount.get(Id);
                    products.get(Id).editSupply(newSupply);
                }
            }
        }
    }


    @Override
    public float reserve(HashMap<String, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) throws CantPurchaseException,  StorePolicyViolatedException,  SupplyManagementException {

            checksIfStorePoliciesMet( ProductAmount, externalConnectionHolder, userInfo);
            for (String Id : ProductAmount.keySet()) {
                synchronized (products.get(Id)) {
                    if (products.get(Id).getBuyOption().checkIfCanBuy(userInfo.getUserId())) {
                        int newSupply = products.get(Id).getSupply() - ProductAmount.get(Id);
                        products.get(Id).setReservedSupply(ProductAmount.get(Id));
                        products.get(Id).editSupply(newSupply);

                    }
                }
            }
            return calculatePriceWithDiscount(ProductAmount,externalConnectionHolder,userInfo);


    }

    @Override
    public float getPriceOfCartAfterDiscount(HashMap<String, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) throws StorePolicyViolatedException {
        //checksIfStorePoliciesMet( ProductAmount, externalConnectionHolder, userInfo);
        return calculatePriceWithDiscount(ProductAmount,externalConnectionHolder,userInfo);
    }

    @Override
    public float getPriceOfCartBeforeDiscount(HashMap<String, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) throws StorePolicyViolatedException {
        //checksIfStorePoliciesMet( ProductAmount, externalConnectionHolder, userInfo);
        return calculatePriceWithoutDiscount(ProductAmount);
    }

    public Product getProduct(String productId) throws  SupplyManagementException {
        Product pro =  products.get(productId);
        if(pro == null){
            throw new  SupplyManagementException("no product with this "+ productId);
        }
        return pro;

    }
    @Override
    public boolean checkIfProductExist(String productId){
        Product pro =  products.get(productId);
        if(pro == null){
            return false;
        }
        return true;
    }


}
