package com.example.demo.GlobalSystemServices;

public class IdGenerator {

    private static IdGenerator single_instance = null;


    private String productPrefix = "ProductID_";
    private Long productSuffix = 0L;

    private String storePrefix = "StoreID_";
    private Long storeSuffix= 0L;

    private String ForumThreadPrefix = "ForumThreadID_";
    private Long ForumThreadSuffix= 0L;

    private String GuestPrefix = "GuestID_";
    private Long GuestSuffix= 0L;

    private String AdminPrefix = "AdminID_";
    private Long AdminSuffix= 0L;

    private String PolicyPrefix = "PolicyID_";
    private Long PolicySuffix= 0L;

    private String DiscountPrefix = "DiscountID_";
    private Long DiscountSuffix= 0L;
    private String PredicatePrefix = "PredicateID_";
    private Long PredicateSuffix= 0L;
    private int complaintNotificationId = 0;;
    private int storeNotificationId = 0;

    private String PurchaseIDPrefix = "PurchaseID_";
    private long PurchaseIDSuffix = 0L;


    private IdGenerator(){

    }

    private IdGenerator(Long productSuffix, Long storeSuffix, Long ForumThreadSuffix, Long guestSuffix, Long AdminSuffix, Long PolicySuffix, Long discountSuffix,long PurchaseIDSuffix, Long PredicateSuffix) {
        this.productSuffix = productSuffix;
        this.storeSuffix = storeSuffix;
        this.ForumThreadSuffix = ForumThreadSuffix;
        this.GuestSuffix = guestSuffix;
        this.AdminSuffix = AdminSuffix;
        this.PolicySuffix = PolicySuffix;
        this.DiscountSuffix = discountSuffix;
        this.PurchaseIDSuffix = PurchaseIDSuffix;
        this.PredicateSuffix = PredicateSuffix;
    }

    public static IdGenerator getInstance()
    {
        if (single_instance == null)
            single_instance = new IdGenerator();
        return single_instance;
    }
    public static IdGenerator getInstance(Long productSuffix, Long storeSuffix, Long ForumThreadSuffix, Long guestSuffix , Long AdminSuffix, Long PolicySuffix, Long discountSuffix,long PurchaseIDSuffix,Long PredicateSuffix)
    {
        if (single_instance == null)
            single_instance = new IdGenerator(productSuffix, storeSuffix, ForumThreadSuffix, guestSuffix, AdminSuffix, PolicySuffix, discountSuffix,PurchaseIDSuffix,PredicateSuffix);
        return single_instance;
    }

    public boolean isGuest(String userId) {
        return userId.startsWith(GuestPrefix);
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
    public int getComplaintNotificationId(){
        complaintNotificationId++;
        return (complaintNotificationId -1);
    }
    public int getStoreNotificationId(){
        storeNotificationId++;
        return (storeNotificationId -1);
    }
    public String getDiscountId() {
        DiscountSuffix++;
        return DiscountPrefix+(DiscountSuffix -1);
    }
    public String getPurchaseID() {
        PurchaseIDSuffix++;
        return PurchaseIDPrefix+(PurchaseIDSuffix -1);
    }
    public String getPredicateID() {
        PredicateSuffix++;
        return PredicatePrefix+(PredicateSuffix -1);
    }
    public boolean checkIfAdmin(String userId){
        return userId.startsWith("AdminID_");
    }

    public boolean isIdEqual(String Id1, String Id2){
        return Id1.equals(Id2);
    }



}
