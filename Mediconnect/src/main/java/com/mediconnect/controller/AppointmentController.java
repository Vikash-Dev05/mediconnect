package com.mediconnect.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mediconnect.entity.Appointment;
import com.mediconnect.entity.Doctor;
import com.mediconnect.entity.Patient;
import com.mediconnect.repository.AppointmentRepository;
import com.mediconnect.repository.DoctorRepository;
import com.mediconnect.repository.PatientRepository;

// Hum ek choti si DTO (Data Transfer Object) class bana rahe hain
// sirf data receive karne ke liye. Ise file ke neeche hi likh sakte hain.
class AppointmentRequest {
    public Long patientId;
    public Long doctorId;
    public String date; // YYYY-MM-DD format mein aayega
}

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired private AppointmentRepository appointmentRepository;
    @Autowired private PatientRepository patientRepository;
    @Autowired private DoctorRepository doctorRepository;

    // 1. BOOK APPOINTMENT (CREATE)
    @PostMapping("/book")
    public Appointment bookAppointment(@RequestBody AppointmentRequest request) {
        // Step 1: Patient dhoondo
        Patient patient = patientRepository.findById(request.patientId)
            .orElseThrow(() -> new RuntimeException("Patient not found ID: " + request.patientId));

        // Step 2: Doctor dhoondo
        Doctor doctor = doctorRepository.findById(request.doctorId)
            .orElseThrow(() -> new RuntimeException("Doctor not found ID: " + request.doctorId));

        // Step 3: Nayi Appointment banao
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(LocalDate.parse(request.date));
        appointment.setStatus("BOOKED"); // Default status

        // Step 4: Save karo
        return appointmentRepository.save(appointment);
    }

    // 2. GET ALL APPOINTMENTS (READ)
    @GetMapping("/all")
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}