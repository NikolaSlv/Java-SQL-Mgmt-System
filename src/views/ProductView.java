package views;

import javax.swing.*;
import controllers.ProductController;
import java.awt.*;

public class ProductView extends JPanel {
    private JTextField txtProductID = new JTextField(10);
    private JTextField txtSupplierID = new JTextField(10);
    private JTextField txtName = new JTextField(20);
    private JTextField txtDescription = new JTextField(20);
    private JTextField txtQuantity = new JTextField(10);
    private JTextField txtPrice = new JTextField(10);
    private JTextField txtSupplyPrice = new JTextField(10);
    private JTextField txtExpiration = new JTextField(15);
    private JButton btnFindProduct = new JButton("Find Product by ID");
    private JButton btnAddUpdateProduct = new JButton("Add/Update Product");
    private JButton btnDeleteProduct = new JButton("Delete Product");
    private JButton btnBack = new JButton("Back");

    private ProductController controller;

    public ProductView() {
        this.controller = new ProductController(this);

        CardLayout cardLayout = MainView.getCardLayout();
        JPanel rightPanel = MainView.getRightPanel();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4); 

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

        addFormField("Product ID:", txtProductID, gbc);
        addFormField("Supplier ID:", txtSupplierID, gbc);
        addFormField("Name:", txtName, gbc);
        addFormField("Description:", txtDescription, gbc);
        addFormField("Quantity:", txtQuantity, gbc);
        addFormField("Price:", txtPrice, gbc);
        addFormField("Supply Price:", txtSupplyPrice, gbc);
        addFormField("Expiration Date:", txtExpiration, gbc);

        
        gbc.insets = new Insets(15, 4, 4, 4); 

        
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnFindProduct, gbc);
        gbc.insets = new Insets(8, 4, 4, 4); 
        add(btnAddUpdateProduct, gbc);
        add(btnDeleteProduct, gbc);

        
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(spacer, gbc); 

        
        gbc.weightx = 0.0; 
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END; 
        add(btnBack, gbc);

        btnFindProduct.addActionListener(e -> controller.findProductByID());
        btnAddUpdateProduct.addActionListener(e -> controller.addOrUpdateProduct());
        btnDeleteProduct.addActionListener(e -> controller.deleteProduct());
        btnBack.addActionListener(e -> cardLayout.show(rightPanel, "ButtonPanel"));
    }

    private void addFormField(String label, JTextField textField, GridBagConstraints gbc) {
        add(new JLabel(label), gbc);
        add(textField, gbc);
    }

    
    public JTextField getTxtProductID() { return txtProductID; }
    public JTextField getTxtSupplierID() { return txtSupplierID; }
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtDescription() { return txtDescription; }
    public JTextField getTxtQuantity() { return txtQuantity; }
    public JTextField getTxtPrice() { return txtPrice; }
    public JTextField getTxtSupplyPrice() { return txtSupplyPrice; }
    public JTextField getTxtExpiration() { return txtExpiration; }
}
