# Health System API

This project is an implementation of a robust and scalable Health System API designed to address the complex requirements of modern healthcare management. It serves as the foundational backbone upon which various healthcare applications and systems can be built, providing essential functionalities for patient management, appointment scheduling, medical record keeping, prescription management, and billing.

## Overview

This project is about creating a strong and flexible Health System API. The API is designed to meet the complex needs of modern healthcare management. It will help manage patients, schedule and search for appointments, keep medical records, manage prescriptions, and handle billing.

## System Entities

The API encompasses the following key entities:

1. **Person**: Represents a generic individual with attributes such as name, contact information, and address.
2. **Patient**: Extends the Person entity to include specific details relevant to patients, such as medical history and current health status.
3. **Doctor**: Extends the Person entity to include information about healthcare professionals, including their specialization and contact details.
4. **Appointment**: Represents scheduled appointments between patients and doctors, including details like date, time, and associated participants.
5. **Medical Record**: Holds comprehensive medical information about patients, covering diagnoses, treatments, and other relevant data.
6. **Prescription**: Records information about prescribed medications, including dosage, instructions, and duration.
7. **Billing**: Manages financial transactions related to healthcare services, including invoices, payments, and outstanding balances.

## Key Objectives

The project aims to achieve the following objectives:

1. **Model Classes**: Implement well-defined model classes with proper inheritance, constructors, and attributes for each entity.
2. **DAO Implementation**: Create Data Access Object (DAO) classes with CRUD methods for each entity, along with comprehensive exception handling and validation.
3. **Logging and Exception Handling**: Implement detailed logging and exception handling mechanisms to ensure robustness and auditability.
4. **RESTful Resource Implementation**: Develop resource classes following RESTful principles, with intuitive URIs, appropriate HTTP methods, and accurate implementations of CRUD operations.