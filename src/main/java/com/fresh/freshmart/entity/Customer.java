package com.fresh.freshmart.entity;

import lombok.Data;


import java.io.Serializable;
import java.util.List;


@Data
public class Customer implements Serializable {

    private Integer customerId;
    private String firstname;
    private String lastname;
    private Integer age;
   private List<Address> address;
}
