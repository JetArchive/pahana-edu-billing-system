package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.CustomerDAO;
import dao.ItemDAO;
import model.Customer;
import model.Item;
import util.JSONUtility;

@WebServlet("/bills")
public class BillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Added serialVersionUID
    private CustomerDAO customerDAO;
    private ItemDAO itemDAO;
    
    @Override
    public void init() {
        customerDAO = new CustomerDAO();
        itemDAO = new ItemDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        int units = Integer.parseInt(request.getParameter("units"));
        
        Customer customer = customerDAO.getCustomer(accountNumber);
        Item item = itemDAO.getItem(itemId);
        
        if (customer != null && item != null) {
            BigDecimal total = item.getPricePerUnit().multiply(new BigDecimal(units));
            
            // Update customer units consumed
            customer.setUnitsConsumed(customer.getUnitsConsumed() + units);
            customerDAO.updateCustomer(customer);
            
            // Create bill response
            String billJson = JSONUtility.billToJSON(
                customer.getName(),
                customer.getAccountNumber(),
                item.getName(),
                units,
                item.getPricePerUnit(),
                total
            );
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(billJson);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
