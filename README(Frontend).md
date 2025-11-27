# Student ERP System - Frontend

A modern, responsive React-based frontend for the Student ERP system with role-based dashboards, real-time data management, and PDF download capabilities.

## 🚀 Features

### Admin Dashboard
- View all students in a responsive table
- Add new students with complete information
- Edit existing student details
- Delete students with confirmation
- Real-time data updates
- Clean and intuitive UI

### Student Portal
- View personal profile information
- Download digital ID card as PDF
- Secure access to own data
- Profile card with avatar

### General Features
- JWT-based authentication
- Role-based routing and access control
- Responsive design (mobile-friendly)
- Modern gradient UI
- Loading states and error handling
- Clean navigation with logout

## 🛠️ Technology Stack

- **React**: 18.x
- **React Router**: 6.x (Navigation)
- **Axios**: HTTP client for API calls
- **CSS3**: Modern styling with gradients and animations
- **localStorage**: Token and user data persistence

## 📋 Prerequisites

- Node.js 16+ and npm
- Backend API running on `http://localhost:8080`
- Modern web browser

## ⚙️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/student-erp-frontend.git
cd student-erp-frontend
```

### 2. Install Dependencies

```bash
npm install
```

### 3. Configure API Base URL

The API base URL is already configured in `src/services/api.js`:

```javascript
const API_BASE_URL = 'http://localhost:8080/api';
```

If your backend runs on a different port, update this value.

### 4. Start Development Server

```bash
npm start
```

The application will open at `http://localhost:3000`

## 🔑 Default Login Credentials

### Admin Login
```
Username: admin
Password: admin123
```

### Student Login
After admin creates a student, use those credentials:
```
Username: (provided by admin)
Password: (provided by admin)
```

## 📱 Application Routes

| Route | Access | Description |
|-------|--------|-------------|
| `/login` | Public | Login page |
| `/` | Public | Redirects based on auth status |
| `/admin/dashboard` | Admin Only | View all students |
| `/admin/add-student` | Admin Only | Add new student form |
| `/admin/edit-student/:id` | Admin Only | Edit student form |
| `/student/profile` | Student Only | View own profile |

## 🎨 UI Components

### Login Page
- Clean, centered login form
- Gradient background
- Error message display
- Loading states during authentication

### Admin Dashboard
- Responsive data table
- Action buttons (Edit, Delete)
- Add student button
- Navbar with user info

### Add/Edit Student Forms
- Two-column responsive layout
- Input validation
- All student fields
- Login credentials section
- Cancel and submit actions

### Student Profile
- Profile card with avatar
- Personal information section
- Academic information section
- Download ID card button
- Gradient header design

## 📂 Project Structure

```
src/
├── components/
│   ├── Login.js              # Login component
│   ├── Login.css             # Login styles
│   ├── Navbar.js             # Navigation bar
│   ├── Navbar.css            # Navbar styles
│   ├── AdminDashboard.js     # Admin student list
│   ├── AdminDashboard.css    # Dashboard styles
│   ├── AddStudent.js         # Add student form
│   ├── EditStudent.js        # Edit student form
│   ├── StudentForm.css       # Shared form styles
│   ├── StudentProfile.js     # Student profile view
│   └── StudentProfile.css    # Profile styles
├── services/
│   └── api.js                # API service (Axios)
├── utils/
│   └── auth.js               # Authentication helpers
├── App.js                    # Main app with routing
├── App.css                   # App styles
├── index.js                  # React entry point
└── index.css                 # Global styles
```

## 🔐 Authentication Flow

### Login Process
1. User enters credentials
2. API call to `/api/auth/login`
3. Receive JWT token and user info
4. Store in localStorage
5. Redirect based on role:
   - Admin → `/admin/dashboard`
   - Student → `/student/profile`

### Protected Routes
- Routes check authentication status
- Verify user role
- Redirect unauthorized users
- Automatic logout on token expiry

### Logout Process
1. Clear localStorage (token + user data)
2. Redirect to login page

## 🌐 API Integration

### API Service (src/services/api.js)

All API calls go through centralized service:

```javascript
// Automatically adds JWT token to requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});
```

### Available API Methods

```javascript
// Authentication
login(credentials)

// Admin APIs
getAllStudents()
getStudentById(id)
createStudent(data)
updateStudent(id, data)
deleteStudent(id)

// Student APIs
getProfile()
downloadIdCard()
```

## 🎯 Key Features Explained

### Role-Based Access Control
```javascript
// Protected route components
<AdminRoute>      // Only accessible by ADMIN role
<StudentRoute>    // Only accessible by STUDENT role
```

### PDF Download
- Downloads student ID card
- Creates blob from API response
- Programmatically triggers download
- Filename: `student_id_{rollNumber}.pdf`

### State Management
- React hooks (useState, useEffect)
- localStorage for persistence
- Component-level state
- No Redux needed for this scope

### Responsive Design
- Mobile-first approach
- CSS Grid and Flexbox
- Media queries for breakpoints
- Touch-friendly buttons

## 🧪 Testing the Application

### Admin Flow
1. Login with admin credentials
2. View student list (empty initially)
3. Click "Add New Student"
4. Fill form and submit
5. View student in table
6. Click "Edit" to modify
7. Click "Delete" to remove

### Student Flow
1. Login with student credentials
2. View profile information
3. Click "Download ID Card"
4. PDF downloads automatically

### Error Handling
- Invalid credentials → Error message
- Network errors → User notification
- Validation errors → Form field errors
- Unauthorized access → Redirect

## 🎨 Customization

### Change Color Theme
Edit gradient colors in CSS files:

```css
/* Primary gradient */
background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);

/* Change to your colors */
background: linear-gradient(135deg, #YOUR_COLOR_1 0%, #YOUR_COLOR_2 100%);
```

### Modify Table Columns
Edit `AdminDashboard.js`:

```javascript
<th>New Column</th>
// Add corresponding data:
<td>{student.newField}</td>
```

## 🐛 Troubleshooting

### CORS Errors
- Ensure backend CORS is configured for `http://localhost:3000`
- Check SecurityConfig.java in backend

### API Not Responding
- Verify backend is running on port 8080
- Check API_BASE_URL in api.js

### Token Expired
- Tokens expire after 24 hours
- User needs to login again
- Consider implementing token refresh

### Styling Issues
- Clear browser cache
- Check CSS file imports
- Verify class names match

## 📦 Build for Production

```bash
npm run build
```

Creates optimized production build in `build/` folder.

### Deploy to Production
1. Build the app
2. Upload `build/` folder to web server
3. Configure environment variables
4. Update API base URL for production
5. Set up HTTPS

## 🚀 Performance Optimization

- Lazy loading for routes
- Memoization with React.memo
- Debouncing for search inputs
- Optimized images
- Code splitting

## 🤝 Contributing

1. Fork the repository
2. Create feature branch: `git checkout -b feature-name`
3. Commit changes: `git commit -m 'Add feature'`
4. Push to branch: `git push origin feature-name`
5. Submit Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Developer

**Aalekh**
- Email: your.email@example.com
- GitHub: @yourusername

## 🙏 Acknowledgments

- React Documentation
- React Router
- Axios Library
- CSS Grid & Flexbox

---

**Note**: This frontend requires the backend API to be running. Ensure the backend is set up and running before starting the frontend application.