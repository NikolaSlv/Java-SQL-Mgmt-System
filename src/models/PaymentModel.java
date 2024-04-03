package models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentModel {
    private int paymentID;
    private int orderID;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String method;

    public PaymentModel(int paymentID, int orderID, BigDecimal amount, LocalDate paymentDate, String method) {
        this.paymentID = paymentID;
        this.orderID = orderID;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.method = method;
    }
    public int getPaymentID() { return paymentID; }
    public void setPaymentID(int paymentID) { this.paymentID = paymentID; }
    public int getOrderID() { return orderID; }
    public void setOrderID(int orderID) { this.orderID = orderID; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
}
