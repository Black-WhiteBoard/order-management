package com.fresh.freshmart.repository;

import com.fresh.freshmart.entity.Order;
import com.fresh.freshmart.model.OrderCategoryCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order,Integer> {

    public void findByCategory(String category);

}
