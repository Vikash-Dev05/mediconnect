package com.mediconnect.repository;

import com.mediconnect.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {


    List<Doctor> findByCategory_Name(String categoryName);
    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);
    List<Doctor> findBySpecializationContainingIgnoreCaseAndIsVerifiedTrue(String specialization);
    List<Doctor> findByIsVerifiedTrue();
    List<Doctor> findByIsVerifiedFalse();
    
    java.util.Optional<Doctor> findById(Long id);
    Doctor findByUser_Id(Long userId);
}