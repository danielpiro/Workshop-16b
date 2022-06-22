package com.example.demo.Database.DTOobjects.Store.Permissions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Store_Role_To_Permission")
public class StoreRoleToPermissionDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "store_role_id")
    private long storeRoleId;
    @Column(name = "permission_id")
    private String PermissionId;

    public StoreRoleToPermissionDTO(long storeRoleId, String permissionId) {
        this.storeRoleId = storeRoleId;
        PermissionId = permissionId;
    }
}
