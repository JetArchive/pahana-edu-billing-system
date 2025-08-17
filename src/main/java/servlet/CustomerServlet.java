package servlet;

import dao.CustomerDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomerDAO dao = new CustomerDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // create or update
        String action = req.getParameter("action");
        try {
            if ("create".equals(action)) {
                String accountNo = req.getParameter("accountNo").trim();
                String name = req.getParameter("name").trim();
                String addr = req.getParameter("address").trim();
                String tel = req.getParameter("telephone").trim();
                int units = Integer.parseInt(req.getParameter("unitsConsumed").trim());

                // basic validation
                if (accountNo.isEmpty() || name.isEmpty()) {
                    req.setAttribute("error", "Account number and name required.");
                    req.getRequestDispatcher("addEditCustomer.jsp").forward(req, resp); return;
                }

                Customer c = new Customer(accountNo, name, addr, tel, units);
                dao.create(c);
                resp.sendRedirect("customers.jsp?msg=created");
            } else if ("update".equals(action)) {
                String accountNo = req.getParameter("accountNo").trim();
                String name = req.getParameter("name").trim();
                String addr = req.getParameter("address").trim();
                String tel = req.getParameter("telephone").trim();
                int units = Integer.parseInt(req.getParameter("unitsConsumed").trim());

                Customer c = new Customer(accountNo, name, addr, tel, units);
                dao.update(c);
                resp.sendRedirect("customers.jsp?msg=updated");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // delete or fetch for edit
        String action = req.getParameter("action");
        try {
            if ("delete".equals(action)) {
                String accountNo = req.getParameter("accountNo");
                dao.delete(accountNo);
                resp.sendRedirect("customers.jsp?msg=deleted");
            } else if ("edit".equals(action)) {
                String accountNo = req.getParameter("accountNo");
                Customer c = dao.findByAccountNo(accountNo);
                req.setAttribute("customer", c);
                req.getRequestDispatcher("addEditCustomer.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
