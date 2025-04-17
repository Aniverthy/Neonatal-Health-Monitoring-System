import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDashboard extends JFrame {
    private Connection connection;
    private String username;
    private JTextArea doctorDetailsTextArea;

    public DoctorDashboard(String username, Connection connection) {
        this.username = username;
        this.connection = connection;

        setTitle("Doctor Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center the JFrame on the screen
        setLocationRelativeTo(null);

        // Create a panel for the header
        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        headerPanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Doctor Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Create a scrollable text area for doctor details
        doctorDetailsTextArea = new JTextArea();
        doctorDetailsTextArea.setEditable(false);
        doctorDetailsTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(doctorDetailsTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the back button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame (DoctorDashboard)
                new MainApp().setVisible(true); // Open the MainApp frame
            }
        });
        backButton.setFont(new Font("Arial", Font.PLAIN, 18));
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadDoctorDetails();
    }

    private void loadDoctorDetails() {
        try {
            // Fetch and display doctor details from the Doctor table
            String doctorQuery = "SELECT * FROM Doctor WHERE DoctorID = ?";
            PreparedStatement doctorStatement = connection.prepareStatement(doctorQuery);
            doctorStatement.setString(1, username);

            ResultSet doctorResultSet = doctorStatement.executeQuery();

            StringBuilder doctorInfo = new StringBuilder();
            if (doctorResultSet.next()) {
                doctorInfo.append("Doctor ID: ").append(doctorResultSet.getString("DoctorID")).append("\n");
                doctorInfo.append("Doctor Name: ").append(doctorResultSet.getString("Name")).append("\n");
                doctorInfo.append("Specialty: ").append(doctorResultSet.getString("Specialty")).append("\n");
                // You can append other doctor details as needed
            }

            // Fetch and display patients assigned to the doctor
            String patientQuery = "SELECT * FROM PatientDoctorAssignment AS PDA " +
                                  "INNER JOIN PatientInformation AS PI ON PDA.PatientID = PI.PatientID " +
                                  "WHERE PDA.DoctorID = ?";
            PreparedStatement patientStatement = connection.prepareStatement(patientQuery);
            patientStatement.setString(1, username);

            ResultSet patientResultSet = patientStatement.executeQuery();

            List<String> patientList = new ArrayList<>();
            while (patientResultSet.next()) {
                String patientName = patientResultSet.getString("MotherName");
                patientList.add(patientName);
            }

            doctorInfo.append("\nPatients Assigned:\n");
            if (!patientList.isEmpty()) {
                for (String patientName : patientList) {
                    doctorInfo.append("- ").append(patientName).append("\n");
                }
            } else {
                doctorInfo.append("- No patients assigned\n");
            }

            doctorDetailsTextArea.setText(doctorInfo.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Example usage:
                // DoctorDashboard doctorDashboard = new DoctorDashboard();
                // doctorDashboard.setVisible(true);
            }
        });
    }
}
