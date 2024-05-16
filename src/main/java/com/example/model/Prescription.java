/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

/**
 *
 * @author chath
 */
public class Prescription {
    
    private int prescriptionID;
    private Patient patient;
    private String medicationDetails;
    private String dosage;
    private String instructions;
    private String duration;

    // Constructor with parameters
    public Prescription(int prescriptionID, Patient patient, String medicationDetails, String dosage, String instructions, String duration) {
        this.prescriptionID = prescriptionID;
        this.patient = patient;
        this.medicationDetails = medicationDetails;
        this.dosage = dosage;
        this.instructions = instructions;
        this.duration = duration;
    }

    // Default constructor
    public Prescription() {
    }

    // Getter for prescription ID
    public int getPrescriptionID() {
        return prescriptionID;
    }

    // Setter for prescription ID
    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    // Getter for patient information
    public Patient getPatient() {
        return patient;
    }

    // Setter for patient information
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // Getter for medication details
    public String getMedicationDetails() {
        return medicationDetails;
    }

    // Setter for medication details
    public void setMedicationDetails(String medicationDetails) {
        this.medicationDetails = medicationDetails;
    }

    // Getter for dosage
    public String getDosage() {
        return dosage;
    }

    // Setter for dosage
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    // Getter for instructions
    public String getInstructions() {
        return instructions;
    }

    // Setter for instructions
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    // Getter for duration
    public String getDuration() {
        return duration;
    }

    // Setter for duration
    public void setDuration(String duration) {
        this.duration = duration;
    }
}
