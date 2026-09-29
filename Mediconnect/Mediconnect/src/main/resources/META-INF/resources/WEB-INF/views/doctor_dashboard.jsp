<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Doctor Dashboard - MediConnect</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f0f2f5; }
        .dashboard-card { border: none; border-radius: 15px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
        .header-bg { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 40px 0; }
        .status-badge { font-size: 0.8rem; padding: 5px 10px; border-radius: 20px; }
    </style>
</head>
<body>

<div class="header-bg text-center position-relative">
    <div class="container">
        <a href="/logout" class="btn btn-danger position-absolute top-0 end-0 m-3 btn-sm">🚪 Logout</a>
        <h2>👨‍⚕️ Welcome, <c:out value="${doctor.name}"/></h2>
        <p>Manage your appointments and availability</p>
        <c:if test="${doctor.verified}">
            <span class="badge bg-success"><i class="bi bi-patch-check"></i> Verified Profile</span>
        </c:if>
        <c:if test="${not doctor.verified}">
            <span class="badge bg-warning text-dark">Pending Verification</span>
        </c:if>
    </div>
</div>

<div class="container my-5">
    <div class="row">
        <!-- Add Slot Form -->
        <div class="col-md-4 mb-4">
            <div class="card dashboard-card">
                <div class="card-body">
                    <h5 class="card-title mb-4">📅 Add Available Slot</h5>
                    <form action="/doctor/add-slot" method="POST">
                        <div class="mb-3">
                            <label class="form-label">Date</label>
                            <input type="date" name="date" class="form-control" required min="<%= java.time.LocalDate.now() %>">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Start Time</label>
                            <input type="time" name="startTime" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">End Time</label>
                            <input type="time" name="endTime" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Create Slot</button>
                    </form>
                </div>
            </div>
        </div>

        <!-- Slot List -->
        <div class="col-md-8">
            <div class="card dashboard-card">
                <div class="card-body">
                    <h5 class="card-title mb-4">Your Recent Slots</h5>
                    <div class="table-responsive">
                        <table class="table align-middle">
                            <thead class="table-light">
                                <tr>
                                    <th>Date</th>
                                    <th>Time</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="slot" items="${slots}">
                                    <tr>
                                        <td><c:out value="${slot.date}"/></td>
                                        <td><c:out value="${slot.startTime}"/> - <c:out value="${slot.endTime}"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${slot.status == 'Available'}">
                                                    <span class="status-badge bg-success-subtle text-success">Available</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="status-badge bg-danger-subtle text-danger"><c:out value="${slot.status}"/></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty slots}">
                                    <tr><td colspan="3" class="text-center text-muted">No slots added yet.</td></tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
