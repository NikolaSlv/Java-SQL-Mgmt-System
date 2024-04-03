package views;

import javax.swing.*;
import java.awt.*;
import controllers.PaymentController;

public class PaymentView extends JPanel {
    private JTextField txtPaymentID = new JTextField(10);
    private JTextField txtOrderID = new JTextField(20);
    private JTextField txtAmount = new JTextField(20);
    private JTextField txtPaymentDate = new JTextField(20); 
    private JTextField txtMethod = new JTextField(20);
    private JButton btnFindPayment = new JButton("Find Payment by ID");
    private JButton btnAddUpdatePayment = new JButton("Add/Update Payment");
    private JButton btnDeletePayment = new JButton("Delete Payment");
    private JButton btnBack = new JButton("Back");

    private PaymentController controller;

    public PaymentView() {
        this.controller = new PaymentController(this);

        CardLayout cardLayout = MainView.getCardLayout();
        JPanel rightPanel = MainView.getRightPanel();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addFormField("Payment ID:", txtPaymentID, gbc);
        addFormField("Order ID:", txtOrderID, gbc);
        addFormField("Amount:", txtAmount, gbc);
        addFormField("Payment Date (YYYY-MM-DD):", txtPaymentDate, gbc);
        addFormField("Method:", txtMethod, gbc);

        gbc.insets = new Insets(15, 4, 4, 4);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnFindPayment, gbc);
        gbc.insets = new Insets(8, 4, 4, 4);
        add(btnAddUpdatePayment, gbc);
        add(btnDeletePayment, gbc);

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(spacer, gbc);

        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(btnBack, gbc);

        btnFindPayment.addActionListener(e -> controller.findPaymentByID());
        btnAddUpdatePayment.addActionListener(e -> controller.addOrUpdatePayment());
        btnDeletePayment.addActionListener(e -> controller.deletePayment());
        btnBack.addActionListener(e -> cardLayout.show(rightPanel, "ButtonPanel"));
    }

    private void addFormField(String label, JTextField textField, GridBagConstraints gbc) {
        add(new JLabel(label), gbc);
        add(textField, gbc);
    }

    
    public JTextField getTxtPaymentID() { return txtPaymentID; }
    public JTextField getTxtOrderID() { return txtOrderID; }
    public JTextField getTxtAmount() { return txtAmount; }
    public JTextField getTxtPaymentDate() { return txtPaymentDate; }
    public JTextField getTxtMethod() { return txtMethod; }
}
