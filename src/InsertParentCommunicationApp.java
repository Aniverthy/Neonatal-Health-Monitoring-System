import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertParentCommunicationApp extends JFrame {
    private JTextField communicationIDField;
    private JTextField patientIDField;
    private JTextField senderField;
    private JTextField dateOfCommunicationField;
    private JTextField timeField;
    private JTextField messageField;

    public InsertParentCommunicationApp() {
        setTitle("Insert Parent Communication Record");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10)); 

        JLabel communicationIDLabel = new JLabel("Communication ID:");
        communicationIDField = new JTextField();
        JLabel patientIDLabel = new JLabel("Patient ID:");
        patientIDField = new JTextField();
        JLabel senderLabel = new JLabel("Sender:");
        senderField = new JTextField();
        JLabel dateOfCommunicationLabel = new JLabel("Date of Communication (YYYY-MM-DD):");
        dateOfCommunicationField = new JTextField();
        JLabel timeLabel = new JLabel("Time:");
        timeField = new JTextField();
        JLabel messageLabel = new JLabel("Message:");
        messageField = new JTextField();

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

        add(communicationIDLabel);
        add(communicationIDField);
        add(patientIDLabel);
        add(patientIDField);
        add(senderLabel);
        add(senderField);
        add(dateOfCommunicationLabel);
        add(dateOfCommunicationField);
        add(timeLabel);
        add(timeField);
        add(messageLabel);
        add(messageField);
        add(insertButton);
        add(backButton);

        Insets textFieldInsets = new Insets(5, 5, 5, 5);
        communicationIDField.setMargin(textFieldInsets);
        patientIDField.setMargin(textFieldInsets);
        senderField.setMargin(textFieldInsets);
        dateOfCommunicationField.setMargin(textFieldInsets);
        timeField.setMargin(textFieldInsets);
        messageField.setMargin(textFieldInsets);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void insertRecord() {
        String communicationID = communicationIDField.getText();
        String patientID = patientIDField.getText();
        String sender = senderField.getText();
        String dateOfCommunication = dateOfCommunicationField.getText();
        String time = timeField.getText();
        String message = messageField.getText();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/neonatal", "sqluser", "password");
            String insertQuery = "INSERT INTO ParentCommunication (CommunicationID, PatientID, Sender, Dateofcom, Time, Message) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, Integer.parseInt(communicationID));
            preparedStatement.setInt(2, Integer.parseInt(patientID));
            preparedStatement.setString(3, sender);
            preparedStatement.setString(4, dateOfCommunication);
            preparedStatement.setString(5, time);
            preparedStatement.setString(6, message);
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
                new InsertParentCommunicationApp();
            }
        });
    }
}
