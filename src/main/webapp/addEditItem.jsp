<%@ page import="model.Item" %>
<%@ page session="true" %>
<%
    if (session.getAttribute("admin") == null) { response.sendRedirect("index.jsp"); return; }
    Item it = (Item) request.getAttribute("item");
    boolean edit = it != null;
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
    <a href="help.jsp">Help</a>
    <a href="admins.jsp">Admins</a>
    <a href="logout">Logout</a>
</nav>
<div class="container">

    <h2><%= edit ? "Edit":"Add New" %> Item</h2>

    <form method="post" action="item">
        <input type="hidden" name="action" value="<%= edit ? "update" : "create" %>"/>
        <% if (edit) { %>
            <input type="hidden" name="id" value="<%= it.getId() %>"/>
        <% } %>
        <div class="form-row">
            <label>Code</label>
            <input name="code" value="<%= edit ? it.getCode() : "" %>" required/>
        </div>
        <div class="form-row">
            <label>Name</label>
            <input name="name" value="<%= edit ? it.getName() : "" %>" required/>
        </div>
        <div class="form-row">
            <label>Price</label>
            <input name="price" type="number" step="0.01" value="<%= edit ? it.getPrice() : "0.00" %>" required/>
        </div>
        <div class="form-row">
            <label>Description</label>
            <textarea name="description"><%= edit ? it.getDescription() : "" %></textarea>
        </div>

        <button class="btn" type="submit"><%= edit ? "Update" : "Create" %></button>
        <a class="btn" href="items.jsp">Back</a>
    </form>

    <%@ include file="footer.jsp" %>
</div>
</body>
</html>
