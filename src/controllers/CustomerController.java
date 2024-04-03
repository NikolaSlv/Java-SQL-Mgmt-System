package controllers;

import views.CustomerView;
import views.MainView;

import javax.swing.JOptionPane;

import app.Application;
import models.CustomerModel;

public class CustomerController {
    private CustomerView view;

    public CustomerController(CustomerView view) {
        this.view = view;
    }

    public void findCustomerByID() {
        int id;
        try {
            id = Integer.parseInt(view.getTxtCustomerID().getText());
            CustomerModel customer = Application.getInstance().dataAdapter.findCustomer(id);
            if (customer != null) {
                view.getTxtName().setText(customer.getName());
                view.getTxtEmail().setText(customer.getEmail());
                view.getTxtPhone().setText(customer.getPhone());
                view.getTxtAddress().setText(customer.getAddress());
            } else {
                JOptionPane.showMessageDialog(view, "Customer not found.", "Find Customer", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric ID.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "An error occurred while fetching customer information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addOrUpdateCustomer() {
        String idText = view.getTxtCustomerID().getText().trim();
        int customerID = 0;
    
        if (!idText.isEmpty()) {
            try {
                customerID = Integer.parseInt(idText);
                if (customerID < 0) {
                    JOptionPane.showMessageDialog(view, "ID must be a non-negative integer.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
                    return; 
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "ID must be a numeric value.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
                return; 
            }
        }
    
        String name = view.getTxtName().getText().trim();
        String email = view.getTxtEmail().getText().trim();
        String phone = view.getTxtPhone().getText().trim();
        String address = view.getTxtAddress().getText().trim();
    
        
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(view, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        CustomerModel existingCustomer = Application.getInstance().dataAdapter.findCustomer(customerID);
        if (existingCustomer != null) {
            
            try {
                existingCustomer.setName(name);
                existingCustomer.setEmail(email);
                existingCustomer.setPhone(phone);
                existingCustomer.setAddress(address);
                boolean success = Application.getInstance().dataAdapter.updateCustomer(existingCustomer);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Customer updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update customer.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error updating customer: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            
            boolean emailExists = Application.getInstance().dataAdapter.customerEmailExists(email);
            if (emailExists) {
                JOptionPane.showMessageDialog(view, "This email is already registered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                
                try {
                    CustomerModel newCustomer = new CustomerModel(customerID, name, email, phone, address);
                    boolean success = Application.getInstance().dataAdapter.addCustomer(newCustomer);
                    if (success) {
                        JOptionPane.showMessageDialog(view, "New customer added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        MainView.getApplyButton().doClick();
                    } else {
                        JOptionPane.showMessageDialog(view, "Failed to add new customer.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view, "Error adding customer: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }    

    public void deleteCustomer() {
        
        String idText = view.getTxtCustomerID().getText().trim();
        int customerID;
    
        try {
            customerID = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric ID.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        if (Application.getInstance().dataAdapter.findCustomer(customerID) == null) {
            JOptionPane.showMessageDialog(view, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this customer?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = Application.getInstance().dataAdapter.deleteCustomer(customerID);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Customer deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    
                    JOptionPane.showMessageDialog(view, "Failed to delete customer. There might be an existing constraint.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                
                JOptionPane.showMessageDialog(view, "Error deleting customer: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Customer deletion cancelled.");
        }
    }
}    
