import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertVitalSignsMonitoringApp extends JFrame {
    private JTextField monitoringIDField;
    private JTextField patientIDField;
    private JTextField timeField;
    private JTextField heartRateField;
    private JTextField respiratoryRateField;
    private JTextField oxygenSaturationField;
    private JTextField diagnosticTestIDField;

    public InsertVitalSignsMonitoringApp() {
        setTitle("Insert Vital Signs Monitoring Record");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 10, 10)); 

        JLabel monitoringIDLabel = new JLabel("Monitoring ID:");
        monitoringIDField = new JTextField();
        JLabel patientIDLabel = new JLabel("Patient ID:");
        patientIDField = new JTextField();
        JLabel timeLabel = new JLabel("Time:");
        timeField = new JTextField();
        JLabel heartRateLabel = new JLabel("Heart Rate:");
        heartRateField = new JTextField();
        JLabel respiratoryRateLabel = new JLabel("Respiratory Rate:");
        respiratoryRateField = new JTextField();
        JLabel oxygenSaturationLabel = new JLabel("Oxygen Saturation:");
        oxygenSaturationField = new JTextField();
        JLabel diagnosticTestIDLabel = new JLabel("Diagnostic Test ID:");
        diagnosticTestIDField = new JTextField();

        JButton insertButton = new JButton("Insert Record");
        JButton backButton = new JButton("Back ");

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertRecord();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new AdminDashboard().setVisible(true);
               dispose();
            }
        });

        add(monitoringIDLabel);
        add(monitoringIDField);
        add(patientIDLabel);
        add(patientIDField);
        add(timeLabel);
        add(timeField);
        add(heartRateLabel);
        add(heartRateField);
        add(respiratoryRateLabel);
        add(respiratoryRateField);
        add(oxygenSaturationLabel);
        add(oxygenSaturationField);
        add(diagnosticTestIDLabel);
        add(diagnosticTestIDField);
        add(insertButton);
        add(backButton);

        Insets textFieldInsets = new Insets(5, 5, 5, 5);
        monitoringIDField.setMargin(textFieldInsets);
        patientIDField.setMargin(textFieldInsets);
        timeField.setMargin(textFieldInsets);
        heartRateField.setMargin(textFieldInsets);
        respiratoryRateField.setMargin(textFieldInsets);
        oxygenSaturationField.setMargin(textFieldInsets);
        diagnosticTestIDField.setMargin(textFieldInsets);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void insertRecord() {
        String monitoringID = monitoringIDField.getText();
        String patientID = patientIDField.getText();
        String time = timeField.getText();
        String heartRate = heartRateField.getText();
        String respiratoryRate = respiratoryRateField.getText();
        String oxygenSaturation = oxygenSaturationField.getText();
        String diagnosticTestID = diagnosticTestIDField.getText();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/neonatal", "sqluser", "password");
            String insertQuery = "INSERT INTO VitalSignsMonitoring (MonitoringID, PatientID, Time, HeartRate, RespiratoryRate, OxygenSaturation, DiagnosticTestID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, Integer.parseInt(monitoringID));
            preparedStatement.setInt(2, Integer.parseInt(patientID));
            preparedStatement.setString(3, time);
            preparedStatement.setInt(4, Integer.parseInt(heartRate));
            preparedStatement.setInt(5, Integer.parseInt(respiratoryRate));
            preparedStatement.setString(6, oxygenSaturation);
            preparedStatement.setInt(7, Integer.parseInt(diagnosticTestID));
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
                new InsertVitalSignsMonitoringApp();
            }
        });
    }
}
