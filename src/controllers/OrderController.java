package controllers;

import views.MainView;
import views.OrderView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JOptionPane;

import app.Application;
import models.OrderModel;

public class OrderController {
    private OrderView view;

    public OrderController(OrderView view) {
        this.view = view;
    }

    public void findOrderByID() {
        int id;
        try {
            id = Integer.parseInt(view.getTxtOrderID().getText());
            OrderModel order = Application.getInstance().dataAdapter.findOrder(id);
            if (order != null) {
                view.getTxtCustomerID().setText(String.valueOf(order.getCustomerID()));
                view.getTxtOrderDate().setText(order.getOrderDate().toString());
                view.getTxtTotalPrice().setText(order.getTotalPrice().toString());
                view.getTxtStatus().setText(order.getStatus());
                view.getTxtPaymentStatus().setText(order.getPaymentStatus());
            } else {
                JOptionPane.showMessageDialog(view, "Order not found.", "Find Order", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric ID.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "An error occurred while fetching order information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addOrUpdateOrder() {
        String idText = view.getTxtOrderID().getText().trim();
        String customerIDText = view.getTxtCustomerID().getText().trim();
        int orderID = 0;
        int customerID = 0;

        try {
            if (!idText.isEmpty()) {
                orderID = Integer.parseInt(idText);
                if (orderID < 0) {
                    JOptionPane.showMessageDialog(view, "Order ID must be a non-negative integer.", "Invalid Order ID", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (!customerIDText.isEmpty()) {
                customerID = Integer.parseInt(customerIDText);
                if (customerID <= 0) {
                    JOptionPane.showMessageDialog(view, "Customer ID must be a positive integer.", "Invalid Customer ID", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(view, "Customer ID is required.", "Invalid Customer ID", JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            if (Application.getInstance().dataAdapter.findCustomer(customerID) == null) {
                JOptionPane.showMessageDialog(view, "Customer ID does not exist.", "Invalid Customer ID", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            LocalDate orderDate;
            try {
                String dateString = view.getTxtOrderDate().getText().trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                orderDate = LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(view, "Order Date must be in the format YYYY-MM-DD.", "Invalid Order Date", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BigDecimal totalPrice = view.getTxtTotalPrice().getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(view.getTxtTotalPrice().getText().trim());
            String status = view.getTxtStatus().getText().trim();
            String paymentStatus = view.getTxtPaymentStatus().getText().trim();

            OrderModel existingOrder = Application.getInstance().dataAdapter.findOrder(orderID);

            if (status.isEmpty() || paymentStatus.isEmpty()) {
                JOptionPane.showMessageDialog(view, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (existingOrder != null) {
                
                boolean success = Application.getInstance().dataAdapter.updateOrder(new OrderModel(orderID, customerID, orderDate, totalPrice, status, paymentStatus));
                if (success) {
                    JOptionPane.showMessageDialog(view, "Order updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update order.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                
                boolean success = Application.getInstance().dataAdapter.addOrder(new OrderModel(orderID, customerID, orderDate, totalPrice, status, paymentStatus));
                if (success) {
                    JOptionPane.showMessageDialog(view, "New order added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add new order.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Numeric fields (ID, Quantity, Price) must be valid numbers.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error processing order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteOrder() {
        String idText = view.getTxtOrderID().getText().trim();
        int orderID;
    
        try {
            orderID = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric ID.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (Application.getInstance().dataAdapter.findOrder(orderID) == null) {
            JOptionPane.showMessageDialog(view, "Order not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this order?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = Application.getInstance().dataAdapter.deleteOrder(orderID);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Order deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to delete order. There might be an existing constraint.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error deleting order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Order deletion cancelled.");
        }
    }    
}
