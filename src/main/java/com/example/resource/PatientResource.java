/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

/**
 *
 * @author chath
 */
import com.example.model.Patient;
import com.example.dao.PatientDAO;
import com.example.dao.PersonDAO;
import com.example.exception.NotFoundException;
import com.example.exception.BadRequestException;
import com.example.model.Appointment;
import com.example.model.Billing;
import com.example.model.MedicalRecord;
import com.example.model.Person;
import com.example.model.Prescription;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/patients")
public class PatientResource {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientResource.class);
    
    // Initializing PatientDAO and PersonDAO
    private static PatientDAO patientDAO = new PatientDAO();
    private static PersonDAO personDAO = new PersonDAO();
    
    // Endpoint to get all patients
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> getAllPatients() {
        LOGGER.info("Getting All Patients...");
        
        List<Patient> patients =  patientDAO.getAllPatients();
        
        if (!patients.isEmpty()){
            LOGGER.info("Fetched All Patients Details ");
            return patients;
        } else {
            throw new NotFoundException("No Patients Found! ");
        }
    }

    // Endpoint to get a patient by ID
    @GET
    @Path("/{patientID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Patient getPatientById(@PathParam("patientID") int patientID) {
        LOGGER.info("Getting Patient by ID: " + patientID + "... ");

        Patient patient = patientDAO.getPatientById(patientID);
        
        if (patient != null) {
            LOGGER.info("Fetched Details of Patient with ID " + patientID);
            return patient;
        } else {
            throw new NotFoundException("Patient with ID " + patientID + " not found.");
        }
    }
    
    // Endpoint to add a new patient
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String addPatient(Patient patient) {
        LOGGER.info("Creating a new Patient... ");
        
        if (patient != null){
            // Validating patient details
            if (patient.getFirstName() == null || patient.getFirstName().isEmpty()){
                throw new BadRequestException("Patient First Name is empty.");
            }
            if (patient.getLastName() == null || patient.getLastName().isEmpty()){
                throw new BadRequestException("Patient Last Name is empty.");
            }
            if (patient.getPhoneNo()== null || patient.getPhoneNo().isEmpty()){
                throw new BadRequestException("Patient Phone NO is empty.");
            }
            if (patient.getAddress()== null || patient.getAddress().isEmpty()){
                throw new BadRequestException("Patient Address is empty.");
            }
            if (patient.getAge() == null || patient.getAge().isEmpty()){
                    throw new BadRequestException("Patient Age is empty.");
                }
            if (patient.getGender()== null || patient.getGender().isEmpty()){
                throw new BadRequestException("Patient Gender is empty.");
            }
            if (patient.getHealthStatus()== null || patient.getHealthStatus().isEmpty()){
                throw new BadRequestException("Patient Health Status is empty.");
            }
            
            LOGGER.info("New Patient Created Successfully! ");
            return patientDAO.addPatient(patient);
            
        } else {
            throw new BadRequestException("Patient content is null or empty.");
        }
       
    }
    
    
    // Endpoint to add a patient using an existing person ID
    @POST
    @Path("/person/{personID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDoctor(@PathParam("personID") int personID, Patient patient) {
        LOGGER.info("Creating a new Patient with Person ID " + personID + "... ");
        
        // Getting person details by ID
        Person person = personDAO.getPersonByID(personID);
        
        if (person != null){
            // Checking if the person is already a patient
            Patient isPatient = patientDAO.getPatientByPersonID(personID);
            
            if (isPatient == null){
                
                if (patient != null){
                
                    if (patient.getAge() == null || patient.getAge().isEmpty()){
                        throw new BadRequestException("Patient Age is empty.");
                    }
                    if (patient.getGender()== null || patient.getGender().isEmpty()){
                        throw new BadRequestException("Patient Gender is empty.");
                    }
                    if (patient.getHealthStatus()== null || patient.getHealthStatus().isEmpty()){
                        throw new BadRequestException("Patient HealthStatus is empty.");
                    }
                    LOGGER.info("New Patient Created Successfully with Person ID " + personID + " !");
                    
                    // Adding patient using existing person details
                    String message = patientDAO.addPatientByPerson(person, patient);
                    return Response.status(Response.Status.CREATED).entity(message).build();

                }else {
                    throw new BadRequestException("Patient content is null or empty."); 
                }
            }else {
                throw new BadRequestException("Person with ID " + personID + " is Already a Patient"); 
            }
            
        }else{
            throw new NotFoundException("Person with ID " + personID + " not found.");
        }
    }

    // Endpoint to update patient details
    @PUT
    @Path("/{patientID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updatePatient(@PathParam("patientID") int patientID, Patient updatedPatient) {
        LOGGER.info("Updating Patient with ID: " + patientID + "... ");
        
        if (updatedPatient == null){
           throw new BadRequestException("Patient update request is null or empty.");
        }
        
        // Getting existing patient by ID
        Patient existingPatient = patientDAO.getPatientById(patientID);

        if (existingPatient != null) {
            updatedPatient.setPatientID(patientID);
            // Updating patient details
            patientDAO.updatePatient(updatedPatient);
            
            LOGGER.info("Patient with ID " + patientID + " updated successfully.");
            return "Patient with ID " + patientID + " updated successfully.";
        }else{
            throw new NotFoundException("Patient with ID " + patientID + " not found.");
        }
    }

    // Endpoint to delete a patient by ID
    @DELETE
    @Path("/{patientID}")
    public String deletePatient(@PathParam("patientID") int patientID) {
        LOGGER.info("Deleting Patient with ID: " + patientID + "... ");
        
        // Getting existing patient by ID
        Patient exisitingPatient = patientDAO.getPatientById(patientID);
        
        if (exisitingPatient != null) {
            // Deleting patient
            patientDAO.deletePatient(patientID);
            
            LOGGER.info("Patient with ID " + patientID + " Deleted Successfully");
            return "Patient with ID " + patientID + " Deleted Successfully";
        } else {
            throw new NotFoundException("Patient with ID " + patientID + " not found.");
        }
    }
    
    // Endpoint to get appointments of a patient by ID
    @GET
    @Path("/{patientID}/appointments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientAppointments(@PathParam("patientID") int patientID) {
        LOGGER.info("Getting Patient Appointments by ID: " + patientID + "... ");
        
        // Getting existing patient by ID
        Patient existingPatient = patientDAO.getPatientById(patientID);
        
        if (existingPatient != null) {
            // Getting appointments of the patient
            List<Appointment> patientAppointments = patientDAO.getPatientAppointments(patientID);
            
            if (!patientAppointments.isEmpty()){
                LOGGER.info("Fetched Appointments of Patient with ID " + patientID);
                return Response.status(Response.Status.OK).entity(patientAppointments).build(); 
            }else{
                LOGGER.warn("No scheduled appointments for Patient with ID " + patientID);
                return Response.status(Response.Status.OK).entity("No scheduled appointments for Patient with ID " + patientID).build();
            }
            
        } else {
            throw new NotFoundException("Patient with ID " + patientID + " not found."); 
        }
    }
    
    // Endpoint to get medical records of a patient by ID
    @GET
    @Path("/{patientID}/medicalRecords")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientMedicalRecords(@PathParam("patientID") int patientID) {
        LOGGER.info("Getting Patient MedicalRecords by ID: " + patientID + "... ");
        
        // Getting existing patient by ID
        Patient existingPatient = patientDAO.getPatientById(patientID);
        
        if (existingPatient != null) {
            // Getting medical records of the patient
            List<MedicalRecord> patientMedicalRecords = patientDAO.getPatientMedicalRecords(patientID);
            
            if (!patientMedicalRecords.isEmpty()){
                LOGGER.info("Fetched MedicalRecords of Patient with ID " + patientID);
                return Response.status(Response.Status.OK).entity(patientMedicalRecords).build(); 
            }else{
                LOGGER.warn("No MedicalRecords for Patient with ID " + patientID);
                return Response.status(Response.Status.OK).entity("No MedicalRecord for Patient with ID " + patientID).build();
            }
            
        } else {
            throw new NotFoundException("Patient with ID " + patientID + " not found."); 
        }
    }
    
    // Endpoint to get prescriptions of a patient by ID
    @GET
    @Path("/{patientID}/prescriptions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientPrescriptions(@PathParam("patientID") int patientID) {
        LOGGER.info("Getting Patient Prescriptions by ID: " + patientID + "... ");
        
        // Getting existing patient by ID
        Patient existingPatient = patientDAO.getPatientById(patientID);
        
        if (existingPatient != null) {
            // Getting prescriptions of the patient
            List<Prescription> patientPrescriptions = patientDAO.getPatientPrescriptions(patientID);
            
            if (!patientPrescriptions.isEmpty()){
                LOGGER.info("Fetched Prescriptions of Patient with ID " + patientID);
                return Response.status(Response.Status.OK).entity(patientPrescriptions).build(); 
            }else{
                LOGGER.warn("No Prescriptions for Patient with ID " + patientID);
                return Response.status(Response.Status.OK).entity("No Prescriptions for Patient with ID " + patientID).build();
            }
            
        } else {
            throw new NotFoundException("Patient with ID " + patientID + " not found."); 
        }
    }
    
    // Endpoint to get billings of a patient by ID
    @GET
    @Path("/{patientID}/billings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientBillings(@PathParam("patientID") int patientID) {
        LOGGER.info("Getting Patient Billings by ID: " + patientID + "... ");
        
        // Getting existing patient by ID
        Patient existingPatient = patientDAO.getPatientById(patientID);
        
        if (existingPatient != null) {
            // Getting billings of the patient
            List<Billing> patientBillings = patientDAO.getPatientBillings(patientID);
            
            if (!patientBillings.isEmpty()){
                LOGGER.info("Fetched Billings of Patient with ID " + patientID);
                return Response.status(Response.Status.OK).entity(patientBillings).build(); 
            }else{
                LOGGER.warn("No Billings for Patient with ID " + patientID);
                return Response.status(Response.Status.OK).entity("No Billings for Patient with ID " + patientID).build();
            }
            
        } else {
            throw new NotFoundException("Patient with ID " + patientID + " not found."); 
        }
    }
}

