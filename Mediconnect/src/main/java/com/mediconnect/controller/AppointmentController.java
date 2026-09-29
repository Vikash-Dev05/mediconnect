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


    @PostMapping("/book")
    public Appointment bookAppointment(@RequestBody AppointmentRequest request) {

        Patient patient = patientRepository.findById(request.patientId)
            .orElseThrow(() -> new RuntimeException("Patient not found ID: " + request.patientId));


        Doctor doctor = doctorRepository.findById(request.doctorId)
            .orElseThrow(() -> new RuntimeException("Doctor not found ID: " + request.doctorId));


        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(LocalDate.parse(request.date));
        appointment.setStatus("BOOKED"); // Default status



        return appointmentRepository.save(appointment);
    }


    @GetMapping("/all")
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}