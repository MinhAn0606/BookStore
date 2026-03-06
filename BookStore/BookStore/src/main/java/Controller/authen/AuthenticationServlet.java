/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.authen;

import DALs.UserDAO;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class AuthenticationServlet extends HttpServlet {

    private User user = new User();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        set action
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        String url;
//        dua theo action chuyen url
        switch (action) {
            case "login":
                url = "views/authen/login.jsp";
                break;
            case "log-out":
                url = logout(request, response);
                break;
            case "sign-up":
                url = "views/authen/register.jsp";
                break;
            default:
                url = "home";
        }

//         chuyen trang
        request.getRequestDispatcher(url).forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        String url;
        switch (action) {
            case "login":
                url = loginDoPost(request, response);
                break;
            case "sign-up":
                url = signUp(request, response);
                break;
            default:
                url = "home";
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    private String loginDoPost(HttpServletRequest request, HttpServletResponse response) {
//        lay thong tin nguoi dung nhap vao
        String url;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
//        true => ve trang home (set account vao session)
        UserDAO userDAO = new UserDAO();
        User findUser = userDAO.login(username, password);
        if (findUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute(Common.CommonConst.SESSION_USERNAME, findUser);
            url = "home";
        } else {
            request.setAttribute("error", "Username or password incorrect!");
            url = "views/authen/login.jsp";
        }

//        false => quay lai trang login (set thong bao loi)
        return url;
    }

    private String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(Common.CommonConst.SESSION_USERNAME);
        String url = "home";
        return url;
    }

    private String signUp(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String url;

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setRole("User");
        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.register(user);
        if (success) {
            url = "home";
        } else {
            request.setAttribute("error", "Username already exists!");
            url = "views/authen/register.jps";
        }
        return url;

    }

}
