package com.example.demo.Database.Repositories.Store.Predicate;

import com.example.demo.Database.DTOobjects.Store.Predicates.CompositePredicateDTO;
import com.example.demo.Database.DTOobjects.Store.Predicates.ProductPredicateDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPredicateRepository extends JpaRepository<ProductPredicateDTO,Long> {
}
