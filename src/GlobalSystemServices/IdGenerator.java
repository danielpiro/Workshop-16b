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

    private String AdminPrefix = "AdminID_";
    private Long AdminSuffix;

    private String PolicyPrefix = "PolicyID_";
    private Long PolicySuffix;

    private IdGenerator(){
        productSuffix = 0L;
        storeSuffix = 0L;
        ForumThreadSuffix = 0L;
        GuestSuffix = 0L;
        AdminSuffix = 0L;
        PolicySuffix = 0L;
    }

    private IdGenerator(Long productSuffix, Long storeSuffix, Long ForumThreadSuffix, Long guestSuffix, Long AdminSuffix, Long PolicySuffix) {
        this.productSuffix = productSuffix;
        this.storeSuffix = storeSuffix;
        this.ForumThreadSuffix = ForumThreadSuffix;
        this.GuestSuffix = guestSuffix;
        this.AdminSuffix = AdminSuffix;
        this.PolicySuffix = PolicySuffix;
    }

    public static IdGenerator getInstance()
    {
        if (single_instance == null)
            single_instance = new IdGenerator();
        return single_instance;
    }
    public static IdGenerator getInstance(Long productSuffix, Long storeSuffix, Long ForumThreadSuffix, Long guestSuffix , Long AdminSuffix, Long PolicySuffix)
    {
        if (single_instance == null)
            single_instance = new IdGenerator(productSuffix, storeSuffix, ForumThreadSuffix, guestSuffix, AdminSuffix, PolicySuffix);
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

    public String getAdminId(){
        AdminSuffix++;
        return AdminPrefix+(AdminSuffix-1L);
    }

    public String getPolicyId(){
        PolicySuffix++;
        return PolicyPrefix+(PolicySuffix-1L);
    }

    public boolean checkIfAdmin(String userId){
        return userId.startsWith("AdminID_");
    }

    public boolean isIdEqual(String Id1, String Id2){
        return Id1.equals(Id2);
    }







}
