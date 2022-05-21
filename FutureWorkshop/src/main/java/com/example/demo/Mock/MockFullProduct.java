package com.example.demo.Mock;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class MockFullProduct {

    @NotNull private String storeId;
    @NotNull private String userId;
    @NotNull private String productName;
    @NotNull private Float price;
    @NotNull private Integer supply;
    @NotNull private String category;

}
