//package com.example.demo;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//// TODO: 10/05/2022 here the left is the class that would be in the DB and in the right is the id that we use to identify it
//public interface RepositoryExample extends JpaRepository<SampleTable , Integer> {
//    // TODO: 10/05/2022 here we can add custom query this is an example
//    @Query(value = "select * from users where users.age:=paramAge",nativeQuery = true)
//    List<SampleTable> getUsersByAge(@Param("paramAge") int age);
//    // TODO: 10/05/2022 now we can use this function without implement her , the query is the implementation
//}
