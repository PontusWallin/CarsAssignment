## CarAssignment Backend Application

This is a simple backend application for a test assignment. It is built using Spring Boot and Java.
It connects to a local database and provides a REST API for the frontend application.


### Installation

1. Run the docker-compose.yml file to set up the database before running the application.
2. Open the project in your IDE and run the application.

The database will be pre-populated with some data and the application will run on port 8080.


### Usage

The application provides a REST API for the frontend application. It has the following endpoints:

- GET /cars - returns all cars
- GET /cars/{id} - returns a car with the given id
- GET /users - returns all users
- GET /users/{id} - returns a user with the given id
- GET /users/{id}/cars - returns all cars owned by the user with the given id

You can use Postman to test the endpoints or install the frontend application and use that to test the full application.
