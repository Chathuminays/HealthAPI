/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

/**
 *
 * @author chath
 */
import com.example.model.Person;
import com.example.dao.PersonDAO;
import com.example.exception.NotFoundException;
import com.example.exception.BadRequestException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/person")
public class PersonResource {
    
    // Logger for logging information and errors
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonResource.class);
    
    // Data access object for handling Person-related operations
    private static PersonDAO personDAO = new PersonDAO(); 
    
    // Method to get all people
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPeople() {
        LOGGER.info("Getting All People... ");
        
        List<Person> people = personDAO.getAllPeople();
        
        if (!people.isEmpty()){
            LOGGER.info("Fetched All Person Details ");
            return people;
        } else {
            throw new NotFoundException("No People Found! "); // 404 status code for not found
        }
    }

    // Method to get a person by ID
    @GET
    @Path("/{personID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPersonByName(@PathParam("personID") int personID) {
        LOGGER.info("Getting Person by ID: " + personID + "... ");
        
        Person person = personDAO.getPersonByID(personID);
        if (person != null) {
            LOGGER.info("Fetched Details of Person with ID " + personID);
            return person;
        } else {
            throw new NotFoundException("Person with ID " + personID + " not found."); // 404 status code for not found
        }
    }
    
    // Method to add a new person
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(Person person) {
        LOGGER.info("Creating a new Person... ");
        
        if (person != null){
            
            // Validation checks for mandatory fields
            if (person.getFirstName() == null || person.getFirstName().isEmpty()){
                throw new BadRequestException("Person First Name is empty."); // 400 status code for bad request
            }
            if (person.getLastName() == null || person.getLastName().isEmpty()){
                throw new BadRequestException("Person Last Name is empty."); // 400 status code for bad request
            }
            if (person.getPhoneNo() == null || person.getPhoneNo().isEmpty()){
                throw new BadRequestException("Person PhoneNo is empty."); // 400 status code for bad request
            }
            if (person.getAddress() == null || person.getAddress().isEmpty()){
                throw new BadRequestException("Person Address is empty."); // 400 status code for bad request
            }
            
            LOGGER.info("New Person Created Successfully! ");
            String message = personDAO.addPerson(person);
            return Response.status(Response.Status.CREATED).entity(message).build(); // 201 status code for created
        } else {
            throw new BadRequestException("Person content is null or empty."); // 400 status code for bad request
        }
    }

    // Method to update an existing person
    @PUT
    @Path("/{personID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("personID") int personID, Person updatedPerson) {
        LOGGER.info("Updating Person with ID: " + personID + "... ");
        
        if (updatedPerson == null){
           throw new BadRequestException("Person update request is null or empty."); // 400 status code for bad request
        } else{
            // Retrieve existing person
            Person existingPerson = personDAO.getPersonByID(personID);

            if (existingPerson != null) {
                // Set person ID and update details
                updatedPerson.setPersonID(personID);
                personDAO.updatePerson(updatedPerson);
                
                LOGGER.info("Person with ID " + personID + " updated successfully.");
                return Response.status(Response.Status.OK).entity("Person with ID " + personID + " updated successfully.").build(); // 200 status code for OK
            } else {
                throw new NotFoundException("Person with ID " + personID + " not found."); // 404 status code for not found
            }
        }
    }

    // Method to delete a person
    @DELETE
    @Path("/{personID}")
    public Response deletePerson(@PathParam("personID") int personID) {
        LOGGER.info("Deleting Person with ID: " + personID + "... ");
        
        // Retrieve existing person
        Person existingPerson = personDAO.getPersonByID(personID);
        
        if (existingPerson != null) {
            // Delete the person
            personDAO.deletePerson(personID);
            
            LOGGER.info("Person with ID " + personID + " Deleted Successfully");
            return Response.status(Response.Status.OK).entity("Person with ID " + personID + " Deleted Successfully").build(); // 200 status code for OK
        } else {
            throw new NotFoundException("Person with ID " + personID + " not found."); // 404 status code for not found
        }
    }
}


