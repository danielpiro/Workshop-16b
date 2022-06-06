package com.example.demo.Database.DTOobjects.Store;

import com.example.demo.Store.BuyinfOptions.BuyOption;
import com.example.demo.Store.ProductsCategories;
import com.example.demo.Store.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product")
public class ProductDTO {

    @Id
    private String id;
    private String name;
    private float price;
    private float rating;//star system changing according to reviews


    private int supply = 0;

    //todo changed to int!
    private int buyOption;
    @Column(name="reserved_supply")
    private int reservedSupply = 0;

    private ProductsCategories category;
}
