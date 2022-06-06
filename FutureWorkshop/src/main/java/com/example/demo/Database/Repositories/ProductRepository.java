package com.example.demo.Database.Repositories;

import com.example.demo.Database.DTOobjects.Store.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductDTO,String> {
}
