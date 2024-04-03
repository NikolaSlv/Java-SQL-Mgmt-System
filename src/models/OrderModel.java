package models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderModel {
    private int orderID;
    private int customerID;
    private LocalDate orderDate;
    private BigDecimal totalPrice;
    private String status;
    private String paymentStatus;

    public OrderModel(int orderID, int customerID, LocalDate orderDate, BigDecimal totalPrice, String status, String paymentStatus) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }
    public int getOrderID() { return orderID; }
    public void setOrderID(int orderID) { this.orderID = orderID; }
    public int getCustomerID() { return customerID; }
    public void setCustomerID(int customerID) { this.customerID = customerID; }
    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}
