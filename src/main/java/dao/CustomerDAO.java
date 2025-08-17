package dao;

import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public void create(Customer cst) throws SQLException {
        String sql = "INSERT INTO customers (account_no, name, address, telephone, units_consumed) VALUES (?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cst.getAccountNo());
            ps.setString(2, cst.getName());
            ps.setString(3, cst.getAddress());
            ps.setString(4, cst.getTelephone());
            ps.setInt(5, cst.getUnitsConsumed());
            ps.executeUpdate();
        }
    }

    public void update(Customer cst) throws SQLException {
        String sql = "UPDATE customers SET name=?, address=?, telephone=?, units_consumed=? WHERE account_no=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cst.getName());
            ps.setString(2, cst.getAddress());
            ps.setString(3, cst.getTelephone());
            ps.setInt(4, cst.getUnitsConsumed());
            ps.setString(5, cst.getAccountNo());
            ps.executeUpdate();
        }
    }

    public void delete(String accountNo) throws SQLException {
        String sql = "DELETE FROM customers WHERE account_no=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, accountNo);
            ps.executeUpdate();
        }
    }

    public Customer findByAccountNo(String accountNo) throws SQLException {
        String sql = "SELECT * FROM customers WHERE account_no=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, accountNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(rs.getString("account_no"), rs.getString("name"), rs.getString("address"),
                        rs.getString("telephone"), rs.getInt("units_consumed"));
            }
        }
        return null;
    }

    public List<Customer> listAll() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY name";
        try (Connection c = DBConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Customer(rs.getString("account_no"), rs.getString("name"), rs.getString("address"),
                        rs.getString("telephone"), rs.getInt("units_consumed")));
            }
        }
        return list;
    }
    public int countAll() throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM customers";
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
