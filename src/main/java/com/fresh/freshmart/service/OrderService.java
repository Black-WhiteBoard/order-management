package com.fresh.freshmart.service;

import com.fresh.freshmart.model.OrderCategoryCount;
import com.fresh.freshmart.repository.OrderRepoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepoBase orderDao;

    public List<OrderCategoryCount> getOrderCategoryDetails(){
        return  orderDao.noOfOrdersOfCategories();
    }
}
