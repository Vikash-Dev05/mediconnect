package com.mediconnect.service;

import com.mediconnect.entity.Payment;
import com.mediconnect.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public void saveManualPayment(Long doctorId, Double amount, String patientInfo) {
        Payment payment = new Payment();
        payment.setDoctorId(doctorId);
        payment.setAmount(amount);
        payment.setPatientInfo(patientInfo);
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
