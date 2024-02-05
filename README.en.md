
# Didi

## Description

This project is an online taxi service backend, built on a microservice architecture. It aims to provide a scalable and efficient platform for taxi service operations, including ride booking, driver management, and billing.

## Software Architecture

The system employs a microservice architecture, comprising three key services: Bill Management, Driver Management, and Passenger Management. Each service operates with its own dedicated database to ensure scalability and independence. The architecture is divided into three layers for each microservice: 

- **Controller Layer**: Serves as the entry point for handling requests.
- **Service Layer**: Implements the business logic.
- **Data Access Layer**: Manages database operations.

**Services Overview**:

- **Passenger Management**: Manages passenger requests, including taxi booking and cancellation.
- **Driver Management**: Handles driver activities, such as order acceptance, status updates, and bill reviews.
- **Bill Management**: Processes billing operations, including order receipt from passengers and assignment to drivers.

## Tech Stack

- **Eureka**: Utilized for service discovery and management.
- **MyBatis**: Employed for database access and operations.
- **OpenFeign**: Applied for simplifying HTTP remote calls between microservices.
- **Spring Cloud Gateway**: Used to route requests to specific services.
- **Spring MVC**: Utilized as the framework.
- **Jwt**: to verify users.

## Illustration of Some Functions

### 1. Calling a Taxi
When a passenger attempts to call a taxi, they send a request containing the destination and starting point. This request is dispatched to the PASSENGER-MANAGEMENT service by the gateway. Information including the destination, starting point, and passenger ID is then sent to an instance of the BILL-MANAGEMENT service via remote calls. BILL-MANAGEMENT maintains a queue of orders awaiting driver acceptance. Upon receiving a taxi request, it creates a Bill instance and enqueues it, blocking if the queue is full. To avoid long HTTP connection times, this operation is executed in a new thread. Drivers receive orders by sending requests to the BILL-MANAGEMENT service, which then dequeues a Bill instance for them.

### 2. User Verification
Both Driver Management and Passenger Management generate and assign a JWT (JSON Web Token) to users upon successful login. This JWT is included in the request header for verification when passing through the gateway.

### 3. Annotation `@UserId`
Nearly all operations, except login and register, require the user's ID. For security reasons, transmitting the user ID in plaintext over the internet is not advisable. Therefore, the user ID is embedded within the JWT and extracted at the gateway. Specific microservices can then access it simply by using the `@UserId` annotation. The argument resolver for `@UserId` retrieves the ID value and assigns it to the annotated variable.

## Problems & Solutions

**Resolved Issues**: Problem No.1 has been resolved. However, Problems No.2 and No.3 are still pending.

### 1. High Memory Usage in Order Handling

**Problem**: Storing orders in memory enhances response speed compared to database retrieval but leads to high memory use or overflow.  
**Solution**: Implement an ArrayBlockingQueue for orders named `waitingBills` with a set limit. The BILL-MANAGEMENT service first saves new orders with a WAITING status in the database and then queues them in a new thread, allowing for quick responses and enabling passengers to check their orders even if they haven't been added to the `waitingBills` queue.

### 2. Lack of Map System Integration

**Problem**: The system does not integrate with any map service, leading to drivers receiving orders without consideration of location or distance, and inaccurate fare calculations.  
**Solution**: Integrate with a map service, such as Baidu Maps, using its API to address location and distance considerations in order dispatch and fare calculation.

### 3. Absence of a Payment System

**Problem**: The system lacks an integrated payment system, requiring passengers to pay fares offline.
