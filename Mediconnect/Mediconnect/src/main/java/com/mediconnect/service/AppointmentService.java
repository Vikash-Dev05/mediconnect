package com.mediconnect.service;

import com.mediconnect.entity.Appointment;
import com.mediconnect.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Appointment save karne ke liye (Booking)
    public void bookAppointment(Appointment appointment) {
        appointment.setStatus("PENDING"); // Shuru mein status Pending rahega
        appointmentRepository.save(appointment);
    }

    // Doctor ko uski appointments dikhane ke liye
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    // Patient ko uski purani bookings dikhane ke liye
    public List<Appointment> getPatientAppointments(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }
}