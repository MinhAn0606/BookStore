/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.admin;

import DALs.BookDAO;
import Models.Book;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;

/**
 *
 * @author Admin
 */
@MultipartConfig
public class ProductAdminServlet extends HttpServlet {

    BookDAO bookDAO = new BookDAO();

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
//        set UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        HttpSession session = request.getSession();
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");

        switch (action) {
            case "add":
                addProductDoPost(request);
                break;
            case "delete":
                deleteProductDoPost(request);
                break;
            case "edit":
                editProductDoPost(request);
                break;
            default:
                throw new AssertionError();
        }
        response.sendRedirect("dashboard");

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

    private void addProductDoPost(HttpServletRequest request) {
        try {
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String category = request.getParameter("category");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            Part part_img = request.getPart("image");

            if (part_img.getSubmittedFileName() == null || part_img.getSubmittedFileName().trim().isEmpty() || part_img == null) {

            } else {

//            Duong dan luu anh
//                String path = request.getServletContext().getRealPath("/images");
//                File dir = new File(path);
                String path = request.getServletContext().getRealPath("/views/img/product");
                File dir = new File(path);

//            Kiem tra duong dan nay ton tai chua
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File image = new File(dir, part_img.getSubmittedFileName());
//            ghi file vao duong dan
                part_img.write(image.getAbsolutePath());

                String contextPath = "/views/img/product" + image.getName();

                Book insertBook = new Book();
                insertBook.setTitle(title);
                insertBook.setAuthor(author);
                insertBook.setCategory(category);
                insertBook.setDescription(description);
                insertBook.setPrice(price);
                insertBook.setStock(stock);
                insertBook.setImageUrl(contextPath);
                bookDAO.addBook(insertBook);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException | ServletException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteProductDoPost(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        bookDAO.deleteBookById(id);
    }

    private void editProductDoPost(HttpServletRequest request) {
        // get data
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("name");
            String author = request.getParameter("author");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("quantity"));
            String description = request.getParameter("description");
            String category = request.getParameter("category");
            String contextPath = null;
            // image
            Part part_img = request.getPart("image");
            if (part_img.getSubmittedFileName() == null
                    || part_img.getSubmittedFileName().trim().isEmpty()
                    || part_img == null) {
//                contextPath = request.getParameter("currentImage");
                String currentImage = request.getParameter("currentImage");
                if (currentImage != null && !currentImage.isEmpty()) {
                    String contextName = request.getContextPath();
                    if (currentImage.startsWith(contextName)) {
                        currentImage = currentImage.substring(contextName.length());
                    }

                }
                contextPath = currentImage;
            } else {
                // duong dan luu anh
                String path = request.getServletContext().getRealPath("/views/img/product");
                File dir = new File(path);
                // xem duongd an nay ton tai chua
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File image = new File(dir, part_img.getSubmittedFileName());
                // ghi file vao trong duong dan
                part_img.write(image.getAbsolutePath());
                // lay ra cai context path cua project
                contextPath = "views/img/product/" + image.getName();

            }

            Book editBook = new Book();
            editBook.setBookId(id);
            editBook.setTitle(title);
            editBook.setAuthor(author);
            editBook.setPrice(price);
            editBook.setStock(stock);
            editBook.setDescription(description);
            editBook.setCategory(category);
            editBook.setImageUrl(contextPath);
            bookDAO.editBookById(editBook);

        } catch (NumberFormatException | IOException | ServletException ex) {
            ex.printStackTrace();
        }
    }

}
