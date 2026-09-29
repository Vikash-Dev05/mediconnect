<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - MediConnect</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f7f6; }
        .admin-header { background: #343a40; color: white; padding: 20px 0; margin-bottom: 30px; }
        .doctor-row:hover { background-color: #f8f9fa; }
    </style>
</head>
<body>

<div class="admin-header text-center position-relative">
    <div class="container">
        <a href="/logout" class="btn btn-danger position-absolute top-0 end-0 m-3 btn-sm">🚪 Logout</a>
        <h1>🛠️ MediConnect Admin Dashboard</h1>
        <p>Manage Doctor Verifications</p>
    </div>
</div>

<div class="container">
    <!-- Tabs for better management -->
    <ul class="nav nav-tabs mb-4" id="adminTabs" role="tablist">
        <li class="nav-item">
            <button class="nav-link active" id="unverified-tab" data-bs-toggle="tab" data-bs-target="#unverified" type="button">Unverified Doctors</button>
        </li>
        <li class="nav-item">
            <button class="nav-link" id="all-doctors-tab" data-bs-toggle="tab" data-bs-target="#all-doctors" type="button">All Doctors</button>
        </li>
        <li class="nav-item">
            <button class="nav-link" id="manual-payment-tab" data-bs-toggle="tab" data-bs-target="#manual-payment" type="button">Manual Payment</button>
        </li>
    </ul>

    <div class="tab-content" id="adminTabContent">
        <!-- Unverified Doctors Section -->
        <div class="tab-pane fade show active" id="unverified">
            <div class="card shadow-sm">
                <div class="card-header bg-white">
                    <h4 class="mb-0">Pending Verifications</h4>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead class="table-light">
                                <tr>
                                    <th>Name</th>
                                    <th>Specialization</th>
                                    <th>Qualification</th>
                                    <th>Degree Document</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="doctor" items="${unverifiedDoctors}">
                                    <tr class="doctor-row">
                                        <td><strong><c:out value="${doctor.name}"/></strong></td>
                                        <td><c:out value="${doctor.specialization}"/></td>
                                        <td><c:out value="${doctor.qualification}"/></td>
                                        <td>
                                            <c:if test="${not empty doctor.degreePath}">
                                                <a href="<c:out value='${doctor.degreePath}'/>" target="_blank" class="btn btn-sm btn-outline-info">View Degree</a>
                                            </c:if>
                                            <c:if test="${empty doctor.degreePath}">
                                                <span class="text-muted">No document</span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <form action="/admin/verify-doctor/${doctor.id}" method="POST" class="d-inline" onsubmit="return confirm('Verify this doctor?');">
                                                <button type="submit" class="btn btn-success btn-sm">Approve</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty unverifiedDoctors}">
                                    <tr>
                                        <td colspan="5" class="text-center py-4 text-muted">No pending verifications.</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!-- All Doctors Section -->
        <div class="tab-pane fade" id="all-doctors">
            <div class="card shadow-sm">
                <div class="card-header bg-white">
                    <h4 class="mb-0">Registered Doctors</h4>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead class="table-light">
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Specialization</th>
                                    <th>Qualification</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="doctor" items="${allDoctors}">
                                    <tr class="doctor-row">
                                        <td><c:out value="${doctor.id}"/></td>
                                        <td><strong><c:out value="${doctor.name}"/></strong></td>
                                        <td><c:out value="${doctor.specialization}"/></td>
                                        <td><c:out value="${doctor.qualification}"/></td>
                                        <td>
                                            <c:if test="${doctor.verified}">
                                                <span class="badge bg-success">Verified</span>
                                            </c:if>
                                            <c:if test="${not doctor.verified}">
                                                <span class="badge bg-warning text-dark">Unverified</span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <form action="/admin/delete-doctor/${doctor.id}" method="POST" onsubmit="return confirm('Remove this doctor permanently?');">
                                                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty allDoctors}">
                                    <tr>
                                        <td colspan="6" class="text-center py-4 text-muted">No doctors registered.</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!-- Manual Payment Section -->
        <div class="tab-pane fade" id="manual-payment">
            <div class="row">
                <div class="col-md-4">
                    <div class="card shadow-sm mb-4">
                        <div class="card-header bg-white">
                            <h4 class="mb-0">Manual Payment Entry</h4>
                        </div>
                        <div class="card-body">
                            <form action="/admin/manual-payment" method="POST">
                                <div class="mb-3">
                                    <label class="form-label">Doctor ID</label>
                                    <input type="number" name="doctorId" class="form-control" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Amount (₹)</label>
                                    <input type="number" step="0.01" name="amount" class="form-control" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Patient Info</label>
                                    <input type="text" name="patientInfo" class="form-control" placeholder="Name or Contact">
                                </div>
                                <button type="submit" class="btn btn-primary w-100">Record Payment</button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="card shadow-sm">
                        <div class="card-header bg-white">
                            <h4 class="mb-0">Recent Payments</h4>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-sm table-hover">
                                    <thead>
                                        <tr>
                                            <th>Date</th>
                                            <th>Doctor ID</th>
                                            <th>Amount</th>
                                            <th>Patient Info</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="pay" items="${payments}">
                                            <tr>
                                                <td><c:out value="${pay.paymentDate}"/></td>
                                                <td><c:out value="${pay.doctorId}"/></td>
                                                <td>₹<c:out value="${pay.amount}"/></td>
                                                <td><c:out value="${pay.patientInfo}"/></td>
                                            </tr>
                                        </c:forEach>
                                        <c:if test="${empty payments}">
                                            <tr><td colspan="4" class="text-center">No payment records.</td></tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS Bundle for Tabs -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
