package com.example.demo.Database.Repositories;

import com.example.demo.Database.DTOobjects.Store.ReviewDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewDTO,Long> {



    @Transactional
    @Modifying
    //notice the : before the parameter!
    @Query(value = "delete from review where product_id = :id ",nativeQuery = true)
    void deleteByProductId(@Param("id") String ProductId);



    List<ReviewDTO> findByProductId(String ProductId);


    @Transactional
    @Modifying
    void deleteByBody(String body);




    }

//
//    List<SampleTable> getUsersByAge(@Param("paramAge") int age);
//    // TODO: 10/05/2022 now we can use this function without implement her , the query is the implementation
//}

