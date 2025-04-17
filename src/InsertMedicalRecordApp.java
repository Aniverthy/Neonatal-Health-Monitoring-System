import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertMedicalRecordApp extends JFrame {
    private JTextField recordIDField;
    private JTextField patientIDField;
    private JTextField primaryDiagnosisField;
    private JTextField treatmentField;
    private JTextField dischargeDateField;
    private JTextField analyticsIDField;
    private JTextField doctorIDField;

    public InsertMedicalRecordApp() {
        setTitle("Insert Medical Record");
        setSize(400, 350); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 2, 10, 10)); 

        JLabel recordIDLabel = new JLabel("Record ID:");
        recordIDField = new JTextField();
        JLabel patientIDLabel = new JLabel("Patient ID:");
        patientIDField = new JTextField();
        JLabel primaryDiagnosisLabel = new JLabel("Primary Diagnosis:");
        primaryDiagnosisField = new JTextField();
        JLabel treatmentLabel = new JLabel("Treatment:");
        treatmentField = new JTextField();
        JLabel dischargeDateLabel = new JLabel("Discharge Date (YYYY-MM-DD):");
        dischargeDateField = new JTextField();
        JLabel analyticsIDLabel = new JLabel("Analytics ID:");
        analyticsIDField = new JTextField();
        JLabel doctorIDLabel = new JLabel("Doctor ID:");
        doctorIDField = new JTextField();

        JButton insertButton = new JButton("Insert Record");
        JButton backButton = new JButton("Back  ");

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertRecord();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                new AdminDashboard().setVisible(true); 
                dispose();
            }
        });

        add(recordIDLabel);
        add(recordIDField);
        add(patientIDLabel);
        add(patientIDField);
        add(primaryDiagnosisLabel);
        add(primaryDiagnosisField);
        add(treatmentLabel);
        add(treatmentField);
        add(dischargeDateLabel);
        add(dischargeDateField);
        add(analyticsIDLabel);
        add(analyticsIDField);
        add(doctorIDLabel);
        add(doctorIDField);
        add(insertButton);
        add(backButton);

        Insets textFieldInsets = new Insets(5, 5, 5, 5);
        recordIDField.setMargin(textFieldInsets);
        patientIDField.setMargin(textFieldInsets);
        primaryDiagnosisField.setMargin(textFieldInsets);
        treatmentField.setMargin(textFieldInsets);
        dischargeDateField.setMargin(textFieldInsets);
        analyticsIDField.setMargin(textFieldInsets);
        doctorIDField.setMargin(textFieldInsets);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void insertRecord() {
        String recordID = recordIDField.getText();
        String patientID = patientIDField.getText();
        String primaryDiagnosis = primaryDiagnosisField.getText();
        String treatment = treatmentField.getText();
        String dischargeDate = dischargeDateField.getText();
        String analyticsID = analyticsIDField.getText();
        String doctorID = doctorIDField.getText();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/neonatal", "sqluser", "password");
            String insertQuery = "INSERT INTO MedicalRecords (RecordID, PatientID, PrimaryDiagnosis, Treatment, DischargeDate, AnalyticsID, DoctorID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, Integer.parseInt(recordID));
            preparedStatement.setInt(2, Integer.parseInt(patientID));
            preparedStatement.setString(3, primaryDiagnosis);
            preparedStatement.setString(4, treatment);
            preparedStatement.setDate(5, java.sql.Date.valueOf(dischargeDate));
            preparedStatement.setInt(6, Integer.parseInt(analyticsID));
            preparedStatement.setInt(7, Integer.parseInt(doctorID));
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
                new InsertMedicalRecordApp();
            }
        });
    }
}
