package Store;

import StorePermissin.User;

import java.util.List;
import java.util.Optional;

public class InventoryManager {
    private List<Product> products;


    public void addToExistingProduct(int productId, int howMach) {
        Optional<Product> Op = products.stream().filter(np->np.getId() == productId).findAny();
        if(Op.isEmpty()){
            throw new RuntimeException("no product with this id");
        }
        Op.get().addToSupply(howMach);
    }
}
