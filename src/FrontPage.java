import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrontPage extends JFrame {

    public FrontPage() {
        setTitle("Neonatal Health Monitoring System");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Heading label
        JLabel headingLabel = new JLabel("Neonatal Health Monitoring System");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        headingLabel.setForeground(Color.WHITE);
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(new Color(0, 76, 153)); // Set a custom background color
        headingPanel.add(headingLabel);
        add(headingPanel, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Add spacing between buttons

        // Button to open MainApp
        JButton mainAppButton = new JButton("Login");
        mainAppButton.setFont(new Font("Arial", Font.BOLD, 16));
        mainAppButton.setBackground(new Color(0, 122, 204)); // Set a custom background color
        mainAppButton.setForeground(Color.WHITE);
        mainAppButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMainApp();
            }
        });

        // Button to open GuestPage
        JButton guestPageButton = new JButton("Guest Page");
        guestPageButton.setFont(new Font("Arial", Font.BOLD, 16));
        guestPageButton.setBackground(new Color(0, 122, 204)); // Set a custom background color
        guestPageButton.setForeground(Color.WHITE);
        guestPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGuestPage();
            }
        });

        buttonPanel.add(mainAppButton);
        buttonPanel.add(guestPageButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Center the JFrame on the screen
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void openMainApp() {
        MainApp mainApp = new MainApp();
        mainApp.setVisible(true);
        dispose();
    }

    private void openGuestPage() {
       // Implement the GuestPage class with your desired content
        GuestPage guestPage = new GuestPage();
        guestPage.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrontPage();
            }
        });
    }
}
