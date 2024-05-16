/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;


/**
 *
 * @author chath
 */
public class Patient extends Person {

    private int patientID;
    private String age;
    private String gender;
    private String healthStatus;

    // Constructor with parameters
    public Patient(int patientID, int personID, String firstName, String lastName, String phoneNo, String address, String age, String gender, String healthStatus) {
        super(personID, firstName, lastName, phoneNo, address);
        this.patientID = patientID;
        this.age = age;
        this.gender = gender;
        this.healthStatus = healthStatus;
    }

    // Default constructor
    public Patient() {
    }

    // Getters and setters for patientID
    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    // Getters and setters for age
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    // Getters and setters for gender
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Getters and setters for healthStatus
    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }
}
