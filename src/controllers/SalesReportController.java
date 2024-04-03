package controllers;

import javax.swing.*;
import app.Application;

public class SalesReportController {

    public void generateReport(String reportType, String startDate, String endDate, boolean isDescending, int cutoff) {
        switch (reportType) {
            case "Total Sale Per Month":
                generateTotalSalesPerMonthReport(startDate, endDate, isDescending, cutoff);
                break;
            case "Total Sale Per Product":
                generateTotalSalesPerProductReport(startDate, endDate, isDescending, cutoff);
                break;
            case "Total Sale Per Customer":
                generateTotalSalesPerCustomerReport(startDate, endDate, isDescending, cutoff);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid report type selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateTotalSalesPerMonthReport(String startDate, String endDate, boolean isDescending, int cutoff) {
        String orderDirection = isDescending ? "DESC" : "ASC";
        String query = "SELECT YEAR(OrderDate) AS Year, MONTH(OrderDate) AS Month, SUM(TotalPrice) AS TotalSales\r\n" + 
                        "FROM Orders\r\n" + 
                        "WHERE OrderDate BETWEEN ? AND ?\r\n" + 
                        "GROUP BY YEAR(OrderDate), MONTH(OrderDate)\r\n" + 
                        "ORDER BY TotalSales " + orderDirection + "\r\n" + 
                        "LIMIT ?;";
        Application.getInstance().dataAdapter.executeQueryAndWriteToFile(query, "TotalSalesPerMonth.txt", startDate, endDate, cutoff);
    }

    private void generateTotalSalesPerProductReport(String startDate, String endDate, boolean isDescending, int cutoff) {
        String orderDirection = isDescending ? "DESC" : "ASC";
        String query = "SELECT p.ProductID, p.Name, SUM(ol.Quantity * ol.PriceAtPurchase) AS TotalSales\r\n" + 
                        "FROM OrderLines ol\r\n" + 
                        "JOIN Orders o ON ol.OrderID = o.OrderID\r\n" + 
                        "JOIN Products p ON ol.ProductID = p.ProductID\r\n" + 
                        "WHERE o.OrderDate BETWEEN ? AND ?\r\n" + 
                        "GROUP BY p.ProductID, p.Name\r\n" + 
                        "ORDER BY TotalSales " + orderDirection + "\r\n" + 
                        "LIMIT ?;";
        Application.getInstance().dataAdapter.executeQueryAndWriteToFile(query, "TotalSalesPerProduct.txt", startDate, endDate, cutoff);
    }

    private void generateTotalSalesPerCustomerReport(String startDate, String endDate, boolean isDescending, int cutoff) {
        String orderDirection = isDescending ? "DESC" : "ASC";
        String query = "SELECT c.CustomerID, c.Name, SUM(o.TotalPrice) AS TotalSales\r\n" + 
                        "FROM Orders o\r\n" + 
                        "JOIN Customers c ON o.CustomerID = c.CustomerID\r\n" + 
                        "WHERE o.OrderDate BETWEEN ? AND ?\r\n" + 
                        "GROUP BY c.CustomerID, c.Name\r\n" + 
                        "ORDER BY TotalSales " + orderDirection + "\r\n" + 
                        "LIMIT ?;";
        Application.getInstance().dataAdapter.executeQueryAndWriteToFile(query, "TotalSalesPerCustomer.txt", startDate, endDate, cutoff);
    }
}
