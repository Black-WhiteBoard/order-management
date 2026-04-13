package com.fresh.freshmart.entity;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@Document(collation = "patient")
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String patientId;
    private String firstname;
    private String lastname;
    private Integer age;
    @Column(name="medical_record")
    private Record medicalRecord;
}
