package com.example.demo.Mock;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
public class MockCategories {

    @NotNull private List<String> categories;


}
