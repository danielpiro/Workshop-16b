package com.example.demo.Store.BuyinfOptions;

public interface BuyOption {
    Boolean checkIfCanBuy(String userId);
    String getBuyOption();
}
