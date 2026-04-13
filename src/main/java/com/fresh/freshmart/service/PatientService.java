package com.fresh.freshmart.service;

import com.fresh.freshmart.entity.Patient;
import com.fresh.freshmart.mapper.OrderMapper;
import com.fresh.freshmart.model.Record;
import com.fresh.freshmart.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    private OrderMapper mapper;

    public Patient createPatient(Patient patient){
        return  patientRepository.save(patient);
    }

    public com.fresh.freshmart.entity.Record getPatientMedicalRecord(String id) {
         Optional<Patient> patient= patientRepository.findById(id);
        com.fresh.freshmart.entity.Record record =null;
          if (patient.isPresent()) {
               record = patient.get().getMedicalRecord();
          }
          return  record;
    }

    public com.fresh.freshmart.model.Patient getPatient(String id) {
        Optional<Patient> patient= patientRepository.findById(id);
        com.fresh.freshmart.model.Patient patientModel=null;
        if(patient.isPresent()){
            patientModel =OrderMapper.mapPatientEntityToPatientModel(patient.get());
        }
        else{
            log.error("Patient Record not found with id :{}",id);
            throw  new RuntimeException("record not found");
        }
        return  patientModel;
    }

}
