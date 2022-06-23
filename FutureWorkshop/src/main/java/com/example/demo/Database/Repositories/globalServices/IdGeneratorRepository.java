package com.example.demo.Database.Repositories.globalServices;

import com.example.demo.Database.DTOobjects.Cart.ShoppingBasketDTO;
import com.example.demo.Database.DTOobjects.GlobalServices.IdGeneratorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface IdGeneratorRepository extends JpaRepository<IdGeneratorDTO,String> {
}
