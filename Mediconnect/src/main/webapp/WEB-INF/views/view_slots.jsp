<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Appointment - MediConnect</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Razorpay Checkout JS -->
    <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
    <style>
        body { background-color: #f8f9fa; }
        .slot-card { cursor: pointer; border: 2px solid #e9ecef; transition: all 0.2s; border-radius: 10px; }
        .slot-card:hover { border-color: #007bff; background-color: #e7f1ff; }
        .slot-time { font-weight: bold; font-size: 1.1rem; }
        .doctor-profile-header { background: white; padding: 30px 0; border-bottom: 1px solid #dee2e6; margin-bottom: 40px; }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">
    <div class="container">
        <a class="navbar-brand" href="/patient/dashboard">🏥 MediConnect</a>
        <div class="navbar-nav ms-auto">
            <a class="nav-link" href="/patient/dashboard">Back to Dashboard</a>
        </div>
    </div>
</nav>

<div class="doctor-profile-header">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-auto">
                <img src="https://via.placeholder.com/120?text=Doc" class="rounded-circle shadow" alt="Doctor">
            </div>
            <div class="col">
                <h2 class="mb-1 text-primary"><c:out value="${doctor.name}"/></h2>
                <p class="mb-1 text-muted"><strong><c:out value="${doctor.qualification}"/></strong></p>
                <span class="badge bg-info text-dark"><c:out value="${doctor.specialization}"/></span>
                <c:if test="${doctor.verified}">
                    <span class="badge bg-success ms-2">Verified</span>
                </c:if>
            </div>
        </div>
    </div>
</div>

<div class="container mb-5">
    <h4 class="mb-4">Available Appointment Slots</h4>
    <div class="row row-cols-1 row-cols-md-3 row-cols-lg-4 g-4">
        <c:forEach var="slot" items="${slots}">
            <div class="col">
                <div class="card h-100 slot-card text-center p-3 shadow-sm">
                    <div class="card-body">
                        <div class="text-muted small mb-1"><c:out value="${slot.date}"/></div>
                        <div class="slot-time text-primary mb-3">
                            <c:out value="${slot.startTime}"/> - <c:out value="${slot.endTime}"/>
                        </div>
                        <p class="text-muted small mb-3">Consultation Fee: ₹500</p>
                        <button type="button" class="btn btn-primary btn-sm w-100" onclick="payNow(${slot.id}, 500)">
                            Book & Pay Now
                        </button>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:if test="${empty slots}">
            <div class="col-12 text-center py-5">
                <div class="alert alert-warning">
                    No available slots for this doctor at the moment. Please check back later.
                </div>
            </div>
        </c:if>
    </div>
</div>

<script>
async function payNow(slotId, amount) {
    try {
        const orderRes = await fetch('/patient/create-order', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ slotId, amount })
        });
        
        const orderData = await orderRes.json();
        
        const options = {
            "key": "rzp_test_YOUR_KEY_ID", // Yahan apni key dalo frontend par
            "amount": orderData.amount,
            "currency": orderData.currency,
            "name": "MediConnect",
            "description": "Consultation Booking",
            "order_id": orderData.id,
            "handler": async function (response) {
                // Payment success: Ab backend verify karega aur book karega
                const verifyRes = await fetch('/patient/verify-payment', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        razorpay_payment_id: response.razorpay_payment_id,
                        razorpay_order_id: response.razorpay_order_id,
                        razorpay_signature: response.razorpay_signature,
                        slotId: slotId
                    })
                });
                
                const verifyData = await verifyRes.json();
                if (verifyData.status === 'success') {
                    alert('Appointment Booked Successfully!');
                    window.location.href = '/patient/dashboard';
                } else {
                    alert('Payment successful but booking failed. Please contact support.');
                }
            },
            "prefill": {
                "name": "Patient Name",
                "email": "patient@example.com"
            },
            "theme": { "color": "#007bff" }
        };
        
        const rzp = new Razorpay(options);
        rzp.open();
        
    } catch (e) {
        console.error(e);
        alert('Could not initiate payment. Check console.');
    }
}
</script>

</body>
</html>
