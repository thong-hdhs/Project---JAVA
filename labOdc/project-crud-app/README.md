# Project CRUD Application

## Overview
The Project CRUD Application is a Spring Boot-based application that provides a RESTful API for managing projects, project teams, project mentors, mentor invitations, milestones, and project change requests. This application follows a code-first approach and is structured into various layers including models, DTOs, responses, services, and controllers.

## Features
- **CRUD Operations**: Create, Read, Update, and Delete operations for all entities.
- **API Documentation**: Integrated Swagger for easy API documentation and testing.
- **Exception Handling**: Global exception handling to manage errors gracefully.
- **DTOs**: Data Transfer Objects for safe data handling between client and server.

## Project Structure
```
project-crud-app
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── projectcrud
│   │   │               ├── api
│   │   │               ├── config
│   │   │               ├── controller
│   │   │               ├── dto
│   │   │               ├── exception
│   │   │               ├── model
│   │   │               ├── repository
│   │   │               ├── response
│   │   │               ├── service
│   │   │               └── serviceimpl
│   │   └── resources
│   └── test
└── README.md
```

## Getting Started
1. **Clone the Repository**: 
   ```
   git clone <repository-url>
   cd project-crud-app
   ```

2. **Build the Project**: 
   ```
   ./mvnw clean install
   ```

3. **Run the Application**: 
   ```
   ./mvnw spring-boot:run
   ```

4. **Access the API**: Open your browser and navigate to `http://localhost:8080/api-docs` to view the API documentation.

## Technologies Used
- Java
- Spring Boot
- Maven
- Swagger
- JPA/Hibernate

## Contributing
Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.