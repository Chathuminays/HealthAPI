/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

/**
 *
 * @author chath
 */
import com.example.model.Doctor;
import com.example.model.Patient;
import com.example.model.Person;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {
    
    private static List<Person> personList = new ArrayList<>();
    
    // Static instances of DoctorDAO and PatientDAO for accessing related data
    private static DoctorDAO doctorDAO = new DoctorDAO(); 
    private static PatientDAO patientDAO = new PatientDAO();

    static {
        personList.add(new Person(1, "John", "Doe", "123456", "New York"));
        personList.add(new Person(2, "Jim", "Cary", "457896", "London"));
        personList.add(new Person(3, "Alice", "Smith", "789123", "Paris"));
        personList.add(new Person(4, "Bob", "Johnson", "456789", "Tokyo"));
        personList.add(new Person(5, "Emma", "Brown", "987654", "Sydney"));
    }

    // Method to retrieve all Person objects
    public List<Person> getAllPeople() {
        return personList;
    }

    // Method to retrieve a Person by their ID
    public Person getPersonByID(int id) {
        for (Person person : personList) {
            if (person.getPersonID() == id) {
                return person;
            }
        }
        return null;
    }
    
    // Method to add a new Person
    public String addPerson(Person person) {
        int newPersonID = getNextPersonID();
        person.setPersonID(newPersonID);
        personList.add(person);
        return "Person with ID " + newPersonID + " created successfully.";
    }

    // Method to update an existing Person
    public void updatePerson(Person updatedPerson) {
        for (int i = 0; i < personList.size(); i++) {
            Person existingPerson = personList.get(i);
            
            if (existingPerson.getPersonID() == updatedPerson.getPersonID()) {
                
                // Update fields if not null
                if (updatedPerson.getFirstName() != null){
                    existingPerson.setFirstName(updatedPerson.getFirstName());
                }
                if (updatedPerson.getLastName() != null){
                    existingPerson.setLastName(updatedPerson.getLastName());
                }
                if (updatedPerson.getPhoneNo() != null){
                    existingPerson.setPhoneNo(updatedPerson.getPhoneNo());
                }
                if (updatedPerson.getAddress() != null){
                    existingPerson.setAddress(updatedPerson.getAddress());
                }
                
                // Update related Doctor object if exists
                Doctor updateDoctor = doctorDAO.getDoctorByPersonID(existingPerson.getPersonID());
                if (updateDoctor != null){
                    updateDoctor.setFirstName(existingPerson.getFirstName());
                    updateDoctor.setLastName(existingPerson.getLastName());
                    updateDoctor.setPhoneNo(existingPerson.getPhoneNo());
                    updateDoctor.setAddress(existingPerson.getAddress());
                }
                
                // Update related Patient object if exists
                Patient updatePatient = patientDAO.getPatientByPersonID(existingPerson.getPersonID());
                if (updatePatient != null){
                    updatePatient.setFirstName(existingPerson.getFirstName());
                    updatePatient.setLastName(existingPerson.getLastName());
                    updatePatient.setPhoneNo(existingPerson.getPhoneNo());
                    updatePatient.setAddress(existingPerson.getAddress());
                }
            }
        }
    }

    // Method to delete a Person by ID
    public void deletePerson(int id) { 
     
        // Delete related Doctor object if exists
        Doctor doctor = doctorDAO.getDoctorByPersonID(id);
        if (doctor != null){
            doctorDAO.deleteDoctor(doctor.getDoctorID());
        }
        
        // Delete related Patient object if exists
        Patient patient = patientDAO.getPatientByPersonID(id);
        if (patient != null){
            patientDAO.deletePatient(patient.getPatientID());
        }
        
        // Remove the Person from the list
        personList.removeIf(person -> person.getPersonID() == id);
    }
    
    // Method to get the next available Person ID
    public int getNextPersonID() {
       
        int maxPersonID = Integer.MIN_VALUE;

        for (Person person : personList) {
            int personID = person.getPersonID();
            if (personID > maxPersonID) {
                maxPersonID = personID;
            }
        }
        return maxPersonID + 1;
    }
    
}

