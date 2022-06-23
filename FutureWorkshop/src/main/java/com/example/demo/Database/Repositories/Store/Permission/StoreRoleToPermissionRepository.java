package com.example.demo.Database.Repositories.Store.Permission;

import com.example.demo.Database.DTOobjects.Store.Permissions.StoreRoleToPermissionDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRoleToPermissionRepository extends JpaRepository<StoreRoleToPermissionDTO,Long> {
    Optional<StoreRoleToPermissionDTO> findByPermissionIdAndStoreRoleId(String permissionId, Long storeRoleId);
    List<StoreRoleToPermissionDTO> getByStoreRoleId(Long storeRoleId);

}
