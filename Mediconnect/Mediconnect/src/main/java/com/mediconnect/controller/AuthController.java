package com.mediconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.mediconnect.entity.User;
import com.mediconnect.repository.UserRepository;

import com.mediconnect.entity.Patient;
import com.mediconnect.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import com.mediconnect.entity.Doctor;
import com.mediconnect.repository.DoctorRepository;
import com.mediconnect.repository.CategoryRepository;
import com.mediconnect.entity.Category;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String UPLOAD_DIR = "uploads/degrees/";


    @PostMapping("/register")
    @ResponseBody
    public String registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Error: Username already taken!";
        }
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("PATIENT");
        }

        user.setPassword("{noop}" + user.getPassword());
        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/register-patient")
    public String registerPatient(@RequestParam String fullName,
                                  @RequestParam String email,
                                  @RequestParam String phoneNumber,
                                  @RequestParam String password,
                                  @RequestParam int age,
                                  @RequestParam String gender) {

        if (userRepository.findByEmailOrPhoneNumber(email, phoneNumber).isPresent()) {
            return "redirect:/signup?error=User+already+exists";
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword("{noop}" + password);
        user.setRole("PATIENT");
        userRepository.save(user);

        Patient patient = new Patient();
        patient.setName(fullName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setUser(user);
        patientRepository.save(patient);

        return "redirect:/login?registered=true";
    }

    @PostMapping("/register-doctor")
    public String registerDoctor(@RequestParam String fullName,
                                 @RequestParam String email,
                                 @RequestParam String phoneNumber,
                                 @RequestParam String password,
                                 @RequestParam String qualification,
                                 @RequestParam String specialization,
                                 @RequestParam Long categoryId,
                                 @RequestParam("degreeFile") MultipartFile degreeFile) {

        if (userRepository.findByEmailOrPhoneNumber(email, phoneNumber).isPresent()) {
            return "redirect:/signup?error=User+already+exists";
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword("{noop}" + password);
        user.setRole("DOCTOR");
        userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setName(fullName);
        doctor.setQualification(qualification);
        doctor.setSpecialization(specialization);
        doctor.setVerified(false); // Initially false, needs admin approval
        doctor.setUser(user);

        // Set Category
        categoryRepository.findById(categoryId).ifPresent(doctor::setCategory);

        // Handle File Upload
        if (!degreeFile.isEmpty()) {
            try {
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String fileName = UUID.randomUUID().toString() + "_" + degreeFile.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.write(path, degreeFile.getBytes());
                doctor.setDegreePath("/" + UPLOAD_DIR + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/signup?error=File+upload+failed";
            }
        }

        doctorRepository.save(doctor);

        return "redirect:/login?registered=true&pending=true";
    }

    // NAYA API: Current User ka Role pata karne ke liye
    @GetMapping("/current-role")
    @ResponseBody
    public String getCurrentUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().findFirst().isPresent()) {
            return auth.getAuthorities().stream().findFirst().get().getAuthority();
        }
        return "UNKNOWN";
    }
}