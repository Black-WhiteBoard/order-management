package com.fresh.freshmart.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.io.Serializable;

@Data
//@Entity
@Document(collation = "order")
public class Order implements Serializable {
 public Order(){}
   @JsonProperty
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Id
    private Integer id;
   @JsonProperty
    @Column(name = "name")
    private  String name;
   @JsonProperty
    @Column(name = "category")
    private String category;

    @JsonProperty
    @Column(name = "quantity")
    private String  price;

}
