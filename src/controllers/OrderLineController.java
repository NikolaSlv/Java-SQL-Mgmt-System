package controllers;

import views.MainView;
import views.OrderLineView;

import java.math.BigDecimal;

import javax.swing.JOptionPane;

import app.Application;
import models.OrderLineModel;

public class OrderLineController {
    private OrderLineView view;

    public OrderLineController(OrderLineView view) {
        this.view = view;
    }

    public void findOrderLineByID() {
        int id;
        try {
            id = Integer.parseInt(view.getTxtOrderLineID().getText());
            OrderLineModel orderLine = Application.getInstance().dataAdapter.findOrderLine(id);
            if (orderLine != null) {
                view.getTxtOrderID().setText(String.valueOf(orderLine.getOrderID()));
                view.getTxtProductID().setText(String.valueOf(orderLine.getProductID()));
                view.getTxtQuantity().setText(String.valueOf(orderLine.getQuantity()));
                view.getTxtPriceAtPurchase().setText(orderLine.getPriceAtPurchase().toString());
            } else {
                JOptionPane.showMessageDialog(view, "Order Line not found.", "Find Order Line", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric ID.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "An error occurred while fetching order line information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addOrUpdateOrderLine() {
        int orderLineID = 0;
        int orderID, productID, quantity;
        BigDecimal priceAtPurchase;
    
        try {
            String orderLineIdText = view.getTxtOrderLineID().getText().trim();
            String orderIDText = view.getTxtOrderID().getText().trim();
            String productIDText = view.getTxtProductID().getText().trim();
            if (!orderLineIdText.isEmpty()) {
                orderLineID = Integer.parseInt(orderLineIdText);
                if (orderLineID < 0) {
                    JOptionPane.showMessageDialog(view, "Order Line ID must be a non-negative integer.", "Invalid Order Line ID", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
    
            if (!orderIDText.isEmpty()) {
                orderID = Integer.parseInt(orderIDText);
            } else {
                JOptionPane.showMessageDialog(view, "Order ID is required.", "Invalid Order ID", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!productIDText.isEmpty()) {
                productID = Integer.parseInt(productIDText);
            } else {
                JOptionPane.showMessageDialog(view, "Product ID is required.", "Invalid Product ID", JOptionPane.ERROR_MESSAGE);
                return;
            }

            quantity = Integer.parseInt(view.getTxtQuantity().getText().trim());
            priceAtPurchase = new BigDecimal(view.getTxtPriceAtPurchase().getText().trim());
    
            
            if (Application.getInstance().dataAdapter.findOrder(orderID) == null) {
                JOptionPane.showMessageDialog(view, "Referenced Order does not exist.", "Invalid Order ID", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            if (Application.getInstance().dataAdapter.findProduct(productID) == null) {
                JOptionPane.showMessageDialog(view, "Referenced Product does not exist.", "Invalid Product ID", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            OrderLineModel orderLine = Application.getInstance().dataAdapter.findOrderLine(orderLineID);
            if (orderLine != null) {
                
                boolean success = Application.getInstance().dataAdapter.updateOrderLine(new OrderLineModel(orderLineID, orderID, productID, quantity, priceAtPurchase));
                if (success) {
                    JOptionPane.showMessageDialog(view, "Order Line updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update Order Line. There might not be enough of the product in stock.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                
                boolean success = Application.getInstance().dataAdapter.addOrderLine(new OrderLineModel(orderLineID, orderID, productID, quantity, priceAtPurchase));
                if (success) {
                    JOptionPane.showMessageDialog(view, "New Order Line added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add new Order Line. There might not be enough of the product in stock.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please ensure all fields are correctly filled with numeric values where applicable.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "An error occurred while processing the order line: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }    

    public void deleteOrderLine() {
        String idText = view.getTxtOrderLineID().getText().trim();
        int orderLineID;
        try {
            orderLineID = Integer.parseInt(idText);
            if (Application.getInstance().dataAdapter.findOrderLine(orderLineID) == null) {
                JOptionPane.showMessageDialog(view, "Order Line not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this order line?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = Application.getInstance().dataAdapter.deleteOrderLine(orderLineID);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Order Line deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to delete Order Line. It may be linked with other data.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric ID.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "An error occurred while deleting the order line: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }   
}
