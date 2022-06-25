package com.example.demo.Database.DTOobjects.Store.Predicates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "category_predicates")
public class CategoryPredicateDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long belongsToPredicateId;

    private String category;

    public CategoryPredicateDTO(Long belongsToPredicateId, String category) {
        this.belongsToPredicateId = belongsToPredicateId;
        this.category = category;
    }
}
