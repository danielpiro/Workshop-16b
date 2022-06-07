package com.example.demo.Mock;

import com.example.demo.StorePermission.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
public class MockSmallPermission {

    @NotNull
    private String storeId;

    @NotNull
    private String userIdGiving;

    @NotNull
    private String UserGettingPermissionId;

    @NotNull
    private List<Permission> permissions;

}
