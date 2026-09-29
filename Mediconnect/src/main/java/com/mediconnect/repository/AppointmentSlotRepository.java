package com.mediconnect.repository;

import com.mediconnect.entity.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {
    List<AppointmentSlot> findByDoctorId(Long doctorId);
    List<AppointmentSlot> findByStatus(String status);
    List<AppointmentSlot> findByDoctorIdAndStatus(Long doctorId, String status);
    java.util.Optional<AppointmentSlot> findById(Long id);
}
