package com.example.demo.Database.Repositories.Store.Predicate;

import com.example.demo.Database.DTOobjects.Store.Predicates.AllPredicateDTO;
import com.example.demo.Database.DTOobjects.Store.StoreDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface AllPredicateRepository extends JpaRepository<AllPredicateDTO,Long> {
    Optional<AllPredicateDTO> findByCartNumOfProductsAndPredicateType(int numOfProducts,String predicateType);

    Optional<AllPredicateDTO> findByPredicateType(String predicateType);

}
