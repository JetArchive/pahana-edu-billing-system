<%@ page import="dao.CustomerDAO, model.Customer, java.util.List" %>
<%@ page session="true" %>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
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
  

    <div class="topbar">
        <h2>Customers</h2>
        <div>
            <a class="btn" href="addEditCustomer.jsp">Add New Customer</a>
            <a class="btn" href="items.jsp">Manage Items</a>
        </div>
    </div>

    <% String msg = request.getParameter("msg");
       if ("created".equals(msg) || "updated".equals(msg) || "deleted".equals(msg)) { %>
       <div class="success">Operation successful: <%= msg %></div>
    <% } else if ("notfound".equals(msg)) { %>
       <div class="alert">Customer not found.</div>
    <% } %>

    <%
        CustomerDAO dao = new CustomerDAO();
        List<Customer> list = dao.listAll();
    %>

    <table class="table">
        <thead><tr><th>Account No</th><th>Name</th><th>Telephone</th><th>Units</th><th>Actions</th></tr></thead>
        <tbody>
        <% for (Customer c : list) { %>
            <tr>
                <td><%= c.getAccountNo() %></td>
                <td><%= c.getName() %></td>
                <td><%= c.getTelephone() %></td>
                <td><%= c.getUnitsConsumed() %></td>
                <td class="actions">
                    <a class="small btn" href="customer?action=edit&accountNo=<%= c.getAccountNo() %>">Edit</a>
                    <a class="small btn" href="javascript:confirmDelete('customer','<%= c.getAccountNo() %>')">Delete</a>
                
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>

   
</div>
<footer>&copy; 2025 Pahana Edu Bookshop</footer>
</body>
</html>
