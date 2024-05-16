/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

/**
 *
 * @author chath
 */
import com.example.model.Doctor;
import com.example.dao.DoctorDAO;
import com.example.dao.PersonDAO;
import com.example.exception.NotFoundException;
import com.example.exception.BadRequestException;
import com.example.model.Appointment;
import com.example.model.Person;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/doctors")
public class DoctorResource {
    // Logger for logging information and errors
    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorResource.class);
    
    // DoctorDAO instance for handling Doctor-related operations
    private static DoctorDAO doctorDAO = new DoctorDAO();  
    
    // PersonDAO instance for handling Person-related operations
    private static PersonDAO personDAO = new PersonDAO();
    
    
    // Method to get all doctors
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Doctor> getAllDoctors() {
        LOGGER.info("Getting All Doctors...");
        
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        
        if (!doctors.isEmpty()){
            LOGGER.info("Fetched All Doctors Details ");
            return doctors;
        } else {
            throw new NotFoundException("No Doctors Found! "); // 404 status code for not found
        }
    }
    

    // Method to get a doctor by ID
    @GET
    @Path("/{doctorID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Doctor getDoctorById(@PathParam("doctorID") int doctorID) {
        LOGGER.info("Getting Doctor by ID: " + doctorID + "... ");
        
        Doctor doctor = doctorDAO.getDoctorById(doctorID);
        
        if (doctor != null) {
            LOGGER.info("Fetched Details of Doctor with ID " + doctorID);
            return doctor;
        } else {
            throw new NotFoundException("Doctor with ID " + doctorID + " not found."); // 404 status code for not found
        }
    }
    
    
    // Method to add a new doctor
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDoctor(Doctor doctor) {
        LOGGER.info("Creating a new Doctor... ");
        
        if (doctor != null){
            // Validation checks for mandatory fields
            if (doctor.getFirstName() == null || doctor.getFirstName().isEmpty()){
                throw new BadRequestException("Doctor First Name is empty."); // 400 status code for bad request
            }
            if (doctor.getLastName() == null || doctor.getLastName().isEmpty()){
                throw new BadRequestException("Doctor Last Name is empty."); // 400 status code for bad request
            }
            if (doctor.getSpecialization() == null || doctor.getSpecialization().isEmpty()){
                throw new BadRequestException("Doctor Specialization is empty."); // 400 status code for bad request
            }
            if (doctor.isAvailability() != true && doctor.isAvailability() != false){
                throw new BadRequestException("Doctor Avalability is empty."); // 400 status code for bad request
            }
            if (doctor.getPhoneNo() == null || doctor.getPhoneNo().isEmpty()){
                throw new BadRequestException("Doctor Phone NO is empty."); // 400 status code for bad request
            }
            if (doctor.getAddress() == null || doctor.getAddress().isEmpty()){
                throw new BadRequestException("Doctor Address is empty."); // 400 status code for bad request
            }
            
            LOGGER.info("New Doctor Created Successfully! ");
            String message = doctorDAO.addDoctor(doctor);
            return Response.status(Response.Status.CREATED).entity(message).build(); // 201 status code for created
            
        } else {
            throw new BadRequestException("Doctor content is null or empty."); // 400 status code for bad request
        }
    }
    
    
    // Method to add a new doctor associated with an existing person
    @POST
    @Path("/person/{personID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDoctor(@PathParam("personID") int personID, Doctor doctor) {
        LOGGER.info("Creating a new Doctor with Person ID " + personID + "... ");
        
        // Retrieve the existing person
        Person person = personDAO.getPersonByID(personID);
        
        if (person != null){          
            Doctor isDoctor = doctorDAO.getDoctorByPersonID(personID);
            
            if (isDoctor == null){
                
                if (doctor != null){
                    // Validation checks for mandatory fields
                    if (doctor.getSpecialization() == null || doctor.getSpecialization().isEmpty()){
                        throw new BadRequestException("Doctor Specialization is empty."); // 400 status code for bad request
                    }
                    if (doctor.isAvailability() != true && doctor.isAvailability() != false){
                        throw new BadRequestException("Doctor Avalability is empty."); // 400 status code for bad request
                    }

                    LOGGER.info("New Doctor Created Successfully with Person ID " + personID + " !");
                    String message = doctorDAO.addDoctorByPerson(person, doctor);
                    return Response.status(Response.Status.CREATED).entity(message).build(); // 201 status code for created

                }else {
                    throw new BadRequestException("Doctor content is null or empty."); // 400 status code for bad request
                }
            } else{
                throw new BadRequestException("Person with ID " + personID + " is Already a Doctor");
            }
            
        }else{
            throw new NotFoundException("Person with ID " + personID + " not found."); // 404 status code for not found
        }
    }
    

    // Method to update an existing doctor
    @PUT
    @Path("/{doctorID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDoctor(@PathParam("doctorID") int doctorID, Doctor updatedDoctor) {
        LOGGER.info("Updating Doctor with ID: " + doctorID + "... ");
        
        if (updatedDoctor == null){
           throw new BadRequestException("Doctor update request is null or empty."); // 400 status code for bad request
        }

        Doctor existingDoctor = doctorDAO.getDoctorById(doctorID);
        
        if (existingDoctor != null) {
            updatedDoctor.setDoctorID(doctorID);
            doctorDAO.updateDoctor(updatedDoctor);
            
            LOGGER.info("Doctor with ID " + doctorID + " updated successfully.");
            return Response.status(Response.Status.OK).entity("Doctor with ID " + doctorID + " updated successfully.").build(); // 200 status code for OK
        } else{
            throw new NotFoundException("Doctor with ID " + doctorID + " not found."); // 404 status code for not found
        }
    }
    

    // Method to delete a doctor
    @DELETE
    @Path("/{doctorID}")
    public Response deleteDoctor(@PathParam("doctorID") int doctorID) {
        LOGGER.info("Deleting Doctor with ID: " + doctorID + "... ");
        
        Doctor existingDoctor = doctorDAO.getDoctorById(doctorID);
        
        if (existingDoctor != null) {
            doctorDAO.deleteDoctor(doctorID);
            
            LOGGER.info("Doctor with ID " + doctorID + " Deleted Successfully");
            return Response.status(Response.Status.OK).entity("Doctor with ID " + doctorID + " Deleted successfully.").build(); // 200 status code for OK
        } else {
            throw new NotFoundException("Doctor with ID " + doctorID + " not found."); // 404 status code for not found
        }
    }
    
    
    // Method to get appointments associated with a doctor
    @GET
    @Path("/{doctorID}/appointments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoctorAppointments(@PathParam("doctorID") int doctorID) {
        LOGGER.info("Getting Doctor Appointments by ID: " + doctorID + "... ");
        
        Doctor existingDoctor = doctorDAO.getDoctorById(doctorID);
        
        if (existingDoctor != null) {
            List<Appointment> doctorAppointments = doctorDAO.getDoctorAppointments(doctorID);
            
            if (!doctorAppointments.isEmpty()){
                LOGGER.info("Fetched Appointments of Doctor with ID " + doctorID);
                return Response.status(Response.Status.OK).entity(doctorAppointments).build(); // 200 status code for OK
            }else{
                LOGGER.warn("No scheduled appointments for Doctor with ID " + doctorID);
                return Response.status(Response.Status.OK).entity("No scheduled appointments for Doctor with ID " + doctorID).build(); // 200 status code for OK
            }
            
        } else {
            throw new NotFoundException("Doctor with ID " + doctorID + " not found."); // 404 status code for not found
        }
    }
}

