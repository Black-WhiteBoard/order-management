package com.fresh.freshmart.repository;

import com.fresh.freshmart.model.OrderCategoryCount;

import java.util.List;

public interface OrderRepoBase{
    public List<OrderCategoryCount> noOfOrdersOfCategories();
}
