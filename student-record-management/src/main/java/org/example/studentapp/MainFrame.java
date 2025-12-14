package org.example.studentapp;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    public MainFrame() {

        // ===== Window Setup =====
        setTitle("Student Record Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // ===== TOP PANEL (LOGO + TITLE) =====
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));
        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(img));

        JLabel titleLabel = new JLabel("Student Record Management System");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 25, 0));

        topPanel.add(logoLabel);
        topPanel.add(titleLabel);

        add(topPanel, BorderLayout.NORTH);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 25, 25));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 180, 40, 180));

        JButton addBtn = createRoundedButton("Add Student", new Color(52, 152, 219));
        JButton viewBtn = createRoundedButton("View Students", new Color(46, 204, 113));

        // ---- Add Student Action ----
        addBtn.addActionListener(e -> {
            StudentDialog dialog = new StudentDialog(this);
            dialog.setStudent(null);
            dialog.setVisible(true);

            if (dialog.isSaved()) {
                try {
                    StudentDAO dao = new StudentDAO();
                    dao.insert(dialog.getStudent());
                    JOptionPane.showMessageDialog(this, "Student saved successfully!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error saving student:\n" + ex.getMessage());
                }
            }
        });

        // ---- View Students Action ----
        viewBtn.addActionListener(e -> {
            ViewStudentsFrame viewFrame = new ViewStudentsFrame();
            viewFrame.setVisible(true);
        });

        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);

        add(buttonPanel, BorderLayout.CENTER);

        // ===== BOTTOM WATERMARK PANEL =====
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        JLabel watermark = new JLabel(
                "<html><center>" +
                        "Developed By:<br>" +
                        "1. Jashwanth Reddy - 2520030236<br>" +
                        "2. V.Rohith- 2520090029<br>" +
                        "3. Sai Pavan Reddy - 2520040018<br>" +
                        "4. P.Tanvitha - 2520030385<br>" +
                        "5. Rukesh Venati - 2520030519<br>" +
                        "6. N.Yishnaani - 2520030414<br>" +
                        "</center></html>"
        );

        watermark.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        watermark.setForeground(new Color(120, 120, 120)); // light grey
        watermark.setHorizontalAlignment(SwingConstants.CENTER);

        bottomPanel.add(watermark);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ===== Rounded Button Creator =====
    private JButton createRoundedButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);

        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        Color hoverColor = color.darker();
        btn.addMouseListener(new MouseAdapter() {
            @Override

            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}