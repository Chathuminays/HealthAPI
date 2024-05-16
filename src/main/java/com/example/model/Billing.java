/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

/**
 *
 * @author chath
 */
import java.util.List;


public class Billing {
    
    private int billID;             
    private Patient patient;        
    private List<Double> invoices;  
    private List<Double> payments;  

    // Constructor to initialize a Billing object with all fields
    public Billing(int billID, Patient patient, List<Double> invoices, List<Double> payments) {
        this.billID = billID;
        this.patient = patient;
        this.invoices = invoices;
        this.payments = payments;
    }

    // Default constructor
    public Billing() {
    }

    // Getter method for retrieving the billing ID
    public int getBillID() {
        return billID;
    }

    // Setter method for setting the billing ID
    public void setBillID(int billID) {
        this.billID = billID;
    }

    // Getter method for retrieving the patient associated with the billing
    public Patient getPatient() {
        return patient;
    }

    // Setter method for setting the patient associated with the billing
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // Getter method for retrieving the list of invoices
    public List<Double> getInvoices() {
        return invoices;
    }

    // Setter method for setting the list of invoices
    public void setInvoices(List<Double> invoices) {
        this.invoices = invoices;
    }

    // Getter method for retrieving the list of payments
    public List<Double> getPayments() {
        return payments;
    }

    // Setter method for setting the list of payments
    public void setPayments(List<Double> payments) {
        this.payments = payments;
    }
   
}
