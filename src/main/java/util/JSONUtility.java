package util;

import java.util.List;
import model.Customer;
import model.Item;

public class JSONUtility {
    public static String customerToJSON(Customer customer) {
        return "{\"accountNumber\":\"" + escape(customer.getAccountNumber()) + 
               "\",\"name\":\"" + escape(customer.getName()) + 
               "\",\"address\":\"" + escape(customer.getAddress()) + 
               "\",\"telephone\":\"" + escape(customer.getTelephone()) + 
               "\",\"unitsConsumed\":" + customer.getUnitsConsumed() + "}";
    }

    public static String customerListToJSON(List<Customer> customers) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < customers.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(customerToJSON(customers.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }

    public static String itemToJSON(Item item) {
        return "{\"id\":" + item.getId() + 
               ",\"name\":\"" + escape(item.getName()) + 
               "\",\"description\":\"" + escape(item.getDescription()) + 
               "\",\"pricePerUnit\":" + item.getPricePerUnit() + "}";
    }

    public static String itemListToJSON(List<Item> items) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(itemToJSON(items.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }

    public static String billToJSON(String customerName, String accountNumber, String itemName, 
                                   int units, java.math.BigDecimal pricePerUnit, java.math.BigDecimal total) {
        return "{\"customerName\":\"" + escape(customerName) + 
               "\",\"accountNumber\":\"" + escape(accountNumber) + 
               "\",\"itemName\":\"" + escape(itemName) + 
               "\",\"units\":" + units + 
               ",\"pricePerUnit\":" + pricePerUnit + 
               ",\"total\":" + total + "}";
    }

    private static String escape(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\r", "\\r")
                  .replace("\n", "\\n")
                  .replace("\t", "\\t");
    }
}
