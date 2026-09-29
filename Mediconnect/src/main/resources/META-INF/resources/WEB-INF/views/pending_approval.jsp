<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Account Pending - MediConnect</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; height: 100vh; display: flex; align-items: center; justify-content: center; }
        .pending-card { max-width: 500px; padding: 40px; border-radius: 20px; text-align: center; background: white; box-shadow: 0 10px 30px rgba(0,0,0,0.1); }
        .icon { font-size: 80px; color: #ffc107; margin-bottom: 20px; }
    </style>
</head>
<body>

<div class="pending-card">
    <div class="icon">⏳</div>
    <h2 class="mb-3">Account Pending Approval</h2>
    <p class="text-muted">Hello, Doctor! Your registration has been received.</p>
    <p>Our admin team is currently reviewing your qualifications and documents. You will be able to access your dashboard once your account is verified.</p>
    <hr>
    <p class="small text-muted">This usually takes 24-48 hours. Thank you for your patience!</p>
    <a href="/login?logout" class="btn btn-outline-primary mt-3">Back to Login</a>
</div>

</body>
</html>
