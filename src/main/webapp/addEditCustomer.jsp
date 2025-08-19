<%@ page import="model.Customer" %>
<%@ page session="true" %>
<%
    if (session.getAttribute("admin") == null) { 
        response.sendRedirect("index.jsp"); 
        return; 
    }
    Customer c = (Customer) request.getAttribute("customer");
    boolean edit = c != null;
    String error = (String) request.getAttribute("error");
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Customers - PahanaEdu</title>
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
    <h2><%= edit ? "Edit" : "Add New" %> Customer</h2>

    <% if (error != null) { %>
        <div class="error"><%= error %></div>
    <% } %>

    <form method="post" action="customer">
        <input type="hidden" name="action" value="<%= edit ? "update" : "create" %>"/>
        
        <table class="form-table">
            <tr>
                <td>Account Number</td>
                <td>
                    <input name="accountNo" 
                           value="<%= (c != null ? c.getAccountNo() : request.getParameter("accountNo") != null ? request.getParameter("accountNo") : "") %>" 
                           <%= edit ? "readonly" : "required" %> />
                </td>
            </tr>
            <tr>
                <td>Name</td>
                <td>
                    <input name="name" 
                           value="<%= (c != null ? c.getName() : request.getParameter("name") != null ? request.getParameter("name") : "") %>" 
                           required/>
                </td>
            </tr>
            <tr>
                <td>Address</td>
                <td>
                    <textarea name="address"><%= (c != null ? c.getAddress() : request.getParameter("address") != null ? request.getParameter("address") : "") %></textarea>
                </td>
            </tr>
            <tr>
                <td>Telephone</td>
                <td>
                    <input name="telephone" 
                           value="<%= (c != null ? c.getTelephone() : request.getParameter("telephone") != null ? request.getParameter("telephone") : "") %>"/>
                </td>
            </tr>
            <tr>
                <td>Units Consumed</td>
                <td>
                    <input type="number" min="0" name="unitsConsumed" 
                           value="<%= (c != null ? c.getUnitsConsumed() : request.getParameter("unitsConsumed") != null ? request.getParameter("unitsConsumed") : "0") %>" 
                           required />
                </td>
            </tr>
        </table>
        
        <button class="btn" type="submit"><%= edit ? "Update" : "Create" %></button>
        <a class="btn" href="customers.jsp">Back</a>
    </form>
</div>

<footer>&copy; 2025 Pahana Edu Bookshop</footer>
</body>
</html>
