package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import controllers.SalesReportController;

public class SalesReportView extends JFrame {
    private SalesReportController controller = new SalesReportController();

    private JComboBox<String> reportTypeComboBox;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton generateReportButton;
    private JCheckBox sortDescendingCheckBox;
    private JTextField cutoffField;

    public SalesReportView() {
        setTitle("Sales Report Generator");
        setSize(400, 250); 
        setLayout(new BorderLayout(10, 10)); 
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); 
    
        reportTypeComboBox = new JComboBox<>(new String[]{"Total Sale Per Month", "Total Sale Per Product", "Total Sale Per Customer"});
        startDateField = new JTextField("YYYY-MM-DD", 10);
        endDateField = new JTextField("YYYY-MM-DD", 10);
        generateReportButton = new JButton("Generate Report");
    
        
        sortDescendingCheckBox = new JCheckBox("Sort Sales Descending");
        sortDescendingCheckBox.setSelected(true);
        cutoffField = new JTextField("100", 10); 
    
        
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        typePanel.add(new JLabel("Report Type:"));
        typePanel.add(reportTypeComboBox);
        
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(new JLabel("Start Date:"));
        datePanel.add(startDateField);
        datePanel.add(new JLabel("End Date:"));
        datePanel.add(endDateField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(generateReportButton);
    
        
        JPanel sortAndCutoffPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sortAndCutoffPanel.add(new JLabel("Cutoff (top n):"));
        sortAndCutoffPanel.add(cutoffField);
        sortAndCutoffPanel.add(sortDescendingCheckBox);
    
        
        mainPanel.add(typePanel);
        mainPanel.add(datePanel);
        mainPanel.add(sortAndCutoffPanel); 
        mainPanel.add(buttonPanel);
    
        add(mainPanel, BorderLayout.CENTER); 
    
        setupButtonListeners();
    
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void setupButtonListeners() {
        generateReportButton.addActionListener((ActionEvent e) -> {
            
            boolean sortDescending = sortDescendingCheckBox.isSelected();
            int cutoff = 0;
            try {
                cutoff = Integer.parseInt(cutoffField.getText());
            } catch (NumberFormatException ex) {
                
                cutoff = 100;
            }
    
            String reportType = (String) reportTypeComboBox.getSelectedItem();
            String startDate = startDateField.getText();
            String endDate = endDateField.getText();
            boolean isDescending = sortDescending;
            int cutoffValue = cutoff;
            controller.generateReport(reportType, startDate, endDate, isDescending, cutoffValue);
        });
    }    
}
