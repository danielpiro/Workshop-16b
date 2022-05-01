package Store;

public class Review {
    private float rating;
    private String UserId;
    private String Title;
    private String Body;




    public Review(float rating, String userId) {
        editRating(rating);
        UserId = userId;
        Title = "";
        Body = "";
    }

    public Review(float rating, String userId, String title, String body) {
        editRating(rating);
        UserId = userId;
        Title = title;
        Body = body;
    }

    public void editTitle(String title){
        Title = title;
    }

    public String getTitle() {
        return Title;
    }

    public void editBody(String body) {
        Body = body;
    }

    public String getBody() {
        return Body;
    }
    public void editRating(float rating){
        if (rating>=1  || rating<=5){
            this.rating=rating;
        }
        else
            throw new IllegalArgumentException("rating is between 1 and 5");
    }
    public float getRating() {
        return rating;
    }

    public String getUserId() {
        return UserId;
    }



}
