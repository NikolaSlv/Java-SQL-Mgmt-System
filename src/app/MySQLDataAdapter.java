package app;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import models.*;

public class MySQLDataAdapter implements DataAccess {

    private Connection connection = null;
    private final String serverUrl = "jdbc:mysql://localhost:3306";
    private final String dbName = "store";
    private final String dbUser = "root";
    private final String dbPass = "admin";
    
    public void connect() {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            
            try (Connection conn = DriverManager.getConnection(serverUrl, dbUser, dbPass);
                 Statement stmt = conn.createStatement()) {

                
                ResultSet rs = stmt.executeQuery("SHOW DATABASES LIKE '" + dbName + "'");
                if (!rs.next()) {
                    
                    stmt.executeUpdate("CREATE DATABASE " + dbName);
                    System.out.println("Database created: " + dbName);
                }
                
                
                connection = DriverManager.getConnection(serverUrl + "/" + dbName, dbUser, dbPass);
            } catch (SQLException ex) {
                System.out.println("MySQL database is not ready. System exits with error! " + ex.getMessage());
                ex.printStackTrace();
                System.exit(2);
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("MySQL Driver not found. System exits with error!");
            ex.printStackTrace();
            System.exit(1);
        }
    }
    @Override
    public Connection getConnection() {
        return connection;
    }
    @Override
    public int disconnect() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("MySQL database cannot be closed. System exits with error!" + ex.getMessage());
        }
        return 0;
    }

    @Override
    public CustomerModel findCustomer(int customerID) {
        CustomerModel customer = null;
        String sql = "SELECT * FROM Customers WHERE CustomerID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                customer = new CustomerModel(
                    resultSet.getInt("CustomerID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Email"),
                    resultSet.getString("Phone"),
                    resultSet.getString("Address")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customer;
    }
    @Override
    public boolean addCustomer(CustomerModel customer) {
        String sql = "INSERT INTO Customers (CustomerID, Name, Email, Phone, Address) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customer.getCustomerID());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPhone());
            statement.setString(5, customer.getAddress());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean updateCustomer(CustomerModel customer) {
        String sql = "UPDATE Customers SET Name = ?, Email = ?, Phone = ?, Address = ? WHERE CustomerID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getAddress());
            statement.setInt(5, customer.getCustomerID());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean customerEmailExists(String email) {
        String sql = "SELECT * FROM Customers WHERE Email = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean deleteCustomer(int customerID) {
        String sql = "DELETE FROM Customers WHERE CustomerID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerID);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public SupplierModel findSupplier(int supplierID) {
        SupplierModel supplier = null;
        String sql = "SELECT * FROM Suppliers WHERE SupplierID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, supplierID);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                supplier = new SupplierModel(
                    resultSet.getInt("SupplierID"),
                    resultSet.getString("Name"),
                    resultSet.getString("ContactName"),
                    resultSet.getString("Email"),
                    resultSet.getString("Phone"),
                    resultSet.getString("Address")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return supplier;
    }
    @Override
    public boolean addSupplier(SupplierModel supplier) {
        String sql = "INSERT INTO Suppliers (SupplierID, Name, ContactName, Email, Phone, Address) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, supplier.getSupplierID());
            statement.setString(2, supplier.getName());
            statement.setString(3, supplier.getContactName());
            statement.setString(4, supplier.getEmail());
            statement.setString(5, supplier.getPhone());
            statement.setString(6, supplier.getAddress());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean updateSupplier(SupplierModel supplier) {
        String sql = "UPDATE Suppliers SET Name = ?, ContactName = ?, Email = ?, Phone = ?, Address = ? WHERE SupplierID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getContactName());
            statement.setString(3, supplier.getEmail());
            statement.setString(4, supplier.getPhone());
            statement.setString(5, supplier.getAddress());
            statement.setInt(6, supplier.getSupplierID());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean supplierEmailExists(String email) {
        String sql = "SELECT * FROM Suppliers WHERE Email = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean deleteSupplier(int supplierID) {
        String sql = "DELETE FROM Suppliers WHERE SupplierID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, supplierID);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ProductModel findProduct(int productID) {
        ProductModel product = null;
        String sql = "SELECT * FROM Products WHERE ProductID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productID);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                product = new ProductModel(
                    resultSet.getInt("ProductID"),
                    resultSet.getInt("SupplierID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Description"),
                    resultSet.getInt("Quantity"),
                    resultSet.getBigDecimal("Price"),
                    resultSet.getBigDecimal("SupplyPrice"),
                    resultSet.getString("Expiration")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return product;
    }
    @Override
    public boolean addProduct(ProductModel product) {
        String sql = "INSERT INTO Products (ProductID, SupplierID, Name, Description, Quantity, Price, SupplyPrice, Expiration) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, product.getProductID());
            statement.setInt(2, product.getSupplierID());
            statement.setString(3, product.getName());
            statement.setString(4, product.getDescription());
            statement.setInt(5, product.getQuantity());
            statement.setBigDecimal(6, product.getPrice());
            statement.setBigDecimal(7, product.getSupplyPrice());
            statement.setString(8, product.getExpiration());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean updateProduct(ProductModel product) {
        String sql = "UPDATE Products SET SupplierID = ?, Name = ?, Description = ?, Quantity = ?, Price = ?, SupplyPrice = ?, Expiration = ? WHERE ProductID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, product.getSupplierID());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getQuantity());
            statement.setBigDecimal(5, product.getPrice());
            statement.setBigDecimal(6, product.getSupplyPrice());
            statement.setString(7, product.getExpiration());
            statement.setInt(8, product.getProductID());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean deleteProduct(int productID) {
        String sql = "DELETE FROM Products WHERE ProductID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productID);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public OrderModel findOrder(int orderID) {
        OrderModel order = null;
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderID);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                order = new OrderModel(
                    resultSet.getInt("OrderID"),
                    resultSet.getInt("CustomerID"),
                    resultSet.getDate("OrderDate").toLocalDate(),
                    resultSet.getBigDecimal("TotalPrice"),
                    resultSet.getString("Status"),
                    resultSet.getString("PaymentStatus")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return order;
    }
    @Override
    public boolean addOrder(OrderModel order) {
        String sql = "INSERT INTO Orders (OrderID, CustomerID, OrderDate, TotalPrice, Status, PaymentStatus) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getOrderID());
            statement.setInt(2, order.getCustomerID());
            statement.setDate(3, Date.valueOf(order.getOrderDate()));
            statement.setBigDecimal(4, BigDecimal.ZERO);
            statement.setString(5, order.getStatus());
            statement.setString(6, order.getPaymentStatus());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean updateOrder(OrderModel order) {
        String sql = "UPDATE Orders SET CustomerID = ?, OrderDate = ?, TotalPrice = ?, Status = ?, PaymentStatus = ? WHERE OrderID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getCustomerID());
            statement.setDate(2, Date.valueOf(order.getOrderDate()));
            statement.setBigDecimal(3, order.getTotalPrice());
            statement.setString(4, order.getStatus());
            statement.setString(5, order.getPaymentStatus());
            statement.setInt(6, order.getOrderID());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean deleteOrder(int orderID) {
        String sql = "DELETE FROM Orders WHERE OrderID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderID);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public OrderLineModel findOrderLine(int orderLineID) {
        OrderLineModel orderLine = null;
        String sql = "SELECT * FROM OrderLines WHERE OrderLineID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderLineID);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                orderLine = new OrderLineModel(
                    resultSet.getInt("OrderLineID"),
                    resultSet.getInt("OrderID"),
                    resultSet.getInt("ProductID"),
                    resultSet.getInt("Quantity"),
                    resultSet.getBigDecimal("priceAtPurchase")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orderLine;
    }
    @Override
    public boolean addOrderLine(OrderLineModel orderLine) {
        int updateQuantity = orderLine.getQuantity();

        if (!canUpdateProductQuantity(orderLine.getProductID(), updateQuantity)) {
            return false;
        }

        String sql = "INSERT INTO OrderLines (OrderLineID, OrderID, ProductID, Quantity, priceAtPurchase) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderLine.getOrderLineID());
            statement.setInt(2, orderLine.getOrderID());
            statement.setInt(3, orderLine.getProductID());
            statement.setInt(4, orderLine.getQuantity());
            statement.setBigDecimal(5, orderLine.getPriceAtPurchase());
            statement.executeUpdate();
            
            updateProductQuantity(orderLine.getProductID(), updateQuantity);
            return updateOrderTotalPrice(orderLine.getOrderID());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean updateOrderLine(OrderLineModel orderLine) {
        OrderLineModel oldOrderLine = findOrderLine(orderLine.getOrderLineID());
        int oldQuantity = oldOrderLine.getQuantity();
        int updateQuantity = orderLine.getQuantity() - oldQuantity;

        if (!canUpdateProductQuantity(orderLine.getProductID(), updateQuantity)) {
            return false;
        }
        
        String sql = "UPDATE OrderLines SET OrderID = ?, ProductID = ?, Quantity = ?, priceAtPurchase = ? WHERE OrderLineID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderLine.getOrderID());
            statement.setInt(2, orderLine.getProductID());
            statement.setInt(3, orderLine.getQuantity());
            statement.setBigDecimal(4, orderLine.getPriceAtPurchase());
            statement.setInt(5, orderLine.getOrderLineID());
            statement.executeUpdate();
                       
            updateProductQuantity(orderLine.getProductID(), updateQuantity);
            return updateOrderTotalPrice(orderLine.getOrderID());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean deleteOrderLine(int orderLineID) {
        String sql = "DELETE FROM OrderLines WHERE OrderLineID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            OrderLineModel orderLine = findOrderLine(orderLineID);
            statement.setInt(1, orderLineID);
            statement.executeUpdate();
            
            return updateOrderTotalPrice(orderLine.getOrderID());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean updateOrderTotalPrice(int orderID) {
        String sqlCalculateTotal = "SELECT SUM(Quantity * priceAtPurchase) AS TotalPrice FROM OrderLines WHERE OrderID = ?";
        String sqlUpdateTotal = "UPDATE Orders SET TotalPrice = ? WHERE OrderID = ?";
        String sqlCalculatePayments = "SELECT SUM(Amount) AS TotalPayments FROM Payments WHERE OrderID = ?";
        String sqlUpdatePaymentStatus = "UPDATE Orders SET PaymentStatus = ? WHERE OrderID = ?";
    
        try {
            
            PreparedStatement calculateStmt = connection.prepareStatement(sqlCalculateTotal);
            calculateStmt.setInt(1, orderID);
            ResultSet rs = calculateStmt.executeQuery();
            BigDecimal newTotalPrice = BigDecimal.ZERO;
            if (rs.next()) {
                newTotalPrice = rs.getBigDecimal("TotalPrice");
                if (newTotalPrice == null) newTotalPrice = BigDecimal.ZERO;
            }
    
            
            PreparedStatement calculatePaymentsStmt = connection.prepareStatement(sqlCalculatePayments);
            calculatePaymentsStmt.setInt(1, orderID);
            rs = calculatePaymentsStmt.executeQuery();
            BigDecimal totalPayments = BigDecimal.ZERO;
            if (rs.next()) {
                totalPayments = rs.getBigDecimal("TotalPayments");
                if (totalPayments == null) totalPayments = BigDecimal.ZERO;
            }
    
            
            PreparedStatement updateStmt = connection.prepareStatement(sqlUpdateTotal);
            updateStmt.setBigDecimal(1, newTotalPrice);
            updateStmt.setInt(2, orderID);
            updateStmt.executeUpdate();
    
            
            String newPaymentStatus = "Unpaid"; 
            if (totalPayments.compareTo(BigDecimal.ZERO) > 0) {
                if (newTotalPrice.compareTo(totalPayments) > 0) {
                    newPaymentStatus = "Partial";
                } else {
                    newPaymentStatus = "Paid";
                }
            }
    
            
            PreparedStatement updatePaymentStatusStmt = connection.prepareStatement(sqlUpdatePaymentStatus);
            updatePaymentStatusStmt.setString(1, newPaymentStatus);
            updatePaymentStatusStmt.setInt(2, orderID);
            updatePaymentStatusStmt.executeUpdate();
    
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }        
    @Override
    public boolean canUpdateProductQuantity(int productID, int quantity) {
        String checkQuantitySql = "SELECT Quantity FROM Products WHERE ProductID = ?";
    
        try {
            PreparedStatement checkStmt = connection.prepareStatement(checkQuantitySql);
    
            checkStmt.setInt(1, productID);
            ResultSet rs = checkStmt.executeQuery();
    
            if (rs.next()) {
                int currentQuantity = rs.getInt("Quantity");
                
                if (currentQuantity - quantity >= 0) {
                    
                    return true;
                } else {
                    
                    System.out.println("Operation denied: Not enough stock.");
                    return false;
                }
            } else {
                
                System.out.println("Product not found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }      
    @Override
    public boolean updateProductQuantity(int productID, int quantity) {
        
        String sql = "UPDATE Products SET Quantity = Quantity - ? WHERE ProductID = ?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            
            stmt.setInt(1, quantity); 
            stmt.setInt(2, productID); 

            
            int rowsAffected = stmt.executeUpdate();
            
            
            if (rowsAffected > 0) {
                System.out.println("Product quantity decreased successfully.");
                return true; 
            } else {
                System.out.println("No product found with the specified ID or quantity is already at minimum.");
                return false; 
            }
        } catch (SQLException e) {
            System.err.println("SQL error during product quantity decrease: " + e.getMessage());
            e.printStackTrace();
            return false; 
        }
    }    

    @Override
    public PaymentModel findPayment(int paymentID) {
        PaymentModel payment = null;
        String sql = "SELECT * FROM Payments WHERE PaymentID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, paymentID);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                payment = new PaymentModel(
                    resultSet.getInt("PaymentID"),
                    resultSet.getInt("OrderID"),
                    resultSet.getBigDecimal("Amount"),
                    resultSet.getDate("PaymentDate").toLocalDate(),
                    resultSet.getString("Method")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return payment;
    }
    @Override
    public boolean addPayment(PaymentModel payment) {
        String sqlInsert = "INSERT INTO Payments (PaymentID, OrderID, Amount, PaymentDate, Method) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sqlInsert)) {
            statement.setInt(1, payment.getPaymentID());
            statement.setInt(2, payment.getOrderID());
            statement.setBigDecimal(3, payment.getAmount());
            statement.setDate(4, Date.valueOf(payment.getPaymentDate()));
            statement.setString(5, payment.getMethod());
            statement.executeUpdate();
            
            
            return updateOrderPaymentStatus(payment.getOrderID());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean updatePayment(PaymentModel payment) {
        String sql = "UPDATE Payments SET OrderID = ?, Amount = ?, PaymentDate = ?, Method = ? WHERE PaymentID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, payment.getOrderID());
            statement.setBigDecimal(2, payment.getAmount());
            statement.setDate(3, Date.valueOf(payment.getPaymentDate()));
            statement.setString(4, payment.getMethod());
            statement.setInt(5, payment.getPaymentID());
            statement.executeUpdate();
            
            
            return updateOrderPaymentStatus(payment.getOrderID());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean deletePayment(int paymentID) {
        String sql = "DELETE FROM Payments WHERE PaymentID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int orderID = findPayment(paymentID).getOrderID();

            statement.setInt(1, paymentID);
            statement.executeUpdate();
            
            
            return updateOrderPaymentStatus(orderID);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean updateOrderPaymentStatus(int orderID) {
        String sqlCalculatePaid = "SELECT SUM(Amount) AS TotalPaid FROM Payments WHERE OrderID = ?";
        String sqlGetTotalPrice = "SELECT TotalPrice FROM Orders WHERE OrderID = ?";
        String sqlUpdateStatus = "UPDATE Orders SET PaymentStatus = ? WHERE OrderID = ?";
        BigDecimal totalPaid = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;

        try (PreparedStatement calculateStmt = connection.prepareStatement(sqlCalculatePaid)) {
            calculateStmt.setInt(1, orderID);
            ResultSet rs = calculateStmt.executeQuery();
            if (rs.next()) {
                totalPaid = rs.getBigDecimal("TotalPaid");
                if (totalPaid == null) totalPaid = BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement priceStmt = connection.prepareStatement(sqlGetTotalPrice)) {
            priceStmt.setInt(1, orderID);
            ResultSet rs = priceStmt.executeQuery();
            if (rs.next()) {
                totalPrice = rs.getBigDecimal("TotalPrice");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        String paymentStatus = "Unpaid";
        if (totalPaid.compareTo(BigDecimal.ZERO) == 0) {
            paymentStatus = "Unpaid";
        } else if (totalPaid.compareTo(totalPrice) < 0) {
            paymentStatus = "Partial";
        } else {
            paymentStatus = "Paid";
        }

        try (PreparedStatement updateStmt = connection.prepareStatement(sqlUpdateStatus)) {
            updateStmt.setString(1, paymentStatus);
            updateStmt.setInt(2, orderID);
            updateStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void executeQueryAndWriteToFile(String query, String fileName, String startDate, String endDate, int cutoff) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Directory to Save Report");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false); 

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File directoryToSave = fileChooser.getSelectedFile();
            String directoryPath = directoryToSave.getAbsolutePath();

            
            if (!directoryPath.endsWith(File.separator)) {
                directoryPath += File.separator;
            }

            String filePath = directoryPath + fileName; 

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, startDate);
                stmt.setString(2, endDate);
                stmt.setInt(3, cutoff);
                try (ResultSet rs = stmt.executeQuery();
                    PrintWriter out = new PrintWriter(new FileWriter(new File(filePath)))) {
                    switch (fileName) {
                        case "TotalSalesPerMonth.txt":
                            out.println(String.format("%-20s %-20s %-20s", "Year", "Month", "TotalSales"));
                            while (rs.next()) {
                                String line = String.format("%-20d %-20d %-20.2f",
                                        rs.getInt("Year"),
                                        rs.getInt("Month"),
                                        rs.getBigDecimal("TotalSales"));
                                out.println(line);
                            }
                            break;
                        case "TotalSalesPerProduct.txt":
                            out.println(String.format("%-20s %-20s %-20s", "ProductID", "Name", "TotalSales"));
                            while (rs.next()) {
                                String line2 = String.format("%-20d %-20s %-20.2f",
                                        rs.getInt("ProductID"),
                                        rs.getString("Name"),
                                        rs.getBigDecimal("TotalSales"));
                                out.println(line2);
                            }
                            break;
                        case "TotalSalesPerCustomer.txt":
                            out.println(String.format("%-20s %-20s %-20s", "CustomerID", "Name", "TotalSales"));
                            while (rs.next()) {
                                String line3 = String.format("%-20d %-20s %-20.2f",
                                        rs.getInt("CustomerID"),
                                        rs.getString("Name"),
                                        rs.getBigDecimal("TotalSales"));
                                out.println(line3);
                            }
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid report type selected.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                    }
                    JOptionPane.showMessageDialog(null, "Report generated: " + filePath);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Failed to generate report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Report generation cancelled by user.", "Cancelled", JOptionPane.WARNING_MESSAGE);
        }
    }
}
