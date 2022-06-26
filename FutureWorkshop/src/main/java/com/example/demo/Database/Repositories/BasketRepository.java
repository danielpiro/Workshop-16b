package com.example.demo.Database.Repositories;

import com.example.demo.Database.DTOobjects.Cart.ShoppingBasketDTO;
import com.example.demo.Database.DTOobjects.Store.ReviewDTO;
import com.example.demo.ShoppingCart.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BasketRepository extends JpaRepository<ShoppingBasketDTO,Long> {


    List<ShoppingBasketDTO> findByUserId(String userId);


    @Transactional
    @Modifying
    void deleteByUserId(String userId);



}
