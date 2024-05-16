/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

/**
 *
 * @author chath
 */
import com.example.model.MedicalRecord;
import com.example.model.Patient;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDAO {
    
    // List to store medical records
    private static List<MedicalRecord> medicalRecordList = new ArrayList<>();
    
    // List to store patients
    private static List<Patient> patientList = new ArrayList<>();
    
    // PatientDAO instance to interact with patient data
    private static PatientDAO patientDAO = new PatientDAO();
    
    // Static block to initialize sample medical records
    static {
        patientList = patientDAO.getAllPatients();
        
        medicalRecordList.add(new MedicalRecord(1, patientList.get(0), "Fractured arm", "treatments", "X-ray results attached"));
        medicalRecordList.add(new MedicalRecord(2, patientList.get(1), "Headache", "Prescribed medication", "MRI scan report attached"));
    }
    
    // Method to get all medical records
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordList;
    }
    
    // Method to get a medical record by ID
    public MedicalRecord getMedicalRecordById(int id) {
        for (MedicalRecord record : medicalRecordList) {
            if (record.getRecordID() == id) {
                return record;
            }
        }
        return null;
    }
    
    // Method to add a new medical record
    public String addMedicalRecord(MedicalRecord record, Patient appointmentPatient) {
        int newRecordID = getNextRecordID();
        record.setRecordID(newRecordID);
        
        record.setPatient(appointmentPatient);
        
        medicalRecordList.add(record);
        return "Medical Record with ID " + newRecordID + " created successfully.";
    }
    
    // Method to update an existing medical record
    public void updateMedicalRecord(MedicalRecord updatedRecord) {
        for (int i = 0; i < medicalRecordList.size(); i++) {
            MedicalRecord existingRecord = medicalRecordList.get(i);
            if (existingRecord.getRecordID() == updatedRecord.getRecordID()) {
                
                if (updatedRecord.getDiagnosis() != null){
                    existingRecord.setDiagnosis(updatedRecord.getDiagnosis());
                }
                if (updatedRecord.getTreatments() != null){
                    existingRecord.setTreatments(updatedRecord.getTreatments());
                }
                if (updatedRecord.getAdditionalDetails()!= null){
                    existingRecord.setAdditionalDetails(updatedRecord.getAdditionalDetails());
                }
                if (updatedRecord.getPatient() != null){
                    existingRecord.setPatient(updatedRecord.getPatient());
                }
            }
        }
    }

    // Method to delete a medical record by ID
    public void deleteMedicalRecord(int id) {
        medicalRecordList.removeIf(record -> record.getRecordID() == id);
    }
    
    // Method to get the next available record ID
    public int getNextRecordID() {

        int maxRecordID = Integer.MIN_VALUE;

        if (medicalRecordList != null && !medicalRecordList.isEmpty()) {

            for (MedicalRecord record : medicalRecordList) {
                int recordID = record.getRecordID();
                if (recordID > maxRecordID) {
                    maxRecordID = recordID;
                }
            }
        } else {
            return 1;
        }

        return maxRecordID + 1;
    }
}
