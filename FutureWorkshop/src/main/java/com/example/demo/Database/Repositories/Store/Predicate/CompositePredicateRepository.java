package com.example.demo.Database.Repositories.Store.Predicate;

import com.example.demo.Database.DTOobjects.Store.Predicates.CompositePredicateDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompositePredicateRepository extends JpaRepository<CompositePredicateDTO,Long> {
}
