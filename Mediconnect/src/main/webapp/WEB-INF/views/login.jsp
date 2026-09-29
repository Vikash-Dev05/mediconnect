<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - MediConnect</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; display: flex; align-items: center; justify-content: center; height: 100vh; margin: 0; }
        .login-card { width: 100%; max-width: 400px; padding: 30px; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); background: white; }
        .login-header { text-align: center; margin-bottom: 30px; }
        .login-header h1 { font-size: 1.8rem; color: #007bff; }
    </style>
</head>
<body>

<div class="login-card">
    <div class="login-header">
        <h1>🏥 MediConnect</h1>
        <p class="text-muted">Welcome back! Please login.</p>
    </div>

    <c:if test="${param.error != null}">
        <div class="alert alert-danger">
            Invalid email/phone or password. <br>
            <a href="/signup" class="alert-link">New user? Sign up here.</a>
        </div>
    </c:if>
    <c:if test="${param.registered != null}">
        <div class="alert alert-success">Registration successful! Please login.</div>
    </c:if>
    <c:if test="${param.logout != null}">
        <div class="alert alert-success">You have been logged out successfully.</div>
    </c:if>

    <form action="/login" method="POST">
        <div class="mb-3">
            <label class="form-label">Email or Phone Number</label>
            <input type="text" name="username" class="form-control" required placeholder="Enter email or phone">
        </div>
        <div class="mb-3">
            <label class="form-label">Password</label>
            <input type="password" name="password" class="form-control" required placeholder="Enter password">
        </div>
        <button type="submit" class="btn btn-primary w-100 py-2">Sign In</button>
    </form>
    
    <div class="mt-4 text-center">
        <p class="small text-muted mb-0">Don't have an account? <a href="/signup">Sign up here</a>.</p>
    </div>
</div>

</body>
</html>
