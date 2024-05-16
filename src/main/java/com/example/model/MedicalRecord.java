/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

/**
 *
 * @author chath
 */
public class MedicalRecord {
    
    private int recordID;
    private Patient patient;
    private String diagnosis;
    private String treatments;
    private String additionalDetails;

    // Constructor with parameters
    public MedicalRecord(int recordID, Patient patient, String diagnosis, String treatments, String additionalDetails) {
        this.recordID = recordID;
        this.patient = patient;
        this.diagnosis = diagnosis;
        this.treatments = treatments;
        this.additionalDetails = additionalDetails;
    }

    // Default constructor
    public MedicalRecord() {
    }

    // Getter method for recordID
    public int getRecordID() {
        return recordID;
    }

    // Setter method for recordID
    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    // Getter method for patient
    public Patient getPatient() {
        return patient;
    }

    // Setter method for patient
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // Getter method for diagnosis
    public String getDiagnosis() {
        return diagnosis;
    }

    // Setter method for diagnosis
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    // Getter method for treatments
    public String getTreatments() {
        return treatments;
    }

    // Setter method for treatments
    public void setTreatments(String treatments) {
        this.treatments = treatments;
    }

    // Getter method for additionalDetails
    public String getAdditionalDetails() {
        return additionalDetails;
    }

    // Setter method for additionalDetails
    public void setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
    }    
}
