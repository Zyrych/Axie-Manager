import javax.swing.*;
import java.awt.event.*;


public class RegistrationController implements ActionListener {
    private RegistrationView registrationView;
    private DatabaseManagerModel databaseManager;

    public RegistrationController(RegistrationView registrationView, DatabaseManagerModel databaseManager) {
        this.registrationView = registrationView;
        this.databaseManager = databaseManager;

        // Add action listener to the register button
        registrationView.addRegistrationListener(this);

        registrationView.addLoginInsteadListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginViewInstead();
                // Close the registration window
                registrationView.setVisible(false);
            }
        });
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registrationView.getRegisterButton()) {
            // Validate input fields
            String username = registrationView.getUsername();
            String password = registrationView.getPassword();
            String confirmedPassword = registrationView.getConfirmedPassword();
            String email = registrationView.getEmail();

            if (username.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty() || email.isEmpty()) {
                // Display error message for empty fields
                JOptionPane.showMessageDialog(registrationView, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmedPassword)) {
                // Display error message if passwords don't match
                JOptionPane.showMessageDialog(registrationView, "Passwords don't match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidEmail(email)) {
                // Display error message for invalid email
                JOptionPane.showMessageDialog(registrationView, "Invalid email address", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if username is unique
            if (databaseManager.doesUsernameExists(username)) {
                // Display error message for duplicate username
                JOptionPane.showMessageDialog(registrationView, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Perform registration by inserting into database
            boolean registrationSuccessful = databaseManager.registerUser(username, password, email);
            if (registrationSuccessful) {
                // Display success message
                JOptionPane.showMessageDialog(registrationView, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Clear input fields
                registrationView.clearFields();
            } else {
                // Display error message for registration failure
                JOptionPane.showMessageDialog(registrationView, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean isValidEmail(String email) {
        // Implement email validation logic (e.g., using regular expressions)
        // Return true if the email is valid, false otherwise
        return true; // Placeholder for demo, replace with actual validation
    }


    private void openLoginViewInstead() {
        LoginView loginView = new LoginView();
        // Create an instance of LoginController and pass the LoginView and DatabaseManager
        LoginController loginController = new LoginController(loginView, databaseManager);
        loginView.setVisible(true); // Make the LoginView visible
    }
}
