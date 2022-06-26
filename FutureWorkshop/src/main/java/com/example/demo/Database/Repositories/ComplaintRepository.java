package com.example.demo.Database.Repositories;

import com.example.demo.Database.DTOobjects.History.HistoryDTO;
import com.example.demo.Database.DTOobjects.User.ComplaintDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface ComplaintRepository extends JpaRepository<ComplaintDTO,Integer> {

    List<ComplaintDTO> findByUserID (String userId);

    @Transactional
    @Modifying
    void deleteByUserID (String userID);


}
