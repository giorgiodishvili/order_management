
# Order Management Service

## Introduction

The Order Management Service is a Spring Boot application designed to manage orders for users. It includes functionality for creating, reading, updating, and deleting orders, and it supports integration with external services. The service also includes comprehensive testing using Testcontainers, WireMock, and Spring Boot testing utilities.

## Features

- RESTful API for managing orders
- Integration with PostgreSQL using Testcontainers
- Comprehensive unit and integration tests
- Secure endpoints with Spring Security
- Logging and error handling with SLF4J and Logback
- Flyway for database migrations

## Technologies Used

- Spring Boot
- Spring Data JPA
- Spring Security
- Flyway
- Lombok
- Testcontainers
- WireMock
- JUnit 5
- Mockito
- PostgreSQL
- Docker

## Getting Started

### Prerequisites

- Java 17 or higher
- Docker

### Installation

1. Clone the repository:

```sh
git clone https://github.com/your-repo/order-management-service.git
cd order-management-service
```

2. Build the project:

```sh
./gradlew build
```

3. Run the application:

```sh
./gradlew bootRun
```

The application will start on `http://localhost:8080`.

### Running Tests

To run the tests, use the following command:

```sh
./gradlew test
```

## Usage

### API Endpoints

The following endpoints are available in the Order Management Service:

- `GET /orders`: Retrieve all orders
- `GET /orders/{id}`: Retrieve a specific order by ID
- `POST /orders`: Create a new order
- `DELETE /orders/{id}`: Delete an order by ID

### Example Order JSON

```json
{
  "userId": 1,
  "product": "Test Product",
  "quantity": 5,
  "price": 100.0,
  "status": "NEW"
}
```

### Security

Endpoints are secured with Spring Security. By default, the `/orders` endpoints require authentication.

### Configuration

#### Application Properties

The application can be configured using the `application.yaml` file located in the `src/main/resources` directory.

#### Database Migration

Flyway is used for database migrations. The migration scripts are located in the `src/main/resources/db/migration` directory.

### Logging

The application uses SLF4J with Logback for logging. The log configuration is defined in the `src/main/resources/logback-spring.xml` file.

## Development

### Code Structure

- `src/main/java`: Main source code
  - `com.gv.order.management`: Main package
    - `config`: Configuration classes
    - `controller`: REST controllers
    - `dto`: Data transfer objects
    - `filter`: Filters for request handling
    - `mapper`: Mappers for converting between entities and DTOs
    - `messaging`: Messaging components
    - `model`: Entity classes
    - `repository`: Data access layer
    - `service`: Service layer
- `src/test/java`: Test source code

### Creating Dummy Data

The `Order` class includes a static method `generateDummyOrder` for creating dummy data for testing purposes.

```java
Order dummyOrder = Order.generateDummyOrder();
```

### Access swagger on:
http://localhost:8085/swagger-ui/index.html

Endpoint for Registration or getting valid token: \
http://localhost:8080/swagger-ui/index.html#/authentication-controller/register

#### email should be valid
e.g. example@gmail.com
#### password password should be valid uppercase, lowercase, a number and special char
e.g. Password123!

#### Pass returned access_token to AUTHORIZATION header

## Contribution

Contributions are welcome! Please open an issue or submit a pull request with your changes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
