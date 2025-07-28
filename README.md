Smart Expense Manager API

A secure, RESTful backend service for managing user expenses, built with Java 21 and Spring Boot 3. This application includes features like user registration, login with JWT authentication, role-based access for admins, expense tracking with filtering and pagination, and basic unit testing.

Tech Stack
	•	Java 21
	•	Spring Boot 3
	•	Spring Security
	•	Spring Data JPA
	•	MySQL
	•	JWT (JSON Web Tokens)
	•	Lombok
	•	JUnit 5 & Mockito
	•	Postman (for manual API testing)

Features

User Authentication and Registration
	•	Register a new user: POST /api/auth/register
	•	Login and receive a JWT: POST /api/auth/login
	•	Passwords are hashed using BCrypt
	•	JWT is used for authenticating protected endpoints

Expense Management (Authenticated Users)

Routes under /expenses/** require JWT authentication.
	•	Create a new expense
	•	Update an existing expense (only if owned by the user)
	•	Delete an expense (only if owned by the user)
	•	Get all expenses for the authenticated user
	•	Supports category-based filtering
	•	Supports date range filtering
	•	Pagination support
	•	Returns total amount in results

Admin Features

Routes under /api/admin/** require the user to have the ADMIN role.
	•	Create other admin accounts
	•	Get all expenses across users (with pagination)
	•	Get an expense by ID
	•	Delete any expense by ID
	•	Get all users (paginated)
	•	Get a user by ID
	•	Delete a user by ID (cascade deletes their expenses)

Security
	•	JWT Bearer Token is required for all protected endpoints
	•	Role-based access via Spring Security
	•	Custom JWT filter to extract and validate token from request headers
	•	Custom exceptions with global error handling

Project Structure
com.example.expensetracker
├── config                  # JWT service, Security configuration
├── data
│   ├── entity             # User and Expense entities
│   └── repository         # JPA repositories for persistence
├── exception              # Custom exceptions and global exception handler
├── service                # Business logic for users, expenses, and admins
├── web
│   ├── api                # REST controllers for auth, user, admin
│   └── dto                # Data Transfer Objects
└── ExpenseTrackerApplication.java


Testing
	•	Unit tests for user registration and login logic
	•	Covers:
	•	Successful user registration
	•	Handling of duplicate usernames or emails
	•	Successful login and JWT generation
	•	Invalid login credentials
	•	Uses JUnit and Mockito
	•	All dependencies like UsersRepository, PasswordEncoder, and JwtService are mocked

API Examples

Register User:
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "username": "user123",
  "password": "mypassword"
}
Login User:
POST /api/auth/login
Content-Type: application/json

{
  "username": "user123",
  "password": "mypassword"
}

Create Expense:
POST /expenses/addExpense
Authorization: Bearer <JWT>

{
  "description": "Groceries",
  "amount": 75.50,
  "category": "Food",
  "date": "2025-07-02"
}
