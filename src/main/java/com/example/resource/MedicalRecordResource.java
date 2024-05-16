/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

/**
 *
 * @author chath
 */
import com.example.model.MedicalRecord;
import com.example.dao.MedicalRecordDAO;
import com.example.dao.PatientDAO;
import com.example.model.Patient;
import com.example.exception.NotFoundException;
import com.example.exception.BadRequestException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/medicalRecords")
public class MedicalRecordResource {
    
    // Logger for logging information and errors
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordResource.class);
    
    // MedicalRecordDAO instance for handling MedicalRecord-related operations
    private MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();
    
    // PatientDAO instance for handling Patient-related operations
    private static PatientDAO patientDAO = new PatientDAO();
    
    
    // Method to get all medical records
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MedicalRecord> getAllMedicalRecords() {
        LOGGER.info("Getting All Medical Records...");
        
        List<MedicalRecord> medicalRecords =  medicalRecordDAO.getAllMedicalRecords();
        
        if (!medicalRecords.isEmpty()){
            LOGGER.info("Fetched All Medical Records Details ");
            return medicalRecords;
        } else {
            throw new NotFoundException("No Medical Records Found! "); // 404 status code for not found
        }
    }
    

    // Method to get a medical record by ID
    @GET
    @Path("/{recordID}")
    @Produces(MediaType.APPLICATION_JSON)
    public MedicalRecord getMedicalRecordById(@PathParam("recordID") int recordID) {
        LOGGER.info("Getting Medical Record by ID: " +  recordID + "... ");
        
        MedicalRecord record = medicalRecordDAO.getMedicalRecordById(recordID);
        
        if (record != null) {
            LOGGER.info("Fetched Details of Medical Record with ID " + recordID);
            return record;
        } else {
            throw new NotFoundException("Medical Record with ID " + recordID + " not found."); // 404 status code for not found
        }
    }
    
    
    // Method to add a new medical record
    @POST
    @Path("/patient/{patientID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMedicalRecord(@PathParam("patientID") int patientID, MedicalRecord record) {
        LOGGER.info("Creating a new Medical Record... ");
        
        Patient appointmentPatient = patientDAO.getPatientById(patientID);
        if (appointmentPatient == null){
            throw new NotFoundException("Patient with ID " + patientID + " not found."); // 404 status code for not found
        }
        
        if (record != null){
            // Validation checks for mandatory fields
            if (record.getDiagnosis() == null || record.getDiagnosis().isEmpty()){
                throw new BadRequestException("Medical Record Diagnosis is empty."); // 400 status code for bad request
            }
            if (record.getTreatments() == null || record.getTreatments().isEmpty()){
                throw new BadRequestException("Medical Record Treatments is empty."); // 400 status code for bad request
            }
            if (record.getAdditionalDetails() == null || record.getAdditionalDetails().isEmpty()){
                throw new BadRequestException("Medical Record Additional Details is empty."); // 400 status code for bad request
            }
            
            LOGGER.info("New Medical Record Created Successfully! ");
            String message = medicalRecordDAO.addMedicalRecord(record, appointmentPatient);
            return Response.status(Response.Status.CREATED).entity(message).build(); // 201 status code for created
        }else {
            throw new BadRequestException("Appointment content is null or empty."); // 400 status code for bad request
        }
    }
    

    // Method to update an existing medical record
    @PUT
    @Path("/{recordID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMedicalRecord(@PathParam("recordID") int recordID, MedicalRecord updatedRecord) {
        LOGGER.info("Updating Medical Record with ID: " + recordID + "... ");
        
        if (updatedRecord == null){
            throw new BadRequestException("Medical Record update request is null or empty."); // 400 status code for bad request
        }
        
        MedicalRecord existingRecord = medicalRecordDAO.getMedicalRecordById(recordID);

        if (existingRecord != null) {
            updatedRecord.setRecordID(recordID);
            medicalRecordDAO.updateMedicalRecord(updatedRecord);
            
            LOGGER.info("Medical Record with ID " + recordID + " updated successfully.");
            return Response.status(Response.Status.OK).entity("Medical Record with ID " + recordID + " updated successfully.").build(); // 200 status code for OK
        }else{
            throw new NotFoundException("Medical Record with ID " + recordID + " not found."); // 404 status code for not found
        }
    }

    
    // Method to delete a medical record
    @DELETE
    @Path("/{recordID}")
    public Response deleteMedicalRecord(@PathParam("recordID") int recordID) {
        LOGGER.info("Deleting Medical Record with ID: " + recordID + "... ");
        
        MedicalRecord existingRecord = medicalRecordDAO.getMedicalRecordById(recordID);
        
        if (existingRecord != null){
            medicalRecordDAO.deleteMedicalRecord(recordID);
            
            LOGGER.info("MedicalRecord with ID " + recordID + " Deleted Successfully");
            return Response.status(Response.Status.OK).entity("MedicalRecord with ID " + recordID + " Deleted Successfully").build(); // 200 status code for OK
        } else {
            throw new NotFoundException("MedicalRecord with ID " + recordID + " not found."); // 404 status code for not found
        }
    }
}
