package com.mediconnect.controller;

import com.mediconnect.entity.User;
import com.mediconnect.entity.AppointmentSlot;
import com.mediconnect.entity.Doctor;
import com.mediconnect.repository.AppointmentSlotRepository;
import com.mediconnect.service.DoctorService;
import com.mediconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentSlotRepository slotRepository;


    @GetMapping("/dashboard")
    public String patientDashboard(Model model, @RequestParam(name = "specialization", required = false) String specialization, Principal principal) {

        String identifier = principal.getName();
        User user = userService.findUserByEmailOrPhone(identifier);
        model.addAttribute("user", user);

        List<Doctor> doctors;
        if (specialization != null && !specialization.isEmpty()) {
            doctors = doctorService.searchDoctors(specialization);
        } else {
            doctors = doctorService.getAllDoctors();
        }
        model.addAttribute("doctors", doctors);

        // Fetch all available slots and group by doctor ID
        List<AppointmentSlot> allAvailableSlots = slotRepository.findByStatus("Available");
        Map<Long, List<AppointmentSlot>> slotsByDoctor = allAvailableSlots.stream()
                .collect(Collectors.groupingBy(slot -> slot.getDoctor().getId()));
        
        model.addAttribute("slotsMap", slotsByDoctor);

        return "patient_dashboard"; // HTML page ka naam
    }
}