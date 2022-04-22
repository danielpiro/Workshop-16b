package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class Proxy implements BridgeInterface {
    private BridgeInterface real;

    private ArrayList<User> users;
    private ArrayList<Store> stores;
    private User currentUserInSystem;


    public Proxy() {
        this.real = null;
        users = new ArrayList<>();
        stores = new ArrayList<>();
        currentUserInSystem = null;
    }
//    public Proxy(BridgeInterface real) {
//        this.real = real;
//        users = new ArrayList<>();
//        stores = new ArrayList<>();
//        currentUserInSystem = null;
//    }

    public BridgeInterface getReal() {
        return real;
    }

//    public void setReal(BridgeInterface real) {
//        this.real = real;
//    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public User getCurrentUserInSystem() {
        return currentUserInSystem;
    }

    public void setCurrentUserInSystem(User currentUserInSystem) {
        this.currentUserInSystem = currentUserInSystem;
    }

    public void uploadUsersAndStores(){
        User user1 = new User("user1", "11111", User.permission.SystemFounder);
        User user2 = new User("user2", "22222", User.permission.SystemManager);
        User user3 = new User("user3", "33333", User.permission.ShopOwner);
        User user4 = new User("user4", "44444", User.permission.ShopManager);
        User user5 = new User("user5", "55555", User.permission.Registered);
        User user6 = new User("user6", "66666", User.permission.Buyer);
        User user7 = new User("user7", "77777", User.permission.Visitor);
        users.add(user1); users.add(user2); users.add(user3); users.add(user4);
        users.add(user5); users.add(user6); users.add(user7);

        Product p0 = new Product("p0", 0);
        Product p1 = new Product("p1", 1);
        Product p2 = new Product("p2", 2);
        Product p3 = new Product("p3", 3);
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(p0,0); products.put(p1, 5); products.put(p2, 15); products.put(p3, 25);
        Store store1 = new Store("store1", products);
        stores.add(store1);
        user1.setStoreOwnedByMe("store1"); user2.setStoreOwnedByMe("store1"); user3.setStoreOwnedByMe("store1");
        user4.setStoreManagedByMe("store1");
        HashMap<String, User.permission> officials = new HashMap<>();
        officials.put(user1.getUsername(), user1.getPermissionLevel());
        officials.put(user2.getUsername(), user2.getPermissionLevel());
        officials.put(user3.getUsername(), user3.getPermissionLevel());
        officials.put(user4.getUsername(), user4.getPermissionLevel());
        store1.setStoreOfficials(officials);
    }

    public void uploadUsersAndStores_FailTest1(){
        users.add(new User("user1", "11111", User.permission.SystemManager));
        users.add(new User("user2", "22222", User.permission.SystemManager));
        users.add(new User("user3", "33333", User.permission.ShopOwner));
        users.add(new User("user4", "44444", User.permission.ShopManager));
        users.add(new User("user5", "55555", User.permission.Registered));
        users.add(new User("user6", "66666", User.permission.Buyer));
        users.add(new User("user7", "77777", User.permission.Visitor));

        Product p1 = new Product("p1", 1);
        Product p2 = new Product("p2", 2);
        Product p3 = new Product("p3", 3);
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(p1, 5); products.put(p2, 15); products.put(p3, 25);
        stores.add(new Store("store1", products));
    }

    public void uploadUsersAndStores_FailTest2(){
        users.add(new User("user1", "11111", User.permission.SystemFounder));
        users.add(new User("user2", "22222", User.permission.SystemManager));
        users.add(new User("user3", "33333", User.permission.ShopOwner));
        users.add(new User("user4", "44444", User.permission.ShopManager));
        users.add(new User("user5", "55555", User.permission.Registered));
        users.add(new User("user6", "66666", User.permission.Buyer));

        Product p1 = new Product("p1", 1);
        Product p2 = new Product("p2", 2);
        Product p3 = new Product("p3", 3);
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(p1, 5); products.put(p2, 15); products.put(p3, 25);
        stores.add(new Store("store1", products));
    }

    /** requirement 1.b in V1 */
    public String parallelUse() { //Need to implement thread-base system
        if(real!=null)
            return real.parallelUse();
        throw new UnsupportedOperationException("Not Implemented Yet - Ask Backend Group");
    }

    /** requirement 1.c in V1 */
    public String systemLogging() { //Need to create log file (containing error logs)
        if(real!=null)
            return real.parallelUse();
        throw new UnsupportedOperationException("Not Implemented Yet - Ask Backend Group");
    }

    /** System requirement - I.1 */
    public String openingMarket(){
        if(real!=null)
            return real.openingMarket();

        if(users.isEmpty() && stores.isEmpty()) // just for tests
            uploadUsersAndStores();

        boolean extServices = true; //TODO
        if(!extServices)
            return "fail - connection with external services has failed";

        if(users.size() == 7 && stores.size() == 1){
            for (User u : users){
                if(u.getPermissionLevel() == User.permission.SystemFounder){
                    return "system opened successfully";
                }
            }
            return "fail - system doesn't have a founder";
        }
        return "fail - system didn't upload all the users or stores";
    }

    /** System requirement - I.2 */
    public String changeExternalService(int currServiceCode, String currServiceName,
                                 int newServiceCode, String newServiceName){
        //Valid ServiceCode includes only positive numbers
        //Valid ServiceName includes only letters
        if(real!=null)
            return real.changeExternalService(currServiceCode, currServiceName, newServiceCode, newServiceName);

        if(newServiceCode <= 0 || !newServiceName.matches("[a-zA-Z]+"))
            return "fail - invalid service code or name";
        else { //TODO: Make a proper service change
            currServiceCode = newServiceCode;
            currServiceName = newServiceName;
            return "change services done successfully";
        }
    }

    /** System requirement - I.2 */
    public String switchExternalService(int currServiceCode, String currServiceName,
                                 int newServiceCode, String newServiceName){
        //Valid ServiceCode includes only positive numbers
        //Valid ServiceName includes only letters
        if(real!=null)
            return real.switchExternalService(currServiceCode, currServiceName, newServiceCode, newServiceName);

        if(newServiceCode <= 0 || !newServiceName.matches("[a-zA-Z]+"))
            return "fail - invalid service code or name";
        else { //TODO: Make a proper service switch
            currServiceCode = newServiceCode;
            currServiceName = newServiceName;
            return "switch services done successfully";
        }
    }

    /** System requirement - I.2 */
    public String addExternalService(int serviceCode, String serviceName){
        //Valid ServiceCode includes only positive numbers
        //Valid ServiceName includes only letters
        if(real!=null)
            return real.addExternalService(serviceCode, serviceName);

        if(serviceCode <= 0 || !serviceName.matches("[a-zA-Z]+"))
            return "fail - invalid service code or name";
        else //TODO: Make a proper service adding
            return "adding services done successfully";
    }

    /** System requirement - I.3 */
    public String payment(){
        if(real!=null)
            return real.payment();

        //TODO: Make sure there is a payment service in the system
        return "Payment done successfully";
    }

    /** System requirement - I.4 */
    public String delivery(){
        if(real!=null)
            return real.delivery();

        //TODO: Make sure there is a delivery service in the system
        return "Delivery done successfully";
    }

    /** System requirement - I.5 */
    public String realtimeNotificationProductBought(){
        if(real!=null)
            return real.realtimeNotificationProductBought();

        //TODO: Make sure there is a realtime notifications in the system
        return "TODO!";
    }

    /** System requirement - I.5 */
    public String realtimeNotificationStoreClosed(){
        if(real!=null)
            return real.realtimeNotificationStoreClosed();

        //TODO: Make sure there is a realtime notifications in the system
        return "TODO!";
    }

    /** System requirement - I.5 */
    public String realtimeNotificationStoreReopened(){
        if(real!=null)
            return real.realtimeNotificationStoreReopened();

        //TODO: Make sure there is a realtime notifications in the system
        return "TODO!";
    }

    /** System requirement - I.5 */
    public String realtimeNotificationUserPermissionUpdate(){
        if(real!=null)
            return real.realtimeNotificationUserPermissionUpdate();

        //TODO: Make sure there is a realtime notifications in the system
        return "TODO!";
    }

    /** System requirement - I.6 */
    public String offlineNotificationProductBought(){
        if(real!=null)
            return real.offlineNotificationProductBought();

        //TODO: Make sure there is a offline notifications in the system
        return "TODO!";
    }

    /** System requirement - I.6 */
    public String offlineNotificationStoreClosed(){
        if(real!=null)
            return real.offlineNotificationStoreClosed();

        //TODO: Make sure there is a offline notifications in the system
        return "TODO!";
    }

    /** System requirement - I.6 */
    public String offlineNotificationStoreReopened(){
        if(real!=null)
            return real.offlineNotificationStoreReopened();

        //TODO: Make sure there is a offline notifications in the system
        return "TODO!";
    }

    /** System requirement - I.6 */
    public String offlineNotificationUserPermissionUpdate(){
        if(real!=null)
            return real.offlineNotificationUserPermissionUpdate();

        //TODO: Make sure there is a offline notifications in the system
        return "TODO!";
    }

    /** User requirement - II.1.1 */
    public String getInToTheSystem(){
        if(real!=null)
            return real.getInToTheSystem();

        if(currentUserInSystem.getPermissionLevel() != User.permission.Visitor)
            return "fail - the user that got in is not a visitor";
        //after get-in
        currentUserInSystem.setPermissionLevel(User.permission.Buyer);
        if(currentUserInSystem.getPermissionLevel() == User.permission.Buyer &&
            currentUserInSystem.getShoppingCart() != null){
            return "user got-in successfully";
        }
        return "fail - user failed to get-in";
    }

    /** User requirement - II.1.2 */
    public String getOutFromTheSystem(){
        if(real!=null)
            return real.getOutFromTheSystem();

        if(currentUserInSystem.getPermissionLevel() == User.permission.Visitor)
            return "fail - the user already got out of the system (as buyer)";

        currentUserInSystem.setPermissionLevel(User.permission.Visitor);
        currentUserInSystem.setShoppingCart(null);
        if(currentUserInSystem.getShoppingCart() != null)
            return "fail - empty user's shopping cart failed";
        if(currentUserInSystem.getPermissionLevel() != User.permission.Visitor)
            return "fail - user's permission didn't change to visitor";

        return "user got out successfully";
    }
    public String getOutFromTheSystem_FailTest2(){
        if(real!=null)
            return real.getOutFromTheSystem();

        if(currentUserInSystem.getPermissionLevel() == User.permission.Visitor)
            return "fail - the user already got out of the system (as buyer)";

        currentUserInSystem.setPermissionLevel(User.permission.Visitor);
        if(currentUserInSystem.getShoppingCart() != null)
            return "fail - empty user's shopping cart failed";
        if(currentUserInSystem.getPermissionLevel() != User.permission.Visitor)
            return "fail - user's permission didn't change to visitor";

        return "user got out successfully";
    }
    public String getOutFromTheSystem_FailTest3(){
        if(real!=null)
            return real.getOutFromTheSystem();

        if(currentUserInSystem.getPermissionLevel() == User.permission.Visitor)
            return "fail - the user already got out of the system (as buyer)";

        currentUserInSystem.setShoppingCart(null);
        if(currentUserInSystem.getShoppingCart() != null)
            return "fail - empty user's shopping cart failed";
        if(currentUserInSystem.getPermissionLevel() != User.permission.Visitor)
            return "fail - user's permission didn't change to visitor";

        return "user got out successfully";
    }

    /** User requirement - II.1.3 */
    public String register(String username, String password){
        //username has to contain at least 3 characters
        //password has to contain at least 4 characters
        if(real!=null)
            return real.register(username, password);

        for(User u : users){
            if(u.getUsername().equals(username)){
                return "fail - this username already exists in the system";
            }
        }
        if(username.length() < 3)
            return "fail - invalid username";
        if(password.length() < 4)
            return "fail - invalid password";

        users.add(new User(username, password, User.permission.Visitor));
        return "the user registered successfully";
    }

    /** User requirement - II.1.4 */
    public String login(String username, String password){
        //username has to contain at least 3 characters
        //password has to contain at least 4 characters
        if(real!=null)
            return real.login(username, password);

        for(User u : users){
            if(u.getUsername().equals(username)){
                if(u.getPassword().equals(password)){
                    currentUserInSystem = u;
                    currentUserInSystem.setIsLoggedIn(true);
                    currentUserInSystem.setPermissionLevel(u.getPermissionLevel());
                    return "user logged in successfully";
                }
                else{
                    return "fail - wrong password";
                }
            }
        }
        return "fail - wrong username / no such username in the system";
    }

    /** User requirement - II.2.1 */
    public String receiveSystemInfo(){
        if(real!=null)
            return real.receiveSystemInfo();

        String str = "";
        str += "Users:\n";
        for (User u : users){
            str = str.concat(u.getUsername() + "\n");
        }
        str += "\nStores:\n";
        for (Store s : stores){
            str = str.concat(s.getStoreName() + "\n");
        }
        return str;
    }

    /** User requirement - II.2.2 */
    public String searchProduct(String productName){
        if(real!=null)
            return real.searchProduct(productName);

        int productCount = 0;
        for (Store s : stores){
            for(Product p : s.getProducts().keySet()){
                if(p.getProductName().equals(productName) && s.getProducts().get(p) > 0){
                    productCount++;
                }
            }
        }
        if(productCount > 0)
            return "showing all the products that were searched by the user";
        else
            return "there's no such product in the system (in any store)";
    }

    /** User requirement - II.2.3 */
    public String saveProductFromStoreToShoppingCart(String storeName, String productName){
        if(real!=null)
            return real.saveProductFromStoreToShoppingCart(storeName, productName);

        if(currentUserInSystem.getPermissionLevel() == User.permission.Visitor)
            return "fail - visitor user can't use shopping cart (need to be at least buyer)";
        for (Store s : stores){
             if(s.getStoreName().equals(storeName)){
                 for (Product p : s.getProducts().keySet()){
                     if(p.getProductName().equals(productName) && s.getProducts().get(p) > 0){
                         HashMap<String, Integer> shoppingCart = currentUserInSystem.getShoppingCart();
                         shoppingCart.put(p.getProductName(), 1);
                         currentUserInSystem.setShoppingCart(shoppingCart);
                         return "product saved to shopping cart successfully";
                     }
                 }
                 return "there's no such product in the store / there's no quantity left to this product";
             }
        }
        return "fail - wrong store name";
    }

    /** User requirement - II.2.4 */
    public String showShoppingCart(){
        if(real!=null)
            return real.showShoppingCart();

        if(currentUserInSystem.getShoppingCart() == null)
            return "fail - shopping cart can't be displayed";

        //currentUserInSystem.getShoppingCart();
        return "showing user's shopping cart";
    }
    /** User requirement - II.2.4 */
    //inc. by 1!
    public String increaseProductQuantityInShoppingCart(String productName){
        if(real!=null)
            return real.increaseProductQuantityInShoppingCart(productName);

        if(currentUserInSystem.getPermissionLevel() == User.permission.Visitor)
            return "fail - visitor user can't use shopping cart (need to be at least buyer)";
        HashMap<String, Integer> shoppingCart = currentUserInSystem.getShoppingCart();
        for (String pName : shoppingCart.keySet()){
            if(pName.equals(productName)){
                int quantity = shoppingCart.get(productName) + 1;
                shoppingCart.put(productName, quantity);
                currentUserInSystem.setShoppingCart(shoppingCart);
                return "the product quantity increased successfully";
            }
        }
        return "failed to increase product quantity";
    }
    /** User requirement - II.2.4 */
    //dec. by 1!
    public String decreaseProductQuantityInShoppingCart(String productName){
        if(real!=null)
            return real.decreaseProductQuantityInShoppingCart(productName);

        if(currentUserInSystem.getPermissionLevel() == User.permission.Visitor)
            return "fail - visitor user can't use shopping cart (need to be at least buyer)";
        HashMap<String, Integer> shoppingCart = currentUserInSystem.getShoppingCart();
        for (String pName : shoppingCart.keySet()){
            if(pName.equals(productName)){
                int quantity = shoppingCart.get(productName) - 1;
                if(quantity < 0)
                    quantity = 0;
                shoppingCart.put(productName, quantity);
                currentUserInSystem.setShoppingCart(shoppingCart);
                return "the product quantity decreased successfully";
            }
        }
        return "failed to decrease product quantity";
    }
    /** User requirement - II.2.4 */
    public String removeProductFromShoppingCart(String productName){
        if(real!=null)
            return real.removeProductFromShoppingCart(productName);

        if(currentUserInSystem.getPermissionLevel() == User.permission.Visitor)
            return "fail - visitor user can't use shopping cart (need to be at least buyer)";
        HashMap<String, Integer> shoppingCart = currentUserInSystem.getShoppingCart();
        for (String pName : shoppingCart.keySet()){
            if(pName.equals(productName)){
                shoppingCart.remove(productName);
                currentUserInSystem.setShoppingCart(shoppingCart);
                return "the product removed successfully";
            }
        }
        return "failed to remove product quantity";
    }

    /** User requirement - II.2.5 */
    public String purchaseShoppingCart(){
        if(real!=null)
            return real.purchaseShoppingCart();

        //TODO: Need to add external services...
        return "TODO!";
    }

    /** User requirement - II.3.1 */
    public String logout(){
        if(real!=null)
            return real.logout();

        if(currentUserInSystem == null || !currentUserInSystem.getIsLoggedIn()){
            return "fail - this user isn't logged in";
        }

        currentUserInSystem.setIsLoggedIn(false);
        //TODO: Need to save the registered user's shopping cart
        currentUserInSystem.setShoppingCart(new HashMap<>());
        //currentUserInSystem.setPermissionLevel(User.permission.Visitor);
        return "logout done successfully";
    }

    /** User requirement - II.3.2 */
    public String openStore(String storeName, HashMap<Product, Integer> products){
        //storeName has to have at least 5 characters
        if(real!=null)
            return real.openStore(storeName, products);

        if(storeName.length() < 5){
            return "fail - store name is not valid";
        }
        if(currentUserInSystem.getPermissionLevel() == User.permission.Visitor ||
            currentUserInSystem.getPermissionLevel() == User.permission.Buyer ||
                !currentUserInSystem.getIsLoggedIn()){
            return "fail - registration & login are required in order to open a store";
        }
        for(Store s : stores){
            if(s.getStoreName().equals(storeName)){
                return "fail - this store name is already taken";
            }
        }
        stores.add(new Store(storeName, products));
        currentUserInSystem.setStoreOwnedByMe(storeName);
        if(currentUserInSystem.getPermissionLevel() == User.permission.Registered ||
                currentUserInSystem.getPermissionLevel() == User.permission.ShopManager) {
            currentUserInSystem.setPermissionLevel(User.permission.ShopOwner);
        }
        return "store was opened successfully";
    }

    /** User requirement - II.4.1 */
    public String addProductToStore(String storeName, String productName, int productPrice, int productQuantity){
        //productName has to have at least 2 characters
        if(real!=null)
            return real.addProductToStore(storeName, productName, productPrice, productQuantity);

        if(productName.length() < 2 || productPrice <= 0 || productQuantity < 0){
            return "fail - product name/price/quantity is not valid";
        }
        if((currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
        currentUserInSystem.getPermissionLevel() != User.permission.SystemManager &&
        currentUserInSystem.getPermissionLevel() != User.permission.SystemFounder)
                || !currentUserInSystem.getIsLoggedIn()){
            return "fail - user has to be at least shop owner and to be logged in";
        }
        if(currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                !currentUserInSystem.getStoreOwnedByMe().equals(storeName)){
            return "fail - the user is not owner on that store";
        }
        for(Store s : stores){
            if(s.getStoreName().equals(storeName)){
                for(Product p : s.getProducts().keySet()){
                    if(p.getProductName().equals(productName)){
                        return "this exact same product is already in the store";
                    }
                }
                HashMap<Product, Integer> prods = s.getProducts();
                prods.put(new Product(productName, productPrice), productQuantity);
                s.setProducts(prods);
                return "product was added to the store successfully";
            }
        }
        return "fail - should never happen!";
    }
    /** User requirement - II.4.1 */
    public String removeProductFromStore(String storeName, String productName){
        if(real!=null)
            return real.removeProductFromStore(storeName, productName);

        if((currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemManager &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemFounder)
                || !currentUserInSystem.getIsLoggedIn()){
            return "fail - user has to be at least shop owner and to be logged in";
        }
        if(currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                !currentUserInSystem.getStoreOwnedByMe().equals(storeName)){
            return "fail - the user is not owner on that store";
        }
        for(Store s : stores){
            if(s.getStoreName().equals(storeName)){
                for(Product p : s.getProducts().keySet()){
                    if(p.getProductName().equals(productName)){
                        HashMap<Product, Integer> prods = s.getProducts();
                        prods.remove(p);
                        s.setProducts(prods);
                        return "product was removed from the store successfully";
                    }
                }
            }
        }
        return "failed to remove product (check storeName or productName)";
    }
    /** User requirement - II.4.1 */
    public String editProductInStore(String storeName, String productName, String newProductName,
                                     int newProductPrice, int newProductQuantity){
        if(real!=null)
            return real.editProductInStore(storeName, productName, newProductName, newProductPrice, newProductQuantity);

        if(newProductName.length() < 2 || newProductPrice <= 0 || newProductQuantity < 0){
            return "fail - new product name/price/quantity is not valid";
        }
        if((currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemManager &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemFounder)
                || !currentUserInSystem.getIsLoggedIn()){
            return "fail - user has to be at least shop owner and to be logged in";
        }
        if(currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                !currentUserInSystem.getStoreOwnedByMe().equals(storeName)){
            return "fail - the user is not owner on that store";
        }
        for(Store s : stores){
            if(s.getStoreName().equals(storeName)){
                for(Product p : s.getProducts().keySet()){
                    if(p.getProductName().equals(productName)){
                        HashMap<Product, Integer> prods = s.getProducts();
                        prods.remove(p);
                        prods.put(new Product(newProductName, newProductPrice), newProductQuantity);
                        s.setProducts(prods);
                        return "product was edited in the store successfully";
                    }
                }
            }
        }
        return "failed to edit product (check storeName or productName)";
    }

    /** User requirement - II.4.2 */
    public String changeStorePolicy(String storeName, String newStorePolicy){
        //policy should contain at least 10 characters;
        if(real!=null)
            return real.changeStorePolicy(storeName, newStorePolicy);

        if(newStorePolicy.length() < 10){
            return "fail - new policy is not valid";
        }
        if((currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemManager &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemFounder)
                || !currentUserInSystem.getIsLoggedIn()){
            return "fail - user has to be at least shop owner and to be logged in";
        }
        if(currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                !currentUserInSystem.getStoreOwnedByMe().equals(storeName)){
            return "fail - the user is not owner on that store (Check store name)";
        }
        for(Store s : stores){
            if(s.getStoreName().equals(storeName)){
                s.setPolicy(newStorePolicy);
                return "changed policy successfully";
            }
        }
        return "failed to change policy (Check store name)";
    }

    /** User requirement - II.4.4 */
    public String addNewStoreOwner(String storeName, String newStoreOwnerUserName){
        if(real!=null)
            return real.addNewStoreOwner(storeName, newStoreOwnerUserName);

        if((currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemManager &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemFounder)
                || !currentUserInSystem.getIsLoggedIn()){
            return "fail - user has to be at least shop owner and to be logged in";
        }
        if(currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                !currentUserInSystem.getStoreOwnedByMe().equals(storeName)){
            return "fail - the user is not owner on that store (Check store name)";
        }
        Store store = null;
        User user = null;
        for (Store s : stores){
            if(s.getStoreName().equals(storeName))
                store = s;
        }
        for (User u : users){
            if(u.getUsername().equals(newStoreOwnerUserName))
                user = u;
        }
        if(store == null || user == null)
            return "fail - store name or username is invalid";
        else if (user.getStoreOwnedByMe()!=null || user.getStoreManagedByMe()!=null)
            return "fail - this user is already managing/owning a store in the system";
        else{
            user.setPermissionLevel(User.permission.ShopOwner);
            user.setStoreOwnedByMe(storeName);
            HashMap<String, User.permission> officials = store.getStoreOfficials();
            officials.put(user.getUsername(), user.getPermissionLevel());
            store.setStoreOfficials(officials);
        }
        return "the user is now store owner";
    }

    /** User requirement - II.4.6 */
    public String addNewStoreManager(String storeName, String newStoreManagerUserName){
        if(real!=null)
            return real.addNewStoreManager(storeName, newStoreManagerUserName);

        if((currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemManager &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemFounder)
                || !currentUserInSystem.getIsLoggedIn()){
            return "fail - user has to be at least shop owner and to be logged in";
        }
        if(currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                !currentUserInSystem.getStoreOwnedByMe().equals(storeName)){
            return "fail - the user is not owner on that store (Check store name)";
        }
        Store store = null;
        User user = null;
        for (Store s : stores){
            if(s.getStoreName().equals(storeName))
                store = s;
        }
        for (User u : users){
            if(u.getUsername().equals(newStoreManagerUserName))
                user = u;
        }
        if(store == null || user == null)
            return "fail - store name or username is invalid";
        else if (user.getStoreOwnedByMe()!=null || user.getStoreManagedByMe()!=null)
            return "fail - this user is already managing/owning a store in the system";
        else{
            user.setPermissionLevel(User.permission.ShopManager);
            user.setStoreOwnedByMe(storeName);
            HashMap<String, User.permission> officials = store.getStoreOfficials();
            officials.put(user.getUsername(), user.getPermissionLevel());
            store.setStoreOfficials(officials);
        }
        return "the user is now store manager";
    }

    /** User requirement - II.4.7 */
    public String changeStoreManagerPermissions(String storeName, String storeManagerUserName, User.permission newPermission){
        if(real!=null)
            return real.changeStoreManagerPermissions(storeName, storeManagerUserName, newPermission);

        if((currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemManager &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemFounder)
                || !currentUserInSystem.getIsLoggedIn()){
            return "fail - user has to be at least shop owner and to be logged in";
        }
        if(currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                !currentUserInSystem.getStoreOwnedByMe().equals(storeName)){
            return "fail - the user is not owner on that store (Check store name)";
        }
        for (Store s : stores){
            if(s.getStoreName().equals(storeName)){
                for(String uName : s.getStoreOfficials().keySet()){
                    if(uName.equals(storeManagerUserName)){
                        for (User u : users){
                            if(u.getUsername().equals(storeManagerUserName)){
                                u.setPermissionLevel(newPermission);
                                if(newPermission == User.permission.ShopOwner)
                                    u.setStoreOwnedByMe(storeName);
                                else
                                    u.setStoreManagedByMe(storeName);
                                HashMap<String, User.permission> officials = s.getStoreOfficials();
                                officials.put(u.getUsername(), u.getPermissionLevel());
                                return "store manager permission has changed successfully";
                            }
                        }
                    }
                }
                return "fail - this user is not a manager in that store";
            }
        }
        return "fail - there's no such store in the system";
    }

    /** User requirement - II.4.9 */
    public String closeStoreByOwner(String storeName){
        if(real!=null)
            return real.closeStoreByOwner(storeName);

        if((currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemManager &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemFounder)
                || !currentUserInSystem.getIsLoggedIn()){
            return "fail - user has to be at least shop owner and to be logged in";
        }
        if(currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                !currentUserInSystem.getStoreOwnedByMe().equals(storeName)){
            return "fail - the user is not owner on that store (Check store name)";
        }
        for (Store s : stores) {
            if (s.getStoreName().equals(storeName)) {
                if(!s.isOpen())
                    return "the store was already closed";
                s.setOpen(false);
                return "the store has closed successfully";
            }
        }
        return "failed to close store (Check store name)";
    }

    /** User requirement - II.4.11 */
    public String showStoreOfficials(String storeName){
        if(real!=null)
            return real.showStoreOfficials(storeName);

        if((currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemManager &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemFounder)
                || !currentUserInSystem.getIsLoggedIn()){
            return "fail - user has to be at least shop owner and to be logged in";
        }
        if(currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                !currentUserInSystem.getStoreOwnedByMe().equals(storeName)){
            return "fail - the user is not owner on that store (Check store name)";
        }
        for (Store s : stores) {
            if (s.getStoreName().equals(storeName)) {
                return "showing all the officials...";
            }
        }
        return "failed to show the store's officials";
    }

    /** User requirement - II.4.13 */
    public String showStorePurchaseHistory(String storeName){
        if(real!=null)
            return real.showStorePurchaseHistory(storeName);

        if((currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemManager &&
                currentUserInSystem.getPermissionLevel() != User.permission.SystemFounder)
                || !currentUserInSystem.getIsLoggedIn()){
            return "fail - user has to be at least shop owner and to be logged in";
        }
        if(currentUserInSystem.getPermissionLevel() != User.permission.ShopOwner &&
                !currentUserInSystem.getStoreOwnedByMe().equals(storeName)){
            return "fail - the user is not owner on that store (Check store name)";
        }
        for (Store s : stores) {
            if (s.getStoreName().equals(storeName)) {
                return "showing all the purchase history...";
            }
        }
        return "failed to show the store's purchase history";
    }
}
