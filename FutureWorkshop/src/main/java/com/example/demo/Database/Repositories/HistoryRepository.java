package com.example.demo.Database.Repositories;

import com.example.demo.Database.DTOobjects.History.HistoryDTO;
import com.example.demo.Database.DTOobjects.Store.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryDTO,Long> {



    List<HistoryDTO> findByUserID (String userId);
    List<HistoryDTO> findByStoreID (String storeId);

}
