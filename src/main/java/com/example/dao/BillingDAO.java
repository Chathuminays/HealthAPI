/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

/**
 *
 * @author chath
 */
import com.example.model.Billing;
import com.example.model.Patient;
import java.util.ArrayList;
import java.util.List;


public class BillingDAO {
    
    // List to hold billing records
    private static List<Billing> billingList = new ArrayList<>();
    
    // List to hold patient records
    private static List<Patient> patientList = new ArrayList<>();
    
    // PatientDAO instance for patient-related operations
    private static PatientDAO patientDAO = new PatientDAO();       
   

    static {
        patientList = patientDAO.getAllPatients();
        
        billingList.add(new Billing(1, patientList.get(0), List.of(120.00, 70.00), List.of(100.00, 50.00)));
        billingList.add(new Billing(2, patientList.get(1), List.of(80.00, 60.00), List.of(20.00, 70.00)));
    }
    
    // Method to retrieve all billing records
    public List<Billing> getAllBills() {
        return billingList;
    }

    // Method to retrieve a billing record by ID
    public Billing getBillById(int id) {
        
        for (Billing bill : billingList) {
            if (bill.getBillID() == id) {
                return bill;
            }
        }
        return null;
    }
    
    // Method to retrieve a billing record by patient ID
    public Billing getBillByPatientId(int patientID) {
        
        for (Billing bill : billingList) {
            if (bill.getPatient().getPatientID() == patientID) {
                return bill;
            }
        }
        return null;
    }
    
    // Method to add a new billing record
    public String addBill(Billing bill, Patient billingPatient) {
        
        int newBillID = getNextBillID();
        bill.setBillID(newBillID);
        
        bill.setPatient(billingPatient);
        billingList.add(bill);
        
        return "Bill with ID " + newBillID + " created successfully.";
    }
    
    // Method to add a new invoice to a bill
    public String addNewInvoiceToBill(int billID, Double newInvoice){
        
        Billing patientBill = getBillById(billID);
        
        List<Double> invoices = new ArrayList<>(patientBill.getInvoices());
        invoices.add(newInvoice);
        
        patientBill.setInvoices(invoices);
        return "New Invoice Amount added to Bill with ID " + billID;
    }
    
    // Method to add a new payment to a bill
    public String addNewPaymentToBill(int billID, Double newPayment){
        
        Billing patientBill = getBillById(billID);
        
        List<Double> payments = new ArrayList<>(patientBill.getPayments());
        payments.add(newPayment);
        
        patientBill.setPayments(payments);
        return "New Payment Amount added to Bill with ID " + billID;
    }

    // Method to update a billing record
    public void updateBill(Billing updatedBill) {
        
        for (int i = 0; i < billingList.size(); i++) {
            Billing existingBill = billingList.get(i);
            
            if (existingBill.getBillID() == updatedBill.getBillID()) {
                
                if (updatedBill.getInvoices() != null){
                    existingBill.setInvoices(updatedBill.getInvoices());
                }
                if (updatedBill.getPayments() != null){
                    existingBill.setPayments(updatedBill.getPayments());
                }
            }
        }
    }

    // Method to delete a billing record by ID
    public void deleteBill(int id) {
        billingList.removeIf(bill -> bill.getBillID() == id);
    }
    
    // Method to calculate the outstanding balance for a patient
    public double calculateOutstandingBalance(int patientID){
        
        Billing patientBill = getBillByPatientId(patientID);
        
        Double totalAmount = 0.00;
        for (Double invoice : patientBill.getInvoices()){
            totalAmount += invoice;
        }
        
        if (patientBill.getPayments() == null || patientBill.getPayments().isEmpty()){
            return totalAmount;
        }
        
        Double totalPayments = 0.00;
        for (Double payment : patientBill.getPayments()){
            totalPayments += payment;
        }
        
        return totalAmount - totalPayments;
    }
    
    // Method to get the next available billing ID
    public int getNextBillID() {
        
        int maxBillingID = Integer.MIN_VALUE;
        
        if (billingList != null && !billingList.isEmpty()) {
            
            for (Billing bill : billingList) {
                int billID = bill.getBillID();
                if (billID > maxBillingID) {
                    maxBillingID = billID;
                }
            }
        } else {
            return 1;
        }
        return maxBillingID + 1;
    }
}
