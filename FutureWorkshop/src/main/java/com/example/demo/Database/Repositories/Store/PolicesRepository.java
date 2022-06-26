package com.example.demo.Database.Repositories.Store;

import com.example.demo.Database.DTOobjects.Store.PolicyDTO;
import com.example.demo.Database.DTOobjects.Store.StoreDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolicesRepository extends JpaRepository<PolicyDTO,String> {

    List<PolicyDTO> getByBelongsToStoreId(String storeId);
}
