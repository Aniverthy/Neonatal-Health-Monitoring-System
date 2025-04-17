import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {
    private JComboBox<String> tableComboBox;
    private JComboBox<String> actionComboBox;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        // Center the JFrame on the screen
        setLocationRelativeTo(null);

        JLabel tableLabel = new JLabel("Select Table:");
        tableComboBox = new JComboBox<>(new String[]{"PatientRecord", "NICUAnalytics", "MedicalRecord", "DiagnosticTest", "VitalSignsMonitoring", "ParentCommunication"});
        JLabel actionLabel = new JLabel("Select Action:");
        actionComboBox = new JComboBox<>(new String[]{"Insert", "Delete"});

        JButton proceedButton = new JButton("Proceed");
        JButton backButton = new JButton("Back");

        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTable = tableComboBox.getSelectedItem().toString();
                String selectedAction = actionComboBox.getSelectedItem().toString();
                String frameName = selectedAction + selectedTable + "App";

                openFrame(frameName);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainApp().setVisible(true);
            }
        });

        // Add components to the JFrame using GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(tableLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(tableComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(actionLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(actionComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(proceedButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(backButton, gbc);

        setVisible(true);
    }

    private void openFrame(String frameName) {
        try {
            Class<?> frameClass = Class.forName(frameName);
            JFrame frame = (JFrame) frameClass.getDeclaredConstructor().newInstance();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error opening frame: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminDashboard();
            }
        });
    }
}
