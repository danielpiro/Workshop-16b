package GlobalSystemServices;

public class IdGenerator {

    private static IdGenerator single_instance = null;

    private String productPrefix = "ProductID_";
    private Long productSuffix;

    private String storePrefix = "StoreID_";
    private Long storeSuffix;

    private String ForumThreadPrefix = "ForumThreadID_";
    private Long ForumThreadSuffix;

    private String GuestPrefix = "GuestID_";
    private Long GuestSuffix;

    private IdGenerator(){
        productSuffix = 0L;
        storeSuffix = 0L;
        ForumThreadSuffix = 0L;
        GuestSuffix = 0L;
    }

    private IdGenerator(Long productSuffix, Long storeSuffix, Long ForumThreadSuffix, Long guestSuffix) {
        this.productSuffix = productSuffix;
        this.storeSuffix = storeSuffix;
        this.ForumThreadSuffix = ForumThreadSuffix;
        this.GuestSuffix = guestSuffix;
    }

    public static IdGenerator getInstance()
    {
        if (single_instance == null)
            single_instance = new IdGenerator();
        return single_instance;
    }
    public static IdGenerator getInstance(Long productSuffix, Long storeSuffix, Long ForumThreadSuffix, Long guestSuffix)
    {
        if (single_instance == null)
            single_instance = new IdGenerator(productSuffix, storeSuffix, ForumThreadSuffix, guestSuffix);
        return single_instance;
    }

    public String getProductId(){
        productSuffix++;
        return productPrefix+(productSuffix-1L);
    }


    public String getGuestId(){
        GuestSuffix++;
        return GuestPrefix+(GuestSuffix-1L);
    }

    public String getStoreId(){
        storeSuffix++;
        return storePrefix+(storeSuffix-1L);
    }

    public String getForumThreadId(){
        ForumThreadSuffix++;
        return ForumThreadPrefix+(ForumThreadSuffix-1L);
    }

    public boolean isIdEqual(String Id1, String Id2){
        return Id1.equals(Id2);
    }







}
