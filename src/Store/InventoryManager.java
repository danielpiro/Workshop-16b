package Store;

import GlobalSystemServices.IdGenerator;
import Views.ProductView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class InventoryManager {
    private HashMap<String, Product> products;

    public InventoryManager(HashMap<String, Product> products) {
        this.products = products;
    }
    public InventoryManager() {
        this.products = new HashMap<String, Product>();
    }



    public void addToExistingProduct(String productId, int howMach) {
        Product Op = products.get(productId);
        if(Op == null){
            throw new RuntimeException("no product with this id");
        }
        Op.addToSupply(howMach);
    }

    public String addNewProduct(String productName, float price, int howMuch){
        Optional<Product> Op = products.values().stream().filter(np-> productName.equals(np.getName())).findAny();
        if(Op.isPresent()){
            throw new RuntimeException("product with this name already exist");
        }
        String newId = IdGenerator.getInstance().getProductId();
        products.put(newId,new Product(newId,productName,price,howMuch));
        return newId;
    }


    public List<ProductView> getAllProducts() {
        List<ProductView> PV= new ArrayList<>();
        for (Product p :
                products.values()) {
            PV.add(p.getProductView());
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
}
