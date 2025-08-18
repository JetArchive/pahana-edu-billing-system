<%@ page import="model.Customer" %>
<%@ page session="true" %>
<%
    if (session.getAttribute("admin") == null) { 
        response.sendRedirect("index.jsp"); 
        return; 
    }
    Customer c = (Customer) request.getAttribute("customer");
    boolean edit = c != null;
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


    <h2><%= edit ? "Edit" : "Add New" %> Customer</h2>

    <form method="post" action="customer">
        <input type="hidden" name="action" value="<%= edit ? "update" : "create" %>"/>
        
        <div class="form-row">
            <label>Account Number</label>
            <input name="accountNo" value="<%= edit ? c.getAccountNo() : "" %>" <%= edit ? "readonly" : "required" %> />
        </div>
        <div class="form-row">
            <label>Name</label>
            <input name="name" value="<%= edit ? c.getName() : "" %>" required/>
        </div>
        <div class="form-row">
            <label>Address</label>
            <textarea name="address"><%= edit ? c.getAddress() : "" %></textarea>
        </div>
        <div class="form-row">
            <label>Telephone</label>
            <input name="telephone" value="<%= edit ? c.getTelephone() : "" %>"/>
        </div>
        <div class="form-row">
            <label>Units Consumed</label>
            <input type="number" min="0" name="unitsConsumed" value="<%= edit ? c.getUnitsConsumed() : "0" %>" required />
        </div>
        
        <button class="btn" type="submit"><%= edit ? "Update" : "Create" %></button>
        <a class="btn" href="customers.jsp">Back</a>
    </form>


</div>
<footer>&copy; 2025 Pahana Edu Bookshop</footer>
</body>
</html>
