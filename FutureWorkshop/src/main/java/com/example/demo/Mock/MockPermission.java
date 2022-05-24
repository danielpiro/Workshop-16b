package com.example.demo.Mock;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
public class MockPermission {

    @NotNull String storeId;
    @NotNull String userIdRemoving;
    @NotNull String UserAffectedId;
    @NotNull List<String> PerToRemove;

}
