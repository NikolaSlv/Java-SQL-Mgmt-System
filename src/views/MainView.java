package views;

import javax.swing.*;

import app.Application;
import controllers.MainViewController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;

public class MainView extends JFrame {
    private static CardLayout cardLayout;
    private static JPanel rightPanel;
    private static JButton applyButton;

    public MainView() {
        MainViewController controller = new MainViewController(this);

        setTitle("Store Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(400); 
        getContentPane().add(splitPane);

        
        JPanel leftPanel = new JPanel(new BorderLayout());
        JTextArea tableTextArea = new JTextArea();
        tableTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(tableTextArea);

        JPanel topPanel = new JPanel(); 
        JComboBox<String> tableSelectComboBox = new JComboBox<>(new String[]{"Customers", "Suppliers", "Products", "Orders", "OrderLines", "Payments"});
        JTextField entryNumberField = new JTextField("50", 5); 
        applyButton = new JButton("Apply");

        topPanel.add(tableSelectComboBox);
        topPanel.add(new JLabel("Entries:"));
        topPanel.add(entryNumberField);
        topPanel.add(applyButton);
        leftPanel.add(topPanel, BorderLayout.NORTH);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        
        cardLayout = new CardLayout();
        rightPanel = new JPanel(cardLayout);
        
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        JButton btnCustomersView = new JButton("Manage Customers");
        JButton btnSuppliersView = new JButton("Manage Suppliers");
        JButton btnProductsView = new JButton("Manage Products");
        JButton btnOrdersView = new JButton("Manage Orders");
        JButton btnOrderLinesView = new JButton("Manage Order Lines");
        JButton btnPaymentsView = new JButton("Manage Payments");
        JButton btnGenSales = new JButton("Generate Sales Report");
        JButton btnExit = new JButton("Exit");

        

        
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tableName = (String) tableSelectComboBox.getSelectedItem();
                int entryLimit;
                try {
                    entryLimit = Integer.parseInt(entryNumberField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainView.this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                controller.updateTableDisplay(tableTextArea, tableName, entryLimit);
            }
        });

        btnCustomersView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rightPanel, "CustomerView");
            }
        });

        btnSuppliersView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rightPanel, "SupplierView");
            }
        });

        btnProductsView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rightPanel, "ProductView");
            }
        });

        btnOrdersView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rightPanel, "OrderView");
            }
        });

        btnOrderLinesView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rightPanel, "OrderLineView");
            }
        });

        btnPaymentsView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rightPanel, "PaymentView");
            }
        });

        btnGenSales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SalesReportView();
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.getInstance().dataAdapter.disconnect();
                System.out.println("Database connection successfully closed.");
                System.exit(0);
            }
        });

        
        buttonPanel.add(btnCustomersView);
        buttonPanel.add(btnSuppliersView);
        buttonPanel.add(btnProductsView);
        buttonPanel.add(btnOrdersView);
        buttonPanel.add(btnOrderLinesView);
        buttonPanel.add(btnPaymentsView);
        buttonPanel.add(btnGenSales);
        buttonPanel.add(btnExit);

        
        CustomerView customerView = new CustomerView();
        SupplierView supplierView = new SupplierView();
        ProductView productView = new ProductView();
        OrderView orderView = new OrderView();
        OrderLineView orderLineView = new OrderLineView();
        PaymentView paymentView = new PaymentView();

        
        JScrollPane customerScrollPane = new JScrollPane(customerView);
        customerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        customerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane supplierScrollPane = new JScrollPane(supplierView);
        supplierScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        supplierScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane productScrollPane = new JScrollPane(productView);
        productScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        productScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane orderScrollPane = new JScrollPane(orderView);
        orderScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        orderScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane orderLineScrollPane = new JScrollPane(orderLineView);
        orderLineScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        orderLineScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane paymentScrollPane = new JScrollPane(paymentView);
        paymentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        paymentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        
        rightPanel.add(buttonPanel, "ButtonPanel");
        rightPanel.add(customerScrollPane, "CustomerView");
        rightPanel.add(supplierScrollPane, "SupplierView");
        rightPanel.add(productScrollPane, "ProductView");
        rightPanel.add(orderScrollPane, "OrderView");
        rightPanel.add(orderLineScrollPane, "OrderLineView");
        rightPanel.add(paymentScrollPane, "PaymentView");

        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
    }

    
    public static CardLayout getCardLayout() {
        return cardLayout;
    }
    public static JPanel getRightPanel() {
        return rightPanel;
    }
    public static JButton getApplyButton() {
        return applyButton;
    }
}
