import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertDoctorInformationApp extends JFrame {
    private JTextField doctorIDField;
    private JTextField doctorNameField;
    private JTextField specializationField;

    public InsertDoctorInformationApp() {
        setTitle("Insert Doctor Information Record");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Center the JFrame on the screen
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel doctorIDLabel = new JLabel("Doctor ID:");
        doctorIDField = new JTextField(20);
        JLabel doctorNameLabel = new JLabel("Doctor Name:");
        doctorNameField = new JTextField(20);
        JLabel specializationLabel = new JLabel("Specialization:");
        specializationField = new JTextField(20);

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
                dispose(); // Close the current frame (InsertDoctorInformationApp)
                new AdminDashboard().setVisible(true); // Open the AdminDashboard frame
                dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(doctorIDLabel, gbc);
        gbc.gridx = 1;
        add(doctorIDField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(doctorNameLabel, gbc);
        gbc.gridx = 1;
        add(doctorNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(specializationLabel, gbc);
        gbc.gridx = 1;
        add(specializationField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(insertButton, gbc);
        gbc.gridy = 4;
        add(backButton, gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void insertRecord() {
        String doctorID = doctorIDField.getText();
        String doctorName = doctorNameField.getText();
        String specialization = specializationField.getText();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/neonatal", "sqluser", "password");
            String insertQuery = "INSERT INTO Doctor (DoctorID, Name, Specialty) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, Integer.parseInt(doctorID));
            preparedStatement.setString(2, doctorName);
            preparedStatement.setString(3, specialization);
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
                new InsertDoctorInformationApp();
            }
        });
    }
}
