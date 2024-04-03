package models;

import java.math.BigDecimal;

public class OrderLineModel {
    private int orderLineID;
    private int orderID;
    private int productID;
    private int quantity;
    private BigDecimal priceAtPurchase;

    public OrderLineModel(int orderLineID, int orderID, int productID, int quantity, BigDecimal priceAtPurchase) {
        this.orderLineID = orderLineID;
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }
    public int getOrderLineID() { return orderLineID; }
    public void setOrderLineID(int orderLineID) { this.orderLineID = orderLineID; }
    public int getOrderID() { return orderID; }
    public void setOrderID(int orderID) { this.orderID = orderID; }
    public int getProductID() { return productID; }
    public void setProductID(int productID) { this.productID = productID; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getPriceAtPurchase() { return priceAtPurchase; }
    public void setPriceAtPurchase(BigDecimal priceAtPurchase) { this.priceAtPurchase = priceAtPurchase; }
}
