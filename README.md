Health System API
This project is an implementation of a robust and scalable Health System API designed to address the complex requirements of modern healthcare management. It serves as the foundational backbone upon which various healthcare applications and systems can be built, providing essential functionalities for patient management, appointment scheduling, medical record keeping, prescription management, and billing.

Overview
The Health System API project presents a comprehensive solution for managing healthcare data and operations in a digital environment. It is designed to align with specific learning goals, focusing on REST API design and implementation using JAX-RS, a Java API for RESTful web services.

System Entities
The API encompasses the following key entities:

Person: Represents a generic individual with attributes such as name, contact information, and address.
Patient: Extends the Person entity to include specific details relevant to patients, such as medical history and current health status.
Doctor: Extends the Person entity to include information about healthcare professionals, including their specialization and contact details.
Appointment: Represents scheduled appointments between patients and doctors, including details like date, time, and associated participants.
Medical Record: Holds comprehensive medical information about patients, covering diagnoses, treatments, and other relevant data.
Prescription: Records information about prescribed medications, including dosage, instructions, and duration.
Billing: Manages financial transactions related to healthcare services, including invoices, payments, and outstanding balances.
Key Objectives
The project aims to achieve the following objectives:

Model Classes: Implement well-defined model classes with proper inheritance, constructors, and attributes for each entity.
DAO Implementation: Create Data Access Object (DAO) classes with CRUD methods for each entity, along with comprehensive exception handling and validation.
Logging and Exception Handling: Implement detailed logging and exception handling mechanisms to ensure robustness and auditability.
RESTful Resource Implementation: Develop resource classes following RESTful principles, with intuitive URIs, appropriate HTTP methods, and accurate implementations of CRUD operations.
