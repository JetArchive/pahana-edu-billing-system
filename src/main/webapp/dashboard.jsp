<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.CustomerDAO, dao.ItemDAO, service.BillService" %>
<%@ page session="true" %>
<%
    // Get username from session, fallback to "Admin"
    String username = (String) session.getAttribute("username");
    if (username == null) {
        username = "Admin";
    }

    // Initialize DAOs/Service
    CustomerDAO customerDAO = new CustomerDAO();
    ItemDAO itemDAO = new ItemDAO();
    BillService billService = new BillService();

    int totalCustomers = 0;
    int totalItems = 0;
    int todaysBills = 0;

    try {
        totalCustomers = customerDAO.countAll();
        totalItems = itemDAO.countAll();
        todaysBills = billService.countTodayBills(); // Implement this method in BillService
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pahana Edu - Dashboard</title>
    <link rel="stylesheet" href="css/style.css"/>
    <style>
        body { font-family: Arial; background: #f2f2f2; margin:0; }
        header { background: #1abc9c; color:white; padding:20px; text-align:center; }
        nav { background: #16a085; padding: 15px; display:flex; justify-content:center; flex-wrap:wrap; }
        nav a { color:white; margin:5px 15px; padding:10px 20px; background:#27ae60; border-radius:5px; text-decoration:none; }
        nav a:hover { background:#2ecc71; }
        main { padding:30px; text-align:center; }
        .card { display:inline-block; background:white; margin:10px; padding:20px; border-radius:8px; width:180px; box-shadow:0 2px 8px rgba(0,0,0,0.2); }
        .card h2 { margin:0 0 10px 0; color:#333; font-size:28px; }
        .card p { margin:0; color:#555; font-size:16px; }
        footer { background:#1abc9c; color:white; text-align:center; padding:10px; position:fixed; bottom:0; width:100%; }
    </style>
</head>
<body>

<header>
    <h1>Welcome, <%= username %>!</h1>
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

<main>
    <div class="card">
        <h2><%= totalCustomers %></h2>
        <p>Total Customers</p>
    </div>
    <div class="card">
        <h2><%= totalItems %></h2>
        <p>Total Items</p>
    </div>
    <div class="card">
        <h2><%= todaysBills %></h2>
        <p>Today's Bills</p>
    </div>
</main>

<footer>&copy; 2025 Pahana Edu Bookshop</footer>

</body>
</html>
