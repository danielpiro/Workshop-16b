package DomainLayer.User;

import DomainLayer.User.Subscriber;

public class ProductReview {

    String review_data = "";
    int rank;
    Subscriber subscriber;


    public ProductReview(Subscriber subscriber,int rank,String review_data)    {
        this.review_data = review_data;
        this.rank = rank;
        this.subscriber = subscriber;
    }

    public String getReview_data() {
        return review_data;
    }

    public int getRank() {
        return rank;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }
}
