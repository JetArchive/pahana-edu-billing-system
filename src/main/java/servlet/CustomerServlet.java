package servlet;

import dao.CustomerDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO dao = new CustomerDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            String accountNo = req.getParameter("accountNo").trim();
            String name = req.getParameter("name").trim();
            String addr = req.getParameter("address").trim();
            String tel = req.getParameter("telephone").trim();
            int units = 0;
            try {
                units = Integer.parseInt(req.getParameter("unitsConsumed").trim());
                if (units < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Units consumed must be a non-negative number.");
                forwardBack(req, resp, accountNo, name, addr, tel, req.getParameter("unitsConsumed"));
                return;
            }

            // Basic validation
            if (accountNo.isEmpty() || name.isEmpty()) {
                req.setAttribute("error", "Account number and name are required.");
                forwardBack(req, resp, accountNo, name, addr, tel, String.valueOf(units));
                return;
            }

            Customer c = new Customer(accountNo, name, addr, tel, units);

            if ("create".equals(action)) {
                try {
                    dao.create(c);
                    resp.sendRedirect("customers.jsp?msg=created");
                } catch (SQLIntegrityConstraintViolationException ex) {
                    req.setAttribute("error", "Account number already exists. Please choose a different one.");
                    req.setAttribute("customer", c);
                    req.getRequestDispatcher("addEditCustomer.jsp").forward(req, resp);
                }
            } else if ("update".equals(action)) {
                try {
                    dao.update(c);
                    resp.sendRedirect("customers.jsp?msg=updated");
                } catch (SQLIntegrityConstraintViolationException ex) {
                    req.setAttribute("error", "Account number already exists. Please choose a different one.");
                    req.setAttribute("customer", c);
                    req.getRequestDispatcher("addEditCustomer.jsp").forward(req, resp);
                }
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    /** Helper to preserve input values on error */
    private void forwardBack(HttpServletRequest req, HttpServletResponse resp,
                             String accountNo, String name, String addr, String tel, String units) 
            throws ServletException, IOException {
        Customer c = new Customer();
        c.setAccountNo(accountNo);
        c.setName(name);
        c.setAddress(addr);
        c.setTelephone(tel);
        try { c.setUnitsConsumed(Integer.parseInt(units)); } catch (Exception ignored) {}
        req.setAttribute("customer", c);
        req.getRequestDispatcher("addEditCustomer.jsp").forward(req, resp);
    }
}
