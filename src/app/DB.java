package app;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

public class DB implements InterfaceDB {

    @Override
    public void menu() {
        
        Application.getInstance().dataAdapter.connect();

        
        try {
            boolean findWindows = false;
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    findWindows = true;
                    break;
                }
            }
            if (!findWindows) {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            
        }

        JFrame frame = new JFrame("Application Configuration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        Box boxLayout = Box.createVerticalBox();
        boxLayout.add(Box.createVerticalGlue());

        JLabel label = new JLabel("Configuration Step");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize()));
        boxLayout.add(label);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton initButton = new JButton("Initialize Tables");
        JButton deleteButton = new JButton("Delete All Data");
        JButton runButton = new JButton("Run Application");
        JButton exitButton = new JButton("Exit");

        Dimension buttonSize = new Dimension(200, 50);
        initButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        runButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        initButton.setFont(new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize()));
        deleteButton.setFont(new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize()));
        runButton.setFont(new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize()));
        exitButton.setFont(new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize()));

        buttonPanel.add(initButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(runButton);
        buttonPanel.add(exitButton);

        boxLayout.add(buttonPanel);
        boxLayout.add(Box.createVerticalGlue());

        frame.add(boxLayout);

        initButton.addActionListener((ActionEvent e) -> {
            Connection connection = Application.getInstance().dataAdapter.getConnection();
            if (Application.getInstance().DB.initTables(connection)) {
                JOptionPane.showMessageDialog(frame, "Database tables initialized successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Database tables NOT initialized successfully.\nThe tables may already exist.");
            }
        });

        deleteButton.addActionListener((ActionEvent e) -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete all data?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Connection connection = Application.getInstance().dataAdapter.getConnection();
                if (Application.getInstance().DB.deleteData(connection)) {
                    JOptionPane.showMessageDialog(frame, "All data deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to delete data.");
                }
            }
        });

        runButton.addActionListener((ActionEvent e) -> {
            frame.dispose();
            
            Application.getInstance().mainView.setVisible(true);
        });

        exitButton.addActionListener((ActionEvent e) -> {
            Application.getInstance().dataAdapter.disconnect();
            System.out.println("Database connection successfully closed.");
            System.exit(0);
        });

        frame.setVisible(true);
    }

    @Override
    public boolean initTables(Connection conn) {
        String[] queries = {
            "CREATE TABLE Customers (" +
            "CustomerID INT UNSIGNED NOT NULL AUTO_INCREMENT," +
            "Name VARCHAR(255) NOT NULL," +
            "Email VARCHAR(255) NOT NULL UNIQUE," +
            "Phone VARCHAR(255) NOT NULL," +
            "Address VARCHAR(255) NOT NULL," +
            "PRIMARY KEY (CustomerID)" +
            ");",
    
            "CREATE TABLE Suppliers (" +
            "SupplierID INT UNSIGNED NOT NULL AUTO_INCREMENT," +
            "Name VARCHAR(255) NOT NULL," +
            "ContactName VARCHAR(255) NOT NULL," +
            "Email VARCHAR(255) NOT NULL UNIQUE," +
            "Phone VARCHAR(255) NOT NULL," +
            "Address VARCHAR(255) NOT NULL," +
            "PRIMARY KEY (SupplierID)" +
            ");",
    
            "CREATE TABLE Products (" +
            "ProductID INT UNSIGNED NOT NULL AUTO_INCREMENT," +
            "SupplierID INT UNSIGNED NOT NULL," +
            "Name VARCHAR(255) NOT NULL," +
            "Description VARCHAR(255)," +
            "Quantity INT UNSIGNED NOT NULL," +
            "Price DECIMAL(10, 2) UNSIGNED NOT NULL," +
            "SupplyPrice DECIMAL(10, 2) UNSIGNED NOT NULL," +
            "Expiration VARCHAR(255) NOT NULL DEFAULT 'n/a'," +
            "PRIMARY KEY (ProductID)," +
            "FOREIGN KEY (SupplierID) REFERENCES Suppliers(SupplierID)" +
            ");",
    
            "CREATE TABLE Orders (" +
            "OrderID INT UNSIGNED NOT NULL AUTO_INCREMENT," +
            "CustomerID INT UNSIGNED NOT NULL," +
            "OrderDate DATE NOT NULL," +
            "TotalPrice DECIMAL(10, 2) UNSIGNED NOT NULL," +
            "Status VARCHAR(255) NOT NULL," +
            "PaymentStatus VARCHAR(255) NOT NULL," +
            "PRIMARY KEY (OrderID)," +
            "FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)" +
            ");",
    
            "CREATE TABLE OrderLines (" +
            "OrderLineID INT UNSIGNED NOT NULL AUTO_INCREMENT," +
            "OrderID INT UNSIGNED NOT NULL," +
            "ProductID INT UNSIGNED NOT NULL," +
            "Quantity INT UNSIGNED NOT NULL," +
            "PriceAtPurchase DECIMAL(10, 2) UNSIGNED NOT NULL," +
            "PRIMARY KEY (OrderLineID)," +
            "FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)," +
            "FOREIGN KEY (ProductID) REFERENCES Products(ProductID)" +
            ");",
    
            "CREATE TABLE Payments (" +
            "PaymentID INT UNSIGNED NOT NULL AUTO_INCREMENT," +
            "OrderID INT UNSIGNED NOT NULL," +
            "Amount DECIMAL(10, 2) UNSIGNED NOT NULL," +
            "PaymentDate DATE NOT NULL," +
            "Method VARCHAR(255) NOT NULL," +
            "PRIMARY KEY (PaymentID)," +
            "FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)" +
            ");"
        };
    
        for (String query : queries) {
            try {
                conn.createStatement().executeUpdate(query);
            } catch (SQLException e) {
                System.err.println("Error creating table: " + e.getMessage());
                return false;
            }
        }
    
        return true;
    }

    @Override
    public boolean deleteData(Connection conn) {
        String[] queries = {
            "DELETE FROM OrderLines", 
            "DELETE FROM Payments",   
            "DELETE FROM Orders",     
            "DELETE FROM Products",   
            "DELETE FROM Customers",  
            "DELETE FROM Suppliers"   
        };

        try {
            Statement stmt = conn.createStatement();
            
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            for (String query : queries) {
                stmt.executeUpdate(query);
            }
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1"); 
            
        } catch (SQLException e) {
            System.err.println("Error deleting data: " + e.getMessage());
            
            try {
                conn.createStatement().executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
            } catch (SQLException e2) {
                System.err.println("Error re-enabling foreign key checks: " + e2.getMessage());
            }
            return false;
        }
        return true;
    }
}
