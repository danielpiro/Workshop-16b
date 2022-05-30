package com.example.demo.Mock;

import com.example.demo.Store.BuyinfOptions.BuyOption;
import com.example.demo.Store.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MockFullProduct {

    @NotNull private String storeId;
    @NotNull private String userId;
    @NotNull private String productName;
    @NotNull private Float price;
    @NotNull private Integer supply;
    @NotNull private String category;



}
