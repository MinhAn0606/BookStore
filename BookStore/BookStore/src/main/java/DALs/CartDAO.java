/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DALs;

import Models.Book;
import Models.Cart;
import Models.CartItem;
import Models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CartDAO extends DB.DBContext {

    public CartDAO() {
        super();

    }

    // ✅ Lấy giỏ hàng theo user_id, nếu chưa có thì trả về null
    public Cart getCartByUserId(int userId) {
        Cart cart = null;
        try {
            String sql = "SELECT * FROM Cart WHERE user_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cart = new Cart();
                cart.setCartId(rs.getInt("cart_id"));
                cart.setUserId(rs.getInt("user_id"));
                cart.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                cart.setItems(getCartItemsByCartId(rs.getInt("cart_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }

    // ✅ Tạo giỏ hàng mới cho user (nếu chưa có)
    public int createCartForUser(int userId) {
        String sql = "INSERT INTO Cart(user_id) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // ✅ Thêm sản phẩm vào giỏ (nếu đã có thì tăng số lượng)
    public void addToCart(int userId, int bookId, int quantity) {
        try {
            int cartId = getCartIdByUserId(userId);

            // Nếu user chưa có giỏ hàng thì tạo mới
            if (cartId == -1) {
                cartId = createCartForUser(userId);
            }

            // Kiểm tra xem sản phẩm đã có trong giỏ chưa
            String checkSql = "SELECT * FROM CartItems WHERE cart_id = ? AND book_id = ?";
            PreparedStatement checkPs = connection.prepareStatement(checkSql);
            checkPs.setInt(1, cartId);
            checkPs.setInt(2, bookId);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                // Cập nhật số lượng nếu đã tồn tại
                String updateSql = "UPDATE CartItems SET quantity = quantity + ? WHERE cart_id = ? AND book_id = ?";
                PreparedStatement updatePs = connection.prepareStatement(updateSql);
                updatePs.setInt(1, quantity);
                updatePs.setInt(2, cartId);
                updatePs.setInt(3, bookId);
                updatePs.executeUpdate();
            } else {
                // Thêm mới nếu chưa có
                String insertSql = "INSERT INTO CartItems(cart_id, book_id, quantity) VALUES (?, ?, ?)";
                PreparedStatement insertPs = connection.prepareStatement(insertSql);
                insertPs.setInt(1, cartId);
                insertPs.setInt(2, bookId);
                insertPs.setInt(3, quantity);
                insertPs.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Lấy danh sách item trong giỏ (kèm thông tin sách)
    public List<CartItem> getCartItemsByCartId(int cartId) {
        List<CartItem> list = new ArrayList<>();
        try {
            String sql = "SELECT ci.*, b.book_id, b.title, b.author, b.price, b.image_url\n"
                    + "                FROM CartItems ci\n"
                    + "                JOIN Books b ON ci.book_id = b.book_id\n"
                    + "                WHERE ci.cart_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setItemId(rs.getInt("item_id"));
                item.setCartId(rs.getInt("cart_id"));
                item.setBookId(rs.getInt("book_id"));
                item.setQuantity(rs.getInt("quantity"));

                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPrice(rs.getDouble("price"));
                book.setImageUrl(rs.getString("image_url"));
                item.setBook(book);

                list.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Xóa toàn bộ item trong giỏ
    public void clearCart(int cartId) {
        String sql = "DELETE FROM CartItems WHERE cart_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Lấy cart_id theo user_id
    public int getCartIdByUserId(int userId) {
        String sql = "SELECT cart_id FROM Cart WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("cart_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // ✅ Xóa giỏ hàng hoàn toàn (sau khi checkout)
    public void deleteCart(int cartId) {
        try {
            PreparedStatement ps1 = connection.prepareStatement("DELETE FROM CartItems WHERE cart_id = ?");
            ps1.setInt(1, cartId);
            ps1.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement("DELETE FROM Cart WHERE cart_id = ?");
            ps2.setInt(1, cartId);
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCartItemQuantity(int cartId, int bookId, int quantity) {
        String sql = "UPDATE CartItems SET quantity = ? WHERE cart_id = ? AND book_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, cartId);
            ps.setInt(3, bookId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCartItem(int cartId, int bookId) {
        String sql = "DELETE FROM CartItems WHERE cart_id = ? AND book_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
