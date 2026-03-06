/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Homepage;

import DALs.BookDAO;
import Models.Book;
import Models.PageControl;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HomeServlet extends HttpServlet {

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
        PageControl pageControl = new PageControl();

        List<Book> listBooks = findBookDoGet(request, pageControl);

        List<String> categories = bookDAO.getDistinctCategories();
        HttpSession session = request.getSession();
        session.setAttribute("listBooks", listBooks);
        session.setAttribute("categories", categories);
        request.setAttribute("pageControl", pageControl);

        request.getRequestDispatcher("views/home.jsp").forward(request, response);
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
        response.sendRedirect("home");
    }

    private List<Book> findBookDoGet(HttpServletRequest request, PageControl pagecontrol) {
        List<Book> listBooks;
        String pageRaw = request.getParameter("page");
        String requestURL = request.getRequestURL().toString();
        int totalRecord;
        int page;
        try {
            page = Integer.parseInt(pageRaw);
            if (page < 0) {
                page = 1;
            }
        } catch (Exception e) {
            page = 1;
        }
        String actionSearch = request.getParameter("search") == null ? "default" : request.getParameter("search");
        switch (actionSearch) {
            case "category":
                String categoryId = request.getParameter("categoryId").trim();
                totalRecord = bookDAO.findTotalRecordByCategory(categoryId);
                listBooks = bookDAO.searchBookByCategory(categoryId, page);
                pagecontrol.setUrlPattern(requestURL + "?search=category&categoryId=" + categoryId + "&");
                break;
            case "searchbar":
                String keyword = request.getParameter("keyword").trim();
                totalRecord = bookDAO.findTotalRecordByName(keyword);
                listBooks = bookDAO.searchBooks(keyword);
                pagecontrol.setUrlPattern(requestURL + "?search=searchbar&keyword=" + keyword + "&");

                break;          
            default:
                totalRecord = bookDAO.findTotalRecord();
                listBooks = bookDAO.getAllBook(page);
                pagecontrol.setUrlPattern(requestURL + "?");
        }
//        total record
//        total page
        int totalPage = (totalRecord % Common.CommonConst.RECORD_PER_PAGE) == 0
                ? totalRecord / Common.CommonConst.RECORD_PER_PAGE
                : totalRecord / Common.CommonConst.RECORD_PER_PAGE + 1;
//        set total record page, page vao pageControl
        pagecontrol.setPage(page);
        pagecontrol.setTotalPage(totalPage);
        pagecontrol.setTotalRecord(totalRecord);

        return listBooks;

    }

}
