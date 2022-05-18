package com.example.demo.Mock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class MockProductReview {

    @NotNull String storeId;
    @NotNull String userId;
    @NotNull String productId;
    @NotNull String Title;
    @NotNull String Body;
    @NotNull float rating;
}
