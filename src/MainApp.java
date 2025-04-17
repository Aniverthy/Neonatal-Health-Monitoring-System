import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainApp extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userRoleComboBox;
    private Connection connection; // Database connection

    public MainApp() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel usernameLabel = new JLabel("ID:");
        usernameField = new JTextField();
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        JLabel userRoleLabel = new JLabel("User Role:");
        userRoleComboBox = new JComboBox<>(new String[]{"Admin", "Patient", "Doctor"});
        formPanel.add(userRoleLabel);
        formPanel.add(userRoleComboBox);

        add(formPanel, BorderLayout.CENTER);

        JButton loginButton = new JButton("Login");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Center the JFrame on the screen
        setLocationRelativeTo(null);

        // Initialize the database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/neonatal", "sqluser", "password");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String userRole = userRoleComboBox.getSelectedItem().toString();

                if ("Admin".equals(userRole) && "admin".equals(username) && "admin".equals(password)) {
                    openAdminDashboard();
                } else if (("Patient".equals(userRole) || "Doctor".equals(userRole)) && validateUser(username, password, userRole)) {
                    if ("Patient".equals(userRole)) {
                        openPatientDashboard(username);
                    } else {
                        openDoctorDashboard(username);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private boolean validateUser(String username, String password, String userRole) {
        String tableName = (userRole.equals("Patient")) ? "PatientInformation" : "Doctor";
        String idColumnName = (userRole.equals("Patient")) ? "PatientID" : userRole + "ID";
        String passwordColumnName = (userRole.equals("Patient")) ? "MotherName" : "Name";

        try {
            String query = "SELECT * FROM " + tableName + " WHERE " + idColumnName + "=? AND " + passwordColumnName + "=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // If a row exists, credentials are valid
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void openAdminDashboard() {
        AdminDashboard adminDashboard = new AdminDashboard();
        adminDashboard.setVisible(true);
        dispose();
    }

    private void openPatientDashboard(String username) {
        PatientDashboard patientDashboard = new PatientDashboard(username, connection);
        patientDashboard.setVisible(true);
        dispose();
    }

    private void openDoctorDashboard(String username) {
        DoctorDashboard doctorDashboard = new DoctorDashboard(username, connection);
        doctorDashboard.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainApp();
            }
        });
    }
}
