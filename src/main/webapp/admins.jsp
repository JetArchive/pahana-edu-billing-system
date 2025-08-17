<%@ page import="dao.AdminDAO, model.Admin, java.util.List" %>
<%@ page session="true" %>
<%
    if (session.getAttribute("admin") == null) { response.sendRedirect("index.jsp"); return; }
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Admins - PahanaEdu</title>
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
    <a href="help.jsp">Help</a>
    <a href="admins.jsp">Admins</a>
    <a href="logout">Logout</a>
</nav>
<div class="container">


    <div class="topbar">
        <h2>Manage Admin Users</h2>
        <div><a class="btn" href="addEditAdmin.jsp">Add Admin</a></div>
    </div>

    <%
        AdminDAO dao = new AdminDAO();
        List<Admin> list = dao.listAll();
    %>

    <table class="table">
        <thead><tr><th>ID</th><th>Username</th><th>Fullname</th><th>Email</th><th>Actions</th></tr></thead>
        <tbody>
        <% for (Admin a : list) { %>
            <tr>
                <td><%= a.getId() %></td>
                <td><%= a.getUsername() %></td>
                <td><%= a.getFullname() %></td>
                <td><%= a.getEmail() %></td>
                <td class="actions">
                    <a class="small btn" href="admin?action=edit&id=<%= a.getId() %>">Edit</a>
                    <a class="small btn" href="javascript:confirmDelete('admin',<%= a.getId() %>)">Delete</a>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>

    <%@ include file="footer.jsp" %>
</div>
</body>
</html>
