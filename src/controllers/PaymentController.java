package controllers;

import views.MainView;
import views.PaymentView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JOptionPane;

import app.Application;
import models.PaymentModel;

public class PaymentController {
    private PaymentView view;

    public PaymentController(PaymentView view) {
        this.view = view;
    }

    public void findPaymentByID() {
        int id;
        try {
            id = Integer.parseInt(view.getTxtPaymentID().getText());
            PaymentModel payment = Application.getInstance().dataAdapter.findPayment(id);
            if (payment != null) {
                view.getTxtOrderID().setText(String.valueOf(payment.getOrderID()));
                view.getTxtAmount().setText(payment.getAmount().toString());
                view.getTxtPaymentDate().setText(payment.getPaymentDate().toString());
                view.getTxtMethod().setText(payment.getMethod());
            } else {
                JOptionPane.showMessageDialog(view, "Payment not found.", "Find Payment", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric ID.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "An error occurred while fetching payment information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addOrUpdatePayment() {
        String idText = view.getTxtPaymentID().getText().trim();
        String orderIDText = view.getTxtOrderID().getText().trim();
        int paymentID = 0;
        int orderID = 0;

        try {
            if (!idText.isEmpty()) {
                paymentID = Integer.parseInt(idText);
                if (paymentID < 0) {
                    JOptionPane.showMessageDialog(view, "Payment ID must be a non-negative integer.", "Invalid Payment ID", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            if (!orderIDText.isEmpty()) {
                orderID = Integer.parseInt(orderIDText);
                if (orderID <= 0) {
                    JOptionPane.showMessageDialog(view, "Order ID must be a positive integer.", "Invalid Order ID", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(view, "Order ID is required.", "Invalid Order ID", JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            if (Application.getInstance().dataAdapter.findOrder(orderID) == null) {
                JOptionPane.showMessageDialog(view, "Referenced Order does not exist.", "Invalid Order ID", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            LocalDate paymentDate;
            try {
                String dateString = view.getTxtPaymentDate().getText().trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                paymentDate = LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(view, "Payment Date must be in the format YYYY-MM-DD.", "Invalid Payment Date", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BigDecimal amount;
            try {
                amount = new BigDecimal(view.getTxtAmount().getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Amount must be a valid number.", "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String method = view.getTxtMethod().getText().trim();

            if (method.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Payment method is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PaymentModel existingPayment = Application.getInstance().dataAdapter.findPayment(paymentID);

            if (existingPayment != null) {
                
                boolean success = Application.getInstance().dataAdapter.updatePayment(new PaymentModel(paymentID, orderID, amount, paymentDate, method));
                if (success) {
                    JOptionPane.showMessageDialog(view, "Payment updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update payment.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                
                boolean success = Application.getInstance().dataAdapter.addPayment(new PaymentModel(paymentID, orderID, amount, paymentDate, method));
                if (success) {
                    JOptionPane.showMessageDialog(view, "New payment added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add new payment.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Numeric fields must contain valid numbers.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error processing payment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deletePayment() {
        String idText = view.getTxtPaymentID().getText().trim();
        int paymentID;

        try {
            paymentID = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric ID.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (Application.getInstance().dataAdapter.findPayment(paymentID) == null) {
            JOptionPane.showMessageDialog(view, "Payment not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this payment?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = Application.getInstance().dataAdapter.deletePayment(paymentID);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Payment deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to delete payment.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error deleting payment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Payment deletion cancelled.");
        }
    }
}
