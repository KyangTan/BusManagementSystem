# Bus Management System

A Spring Boot-based RESTful API service for managing bus operations, routes, and schedules.

## Features

- **Authentication & Authorization**
  - Role-based access control:
    - ADMIN: Full access to all endpoints
    - DRIVER: Can only create trip records
    - STUDENT/STAFF: Read-only access
  - Token-based authentication with Bearer scheme
  - Request validation and sanitization
  - Session management
  - Secure password hashing using SHA-256

- **Bus Management**
  - CRUD operations for buses
  - Track bus status and type
  - Internal/External bus classification

- **Bus Stop Management**
  - CRUD operations for bus stops
  - Fuzzy search functionality
  - Geolocation support (latitude/longitude)
  - Hidden/Visible status

- **Route Management**
  - Create and manage bus routes
  - Route stop sequencing
  - Polyline plotting for route visualization
  - Route distance calculation
  - Active/Inactive status
 
- **Route Trip Management**
  - Track bus trips on routes
  - Record trip start/end times
  - Driver assignment
  - Trip status tracking

- **Polyline Plot Management**
  - Store and manage route polylines
  - Validate polyline data format
  - Support for complex route visualization
  - Example format:
    ```json
    {
      "polylineData": "3.121214,101.653525;3.121099,101.653525;..."
    }
    ```

## Tech Stack

- Java 17
- Spring Boot 3.4.1
- Spring Data JPA
- MySQL/PostgreSQL
- Maven

## Getting Started

### Prerequisites

- JDK 17 or higher
- Maven
- MySQL or PostgreSQL database

### Installation

1. Clone the repository:
```
bash
git clone https://github.com/yourusername/BusManagementSystem.git
```

2. Configure database connection in `application.properties`:
properties
spring.datasource.url=jdbc:mysql://localhost:3306/bus_management
spring.datasource.username=your_username
spring.datasource.password=your_password

3. Build the project:
```
bash
mvn clean install
```
4. Run the application:
```
bash
mvn spring-boot:run
```
## API Documentation

### Authentication Endpoints
- POST /api/auth/login - Login user
- POST /api/auth/logout - Logout user
- POST /api/auth/register - Register new user
- GET /api/auth/me - Get current user details

### Bus Management Endpoints
- GET /api/bus - Get all buses
- GET /api/bus/{id} - Get bus by ID
- POST /api/bus - Create new bus
- PUT /api/bus/{id} - Update bus
- DELETE /api/bus/{id} - Delete bus

### Bus Stop Management Endpoints
- GET /api/bus-stop - Get all bus stops
- GET /api/bus-stop/{id} - Get bus stop by ID
- POST /api/bus-stop - Create new bus stop
- PUT /api/bus-stop/{id} - Update bus stop
- DELETE /api/bus-stop/{id} - Delete bus stop
- GET /api/bus-stop/search - Search bus stops

### Bus Route Management Endpoints
- GET /api/bus-route - Get all bus routes
- GET /api/bus-route/{id} - Get route by ID
- POST /api/bus-route - Create new route
- PUT /api/bus-route/{id} - Update route
- DELETE /api/bus-route/{id} - Delete route

### Route Stop Management Endpoints
- GET /api/route-stop - Get all route stops
- GET /api/route-stop/{id} - Get route stop by ID
- POST /api/route-stop - Create new route stop
- PUT /api/route-stop/{id} - Update route stop
- DELETE /api/route-stop/{id} - Delete route stop

### Polyline Plot Management Endpoints
- GET /api/polyline-plot - Get all polyline plots
- GET /api/polyline-plot/{id} - Get polyline plot by ID
- POST /api/polyline-plot - Create new polyline plot
- PUT /api/polyline-plot/{id} - Update polyline plot
- DELETE /api/polyline-plot/{id} - Delete polyline plot

## Security

- All POST, PUT, and DELETE operations require an ADMIN role
- The DRIVER role can only create trip records
- All GET operations are accessible to authenticated users
- Authentication token required for all protected endpoints

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Spring Boot team for the amazing framework
- Contributors and maintainers
