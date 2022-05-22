package com.example.demo.Mock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MockSmallProduct {

    @NotNull private String user_id;
    @NotNull private String productID;
    @NotNull private String storeID;
    @NotNull private Integer amount;


}
