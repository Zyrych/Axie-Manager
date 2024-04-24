import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class RegistrationView extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;
    private JButton registerButton, loginInsteadButton;


    public RegistrationView() {
        setTitle("Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add margin of 10 pixels on all sides

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField(15);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(15);

        registerButton = new JButton("Register");
        loginInsteadButton = new JButton("Login Instead");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(new JLabel()); // Empty label for alignment
        panel.add(registerButton);
        panel.add(new JLabel()); // Empty label for alignment
        panel.add(loginInsteadButton);

        add(panel);
        setVisible(true);
    }


    public String getUsername() {
        return usernameField.getText();
    }


    public String getPassword() {
        return new String(passwordField.getPassword());
    }


    public String getConfirmedPassword() {
        return new String(confirmPasswordField.getPassword());
    }


    public String getEmail() {
        return emailField.getText();
    }


    public void addRegistrationListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }


    public JButton getRegisterButton() {
        return registerButton;
    }


    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        emailField.setText("");
    }


    public void addLoginInsteadListener(ActionListener listener) {
        loginInsteadButton.addActionListener(listener);
    }
}
