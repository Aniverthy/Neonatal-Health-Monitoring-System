import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;
import java.sql.*;

public class PatientDashboard extends JFrame {
    private Connection connection;
    private String username;
    private JTextArea patientDetailsTextArea;

    public PatientDashboard(String username, Connection connection) {
        this.username = username;
        this.connection = connection;

        setTitle("Patient Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center the JFrame on the screen
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Patient Dashboard");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        patientDetailsTextArea = new JTextArea();
        patientDetailsTextArea.setEditable(false);
        patientDetailsTextArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
        JScrollPane scrollPane = new JScrollPane(patientDetailsTextArea);
        add(scrollPane, BorderLayout.CENTER);

        loadPatientDetails();

        JButton backButton = new JButton("Back to MainApp");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainApp().setVisible(true);
                dispose();
            }
        });

        JButton printButton = new JButton("Print Report");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printPatientReport();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(printButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadPatientDetails() {
        try {
            String query = "SELECT * FROM PatientInformation AS PI " +
                           "LEFT JOIN MedicalRecords AS MR ON PI.PatientID = MR.PatientID " +
                           "LEFT JOIN DiagnosticTests AS DT ON PI.PatientID = DT.PatientID " +
                           "LEFT JOIN VitalSignsMonitoring AS VSM ON PI.PatientID = VSM.PatientID " +
                           "LEFT JOIN ParentCommunication AS PC ON PI.PatientID = PC.PatientID " +
                           "LEFT JOIN PatientDoctorAssignment AS PDA ON PI.PatientID = PDA.PatientID " +
                           "WHERE PI.PatientID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            StringBuilder patientInfo = new StringBuilder();
            while (resultSet.next()) {
                patientInfo.append("Patient Details:\n");
                patientInfo.append("---------------------------------------------------\n");
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnValue = resultSet.getString(i);
                    patientInfo.append(String.format("%-20s %s\n", columnName + ":", columnValue));
                }
                patientInfo.append("---------------------------------------------------\n");
            }

            patientDetailsTextArea.setText(patientInfo.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading patient details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void printPatientReport() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
                if (pageIndex > 0) {
                    return NO_SUCH_PAGE;
                }
    
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
    
                // Formatting for the official report
                String reportTitle = "Patient Report for Username: " + username;
                String patientDetails = patientDetailsTextArea.getText();
    
                Font titleFont = new Font("Arial", Font.BOLD, 24);
                Font detailsFont = new Font("Arial", Font.PLAIN, 14);
                int lineSpacing = 20; // Adjust line spacing as needed
    
                // Calculate the vertical position for each line of text
                int yOffset = 100; // Initial vertical position
    
                // Draw report title
                g2d.setFont(titleFont);
                g2d.drawString(reportTitle, 100, yOffset);
                yOffset += lineSpacing * 2; // Increase vertical position for spacing
    
                // Split patient details into lines and draw them
                g2d.setFont(detailsFont);
                String[] lines = patientDetails.split("\n");
                for (String line : lines) {
                    g2d.drawString(line, 100, yOffset);
                    yOffset += lineSpacing;
                }
    
                return PAGE_EXISTS;
            }
        });
    
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error printing report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Example of how to create and display the PatientDashboard frame
                // Connection connection = null; // You need to establish a database connection here
                // new PatientDashboard("sqluser", connection).setVisible(true);
            }
        });
    }
}
