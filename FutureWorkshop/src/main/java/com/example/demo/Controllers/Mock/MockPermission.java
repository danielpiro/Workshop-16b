package com.example.demo.Controllers.Mock;

import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.List;

public class MockPermission {

    @NotNull
    String storeId;

    @NotNull
    String userIdRemoving;

    @NotNull
    String UserAffectedId;

    @NotNull
    List<String> PerToRemove;

    public MockPermission(@NotNull String storeId, @NotNull String userIdRemoving, @NotNull String userAffectedId, @NotNull List<String> perToRemove) {
        this.storeId = storeId;
        this.userIdRemoving = userIdRemoving;
        UserAffectedId = userAffectedId;
        PerToRemove = perToRemove;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getUserIdRemoving() {
        return userIdRemoving;
    }

    public String getUserAffectedId() {
        return UserAffectedId;
    }

    public List<String> getPerToRemove() {
        return PerToRemove;
    }
}
