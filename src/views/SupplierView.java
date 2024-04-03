package views;

import javax.swing.*;
import controllers.SupplierController;
import java.awt.*;

public class SupplierView extends JPanel {
    private JTextField txtSupplierID = new JTextField(10);
    private JTextField txtName = new JTextField(20);
    private JTextField txtContactName = new JTextField(20);
    private JTextField txtEmail = new JTextField(20);
    private JTextField txtPhone = new JTextField(20);
    private JTextField txtAddress = new JTextField(20);
    private JButton btnFindSupplier = new JButton("Find Supplier by ID");
    private JButton btnAddUpdateSupplier = new JButton("Add/Update Supplier");
    private JButton btnDeleteSupplier = new JButton("Delete Supplier");
    private JButton btnBack = new JButton("Back");

    private SupplierController controller;

    public SupplierView() {
        this.controller = new SupplierController(this);

        CardLayout cardLayout = MainView.getCardLayout();
        JPanel rightPanel = MainView.getRightPanel();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4); 

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

        addFormField("Supplier ID:", txtSupplierID, gbc);
        addFormField("Name:", txtName, gbc);
        addFormField("Contact Name:", txtContactName, gbc);
        addFormField("Email:", txtEmail, gbc);
        addFormField("Phone:", txtPhone, gbc);
        addFormField("Address:", txtAddress, gbc);

        
        gbc.insets = new Insets(15, 4, 4, 4); 

        
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnFindSupplier, gbc);
        gbc.insets = new Insets(8, 4, 4, 4); 
        add(btnAddUpdateSupplier, gbc);
        add(btnDeleteSupplier, gbc);

        
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(spacer, gbc); 

        
        gbc.weightx = 0.0; 
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END; 
        add(btnBack, gbc);

        btnFindSupplier.addActionListener(e -> controller.findSupplierByID());
        btnAddUpdateSupplier.addActionListener(e -> controller.addOrUpdateSupplier());
        btnDeleteSupplier.addActionListener(e -> controller.deleteSupplier());
        btnBack.addActionListener(e -> cardLayout.show(rightPanel, "ButtonPanel"));
    }

    private void addFormField(String label, JTextField textField, GridBagConstraints gbc) {
        add(new JLabel(label), gbc);
        add(textField, gbc);
    }

    
    public JTextField getTxtSupplierID() { return txtSupplierID; }
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtContactName() { return txtContactName; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JTextField getTxtPhone() { return txtPhone; }
    public JTextField getTxtAddress() { return txtAddress; }
}
