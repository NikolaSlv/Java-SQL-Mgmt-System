package controllers;

import views.MainView;
import views.SupplierView;
import javax.swing.JOptionPane;

import app.Application;
import models.SupplierModel;

public class SupplierController {
    private SupplierView view;

    public SupplierController(SupplierView view) {
        this.view = view;
    }

    public void findSupplierByID() {
        int id;
        try {
            id = Integer.parseInt(view.getTxtSupplierID().getText());
            SupplierModel supplier = Application.getInstance().dataAdapter.findSupplier(id);
            if (supplier != null) {
                view.getTxtName().setText(supplier.getName());
                view.getTxtContactName().setText(supplier.getContactName());
                view.getTxtEmail().setText(supplier.getEmail());
                view.getTxtPhone().setText(supplier.getPhone());
                view.getTxtAddress().setText(supplier.getAddress());
            } else {
                JOptionPane.showMessageDialog(view, "Supplier not found.", "Find Supplier", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric ID.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "An error occurred while fetching supplier information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addOrUpdateSupplier() {
        String idText = view.getTxtSupplierID().getText().trim();
        int supplierID = 0;
        
        if (!idText.isEmpty()) {
            try {
                supplierID = Integer.parseInt(idText);
                if (supplierID < 0) {
                    JOptionPane.showMessageDialog(view, "ID must be a non-negative integer.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
                    return; 
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "ID must be a numeric value.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
                return; 
            }
        }
    
        String name = view.getTxtName().getText().trim();
        String contactName = view.getTxtContactName().getText().trim();
        String email = view.getTxtEmail().getText().trim();
        String phone = view.getTxtPhone().getText().trim();
        String address = view.getTxtAddress().getText().trim();
    
        
        if (name.isEmpty() || contactName.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(view, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        SupplierModel existingSupplier = Application.getInstance().dataAdapter.findSupplier(supplierID);
        if (existingSupplier != null) {
            
            existingSupplier.setName(name);
            existingSupplier.setContactName(contactName);
            existingSupplier.setEmail(email);
            existingSupplier.setPhone(phone);
            existingSupplier.setAddress(address);
    
            try {
                boolean success = Application.getInstance().dataAdapter.updateSupplier(existingSupplier);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Supplier updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update supplier.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error updating supplier: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            
            boolean emailExists = Application.getInstance().dataAdapter.supplierEmailExists(email); 
            if (emailExists) {
                JOptionPane.showMessageDialog(view, "This email is already in use by another supplier.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            
            SupplierModel newSupplier = new SupplierModel(supplierID, name, contactName, email, phone, address);
            try {
                boolean success = Application.getInstance().dataAdapter.addSupplier(newSupplier);
                if (success) {
                    JOptionPane.showMessageDialog(view, "New supplier added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add new supplier.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error adding supplier: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }    

    public void deleteSupplier() {
        
        String idText = view.getTxtSupplierID().getText().trim();
        int supplierID;
    
        try {
            supplierID = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric ID.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        if (Application.getInstance().dataAdapter.findSupplier(supplierID) == null) {
            JOptionPane.showMessageDialog(view, "Supplier not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this supplier?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = Application.getInstance().dataAdapter.deleteSupplier(supplierID);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Supplier deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    
                    JOptionPane.showMessageDialog(view, "Failed to delete supplier. There might be an existing constraint.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                
                JOptionPane.showMessageDialog(view, "Error deleting supplier: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Supplier deletion cancelled.");
        }
    }    
}
