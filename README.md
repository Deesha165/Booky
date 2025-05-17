
# Event Booking Platform

A full-stack event booking platform that supports user registration, event browsing, bookings with verification and tracking, admin controls, and multi-language support. Built with Spring Boot and Angular, and containerized using Docker for easy setup and deployment.

---

## 🚀 Setup Instructions

1. **Clone the repository:**



2. **Navigate to the root directory:**



3. **Start all services using Docker:**

   ```bash
   docker-compose up --build
   ```

   This command will build and start the backend, frontend, and database services.

---
## 🎥 Demo

**See the video for all features:**  
[![Video Title](https://img.youtube.com/vi/SYyeJgRqM0o/0.jpg)](https://www.youtube.com/watch?v=SYyeJgRqM0o)

---

## 🧰 Tech Stack

### Backend
- Java
- Spring Boot
- Spring Security
- Spring Data JPA

### Frontend
- Angular
- TypeScript
- ngx-translate (for internationalization)

### DevOps & Tools
- Docker
- Git

### Testing
- JUnit
- Mockito
### Others
- Spring Validation
- Java Mail Sender
- Lombok
- Scheduling

---

## ✅ Functional Requirements

- **User APIs**: Register, login, and manage user profile
- **Event Management**: Create, update, delete, and view events
- **Booking System**:
  - Book event tickets
  - Prevent double-booking (concurrency-safe)
  - Booking verification with a verifier role
  - Email notifications for booking status
- **Media API**: Upload and retrieve event images
- **Admin Panel**:
  - Manage users, events, and categories
  - Filter events by category
  - View trending events by tags
- **Security**:
  - Role-based access control
  - Password change functionality
- **Localization**:
  - Static multi-language support
- **Authentication**:
  - JWT access and refresh tokens
  - Refresh token mechanism


---

## ⚙️ Non-Functional Requirements

- **Unit Testing**: Ensures code correctness and coverage with JUnit and Mockito
- **Concurrency Handling**: Prevents double-booking through transactional safety
- **User Experience**:
  - Infinite scrolling
  - Pagination for performance and navigation
- **Portability**: Docker Compose enables seamless environment setup
-  **Pagination & Scrolling**:
  - Infinite scrolling for better UX
  - Pagination for large datasets
- **Maintainability**:
  - Clean architecture for both frontend and backend
  - Modular and scalable code structure
- **Security**:
  - JWT authentication and refresh token mechanism
  - Secure password handling and role checks
- **Performance Optimization**:
  - Lazy loading with pagination 
  - Use of JPA projections for efficient querying
- **Robust Error Handling**:
  - RESTful API error handling practices
  - Descriptive messages and consistent response formats
- **Attention to Detail**:
  - Structured codebase
  - Clear naming conventions and modular separation** 

---

## 🌟 What Makes This Project a Strong Candidate?

 Here's how this project stands out in each category:

### 1. Code Quality and Maintainability
- Follows clean architecture principles with clear separation of concerns
- Backend and frontend are modular, scalable, and easy to extend
- Use of Lombok, Mappers, Spring best practices, and organized DTOs/entities/services
- Adopts best practices in Angular for modular components and services

### 2. Functionality and Completeness
- Comprehensive user and event management system with all major CRUD operations
- Fully implemented booking system with verifier-based confirmation and email integration
- Admin control panel with filtering, trending features, and role-based permissions
- Static multi-language support for wider accessibility

### 3. Creativity and Innovation
- Trending events based on tag occurrence like twitter
- Role-based booking verification introduces a unique workflow
- Real-time email notifications enhance user engagement
- Internationalization with `ngx-translate` and support for infinite scroll for better UX
- Dockerized setup improves development and deployment consistency

### 4. Adherence to Requirements and Best Practices
- Implements JWT with refresh token support securely
- Pagination, lazy loading, and projection for performance and scalability
- Strong error handling with REST best practices
- High attention to detail and consistent coding standards across the stack

---

## ERD
<p align="center">
  <img src="./ERD.png" alt="ERD Diagram" width="600"/>
</p>

