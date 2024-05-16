/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

/**
 *
 * @author chath
 */
public class Doctor extends Person {
    
    private int doctorID;            
    private String specialization; 
    private boolean availability;  

    // Constructor to initialize a Doctor object with all fields
    public Doctor(int doctorID, String firstName, String lastName, String specialization, boolean availability, int personID, String phoneNo, String address) {
        super(personID, firstName, lastName, phoneNo, address); // Call superclass constructor
        this.doctorID = doctorID;
        this.specialization = specialization;
        this.availability = availability;
    }

    // Default constructor
    public Doctor() {
    }

    // Getter method for retrieving the doctor's ID
    public int getDoctorID() {
        return doctorID;
    }

    // Setter method for setting the doctor's ID
    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    // Getter method for retrieving the doctor's specialization
    public String getSpecialization() {
        return specialization;
    }

    // Setter method for setting the doctor's specialization
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // Getter method for retrieving the doctor's availability
    public boolean isAvailability() {
        return availability;
    }

    // Setter method for setting the doctor's availability
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}

