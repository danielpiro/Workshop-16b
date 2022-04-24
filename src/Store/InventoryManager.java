package Store;

import ExternalConnections.PurchasePolicies;
import GlobalSystemServices.IdGenerator;
import ShoppingCart.InventoryProtector;
import Views.ProductView;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InventoryManager  implements InventoryProtector {
    private HashMap<String, Product> products;
    private List<Discount> discounts;


    public InventoryManager(HashMap<String, Product> products, List<Discount> discounts) {
        this.products = products;
        this.discounts = discounts;
    }
    public InventoryManager() {
        this.products = new HashMap<String, Product>();
        this.discounts = new ArrayList<Discount>();
    }



    public void editProductSupply(String productId, int newSupply, String newName, float newPrice, String category) {
        Product Op = products.get(productId);
        if(Op == null){
            throw new RuntimeException("no product with this id");
        }
        Op.editSupply(newSupply);
        Op.editPrice(newPrice);
        Op.editName(newName);
        Op.setCategory(category);
    }

    public String addNewProduct(String productName, float price, int howMuch, String category){
        Optional<Product> Op = products.values().stream().filter(np-> productName.equals(np.getName())).findAny();
        if(Op.isPresent()){
            throw new RuntimeException("product with this name already exist");
        }
        String newId = IdGenerator.getInstance().getProductId();
        products.put(newId,new Product(newId,productName,price,howMuch,category));
        return newId;
    }


    public List<ProductView> getAllProducts(Predicate<Product> filter) {
        List<ProductView> PV= new ArrayList<>();
        for (Product p :
                products.values()) {
            if(filter.test(p)){
                PV.add(p.getProductView());
            }

        }
        return PV;
    }

    public void addProductReview(String productId, String userId,  String title, String body, float rating) {
        Product Op = products.get(productId);
        if(Op == null){
            throw new RuntimeException("no product with this id");
        }
        Op.addReview(rating, userId, title, body);
    }

    public void deleteProduct(String productId) {
        products.remove(productId);
    }
    public static <T,S> HashMap<T, S> deepCopyWorkAround(HashMap<T, S> original)
    {
        HashMap<T, S> copy = new HashMap<>();
        for (Map.Entry<T, S> entry : original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue() );
        }
        return copy;
    }
    private float calculatePriceWithDiscount(HashMap<String, Integer> ProductIdAmount){
        HashMap<String,Integer> copyProductIdAmount = deepCopyWorkAround(ProductIdAmount);

        float finalPrice = 0f;
        for (Discount d : discounts) {
            HashMap<String, Integer> productsWithTheDeal = d.checkIfDiscountApply(copyProductIdAmount);
            finalPrice += d.applyDiscount(productsWithTheDeal);
            for (String Id : productsWithTheDeal.keySet()){
                copyProductIdAmount.replace(Id, (copyProductIdAmount.get(Id) - productsWithTheDeal.get(Id)) );
            }
        }
        return finalPrice;
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
    public void purchaseSuccessful(HashMap<String, Integer> ProductAmount, boolean success) {
        if(success){
            for (String Id : ProductAmount.keySet()) {
                int newReservedSupply = products.get(Id).getReservedSupply() - ProductAmount.get(Id);
                products.get(Id).setReservedSupply(newReservedSupply);
            }
        }
        else{
            for (String Id : ProductAmount.keySet()) {
                int newReservedSupply = products.get(Id).getReservedSupply() - ProductAmount.get(Id);
                products.get(Id).setReservedSupply(newReservedSupply);

                int newSupply = products.get(Id).getSupply() + ProductAmount.get(Id);
                products.get(Id).editSupply(newSupply);
            }
        }
    }

    @Override
    public float reserve(HashMap<String, Integer> ProductAmount, PurchasePolicies purchasePolicies) {
        for (String Id : ProductAmount.keySet()) {
            int newSupply = products.get(Id).getSupply() - ProductAmount.get(Id);
            products.get(Id).editSupply(newSupply);
            products.get(Id).setReservedSupply(products.get(Id).getSupply());
        }
        return calculatePriceWithDiscount(ProductAmount);
    }
}
