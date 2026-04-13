package com.fresh.freshmart.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Record implements Serializable {
    private String fileName;
    private String fileType;
    private byte[] data;
}
