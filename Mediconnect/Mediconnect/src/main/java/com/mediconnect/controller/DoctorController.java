package com.mediconnect.controller;

import com.mediconnect.entity.Doctor;
import com.mediconnect.entity.User;
import com.mediconnect.service.AppointmentService;
import com.mediconnect.service.DoctorService;
import com.mediconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

import com.mediconnect.repository.AppointmentSlotRepository;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentSlotRepository slotRepository;


    @GetMapping("/dashboard")
    public String doctorDashboard(Model model, Principal principal) {

        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        
        Doctor doctor = doctorService.getDoctorByUserId(user.getId());

        if (doctor == null) {
            System.out.println("DEBUG: Doctor profile not found for user: " + email);
            model.addAttribute("error", "Your doctor profile is not fully set up. Please contact admin.");
            return "login"; // Fallback to login but WITH an error message to display
        }

        if (!doctor.isVerified()) {
            System.out.println("DEBUG: Doctor " + email + " is NOT verified. Redirecting to /pending-approval");
            return "redirect:/pending-approval";
        }

        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointmentService.getDoctorAppointments(doctor.getId()));
        model.addAttribute("slots", slotRepository.findByDoctorId(doctor.getId()));
        
        return "doctor_dashboard"; // HTML page ka naam
    }
}