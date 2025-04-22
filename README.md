# Meeting Calendar APIs

A RESTful backend service built using Spring Boot, Java 17, and Spring Data JPA to power the Meeting Calendar App. It provides APIs for scheduling, managing, updating, and deleting meetings with database management using MySQL.

 ## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
   - [Implemented Features](#implemented-features)
3. [Technologies Used](#technologies-used)
4. [Set up](#set-up)
5. [Project Structure](#project-structure)
6. [Entity Relationships](#entity-relationships)
7. [API Endpoints](#api-endpoints)
8. [Swagger Documentation](#swagger-documentation)
9. [Future Enhancements](#future-enhancements)


## Overview

The Meeting Calendar Backend offers REST APIs to manage meeting data. It allows you to create, update, delete, and retrieve meetings. The system uses Spring Boot for quick development, Spring Data JPA to work with MySQL, and follows REST principles to send data to the frontend.


## Features

### Implemented Features

- **Create Meetings** : Accepts meeting details like title, date, time, and participants emails.

- **Edit Meetings** : Allows updating of meeting details by ID.

- **Delete Meetings** : Deletes a meeting using its unique identifier.

- **View Meetings** : Retrieves all or specific meetings via REST APIs.

- **Validation** : Ensures that the meeting data is correct and valid using annotations like @NotNull and @Email.

- **Many-to-Many Relationship** : A Meeting can have multiple Participants and a Participant can be part of multiple Meetings.



## Technologies Used

This project uses the following core technologies and libraries:

- **Java 17** : The version of Java used for developing backend.

- **Spring Boot** : A framework for building Java-based web applications and microservices.

- **Spring Data JPA**: Simplifies data access using repositories and abstracts the underlying database interaction.

- **Spring Web (REST)** : For building RESTful APIs.


- **MySQL** : A relational database used to persist meeting and participant data.

- **Lombok** : Reduces boilerplate code in the project (like getters, setters, constructors).

- **Swagger** : For documenting and testing the REST APIs.

- **JUnit & Mockito** : For testing the backend functionality and APIs.


## Set up

Follow the steps to run locally .

1. **Clone the repository**:
   - Run `git clone https://github.com/SayanaSurendra/MeetingCalendarAPI.git` to clone the project 
   - Then, navigate into the project directory using `cd MeetingCalendarAPI`.

2. **Install dependencies**:
   This project uses Maven for dependency management. To install required dependencies:
   - Run mvn install to install all required dependencies

3. **Configure MySQL Database**:
  Create a MySQL database and update the application.properties file with the correct database credentials. Example:
  ```
  spring.datasource.url=jdbc:mysql://localhost:3306/meeting_calendar
  spring.datasource.username=root
  spring.datasource.password=yourpassword
  ```

4. **Build and run**

## Project Structure

Project structure is as below.

```
MeetingCalendarAPI/
  README.md
   src/
    main/
      java/
         se/
         lexicon/
             meetingcalendarapi/
                  config/
                  controllers/
                  domain/
                     dto/
                     entity/
                 repository/
                 exception/
                 service/
                    impl/
            MeetingCalendarApplication.java
      resources/
        application.properties
    test/
  .gitignore
  pom.xml
  target/


```
- **`config/`** : Contains the configuration for Swagger.
- **`controller/`** : Contains the REST controllers APIs for handling requests related to meetings.

- **`dpmaon/dto/`** : Contains Data Transfer Objects (DTOs) used for API responses and requests.

- **`domain/entity/`** : Contains the JPA entities, such as Meeting and Participant.

- **`repository/`** : Contains the JPA repository interfaces for CRUD operations.

- **`service/`** : Contains interfaces for the application's business logic.
- **`service/impl`** : Implements the business logic defined in the service interfaces.

- **`exception/`** : Contains custom exception classes and handlers to manage errors.

- **MeetingCalendarApplication.java** : The main class that runs the Spring Boot application.
 - **application.properties** : Contains application-level configuration such as database connection and JPA settings.


## Entity Relationships

The application models a many-to-many relationship between Meeting and Participant. Each meeting can have multiple participants and each participant can be part of multiple meetings. This relationship is managed through a join table in the database using JPA annotations.

## API Endpoints

Meeting Calendar application exposes the following API endpoints:

- **POST** `/api/meetings`: Create a new meeting.

- **GET** `/api/meetings`: Retrieve all meetings..
 
 - **GET** `/api/meetings/{id}` : Retrieve a specific meeting by ID.

- **PUT** `/api/meetings/{id}` : Update an existing meeting.

- **DELETE** `/api/meetings/{id}` : Delete a meeting by ID.

## Swagger Documentation
The backend uses Swagger (Springdoc OpenAPI) to generate interactive API documentation.

#### Access Swagger UI:
Once the backend is running, navigate to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to view and test the API.


## Future Enhancements
Below are few features planned for future development to enhance the functionality :

- **User Authentication & Login** :Implement secure user authentication.

- **Role-Based Access Control** : Define user roles and access permissions for different types of users (Admin, User,Guest).

- **Notifications** : Send email notifications for upcoming meetings.

- **Participant Management**: Manage participant details.

- **Custom Query Filters** : Filter meetings by date, user ,title etc






