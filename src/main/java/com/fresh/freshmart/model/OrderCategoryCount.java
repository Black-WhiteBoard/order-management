package com.fresh.freshmart.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderCategoryCount {
    private String category;
    private Integer count;
}
