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
        <h2>Help and Usage Guide</h2>
    </div>

    <div class="content-section">
        <p>Welcome to the <b>Pahana Edu Billing System</b>. Follow these steps to use the system:</p>
        <ol>
            <li><b>Login:</b> Enter your username and password to access the system. If you have trouble logging in, contact the admin.</li>
            <li><b>Customers:</b> Go to the Customers page to add, edit, or delete customer accounts. Each account must have a unique account number.</li>
            <li><b>Items:</b> Go to the Items page to manage items. You can add, update, or delete any product details.</li>
            <li><b>Billing:</b> Go to Create Bill, select a customer, choose items, enter quantities, and generate the bill.</li>
            <li><b>Print Bill:</b> After creating the bill, click the Print button to get a customer receipt.</li>
            <li><b>Admin Management:</b> Admins can manage other admin accounts in the Admin Management page.</li>
            <li><b>Logout:</b> Click Logout to securely exit the system.</li>
        </ol>
        <p>For further assistance, please contact your system administrator.</p>
    </div>
</div>



</body>
</html>
