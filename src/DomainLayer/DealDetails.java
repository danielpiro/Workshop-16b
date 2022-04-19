package DomainLayer;

public class DealDetails {
    private double price;
    private String buyer_name;
    private String creditCardNumber;
    private String expireDate;
    private int cvv;


    public DealDetails(double price, String buyer_name, String creditCardNumber, String expireDate, int cvv) {
        this.price = price;
        this.buyer_name = buyer_name;
        this.creditCardNumber = creditCardNumber;
        this.expireDate = expireDate;
        this.cvv = cvv;
    }

    public double getPrice() {
        return price;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public int getCvv() {
        return cvv;
    }
}
