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
import com.example.model.Person;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {
    
    // List to hold Doctor objects
    private static List<Doctor> doctorList;
    
    // List to hold Appointment objects
    private static List<Appointment> appointmentList;
    
    // PersonDAO instance for handling related Person objects
    private static PersonDAO personDAO = new PersonDAO();
   
    static {
       doctorList = new ArrayList<>();  
       // Sample data for Doctors
       doctorList.add(new Doctor(1, "John", "Doe", "Surgeon", true, 1, "123456", "New York"));
       doctorList.add(new Doctor(2, "Jim", "Cary", "Heart Specialist", true, 2, "457896", "London"));     
    }

    // Method to retrieve all Doctors
    public List<Doctor> getAllDoctors() {
        return doctorList;
    }

    // Method to get a Doctor by ID
    public Doctor getDoctorById(int id) {
        for (Doctor doctor : doctorList) {
            if (doctor.getDoctorID() == id) {
                return doctor;
            }
        }
        return null;
    }
    
    // Method to add a new Doctor
    public String addDoctor(Doctor doctor) {  
        
        // Generate new Doctor ID
        int newDoctorID = getNextDoctorID();
        doctor.setDoctorID(newDoctorID);
        
        // Create new Person object and add it to PersonDAO
        Person newPerson = new Person();
        newPerson.setFirstName(doctor.getFirstName());
        newPerson.setLastName(doctor.getLastName());
        newPerson.setPhoneNo(doctor.getPhoneNo());
        newPerson.setAddress(doctor.getAddress());
        personDAO.addPerson(newPerson);
        
        // Retrieve updated list of Persons
        List<Person> personList = personDAO.getAllPeople();
        
        // Set Person ID and add Doctor to the list
        doctor.setPersonID(personList.size());
        doctorList.add(doctor);
        
        return "Doctor with ID " + newDoctorID + " created successfully.";
    }
    
    // Method to add a new Doctor associated with an existing Person
    public String addDoctorByPerson(Person existingPerson, Doctor doctor){
        
        // Generate new Doctor ID
        int newDoctorID = getNextDoctorID();
        doctor.setDoctorID(newDoctorID);
        
        // Set Doctor details from existing Person
        doctor.setPersonID(existingPerson.getPersonID());
        doctor.setFirstName(existingPerson.getFirstName());
        doctor.setLastName(existingPerson.getLastName());
        doctor.setPhoneNo(existingPerson.getPhoneNo());
        doctor.setAddress(existingPerson.getAddress());
        
        // Add Doctor to the list
        doctorList.add(doctor);
        
        return "Doctor with ID " + newDoctorID + " created successfully for Person ID " + existingPerson.getPersonID();
    }

    // Method to update an existing Doctor
    public void updateDoctor(Doctor updatedDoctor) {
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor existingDoctor = doctorList.get(i);
            if (existingDoctor.getDoctorID() == updatedDoctor.getDoctorID()) {
                
                // Update Doctor fields if provided
                if (updatedDoctor.getFirstName() != null){
                    existingDoctor.setFirstName(updatedDoctor.getFirstName());
                }
                if (updatedDoctor.getLastName() != null){
                    existingDoctor.setLastName(updatedDoctor.getLastName());
                }
                if (updatedDoctor.getSpecialization() != null){
                    existingDoctor.setSpecialization(updatedDoctor.getSpecialization());
                }
                if (updatedDoctor.isAvailability() == true || updatedDoctor.isAvailability() == false){
                    existingDoctor.setAvailability(updatedDoctor.isAvailability());
                }
                if (updatedDoctor.getPhoneNo() != null){
                    existingDoctor.setPhoneNo(updatedDoctor.getPhoneNo());
                }
                if (updatedDoctor.getAddress() != null){
                    existingDoctor.setAddress(updatedDoctor.getAddress());
                }
            }
        }
    }

    // Method to delete a Doctor by ID
    public void deleteDoctor(int id) {
        doctorList.removeIf(doctor -> doctor.getDoctorID() == id);
    }
    
    // Method to get Doctor by Person ID
    public Doctor getDoctorByPersonID(int personID){
        for (Doctor doctor : doctorList) {
            if (doctor.getPersonID() == personID) {
                return doctor;
            }
        }
        return null;
    }
    
    // Method to get appointments associated with a Doctor
    public List<Appointment> getDoctorAppointments(int doctorID){
        
        // Retrieve all appointments
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        appointmentList = appointmentDAO.getAllAppoinments();
       
        List<Appointment> doctorAppointments = new ArrayList<>();
        for (Appointment appointment : appointmentList){
            if (appointment.getDoctor().getDoctorID() == doctorID){
                doctorAppointments.add(appointment);
            }
        }
        return doctorAppointments;
    }
    
    // Method to get the next available Doctor ID
    public int getNextDoctorID() {
        if (doctorList.isEmpty()) {
            return 1; 
        }else {
            int maxDoctorID = Integer.MIN_VALUE;
            for (Doctor doctor : doctorList) {
                int doctorID = doctor.getDoctorID();
                if (doctorID > maxDoctorID) {
                    maxDoctorID = doctorID;
                }
            }
            return maxDoctorID + 1;
        }
    }
}

