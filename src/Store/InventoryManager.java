package Store;

import ExternalConnections.PurchasePolicies;
import GlobalSystemServices.IdGenerator;
import ShoppingCart.InventoryProtector;
import Views.ProductView;

import java.util.*;
import java.util.function.Predicate;

public class InventoryManager  implements InventoryProtector {
    private HashMap<String, Product> products;



    public InventoryManager(HashMap<String, Product> products) {
        this.products = products;
    }
    public InventoryManager() {
        this.products = new HashMap<String, Product>();
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

    }

    @Override
    public float reserve(HashMap<String, Integer> ProductAmount, PurchasePolicies purchasePolicies) {
        float sumPrice = 0;
        for (Product p :
                products.values()) {


        }
        return 0L;
    }
}
