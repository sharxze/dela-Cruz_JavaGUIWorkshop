package pages;

import dal.admins.AdminDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginPage extends JFrame {
    private final AdminDAO adminDao = new AdminDAO();
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JLabel usernameErrorLabel;
    private final JLabel passwordErrorLabel;

    public LoginPage() {
        setTitle("Admin Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 550); // Initial size
        setLocationRelativeTo(null); // Center on screen

        // ==== Main Container Panel ====
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(50, 60, 50, 60));
        mainPanel.setBackground(Color.WHITE);

        // ==== Title Panel ====
        JLabel title = new JLabel("<html><div style='text-align:center;'>Welcome to Admin Portal</div></html>");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(new Color(40, 40, 100));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(title, BorderLayout.NORTH);

        // ==== Form Panel ====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);

        // Username Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Username:"), gbc);

        // Username Field
        usernameField = new JTextField();
        usernameField.setFont(fieldFont);
        usernameField.setToolTipText("At least 4 characters.");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(usernameField, gbc);

        // Username Error
        usernameErrorLabel = new JLabel(" ");
        usernameErrorLabel.setForeground(Color.RED);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(usernameErrorLabel, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Password:"), gbc);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);
        passwordField.setToolTipText("Min 6 characters incl. uppercase, number, symbol.");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(passwordField, gbc);

        // Password Error
        passwordErrorLabel = new JLabel(" ");
        passwordErrorLabel.setForeground(Color.RED);
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(passwordErrorLabel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // ==== Buttons Panel ====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        JButton signInButton = new JButton("Sign In");
        styleButton(signInButton, new Color(0, 123, 255));

        JButton signUpButton = new JButton("Sign Up");
        styleButton(signUpButton, new Color(40, 167, 69));

        buttonPanel.add(signInButton);
        buttonPanel.add(signUpButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // ==== Add Listeners ====
        signInButton.addActionListener(e -> handleLogin());
        signUpButton.addActionListener(e -> handleSignUp());

        // ==== Finalize ====
        setContentPane(mainPanel);
        setMinimumSize(new Dimension(350, 300));
        pack();
        setVisible(true);
    }

    private void styleButton(JButton button, Color color) {
        button.setFocusPainted(false);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(100, 35));
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        boolean valid = adminDao.checkIfAdminExists(username, password);
        if (valid) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            new StudentPage();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    private void handleSignUp() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        usernameErrorLabel.setText(" ");
        passwordErrorLabel.setText(" ");

        String error = Validator.validateSignup(username, password);
        if (error != null) {
            if (error.toLowerCase().contains("username")) {
                usernameErrorLabel.setText(error);
            } else if (error.toLowerCase().contains("password")) {
                passwordErrorLabel.setText(error);
            } else {
                JOptionPane.showMessageDialog(this, error);
            }
            return;
        }

        boolean exists = adminDao.checkIfAdminExists(username, password);
        if (exists) {
            JOptionPane.showMessageDialog(this, "User already exists. Please log in.");
        } else {
            boolean success = adminDao.addAdmin(username, password);
            if (success) {
                JOptionPane.showMessageDialog(this, "Sign up successful! You can now log in.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register. Please try again.");
            }
        }
    }
}
