package views;

import javax.swing.*;
import java.awt.*;
import controllers.OrderLineController;

public class OrderLineView extends JPanel {
    private JTextField txtOrderLineID = new JTextField(10);
    private JTextField txtOrderID = new JTextField(20);
    private JTextField txtProductID = new JTextField(20);
    private JTextField txtQuantity = new JTextField(20);
    private JTextField txtPriceAtPurchase = new JTextField(20);
    private JButton btnFindOrderLine = new JButton("Find Order Line by ID");
    private JButton btnAddUpdateOrderLine = new JButton("Add/Update Order Line");
    private JButton btnDeleteOrderLine = new JButton("Delete Order Line");
    private JButton btnBack = new JButton("Back");

    private OrderLineController controller;

    public OrderLineView() {
        this.controller = new OrderLineController(this);

        CardLayout cardLayout = MainView.getCardLayout();
        JPanel rightPanel = MainView.getRightPanel();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addFormField("Order Line ID:", txtOrderLineID, gbc);
        addFormField("Order ID:", txtOrderID, gbc);
        addFormField("Product ID:", txtProductID, gbc);
        addFormField("Quantity:", txtQuantity, gbc);
        addFormField("Price at Purchase:", txtPriceAtPurchase, gbc);

        gbc.insets = new Insets(15, 4, 4, 4);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnFindOrderLine, gbc);
        gbc.insets = new Insets(8, 4, 4, 4);
        add(btnAddUpdateOrderLine, gbc);
        add(btnDeleteOrderLine, gbc);

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(spacer, gbc);

        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(btnBack, gbc);

        btnFindOrderLine.addActionListener(e -> controller.findOrderLineByID());
        btnAddUpdateOrderLine.addActionListener(e -> controller.addOrUpdateOrderLine());
        btnDeleteOrderLine.addActionListener(e -> controller.deleteOrderLine());
        btnBack.addActionListener(e -> cardLayout.show(rightPanel, "ButtonPanel"));
    }

    private void addFormField(String label, JTextField textField, GridBagConstraints gbc) {
        add(new JLabel(label), gbc);
        add(textField, gbc);
    }

    
    public JTextField getTxtOrderLineID() { return txtOrderLineID; }
    public JTextField getTxtOrderID() { return txtOrderID; }
    public JTextField getTxtProductID() { return txtProductID; }
    public JTextField getTxtQuantity() { return txtQuantity; }
    public JTextField getTxtPriceAtPurchase() { return txtPriceAtPurchase; }
}
