
# 🛒 Spring Boot Microservices E-Commerce Backend

A backend E-Commerce application built using **Spring Boot Microservices**. The project demonstrates a scalable microservices architecture using Spring Cloud, JWT Authentication, Spring Security, Eureka Discovery Server, Config Server, API Gateway, OpenFeign, Spring Data JPA, and MySQL.

---

## 🚀 Features

- JWT Authentication
- Spring Security
- API Gateway
- Eureka Discovery Server
- Spring Cloud Config Server
- Product Management
- Order Management
- REST APIs
- MySQL Database
- Spring Data JPA
- OpenFeign Communication

---

## 📂 Project Structure

Project-main/
- API_Gateway
- Auth_Service
- Config_Server
- Discovery_Server
- Product_Service
- Order_Service

---

## 🏗️ Architecture

Client
  |
API Gateway
  |
+-------------------------------+
| Auth | Product | Order |
+-------------------------------+
        |
   Eureka Discovery
        |
    Config Server

---

## 🛠️ Technology Stack

- Java 17
- Spring Boot
- Spring Cloud
- Spring Security
- JWT
- OpenFeign
- Spring Data JPA
- Hibernate
- MySQL
- Maven

---

## 📌 Microservices

### Config Server
Stores centralized configuration for all services.

### Discovery Server
Registers and discovers microservices using Eureka.

### API Gateway
Single entry point for all client requests.

### Auth Service
Handles user registration, login, and JWT generation.

### Product Service
Manages product CRUD operations.

### Order Service
Processes customer orders and communicates with Product Service.

---

## 🔐 Authentication Flow

1. User logs in.
2. Auth Service validates credentials.
3. JWT token is generated.
4. Client sends JWT with every request.
5. Gateway validates the token.
6. Request reaches the required microservice.

---

## 📑 Sample API Endpoints

### Authentication
- POST /auth/register
- POST /auth/login

### Products
- GET /products
- GET /products/{id}
- POST /products
- PUT /products/{id}
- DELETE /products/{id}

### Orders
- POST /orders
- GET /orders
- GET /orders/{id}

---

## ▶️ How to Run

1. Start Config Server
2. Start Discovery Server
3. Start API Gateway
4. Start Auth Service
5. Start Product Service
6. Start Order Service

---

 👨‍💻 Author

Akila Dhanasekar
