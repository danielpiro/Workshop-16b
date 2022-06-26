package com.example.demo.Database.DTOobjects.Cart;

import com.example.demo.ShoppingCart.InventoryProtector;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;


@AllArgsConstructor
@Setter
@NoArgsConstructor
@Data
@Entity
@Table(name = "shopping_basket")
public class ShoppingBasketDTO {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String StoreId;
    private String userId;
    private String productID;
    private Integer prodAmount;

    public ShoppingBasketDTO(String storeId, String userId, String productID, Integer prodAmount) {
        StoreId = storeId;
        this.userId = userId;
        this.productID = productID;
        this.prodAmount = prodAmount;
    }
}
