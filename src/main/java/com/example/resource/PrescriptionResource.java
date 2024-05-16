/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

/**
 *
 * @author chath
 */
import com.example.dao.PatientDAO;
import com.example.model.Prescription;
import com.example.dao.PrescriptionDAO;
import com.example.exception.NotFoundException;
import com.example.exception.BadRequestException;
import com.example.model.Patient;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/prescriptions")
public class PrescriptionResource {
    
    // Logger for logging information and errors
    private static final Logger LOGGER = LoggerFactory.getLogger(PrescriptionResource.class);
    
    // PrescriptionDAO instance for handling Prescription-related operations
    private PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
    
    // PatientDAO instance for handling Patient-related operations
    private static PatientDAO patientDAO = new PatientDAO();
    
    
    // Endpoint to retrieve all prescriptions.
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Prescription> getAllPrescriptions() {
        LOGGER.info("Getting All Prescriptions...");
        
        List<Prescription> prescriptions = prescriptionDAO.getAllPrescriptions();
        
        if (!prescriptions.isEmpty()){
            LOGGER.info("Fetched All Prescriptions Details ");
            return prescriptions;
        } else {
            throw new NotFoundException("No Prescriptions Found! "); // 404 status code for not found
        }
    }
    

    // Endpoint to retrieve a prescription by ID.
    @GET
    @Path("/{prescriptionID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Prescription getPrescriptionById(@PathParam("prescriptionID") int prescriptionID) {
        LOGGER.info("Getting Prescription by ID: " + prescriptionID + "... ");
        
        Prescription prescription = prescriptionDAO.getPrescriptionById(prescriptionID);
        
        if (prescription != null) {
            LOGGER.info("Fetched Details of Prescription with ID " + prescriptionID);
            return prescription;
        } else {
            throw new NotFoundException("Prescription with ID " + prescriptionID + " not found."); // 404 status code for not found
        }
    }
    
    
    // Endpoint to add a new prescription.
    @POST
    @Path("/patient/{patientID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPrescription(@PathParam("patientID") int patientID, Prescription prescription) {
        LOGGER.info("Creating a new Prescription... ");
        
        Patient prescriptionPatient = patientDAO.getPatientById(patientID);
        if (prescriptionPatient == null){
            throw new NotFoundException("Patient with ID " + patientID + " not found."); // 404 status code for not found
        }
        
        if (prescription != null){
            // Validation checks for mandatory fields
            if (prescription.getMedicationDetails() == null || prescription.getMedicationDetails().isEmpty()){
                throw new BadRequestException("Prescription Medication Detail is empty."); // 400 status code for bad request
            }
            if (prescription.getDosage() == null || prescription.getDosage().isEmpty()){
                throw new BadRequestException("Prescription Dosage is empty."); // 400 status code for bad request
            }
            if (prescription.getDuration() == null || prescription.getDuration().isEmpty()){
                throw new BadRequestException("Prescription Duration is empty."); // 400 status code for bad request
            }
            if (prescription.getInstructions() == null || prescription.getInstructions().isEmpty()){
                throw new BadRequestException("Prescription Instructions is empty."); // 400 status code for bad request
            }
            
            LOGGER.info("New Prescription Created Successfully! ");
            String message = prescriptionDAO.addPrescription(prescription, prescriptionPatient);
            return Response.status(Response.Status.CREATED).entity(message).build(); // 201 status code for created
        }else {
            throw new BadRequestException("Prescription content is null or empty."); // 400 status code for bad request
        }
    }
    

    // Endpoint to update an existing prescription.
    @PUT
    @Path("/{prescriptionID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePrescription(@PathParam("prescriptionID") int prescriptionID, Prescription updatedPrescription) {
        LOGGER.info("Updating Prescription with ID: " + prescriptionID + "... ");
        
        if (updatedPrescription == null){
            throw new BadRequestException("Prescription update request is null or empty."); // 400 status code for bad request
        }
        
        Prescription existingPrescription = prescriptionDAO.getPrescriptionById(prescriptionID);

        if (existingPrescription != null) {
            updatedPrescription.setPrescriptionID(prescriptionID);
            prescriptionDAO.updatePrescription(updatedPrescription);
            
            LOGGER.info("Prescription with ID " + prescriptionID + " updated successfully.");
            return Response.status(Response.Status.OK).entity("Prescription with ID " + prescriptionID + " updated successfully.").build(); // 200 status code for OK
        }else{
            throw new NotFoundException("Prescription with ID " + prescriptionID + " not found."); // 404 status code for not found
        }
    }
    

    // Endpoint to delete a prescription.
    @DELETE
    @Path("/{prescriptionID}")
    public Response deletePrescription(@PathParam("prescriptionID") int prescriptionID) {
        LOGGER.info("Deleting Prescription with ID: " + prescriptionID + "... ");
        
        Prescription existingPrescription = prescriptionDAO.getPrescriptionById(prescriptionID);
        
        if (existingPrescription != null){
            prescriptionDAO.deletePrescription(prescriptionID);
            
            LOGGER.info("Prescription with ID " + prescriptionID + " Deleted Successfully");
            return Response.status(Response.Status.OK).entity("Prescription ID " + prescriptionID + " Deleted Successfully").build(); // 200 status code for OK
        } else {
            throw new NotFoundException("Prescription with ID " + prescriptionID + " not found."); // 404 status code for not found
        }

    }
}
