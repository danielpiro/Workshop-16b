package ServiceLayer;

import DomainLayer.Product;
import DomainLayer.PurchaseProcess;

import java.util.List;

public interface ISubscriber {

    public List<Product> view_products_information_store (String store_name); //2.4
    public List<Product> search_products(String product_name);  //2.5 (Right now, search via name only).

    public boolean save_products(String userName,String product_name, String store_name); //2.6
    public List<String> watch_products_in_cart(String userName);  //2.7.1
    //remove_product_from cart                //2.7.2
    public boolean buy_products_in_cart(String id,String buyerName,String creditCardNumber,String expireDate,int cvv,double discount);   //2.8 //7

    public boolean sign_out(String user_name);  //3.1
    public boolean open_store(String user_name, String store_name);
    public boolean write_review(String user_name, String product_name, String store_name, String review_data, int rank); //3.3
    public boolean rank_product(); //3.4
    public boolean rank_store();   //3.4
    public boolean send_query_to_store(String user_name,String Query);//3.5
    public boolean fill_complaint();      //3.6
    public List<PurchaseProcess> view_purchase_history(String user_name);  //3.7    //changed the return boolean type to List<PurchaseProcess> and the input from none to string user_name
    public boolean edit_account();     //3.8


}
