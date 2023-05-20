# Group-ASE-server

This is the server-side code for a Book E-Commerce web application developed by Group ASE as part of the FS23 Advanced Software Engineering course.

## Technologies Used

The project is built using the following technologies:

Java

Spring Boot

Gradle

RESTful web services

MySQL (for data storage)

Docker (for containerization and deployment)

## Architecture
This project follows a client-server architecture, where the server is responsible for handling user requests and providing appropriate responses. The server is built using Java and Spring Boot, which provides a robust framework for building web applications. Gradle is used to manage dependencies and build the project. RESTful web services are used to communicate between the client and server.It follows a three-tier architecture and consists of the following modules:

1.Controller Layer: Responsible for handling HTTP requests and responses. It routes user requests to the corresponding service layer for processing and returns the appropriate response. The controller layer interacts with DTOs to receive request payloads and send response payloads, ensuring proper data transfer between the frontend and backend.

2.Service Layer: Implements the business logic and handles specific business requirements such as user management, book management, cart management, and order management. The service layer interacts with DTOs to receive data from the controller layer, performs necessary operations or validations, and returns data back to the controller layer.

3.Data Access Layer: Handles interactions with the database. It provides operations for creating, reading, updating, and deleting data. The data access layer communicates with the service layer using entities or DTOs, retrieving or persisting data as required.

## REST Specification


<img width="621" alt="restful1" src="https://github.com/FS23-ASE/ASE-server/assets/116545176/fe617005-9bd0-4c7b-bf06-e631ee1c9c21">
<img width="622" alt="restful2" src="https://github.com/FS23-ASE/ASE-server/assets/116545176/45c56fbb-8e0f-4c3e-8be7-a75c4d11bd14">




## Set up local database and  link it to server program
First download and install Mysql: https://dev.mysql.com/downloads/mysql/  

Then log in Mysql in the terminal by entering: mysql -uroot -p

After enter the password, enter: CREATE DATABASE ase;

And you will have a database named ase which will store the book and user data

You can link the database to the program easily by just enter your username and password in the /src/main/resources/application.properties

After that the program is ready for use

## Building with Gradle
-   macOS: `./gradlew`
-   Linux: `./gradlew`
-   Windows: `./gradlew.bat`

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

### Test

```bash
./gradlew test
```




