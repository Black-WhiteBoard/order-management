package com.fresh.freshmart.mapper;

import com.fresh.freshmart.model.Patient;
import com.fresh.freshmart.model.Record;


public class OrderMapper {

    public static Patient mapPatientEntityToPatientModel(com.fresh.freshmart.entity.Patient patient){
        Patient patientModel =new Patient();
                patientModel.setLastname(patient.getLastname());
                patientModel.setAge(patient.getAge());
                Record medicalRecord =new Record();
                medicalRecord.setFileName(patient.getMedicalRecord().getFileName());
                medicalRecord.setData(patient.getMedicalRecord().getFile().getData());
                medicalRecord.setFileType(patient.getMedicalRecord().getFileType());
                patientModel.setMedicalRecord(medicalRecord);
        return  patientModel;
    }
}
