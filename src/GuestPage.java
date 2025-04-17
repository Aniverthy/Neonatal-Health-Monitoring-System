import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class GuestPage extends JFrame {

    public GuestPage() {
        setTitle("Guest Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center the JFrame on the screen
        setLocationRelativeTo(null);

        // Heading label
        JLabel headingLabel = new JLabel("Welcome to Neonatal Health Monitoring System (Guest Page)");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        add(headingLabel, BorderLayout.NORTH);

        // Panel for hospital information
        JPanel hospitalPanel = new JPanel();
        hospitalPanel.setLayout(new BorderLayout());
        hospitalPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add margins

        JLabel hospitalLabel = new JLabel("Hospital Information:");
        hospitalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        hospitalLabel.setHorizontalAlignment(JLabel.CENTER);
        hospitalPanel.add(hospitalLabel, BorderLayout.NORTH);

        JTextArea hospitalInfo = new JTextArea(
                "Neonatal Health Hospital is dedicated to providing the highest quality care for newborns and infants. " +
                        "Our team of experienced doctors and staff are committed to ensuring the health and well-being of your child.\n\n" +
                        "Our state-of-the-art facilities are equipped with the latest medical technology to deliver the best care possible. " +
                        "We offer a wide range of services, including prenatal care, neonatal care, and pediatric care.\n\n" +
                        "At Neonatal Health Hospital, we prioritize the comfort and safety of our young patients and their families. " +
                        "We understand the unique needs of newborns and infants, and our compassionate healthcare professionals are here " +
                        "to provide support and guidance throughout your child's healthcare journey.\n\n" +
                        "Thank you for choosing Neonatal Health Hospital for your child's healthcare needs. " +
                        "We look forward to serving you and your family."
        );
        hospitalInfo.setWrapStyleWord(true);
        hospitalInfo.setLineWrap(true);
        hospitalInfo.setEditable(false);
        hospitalInfo.setBackground(getBackground());
        hospitalInfo.setFont(new Font("Arial", Font.PLAIN, 16));
        hospitalPanel.add(hospitalInfo, BorderLayout.CENTER);

        // Panel for doctor details
        JPanel doctorPanel = new JPanel(new BorderLayout());
        doctorPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add margins

        JLabel doctorLabel = new JLabel("Doctors Information:");
        doctorLabel.setFont(new Font("Arial", Font.BOLD, 18));
        doctorLabel.setHorizontalAlignment(JLabel.CENTER);
        doctorPanel.add(doctorLabel, BorderLayout.NORTH);

        JTable doctorTable = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Specialization");
        model.addColumn("Experience");

        addDoctorInfo(model, "Dr. John Doe", "Pediatrician", "10 years of experience");
        addDoctorInfo(model, "Dr. Jane Smith", "Neonatologist", "15 years of experience");
        addDoctorInfo(model, "Dr. David Wilson", "Obstetrician", "20 years of experience");

        doctorTable.setModel(model);
        doctorTable.setRowHeight(30);

        JScrollPane doctorScrollPane = new JScrollPane(doctorTable);
        doctorPanel.add(doctorScrollPane, BorderLayout.CENTER);

        // Split pane to divide the window into two sections
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, hospitalPanel, doctorPanel);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void addDoctorInfo(DefaultTableModel model, String name, String specialization, String experience) {
        model.addRow(new Object[]{name, specialization, experience});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GuestPage();
            }
        });
    }
}
