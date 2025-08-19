<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pahana Edu - Welcome</title>
    <style>
        body { 
            font-family: Arial, sans-serif; 
            background: linear-gradient(to right, #1abc9c, #16a085); 
            height: 100vh; 
            margin: 0; 
            display: flex; 
            justify-content: center; 
            align-items: center; 
            color: white; 
            text-align: center;
        }
        .container { 
            background: rgba(0,0,0,0.3); 
            padding: 40px; 
            border-radius: 10px; 
        }
        h1 { font-size: 36px; margin-bottom: 10px; }
        p { font-size: 18px; margin-bottom: 20px; }
        .btn { 
            background-color: #e67e22; 
            padding: 12px 25px; 
            color: white; 
            text-decoration: none; 
            font-size: 16px; 
            border-radius: 5px; 
            transition: 0.3s; 
        }
        .btn:hover { 
            background-color: #d35400; 
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to Pahana Edu</h1>
        <p>Manage your bookstore efficiently</p>
        <a class="btn" href="login.jsp">Go to Login</a>
    </div>
</body>
</html>
