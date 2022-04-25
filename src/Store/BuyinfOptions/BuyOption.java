package Store.BuyinfOptions;

import Store.Store;

public interface BuyOption {
    Boolean checkIfCanBuy(String userId);
    String getBuyOption();
}
