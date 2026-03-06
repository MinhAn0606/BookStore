/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.user;

import Common.CommonConst;
import DALs.CartDAO;
import DALs.OrderDAO;
import Models.Book;
import Models.Cart;
import Models.CartItem;
import Models.Order;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Admin
 */
public class PaymentServlet extends HttpServlet {

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
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        switch (action) {
            case "view-cart":
                viewCart(request, response);

                request.getRequestDispatcher("views/user/payment/cart.jsp").forward(request, response);
                break;
            default:

                request.getRequestDispatcher("views/user/payment/cart.jsp").forward(request, response);
        }

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
        switch (action) {
            case "add-product":
                addProduct(request, response);
                request.getRequestDispatcher("views/user/payment/cart.jsp").forward(request, response);
                break;
            case "change-quantity":
                changeQuantity(request, response);
                request.getRequestDispatcher("views/user/payment/cart.jsp").forward(request, response);
                break;
            case "delete":
                delete(request, response);
                request.getRequestDispatcher("views/user/payment/cart.jsp").forward(request, response);
                break;
            case "check-out":
                checkOut(request, response);
                request.getRequestDispatcher("views/user/payment/cart.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect("home");
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        if (user == null) {
            response.sendRedirect("authen?action=login");
            return;
        }

        try {

            int bookId = Integer.parseInt(request.getParameter("id"));
            int quantity = Integer.parseInt(request.getParameter("stock"));

            CartDAO cartDAO = new CartDAO();
            cartDAO.addToCart(user.getUserId(), bookId, quantity);

            Cart updatedCart = cartDAO.getCartByUserId(user.getUserId());

            session.setAttribute("cart", updatedCart);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("home");
        }
    }

    private void changeQuantity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");  // user đã login
        CartDAO cartDAO = new CartDAO();

        try {
            //
            int bookId = Integer.parseInt(request.getParameter("id"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity < 1) {
                quantity = 1;
            }

            //
            int cartId = cartDAO.getCartIdByUserId(user.getUserId());
            if (cartId == -1) {
                response.sendRedirect("payment");
                return;
            }

            //Cập nhật quantity trong DB
            cartDAO.updateCartItemQuantity(cartId, bookId, quantity);

            Cart updatedCart = cartDAO.getCartByUserId(user.getUserId());
            session.setAttribute("cart", updatedCart);

            response.sendRedirect("payment");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("payment");
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("username");  // user hiện tại
            int bookId = Integer.parseInt(request.getParameter("id"));

            CartDAO cartDAO = new CartDAO();

            int cartId = cartDAO.getCartIdByUserId(user.getUserId());
            if (cartId == -1) {
                response.sendRedirect("payment");
                return;
            }

            cartDAO.deleteCartItem(cartId, bookId);
            Cart updatedCart = cartDAO.getCartByUserId(user.getUserId());
            session.setAttribute("cart", updatedCart);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("payment");
        }

    }

    private double findPriceById(List<Book> list, int bookId) {
        for (Book obj : list) {
            if (obj.getBookId() == bookId) {
                return obj.getPrice();
            }
        }
        return 0;
    }

    private int calculateAmount(Cart cart, List<Book> list) {
        int amount = 0;
        for (CartItem item : cart.getItems()) {
            amount += (item.getQuantity() * findPriceById(list, item.getBookId()));
        }
        return amount;
    }

    private void checkOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");

        CartDAO cartDAO = new CartDAO();
        OrderDAO orderDAO = new OrderDAO();

        Cart cart = cartDAO.getCartByUserId(user.getUserId());
        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("payment");
            return;
        }

        double total = cart.getItems().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();

        int orderId = orderDAO.createOrder(user.getUserId(), total);
        orderDAO.addOrderItems(orderId, cart.getItems());

        // Xóa giỏ hàng sau khi thanh toán
        cartDAO.deleteCart(cart.getCartId());

        response.sendRedirect("order-success.jsp");
    }

    private void viewCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");

        if (user == null) {
            response.sendRedirect("authen?action=login");
            return;
        }

        CartDAO cartDAO = new CartDAO();
        Cart cart = cartDAO.getCartByUserId(user.getUserId());

        if (cart == null) {
            cart = new Cart();
            cart.setUserId(user.getUserId());
        }

        session.setAttribute("cart", cart);
    }

}
