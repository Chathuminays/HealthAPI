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
import com.example.model.Doctor;
import com.example.model.Patient;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    
    // List to store appointments
    private static List<Appointment> appointmentList = new ArrayList<>();
    
    // Instances of DoctorDAO and PatientDAO to interact with doctor and patient data
    private static DoctorDAO doctorDAO = new DoctorDAO();
    private static PatientDAO patientDAO = new PatientDAO();
    
    // Static initialization block to initialize appointmentList with sample data
    static{
        List<Doctor> doctorList = doctorDAO.getAllDoctors();
        List<Patient> patientList = patientDAO.getAllPatients();
        
        appointmentList.add(new Appointment(1, "2024-04-26", "10:00AM", "45", doctorList.get(0), patientList.get(0)));
        appointmentList.add(new Appointment(2, "2024-05-01", "12:00PM", "48", doctorList.get(1), patientList.get(1)));
    }
    
    // Method to get all appointments
    public List<Appointment> getAllAppoinments() {
        return appointmentList;
    }

    // Method to get an appointment by ID
    public Appointment getAppointmentById(int id) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppoinmentID() == id) {
                return appointment;
            }
        }
        return null;
    }
    
    // Method to search for available appointments
    public List<Appointment> searchForAvailableAppointments(String date, int doctorID){
        List<Appointment> availableAppointments = new ArrayList<>();
        
        for (Appointment appointment : appointmentList) {
            if (appointment.getDate().equals(date)) {
                if (appointment.getDoctor().getDoctorID() == doctorID){
                    availableAppointments.add(appointment);
                }
            }
        }
        return availableAppointments;
    }
    
    // Method to add a new appointment
    public String addAppointment(Appointment appointment, Doctor appointmentDoctor, Patient appointmentPatient) {
        int newAppointmentID = getNextAppointmentID();
        appointment.setAppoinmentID(newAppointmentID);
        
        appointment.setDoctor(appointmentDoctor);
        appointment.setPatient(appointmentPatient);
        
        appointmentList.add(appointment);
        return "Appointment with ID " + newAppointmentID + " created successfully.";
    }

    // Method to update an existing appointment
    public void updateAppointment(Appointment updatedAppointment) {
        
        for (int i = 0; i < appointmentList.size(); i++) {
            Appointment existingAppointment = appointmentList.get(i);
            if (existingAppointment.getAppoinmentID() == updatedAppointment.getAppoinmentID()) {
                
                if (updatedAppointment.getDate() != null){
                    existingAppointment.setDate(updatedAppointment.getDate());
                }
                if (updatedAppointment.getTime() != null){
                    existingAppointment.setTime(updatedAppointment.getTime());
                }
                if (updatedAppointment.getRoomNo() != null){
                    existingAppointment.setRoomNo(updatedAppointment.getRoomNo());
                }
                if (updatedAppointment.getDoctor() != null){
                    existingAppointment.setDoctor(updatedAppointment.getDoctor());
                }
                if (updatedAppointment.getPatient() != null){
                    existingAppointment.setPatient(updatedAppointment.getPatient());
                }
            }
        }
    }
    

    // Method to delete an appointment by ID
    public void deleteAppointment(int id) {
        appointmentList.removeIf(appointment -> appointment.getAppoinmentID() == id);
    }
    
    // Method to get the next available appointment ID
    public int getNextAppointmentID() {
        
        int maxAppointmentID = Integer.MIN_VALUE;

        if (appointmentList != null && !appointmentList.isEmpty()) {

            for (Appointment appointment : appointmentList) {
                int appointmentID = appointment.getAppoinmentID();
                if (appointmentID > maxAppointmentID) {
                    maxAppointmentID = appointmentID;
                }
            }
        } else {
            return 1;
        }

        return maxAppointmentID + 1;
    }

}
