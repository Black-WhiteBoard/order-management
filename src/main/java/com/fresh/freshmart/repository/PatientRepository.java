package com.fresh.freshmart.repository;

import com.fresh.freshmart.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository  extends MongoRepository<Patient,String> {
}
