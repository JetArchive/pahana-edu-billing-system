<%@ page import="model.Item" %>
<%@ page session="true" %>
<%
    if (session.getAttribute("admin") == null) { 
        response.sendRedirect("index.jsp"); 
        return; 
    }
    Item it = (Item) request.getAttribute("item");
    boolean edit = it != null;

    // Get any error message set by servlet
    String errorMsg = (String) request.getAttribute("error");
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Items - PahanaEdu</title>
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
        .form-table input, 
        .form-table textarea {
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
            background: #fdd;
            color: #900;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #c00;
            border-radius: 5px;
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
    <h2><%= edit ? "Edit" : "Add New" %> Item</h2>

    <% if (errorMsg != null) { %>
        <div class="error"><%= errorMsg %></div>
    <% } %>

    <form method="post" action="item">
        <input type="hidden" name="action" value="<%= edit ? "update" : "create" %>"/>
        <% if (edit) { %>
            <input type="hidden" name="id" value="<%= it.getId() %>"/>
        <% } %>

        <table class="form-table">
            <tr>
                <td>Code</td>
                <td><input name="code" value="<%= edit ? it.getCode() : "" %>" required/></td>
            </tr>
            <tr>
                <td>Name</td>
                <td><input name="name" value="<%= edit ? it.getName() : "" %>" required/></td>
            </tr>
            <tr>
                <td>Price</td>
                <td><input name="price" type="number" step="0.01" value="<%= edit ? it.getPrice() : "0.00" %>" required/></td>
            </tr>
            <tr>
                <td>Description</td>
                <td><textarea name="description"><%= edit ? it.getDescription() : "" %></textarea></td>
            </tr>
        </table>

        <button class="btn" type="submit"><%= edit ? "Update" : "Create" %></button>
        <a class="btn" href="items.jsp">Back</a>
    </form>
</div>

<footer>&copy; 2025 Pahana Edu Bookshop</footer>
</body>
</html>
