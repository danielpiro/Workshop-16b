package ServiceLayer;

import DomainLayer.Product;

import java.util.List;

public interface IGuest {

    public boolean sign_up(String user_name, String password);
    public boolean login(String user_name, String password);
    public List<Product> view_products_information_store (String store_name);
    public List<Product> search_products(String product_name);
    public boolean save_products(int id,String product_name, String store_name);
    public List<String> watch_products_in_cart(int id);
    public boolean buy_products_in_cart(int id,String buyerName,String creditCardNumber,String expireDate,int cvv,double discount);   //2.8 //7

}
