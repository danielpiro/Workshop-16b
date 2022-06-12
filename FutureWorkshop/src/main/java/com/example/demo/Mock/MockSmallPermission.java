package com.example.demo.Mock;

import com.example.demo.StorePermission.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class MockSmallPermission {

    @NotNull
    private String storeId;

    @NotNull
    private String userIdGiving;

    @NotNull
    private String UserGettingPermissionId;

    @NotNull
    private List<String> permissions;

}
