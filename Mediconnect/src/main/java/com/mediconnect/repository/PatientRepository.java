package com.mediconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediconnect.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Bas! Abhi ke liye itna hi.
}