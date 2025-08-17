package servlet;

import dao.AdminDAO;
import model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AdminDAO dao = new AdminDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("create".equals(action)) {
                Admin a = new Admin();
                a.setUsername(req.getParameter("username").trim());
                a.setPassword(req.getParameter("password").trim());
                a.setFullname(req.getParameter("fullname").trim());
                a.setEmail(req.getParameter("email").trim());
                dao.create(a);
                resp.sendRedirect("admins.jsp?msg=created");
            } else if ("update".equals(action)) {
                Admin a = new Admin();
                a.setId(Integer.parseInt(req.getParameter("id")));
                a.setUsername(req.getParameter("username").trim());
                a.setPassword(req.getParameter("password").trim());
                a.setFullname(req.getParameter("fullname").trim());
                a.setEmail(req.getParameter("email").trim());
                dao.update(a);
                resp.sendRedirect("admins.jsp?msg=updated");
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
}
