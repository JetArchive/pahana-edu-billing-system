<%@ page import="model.Admin" %>
<%@ page session="true" %>
<%
    if (session.getAttribute("admin") == null) { response.sendRedirect("index.jsp"); return; }
    Admin a = (Admin) request.getAttribute("adminObj");
    boolean edit = a != null;
%>
<!doctype html>
<html>
<head>

    <meta charset="utf-8">
    <title>Customers - PahanaEdu</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/scripts.js"></script>
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
  

    <h2><%= edit ? "Edit":"Add New" %> Admin</h2>

    <form method="post" action="admin">
        <input type="hidden" name="action" value="<%= edit ? "update" : "create" %>"/>
        <% if (edit) { %> <input type="hidden" name="id" value="<%= a.getId() %>"/> <% } %>

        <div class="form-row">
            <label>Username</label>
            <input name="username" value="<%= edit ? a.getUsername() : "" %>" required/>
        </div>
        <div class="form-row">
            <label>Password</label>
            <input type="password" name="password" value="<%= edit ? a.getPassword() : "" %>" required/>
        </div>
        <div class="form-row">
            <label>Full Name</label>
            <input name="fullname" value="<%= edit ? a.getFullname() : "" %>"/>
        </div>
        <div class="form-row">
            <label>Email</label>
            <input name="email" value="<%= edit ? a.getEmail() : "" %>"/>
        </div>

        <button class="btn" type="submit"><%= edit ? "Update" : "Create" %></button>
        <a class="btn" href="admins.jsp">Back</a>
    </form>


</div>
<footer>&copy; 2025 Pahana Edu Bookshop</footer>
</body>
</html>
