package com.example.demo.Database.DTOobjects.Store.Predicates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product_predicates")
public class ProductPredicateDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long productIdForeignKey;
    private Integer num;
    private String predicateProductType;

    public ProductPredicateDTO(long productIdForeignKey, Integer num, String predicateProductType) {
        this.productIdForeignKey = productIdForeignKey;
        this.num = num;
        this.predicateProductType = predicateProductType;
    }

    public ProductPredicateDTO(long productIdForeignKey, String predicateProductType) {
        this.productIdForeignKey = productIdForeignKey;
        this.predicateProductType = predicateProductType;
    }
}