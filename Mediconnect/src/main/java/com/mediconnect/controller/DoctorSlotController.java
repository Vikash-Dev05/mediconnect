package com.mediconnect.controller;

import com.mediconnect.entity.AppointmentSlot;
import com.mediconnect.entity.Doctor;
import com.mediconnect.entity.User;
import com.mediconnect.repository.AppointmentSlotRepository;
import com.mediconnect.service.DoctorService;
import com.mediconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class DoctorSlotController {

    @Autowired
    private AppointmentSlotRepository slotRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserService userService;

    // Doctor Dashboard: Slots manage karne ke liye
    @PostMapping("/doctor/add-slot")
    public String addSlot(@RequestParam String date, @RequestParam String startTime, @RequestParam String endTime, Principal principal) {
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        Doctor doctor = doctorService.getDoctorByUserId(user.getId());

        if (doctor != null && doctor.isVerified()) {
            AppointmentSlot slot = new AppointmentSlot();
            slot.setDoctor(doctor);
            slot.setDate(LocalDate.parse(date));
            slot.setStartTime(LocalTime.parse(startTime));
            slot.setEndTime(LocalTime.parse(endTime));
            slot.setStatus("Available");
            slotRepository.save(slot);
        }

        return "redirect:/doctor/dashboard";
    }

    // Patient View: Specific Doctor ke slots dekhne ke liye
    @GetMapping("/patient/view-slots/{doctorId}")
    public String viewSlots(@PathVariable Long doctorId, Model model) {
        List<AppointmentSlot> availableSlots = slotRepository.findByDoctorIdAndStatus(doctorId, "Available");
        model.addAttribute("slots", availableSlots);
        model.addAttribute("doctor", doctorService.getDoctorById(doctorId));
        return "view_slots";
    }

    @PostMapping("/patient/book-appointment")
    public String bookAppointment(@RequestParam Long slotId) {
        AppointmentSlot slot = slotRepository.findById(slotId).orElse(null);
        if (slot != null) {
            slot.setStatus("Booked");
            slotRepository.save(slot);
            // Ideally, create an Appointment entity here as well
        }
        return "redirect:/patient/dashboard";
    }
}
