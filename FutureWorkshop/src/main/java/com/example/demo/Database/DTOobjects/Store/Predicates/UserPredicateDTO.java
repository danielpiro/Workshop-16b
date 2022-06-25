package com.example.demo.Database.DTOobjects.Store.Predicates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_predicates")
public class UserPredicateDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long predicateId;

    private String userId;

    public UserPredicateDTO(long predicateId, String userId) {
        this.predicateId = predicateId;
        this.userId = userId;
    }
}
