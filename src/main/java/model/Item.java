package model;

import java.math.BigDecimal;

public class Item {
    private int id;
    private String code;
    private String name;
    private BigDecimal price;
    private String description;

    public Item() {}
    public Item(int id, String code, String name, BigDecimal price, String description) {
        this.id = id; this.code = code; this.name = name; this.price = price; this.description = description;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
