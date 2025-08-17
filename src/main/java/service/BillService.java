package service;

import java.math.BigDecimal;
import java.sql.*;
import dao.DBConnection;
import model.Customer;

public class BillService {

    private static final BigDecimal PRICE_PER_UNIT = new BigDecimal("10.00");
    private static final BigDecimal FIXED_CHARGE = new BigDecimal("50.00");

    public static BigDecimal calculateBill(Customer c) {
        int units = c.getUnitsConsumed();
        BigDecimal unitsCost = PRICE_PER_UNIT.multiply(new BigDecimal(units));
        BigDecimal total = unitsCost.add(FIXED_CHARGE);
        return total;
    }

    // New method to count today's bills
    public int countTodayBills() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM bills WHERE DATE(bill_date) = CURDATE()";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
