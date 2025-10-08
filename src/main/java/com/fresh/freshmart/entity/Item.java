package com.fresh.freshmart.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Item implements Serializable {
 public Item(){}
   @JsonProperty
    private String id;
   @JsonProperty
    private  String name;
   @JsonProperty
    private String catagory;
    @JsonProperty
    private String  quantity;

}
