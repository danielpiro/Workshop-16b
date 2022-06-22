package com.example.demo.Database.DTOobjects.Store.Permissions;

import com.example.demo.StorePermission.Permission;
import com.example.demo.StorePermission.StoreRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "store_roles")
public class StoreRoleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="user_id")
    private String userId ;

    @Column(name="store_id")
    private String storeId ;

    private String type;


    public StoreRoleDTO(String userId, String storeId, String type) {
        this.userId = userId;
        this.storeId = storeId;
        this.type = type;
    }
}
