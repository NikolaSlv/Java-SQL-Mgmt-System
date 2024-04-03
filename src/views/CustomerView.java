package views;

import javax.swing.*;

import controllers.CustomerController;

import java.awt.*;

public class CustomerView extends JPanel {
    private JTextField txtCustomerID = new JTextField(10);
    private JTextField txtName = new JTextField(20);
    private JTextField txtEmail = new JTextField(20);
    private JTextField txtPhone = new JTextField(20);
    private JTextField txtAddress = new JTextField(20);
    private JButton btnFindCustomer = new JButton("Find Customer by ID");
    private JButton btnAddUpdateCustomer = new JButton("Add/Update Customer");
    private JButton btnDeleteCustomer = new JButton("Delete Customer");
    private JButton btnBack = new JButton("Back");

    private CustomerController controller;

    public CustomerView() {
        this.controller = new CustomerController(this);

        CardLayout cardLayout = MainView.getCardLayout();
        JPanel rightPanel = MainView.getRightPanel();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4); 

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

        addFormField("Customer ID:", txtCustomerID, gbc);
        addFormField("Name:", txtName, gbc);
        addFormField("Email:", txtEmail, gbc);
        addFormField("Phone:", txtPhone, gbc);
        addFormField("Address:", txtAddress, gbc);

        
        gbc.insets = new Insets(15, 4, 4, 4); 

        
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnFindCustomer, gbc);
        gbc.insets = new Insets(8, 4, 4, 4); 
        add(btnAddUpdateCustomer, gbc);
        add(btnDeleteCustomer, gbc);

        
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(spacer, gbc); 

        
        gbc.weightx = 0.0; 
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END; 
        add(btnBack, gbc);

        btnFindCustomer.addActionListener(e -> controller.findCustomerByID());
        btnAddUpdateCustomer.addActionListener(e -> controller.addOrUpdateCustomer());
        btnDeleteCustomer.addActionListener(e -> controller.deleteCustomer());
        btnBack.addActionListener(e -> cardLayout.show(rightPanel, "ButtonPanel"));
    }

    private void addFormField(String label, JTextField textField, GridBagConstraints gbc) {
        add(new JLabel(label), gbc);
        add(textField, gbc);
    }

    
    public JTextField getTxtCustomerID() { return txtCustomerID; }
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JTextField getTxtPhone() { return txtPhone; }
    public JTextField getTxtAddress() { return txtAddress; }
}
