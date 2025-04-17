import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteVitalSignsMonitoringApp extends JFrame {
    private JTextField monitoringIDField;

    public DeleteVitalSignsMonitoringApp() {
        setTitle("Delete Vital Signs Monitoring Record");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center the JFrame on the screen
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel monitoringIDLabel = new JLabel("Monitoring ID to Delete:");
        monitoringIDField = new JTextField(10);

        JButton deleteButton = new JButton("Delete Record");
        JButton backButton = new JButton("Back"); // Added back button

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRecord();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame (DeleteVitalSignsMonitoringApp)
                new AdminDashboard().setVisible(true); // Open the AdminDashboard frame
                dispose();
            }
        });

        topPanel.add(monitoringIDLabel);
        topPanel.add(monitoringIDField);

        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void deleteRecord() {
        String monitoringID = monitoringIDField.getText();

        // Perform the database delete operation using JDBC here
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/neonatal", "sqluser", "password");
            String deleteQuery = "DELETE FROM VitalSignsMonitoring WHERE MonitoringID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, monitoringID);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Record deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Record not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DeleteVitalSignsMonitoringApp();
            }
        });
    }
}
