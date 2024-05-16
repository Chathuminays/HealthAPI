/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

/**
 *
 * @author chath
 */
public class Person {
    
    private int personID;        
    private String firstName;    
    private String lastName;     
    private String phoneNo;         
    private String address;      

    // Constructor to initialize the Person object with all fields
    public Person(int personID, String firstName, String lastName, String phoneNo, String address) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    // Default constructor
    public Person() {
    }

    // Getter method for retrieving the person's ID
    public int getPersonID() {
        return personID;
    }

    // Setter method for setting the person's ID
    public void setPersonID(int personID) {
        this.personID = personID;
    }

    // Getter method for retrieving the person's first name
    public String getFirstName() {
        return firstName;
    }

    // Setter method for setting the person's first name
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter method for retrieving the person's last name
    public String getLastName() {
        return lastName;
    }

    // Setter method for setting the person's last name
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter method for retrieving the person's phone number
    public String getPhoneNo() {
        return phoneNo;
    }

    // Setter method for setting the person's phone number
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    // Getter method for retrieving the person's address
    public String getAddress() {
        return address;
    }

    // Setter method for setting the person's address
    public void setAddress(String address) {
        this.address = address;
    }
}

