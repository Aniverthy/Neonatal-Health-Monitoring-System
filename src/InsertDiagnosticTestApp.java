import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertDiagnosticTestApp extends JFrame {
    private JTextField testIDField;
    private JTextField patientIDField;
    private JTextField testTypeField;
    private JTextField resultField;
    private JTextField vitalSignsMonitoringIDField;

    public InsertDiagnosticTestApp() {
        setTitle("Insert Diagnostic Test Record");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Center the JFrame on the screen
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel testIDLabel = new JLabel("Test ID:");
        testIDField = new JTextField(20);
        JLabel patientIDLabel = new JLabel("Patient ID:");
        patientIDField = new JTextField(20);
        JLabel testTypeLabel = new JLabel("Test Type:");
        testTypeField = new JTextField(20);
        JLabel resultLabel = new JLabel("Result:");
        resultField = new JTextField(20);
        JLabel vitalSignsMonitoringIDLabel = new JLabel("Vital Signs Monitoring ID:");
        vitalSignsMonitoringIDField = new JTextField(20);

        JButton insertButton = new JButton("Insert Record");
        JButton backButton = new JButton("Back");

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertRecord();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame (InsertDiagnosticTestApp)
                new AdminDashboard().setVisible(true); // Open the AdminDashboard frame
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(testIDLabel, gbc);
        gbc.gridx = 1;
        add(testIDField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(patientIDLabel, gbc);
        gbc.gridx = 1;
        add(patientIDField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(testTypeLabel, gbc);
        gbc.gridx = 1;
        add(testTypeField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(resultLabel, gbc);
        gbc.gridx = 1;
        add(resultField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(vitalSignsMonitoringIDLabel, gbc);
        gbc.gridx = 1;
        add(vitalSignsMonitoringIDField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(insertButton, gbc);
        gbc.gridy = 6;
        add(backButton, gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void insertRecord() {
        String testID = testIDField.getText();
        String patientID = patientIDField.getText();
        String testType = testTypeField.getText();
        String result = resultField.getText();
        String vitalSignsMonitoringID = vitalSignsMonitoringIDField.getText();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/neonatal", "sqluser", "password");
            String insertQuery = "INSERT INTO DiagnosticTests (TestID, PatientID, TestType, Result, VitalSignsMonitoringID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, Integer.parseInt(testID));
            preparedStatement.setInt(2, Integer.parseInt(patientID));
            preparedStatement.setString(3, testType);
            preparedStatement.setString(4, result);
            preparedStatement.setInt(5, Integer.parseInt(vitalSignsMonitoringID));
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record inserted successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inserting record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                new InsertDiagnosticTestApp();
            }
        });
    }
}
