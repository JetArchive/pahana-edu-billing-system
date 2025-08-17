package dao;

import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    public void create(Item it) throws SQLException {
        String sql = "INSERT INTO items (code,name,price,description) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, it.getCode());
            ps.setString(2, it.getName());
            ps.setBigDecimal(3, it.getPrice());
            ps.setString(4, it.getDescription());
            ps.executeUpdate();
        }
    }

    public void update(Item it) throws SQLException {
        String sql = "UPDATE items SET code=?, name=?, price=?, description=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, it.getCode());
            ps.setString(2, it.getName());
            ps.setBigDecimal(3, it.getPrice());
            ps.setString(4, it.getDescription());
            ps.setInt(5, it.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM items WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Item findById(int id) throws SQLException {
        String sql = "SELECT * FROM items WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Item(rs.getInt("id"), rs.getString("code"), rs.getString("name"),
                        rs.getBigDecimal("price"), rs.getString("description"));
            }
        }
        return null;
    }

    public List<Item> listAll() throws SQLException {
        List<Item> list = new ArrayList<>();
        String sql = "SELECT * FROM items ORDER BY name";
        try (Connection c = DBConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Item(rs.getInt("id"), rs.getString("code"), rs.getString("name"),
                        rs.getBigDecimal("price"), rs.getString("description")));
            }
        }
        return list;
    }
 // At the end of ItemDAO class
    public int countAll() throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM items"; // Replace "items" with your table name if different
        try (Connection c = DBConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        return count;
    }

}

