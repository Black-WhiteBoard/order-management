package com.fresh.freshmart.model;

import lombok.Data;
import org.bson.types.Binary;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class Patient implements Serializable {

    private String patientId;
    private String firstname;
    private String lastname;
    private Integer age;
    private Record medicalRecord;
}
