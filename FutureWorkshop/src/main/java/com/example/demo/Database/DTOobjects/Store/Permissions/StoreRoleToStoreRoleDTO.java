package com.example.demo.Database.DTOobjects.Store.Permissions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "StoreRole_To_StoreRole")
public class StoreRoleToStoreRoleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "giving_Permission_Id")
    private long givingPermissionId;
    @Column(name = "getting_Permission_Id")
    private long gettingPermissionId;
}
