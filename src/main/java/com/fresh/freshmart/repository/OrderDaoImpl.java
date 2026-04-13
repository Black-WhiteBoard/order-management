package com.fresh.freshmart.repository;

import com.fresh.freshmart.model.OrderCategoryCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderRepoBase {
@Autowired
private MongoTemplate mongoTemplate;


    public List<OrderCategoryCount> noOfOrdersOfCategories() {

      GroupOperation groupOperation = Aggregation.group("category").count().as("count");
        ProjectionOperation project =Aggregation.project("category");
      Aggregation aggregation =Aggregation.newAggregation(project,groupOperation);
      AggregationResults<OrderCategoryCount> results = mongoTemplate.aggregate(aggregation,"order",OrderCategoryCount.class);
      return  results.getMappedResults();
    }
}
