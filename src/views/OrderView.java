package views;

import javax.swing.*;
import java.awt.*;
import controllers.OrderController;

public class OrderView extends JPanel {
    private JTextField txtOrderID = new JTextField(10);
    private JTextField txtCustomerID = new JTextField(20);
    private JTextField txtOrderDate = new JTextField(20);
    private JTextField txtTotalPrice = new JTextField(20);
    private JTextField txtStatus = new JTextField(20);
    private JTextField txtPaymentStatus = new JTextField(20);
    private JButton btnFindOrder = new JButton("Find Order by ID");
    private JButton btnAddUpdateOrder = new JButton("Add/Update Order");
    private JButton btnDeleteOrder = new JButton("Delete Order");
    private JButton btnBack = new JButton("Back");

    private OrderController controller;

    public OrderView() {
        txtTotalPrice.setEditable(false);
        

        this.controller = new OrderController(this);

        CardLayout cardLayout = MainView.getCardLayout();
        JPanel rightPanel = MainView.getRightPanel();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addFormField("Order ID:", txtOrderID, gbc);
        addFormField("Customer ID:", txtCustomerID, gbc);
        addFormField("Order Date (YYYY-MM-DD):", txtOrderDate, gbc);
        addFormField("Total Price:", txtTotalPrice, gbc);
        addFormField("Status:", txtStatus, gbc);
        addFormField("Payment Status:", txtPaymentStatus, gbc);

        gbc.insets = new Insets(15, 4, 4, 4);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnFindOrder, gbc);
        gbc.insets = new Insets(8, 4, 4, 4);
        add(btnAddUpdateOrder, gbc);
        add(btnDeleteOrder, gbc);

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(spacer, gbc);

        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(btnBack, gbc);

        btnFindOrder.addActionListener(e -> controller.findOrderByID());
        btnAddUpdateOrder.addActionListener(e -> controller.addOrUpdateOrder());
        btnDeleteOrder.addActionListener(e -> controller.deleteOrder());
        btnBack.addActionListener(e -> cardLayout.show(rightPanel, "ButtonPanel"));
    }

    private void addFormField(String label, JTextField textField, GridBagConstraints gbc) {
        add(new JLabel(label), gbc);
        add(textField, gbc);
    }

    
    public JTextField getTxtOrderID() { return txtOrderID; }
    public JTextField getTxtCustomerID() { return txtCustomerID; }
    public JTextField getTxtOrderDate() { return txtOrderDate; }
    public JTextField getTxtTotalPrice() { return txtTotalPrice; }
    public JTextField getTxtStatus() { return txtStatus; }
    public JTextField getTxtPaymentStatus() { return txtPaymentStatus; }
}
