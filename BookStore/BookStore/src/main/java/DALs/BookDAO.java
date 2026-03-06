/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DALs;

import Models.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class BookDAO extends DB.DBContext {

    public BookDAO() {
        super();
    }

    public List<Book> getAllBook() {
        List<Book> listBook = new ArrayList<>();
        String query = "Select * from Books";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book b = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("image_url"),
                        rs.getString("created_at")
                );
                listBook.add(b);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listBook;
    }

    public List<Book> getAllBook(int page) {
        List<Book> listBook = new ArrayList<>();
        String query = "Select * from Books order by book_id"
                + " offset ? rows fetch next ? rows only";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, (page - 1) * Common.CommonConst.RECORD_PER_PAGE);
            ps.setInt(2, Common.CommonConst.RECORD_PER_PAGE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book b = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("image_url"),
                        rs.getString("created_at")
                );
                listBook.add(b);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listBook;
    }

    public Book getBookById(int id) {
        String query = "Select * from Books where book_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book b = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("image_url"),
                        rs.getString("created_at")
                );
                return b;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    public List<Book> searchBooks(String key) {
        List<Book> list = new ArrayList<>();
        String query = "Select * from Books where title like ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, "%" + key + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book b = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("image_url"),
                        rs.getString("created_at")
                );

                list.add(b);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<String> getDistinctCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM Books WHERE category IS NOT NULL AND category <> ''";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<Book> searchBookByCategory(String category, int page) {
        List<Book> list = new ArrayList<>();
        String query = "Select * from Books where category = ? order by book_id"
                + " offset ? rows fetch next ? rows only";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, category);
            ps.setInt(2, (page - 1) * Common.CommonConst.RECORD_PER_PAGE);
            ps.setInt(3, Common.CommonConst.RECORD_PER_PAGE);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book b = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("image_url"),
                        rs.getString("created_at")
                );

                list.add(b);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public int findTotalRecordByCategory(String categoryId) {
        String sql = "SELECT count(*)\n"
                + "  FROM Books\n"
                + "  where category = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, categoryId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;

    }

    public int findTotalRecordByName(String keyword) {
        String sql = "SELECT count(*)\n"
                + "  FROM Books\n"
                + "  where title like ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public int findTotalRecord() {
        String sql = "SELECT count(*)\n"
                + "  FROM Books\n";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int a = rs.getInt(1);
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public boolean addBook(Book book) {
        String sql = "INSERT INTO Books (title, author, category, description, price, stock, image_url, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE())";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setString(4, book.getDescription());
            ps.setDouble(5, book.getPrice());
            ps.setInt(6, book.getStock());
            ps.setString(7, book.getImageUrl());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBookById(int id) {
        String sql = "DELETE FROM Books WHERE book_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editBookById(Book book) {
        String sql = "UPDATE Books "
                + "SET title = ?, author = ?, category = ?, description = ?, "
                + "price = ?, stock = ?, image_url = ? "
                + "WHERE book_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setString(4, book.getDescription());
            ps.setDouble(5, book.getPrice());
            ps.setInt(6, book.getStock());
            ps.setString(7, book.getImageUrl());
            ps.setInt(8, book.getBookId());

            int rows = ps.executeUpdate();
            return rows > 0; // true nếu cập nhật thành công

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
