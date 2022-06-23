package com.example.demo.Database.Repositories.Store.Permission;

import com.example.demo.Database.DTOobjects.Store.Permissions.StoreRoleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface StoreRoleRepository extends JpaRepository<StoreRoleDTO,Long> {


  @Transactional
  @Modifying
  @Query
  Optional<StoreRoleDTO> findByUserId(String userId);
  @Transactional
  @Modifying
  @Query
  Optional<StoreRoleDTO> findById(String Id);

  @Transactional
  @Modifying
  @Query
  List<StoreRoleDTO> getByStoreId(String Id);

  Optional<StoreRoleDTO> findByUserIdAndStoreId(String userId, String storeId);




}
