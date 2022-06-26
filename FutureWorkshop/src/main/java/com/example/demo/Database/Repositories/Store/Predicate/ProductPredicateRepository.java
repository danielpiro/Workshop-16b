package com.example.demo.Database.Repositories.Store.Predicate;

import com.example.demo.Database.DTOobjects.Store.Predicates.CompositePredicateDTO;
import com.example.demo.Database.DTOobjects.Store.Predicates.ProductPredicateDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPredicateRepository extends JpaRepository<ProductPredicateDTO,Long> {
List<ProductPredicateDTO> getByProductIdForeignKey(long productId);
}
