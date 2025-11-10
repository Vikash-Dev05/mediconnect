package com.mediconnect.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mediconnect.entity.Category;
import com.mediconnect.entity.Doctor;
import com.mediconnect.repository.CategoryRepository;
import com.mediconnect.repository.DoctorRepository;

// DTO mein naye fields add kiye
class DoctorRequest {
    public String name;
    public String qualification;
    public Long categoryId;
    public String timingFrom; // Naya Field
    public String timingTo;   // Naya Field
}

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired private DoctorRepository doctorRepository;
    @Autowired private CategoryRepository categoryRepository;

    @PostMapping("/add")
    public Doctor addDoctor(@RequestBody DoctorRequest request) {
        Category category = categoryRepository.findById(request.categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        Doctor doctor = new Doctor();
        doctor.setName(request.name);
        doctor.setQualification(request.qualification);
        doctor.setCategory(category);
        // Timings set karna
        doctor.setTimingFrom(request.timingFrom);
        doctor.setTimingTo(request.timingTo);
        
        return doctorRepository.save(doctor);
    }

    @GetMapping("/all")
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @PutMapping("/update/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody DoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        Category category = categoryRepository.findById(request.categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        doctor.setName(request.name);
        doctor.setQualification(request.qualification);
        doctor.setCategory(category);
        // Timings update karna
        doctor.setTimingFrom(request.timingFrom);
        doctor.setTimingTo(request.timingTo);
        
        return doctorRepository.save(doctor);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorRepository.deleteById(id);
        return "Doctor deleted with id: " + id;
    }
}