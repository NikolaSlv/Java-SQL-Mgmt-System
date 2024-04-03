package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import app.Application;
import views.MainView;

public class MainViewController {
    private MainView view;

    public MainViewController(MainView view) {
        this.view = view;
    }

    public void updateTableDisplay(JTextArea tableTextArea, String tableName, int entryLimit) {
        Connection conn = Application.getInstance().dataAdapter.getConnection();
        if (conn == null) {
            JOptionPane.showMessageDialog(view, "No database connection. Please connect to the database first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String query = String.format("SELECT * FROM %s LIMIT %d", tableName, entryLimit);

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
            
            StringBuilder sb = new StringBuilder();

            
            switch (tableName) {
                case "Customers":
                    sb.append("CustomerID\tName\tEmail\tPhone\tAddress\n");
                    sb.append("----------\t----\t-----\t-----\t-------\n");
                    break;
                case "Suppliers":
                    sb.append("SupplierID\tName\tContactName\tEmail\tPhone\tAddress\n");
                    sb.append("----------\t----\t-----------\t-----\t-----\t-------\n");
                    break;
                case "Products":
                    sb.append("ProductID\tSupplierID\tName\tDescription\tQuantity\tPrice\tSupplyPrice\tExpiration\n");
                    sb.append("---------\t----------\t----\t-----------\t--------\t-----\t-----------\t----------\n");
                    break;
                case "Orders":
                    sb.append("OrderID\tCustomerID\tOrderDate\tTotalPrice\tStatus\tPaymentStatus\n");
                    sb.append("-------\t----------\t---------\t----------\t------\t-------------\n");
                    break;
                case "OrderLines":
                    sb.append("OrderLineID\tOrderID\tProductID\tQuantity\tPriceAtPurchase\n");
                    sb.append("-----------\t-------\t---------\t--------\t---------------\n");
                    break;
                case "Payments":
                    sb.append("PaymentID\tOrderID\tAmount\tPaymentDate\tMethod\n");
                    sb.append("---------\t-------\t------\t-----------\t------\n");
                    break;
            }

            
            while (rs.next()) {
                switch (tableName) {
                    case "Customers":
                        sb.append(rs.getInt("CustomerID")).append("\t")
                        .append(rs.getString("Name")).append("\t")
                        .append(rs.getString("Email")).append("\t")
                        .append(rs.getString("Phone")).append("\t")
                        .append(rs.getString("Address")).append("\n");
                        break;
                    case "Suppliers":
                        sb.append(rs.getInt("SupplierID")).append("\t")
                        .append(rs.getString("Name")).append("\t")
                        .append(rs.getString("ContactName")).append("\t")
                        .append(rs.getString("Email")).append("\t")
                        .append(rs.getString("Phone")).append("\t")
                        .append(rs.getString("Address")).append("\n");
                        break;
                    case "Products":
                        sb.append(rs.getInt("ProductID")).append("\t")
                        .append(rs.getInt("SupplierID")).append("\t")
                        .append(rs.getString("Name")).append("\t")
                        .append(rs.getString("Description")).append("\t")
                        .append(rs.getInt("Quantity")).append("\t")
                        .append(rs.getBigDecimal("Price")).append("\t")
                        .append(rs.getBigDecimal("SupplyPrice")).append("\t")
                        .append(rs.getString("Expiration")).append("\n");
                        break;
                    case "Orders":
                        sb.append(rs.getInt("OrderID")).append("\t")
                        .append(rs.getInt("CustomerID")).append("\t")
                        .append(rs.getDate("OrderDate")).append("\t")
                        .append(rs.getBigDecimal("TotalPrice")).append("\t")
                        .append(rs.getString("Status")).append("\t")
                        .append(rs.getString("PaymentStatus")).append("\n");
                        break;
                    case "OrderLines":
                        sb.append(rs.getInt("OrderLineID")).append("\t")
                        .append(rs.getInt("OrderID")).append("\t")
                        .append(rs.getInt("ProductID")).append("\t")
                        .append(rs.getInt("Quantity")).append("\t")
                        .append(rs.getBigDecimal("PriceAtPurchase")).append("\n");
                        break;
                    case "Payments":
                        sb.append(rs.getInt("PaymentID")).append("\t")
                        .append(rs.getInt("OrderID")).append("\t")
                        .append(rs.getBigDecimal("Amount")).append("\t")
                        .append(rs.getDate("PaymentDate")).append("\t")
                        .append(rs.getString("Method")).append("\n");
                        break;
                }
            }

            
            SwingUtilities.invokeLater(() -> tableTextArea.setText(sb.toString()));
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to fetch data: " + e.getMessage() + " Trying to reconnect...", "Error", JOptionPane.ERROR_MESSAGE);
            Application.getInstance().dataAdapter.connect();
            if (Application.getInstance().dataAdapter.getConnection() == null) {
                JOptionPane.showMessageDialog(view, "Failed to reconnect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Reconnected to the database. Please try again.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
