package com.fresh.freshmart.entity;

import lombok.Data;
import org.bson.types.Binary;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class Record implements Serializable {
    private String fileName;
    private String fileType;
    private Binary file;
}
