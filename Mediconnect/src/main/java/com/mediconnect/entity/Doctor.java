package com.mediconnect.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String qualification; // e.g., MBBS, MD
// Existing fields ke neeche add karein...
    
    private String timingFrom; // e.g., "09:00 AM"
    private String timingTo;   // e.g., "05:00 PM"

    // --- Inke Getters and Setters neeche add karein ---
    public String getTimingFrom() { return timingFrom; }
    public void setTimingFrom(String timingFrom) { this.timingFrom = timingFrom; }
    public String getTimingTo() { return timingTo; }
    public void setTimingTo(String timingTo) { this.timingTo = timingTo; }

    // NAYA CHANGE: Specialization ab ek Category object hai
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}