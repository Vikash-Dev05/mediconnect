package com.mediconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediconnect.entity.Patient;
import com.mediconnect.repository.PatientRepository;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // 1. API to create a new patient (CREATE)
    @PostMapping("/add")
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    // 2. API to get all patients (READ)
    @GetMapping("/all")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // 3. API to update a patient by ID (UPDATE)
    @PutMapping("/update/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        
        // Pehle database se purana patient dhoondo
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

        // Nayi details ko purane par set karo
        existingPatient.setName(patientDetails.getName());
        existingPatient.setAge(patientDetails.getAge());
        existingPatient.setGender(patientDetails.getGender());

        // Updated patient ko save kar do
        return patientRepository.save(existingPatient);
    }

    // 4. API to delete a patient by ID (DELETE)
    @DeleteMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        
        // Pehle check karo ki woh ID hai ya nahi
        patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        
        // Agar mil gaya, toh delete karo
        patientRepository.deleteById(id);
        
        return "Patient deleted successfully with id: " + id;
    }
}