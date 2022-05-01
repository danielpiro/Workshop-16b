package Store.BuyinfOptions;

public class ImmediateBuy implements BuyOption{
    @Override
    public Boolean checkIfCanBuy(String userId) {
        return true;
    }

    @Override
    public String getBuyOption() {
        return this.getClass().getSimpleName();
    }
}
