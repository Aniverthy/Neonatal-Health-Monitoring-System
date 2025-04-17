import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertNICUAnalyticsApp extends JFrame {
    private JTextField analyticsIDField;
    private JTextField averageLengthOfStayField;
    private JTextField mortalityRateField;

    public InsertNICUAnalyticsApp() {
        setTitle("Insert NICU Analytics Record");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel analyticsIDLabel = new JLabel("Analytics ID:");
        analyticsIDField = new JTextField(20);
        JLabel averageLengthOfStayLabel = new JLabel("Average Length Of Stay:");
        averageLengthOfStayField = new JTextField(20);
        JLabel mortalityRateLabel = new JLabel("Mortality Rate:");
        mortalityRateField = new JTextField(20);

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
                dispose(); // Close the current frame (InsertNICUAnalyticsApp)
                new AdminDashboard().setVisible(true); // Open the AdminDashboard frame
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(analyticsIDLabel, gbc);
        gbc.gridx = 1;
        add(analyticsIDField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(averageLengthOfStayLabel, gbc);
        gbc.gridx = 1;
        add(averageLengthOfStayField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(mortalityRateLabel, gbc);
        gbc.gridx = 1;
        add(mortalityRateField, gbc);
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
        String analyticsID = analyticsIDField.getText();
        String averageLengthOfStay = averageLengthOfStayField.getText();
        String mortalityRate = mortalityRateField.getText();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/neonatal", "sqluser", "password");
            String insertQuery = "INSERT INTO NICUAnalytics (AnalyticsID, AverageLengthOfStay, MortalityRate) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, Integer.parseInt(analyticsID));
            preparedStatement.setString(2, averageLengthOfStay);
            preparedStatement.setDouble(3, Double.parseDouble(mortalityRate));
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
                new InsertNICUAnalyticsApp();
            }
        });
    }
}
