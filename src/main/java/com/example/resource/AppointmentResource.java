/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

/**
 *
 * @author chath
 */
import com.example.model.Appointment;
import com.example.dao.AppointmentDAO;
import com.example.dao.DoctorDAO;
import com.example.dao.PatientDAO;
import com.example.exception.NotFoundException;
import com.example.exception.BadRequestException;
import com.example.model.Doctor;
import com.example.model.Patient;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/appointments")
public class AppointmentResource {
    
    // Logger for logging information and errors
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentResource.class);
    
    // AppointmentDAO instance for handling Appointment-related operations
    private AppointmentDAO appointmentDAO = new AppointmentDAO(); 
    
    // DoctorDAO instance for handling Doctor-related operations
    private static DoctorDAO doctorDAO = new DoctorDAO();
    
    // PatientDAO instance for handling Patient-related operations
    private static PatientDAO patientDAO = new PatientDAO();
    
    
    // Endpoint to retrieve all appointments.
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Appointment> getAllAppointments() {
        LOGGER.info("Getting All Appointments...");
        
        List<Appointment> appointments = appointmentDAO.getAllAppoinments();
        
        if (!appointments.isEmpty()){
            LOGGER.info("Fetched All Appointments Details ");
            return appointments;
        } else {
            throw new NotFoundException("No Appointments Found! "); // 404 status code for not found
        }
    }
    

    // Endpoint to retrieve an appointment by ID.
    @GET
    @Path("/{appointmentID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Appointment getAppointmentById(@PathParam("appointmentID") int appointmentID) {
        LOGGER.info("Getting Appointment by ID: " + appointmentID + "... ");
        
        Appointment appointment = appointmentDAO.getAppointmentById(appointmentID);
        
        if (appointment != null) {
            LOGGER.info("Fetched Details of Appointment with ID " + appointmentID);
            return appointment;
        } else {
            throw new NotFoundException("Appointment with ID " + appointmentID + " not found."); // 404 status code for not found
        }
    }
    
    // Endpoint to search for available appointments.
    @GET
    @Path("/search/date/{date}/doctor/{doctorID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointmentById(@PathParam("date") String date, @PathParam("doctorID") int doctorID) {
        LOGGER.info("Search Available Appointment for Date: " + date + " with Doctor: " + doctorID);
        
        Doctor isDoctor = doctorDAO.getDoctorById(doctorID);
        
        if (isDoctor != null){
            List<Appointment> availableAppointments = appointmentDAO.searchForAvailableAppointments(date, doctorID);
            
            if (!availableAppointments.isEmpty()){
                LOGGER.info("Fetched Available Appointment for Date: " + date + " with Doctor: " + doctorID);
                return Response.status(Response.Status.OK).entity(availableAppointments).build();  // 200 status code for OK
            } else{
                LOGGER.info("No Available Appointments for Date: " + date + " with Doctor ID: " + doctorID);
                return Response.status(Response.Status.OK).entity("No Available Appointments for Date: " + date + " with Doctor ID: " + doctorID).build(); // 200 status code for OK
            }
        } else{
            throw new NotFoundException("Doctor with ID " + doctorID + " not found."); // 404 status code for not found
        }
    }
    
    
    // Endpoint to add a new appointment.
    @POST
    @Path("/doctor/{doctorID}/patient/{patientID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAppointment(Appointment appointment, @PathParam("doctorID") int doctorID, @PathParam("patientID") int patientID) {
        LOGGER.info("Creating a new Appointment... ");
        
        Doctor appointmentDoctor = doctorDAO.getDoctorById(doctorID);
        if (appointmentDoctor == null){
            throw new NotFoundException("Doctor with ID " + doctorID + " not found."); // 404 status code for not found
        }
        
        Patient appointmentPatient = patientDAO.getPatientById(patientID);
        if (appointmentPatient == null){
            throw new NotFoundException("Patient with ID " + patientID + " not found."); // 404 status code for not found
        }
        
        if (appointment != null){
            // Validation checks for mandatory fields
            if (appointment.getDate() == null || appointment.getDate().isEmpty()){
                throw new BadRequestException("Appointment Date is empty."); // 400 status code for bad request
            }
            if (appointment.getTime()== null || appointment.getTime().isEmpty()){
                throw new BadRequestException("Appointment Time is empty."); // 400 status code for bad request
            }
            if (appointment.getRoomNo()== null || appointment.getRoomNo().isEmpty()){
                throw new BadRequestException("Appointment Room No is empty."); // 400 status code for bad request
            }
            
            LOGGER.info("New Appointment Created Successfully! ");
            String message = appointmentDAO.addAppointment(appointment, appointmentDoctor, appointmentPatient);
            return Response.status(Response.Status.CREATED).entity(message).build(); // 201 status code for created
        }else {
            throw new BadRequestException("Appointment content is null or empty."); // 400 status code for bad request
        }
    }
    
    
    // Endpoint to update an existing appointment.
    @PUT
    @Path("/{appointmentID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("appointmentID") int appointmentID, Appointment updatedAppointment) {
        LOGGER.info("Updating Appointment with ID: " + appointmentID + "... ");
        
        if (updatedAppointment == null){
           throw new BadRequestException("Appointment update request is null or empty."); // 400 status code for bad request
        }
        
        Appointment existingAppointment = appointmentDAO.getAppointmentById(appointmentID);

        if (existingAppointment != null) {
            updatedAppointment.setAppoinmentID(appointmentID);
            appointmentDAO.updateAppointment(updatedAppointment);
            
            LOGGER.info("Appointment with ID " + appointmentID + " updated successfully.");
            return Response.status(Response.Status.OK).entity("Appointment with ID " + appointmentID + " updated successfully.").build(); // 200 status code for OK
        }else{
            throw new NotFoundException("Appointment with ID " + appointmentID+ " not found."); // 404 status code for not found
        }
    }
    
    
    // Endpoint to delete an appointment.
    @DELETE
    @Path("/{appointmentID}")
    public Response deleteAppointment(@PathParam("appointmentID") int appointmentID) {
        LOGGER.info("Deleting Appointment with ID: " + appointmentID + "... ");
        
        Appointment existingAppointment = appointmentDAO.getAppointmentById(appointmentID);
        
        if (existingAppointment != null){
            appointmentDAO.deleteAppointment(appointmentID);
            
            LOGGER.info("Appointment with ID " + appointmentID + " Deleted Successfully");
            return Response.status(Response.Status.OK).entity("Appointment with ID " + appointmentID + " Deleted Successfully").build(); // 200 status code for OK
        } else {
            throw new NotFoundException("Appointment with ID " + appointmentID + " not found."); // 404 status code for not found
        }
    }
}
