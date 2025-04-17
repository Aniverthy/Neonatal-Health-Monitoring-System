import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertPatientRecordApp extends JFrame {
    private JTextField patientIDField;
    private JTextField motherIDField;
    private JTextField birthDateField;
    private JTextField gestationalAgeField;
    private JTextField weightField;
    private JTextField lengthField;
    private JTextField apgarScoreField;
    private JTextField admissionDateField;

    public InsertPatientRecordApp() {
        setTitle("Insert Patient Record");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 2, 10, 10)); 

        JLabel patientIDLabel = new JLabel("Patient ID:");
        patientIDField = new JTextField();
        JLabel motherIDLabel = new JLabel("Mother ID:");
        motherIDField = new JTextField();
        JLabel birthDateLabel = new JLabel("Birth Date (YYYY-MM-DD):");
        birthDateField = new JTextField();
        JLabel gestationalAgeLabel = new JLabel("Gestational Age:");
        gestationalAgeField = new JTextField();
        JLabel weightLabel = new JLabel("Weight:");
        weightField = new JTextField();
        JLabel lengthLabel = new JLabel("Length:");
        lengthField = new JTextField();
        JLabel apgarScoreLabel = new JLabel("Apgar Score:");
        apgarScoreField = new JTextField();
        JLabel admissionDateLabel = new JLabel("Admission Date (YYYY-MM-DD):");
        admissionDateField = new JTextField();

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

        add(patientIDLabel);
        add(patientIDField);
        add(motherIDLabel);
        add(motherIDField);
        add(birthDateLabel);
        add(birthDateField);
        add(gestationalAgeLabel);
        add(gestationalAgeField);
        add(weightLabel);
        add(weightField);
        add(lengthLabel);
        add(lengthField);
        add(apgarScoreLabel);
        add(apgarScoreField);
        add(admissionDateLabel);
        add(admissionDateField);
        add(insertButton);
        add(backButton);

        Insets textFieldInsets = new Insets(5, 5, 5, 5);
        patientIDField.setMargin(textFieldInsets);
        motherIDField.setMargin(textFieldInsets);
        birthDateField.setMargin(textFieldInsets);
        gestationalAgeField.setMargin(textFieldInsets);
        weightField.setMargin(textFieldInsets);
        lengthField.setMargin(textFieldInsets);
        apgarScoreField.setMargin(textFieldInsets);
        admissionDateField.setMargin(textFieldInsets);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void insertRecord() {
        String patientID = patientIDField.getText();
        String motherID = motherIDField.getText();
        String birthDate = birthDateField.getText();
        String gestationalAge = gestationalAgeField.getText();
        String weight = weightField.getText();
        String length = lengthField.getText();
        String apgarScore = apgarScoreField.getText();
        String admissionDate = admissionDateField.getText();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/neonatal", "sqluser", "password");
            String insertQuery = "INSERT INTO PatientInformation (PatientID, MotherName, BirthDate, GestationalAge, Weight, Length, ApgarScore, AdmissionDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, Integer.parseInt(patientID));
            preparedStatement.setString(2, motherID);
            preparedStatement.setString(3, birthDate);
            preparedStatement.setString(4, gestationalAge);
            preparedStatement.setString(5, weight);
            preparedStatement.setString(6, length);
            preparedStatement.setString(7, apgarScore);
            preparedStatement.setString(8, admissionDate);
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
                new InsertPatientRecordApp();
            }
        });
    }
}
