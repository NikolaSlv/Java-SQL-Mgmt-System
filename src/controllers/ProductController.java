package controllers;

import views.MainView;
import views.ProductView;

import java.math.BigDecimal;

import javax.swing.JOptionPane;

import app.Application;
import models.ProductModel;

public class ProductController {
    private ProductView view;

    public ProductController(ProductView view) {
        this.view = view;
    }

    public void findProductByID() {
        int id;
        try {
            id = Integer.parseInt(view.getTxtProductID().getText());
            ProductModel product = Application.getInstance().dataAdapter.findProduct(id);
            if (product != null) {
                view.getTxtSupplierID().setText(String.valueOf(product.getSupplierID()));
                view.getTxtName().setText(product.getName());
                view.getTxtDescription().setText(product.getDescription());
                view.getTxtQuantity().setText(String.valueOf(product.getQuantity()));
                view.getTxtPrice().setText(product.getPrice().toString());
                view.getTxtSupplyPrice().setText(product.getSupplyPrice().toString());
                view.getTxtExpiration().setText(product.getExpiration());
            } else {
                JOptionPane.showMessageDialog(view, "Product not found.", "Find Product", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric ID.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "An error occurred while fetching product information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addOrUpdateProduct() {
        String idText = view.getTxtProductID().getText().trim();
        String supplierIdText = view.getTxtSupplierID().getText().trim();
        int productID = 0;
        int supplierID = 0;

        try {
            if (!idText.isEmpty()) {
                productID = Integer.parseInt(idText);
                if (productID < 0) {
                    JOptionPane.showMessageDialog(view, "Product ID must be a non-negative integer.", "Invalid Product ID", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            if (!supplierIdText.isEmpty()) {
                supplierID = Integer.parseInt(supplierIdText);
                if (supplierID <= 0) {
                    JOptionPane.showMessageDialog(view, "Supplier ID must be a positive integer.", "Invalid Supplier ID", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(view, "Supplier ID is required.", "Invalid Supplier ID", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "IDs must be numeric values.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        if (Application.getInstance().dataAdapter.findSupplier(supplierID) == null) {
            JOptionPane.showMessageDialog(view, "Referenced Supplier does not exist.", "Invalid Supplier ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = view.getTxtName().getText().trim();
        String description = view.getTxtDescription().getText().trim();
        int quantity;
        try {
            quantity = Integer.parseInt(view.getTxtQuantity().getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Quantity must be a numeric value.", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        BigDecimal price, supplyPrice;
        try {
            price = new BigDecimal(view.getTxtPrice().getText().trim());
            supplyPrice = new BigDecimal(view.getTxtSupplyPrice().getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Price and Supply Price must be valid numbers.", "Invalid Price", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String expiration = view.getTxtExpiration().getText().trim();

        ProductModel existingProduct = Application.getInstance().dataAdapter.findProduct(productID);

        if (name.isEmpty() || description.isEmpty() || expiration.isEmpty()) {
            JOptionPane.showMessageDialog(view, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (existingProduct != null) {
            
            try {
                boolean success = Application.getInstance().dataAdapter.updateProduct(new ProductModel(productID, supplierID, name, description, quantity, price, supplyPrice, expiration));
                if (success) {
                    JOptionPane.showMessageDialog(view, "Product updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update product.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error updating product: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            
            try {
                boolean success = Application.getInstance().dataAdapter.addProduct(new ProductModel(productID, supplierID, name, description, quantity, price, supplyPrice, expiration));
                if (success) {
                    JOptionPane.showMessageDialog(view, "New product added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add new product.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error adding product: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }   

    public void deleteProduct() {
        String idText = view.getTxtProductID().getText().trim();
        int productID;
    
        try {
            productID = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric ID.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (Application.getInstance().dataAdapter.findProduct(productID) == null) {
            JOptionPane.showMessageDialog(view, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this product?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = Application.getInstance().dataAdapter.deleteProduct(productID);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Product deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainView.getApplyButton().doClick();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to delete product. There might be an existing constraint.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Error deleting product: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Product deletion cancelled.");
        }
    }    
}
