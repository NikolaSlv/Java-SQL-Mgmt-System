package models;

import java.math.BigDecimal;

public class ProductModel {
    private int productID;
    private int supplierID;
    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;
    private BigDecimal supplyPrice;
    private String expiration;

    public ProductModel(int productID, int supplierID, String name, String description, int quantity, BigDecimal price, BigDecimal supplyPrice, String expiration) {
        this.productID = productID;
        this.supplierID = supplierID;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.supplyPrice = supplyPrice;
        this.expiration = expiration;
    }
    public int getProductID() { return productID; }
    public void setProductID(int productID) { this.productID = productID; }
    public int getSupplierID() { return supplierID; }
    public void setSupplierID(int supplierID) { this.supplierID = supplierID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getSupplyPrice() { return supplyPrice; }
    public void setSupplyPrice(BigDecimal supplyPrice) { this.supplyPrice = supplyPrice; }
    public String getExpiration() { return expiration; }
    public void setExpiration(String expiration) { this.expiration = expiration; }
}
