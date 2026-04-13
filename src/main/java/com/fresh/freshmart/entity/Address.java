package com.fresh.freshmart.entity;

import lombok.Data;


import java.io.Serializable;

@Data
public class Address implements Serializable {
    private Integer id;
    private String city;
    private String state;
    private String zipcode;
    private String type;
    private String address1;
    private String address2;
}
