package com.mediconnect.service;

import com.mediconnect.entity.Doctor;
import com.mediconnect.entity.User;
import com.mediconnect.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private com.mediconnect.repository.UserRepository userRepository;

    // Doctor ki profile save karne ke liye
    public void saveDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    // Saare doctors ki list lane ke liye (Patient jab page kholega - ONLY VERIFIED)
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findByIsVerifiedTrue();
    }

    // Patient jab search karega (e.g., "Fever" -> General Physician - ONLY VERIFIED)
    public List<Doctor> searchDoctors(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return doctorRepository.findBySpecializationContainingIgnoreCaseAndIsVerifiedTrue(keyword);
        }
        return doctorRepository.findByIsVerifiedTrue();
    }
    
    // Login user ID se Doctor ki detail nikalne ke liye
    public Doctor getDoctorByUserId(Long userId) {
        return doctorRepository.findByUser_Id(userId);
    }

    public Doctor getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId).orElse(null);
    }

    public List<Doctor> getUnverifiedDoctors() {
        return doctorRepository.findByIsVerifiedFalse();
    }

    public List<Doctor> getAllDoctorsForAdmin() {
        return doctorRepository.findAll();
    }

    public void verifyDoctor(Long doctorId) {
        Doctor doctor = getDoctorById(doctorId);
        if (doctor != null) {
            doctor.setVerified(true);
            doctorRepository.save(doctor);
        }
    }

    @org.springframework.transaction.annotation.Transactional
    public void deleteDoctor(Long doctorId) {
        Doctor doctor = getDoctorById(doctorId);
        if (doctor != null) {
            User user = doctor.getUser();
            doctorRepository.delete(doctor);
            if (user != null) {
                userRepository.delete(user);
            }
        }
    }
}