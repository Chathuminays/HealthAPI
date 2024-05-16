/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

/**
 *
 * @author chath
 */
import com.example.model.Billing;
import com.example.dao.BillingDAO;
import com.example.dao.PatientDAO;
import com.example.exception.NotFoundException;
import com.example.exception.BadRequestException;
import com.example.model.Patient;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/billings")
public class BillingResource {
    
    // Logger for logging information and errors
    private static final Logger LOGGER = LoggerFactory.getLogger(BillingResource.class);
    
    // BillingDAO instance for handling billing-related operations
    private BillingDAO billingDAO = new BillingDAO();   
    
    // PatientDAO instance for handling patient-related operations
    private static PatientDAO patientDAO = new PatientDAO();
    
    
    // Endpoint to retrieve all billing records.
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Billing> getAllBills() {
        LOGGER.info("Getting All Billings...");
        
         List<Billing> bills = billingDAO.getAllBills();
         
         if (!bills.isEmpty()){
            LOGGER.info("Fetched All Billings Details ");
            return bills;
        } else {
            throw new NotFoundException("No Billings Found! ");
        }
    }
    

    // Endpoint to retrieve a billing record by ID.
    @GET
    @Path("/{billID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Billing getBillById(@PathParam("billID") int billID) {
        LOGGER.info("Getting Bill by ID: " +  billID + "... ");

        Billing bill = billingDAO.getBillById(billID);
        
        if (bill != null) {
            LOGGER.info("Fetched Details of Bill with ID " + billID);
            return bill;
        } else {
            throw new NotFoundException("Bill with ID " + billID + " not found.");
        }
    }
    
    
    // Endpoint to retrieve outstanding balance for a patient.
    @GET
    @Path("/patient/{patientID}/balance")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOutstandingBalance(@PathParam("patientID") int patientID) {
        LOGGER.info("Getting OutstandingBalance for Patient with ID: " +  patientID + "... ");

        Patient patient = patientDAO.getPatientById(patientID);
        
        if (patient!= null) {
            
            Billing patientBill = billingDAO.getBillByPatientId(patientID);
            
            if (patientBill.getInvoices() == null || patientBill.getInvoices().isEmpty()){
                throw new BadRequestException("Billing Invoices are empty.");
            }
            
            Double outstandingBalance = billingDAO.calculateOutstandingBalance(patientID);
            LOGGER.info("Fetched OutstandingBalance for Patient with ID: " +  patientID);
            return "Outstanding Balance for Patient with ID: " +  patientID + " = " + outstandingBalance;
            
        } else {
            throw new NotFoundException("Bill for Patient with ID " + patientID + " not found.");
        }
    }
    
    
    // Endpoint to add a new billing record for a patient.
    @POST
    @Path("/patient/{patientID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBill(@PathParam("patientID") int patientID, Billing bill) {
        LOGGER.info("Creating a new Bill... ");
        
        Patient billingPatient = patientDAO.getPatientById(patientID);
        if (billingPatient == null){
            throw new NotFoundException("Patient with ID " + patientID + " not found.");
        }
        
        Billing existingBill = billingDAO.getBillByPatientId(patientID);
        if (existingBill != null){
            throw new BadRequestException("Bill for Patient with ID " + patientID + " already exists.");
        }
        
        if (bill != null){
            if (bill.getInvoices() == null || bill.getInvoices().isEmpty()){
                throw new BadRequestException("Billing Invoices is empty.");
            }
      
            LOGGER.info("New Bill Created Successfully! ");
            String message = billingDAO.addBill(bill, billingPatient);
            return Response.status(Response.Status.CREATED).entity(message).build();
        }else {
            throw new BadRequestException("Bill content is null or empty.");
        }
    }
    
    
    // Endpoint to add a new invoice to a bill.
    @POST
    @Path("/{billID}/addInvoice")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewInvoiceToBill(@PathParam("billID") int billID, Double newInvoice) {
        LOGGER.info("Adding a new invoice to Bill with ID: " +  billID + "... ");
        
        Billing bill = billingDAO.getBillById(billID);
        
        if (bill != null) {
            if (newInvoice == null || newInvoice <= 0){
                throw new BadRequestException("New Invoice amount is empty or null !");
            }
            
            LOGGER.info("Successfully added new invoice to Bill with ID " +  billID);
            String message = billingDAO.addNewInvoiceToBill(billID, newInvoice);
            return Response.status(Response.Status.CREATED).entity(message).build();
        } else {
            throw new NotFoundException("Bill with ID " + billID + " not found.");
        }
    }
    
    
    // Endpoint to add a new payment to a bill.
    @POST
    @Path("/{billID}/addPayment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewPaymentToBill(@PathParam("billID") int billID, Double newPayment) {
        LOGGER.info("Adding a new payment to Bill with ID: " +  billID + "... ");
        
        Billing bill = billingDAO.getBillById(billID);
        
        if (bill != null) {
            if (newPayment == null || newPayment <= 0){
                throw new BadRequestException("New Payment amount is empty or null !");
            }
            
            LOGGER.info("Successfully added new payment to Bill with ID " +  billID);
            String message = billingDAO.addNewPaymentToBill(billID, newPayment);
            return Response.status(Response.Status.CREATED).entity(message).build();
        } else {
            throw new NotFoundException("Bill with ID " + billID + " not found.");
        }
    }
    

    // Endpoint to update a billing record.
    @PUT
    @Path("/{billID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBill(@PathParam("billID") int billID, Billing updatedBill) {
        LOGGER.info("Updating Bill with ID: " + billID + "... ");
        
        if (updatedBill == null){
            throw new BadRequestException("Bill update request is null or empty.");
        }
        
        Billing existingBill = billingDAO.getBillById(billID);

        if (existingBill != null) {
            updatedBill.setBillID(billID);
            billingDAO.updateBill(updatedBill);
            
            LOGGER.info("Bill with ID " + billID + " updated successfully.");
            return Response.status(Response.Status.OK).entity("Bill with ID " + billID + " updated successfully.").build();
        }else{
            throw new NotFoundException("Bill with ID " + billID + " not found.");
        }
    }
    

    // Endpoint to delete a billing record.
    @DELETE
    @Path("/{billID}")
    public Response deleteBill(@PathParam("billID") int billID) {
        LOGGER.info("Deleting Bill with ID: " + billID + "... ");
        
        Billing existingBill = billingDAO.getBillById(billID);
        
        if (existingBill != null) {
            billingDAO.deleteBill(billID);
            
            LOGGER.info("Bill with ID " + billID + " Deleted successfully.");
            return Response.status(Response.Status.OK).entity("Bill with ID " + billID + " Deleted successfully.").build();
        }else{
            throw new NotFoundException("Bill with ID " + billID + " not found.");
        }
    }
}
