package com.example.demo.Database.DTOobjects.Store.Predicates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "composite_predicates")
public class CompositePredicateDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstType;
    private Long predicateIdFirst;
    private String secondType;
    private Long predicateIdSecond;

    private String predicateType;


    public CompositePredicateDTO(String firstType, Long predicateIdFirst, String secondType, Long predicateIdSecond, String predicateType) {
        this.firstType = firstType;
        this.predicateIdFirst = predicateIdFirst;
        this.secondType = secondType;
        this.predicateIdSecond = predicateIdSecond;
        this.predicateType = predicateType;
    }
}
