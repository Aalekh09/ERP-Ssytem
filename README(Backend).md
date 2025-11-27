# Student ERP System - Backend

A comprehensive Student ERP (Enterprise Resource Planning) system built with Java Spring Boot featuring JWT authentication, role-based access control, and RESTful APIs.

## 🚀 Features

### Authentication & Authorization
- JWT (JSON Web Token) based authentication
- Role-Based Access Control (RBAC)
  - **Admin Role**: Full CRUD operations on students
  - **Student Role**: View profile and download digital ID card
- Secure password encryption using BCrypt
- Protected API endpoints with Spring Security

### Admin Features
- Add new students with login credentials
- View all students in the system
- Edit student information
- Delete students and associated accounts
- Automated user account creation for each student

### Student Features
- View personal profile information
- Download digital ID card as PDF
- Secure access to own data only

### Technical Features
- RESTful API architecture
- MySQL database with JPA/Hibernate
- DTO pattern for clean data transfer
- Global exception handling
- Input validation
- Layered architecture (Controller → Service → Repository)
- PDF generation using iText7

## 🛠️ Technology Stack

- **Java**: 17+
- **Spring Boot**: 3.2.0
- **Spring Security**: JWT authentication
- **Spring Data JPA**: Database operations
- **MySQL**: 8.0+
- **Hibernate**: ORM
- **Maven**: Dependency management
- **iText7**: PDF generation
- **Lombok**: Boilerplate code reduction
- **JJWT**: JWT token handling

## 📋 Prerequisites

Before running this application, ensure you have:

- Java JDK 17 or higher
- MySQL 8.0 or higher
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)
- Postman (for API testing)

## ⚙️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/student-erp-backend.git
cd student-erp-backend
```

### 2. Configure MySQL Database

Create a new MySQL database:

```sql
CREATE DATABASE student_erp_db;
```

### 3. Update Application Properties

Edit `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/student_erp_db
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Server Configuration
server.port=8080

# JWT Configuration (Change in production!)
jwt.secret=5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437
jwt.expiration=86400000
```

### 4. Install Dependencies

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

Or run `StudentErpApplication.java` from your IDE.

The application will start on `http://localhost:8080`

## 🔑 Initial Setup

### Create First Admin User

Make a POST request to create the admin account:

```bash
POST http://localhost:8080/api/auth/register-admin
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123",
  "email": "admin@erp.com"
}
```

## 📚 API Documentation

### Authentication Endpoints

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "admin",
  "email": "admin@erp.com",
  "role": "ADMIN"
}
```

### Admin Endpoints (Requires Admin Role)

#### Get All Students
```http
GET /api/admin/students
Authorization: Bearer {token}
```

#### Get Student by ID
```http
GET /api/admin/students/{id}
Authorization: Bearer {token}
```

#### Create Student
```http
POST /api/admin/students
Authorization: Bearer {token}
Content-Type: application/json

{
  "rollNumber": "STU001",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@student.com",
  "phone": "1234567890",
  "department": "Computer Science",
  "course": "B.Tech",
  "semester": 5,
  "dateOfBirth": "2002-05-15",
  "enrollmentDate": "2021-08-01",
  "username": "john_doe",
  "password": "student123"
}
```

#### Update Student
```http
PUT /api/admin/students/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "rollNumber": "STU001",
  "firstName": "John",
  "lastName": "Smith",
  ...
}
```

#### Delete Student
```http
DELETE /api/admin/students/{id}
Authorization: Bearer {token}
```

### Student Endpoints (Requires Student Role)

#### Get Own Profile
```http
GET /api/student/profile
Authorization: Bearer {token}
```

#### Download ID Card
```http
GET /api/student/id-card
Authorization: Bearer {token}

Response: PDF file
```

## 🏗️ Project Structure

```
src/main/java/com/erp/studenterp/
├── config/
│   ├── CustomUserDetailsService.java
│   ├── JwtAuthenticationFilter.java
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   ├── AdminController.java
│   └── StudentController.java
├── dto/
│   ├── LoginRequest.java
│   ├── AuthResponse.java
│   ├── StudentRequest.java
│   ├── StudentResponse.java
│   └── ErrorResponse.java
├── entity/
│   ├── User.java
│   └── Student.java
├── exception/
│   ├── ResourceNotFoundException.java
│   ├── BadRequestException.java
│   └── GlobalExceptionHandler.java
├── repository/
│   ├── UserRepository.java
│   └── StudentRepository.java
├── service/
│   ├── AuthService.java
│   ├── StudentService.java
│   └── PdfService.java
├── util/
│   └── JwtUtil.java
└── StudentErpApplication.java
```

## 🔐 Security Features

- **Password Encryption**: BCrypt hashing algorithm
- **JWT Tokens**: Stateless authentication with 24-hour expiry
- **Role-Based Access**: Separate permissions for Admin and Student roles
- **CORS Configuration**: Controlled cross-origin access
- **Input Validation**: Jakarta Bean Validation
- **Exception Handling**: Centralized error responses

## 🗄️ Database Schema

### users Table
| Column   | Type         | Description                    |
|----------|--------------|--------------------------------|
| id       | BIGINT (PK)  | Primary key                    |
| username | VARCHAR(255) | Unique username                |
| password | VARCHAR(255) | BCrypt encrypted password      |
| email    | VARCHAR(255) | User email                     |
| role     | VARCHAR(50)  | ADMIN or STUDENT               |
| active   | BOOLEAN      | Account status                 |

### students Table
| Column          | Type         | Description                    |
|-----------------|--------------|--------------------------------|
| id              | BIGINT (PK)  | Primary key                    |
| roll_number     | VARCHAR(50)  | Unique student ID              |
| first_name      | VARCHAR(100) | First name                     |
| last_name       | VARCHAR(100) | Last name                      |
| email           | VARCHAR(255) | Student email                  |
| phone           | VARCHAR(20)  | Contact number                 |
| department      | VARCHAR(100) | Department name                |
| course          | VARCHAR(100) | Course name                    |
| semester        | INTEGER      | Current semester               |
| date_of_birth   | DATE         | Date of birth                  |
| enrollment_date | DATE         | Enrollment date                |
| user_id         | BIGINT (FK)  | Reference to users table       |
| active          | BOOLEAN      | Active status                  |

## 🧪 Testing

### Using Postman

1. Import the API endpoints
2. Create environment variables:
   - `baseUrl`: http://localhost:8080/api
   - `adminToken`: (Set after admin login)
   - `studentToken`: (Set after student login)
3. Test authentication flow
4. Test CRUD operations
5. Verify role-based access control

### Test Scenarios

- ✅ Admin login and token generation
- ✅ Student creation by admin
- ✅ Student login with credentials
- ✅ Admin accessing all endpoints
- ✅ Student accessing only allowed endpoints
- ✅ PDF generation and download
- ✅ Access denial for unauthorized roles

## 🐛 Troubleshooting

### Database Connection Issues
- Verify MySQL is running
- Check username and password in application.properties
- Ensure database `student_erp_db` exists

### JWT Token Issues
- Check token expiration (24 hours default)
- Verify Authorization header format: `Bearer {token}`
- Ensure jwt.secret is configured

### Port Already in Use
Change the port in application.properties:
```properties
server.port=8081
```

## 📦 Build for Production

```bash
mvn clean package
java -jar target/student-erp-0.0.1-SNAPSHOT.jar
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Developer

**Aalekh**
- Email: aalekh09kumar@gmail.com
- GitHub: @Aalekh09

## 🙏 Acknowledgments

- Spring Boot Framework
- iText7 PDF Library
- JWT.io for token handling
- MySQL Database

---

**Note**: This is a demonstration project. For production use:
- Change JWT secret key
- Implement token refresh mechanism
- Add more security layers
- Use environment variables for sensitive data
- Implement logging and monitoring