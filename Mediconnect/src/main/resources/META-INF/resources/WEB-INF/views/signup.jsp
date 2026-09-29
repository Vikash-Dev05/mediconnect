<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign Up - MediConnect</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; display: flex; align-items: center; justify-content: center; min-height: 100vh; margin: 0; padding: 20px;}
        .signup-card { width: 100%; max-width: 600px; padding: 30px; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); background: white; }
        .signup-header { text-align: center; margin-bottom: 20px; }
        .signup-header h1 { font-size: 1.8rem; color: #007bff; }
        .nav-tabs { margin-bottom: 20px; border-bottom: 2px solid #dee2e6; }
        .nav-tabs .nav-link { color: #495057; font-weight: 500; border: none; padding: 10px 20px; }
        .nav-tabs .nav-link.active { color: #007bff; border-bottom: 2px solid #007bff; background: transparent; }
    </style>
</head>
<body>

<div class="signup-card">
    <div class="signup-header">
        <h1>🏥 MediConnect</h1>
        <p class="text-muted">Join our community</p>
    </div>

    <c:if test="${param.error != null}">
        <div class="alert alert-danger">${param.error}</div>
    </c:if>

    <ul class="nav nav-tabs nav-fill" id="signupTabs" role="tablist">
        <li class="nav-item">
            <button class="nav-link active" id="patient-tab" data-bs-toggle="tab" data-bs-target="#patient" type="button" role="tab">Patient</button>
        </li>
        <li class="nav-item">
            <button class="nav-link" id="doctor-tab" data-bs-toggle="tab" data-bs-target="#doctor" type="button" role="tab">Doctor</button>
        </li>
    </ul>

    <div class="tab-content" id="signupTabsContent">
        <!-- Patient Signup Form -->
        <div class="tab-pane fade show active" id="patient" role="tabpanel">
            <form action="/api/auth/register-patient" method="POST">
                <div class="mb-3">
                    <label class="form-label">Full Name</label>
                    <input type="text" name="fullName" class="form-control" required placeholder="Enter your full name">
                </div>
                <div class="mb-3">
                    <label class="form-label">Email Address</label>
                    <input type="email" name="email" class="form-control" required placeholder="Enter your email">
                </div>
                <div class="mb-3">
                    <label class="form-label">Phone Number</label>
                    <input type="text" name="phoneNumber" class="form-control" required placeholder="Enter your phone number">
                </div>
                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <input type="password" name="password" class="form-control" required placeholder="Create a password">
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Age</label>
                        <input type="number" name="age" class="form-control" required placeholder="Age">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Gender</label>
                        <select name="gender" class="form-select" required>
                            <option value="">Select Gender</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Other">Other</option>
                        </select>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary w-100 py-2">Sign Up as Patient</button>
            </form>
        </div>

        <!-- Doctor Signup Form -->
        <div class="tab-pane fade" id="doctor" role="tabpanel">
            <form action="/api/auth/register-doctor" method="POST" enctype="multipart/form-data">
                <div class="mb-3">
                    <label class="form-label">Full Name</label>
                    <input type="text" name="fullName" class="form-control" required placeholder="Dr. Enter your name">
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" name="email" class="form-control" required placeholder="Email">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Phone</label>
                        <input type="text" name="phoneNumber" class="form-control" required placeholder="Phone">
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <input type="password" name="password" class="form-control" required placeholder="Password">
                </div>
                <div class="mb-3">
                    <label class="form-label">Qualification</label>
                    <input type="text" name="qualification" class="form-control" required placeholder="e.g. MBBS, MD, MS">
                </div>
                <div class="mb-3">
                    <label class="form-label">Specialization Details</label>
                    <textarea name="specialization" class="form-control" rows="2" placeholder="Briefly describe what you treat"></textarea>
                </div>
                <div class="mb-3">
                    <label class="form-label">Primary Specialization Category</label>
                    <select name="categoryId" class="form-select" required>
                        <option value="">Select Category</option>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.id}">${category.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Attach Degree/Certificate (PDF/Image)</label>
                    <input type="file" name="degreeFile" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-success w-100 py-2">Sign Up as Doctor</button>
                <p class="text-center small mt-2 text-warning">Note: Your account will be active after Admin approval.</p>
            </form>
        </div>
    </div>
    
    <div class="mt-4 text-center">
        <p class="small text-muted mb-0">Already have an account? <a href="/login">Login here</a></p>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
