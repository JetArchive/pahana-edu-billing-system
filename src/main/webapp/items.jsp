<%@ page import="dao.ItemDAO, model.Item, java.util.List" %>
<%@ page session="true" %>
<%
    if (session.getAttribute("admin") == null) { response.sendRedirect("index.jsp"); return; }
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Items - PahanaEdu</title>
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
        <h2>Items (goods)</h2>
        <div>
            <a class="btn" href="addEditItem.jsp">Add Item</a>
            <a class="btn" href="customers.jsp">Customers</a>
        </div>
    </div>

    <%
        ItemDAO dao = new ItemDAO();
        List<Item> items = dao.listAll();
    %>

    <table class="table">
        <thead><tr><th>Code</th><th>Name</th><th>Price</th><th>Description</th><th>Actions</th></tr></thead>
        <tbody>
        <% for (Item it : items) { %>
            <tr>
                <td><%= it.getCode() %></td>
                <td><%= it.getName() %></td>
                <td><%= it.getPrice() %></td>
                <td><%= it.getDescription() %></td>
                <td class="actions">
                    <a class="small btn" href="item?action=edit&id=<%= it.getId() %>">Edit</a>
                    <a class="small btn" href="javascript:confirmDelete('item',<%= it.getId() %>)">Delete</a>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>


</div>
<footer>&copy; 2025 Pahana Edu Bookshop</footer>
</body>
</html>
