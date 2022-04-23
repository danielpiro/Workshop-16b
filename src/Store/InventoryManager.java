package Store;

import GlobalSystemServices.IdGenerator;
import StorePermissin.User;
import Views.ProductView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryManager {
    private List<Product> products;

    public InventoryManager(List<Product> products) {
        this.products = products;
    }
    public InventoryManager() {
        this.products = new ArrayList<Product>();
    }

    public void addToExistingProduct(String productId, int howMach) {
        Optional<Product> Op = products.stream().filter(np-> IdGenerator.getInstance().isIdEqual(np.getId() , productId)).findAny();
        if(Op.isEmpty()){
            throw new RuntimeException("no product with this id");
        }
        Op.get().addToSupply(howMach);
    }

    public void addNewProduct(String productName, float price, int howMuch){
        Optional<Product> Op = products.stream().filter(np-> productName.equals(np.getName())).findAny();
        if(Op.isPresent()){
            throw new RuntimeException("product with this name already exist");
        }
        products.add(new Product(IdGenerator.getInstance().getProductId(),productName,price,howMuch));
    }


    public List<ProductView> getAllProducts() {
        List<ProductView> PV= new ArrayList<>();
        for (Product p :
                products) {
            PV.add(p.getProductView());
        }
        return PV;
    }
}
