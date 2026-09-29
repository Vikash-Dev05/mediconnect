package com.mediconnect.controller;

import com.mediconnect.entity.AppointmentSlot;
import com.mediconnect.entity.Appointment;
import com.mediconnect.entity.User;
import com.mediconnect.repository.AppointmentSlotRepository;
import com.mediconnect.repository.AppointmentRepository;
import com.mediconnect.service.UserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Map;
import java.util.HashMap;

@RestController
public class PaymentController {

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Autowired
    private AppointmentSlotRepository slotRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/patient/create-order")
    @ResponseBody
    public String createOrder(@RequestBody Map<String, Object> data) throws RazorpayException {
        int amount = Integer.parseInt(data.get("amount").toString());
        Long slotId = Long.parseLong(data.get("slotId").toString());

        RazorpayClient client = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100); // Paisa mein convert (500 INR = 50000)
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + slotId);

        Order order = client.orders.create(orderRequest);
        return order.toString();
    }

    @PostMapping("/patient/verify-payment")
    @ResponseBody
    public Map<String, String> verifyPayment(@RequestBody Map<String, Object> data, Principal principal) {
        String paymentId = data.get("razorpay_payment_id").toString();
        String orderId = data.get("razorpay_order_id").toString();
        Long slotId = Long.parseLong(data.get("slotId").toString());

        Map<String, String> response = new HashMap<>();

        // Yahan ideally signature verify hona chahiye signature check karke
        
        AppointmentSlot slot = slotRepository.findById(slotId).orElse(null);
        if (slot != null && "Available".equals(slot.getStatus())) {
            slot.setStatus("BOOKED");
            slotRepository.save(slot);

            // Create Appointment record
            Appointment appointment = new Appointment();
            appointment.setDoctor(slot.getDoctor());
            appointment.setAppointmentDate(slot.getDate());
            appointment.setStatus("CONFIRMED");
            // Patient dhoondhne ke liye (Assume logic to get patient linked to user)
            // appointment.setPatient(patient);
            appointmentRepository.save(appointment);

            response.put("status", "success");
        } else {
            response.put("status", "failed");
        }

        return response;
    }
}
