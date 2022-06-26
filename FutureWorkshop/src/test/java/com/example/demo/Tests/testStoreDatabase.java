package com.example.demo.Tests;

import com.example.demo.Controllers.BigController;
import com.example.demo.CustomExceptions.Exception.NotifyException;
import com.example.demo.CustomExceptions.Exception.ResourceNotFoundException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.CustomExceptions.Exception.UserException;
import com.example.demo.Database.DTOobjects.Store.Predicates.AllPredicateDTO;
import com.example.demo.Database.DTOobjects.Store.Predicates.PredicatesTypes;
import com.example.demo.Database.Service.DatabaseService;
import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.Product;
import com.example.demo.Store.StorePurchase.Policies.Policy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@SpringBootTest
public class testStoreDatabase {
    @Autowired
    DatabaseService databaseService;
    BigController bigController;
//    @BeforeAll
//    void setup() throws SupplyManagementException, SQLException, NoPermissionException, IOException, UserException, ResourceNotFoundException, InterruptedException, NotifyException {
//        bigController = new BigController(databaseService);
//    }
    @Test
    void bla(){
        AllPredicateDTO categoryPred = new AllPredicateDTO(PredicatesTypes.CategoryPredicate.toString());
        databaseService.saveCategoryPredicateDTOPolicy("test",categoryPred, new ArrayList<>(), new Policy() {
            @Override
            public boolean checkIfPolicyStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
                return false;
            }
        });
        AllPredicateDTO cartPredicateDTO= new AllPredicateDTO(PredicatesTypes.CartPredicate.toString(),Integer.parseInt("2"));
        AllPredicateDTO cartPredicateDTO2= new AllPredicateDTO(PredicatesTypes.CartPredicate.toString(),Integer.parseInt("2"));
        AllPredicateDTO cartPredicateDTO3= new AllPredicateDTO(PredicatesTypes.CartPredicate.toString(),Integer.parseInt("2"));
        //databaseService.saveCategoryPredicateDTOPolicy(new AllPredicateDTO(),);
        System.out.println(cartPredicateDTO.getId());

    }

}
