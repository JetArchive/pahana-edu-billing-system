package servlet;

import dao.ItemDAO;
import model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/item")
public class ItemServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ItemDAO dao = new ItemDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("create".equals(action)) {
                String code = req.getParameter("code").trim();
                String name = req.getParameter("name").trim();
                BigDecimal price = new BigDecimal(req.getParameter("price").trim());
                String desc = req.getParameter("description").trim();
                Item it = new Item();
                it.setCode(code); it.setName(name); it.setPrice(price); it.setDescription(desc);
                dao.create(it);
                resp.sendRedirect("items.jsp?msg=created");
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                String code = req.getParameter("code").trim();
                String name = req.getParameter("name").trim();
                BigDecimal price = new BigDecimal(req.getParameter("price").trim());
                String desc = req.getParameter("description").trim();
                Item it = new Item(id, code, name, price, desc);
                dao.update(it);
                resp.sendRedirect("items.jsp?msg=updated");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                dao.delete(id);
                resp.sendRedirect("items.jsp?msg=deleted");
            } else if ("edit".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Item it = dao.findById(id);
                req.setAttribute("item", it);
                req.getRequestDispatcher("addEditItem.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
