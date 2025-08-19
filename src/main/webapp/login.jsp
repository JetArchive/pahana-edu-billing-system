<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Pahana Edu - Login</title>
    <link rel="stylesheet" href="css/style.css"/>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f2f2f2; }
        .container { background-color: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.2); margin: 60px auto; max-width: 420px; }
        h2 { margin-bottom: 6px; color: #333; }
        .form-row { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #555; }
        input[type="text"], input[type="password"] { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
        .btn { background-color: #1abc9c; color: white; border: none; padding: 10px 15px; border-radius: 4px; cursor: pointer; }
        .btn:hover { background-color: #16a085; }
        .alert { background-color: #e74c3c; color: white; padding: 8px; margin-bottom: 15px; border-radius: 4px; }
        .note { margin-top: 12px; font-size: 13px; color: #666; }
    </style>
</head>
<body>
<div class="container">
    <h2>Admin Login</h2>
    <p style="color:#555; margin-top:0;">Login to manage Pahana Edu system</p>

    <% 
        // Display error message if present
        String error = (String) request.getAttribute("error"); 
        if (error != null && !error.trim().isEmpty()) { 
    %>
        <div class="alert"><%= error %></div>
    <% } %>

    <form method="post" action="login">
        <div class="form-row">
            <label>Username</label>
            <input type="text" name="username" required/>
        </div>
        <div class="form-row">
            <label>Password</label>
            <input type="password" name="password" required/>
        </div>
        <button class="btn" type="submit">Login</button>
    </form>

    <div class="note">
        Use <strong>admin/admin123 (temp message)</strong>
    </div>
</div>
<footer>&copy; 2025 Pahana Edu Bookshop</footer>
</body>
</html>
