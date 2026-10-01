# Crop Health Monitor

## Project Description
AI-Based Crop Health Monitoring and Disease Detection System. This system helps agronomists and farmers identify diseases early using leaf images.

## Features
- User registration and login (BCrypt hashing, sessions)
- Secure User Dashboard with statistics (Chart.js)
- Crop Image Analysis (Upload with validations)
- Results view showing confidence, symptoms, treatment, and prevention
- History view and ability to delete analyses
- Profile management
- Fully responsive modern UI (Bootstrap 5)
- SQLite database integration (Hibernate JPA)

## Technology Stack
- Java 17, Spring Boot 3
- Spring Web MVC, Spring Data JPA
- SQLite
- Thymeleaf
- HTML5, CSS3, JavaScript, Bootstrap 5

## Requirements
- Java 17
- Maven

## Running Instructions
1. Ensure Java 17 is installed.
2. In the project root, run:
   ```bash
   mvn spring-boot:run
   ```
3. Access the application at `http://localhost:8080/`

## AI Architecture Note
The current implementation uses a `DemoAiPredictionService` to simulate AI classification. The architecture uses an `AiPredictionService` interface, making it easy to swap in a real ML model implementation via a REST client later without touching controllers.
