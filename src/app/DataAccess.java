package app;

import java.sql.Connection;

import models.*;

public interface DataAccess {
    public void connect();
    public Connection getConnection();
    public int disconnect();

    public CustomerModel findCustomer(int customerID);
    public boolean addCustomer(CustomerModel customer);
    public boolean updateCustomer(CustomerModel customer);
    public boolean customerEmailExists(String email);
    public boolean deleteCustomer(int customerID);

    public SupplierModel findSupplier(int supplierID);
    public boolean addSupplier(SupplierModel supplier);
    public boolean updateSupplier(SupplierModel supplier);
    public boolean supplierEmailExists(String email);
    public boolean deleteSupplier(int supplierID);

    public ProductModel findProduct(int productID);
    public boolean addProduct(ProductModel product);
    public boolean updateProduct(ProductModel product);
    public boolean deleteProduct(int productID);

    public OrderModel findOrder(int orderID);
    public boolean addOrder(OrderModel order);
    public boolean updateOrder(OrderModel order);
    public boolean deleteOrder(int orderID);

    public OrderLineModel findOrderLine(int orderLineID);
    public boolean addOrderLine(OrderLineModel orderLine);
    public boolean updateOrderLine(OrderLineModel orderLine);
    public boolean deleteOrderLine(int orderLineID);
    public boolean updateOrderTotalPrice(int orderID);
    public boolean canUpdateProductQuantity(int productID, int quantity);
    public boolean updateProductQuantity(int productID, int quantity);

    public PaymentModel findPayment(int paymentID);
    public boolean addPayment(PaymentModel payment);
    public boolean updatePayment(PaymentModel payment);
    public boolean deletePayment(int paymentID);
    public boolean updateOrderPaymentStatus(int orderID);

    public void executeQueryAndWriteToFile(String query, String fileName, String startDate, String endDate, int cutoff);
}
