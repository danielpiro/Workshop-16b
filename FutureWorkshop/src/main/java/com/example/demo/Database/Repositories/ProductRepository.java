package com.example.demo.Database.Repositories;

import com.example.demo.Database.DTOobjects.Store.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductDTO,String> {
    List<ProductDTO> getByBelongsToStore(String BelongsToStore);
}
