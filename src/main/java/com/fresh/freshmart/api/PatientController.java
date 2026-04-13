package com.fresh.freshmart.api;

import com.fresh.freshmart.entity.Patient;
import  com.fresh.freshmart.entity.Record;
import com.fresh.freshmart.expceptionhandler.RecordNotFoundException;
import com.fresh.freshmart.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestParam("age") Integer age,@RequestParam("name") String name,@RequestParam("medical_record") MultipartFile medicalRecord ){
        Patient patient =new Patient();
         patient.setAge(age);
         patient.setLastname(name);
        Record record =new Record();
        record.setFileName(medicalRecord.getOriginalFilename());
        record.setFileType(medicalRecord.getContentType());

        try {
            record.setFile(new Binary(BsonBinarySubType.BINARY,medicalRecord.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        patient.setMedicalRecord(record);
        Patient patient1 =  patientService.createPatient(patient);
       return  ResponseEntity.ok(patient);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<com.fresh.freshmart.model.Patient> getPatient(@PathVariable("id") String id){
        com.fresh.freshmart.model.Patient patientRecord =  patientService.getPatient(id);
        return ResponseEntity.ok().body(patientRecord);
    }

    @GetMapping("/id/{id}/medical_record")
    public ResponseEntity getPatientMedicalRecord(@PathVariable("id") String id){
        Record patientRecord =  patientService.getPatientMedicalRecord(id);
        Resource resource =null;
        HttpHeaders headers =new HttpHeaders();
                 if (patientRecord !=null) {
                     resource = new ByteArrayResource(patientRecord.getFile().getData(), patientRecord.getFileName());
                     headers.setContentType(MediaType.parseMediaType(patientRecord.getFileType()));
                     headers.setContentDisposition(ContentDisposition.builder("inline").filename(patientRecord.getFileName()).build());
                     return ResponseEntity.ok().headers(headers).body(resource);
                 }
                 else {
                     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RecordNotFoundException("record not found"));
                 }
    }

}
