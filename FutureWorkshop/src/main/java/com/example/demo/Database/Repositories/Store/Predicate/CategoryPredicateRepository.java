package com.example.demo.Database.Repositories.Store.Predicate;

import com.example.demo.Database.DTOobjects.Store.Predicates.CategoryPredicateDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryPredicateRepository extends JpaRepository<CategoryPredicateDTO,Long> {
}
