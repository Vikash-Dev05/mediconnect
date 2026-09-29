package com.mediconnect.controller;

import com.mediconnect.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private com.mediconnect.service.PaymentService paymentService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("unverifiedDoctors", doctorService.getUnverifiedDoctors());
        model.addAttribute("allDoctors", doctorService.getAllDoctorsForAdmin());
        model.addAttribute("payments", paymentService.getAllPayments());
        return "admin_dashboard";
    }

    @PostMapping("/verify-doctor/{doctorId}")
    public String verifyDoctor(@PathVariable Long doctorId) {
        doctorService.verifyDoctor(doctorId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete-doctor/{doctorId}")
    public String deleteDoctor(@PathVariable Long doctorId) {
        doctorService.deleteDoctor(doctorId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/manual-payment")
    public String addManualPayment(@RequestParam Long doctorId, @RequestParam Double amount, @RequestParam(required = false) String patientInfo) {
        paymentService.saveManualPayment(doctorId, amount, patientInfo);
        return "redirect:/admin/dashboard#manual-payment";
    }
}
