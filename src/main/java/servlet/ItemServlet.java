package servlet;

import dao.ItemDAO;
import model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet("/item")
public class ItemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ItemDAO dao = new ItemDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            handleCreate(req, resp);
        } else if ("update".equals(action)) {
            handleUpdate(req, resp);
        }
    }

    private void handleCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        String desc = req.getParameter("description");

        // Validation
        if (code == null || code.trim().isEmpty()) {
            req.setAttribute("error", "Item code is required.");
            forwardBack(req, resp, null);
            return;
        }
        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "Item name is required.");
            forwardBack(req, resp, null);
            return;
        }

        BigDecimal price;
        try {
            price = new BigDecimal(priceStr.trim());
            if (price.compareTo(BigDecimal.ZERO) < 0) {
                req.setAttribute("error", "Price cannot be negative.");
                forwardBack(req, resp, null);
                return;
            }
        } catch (Exception e) {
            req.setAttribute("error", "Invalid price format.");
            forwardBack(req, resp, null);
            return;
        }

        Item it = new Item();
        it.setCode(code.trim());
        it.setName(name.trim());
        it.setPrice(price);
        it.setDescription(desc != null ? desc.trim() : "");

        try {
            dao.create(it);
            resp.sendRedirect("items.jsp?msg=created");
        } catch (SQLIntegrityConstraintViolationException ex) {
            req.setAttribute("error", "Item code already exists. Please choose another.");
            req.setAttribute("item", it);
            req.getRequestDispatcher("addEditItem.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        String desc = req.getParameter("description");
        int id = Integer.parseInt(req.getParameter("id"));

        // Validation
        if (code == null || code.trim().isEmpty()) {
            req.setAttribute("error", "Item code is required.");
            forwardBack(req, resp, id);
            return;
        }
        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "Item name is required.");
            forwardBack(req, resp, id);
            return;
        }

        BigDecimal price;
        try {
            price = new BigDecimal(priceStr.trim());
            if (price.compareTo(BigDecimal.ZERO) < 0) {
                req.setAttribute("error", "Price cannot be negative.");
                forwardBack(req, resp, id);
                return;
            }
        } catch (Exception e) {
            req.setAttribute("error", "Invalid price format.");
            forwardBack(req, resp, id);
            return;
        }

        Item it = new Item(id, code.trim(), name.trim(), price, desc != null ? desc.trim() : "");

        try {
            dao.update(it);
            resp.sendRedirect("items.jsp?msg=updated");
        } catch (SQLIntegrityConstraintViolationException ex) {
            req.setAttribute("error", "Item code already exists. Please choose another.");
            req.setAttribute("item", it);
            req.getRequestDispatcher("addEditItem.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
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

    /** Helper: forward back to form with values preserved */
    private void forwardBack(HttpServletRequest req, HttpServletResponse resp, Integer id) throws ServletException, IOException {
        Item it = new Item();
        if (id != null) {
            it.setId(id);
        }
        it.setCode(req.getParameter("code"));
        it.setName(req.getParameter("name"));
        try {
            it.setPrice(new BigDecimal(req.getParameter("price")));
        } catch (Exception ignored) {}
        it.setDescription(req.getParameter("description"));

        req.setAttribute("item", it);
        req.getRequestDispatcher("addEditItem.jsp").forward(req, resp);
    }
}
