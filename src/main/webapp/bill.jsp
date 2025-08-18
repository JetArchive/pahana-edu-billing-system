<%@ page import="dao.CustomerDAO, dao.ItemDAO, model.Customer, model.Item, java.util.List, java.text.SimpleDateFormat, java.util.Date" %>
<%@ page session="true" %>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    CustomerDAO customerDAO = new CustomerDAO();
    ItemDAO itemDAO = new ItemDAO();
    List<Customer> customers = customerDAO.listAll();
    List<Item> items = itemDAO.listAll();
    // Get current date for the bill
    String currentDate = new SimpleDateFormat("MMMM dd, yyyy").format(new Date());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Bill - Pahana Edu</title>
<link rel="stylesheet" href="css/style.css">
<style>
.form-row { margin-bottom: 15px; }
select, input[type=number] { padding:8px; width:100%; }
.items-table { width:100%; border-collapse:collapse; margin-top:10px; }
.items-table th, .items-table td { border:1px solid #ccc; padding:8px; text-align:center; }
.btn { background:#1abc9c; color:white; border:none; padding:10px 15px; border-radius:4px; cursor:pointer; }
.btn:hover { background:#16a085; }
.total { font-weight:bold; color:#333; margin-top:15px; font-size:18px; }
/* Hide print area normally */
#printArea { display:none; }
/* Print styles */
@media print {
    body * { visibility:hidden; }
    #printArea, #printArea * { visibility:visible; }
    #printArea { display:block; position:absolute; left:0; top:0; width:100%; font-family: Arial, sans-serif; }
    #printArea h2 { text-align: center; margin-bottom: 20px; }
    #printArea table { width:100%; border-collapse: collapse; margin-top: 10px; }
    #printArea th, #printArea td { border:1px solid #000; padding: 8px; text-align:left; }
    #printArea tfoot th { text-align: right; }
}
</style>
<script>
function addItemRow() {
    let template = document.getElementById("itemSelectTemplate").firstElementChild.cloneNode(true);
    template.addEventListener("change", function() { updatePrice(this); });
    let tableBody = document.getElementById("itemsTable").getElementsByTagName("tbody")[0];
    let row = tableBody.insertRow();
    let cell1 = row.insertCell(0);
    let cell2 = row.insertCell(1);
    let cell3 = row.insertCell(2);
    let cell4 = row.insertCell(3);
    cell1.appendChild(template);
    cell2.innerHTML = '<input type="number" value="1" min="1" onchange="calculateTotal()">';
    cell3.className = "priceCell";
    cell3.textContent = "0.00";
    cell4.innerHTML = '<button type="button" onclick="removeRow(this)">X</button>';
    calculateTotal();
}
function removeRow(btn) {
    let row = btn.closest("tr");
    row.remove();
    calculateTotal();
}
function updatePrice(select) {
    let price = parseFloat(select.selectedOptions[0]?.dataset.price || 0);
    let priceCell = select.closest("tr").querySelector(".priceCell");
    priceCell.textContent = price.toFixed(2);
    calculateTotal();
}
function calculateTotal() {
    let rows = document.querySelectorAll("#itemsTable tbody tr");
    let total = 0;
    rows.forEach(row => {
        let select = row.querySelector("select");
        let qtyInput = row.querySelector("input");
        let priceCell = row.querySelector(".priceCell");
        if (select && select.value && qtyInput && priceCell) { // Only include valid rows with selected item
            let qty = parseInt(qtyInput.value) || 0;
            let price = parseFloat(priceCell.textContent) || 0;
            total += qty * price;
        }
    });
    document.getElementById("totalAmount").textContent = total.toFixed(2);
}
function printBill() {
    let customerSelect = document.querySelector("select[name='customerId']");
    if (!customerSelect.value) {
        alert("Please select a customer.");
        return;
    }
    let rows = document.querySelectorAll("#itemsTable tbody tr");
    if (rows.length === 0) {
        alert("Please add at least one item.");
        return;
    }
    // Extract only the customer name by splitting the text
    let customerOptionText = customerSelect.selectedOptions[0]?.textContent || '';
    let customerName = customerOptionText.split(" - ")[0] || '';
    let customerAccount = customerSelect.value || '';
    document.getElementById("printCustomerName").textContent = customerName;
    document.getElementById("printCustomerAccount").textContent = customerAccount;
    let tbody = document.getElementById("printItemsBody");
    tbody.innerHTML = '';
    let validItemCount = 0;
    rows.forEach(row => {
        let itemSelect = row.querySelector("select");
        if (!itemSelect.value) {
            return; // Skip rows where no item is selected
        }
        validItemCount++;
        let itemText = itemSelect.selectedOptions[0]?.textContent || '';
        let item = itemText.split(" - ")[0] || ''; // Get only the item name
        let qty = row.querySelector("input")?.value || '0';
        let price = parseFloat(row.querySelector(".priceCell")?.textContent || 0);
        let subtotal = (parseInt(qty) * price).toFixed(2);
        let tr = document.createElement("tr");
        tr.innerHTML = `<td>${item}</td><td>${qty}</td><td>LKR ${price.toFixed(2)}</td><td>LKR ${subtotal}</td>`;
        tbody.appendChild(tr);
    });
    if (validItemCount === 0) {
        alert("No valid items selected. Please select an item from the dropdown in each row.");
        return;
    }
    document.getElementById("printTotal").textContent = document.getElementById("totalAmount").textContent;
    window.print();
}
</script>
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
   
    <h2>Create New Bill</h2>
    <form>
        <!-- Customer -->
        <div class="form-row">
            <label>Customer</label>
            <select name="customerId" required>
                <option value="">-- Select Customer --</option>
                <% for(Customer c: customers){ %>
                    <option value="<%= c.getAccountNo() %>"><%= c.getName() %> - <%= c.getAccountNo() %></option>
                <% } %>
            </select>
        </div>
        <!-- Items -->
        <div class="form-row">
            <label>Items</label>
            <table class="items-table" id="itemsTable">
                <thead>
                    <tr><th>Item</th><th>Quantity</th><th>Price</th><th>Action</th></tr>
                </thead>
                <tbody></tbody>
            </table>
            <button type="button" class="btn" onclick="addItemRow()">Add Item</button>
        </div>
        <div class="total">
            Total: LKR <span id="totalAmount">0.00</span>
        </div>
        <br>
        <button type="button" class="btn" onclick="printBill()">Print</button>
        <a class="btn" href="dashboard.jsp">Cancel</a>
    </form>
    <!-- Hidden template -->
    <div id="itemSelectTemplate" style="display:none;">
        <select name="itemIds" required>
            <option value="">-- Select Item --</option>
            <% for(Item item: items){ %>
                <option value="<%= item.getId() %>" data-price="<%= item.getPrice() %>"><%= item.getName() %> - LKR <%= item.getPrice() %></option>
            <% } %>
        </select>
    </div>
    <!-- Hidden Print area -->
    <div id="printArea">
        <h2>Pahana Edu</h2>
        <p>Date: <%= currentDate %></p>
        <p>Customer: <span id="printCustomerName"></span> (Acc: <span id="printCustomerAccount"></span>)</p>
        <table>
            <thead>
                <tr><th>Item</th><th>Quantity</th><th>Price</th><th>Subtotal</th></tr>
            </thead>
            <tbody id="printItemsBody"></tbody>
            <tfoot>
                <tr><th colspan="3">Total</th><td>LKR <span id="printTotal"></span></td></tr>
            </tfoot>
        </table>
    </div>

</div>
<footer>&copy; 2025 Pahana Edu Bookshop</footer>
</body>
</html>