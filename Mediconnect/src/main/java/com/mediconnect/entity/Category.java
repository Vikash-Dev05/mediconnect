package com.mediconnect.entity;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore; // Yeh zaroori hai

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // Naam unique hona chahiye
    private String name; // e.g., "Cardiologist", "Dentist"

    // Ek Category ke bahut saare Doctors ho sakte hain
    @OneToMany(mappedBy = "category")
    @JsonIgnore // Infinite loop rokne ke liye (jab category fetch karein toh doctors ki poori list na aaye)
    private List<Doctor> doctors;

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Doctor> getDoctors() { return doctors; }
    public void setDoctors(List<Doctor> doctors) { this.doctors = doctors; }
}
