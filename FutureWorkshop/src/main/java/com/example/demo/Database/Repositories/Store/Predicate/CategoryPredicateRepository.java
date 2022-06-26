package com.example.demo.Database.Repositories.Store.Predicate;

import com.example.demo.Database.DTOobjects.Store.Predicates.CategoryPredicateDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryPredicateRepository extends JpaRepository<CategoryPredicateDTO,Long> {
    List<CategoryPredicateDTO> getByBelongsToPredicateId(Long BelongsTo);
}
