package dao;

import model.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    public Admin findByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "SELECT * FROM admins WHERE username=? AND password=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password); // plaintext for simplicity
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Admin(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
                        rs.getString("fullname"), rs.getString("email"));
            }
        }
        return null;
    }

    public List<Admin> listAll() throws SQLException {
        List<Admin> list = new ArrayList<>();
        String sql = "SELECT * FROM admins ORDER BY id";
        try (Connection c = DBConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Admin(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
                        rs.getString("fullname"), rs.getString("email")));
            }
        }
        return list;
    }

    public void create(Admin a) throws SQLException {
        String sql = "INSERT INTO admins (username,password,fullname,email) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            ps.setString(3, a.getFullname());
            ps.setString(4, a.getEmail());
            ps.executeUpdate();
        }
    }

    public void update(Admin a) throws SQLException {
        String sql = "UPDATE admins SET username=?, password=?, fullname=?, email=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            ps.setString(3, a.getFullname());
            ps.setString(4, a.getEmail());
            ps.setInt(5, a.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM admins WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Admin findById(int id) throws SQLException {
        String sql = "SELECT * FROM admins WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Admin(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
                        rs.getString("fullname"), rs.getString("email"));
            }
        }
        return null;
    }
}
