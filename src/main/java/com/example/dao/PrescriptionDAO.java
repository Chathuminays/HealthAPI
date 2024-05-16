/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

/**
 *
 * @author chath
 */
import com.example.model.Patient;
import com.example.model.Prescription;
import java.util.ArrayList;
import java.util.List;


public class PrescriptionDAO {
    
    // List to store prescription objects
    private static List<Prescription> prescriptionList = new ArrayList<>();
    
    // List to store patient objects
    private static List<Patient> patientList = new ArrayList<>();
    
    // PatientDAO instance for accessing patient data
    private static PatientDAO patientDAO = new PatientDAO();
    
    
    static {
        // Retrieve all patients
        patientList = patientDAO.getAllPatients();
        
        // Sample data
        prescriptionList.add(new Prescription(001, patientList.get(0), "Ibuprofen", "200mg", "Take with food", "7 days"));
        prescriptionList.add(new Prescription(2, patientList.get(1), "Paracetamol", "500mg", "Take as needed for pain relief", "10 days"));
    }


    // Retrieve all prescriptions.
    public List<Prescription> getAllPrescriptions() {
        return prescriptionList;
    }
    
    
    // Retrieve a prescription by its ID.
    public Prescription getPrescriptionById(int id) {
        for (Prescription prescription : prescriptionList) {
            if (prescription.getPrescriptionID() == id) {
                return prescription;
            }
        }
        return null;
    }
    
    
    // Add a new prescription.
    public String addPrescription(Prescription prescription, Patient prescriptionPatient) {
        // Generate a new prescription ID
        int newPrescriptionID = getNextPrescriptionID();
        prescription.setPrescriptionID(newPrescriptionID);
        
        // Associate the prescription with the patient
        prescription.setPatient(prescriptionPatient);
        
        // Add the prescription to the list
        prescriptionList.add(prescription);
        return "Prescription with ID " + newPrescriptionID + " created successfully.";
    }

   
    // Update an existing prescription.
    public void updatePrescription(Prescription updatedPrescription) {
        for (int i = 0; i < prescriptionList.size(); i++) {
            Prescription existingPrescription = prescriptionList.get(i);
            if (existingPrescription.getPrescriptionID() == updatedPrescription.getPrescriptionID()) {
                
                // Update prescription details
                if (updatedPrescription.getMedicationDetails() != null){
                    existingPrescription.setMedicationDetails(updatedPrescription.getMedicationDetails());
                }
                if (updatedPrescription.getDosage() != null){
                    existingPrescription.setDosage(updatedPrescription.getDosage());
                }
                if (updatedPrescription.getInstructions() != null){
                    existingPrescription.setInstructions(updatedPrescription.getInstructions());
                }
                if (updatedPrescription.getDuration() != null){
                    existingPrescription.setDuration(updatedPrescription.getDuration());
                }
            }
        }
    }

    
    // Delete a prescription by its ID.
    public void deletePrescription(int id) {
        prescriptionList.removeIf(prescription -> prescription.getPrescriptionID() == id);
    }
    
    
    // Get the next available prescription ID.
    public int getNextPrescriptionID() {

        int maxPrescriptionID = Integer.MIN_VALUE;

        if (prescriptionList != null && !prescriptionList.isEmpty()) {

            for (Prescription prescription : prescriptionList) {
                int prescriptionID = prescription.getPrescriptionID();
                if (prescriptionID > maxPrescriptionID) {
                    maxPrescriptionID = prescriptionID;
                }
            }
        } else {
            return 1;
        }

        return maxPrescriptionID + 1;
    }
}
