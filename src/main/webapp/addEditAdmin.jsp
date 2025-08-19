<%@ page import="model.Admin" %>
<%@ page session="true" %>
<%
    if (session.getAttribute("admin") == null) { response.sendRedirect("index.jsp"); return; }
    Admin a = (Admin) request.getAttribute("adminObj");
    boolean edit = a != null;
    String error = (String) request.getAttribute("error");
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Admins - PahanaEdu</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/scripts.js"></script>
    <style>
        .form-table {
            width: 100%;
            border-collapse: collapse;
        }
        .form-table td {
            padding: 8px 12px;
            vertical-align: middle;
        }
        .form-table td:first-child {
            text-align: right;
            font-weight: bold;
            width: 200px;
        }
        .form-table input {
            width: 100%;
            padding: 6px;
            box-sizing: border-box;
        }
        .btn {
            margin: 10px 5px 0 0;
            padding: 8px 16px;
            display: inline-block;
        }
        .error {
            color: red;
            font-weight: bold;
            margin: 10px 0;
        }
    </style>
</head>
<body>
<header>
    <h1>Pahana Edu Billing System</h1>
</header>

<nav>
    <a href="dashboard.jsp">Dashboard</a>
    <a href="customers.jsp">Customers</a>
    <a href="items.jsp">Items</a>
    <a href="bill.jsp">Bills</a>
    <a href="help.jsp">Help</a>
    <a href="admins.jsp">Admins</a>
    <a href="logout">Logout</a>
</nav>

<div class="container">
    <h2><%= edit ? "Edit" : "Add New" %> Admin</h2>

    <% if (error != null) { %>
        <div class="error"><%= error %></div>
    <% } %>

    <form method="post" action="admin">
        <input type="hidden" name="action" value="<%= edit ? "update" : "create" %>"/>
        <% if (edit) { %>
            <input type="hidden" name="id" value="<%= a.getId() %>"/>
        <% } %>

        <table class="form-table">
            <tr>
                <td>Username</td>
                <td><input name="username" value="<%= edit ? a.getUsername() : (request.getParameter("username") != null ? request.getParameter("username") : "") %>" required/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="password" value="<%= edit ? a.getPassword() : "" %>" required/></td>
            </tr>
            <tr>
                <td>Full Name</td>
                <td><input name="fullname" value="<%= edit ? a.getFullname() : (request.getParameter("fullname") != null ? request.getParameter("fullname") : "") %>"/></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input name="email" value="<%= edit ? a.getEmail() : (request.getParameter("email") != null ? request.getParameter("email") : "") %>"/></td>
            </tr>
        </table>

        <button class="btn" type="submit"><%= edit ? "Update" : "Create" %></button>
        <a class="btn" href="admins.jsp">Back</a>
    </form>
</div>

<footer>&copy; 2025 Pahana Edu Bookshop</footer>
</body>
</html>
