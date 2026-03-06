/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DALs;

import Models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class UserDAO extends DB.DBContext {

    public UserDAO() {
        super();
    }

    public User login(String username, String pass) {
        String query = "Select * from Users where username = ? and password = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("created_at"));
                return u;

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean register(User user) {
        String query = "INSERT INTO Users (username, password, fullname, email, role) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getRole());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM Users WHERE user_id= ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setFullname(rs.getString("fullname"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                u.setCreatedAt(rs.getString("created_at"));
                return u;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public boolean isUsernameExists(String username) {
        String sql = "SELECT 1 FROM Users WHERE username=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e);;
        }
        return false;
    }

}
