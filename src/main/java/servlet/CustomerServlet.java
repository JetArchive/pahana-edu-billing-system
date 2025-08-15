package servlet;


import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.CustomerDAO;
import model.Customer;
import util.JSONUtility;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Added serialVersionUID
    private CustomerDAO customerDAO;
    
    @Override
    public void init() {
        customerDAO = new CustomerDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        if ("get".equals(action)) {
            String accountNumber = request.getParameter("accountNumber");
            Customer customer = customerDAO.getCustomer(accountNumber);
            
            if (customer != null) {
                String customerJson = JSONUtility.customerToJSON(customer);
                response.getWriter().write(customerJson);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Customer not found\"}");
            }
        } else {
            List<Customer> customers = customerDAO.getAllCustomers();
            String customersJson = JSONUtility.customerListToJSON(customers);
            response.getWriter().write(customersJson);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        int unitsConsumed = Integer.parseInt(request.getParameter("unitsConsumed"));
        
        Customer customer = new Customer(accountNumber, name, address, telephone, unitsConsumed);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        if (customerDAO.addCustomer(customer)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"success\":\"Customer added successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Failed to add customer\"}");
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        int unitsConsumed = Integer.parseInt(request.getParameter("unitsConsumed"));
        
        Customer customer = new Customer(accountNumber, name, address, telephone, unitsConsumed);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        if (customerDAO.updateCustomer(customer)) {
            response.getWriter().write("{\"success\":\"Customer updated successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Failed to update customer\"}");
        }
    }
}
