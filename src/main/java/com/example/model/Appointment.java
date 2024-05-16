/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

/**
 *
 * @author chath
 */
public class Appointment {
    
    private int appoinmentID; 
    private String date;
    private String time;
    private String roomNo; 
    private Doctor doctor;
    private Patient patient;

    // Parameterized constructor to initialize all fields of the Appointment class
    public Appointment(int appoinmentID, String date, String time, String roomNo, Doctor doctor, Patient patient) {
        this.appoinmentID = appoinmentID;
        this.date = date;
        this.time = time;
        this.roomNo = roomNo;
        this.doctor = doctor;
        this.patient = patient;
    }

    // Default constructor
    public Appointment() {
    }

    // Getter method for appoinmentID
    public int getAppoinmentID() {
        return appoinmentID;
    }

    // Setter method for appoinmentID
    public void setAppoinmentID(int appoinmentID) {
        this.appoinmentID = appoinmentID;
    }

    // Getter method for date
    public String getDate() {
        return date;
    }

    // Setter method for date
    public void setDate(String date) {
        this.date = date;
    }

    // Getter method for time
    public String getTime() {
        return time;
    }

    // Setter method for time
    public void setTime(String time) {
        this.time = time;
    }

    // Getter method for roomNo
    public String getRoomNo() {
        return roomNo;
    }

    // Setter method for roomNo
    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    // Getter method for doctor
    public Doctor getDoctor() {
        return doctor;
    }

    // Setter method for doctor
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    // Getter method for patient
    public Patient getPatient() {
        return patient;
    }

    // Setter method for patient
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
}

