package servlet;

import dao.CustomerDAO;
import dao.ItemDAO;
import model.Customer;
import model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Very small JSON endpoint to satisfy "web services" requirement.
 * Example: GET /api?action=listCustomers
 */
@WebServlet("/api")
public class ApiServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomerDAO customerDAO = new CustomerDAO();
    private ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            resp.setContentType("application/json");

            if ("listCustomers".equals(action)) {
                List<Customer> list = customerDAO.listAll();
                resp.getWriter().write(toCustomerJson(list));
            } else if ("listItems".equals(action)) {
                List<Item> list = itemDAO.listAll();
                resp.getWriter().write(toItemJson(list));
            } else {
                resp.getWriter().write("{\"message\":\"unknown action\"}");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private String toCustomerJson(List<Customer> customers) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"customers\":[");
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            sb.append("{")
            .append("\"accountNo\":\"").append(c.getAccountNo()).append("\",")
            .append("\"name\":\"").append(c.getName()).append("\",")
            .append("\"address\":\"").append(c.getAddress()).append("\",")
            .append("\"telephone\":\"").append(c.getTelephone()).append("\"")
            .append("}");
            if (i < customers.size() - 1) sb.append(",");
        }
        sb.append("]}");
        return sb.toString();
    }

    private String toItemJson(List<Item> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"items\":[");
        for (int i = 0; i < items.size(); i++) {
            Item it = items.get(i);
            sb.append("{")
              .append("\"id\":\"").append(it.getId()).append("\",")
              .append("\"name\":\"").append(it.getName()).append("\",")
              .append("\"price\":\"").append(it.getPrice()).append("\"")
              .append("}");
            if (i < items.size() - 1) sb.append(",");
        }
        sb.append("]}");
        return sb.toString();
    }
}
