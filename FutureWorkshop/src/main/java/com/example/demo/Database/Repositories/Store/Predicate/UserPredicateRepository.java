package com.example.demo.Database.Repositories.Store.Predicate;

import com.example.demo.Database.DTOobjects.Store.Predicates.CompositePredicateDTO;
import com.example.demo.Database.DTOobjects.Store.Predicates.UserPredicateDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPredicateRepository extends JpaRepository<UserPredicateDTO,Long> {
    List<UserPredicateDTO> getByPredicateId(Long PredicateId);
}
