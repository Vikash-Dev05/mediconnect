<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Patient Dashboard - MediConnect</title>
    <!-- Bootstrap CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f7f6; }
        .dashboard-header { background: #007bff; color: white; padding: 20px 0; margin-bottom: 30px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
        .search-container { background: white; padding: 25px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.05); margin-bottom: 30px; }
        .doctor-card { transition: transform 0.2s; border: none; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
        .doctor-card:hover { transform: translateY(-5px); }
        .specialization-badge { background-color: #e9ecef; color: #495057; font-size: 0.9rem; padding: 5px 12px; border-radius: 20px; }
    </style>
</head>
<body>

<div class="dashboard-header text-center">
    <div class="container position-relative">
        <div class="text-end">
            <a href="/logout" class="btn btn-danger btn-sm px-4">🚪 Logout</a>
        </div>
        <h1 class="mt-2">🏥 MediConnect Patient Dashboard</h1>
        <h3>Welcome, <c:out value="${user.fullName}" default="Patient"/>!</h3>
    </div>
</div>

<div class="container">
    <!-- Search Bar Section -->
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="search-container">
                <form action="/patient/dashboard" method="GET" class="row g-3">
                    <div class="col-md-9">
                        <input type="text" name="specialization" class="form-control form-control-lg" 
                               placeholder="Search by disease (e.g. Fever, Diabetes, Heart)..." 
                               value="${param.specialization}">
                    </div>
                    <div class="col-md-3">
                        <button type="submit" class="btn btn-primary btn-lg w-100">Search Doctors</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Doctor Listings -->
    <h4 class="mb-4">Recommended Doctors</h4>
    <div class="row">
        <c:choose>
            <c:when test="${not empty doctors}">
                <c:forEach var="doctor" items="${doctors}">
                    <div class="col-md-4 mb-4">
                        <div class="card h-100 doctor-card">
                            <div class="card-body text-center">
                                <img src="https://via.placeholder.com/100?text=Doctor" class="rounded-circle mb-3" alt="Doctor Profile">
                                <h5 class="card-title text-primary"><c:out value="${doctor.name}"/></h5>
                                <p class="mb-2"><strong><c:out value="${doctor.qualification}"/></strong></p>
                                <div class="mb-3">
                                    <span class="specialization-badge"><c:out value="${doctor.specialization}" default="General Physician"/></span>
                                </div>
                                <p class="card-text text-muted small">Specialist in treating <c:out value="${doctor.specialization}" default="various diseases"/></p>
                                <a href="/patient/view-slots/${doctor.id}" class="btn btn-outline-primary w-100">View Slots</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="col-12">
                    <div class="alert alert-info text-center">No doctors found matching your search.</div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<!-- Bootstrap JS Bundle CDN -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
