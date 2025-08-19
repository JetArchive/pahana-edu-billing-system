package servlet;

import dao.AdminDAO;
import model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminDAO dao = new AdminDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        String idStr = req.getParameter("id");
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();
        String fullname = req.getParameter("fullname").trim();
        String email = req.getParameter("email").trim();

        // Basic validation
        if (username.isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "Username and password are required.");
            forwardBack(req, resp, idStr, username, password, fullname, email);
            return;
        }

        Admin a = new Admin();
        if (idStr != null && !idStr.isEmpty()) {
            a.setId(Integer.parseInt(idStr));
        }
        a.setUsername(username);
        a.setPassword(password);
        a.setFullname(fullname);
        a.setEmail(email);

        try {
            if ("create".equals(action)) {
                try {
                    dao.create(a);
                    resp.sendRedirect("admins.jsp?msg=created");
                } catch (SQLIntegrityConstraintViolationException ex) {
                    req.setAttribute("error", "Username already exists. Please choose a different one.");
                    req.setAttribute("adminObj", a);
                    req.getRequestDispatcher("addEditAdmin.jsp").forward(req, resp);
                }
            } else if ("update".equals(action)) {
                try {
                    dao.update(a);
                    resp.sendRedirect("admins.jsp?msg=updated");
                } catch (SQLIntegrityConstraintViolationException ex) {
                    req.setAttribute("error", "Username already exists. Please choose a different one.");
                    req.setAttribute("adminObj", a);
                    req.getRequestDispatcher("addEditAdmin.jsp").forward(req, resp);
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
                int id = Integer.parseInt(req.getParameter("id"));
                dao.delete(id);
                resp.sendRedirect("admins.jsp?msg=deleted");
            } else if ("edit".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Admin a = dao.findById(id);
                req.setAttribute("adminObj", a);
                req.getRequestDispatcher("addEditAdmin.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /** Helper to preserve input values on error */
    private void forwardBack(HttpServletRequest req, HttpServletResponse resp,
                             String id, String username, String password, String fullname, String email)
            throws ServletException, IOException {
        Admin a = new Admin();
        if (id != null && !id.isEmpty()) a.setId(Integer.parseInt(id));
        a.setUsername(username);
        a.setPassword(password);
        a.setFullname(fullname);
        a.setEmail(email);
        req.setAttribute("adminObj", a);
        req.getRequestDispatcher("addEditAdmin.jsp").forward(req, resp);
    }
}
