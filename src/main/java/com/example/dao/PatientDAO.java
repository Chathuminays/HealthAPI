/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

/**
 *
 * @author chath
 */
import com.example.model.Appointment;
import com.example.model.Billing;
import com.example.model.MedicalRecord;
import com.example.model.Prescription;
import com.example.model.Patient;
import com.example.model.Person;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    
    private static List<Patient> patientList;
    private static List<Person> personList;
    private static List<Appointment> appointmentList;
    private static List<MedicalRecord> medicalRecordList;
    private static List<Prescription> prescriptionList;
    private static List<Billing> billingList;
    
    // Instance of PersonDAO for person-related operations
    private static PersonDAO personDAO = new PersonDAO();

    static {
        patientList = new ArrayList<>();
        
        // Adding sample patient data
        patientList.add(new Patient(1, 3, "Alice", "Smith", "789123", "Paris", "30", "Female", "Good"));
        patientList.add(new Patient(2, 4, "Bob", "Johnson", "456789", "Tokyo", "34", "Male", "Excellent"));
        
        // Initializing DAOs for related entities
        MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();
        medicalRecordList = medicalRecordDAO.getAllMedicalRecords();
        
        PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
        prescriptionList = prescriptionDAO.getAllPrescriptions();
        
        BillingDAO billingDAO = new BillingDAO();
        billingList = billingDAO.getAllBills();
    }

    // Method to get all patients
    public List<Patient> getAllPatients() {
        return patientList;
    }

    // Method to get patient by ID
    public Patient getPatientById(int id) {
        for (Patient patient : patientList) {
            if (patient.getPatientID() == id) {
                return patient;
            }
        }
        return null;
    }
    
    // Method to add a new patient
    public String addPatient(Patient patient) {
        // Generating new patient ID
        int newPatientID = getNextPatientID();
        patient.setPatientID(newPatientID);
        
        // Creating a new person object and setting details
        Person newPerson = new Person();
        newPerson.setFirstName(patient.getFirstName());
        newPerson.setLastName(patient.getLastName());
        newPerson.setPhoneNo(patient.getPhoneNo());
        newPerson.setAddress(patient.getAddress());
        
        // Adding person to person DAO
        personDAO.addPerson(newPerson);
        List<Person> personList = personDAO.getAllPeople();
        
        // Setting person ID for patient
        patient.setPersonID(personList.size());
        
        // Adding patient to patient list
        patientList.add(patient);
        return "Patient with ID " + newPatientID + " created successfully.";
    }
    
    // Method to add patient by existing person
    public String addPatientByPerson(Person existingPerson, Patient patient){
        // Generating new patient ID
        int newPatientID = getNextPatientID();
        patient.setPatientID(newPatientID);
        
        // Setting person details for patient
        patient.setPersonID(existingPerson.getPersonID());
        patient.setFirstName(existingPerson.getFirstName());
        patient.setLastName(existingPerson.getLastName());
        patient.setPhoneNo(existingPerson.getPhoneNo());
        patient.setAddress(existingPerson.getAddress());
        
        // Adding patient to patient list
        patientList.add(patient);
        
        return "Patient with ID " + newPatientID + " created successfully for Person ID " + existingPerson.getPersonID();
    }

    // Method to update patient details
    public void updatePatient(Patient updatedPatient) {
        for (int i = 0; i < patientList.size(); i++) {
            Patient existingPatient = patientList.get(i);
            if (existingPatient.getPatientID() == updatedPatient.getPatientID()) {
                // Updating patient details
                if (updatedPatient.getFirstName() != null){
                    existingPatient.setFirstName(updatedPatient.getFirstName());
                }
                if (updatedPatient.getLastName() != null){
                    existingPatient.setLastName(updatedPatient.getLastName());
                }
                if (updatedPatient.getPhoneNo() != null){
                    existingPatient.setPhoneNo(updatedPatient.getPhoneNo());
                }
                if (updatedPatient.getAddress() != null){
                    existingPatient.setAddress(updatedPatient.getAddress());
                }
                if (updatedPatient.getAge() != null){
                    existingPatient.setAge(updatedPatient.getAge());
                }
                if (updatedPatient.getGender() != null){
                    existingPatient.setGender(updatedPatient.getGender());
                }
                if (updatedPatient.getHealthStatus() != null){
                    existingPatient.setHealthStatus(updatedPatient.getHealthStatus());
                }
            }
        }
    }

    // Method to delete a patient by ID
    public void deletePatient(int id) {
        patientList.removeIf(patient -> patient.getPatientID() == id);
    }
    
    // Method to get patient by person ID
    public Patient getPatientByPersonID(int personID){
        for (Patient patient : patientList) {
            if (patient.getPersonID() == personID) {
                return patient;
            }
        }
        return null;
    }
    
    // Method to get appointments of a patient
    public List<Appointment> getPatientAppointments(int patientID){
        // Initializing appointment DAO
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        appointmentList = appointmentDAO.getAllAppoinments();
        
        // List to store patient appointments
        List<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment appointment : appointmentList){
            if (appointment.getPatient().getPatientID() == patientID){
                patientAppointments.add(appointment);
            }
        }
        return patientAppointments;
    }
    
    // Method to get medical records of a patient
    public List<MedicalRecord> getPatientMedicalRecords(int patientID){
        // List to store patient medical records
        List<MedicalRecord> patientMedicalRecords = new ArrayList<>();
        for (MedicalRecord record : medicalRecordList){
            if (record.getPatient().getPatientID() == patientID){
                patientMedicalRecords.add(record);
            }
        }
        return patientMedicalRecords;
    }
    
    // Method to get prescriptions of a patient
    public List<Prescription> getPatientPrescriptions(int patientID){
        // List to store patient prescriptions
        List<Prescription> patientPrescriptions = new ArrayList<>();
        for (Prescription prescription : prescriptionList){
            if (prescription.getPatient().getPatientID() == patientID){
                patientPrescriptions.add(prescription);
            }
        }
        return patientPrescriptions;
    }
    
    // Method to get billings of a patient
    public List<Billing> getPatientBillings(int patientID){
        // List to store patient billings
        List<Billing> patientBillings = new ArrayList<>();
        for (Billing bill : billingList){
            if (bill.getPatient().getPatientID() == patientID){
                patientBillings.add(bill);
            }
        }
        return patientBillings;
    }
    
    // Method to generate next patient ID
    public int getNextPatientID() {
        // If patient list is empty, return 1
        if (patientList.isEmpty()) {
            return 1; 
        } else {
            // Find maximum patient ID and increment
            int maxPatientID = Integer.MIN_VALUE;
            for (Patient patient : patientList) {
                int patientID = patient.getPatientID();
                if (patientID > maxPatientID) {
                    maxPatientID = patientID;
                }
            }
            // Incrementing the maximum patient ID to generate the next available ID
            return maxPatientID + 1;
        }
    }
}

