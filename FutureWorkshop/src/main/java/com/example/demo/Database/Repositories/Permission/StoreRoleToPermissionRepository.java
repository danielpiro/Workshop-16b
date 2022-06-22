package com.example.demo.Database.Repositories.Permission;

import com.example.demo.Database.DTOobjects.Store.Permissions.StoreRoleToPermissionDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRoleToPermissionRepository extends JpaRepository<StoreRoleToPermissionDTO,Long> {
}
