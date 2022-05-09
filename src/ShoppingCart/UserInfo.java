package ShoppingCart;

public class UserInfo {
    private int age;
    private String userId;
    public UserInfo(int age, String userId) {
        this.age = age;
        this.userId = userId;
    }

    public int getAge() {
        return age;
    }

    public String getUserId() {
        return userId;
    }
}
