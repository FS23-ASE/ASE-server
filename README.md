# Group-ASE-server

This is the server-side code for a Book E-Commerce web application developed by Group ASE as part of the FS23 Advanced Software Engineering course.


## Technologies Used

The project is built using the following technologies:

Java

Spring Boot

Gradle

RESTful web services

H2Database (for data storage)

Docker (for containerization and deployment)

## Motivation

1.Convenience: A second-hand book trading website offers readers a convenient platform to buy and sell books online. This means they can search, browse, and purchase used books anytime, anywhere, without the need to visit physical bookstores or markets.

2.Cost savings: Buying used books is more affordable than purchasing brand new ones, especially for students, bookworms, or individuals on a tight budget. A second-hand book trading website can help readers find books at more reasonable prices, saving them money on their book purchases.

3.Sustainable development: By promoting the reuse of second-hand books, a second-hand book trading website can contribute to sustainable development. It encourages readers to pass on books they no longer need to others, reducing waste and resource consumption.

4.Expanded reading choices: A second-hand book trading website provides readers with a wider range of book options. Readers can find out-of-print books, rare books, or specific academic materials that may be difficult to locate in traditional bookstores or markets.

5.Community engagement: A second-hand book trading website can serve as a community platform where readers can connect, share their experiences, provide book reviews, and offer recommendations. This community engagement fosters interaction and collaboration among readers.

6.Stimulating cultural consumption: Building a second-hand book trading website can stimulate cultural consumption and encourage reading habits. It helps cultivate more readers and provides a sustainable market for the cultural industry.

## Architecture
This project follows a client-server architecture, where the server is responsible for handling user requests and providing appropriate responses. The server is built using Java and Spring Boot, which provides a robust framework for building web applications. Gradle is used to manage dependencies and build the project. RESTful web services are used to communicate between the client and server.It follows a three-tier architecture and consists of the following modules:

1.Controller Layer: Responsible for handling HTTP requests and responses. It routes user requests to the corresponding service layer for processing and returns the appropriate response. The controller layer interacts with DTOs to receive request payloads and send response payloads, ensuring proper data transfer between the frontend and backend.

2.Service Layer: Implements the business logic and handles specific business requirements such as user management, book management, cart management, and order management. The service layer interacts with DTOs to receive data from the controller layer, performs necessary operations or validations, and returns data back to the controller layer.

3.Data Access Layer: Handles interactions with the database. It provides operations for creating, reading, updating, and deleting data. The data access layer communicates with the service layer using entities or DTOs, retrieving or persisting data as required.

## REST Specification


<img width="621" alt="restful1" src="https://github.com/FS23-ASE/ASE-server/assets/116545176/fe617005-9bd0-4c7b-bf06-e631ee1c9c21">
<img width="622" alt="restful2" src="https://github.com/FS23-ASE/ASE-server/assets/116545176/45c56fbb-8e0f-4c3e-8be7-a75c4d11bd14">


## Docker
The setup relies on the Docker.Please make sure you have downloaded it.Also,you can download docker compose using homebrew
by running: brew install docker-compose

## How to run it

The structure of this project is:

- Root

  |- ASE-client
  
  |- ASE-server
  
  |- docker-compose.yml
  

### LocalStack

Here are the steps if you want to test this application using LocalStack.

Run LocalStack S3 Container:
Execute the following command to start the LocalStack S3 container:

```docker run --rm -it -p 4566:4566 -p 8888:8080 localstack/localstack```

This command will download the LocalStack Docker image (if not already downloaded) and start the container. The S3 service will be available at http://localhost:4566.

Configure AWS CLI:
If you don't have the AWS Command Line Interface (CLI) installed, download and install it from the official AWS CLI website.
Execute the following command to configure the AWS CLI with LocalStack credentials:

```aws configure --profile localstack```

You will be prompted to enter the Access Key ID, Secret Access Key, and region. For LocalStack, you can use any dummy values as the actual AWS credentials are not required. However, make sure to provide valid input to proceed with the configuration.

Create an S3 Bucket:
Execute the following command to create an S3 bucket using LocalStack:

```aws --endpoint-url=http://localhost:4566 s3 mb s3://images```

This command will create an S3 bucket named "images" in LocalStack.

Also add your_access_key and your_secret_key in the docker-compose.yml file.

### Deploy

To quickly build and test the docker images, a docker-compose.yml file is in the root directory. 


In the server folder, run:

```bash
./gradlew bootJar
```

In the root directory, start the deployment with:


```bash
docker compose up
```
To stop them, run:
```bash
docker compose down
```

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


## Organization
[ORGANIZATION.md](ORGANIZATION.md)



## Testing and continuous integration
We have implemented a continuous integration (CI) flow in our project and utilized Travis CI and SonarCloud as essential tools for automated build, testing, and code quality analysis.

Travis CI is a cloud-based CI tool that integrates with version control systems like GitHub, enabling automatic triggering of the build process. We stored the project's code in a GitHub repository and employed Travis CI to monitor changes in the repository. Whenever new code commits or pushes occur, Travis CI automatically triggers the build process.

During the build stage, Travis CI performs the build and compilation based on the project's configuration file (e.g., .travis.yml). We specified Gradle as the build tool in the configuration file, defining the necessary dependencies and build tasks. Travis CI automatically downloads the required dependencies and executes the build command. If the build is successful, it generates an executable application or software package.

Subsequently, Travis CI runs automated tests. We have developed unit tests within the project. Travis CI executes these tests according to the instructions in the configuration file and collects the test results. If the tests pass, it provides feedback and proceeds to the next stage.

In the continuous integration flow, code quality analysis is crucial. To achieve static code analysis, We employed SonarCloud. SonarCloud performs static analysis of the code, detecting potential issues and violations such as code quality, security vulnerabilities, and technical debt. It offers detailed code quality metrics and suggestions to help us improve the code's quality and maintainability.

The integration of Travis CI and SonarCloud allows us to continuously monitor and enhance code quality. After a successful build and passing tests, Travis CI sends the build report and test results to SonarCloud for code quality analysis. SonarCloud generates comprehensive reports and provides a visual dashboard to track code quality and potential issues.

By utilizing Travis CI and SonarCloud, we have effectively implemented automated build, testing, and code quality analysis within the continuous integration flow. This enables us to develop and deliver high-quality software more rapidly and efficiently. Additionally, the continuous integration flow establishes a solid foundation for team collaboration and project management.

## Database management

ORM Framework: An ORM (Object-Relational Mapping) framework,Spring Data JPA, is used to simplify database operations and handle the mapping and persistence of objects to the database.

Relational Database: User, book, cart, order and contact form data are stored in a relational database.

1.User:
id[long], username[string], email[string], password[string], address[string]

2.Book:
id[long], name[string], author[string], description[string], publisher[string], status[bool], image[BLOB], seller_id[long], buyer_id[long]

3.Cart:
id[long],books[List<Book>],quantity[int],prices[double],userId[long]

4.Order:
id[long], buyerId[long],sellerId[long], books[List{Book}], amount[double], date[Date], status[String (CANCELLED, SHIPPED, RECEIVED, PAID)]

5.Contact Form:
id[long], sender[long], accepter[long], orderId[long], msg[String]





