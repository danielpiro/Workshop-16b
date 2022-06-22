package com.example.demo.Database.Repositories.Store.Permission;

import com.example.demo.Database.DTOobjects.Store.Permissions.StoreRoleToStoreRoleDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRoleToStoreRoleRepository extends JpaRepository<StoreRoleToStoreRoleDTO,Long> {
    Optional<StoreRoleToStoreRoleDTO> findByGettingPermissionIdAndGivingPermissionId(long getting,long giving);
}
