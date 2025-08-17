package servlet;

import dao.CustomerDAO;
import model.Customer;
import service.BillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/bill")
public class BillServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomerDAO dao = new CustomerDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accountNo = req.getParameter("accountNo");
        try {
            Customer c = dao.findByAccountNo(accountNo);
            if (c == null) {
                resp.sendRedirect("customers.jsp?msg=notfound");
                return;
            }
            BigDecimal total = BillService.calculateBill(c);
            req.setAttribute("customer", c);
            req.setAttribute("total", total);
            req.getRequestDispatcher("viewBill.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
