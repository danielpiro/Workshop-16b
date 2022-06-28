package com.example.demo.GlobalSystemServices;

import com.example.demo.Database.DTOobjects.GlobalServices.IdGeneratorDTO;
import com.example.demo.Database.Service.DatabaseService;
import com.example.demo.Store.StorePurchase.Discounts.Discount;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class IdGenerator {
    private static DatabaseService database;
    private static IdGenerator single_instance = null;

    private final String productPrefix = "ProductID_";
    private Long productSuffix = 0L;

    private final String storePrefix = "StoreID_";
    private Long storeSuffix= 0L;

    private final String ForumThreadPrefix = "ForumThreadID_";
    private Long ForumThreadSuffix= 0L;

    private final String GuestPrefix = "GuestID_";
    private Long GuestSuffix= 0L;

    private final String AdminPrefix = "AdminID_";
    private Long AdminSuffix= 0L;

    private final String PolicyPrefix = "PolicyID_";
    private Long PolicySuffix= 0L;

    private final String DiscountPrefix = "DiscountID_";
    private Long DiscountSuffix= 0L;
    private final String PredicatePrefix = "PredicateID_";
    private Long PredicateSuffix= 0L;
    private final String PrefixComplaintNotificationId = "ComplaintNotification_";
    private int complaintNotificationId = 0;
    private final String PrefixStoreNotificationId = "storeNotificationNotification_";
    private int storeNotificationId = 0;

    private final String PurchaseIDPrefix = "PurchaseID_";
    private long PurchaseIDSuffix = 0L;


    private IdGenerator(){
        loadData();
    }
    private IdGenerator(DatabaseService databaseService){
        database = databaseService;
        loadData();
        save();
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
    public static IdGenerator addDatabase(DatabaseService databaseService)
    {
        if (database == null)
            single_instance = new IdGenerator(databaseService);
        return single_instance;
    }
    public static IdGenerator getInstance(Long productSuffix, Long storeSuffix, Long ForumThreadSuffix, Long guestSuffix , Long AdminSuffix, Long PolicySuffix, Long discountSuffix,long PurchaseIDSuffix,Long PredicateSuffix)
    {
        if (single_instance == null)
            single_instance = new IdGenerator(productSuffix, storeSuffix, ForumThreadSuffix, guestSuffix, AdminSuffix, PolicySuffix, discountSuffix,PurchaseIDSuffix,PredicateSuffix);
        return single_instance;
    }
    public static IdGenerator getForTestingOnly(Long productSuffix, Long storeSuffix, Long ForumThreadSuffix, Long guestSuffix , Long AdminSuffix, Long PolicySuffix, Long discountSuffix,long PurchaseIDSuffix,Long PredicateSuffix)
    {

        single_instance = new IdGenerator(productSuffix, storeSuffix, ForumThreadSuffix, guestSuffix, AdminSuffix, PolicySuffix, discountSuffix,PurchaseIDSuffix,PredicateSuffix);
        return single_instance;
    }

    public boolean isGuest(String userId) {
        return userId.startsWith(GuestPrefix);
    }

    public String getProductId(){
        productSuffix++;
        save();
        return productPrefix+(productSuffix-1L);
    }


    public String getGuestId(){
        GuestSuffix++;
        save();
        return GuestPrefix+(GuestSuffix-1L);
    }

    public String getStoreId(){
        storeSuffix++;
        save();
        return storePrefix+(storeSuffix-1L);
    }

    public String getForumThreadId(){
        ForumThreadSuffix++;
        save();
        return ForumThreadPrefix+(ForumThreadSuffix-1L);
    }

    public String getAdminId(){
        AdminSuffix++;
        save();
        return AdminPrefix+(AdminSuffix-1L);
    }

    public String getPolicyId(){
        PolicySuffix++;
        save();
        return PolicyPrefix+(PolicySuffix-1L);
    }
    public int getComplaintNotificationId(){
        complaintNotificationId++;
        save();
        return (complaintNotificationId -1);
    }
    public int getStoreNotificationId(){
        storeNotificationId++;
        save();
        return (storeNotificationId -1);
    }
    public String getSessionId(){
        return UUID.randomUUID().toString();
    }
    public String getDiscountId() {
        DiscountSuffix++;
        save();
        return DiscountPrefix+(DiscountSuffix -1);
    }
    public String getPurchaseID() {
        PurchaseIDSuffix++;
        save();
        return PurchaseIDPrefix+(PurchaseIDSuffix -1);
    }
    public String getPredicateID() {
        PredicateSuffix++;
        save();
        return PredicatePrefix+(PredicateSuffix -1);
    }
    public boolean checkIfAdmin(String userId){
        return userId.startsWith("AdminID_");
    }

    public boolean isIdEqual(String Id1, String Id2){
        return Id1.equals(Id2);
    }

    private void save(){
        List<IdGeneratorDTO> idGeneratorDTOList = new ArrayList<>();
        idGeneratorDTOList.add(new IdGeneratorDTO(productPrefix,productSuffix));
        idGeneratorDTOList.add(new IdGeneratorDTO(storePrefix,storeSuffix));
        idGeneratorDTOList.add(new IdGeneratorDTO(ForumThreadPrefix,ForumThreadSuffix));
        idGeneratorDTOList.add(new IdGeneratorDTO(GuestPrefix,GuestSuffix));
        idGeneratorDTOList.add(new IdGeneratorDTO(AdminPrefix,AdminSuffix));
        idGeneratorDTOList.add(new IdGeneratorDTO(PolicyPrefix,PolicySuffix));
        idGeneratorDTOList.add(new IdGeneratorDTO(DiscountPrefix,DiscountSuffix));
        idGeneratorDTOList.add(new IdGeneratorDTO(PredicatePrefix,PredicateSuffix));
        idGeneratorDTOList.add(new IdGeneratorDTO(PrefixComplaintNotificationId,complaintNotificationId));
        idGeneratorDTOList.add(new IdGeneratorDTO(PrefixStoreNotificationId,storeNotificationId));
        idGeneratorDTOList.add(new IdGeneratorDTO(PurchaseIDPrefix,PurchaseIDSuffix));

        database.saveIdGenerator(idGeneratorDTOList);
    }

    public void loadData(){
        List<IdGeneratorDTO> DTOList = database.loadIdGenerator();
        for(IdGeneratorDTO dto: DTOList){
            switch (dto.getIdPrefix()){
                case productPrefix:
                    productSuffix = dto.getSuffix();
                    break;
                case storePrefix:
                    storeSuffix = dto.getSuffix();
                    break;
                case GuestPrefix:
                    GuestSuffix = dto.getSuffix();
                    break;
                case AdminPrefix:
                    AdminSuffix = dto.getSuffix();
                    break;
                case PolicyPrefix:
                    PolicySuffix = dto.getSuffix();
                    break;
                case DiscountPrefix:
                    DiscountSuffix = dto.getSuffix();
                    break;
                case PredicatePrefix:
                    PredicateSuffix = dto.getSuffix();
                    break;
                case PrefixComplaintNotificationId:
                    complaintNotificationId = (int)dto.getSuffix();
                    break;
                case PrefixStoreNotificationId:
                    storeNotificationId = (int)dto.getSuffix();
                    break;
                case PurchaseIDPrefix:
                    PurchaseIDSuffix = dto.getSuffix();
                    break;

            }
        }
    }


    public boolean equals(IdGenerator that) {
        return complaintNotificationId == that.complaintNotificationId &&
                storeNotificationId == that.storeNotificationId &&
                PurchaseIDSuffix == that.PurchaseIDSuffix &&
                productSuffix.equals(that.productSuffix) &&
                storeSuffix.equals(that.storeSuffix) &&
                ForumThreadSuffix.equals(that.ForumThreadSuffix) &&
                GuestSuffix.equals(that.GuestSuffix) &&
                AdminSuffix.equals(that.AdminSuffix) &&
                PolicySuffix.equals(that.PolicySuffix) &&
                DiscountSuffix.equals(that.DiscountSuffix) &&
                PredicateSuffix.equals(that.PredicateSuffix);
    }


}
